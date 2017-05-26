(ns tweetlog.core
  (:require [permacode.core :as perm]
            [clojure.string :as str]
            [perm.QmTu5JshMoxu7CT9KvhqYjpJKwYYceuDC5dJoYdKMapRN7 :as c]))

(perm/pure
 (c/defrule followee-tweets [user author tweet]
   [:tweetlog/follows user author] (c/by user)
   [:tweetlog/tweeted author tweet] (c/by author))
 
 (c/defclause tl-1 [:tweetlog/timeline user -> author tweet]
   [followee-tweets user author tweet])

 (c/defrule mention [mentioned author tweet]
   [:tweetlog/tweeted author tweet] (c/by author)
   (for [token (str/split tweet #"[ .,:?!]+")])
   (when (str/starts-with? token "@"))
   (let [mentioned (subs token 1)]))

 (c/defclause tl-2 [:tweetlog/timeline user -> author tweet]
   [mention user author tweet]
   (let [tweet (str "You were mentioned: " tweet)])))



