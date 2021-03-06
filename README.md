# eq

Like [`jq`](https://stedolan.github.io/jq/), but for EDN (and a lot less
powerful).

## Usage

```
$ eq [selector [<path>]]
```

If no path is given, `eq` reads from `stdin`. The default selector is `.` (just
like `jq`).

### Examples

```sh
# pretty-print each line
cat *.edn | eq .

# pretty-print the :foo-bar value of each line
cat *.edn | eq .foo-bar

# read from a file
eq .name people.edn
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

### With [Homebrew](https://brew.sh/) on macOS

    brew install bfontaine/utils/eq

## License

Copyright © 2016-2017 Oscaro
