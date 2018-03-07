(ns brave-clojure.ch5-ex
  "https://www.braveclojure.com/functional-programming/"
  )


;; 1. You used (comp :intelligence :attributes) to create a function that
;; returns a character’s intelligence. Create a new function, attr, that
;; you can call like (attr :intelligence) and that does the same thing.
(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

;; Obvious way.
;(defn attr
  ;[attr-key]
  ;(attr-key (:attributes character))
  ;)

;; Using compose and then calling composed function
;(defn attr
  ;[attr-key]
  ;((comp attr-key :attributes) character)
  ;)

;; First function below accepts n-amount of keyword arguments to get a value
;; nested within a map. Then, with 'partial', create the attr function by
;; setting the first argument of 'get-map-nestedthat function to :attributes.
(defn get-map-nested-value [& nested-keys] (get-in character nested-keys))
(def attr (partial get-map-nested-value :attributes))

(defn ex1
  []
  (println (attr :intelligence))
  )
