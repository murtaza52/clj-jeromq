(ns murtaza52.clj-jeromq
  "Example usage of the library"
  (:require [murtaza52.clj-jeromq.context :as zmqc]
            [murtaza52.clj-jeromq.socket :as zmqs]
            [murtaza52.clj-jeromq.message :as zmqm]))

;; create a subscriber

(future
  (let [;; create a context
        context (zmqc/create-context!)
        ;; create a socket
        socket (zmqs/create-socket context :sub)]
    ;; connect the socket
    (zmqs/connect socket "tcp://*:12345")
    ;; subscribe to the topic
    (zmqs/subscribe socket "")
    (while true
      ;; send a msg
      (println (zmqm/receive-msg socket)))
    ;; destroy the context
    (zmqc/destroy-context! context)))



;; create a new publisher socket

(let [;; create a context
      context (zmqc/create-context!)
      ;; create a socket
      socket (zmqs/create-socket context :pub)]
  ;; bind the socket
  (zmqs/bind socket "tcp://*:12345")
  ;; each msg is a seq of strings, where the first string represents the topic.
  (doseq [msg [["topic1" "hi"] ["topic2" "hello"] ["topic1" "world"] ["topic2" "sky"]]]
    ;; send a msg
    (println (str "sending msg " msg))
    (zmqm/send-msg socket msg))
  ;; destroy the context
  (zmqc/destroy-context! context))


