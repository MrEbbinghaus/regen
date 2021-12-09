# regen

Generate optimized regular expressions based of a set of strings.

Get it as a lib:

`me.ebbinghaus/regen {:mvn/version "1.0.7"}` [Clojars](https://clojars.org/me.ebbinghaus/regen)

Or a binary

## Usage

As a clj cli tool.

```sh-session
❯ clj -M:regen "romane romanus"                                        
(?:roman(?:e|us))

❯ clj -M:regen "romane romanus romulus rubens ruber rubicon rubicundus"
(?:r(?:om(?:an(?:e|us)|ulus)|ub(?:e(?:ns|r)|ic(?:on|undus))))

❯ clj -M:regen < <(curl -s https://raw.githubusercontent.com/dwyl/english-words/master/words.txt) > my-pattern.txt
```
As a lib

```clojure
(require '[me.ebbinghaus.regen :as regen])
; => nil
(regen/generate ["romane" "romanus" "romulus" "rubens" "ruber" "rubicon" "rubicundus"])
; => #"(?:r(?:om(?:an(?:e|us)|ulus)|ub(?:e(?:ns|r)|ic(?:on|undus))))"
```


Build native binary (needs [babashka](https://babashka.org/) and GraalVM installed):
```sh-session
$ bb native-image    
```

Run the project's tests:
```sh-session
$ clojure -T:build test
```
Run the project's CI pipeline and build a JAR:
```sh-session
$ clojure -T:build ci
```
Install it locally (requires the `ci` task be run first):
```sh-session
$ clojure -T:build install
```


## License

Copyright © 2021 Björn Ebbinghaus

Distributed under the Eclipse Public License version 1.0.
