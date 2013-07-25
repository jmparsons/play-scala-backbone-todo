# Play 2.1 Scala Todo list on Backbone
A Todo list application built on [Play Framework](http://www.playframework.com), [Backbone](http://backbonejs.org), [RequireJS](http://requirejs.org), [CoffeeScript](http://coffeescript.org), [Less](http://lesscss.org) and [Dust](http://linkedin.github.io/dustjs/) using the H2 in-memory database.

## Features
1. Setup Play routes and methods to interact with Backbone crud.
2. Shows use of Play JSON Objects / Reads / Writes / Formatters.
3. Renders based on successful Backbone synced calls to the database.
4. Grouping of Models, Collections, Views etc. in the app object.
5. RequireJS Play structure and setup with shim.
6. Uses Dust js renderer and template loading.
7. Has front and backend validation returning JsObjects.

## Additional
1. In-line editing from Play Framework Zentasks sample application.

## Changelog

July 24, 2013

- Removed shim from shim file, now using it in main as require configuration.
- Removed domReady just using jQuery ready now.

## License
MIT: <http://jmparsons.mit-license.org> - [@jmparsons](http://twitter.com/jmparsons)
