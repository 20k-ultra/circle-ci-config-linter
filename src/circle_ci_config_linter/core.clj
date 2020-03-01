(ns circle-ci-config-linter.core
  (:refer-clojure :exclude [load])
  (:require [json-schema.core :as sch]
            [cheshire.core :as json]
            [clojure.java.io :as io]
            [clj-yaml.core :as yaml]))

(defn read-json [path] (json/parse-string (slurp path) keyword))
(defn read-yml [path] (yaml/parse-string (slurp path)))

(def REPO "CircleCI-Public/circleci-cli")
(def BRANCH "master")

(defn -main
  [& args]
  (let [schema (read-json (.getPath (io/resource "circle-ci-schema.json")))
        config (read-yml (str "https://raw.githubusercontent.com/" REPO "/" BRANCH "/.circleci/config.yml"))]
    (println (:errors (sch/validate schema config))))
    )
