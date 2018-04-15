(ns brave-clojure.ch7-ex
  "https://www.braveclojure.com/read-and-eval/"
  )


;; 1. Use the list function, quoting, and read-string to create a list that,
;; when evaluated, prints your first name and your favorite sci-fi movie.
(defn ex1
  []
  (eval (apply list
               '#(doseq [i %&] (println i))
               (read-string "[\"Tomás\" \"Aelita, Queen of Mars\"]"))))


;; 2. Create an infix function that takes a list like (1 + 3 * 4 - 5) and
;; transforms it into the lists that Clojure needs in order to correctly
;; evaluate the expression using operator precedence rules.
;; TODO
