(ns eq.core-test
  (:require [eq.core :as eq]
            [cljs.test :refer-macros [deftest is are testing]]
            [cljs.nodejs :as node]))

(deftest build-selector
  (is (nil? (eq/->selector "foo"))))

(deftest select-empty-selector
  (let [sel (eq/->selector "")]
    (is (some? sel))
    (are [data] (= [data] (eq/select sel [data]))
       {}
       {:a 1 :b 2}
       {"" 42}
       )))
