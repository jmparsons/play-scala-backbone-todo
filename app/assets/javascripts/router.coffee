define [
  "app",
  "jquery", 
  "underscore", 
  "backbone",
  "modules/todo"
  
], (app, $, _, Backbone) ->

  class Router extends Backbone.Router
    routes:
      "":           "index"
    index: ->
      new app.Views.TodoView
      
  Router