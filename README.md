# circle-ci-config-linter

Add a lint errors badge on your repos [CircleCI](https://circleci.com/) config. You can learn more about CircleCI config options at [docs](https://circleci.com/docs/2.0/configuration-reference/).

### Making your own

Replace {{YOUR-REPOSITORY}} to your profile or org and repository name.

```
[![CircleCI-Linter](https://qnld475cd3.execute-api.us-east-2.amazonaws.com/beta?repo={{YOUR-REPOSITORY}})](https://github.com/20k-ultra/circle-ci-config-linter)
```

### Examples

[CircleCI-Public / circleci-cli](https://github.com/CircleCI-Public/circleci-cli/blob/master/.circleci/config.yml)

[![CircleCI-Linter](https://qnld475cd3.execute-api.us-east-2.amazonaws.com/beta?repo=CircleCI-Public/circleci-cli)](https://github.com/20k-ultra/circle-ci-config-linter)

[juxt / jinx](https://github.com/juxt/jinx//blob/master/.circleci/config.yml)

[![CircleCI-Linter](https://qnld475cd3.execute-api.us-east-2.amazonaws.com/beta?repo=juxt/jinx)](https://github.com/20k-ultra/circle-ci-config-linterl)

Repo without CircleCI config

[![CircleCI-Linter](https://qnld475cd3.execute-api.us-east-2.amazonaws.com/beta?repo=DNSCrypt/dnscrypt-proxy)](https://github.com/20k-ultra/circle-ci-config-linter)

### Building

```bash
$ lein install
$ lein test
```

### To do

- allow for passing parameter so that details about errors are returned instead of an SVG.
- add more button styles (See [shields.io](https://shields.io))

### Built with

This tool was made possible because of:

- [Schemastore](https://github.com/SchemaStore/schemastore/)
- [json-schema.clj](https://github.com/niquola/json-schema.clj)

The Schemastore spec used for this can be found in [/resources/circle-ci-schema.json](https://github.com/20k-ultra/circle-ci-config-linter/blob/master/resources/circle-ci-schema.json). I had to modify it slightly because the original was causing json-schema.clj to throw some errors.


## License

Copyright © 2020

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
