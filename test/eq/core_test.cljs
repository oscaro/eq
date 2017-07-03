(ns eq.core-test
  (:require [eq.core :as eq]
            [cljs.test :refer-macros [deftest is testing]]
            [cljs.nodejs :as node]))

(deftest build-selector
  (is (nil? (eq/build-selector "foo"))))
