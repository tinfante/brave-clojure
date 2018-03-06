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
(defn append-suspect
  [suspect-map-list suspect-name suspect-index]
  (conj suspect-map-list {:name suspect-name :glitter-index suspect-index})
  )

(defn ex2
  []
  (println
    (append-suspect suspects "Tomas Infante" -1)
    )
  )


;; 3. Write a function, validate, which will check that :name and
;; :glitter-index are present when you append. The validate function
;; should accept two arguments: a map of keywords to validating
;; functions, similar to conversions, and the record to be validated.
(def validations
  {:name (fn [x] (string? x))
   :glitter-index (fn [x] (integer? x))
   }
  )

(defn validate-key-value
  [vamp-key value]
  ((get validations vamp-key) value)
  )

(defn validate-all-key-values
  [record]
  (every?
    (fn [k] (validate-key-value k (k record)))
    (map (fn [[k v]] k) validations)
    )
  )

(defn validate
  [validations record]
  (if (every? (fn [k] (contains? record k))
              (map (fn [[k v]] k) validations))
    ; Record has all validation keys. Check if key values OK.
    (validate-all-key-values record)
    ; Record doesn't have all validation keys.
    false
    )
  )

(defn ex3
  []
  (println (str "{:name \"Tomas Infante\" :glitter-index 1} -> "
                (validate validations {:name "a" :glitter-index 9})))
  (println (str "{:name \"Tomas Infante\"} -> "
                (validate validations {:name "Tomas Infante"})))
  (println (str "{:name \"Tomas Infante\" :glitter-index \"one\"} -> "
                (validate validations {:name "Tomas Infante" :glitter-index "uno"})))
  )


;; 4. Write a function that will take your list of maps and convert it
;; back to a CSV string. You’ll need to use the clojure.string/join function.
;; TODO
(defn suspect-map-list->csv-string
  [suspect-map-list]
  (clojure.string/join
    "\n"
    (map (fn [sm] (str (:name sm) "," (:glitter-index sm))) suspect-map-list)
    )
  )

(defn ex4
  []
  (println (suspect-map-list->csv-string suspects))
  )
