(ns circle-ci-config-linter.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [circle-ci-config-linter.core :refer :all]))

(def test-data {:id 44 :price 2.99 :greetings ["hello" "hi" "good morning"] :country {:name "Canada"}})
(def plastic-button-with-zero-errors "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"190\" height=\"20\"> <linearGradient id=\"b\" x2=\"0\" y2=\"100%\"> <stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\" /> <stop offset=\"1\" stop-opacity=\".1\" /> </linearGradient> <clipPath id=\"a\"> <rect width=\"190\" height=\"20\" rx=\"3\" fill=\"#fff\" /> </clipPath> <g clip-path=\"url(#a)\"> <path fill=\"#555\" d=\"M0 0h129v20H0z\" /> <path fill=\"#4c1\" d=\"M129 0h61v20H129z\" /> <path fill=\"url(#b)\" d=\"M0 0h190v20H0z\" /> </g> <g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\"> <text x=\"655\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"655\" y=\"140\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"1588\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"510\"> 0 errors </text> <text x=\"1588\" y=\"140\" transform=\"scale(.1)\" textLength=\"510\"> 0 errors </text> </g> </svg>")
(def plastic-button-with-errors "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"190\" height=\"20\"> <linearGradient id=\"b\" x2=\"0\" y2=\"100%\"> <stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\" /> <stop offset=\"1\" stop-opacity=\".1\" /> </linearGradient> <clipPath id=\"a\"> <rect width=\"190\" height=\"20\" rx=\"3\" fill=\"#fff\" /> </clipPath> <g clip-path=\"url(#a)\"> <path fill=\"#555\" d=\"M0 0h129v20H0z\" /> <path fill=\"#e05d44\" d=\"M129 0h61v20H129z\" /> <path fill=\"url(#b)\" d=\"M0 0h190v20H0z\" /> </g> <g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\"> <text x=\"655\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"655\" y=\"140\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"1588\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"510\"> 5 errors </text> <text x=\"1588\" y=\"140\" transform=\"scale(.1)\" textLength=\"510\"> 5 errors </text> </g> </svg>")
(def plastic-button-with-no-config "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"190\" height=\"20\"> <linearGradient id=\"b\" x2=\"0\" y2=\"100%\"> <stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\" /> <stop offset=\"1\" stop-opacity=\".1\" /> </linearGradient> <clipPath id=\"a\"> <rect width=\"190\" height=\"20\" rx=\"3\" fill=\"#fff\" /> </clipPath> <g clip-path=\"url(#a)\"> <path fill=\"#555\" d=\"M0 0h129v20H0z\" /> <path fill=\"#e05d44\" d=\"M129 0h61v20H129z\" /> <path fill=\"url(#b)\" d=\"M0 0h190v20H0z\" /> </g> <g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\"> <text x=\"655\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"655\" y=\"140\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"1588\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"510\"> no config </text> <text x=\"1588\" y=\"140\" transform=\"scale(.1)\" textLength=\"510\"> no config </text> </g> </svg>")
(def plastic-button-good-config "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"190\" height=\"20\"> <linearGradient id=\"b\" x2=\"0\" y2=\"100%\"> <stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\" /> <stop offset=\"1\" stop-opacity=\".1\" /> </linearGradient> <clipPath id=\"a\"> <rect width=\"190\" height=\"20\" rx=\"3\" fill=\"#fff\" /> </clipPath> <g clip-path=\"url(#a)\"> <path fill=\"#555\" d=\"M0 0h129v20H0z\" /> <path fill=\"#4c1\" d=\"M129 0h61v20H129z\" /> <path fill=\"url(#b)\" d=\"M0 0h190v20H0z\" /> </g> <g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\"> <text x=\"655\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"655\" y=\"140\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"1588\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"510\"> 0 errors </text> <text x=\"1588\" y=\"140\" transform=\"scale(.1)\" textLength=\"510\"> 0 errors </text> </g> </svg>")
(def plastic-button-some-errors-config "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"190\" height=\"20\"> <linearGradient id=\"b\" x2=\"0\" y2=\"100%\"> <stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\" /> <stop offset=\"1\" stop-opacity=\".1\" /> </linearGradient> <clipPath id=\"a\"> <rect width=\"190\" height=\"20\" rx=\"3\" fill=\"#fff\" /> </clipPath> <g clip-path=\"url(#a)\"> <path fill=\"#555\" d=\"M0 0h129v20H0z\" /> <path fill=\"#e05d44\" d=\"M129 0h61v20H129z\" /> <path fill=\"url(#b)\" d=\"M0 0h190v20H0z\" /> </g> <g fill=\"#fff\" text-anchor=\"middle\" font-family=\"DejaVu Sans,Verdana,Geneva,sans-serif\" font-size=\"110\"> <text x=\"655\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"655\" y=\"140\" transform=\"scale(.1)\" textLength=\"1190\">circle-ci-config-lint</text> <text x=\"1588\" y=\"150\" fill=\"#010101\" fill-opacity=\".3\" transform=\"scale(.1)\" textLength=\"510\"> 5 errors </text> <text x=\"1588\" y=\"140\" transform=\"scale(.1)\" textLength=\"510\"> 5 errors </text> </g> </svg>")

(deftest test-read-json
  (testing
    (is (= test-data (read-json (.getPath (io/resource "test.json")))))))

(deftest test-read-yml
  (testing
    (is (= test-data (read-yml (.getPath (io/resource "test.yml")))))))

(deftest test-plastic-button-no-errors
  (testing
    (is (= plastic-button-with-zero-errors (plastic-button true "0 errors")))))

(deftest test-plastic-button-with-errors
  (testing
    (is (= plastic-button-with-errors (plastic-button false "5 errors")))))

(deftest test-plastic-button-with-no-config
  (testing
    (is (= plastic-button-with-no-config (plastic-button false "no config")))))

(deftest test-make-button-default-style
  (testing
    (is (= plastic-button-with-zero-errors (make-button true "0 errors")))))

(deftest test-make-button-default-style
  (testing
    (is (= plastic-button-with-zero-errors (make-button true "0 errors" :plastic)))))

(deftest test-linting-no-config
  (testing
    (is (= plastic-button-with-no-config (-main)))))

(deftest test-linting-no-error-default-style-config
  (testing
    (is (= plastic-button-good-config (-main "juxt/jinx")))))

(deftest test-linting-error-default-style-config
  (testing
    (is (= plastic-button-some-errors-config (-main "CircleCI-Public/circleci-cli")))))