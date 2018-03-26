(ns brave-clojure.ch9-ex
  "https://www.braveclojure.com/concurrency/"
  )

;; 1. Write a function that takes a string as an argument and searches
;; for it on Bing and Google using the slurp function. Your function
;; should return the HTML of the first page returned by the search.
(defn do-search
  [query]
  (let [result (promise)]
    ;(future (deliver result (slurp (str "http://www.google.com/search?q=" query))))
    ;; Line above: google returns 403. Don't use for now.
    (future (deliver result (slurp (str "http://www.bing.com/search?q=" query))))
    (future (deliver result (slurp (str "http://search.yahoo.com/search?q=" query))))
    @result
    )
  )

(defn ex1
  []
  (println (do-search "clojure"))
  (shutdown-agents)  ;; Otherwise "$lein run" doesn't end.
  )


;; 2. Update your function so it takes a second argument consisting of
;; the search engines to use.
(def search-engines
  [
   ;"http://www.google.com/search?q="
   ;; Line above: google returns 403. Don't use for now.
   "http://www.bing.com/search?q="
   "http://search.yahoo.com/search?q="
   ]
  )

(defn do-search-2
  [query engines]
  (let [result (promise)]
    (doseq [engine engines]
      (future (deliver result (slurp (str engine query))))
      )
    @result
    )
  )

(defn ex2
  []
  (println (do-search-2 "clojure" search-engines))
  (shutdown-agents)
  )


;; 3. Create a new function that takes a search term and search engines
;; as arguments, and returns a vector of the URLs from the first page
;; of search results from each search engine.
;; TODO.
