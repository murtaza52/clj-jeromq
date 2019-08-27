(ns murtaza52.clj-jeromq.message
  (:import [org.zeromq ZFrame]))

(defn frame->str
  [frame]
  (apply str (map char frame)))

(defn receive
  [socket]
  (frame->str (.recv socket)))

(defn more?
  [socket]
  (.hasReceiveMore socket))

;; https://github.com/daveyarwood/ezzmq/blob/master/src/ezzmq/message.clj
(defn receive-msg
  [socket]
  (when-let [msg (receive socket)]
    (loop [msgs [msg]
           pred  (more? socket)]
      (if pred
        (recur (conj msgs (receive socket))
               (more? socket))
        msgs))))

;; TODO manage oversized strings
(defn string->frame
  [s]
  (ZFrame. s))


(defn msg->frames
  [msg]
  (mapv string->frame msg))


(defn send-frame
  ([socket frame] (send-frame socket frame ZFrame/MORE))
  ([socket frame n] (.send frame socket n)))


(defn send-msg
  [socket msg]
  (let [frames (msg->frames msg)]
    (doseq [frame (butlast frames)]
      (send-frame socket frame))
    (send-frame socket (last frames) 0)))
