define [
  "app",
  "jquery", 
  "underscore", 
  "backbone"
  
], (app, $, _, Backbone) ->

  class Router extends Backbone.Router
    routes:
      "":           "index"
    index: ->
      console.log "index"
  Router