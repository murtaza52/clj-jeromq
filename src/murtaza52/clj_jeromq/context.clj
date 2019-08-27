(ns murtaza52.clj-jeromq.context
  (:require [clojure.set :as cs])
  (:import [org.zeromq ZMQ ZContext]))


(def contexts (atom #{}))


(defn create-context!
  []
  (let [ctx (ZContext. 1)]
    (swap! contexts conj ctx)
    ctx))


(defn destroy-context!
  [ctx]
  (swap! contexts cs/difference #{ctx})
  (.destroy ctx))


(defn destroy-all-contexts!
  []
  (doseq [ctx @contexts]
    (destroy-context! ctx)))

;; destroy all contexts on shutdown
;; thanks to https://github.com/daveyarwood/ezzmq/blob/master/src/ezzmq/context.clj
(.addShutdownHook (Runtime/getRuntime)
                  (Thread.
                   (destroy-all-contexts!)))

(comment
  (def c1 (create-context!))
  (def c2 (create-context!))
  (destroy-context! c1))


