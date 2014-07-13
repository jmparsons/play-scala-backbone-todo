require.config
  paths:
    jquery: "../lib/jquery/jquery"
    backbone: "../lib/backbonejs/backbone"
    underscore: "../lib/underscorejs/underscore"
    dust: "../lib/dustjs-linkedin/dust-core"
    todoitem: "../templates/todoitem"
  shim:
    backbone:
      deps: ["underscore", "jquery"]
      exports: "Backbone"
    underscore:
      exports: "_"
    jquery:
      exports: "$"
    todoitem:
      deps: ["dust"]

require ["app", "router", "jquery", "backbone", "underscore", "dust"], (app, Router, $, Backbone, _) ->

  app.router = new Router()
  Backbone.history.start
    pushState: true
    root: "/"
