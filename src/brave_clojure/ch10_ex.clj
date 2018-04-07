(ns brave-clojure.ch10-ex
  "https://www.braveclojure.com/zombie-metaphysics/"
  )


;; 1. Create an atom with the initial value 0, use swap! to increment it a
;; couple of times, and then dereference it.
(defn create-swap-atom
  []
  (def my-atom (atom 0))
  (swap! my-atom (fn [current-state] (inc current-state)))
  (swap! my-atom (fn [current-state] (inc current-state)))
  @my-atom
  )

(defn ex1
  []
  (println (create-swap-atom))
  )


;; 2. Create a function that uses futures to parallelize the task of
;; downloading random quotes from http://www.braveclojure.com/random-quote
;; using (slurp "http://www.braveclojure.com/random-quote"). The futures
;; should update an atom that refers to a total word count for all quotes.
;; The function will take the number of quotes to download as an argument and
;; return the atom’s final value. Keep in mind that you’ll need to ensure that
;; all futures have finished before returning the atom’s final value. Here’s
;; how you would call it and an example result:
;;
;;        (quote-word-count 5)
;;        ; => {"ochre" 8, "smoothie" 2}
(defn get-quote
  []
  (slurp "https://www.braveclojure.com/random-quote")
  ) ; Exercise used to use http instead of https... cert error for https
    ; on osx, works at home on linux.

(defn clean-norm-quote
  [the-quote]
  (clojure.string/lower-case
    (clojure.string/trim
      (first (clojure.string/split the-quote #"--"))))
  )

(defn get-words [normalized-quote]
  (re-seq #"\w+" normalized-quote))

(defn update-word-count
  [word-count quote-word-list]
  (doseq [word quote-word-list]
    (swap! word-count (fn [state] (merge-with + state {word 1})))
    )
  )

(defn create-futures
  [num-quotes word-count]
  (repeatedly num-quotes
              (fn [] (future (update-word-count word-count
                              (get-words (clean-norm-quote (get-quote))))))
    )
  )

(defn deref-futures
  [quote-futures]
  (dorun (map deref quote-futures))
  )

(defn parallel-quote-word-count
  [num-quotes]
  (let [word-count (atom {})]
    (deref-futures (create-futures num-quotes word-count))
    @word-count)
  )

(defn ex2 [] (println (parallel-quote-word-count 5))
             (shutdown-agents)  ;; Otherwise "$lein run" doesn't end.
  )


;; 3. Create representations of two characters in a game. The first character
;; has 15 hit points out of a total of 40. The second character has a healing
;; potion in his inventory. Use refs and transactions to model the consumption
;; of the healing potion and the first character healing.
(defn create-char
  ([name- max-hp hp]
   {
    :name name-
    :max-hp max-hp
    :hp hp
    :inventory {:gold 0 :health-potion 0}
    }
   )
  ([name- max-hp hp gold health-potion]
   {
    :name name-
    :max-hp max-hp
    :hp hp
    :inventory {:gold gold :health-potion health-potion}
    }
   )
  )

(def Minsc (ref (create-char "Minsc" 40 15)))
(def Boo (ref (create-char "Boo" 5 5 200 3)))

; Does not check if giving character has enough of the item to make the
; transaction.
(defn transfer-item
  ([giving-char item receiving-char]
   (dosync
     (alter giving-char update-in [:inventory item] dec)
     (alter receiving-char update-in [:inventory item] inc)
     )
   )
  ([giving-char item receiving-char n]
   (dosync
     (alter giving-char update-in [:inventory item] #(- % n))
     (alter receiving-char update-in [:inventory item] #(+ % n))
     )
   )
  )

; Does not check if character has enough health potions or if max hp will
; be exceeded.
(defn consume-health-potion
  [character]
  (dosync
    (alter character update-in [:inventory :health-potion] dec)
    (alter character update-in [:hp] #(+ % 15))
    )
  )

(defn ex3
  []
  (println "Before:\n" @Boo "\n" @Minsc)
  (transfer-item Boo :health-potion Minsc 2)
  (consume-health-potion Minsc)
  (println "After:\n" @Boo "\n" @Minsc)
  )
