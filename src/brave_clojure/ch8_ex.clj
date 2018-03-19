(ns brave-clojure.ch8-ex
  "https://www.braveclojure.com/writing-macros/"
  )


;; 1. Write the macro when-valid so that it behaves similarly to when. Here is
;; an example of calling it:
;;
;;  (when-valid order-details order-details-validations
;;    (println "It's a success!")
;;    (render :success))
;;
;; When the data is valid, the println and render forms should be evaluated,
;; and when-valid should return nil if the data is invalid
