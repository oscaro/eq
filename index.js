require('./out-none/goog/bootstrap/nodejs');
require('./target/eq-none');
require('./out-none/eq/core');
eq.core._main(process.argv[2], process.argv[3], process.argv[4], process.argv[5], process.argv[6]);
