(ns me.ebbinghaus.regen
  (:require
   [clojure.string :as str])
  (:gen-class))

(defn into-trie [trie s]
  (assoc-in trie (conj (vec s) "") s))

(defn compress-trie
  [trie]
  (reduce-kv
   (fn [m k v]
     (if (map? v)
       (let [child (compress-trie v)
             children-keys (keys child)]
         (if (= 1 (count children-keys))
           (let [child-k (first children-keys)]
             (assoc m (str k child-k) (get child child-k)))
           (assoc m k child)))
       (assoc m k v)))
   {}
   trie))


(defn- line [n k]
  (if (zero? n)
    k
    (str (apply str (repeat (dec n) "| ")) "└ " k)))

(defn pprint-trie
  ([trie] (pprint-trie 0 trie))
  ([offset trie]
   (doseq [[k v] trie]
     (println (line offset (if (= "" k) "^" k)))
     (when (coll? v)
       (pprint-trie (inc offset) v)))))

(defn to-pattern-str [trie]
  (let [terminate? (contains? trie "")
        children
        (->> trie
             (map
              (fn [[k v]]
                (str k
                     (when (coll? v)
                       (to-pattern-str v)))))
             (remove #{""}))
        many? (< 1 (count children))
        singles? (every? #(= 1 (count %)) children)]

    (str
     (cond
       (and many? singles?)
       (str "[" (str/join children) "]")

       singles? (first children)

       :else (str "(" (str/join "|" children) ")"))
     (when terminate? "?"))))

(defn ^:export make-trie [coll]
  (reduce into-trie {} coll))

(defn ^:export generate-str [strings]
  (-> strings
      make-trie
      compress-trie
      to-pattern-str))

(defn ^:export generate-pattern [strings]
  (re-pattern (generate-str strings)))
