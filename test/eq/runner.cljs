(ns eq.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [eq.core-test]))

(doo-tests 'eq.core-test)
