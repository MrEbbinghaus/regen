(ns me.ebbinghaus.regen-test
  (:require [clojure.test :refer :all]
            [me.ebbinghaus.regen :refer :all]))

(deftest compress-test
  (testing "If compression works"

    (is (= {"" true} (compress-trie {"" true})))
    (is (= {"a" true} (compress-trie {\a {"" true}})))
    (is (= {"ab" true} (compress-trie {\a {\b {"" true}}})))
    (is (= {\a {"" true
                "b" true}}
           (compress-trie {\a {"" true \b {"" true}}})))))
