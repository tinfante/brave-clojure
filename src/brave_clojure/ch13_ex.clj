(ns brave-clojure.ch13-ex
  "https://www.braveclojure.com/multimethods-records-protocols/"
  )


;; 1. Extend the full-moon-behavior multimethod to add behavior for your
;; own kind of were-creature.
(defmulti full-moon-behavior (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :cat
  [were-creature]
  (str (:name were-creature)
       " will howl to bet let out at 6am, then howl to be let back in at 7."))

(defn ex1
  []
  (println (full-moon-behavior {:were-type :cat :name "Negrin"}))
  )


;; 2. Create a WereSimmons record type, and then extend the WereCreature
;; protocol.
(defprotocol WereCreature
  (full-moon-behavior-2 [x]))

(defrecord WereSimmons
  [name- title]
  WereCreature
  (full-moon-behavior-2 [x]
    (str name- " will encourage people and sweat to the oldies"))
  )

(defn ex2
  []
  (println
    (full-moon-behavior-2 (->WereSimmons "Richard Simmons" "Personal Trainer"))
    ))


;; 3. Create your own protocol, and then extend it using extend-type and
;; extend-protocol.


;; 4. Create a role-playing game that implements behavior using multiple
;; dispatch.
