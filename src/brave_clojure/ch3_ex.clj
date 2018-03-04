(ns brave-clojure.ch3-ex)


; 1. Use the str, vector, list, hash-map, and hash-set functions.
(defn ex1
  []
  (let [
        str_call (str "¡Hola " "mundo " "cruel!")
        vec_call (vector 1 2 3 4)
        lst_call (list 1 2 3 4)
        map_call (hash-map :a 1 :b 2 :c 3)
        set_call (hash-set 1 1 1 1 1 1 2 3 4)
        ]
    (println str_call)
    (println vec_call)
    (println lst_call)
    (println map_call)
    (println set_call)
    )
  )


; 2. Write a function that takes a number and adds 100 to it.
(defn add100
  [n]
  (+ 100 n)
  )

(defn ex2
  [n]
  (println (add100 n))
  )


; 3. Write a function, dec-maker, that works exactly like the function
; inc-maker except with subtraction:
(defn dec-maker
  [n]
  (fn [m] (- m n))
  )

(defn ex3
  []
  (def dec9 (dec-maker 9))
  (println (dec9 10))
  )


; 4. Write a function, mapset, that works like map except the return
; value is a set:
(defn mapset
  [f v]
  ;(set (map f v))
  ;(apply hash-set (map f v))
  (into #{} (map f v))
  )

(defn ex4
  []
  (println (mapset inc [1 1 2 2]))
  )


; 5. Create a function that’s similar to symmetrize-body-parts except
; that it has to work with weird space aliens with radial symmetry.
; Instead of two eyes, arms, legs, and so on, they have five.
; TODO
(def asym-body-parts 
  [{:name "head" :size 3}
   {:name "left-eye" :size 1}
   {:name "left-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "nose" :size 1}
   {:name "neck" :size 2}
   {:name "left-shoulder" :size 3}
   {:name "left-upper-arm" :size 3}
   {:name "chest" :size 10}
   {:name "back" :size 10}
   {:name "left-forearm" :size 3}
   {:name "abdomen" :size 6}
   {:name "left-kidney" :size 1}
   {:name "left-hand" :size 2}
   {:name "left-knee" :size 2}
   {:name "left-thigh" :size 4}
   {:name "left-lower-leg" :size 3}
   {:name "left-achilles" :size 1}
   {:name "left-foot" :size 2}
   ]
  )

(defn matching-multi-part [part n]
  (defn gen-names [part]
    (if (re-matches #"^left-.*" (:name part))
      (map (fn [x] {:name (clojure.string/replace (:name part) #"^left-" (str x "-")) :size (:size part)} ) 
           (range 1 (+ 1 n) 1))
      [part]
      ))
  (gen-names part)
  )

(defn complete-body-parts
  [asym-body-parts n]
  (loop [remaining-asym-parts asym-body-parts 
       final-body-parts []]
    (if (empty? remaining-asym-parts) 
    final-body-parts
      (let [[part & remaining] remaining-asym-parts] 
        (recur remaining 
             (into final-body-parts
               (set (matching-multi-part part n)))
          )
        )))
  )

(defn ex5 
  []
  (println (complete-body-parts asym-body-parts 5))
  )


; 6. Create a function that generalizes symmetrize-body-parts and the
; function you created in Exercise 5. The new function should take a
; collection of body parts and the number of matching body parts to add.
; TODO
(defn ex6
  "See func matching-multi-part in section for exercise 5."
  [n]
  (println (complete-body-parts asym-body-parts n))
  )
