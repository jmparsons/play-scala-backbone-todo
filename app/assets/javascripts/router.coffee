define ["app", "modules/todo"], (app) ->

  class Router extends Backbone.Router
    routes:
      "": "index"
    index: ->
      new app.Views.TodoListView
