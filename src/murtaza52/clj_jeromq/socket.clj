(ns murtaza52.clj-jeromq.socket
  (:import [org.zeromq ZMQ ZContext]))


;; from cljzmq
(def ^:const socket-types
  {:pair   ZMQ/PAIR
   :pub    ZMQ/PUB
   :sub    ZMQ/SUB
   :req    ZMQ/REQ
   :rep    ZMQ/REP
   :xreq   ZMQ/XREQ
   :xrep   ZMQ/XREP
   :dealer ZMQ/DEALER
   :router ZMQ/ROUTER
   :xpub   ZMQ/XPUB
   :xsub   ZMQ/XSUB
   :pull   ZMQ/PULL
   :push   ZMQ/PUSH})


(defn create-socket
  [ctx socket-type]
  (.createSocket ctx (socket-types socket-type)))

(defn bind
  [socket address]
  (.bind socket address))

(defn connect
  [socket address]
  (.connect socket address))

(defn subscribe
  [socket topic]
  (.subscribe socket (.getBytes topic)))

(defn set-timeout
  [socket]
  (.setSendTimeOut socket))
