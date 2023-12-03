(ns day-3
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

;; There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
;;
;; Here is an example engine schematic:
;;
;; 467..114..
;; ...*......
;; ..35..633.
;; ......#...
;; 617*......
;; .....+.58.
;; ..592.....
;; ......755.
;; ...$.*....
;; .664.598..
;;
;; In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
;;
;; Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?

(defn input-lines []
  (line-seq (io/reader "day-3/input")))

(defn all-numbers [line]
  (re-seq #"\d+" line))

(defn index&length [line number]
  [(string/index-of line number)
   (count number)])

(defn symbol? [x]
  (and (not (Character/isDigit x))
       (not (= \. x))))

; (defn has-symbol-horizontally [s n]
;   (let [[idx num-len] (index&length s n)]
;     (print idx num-len)
;
;     (when (not (= idx 1))
;       (let [previous (nth s (+ idx num-len))]
;         (print "previous")
;         (symbol? previous)))
;
;     (when (not (= idx (- (dec (count s)) num-len))))
;       (dbg (count s))
;       (print "num-len:" num-len)
;       (let [next (nth s (+ idx num-len))]
;         (dbg next)
;         (symbol? next))))

(defn has-symbol-horizontally [s n]
  (or (re-seq (re-pattern (str "[^\\.]" n)) s)
          (re-seq (re-pattern (str n "[^\\.]")) s)))

(defn has-symbol-between [line start end]
  (if (= line "")
    nil
    (some symbol? (subvec (vec line) start end))))

(defn has-symbol-nearby [number & {:keys [previous, current, next]
                                   :or {previous "", current "", next ""}}]
  (println previous "\n" current "\n" next)
  (let [[idx num-len] (index&length current number)]
    (if (or (has-symbol-horizontally current number)
            (has-symbol-between previous idx (+ idx num-len))
            (has-symbol-between next idx (+ idx num-len)))
      (Integer/parseInt number))))

(defn generate-valid-lines [lines line-number]
  (case line-number
        0 {:current (nth lines 0)
           :next (nth lines 1)}
        (count lines)
          {:current (nth lines line-number)
           :previous (nth lines (dec line-number))}))

(let [lines (input-lines)]
  (loop [line-number 0]
    (let [numbers (all-numbers (nth lines line-number))]
      (println numbers)
      (let [results (map (fn [num]
             (has-symbol-nearby num (generate-valid-lines lines line-number)))
           numbers)]
        results))
    (recur (inc line-number))))
