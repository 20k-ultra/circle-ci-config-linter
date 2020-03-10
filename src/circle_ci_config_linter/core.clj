(ns circle-ci-config-linter.core
  (:require [uswitch.lambada.core :refer [deflambdafn]]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [json-schema.core :as sch]
            [cheshire.core :as chesire]
            [clj-yaml.core :as yaml]
            [clojure.core.match :refer [match]]))

(defn read-json [path] (chesire/parse-string (slurp path) keyword))
(defn read-yml [path] (yaml/parse-string (slurp path)))

(defn plastic-button [valid text-output]
  (let [button-color (if valid "#4c1" "#e05d44")]
    (str "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"190\" height=\"20\"> <linearGradient id=\"b\" x2=\"0\" y2=\"100%\"> <stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\" /> <stop offset=\"1\" stop-opacity=\".1\" /> </linearGradient> <clipPath id=\"a\"> <rect width=\"190\" height=\"20\" rx=\"3\" fill=\"#fff\" /> </clipPath> <g clip-path=\"url(#a)\"> <path fill=\"#555\" d=\"M0 0h129v20H0z\" /> <path fill=\"" button-color "\" d=\"M129 0h61v20H129z\" /> <path fill=\"url(#b)\" d=\"M0 0h190v20H0z\" /> </g> <g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\"> <text x=\"655\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"655\" y=\"140\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"1588\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"510\"> " text-output " </text> <text x=\"1588\" y=\"140\" transform=\"scale(.1)\" textLength=\"510\"> " text-output " </text> </g> </svg>")))

(defn make-button
  ([valid text-output style]
    (match [style]
      [:plastic] (plastic-button valid text-output)
      :else (plastic-button valid text-output)))
  ([valid text-output]
    (make-button valid text-output :plastic)))

(defn handle-event
  [event]
  (def REPO (get-in event ["queryStringParameters" "repo"]))
  (def BRANCH "master")
  (try
    (def config (read-yml (str "https://raw.githubusercontent.com/" REPO "/" BRANCH "/.circleci/config.yml")))
    (catch java.io.FileNotFoundException e (def config nil))
    (catch Exception e (def config nil) (println (str "Caught: " (.toString e)))))
  (if (nil? config)
    (let [svg (make-button false "no config")]
      (hash-map :body svg))
    (let [schema (read-json (.getPath (io/resource "circle-ci-schema.json")))
        errors (:errors (sch/validate schema config))
        error-count (count errors)]
      (let [svg (make-button (= error-count 0) (str error-count " errors"))]
        (hash-map :body svg)))))

(deflambdafn circle-ci.lambda.linter
  [in out ctx]
  (let [event (json/read (io/reader in))
        res (handle-event event)]
    (with-open [w (io/writer out)]
      (json/write res w))))
