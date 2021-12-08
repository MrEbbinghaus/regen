(ns me.ebbinghaus.main
  (:require 
   [clojure.string :as str]
   [me.ebbinghaus.regen :as regen])
  (:gen-class))

(defn -main [& args]
  (if-let [input (or (first args) (slurp *in*))]
    (let [strings (str/split input #"\s")
          regex (regen/generate-str strings)]
      (println regex))
    (do
      (println "No input!")
      (System/exit 1))))