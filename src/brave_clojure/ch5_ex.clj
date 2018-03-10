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


;; 2. Implement the comp function.
(defn my-comp
  [first-func & rest-funcs]
  (if (empty? rest-funcs)
    ;#(first-func %)
    (fn [x] (first-func x))
    (fn [param] (first-func ((apply my-comp rest-funcs) param)))
    )
  )

(defn ex2
  []
  (println ((my-comp #(* % 2) #(+ % 1)) 3))
  )


;; 3. Implement the assoc-in function. Hint: use the assoc function and
;; define its parameters as [m [k & ks] v].
(defn my-assoc-in
  [m [first-key & rest-keys] value]
  (if (empty? rest-keys)
    (assoc m first-key value)
    (assoc m
           first-key
           (my-assoc-in (or (m first-key) {}) rest-keys value)
      )
    )
  )

(defn ex3
  []
  (println (my-assoc-in {:a {:b {:c "asd"}}} [:a :b :d] "qwe"))
)


;; 4. Look up and use the update-in function.
(defn ex4
  []
  (println
    (update-in character [:attributes :intelligence] - 9)
    )
  )

;; 5. Implement update-in.
(defn my-update-in
  [m [first-key & rest-keys] func & args]
  (if (empty? rest-keys)
    (apply update m first-key func args)
    (assoc m
           first-key
           (apply my-update-in (get m first-key) rest-keys func args)
      )
    )
  )

(defn ex5
  []
  (println
    (my-update-in character [:attributes :intelligence] - 9)
    )
  )
