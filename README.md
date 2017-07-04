# eq

Like [`jq`](https://stedolan.github.io/jq/), but for EDN (and a lot less
powerful).

## Usage

`eq` reads on `stdin` and takes an optional selector as its sole argument.

```sh
# pretty-print each line
cat *.edn | eq .

# pretty-print the :foo-bar value of each line
cat *.edn | eq .foo-bar
```

### Supported selectors

* `.`: the object itself. This is identical to giving no selector at all.
* `:foo`: Apply `:foo` to the current object.
* `.foo`: Equivalent of `:foo` but `.foo.bar` is interpreted as `:foo` then
  `:bar` instead of the `:foo.bar` keyword.

## Install

You need to have Node installed on your machine.

1. Retrieve this project locally and run `./scripts/build-advanced`. You can
   also download `eq.js` from the [releases page][releases]
2. `chmod u+x target/eq.js`
3. Move `target/eq.js` somewhere in your `PATH`

[releases]: https://github.com/oscaro/eq/releases

## License

Copyright © 2016-2017 Oscaro
