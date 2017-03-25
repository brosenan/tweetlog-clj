(ns tweetlog.core
  (:require [permacode.core :as perm]
            [clojure.string :as str]
            [perm.Qmf6or1t8bjdsKqSpnawvTR9Vo8M4Lp2eNBgDv5AQNK3yR :as c]))

(perm/pure
 (def app :tweetlog)

 (c/defrule timeline-by-tweets [user author tweet]
   [:tweetlog/follows user author] (c/by [:user= user])
   [:tweetlog/tweeted author tweet] (c/by [:user= author]))
 
 (c/defclause timeline-1 [:tweetlog/timeline user -> author tweet]
   [timeline-by-tweets user author tweet] (c/by app))

 (c/defrule mention [mentioned author tweet]
   [:tweetlog/tweeted author tweet] (c/by [:user= author])
   (for [token (str/split tweet #"[ .,:!?]+")])
   (when (str/starts-with? token "@"))
   (let [mentioned (subs token 1)]))

 (c/defclause timeline-2 [:tweetlog/timeline user -> author tweet]
   [mention user author tweet] (c/by app)
   (let [tweet (str "You were mentioned: " tweet)])))