(ns brave-clojure.ch7-ex
  "https://www.braveclojure.com/read-and-eval/"
  )


;; 1. Use the list function, quoting, and read-string to create a list that,
;; when evaluated, prints your first name and your favorite sci-fi movie.
(defn ex1
  []
  (eval (apply list
               '#(doseq [i %&] (println i))
               (read-string "[\"Tomás\" \"Aelita, Queen of Mars\"]")
               )))


;; 2. Create an infix function that takes a list like (1 + 3 * 4 - 5) and
;; transforms it into the lists that Clojure needs in order to correctly
;; evaluate the expression using operator precedence rules.
(defn validate-infix-list
  [infix-list]
  (and
    (< 2 (count infix-list))
    (odd? (count infix-list))
    (every? #(= (type %) java.lang.Long) (take-nth 2 infix-list))
    (every?
      #(or (= '* %) (= '/ %) (= '+ %) (= '- %))
      (take-nth 2 (rest infix-list))
      )))

(defn solve-first
  [infix-exp]
    [(list (second infix-exp) (first infix-exp) (nth infix-exp 2))
     (take-last (- (count infix-exp) 3) infix-exp)
     ])

(defn solve-rest
  [polish rest-infix]
  (cond (empty? rest-infix)
        polish

        (= (first rest-infix) (first polish))
        (solve-rest (apply list (conj (vec polish) (second rest-infix)))
                    (take-last (- (count rest-infix) 2) rest-infix)
                    )

        (and (or (= '* (first rest-infix)) (= '/ (first rest-infix)))
             (or (= '+ (first polish)) (= '- (first polish)))
             )
        (solve-rest (apply list (conj 
                                  (vec (take (dec (count polish)) polish))
                                  (list (first rest-infix)
                                        (last polish)
                                        (second rest-infix)
                                        )))
                    (take-last (- (count rest-infix) 2) rest-infix)
                    )

        :else
        (solve-rest (apply list (conj 
                                  (vec (conj () polish (first rest-infix)))
                                  (second rest-infix)
                                  ))
                    (take-last (- (count rest-infix) 2) rest-infix)
                    )))

(defn infix->polish
  [infix-exp]
  (if (validate-infix-list infix-exp)
    (apply solve-rest (solve-first infix-exp))
    nil
    ))

(defn ex2
  [infix-exp]
  (let [polish-exp (infix->polish infix-exp)
        result (eval (infix->polish infix-exp))
        ]
    (println infix-exp)
    (println polish-exp)
    (println result)
    ))
