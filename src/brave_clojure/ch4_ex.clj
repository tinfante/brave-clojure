(ns brave-clojure.ch4-ex
  "https://www.braveclojure.com/core-functions-in-depth/"
  (:require [brave-clojure.ch4-fwpd :as fwpd])
  )


;; 1. Turn the result of your glitter filter into a list of names.
(def suspects 
  (fwpd/glitter-filter 3 (fwpd/mapify (fwpd/parse (slurp fwpd/filename))))
  )

(defn suspect-map-list->name-list
  [suspect-map-list]
  (map (fn [row] (:name row)) suspect-map-list)
  )

(defn ex1
  []
  (println (clojure.string/join "\n" (suspect-map-list->name-list suspects)))
  )


;; 2. Write a function, append, which will append a new suspect to your
;; list of suspects.
(defn append
  [suspect-map-list suspect-name suspect-index]
  (conj suspect-map-list {:name suspect-name :glitter-index suspect-index})
  )

(defn ex2
  []
  (println
    (append suspects "Tomas Infante" -1)
    )
  )


;; 3. Write a function, validate, which will check that :name and
;; :glitter-index are present when you append. The validate function
;; should accept two arguments: a map of keywords to validating
;; functions, similar to conversions, and the record to be validated.
;; TODO
(defn validate
  [validations record]
  (if (every? (fn [k] (contains? record k))
              (map (fn [[k v]] k) validations))
    ; Record has all validation keys.
    true
    ; Record doesn't have all validation keys.
    false
    )
  )

(defn ex3
  []
  (println (validate {:name "a" :glitter-index 9}
                     ;{:name "Mal Registro"}
                     {:name "Yuri Guigan" :glitter-index 1000}
             )
    )
  )


;; 4. Write a function that will take your list of maps and convert it
;; back to a CSV string. You’ll need to use the clojure.string/join function.
;; TODO
