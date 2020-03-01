(defproject circle-ci-config-linter "0.1.0-SNAPSHOT"
  :description "CircleCI config linter using SchemaStore spec"
  :url "https://github.com/20k-ultra/circle-ci-config-linter"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                [json-schema "0.2.0-RC11"]
                [cheshire "5.6.3"]
                [clj-commons/clj-yaml "0.7.0"]]
  :main ^:skip-aot circle-ci-config-linter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
