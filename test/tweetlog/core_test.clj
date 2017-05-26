(ns tweetlog.core-test
  (:require [midje.sweet :refer :all]
            [tweetlog.core :refer :all]
            [perm.QmTu5JshMoxu7CT9KvhqYjpJKwYYceuDC5dJoYdKMapRN7 :as c]))

(def rules (for [[k v] (ns-publics 'tweetlog.core)] @v))
(defn test-rules [facts name arity]
  ((apply c/simulate-rules-with rules facts) [name arity]))
(defn query [facts q arity readers]
  (apply c/run-query rules q arity :foo readers facts))

(def story
  [(c/f [:tweetlog/tweeted "maui" "I'm a #demigod!"]
        :writers #{"maui"})
   (c/f [:tweetlog/follows "moana" "maui"]
        :writers #{"moana"})
   (c/f [:tweetlog/tweeted "tamatoa" "I'm so shiny!"]
        :writers #{"tamatoa"})
   (c/f [:tweetlog/follows "moana" "tamatoa"]
        :writers #{"tamatoa"})
   (c/f [:tweetlog/tweeted "maui" "@tamatoa is so shiny!"]
        :writers #{"tamatoa"})
   (c/f [:tweetlog.core/followee-tweets "moana" "tamatoa" "I'm so shiny!"]
        :writers #{"tamatoa"})
   (c/f [:tweetlog/tweeted "moana" "Hey @maui, time to save the world!"]
        :writers #{"moana"})
   (c/f [:tweetlog/tweeted "maui" "I just need to get my hook from @tamatoa"]
        :writers #{"maui"}
        :readers #{"moana"})])

(fact
 (query story [:tweetlog/timeline "moana"] 2 #{"moana"})
 => #{["maui" "I'm a #demigod!"]
      ["maui" "I just need to get my hook from @tamatoa"]}
 (query story [:tweetlog/timeline "maui"] 2 #{"maui"})
 => #{["moana" "You were mentioned: Hey @maui, time to save the world!"]}
 (query story [:tweetlog/timeline "tamatoa"] 2 #{"tamatoa"})
 => #{})
