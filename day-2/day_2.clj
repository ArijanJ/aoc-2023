(ns day-2
  (:require [clojure.java.io :as io]))

;; You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
;;
;; For example, the record of a few games might look like this:
;;
;; Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
;; Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
;; Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
;; Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
;; Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
;;
;; In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
;;
;; The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?

(defn input-lines []
  (line-seq (io/reader "day-2/input")))

(defn all-occurrences [line color]
  (re-seq (re-pattern (str "(\\d+) " color))
          line))

(defn id [line]
  (-> (re-find #"Game (\d+):" line)
      second
      Integer/parseInt))

(defn most-of [line color]
  (apply max (map (comp #(Integer/parseInt %) second)
                  (all-occurrences line color))))

(defn can-play? [line]
  (and (<= (most-of line "red")   12)
       (<= (most-of line "green") 13)
       (<= (most-of line "blue")  14)))

; Solution to part one
(reduce + (map (fn [line]
                 (if (can-play? line)
                   (id line)
                   0))
               (input-lines)))

;; As you continue your walk, the Elf poses a second question:
;; in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?

(defn power [line]
  (* (most-of line "red")
     (most-of line "green")
     (most-of line "blue")))

; Solution to part two
(reduce + (map power (input-lines)))
