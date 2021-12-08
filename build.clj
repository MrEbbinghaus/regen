(ns build
  (:refer-clojure :exclude [test])
  (:require 
   [org.corfield.build :as bb]))

(def lib 'me.ebbinghaus/regen)
(def version "1.0.0")

(defn assoc-lib-info [opts]
  (assoc opts :lib lib :version version :src-pom "template/pom.xml"))

(defn test "Run the tests." [opts]
  (bb/run-tests opts))

(defn ci "Run the CI pipeline of tests (and build the JAR)." [opts]
  (-> opts
      assoc-lib-info
      (bb/run-tests)
      (bb/clean)
      (bb/jar)))

(defn install "Install the JAR locally." [opts]
  (-> opts
      assoc-lib-info
      (bb/install)))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (-> opts
      assoc-lib-info
      (bb/deploy)))

(defn uber [opts]
  (-> opts
      assoc-lib-info
      (bb/clean)
      (bb/uber)))