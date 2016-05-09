require.config
  paths:
    jquery: "../lib/jquery/dist/jquery"
    backbone: "../lib/backbone/backbone"
    underscore: "../lib/underscore/underscore"
    dust: "../lib/dustjs-linkedin/dist/dust-full"
    dustHelpers: "../lib/dustjs-helpers/dist/dust-helpers"
    todoitem: "../templates/todoitem"
  shim:
    backbone:
      deps: ["underscore", "jquery"]
      exports: "Backbone"
    underscore:
      exports: "_"
    jquery:
      exports: "$"
    dustHelpers:
      deps: ["dust"]
    todoitem:
      deps: ["dust", "dustHelpers"]

require ["app", "router"], (app, Router) ->

  app.router = new Router()
  Backbone.history.start
    pushState: true
    root: "/"
