(defproject flintstones "0.1.0-SNAPSHOT"
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"] ]
  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.15"] ]

  :source-paths ["src"]
  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src"]
                ;; The presence of a :figwheel configuration here will cause figwheel to inject the
                ;; figwheel client into your build
                :figwheel     {:on-jsload "flintstones.core/on-js-reload"
                               ;; :open-urls will pop open your application in the default browser once
                               ;; Figwheel has started and compiled your application.  Comment this out
                               ;; once it no longer serves you.
                               :open-urls ["http://localhost:3449/index.html"]}
                :compiler     {:main                 flintstones.core
                               :optimizations        :none
                               :libs                 ["resources/public/libs"] ; recursive includes all children
                               :output-to            "resources/public/js/compiled/flintstones.js"
                               :output-dir           "resources/public/js/compiled/flintstones-dev"
                               :asset-path           "js/compiled/flintstones-dev" ; rel to figwheel default of `resources/public`
                                                        ; ^^^ must match :output-dir
                               :source-map-timestamp true
                               ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                               ;; https://github.com/binaryage/cljs-devtools
                               :preloads             [devtools.preload]}}
               ]}
  :profiles {:dev {; need to add dev source path here to get user.clj loaded
                   :source-paths  ["src" "dev"]
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     "out"
                                                     :target-path]}}
 :jvm-opts ["-Xmx1g" "--add-modules" "java.xml.bind"]
)
