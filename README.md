# circle-ci-config-linter

Lint a [CircleCI](https://circleci.com/) config file from a github repository. You can learn more about CircleCI config options at [docs](https://circleci.com/docs/2.0/configuration-reference/).

This tool was made possible because of:

- [Schemastore](https://github.com/SchemaStore/schemastore/)
- [json-schema.clj](https://github.com/niquola/json-schema.clj)

The Schemastore spec used for this can be found in [/resources/circle-ci-schema.json](https://github.com/20k-ultra/circle-ci-config-linter/blob/master/resources/circle-ci-schema.json). I had to modify it slightly because the origin was causing json-schema.clj to throw some errors.

### Building

```bash
$ lein install
$ lein run
```

### To do

- make this run on Lambda with [lambada](https://github.com/uswitch/lambada)
- see why original schema-store spec for circleci config upsets json-schema.clj

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
