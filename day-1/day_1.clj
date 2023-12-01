(ns day-1
  (:require [clojure.java.io :as io]))

;; On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
;;
;; For example:
;;
;; 1abc2
;; pqr3stu8vwx
;; a1b2c3d4e5f
;; treb7uchet
;;
;; In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.

(defn input-lines []
  (line-seq (io/reader "day-1/input")))

(defn digits [xs]
  (filter #(Character/isDigit %) xs))

(defn join-first&last [s]
  (str (first s) (last s)))

; Solution to part one
(reduce + (map #(-> %
                    digits
                    join-first&last
                    Integer/parseInt)
               (input-lines)))

;; Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".

(def numbers #"(?=(one|two|three|four|five|six|seven|eight|nine|\d))")

(def word->digit
  (zipmap ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]
          ["1"   "2"   "3"     "4"    "5"    "6"   "7"     "8"     "9"]))

(defn str-to-digit [x]
  (if (= (count x) 1)
    x
    (get word->digit x)))

(defn find-digits [s]
  (map (comp str-to-digit second) (re-seq numbers s)))

; Solution to part two
(reduce + (map #(-> %
                    find-digits
                    join-first&last
                    Integer/parseInt)
               (input-lines)))
