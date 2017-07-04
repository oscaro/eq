# eq Changelog

## 0.2.1 (2017/07/04)

This is the first public release of `eq`.

* Expose `eq.core/select`
* Improve the documentation
* Support more selectors: `.foo.bar` for `#(-> % :foo :bar)` and `:foo` for
  simple keywords.
* Extract I/O utilities from `eq.core` into `eq.io`
* Add more tests
