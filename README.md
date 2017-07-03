# eq

Like [`jq`](https://stedolan.github.io/jq/), but for EDN (and a lot less
powerful).

## Usage

```sh
# pretty-print each line
cat *.edn | eq .

# pretty-print the :foo-bar value of each line
cat *.edn | eq .foo-bar
```

## Install

You need to have Node installed on your machine.

1. Retrieve this project locally and run `./scripts/build-advanced`
2. `chmod u+x target/eq.js`
3. Move `target/eq.js` somewhere in your `PATH`

## License

Copyright © 2016-2017 Oscaro
