require.config
  paths:
    jquery: "../lib/jquery/jquery"
    backbone: "../lib/backbonejs/backbone"
    underscore: "../lib/underscorejs/underscore"
    dust: "../lib/dustjs-linkedin/dust-core"
  shim:
    backbone:
      deps: ["underscore", "jquery"]
      exports: "Backbone"
    underscore:
      exports: "_"
    jquery:
      exports: "$"

require ["app", "router", "jquery", "backbone", "underscore", "dust"], (app, Router, $, Backbone, _) ->

  app.router = new Router()
  Backbone.history.start
    pushState: true
    root: "/"
