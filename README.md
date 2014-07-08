# Play 2.3.x Todo list example app
A Todo list application built on [Play Framework](http://www.playframework.com) (Scala), [Slick](http://slick.typesafe.com/), [Webjars](https://github.com/webjars), [Backbone](http://backbonejs.org), [RequireJS](http://requirejs.org), [CoffeeScript](http://coffeescript.org) and [Dustjs](http://linkedin.github.io/dustjs/) using the H2 in-memory database.

## Features
1. Setup Play routes and methods to interact with Backbone CRUD.
2. Shows use of Play JSON Objects / Reads / Writes / Formatters.
3. Renders based on successful Backbone synced calls to the database.
4. Grouping of Models, Collections, Views etc. in the app object.
5. RequireJS structure and setup with config.
6. Uses Dustjs renderer and template loading.
7. Has front and backend validation returning JsObjects.
8. In-line editing from Play Framework Zentasks sample application.

## Changelog

July 8, 2014 (4.0)

- Updated to Play 2.3.1
- Added to Activator templates
- Updated [play-slick](https://github.com/playframework/play-slick) to 0.8.x - using scala version 2.11.x
- Updated from [play-dustjs](https://github.com/jmparsons/play-dustjs) to [sbt-dustjs-linkedin](https://github.com/jmparsons/sbt-dustjs-linkedin)
- Now using webjars
- Changed from MIT license to Apache 2

November 18, 2013 (3.0)

- Updated to Play 2.2.1
- Now using latest Dust templating [play-dustjs](https://github.com/jmparsons/play-dustjs)
- Updated [play-slick](https://github.com/playframework/play-slick) to 0.5.0.8 - latest for Play 2.2.x
- Added build.sbt and removed Build.scala file
- Updated TodoController to use DBAction with new parser built-in
- Updated Todo Model to use new DAO separation component
- Updated tests to use PlaySpecification
- Updated todo js module to use new bindAll from Underscore with concat functions
- Updated javascript file versions

September 3, 2013

- Updated to Play 2.1.3
- Updated [play-slick](https://github.com/playframework/play-slick) to 0.4.0 - latest for Play 2.1
- Added implicit sessions to models removing the withSessions
- Added an example of using DBAction then grabbing the request as json with validation
- Added an example of using a json body parser to retrieve the request then using DB withSession
- Updated tests to use session

July 29, 2013

- Added DBType text for content column.

July 25, 2013 (2.0)

- Now using [Slick](http://slick.typesafe.com/) via [play-slick](https://github.com/playframework/play-slick) instead of anorm.
- Using Slick via DB with session instead of DBAction for now.
- Removed evolution, now using Slick DDL generation.
- Removed reads and writes, now using format since Option Long is used instead of PK.
- Added selenium / fluentlenium tests.
- Move Require JS shim configuartion options to main.
- Removed domReady using jQuery ready now.

