define ["app", "jquery", "underscore", "backbone"], (app, $, _, Backbone) ->

  class app.Models.TodoModel extends Backbone.Model
    url: "/todos"

  class app.Collections.TodoCollection extends Backbone.Collection
    model: app.Models.TodoModel
    url: "/todos"

  class app.Views.TodoView extends Backbone.View
    el: "#todos"
    template: _.template("<p><%=id%>. <%=title%> - <%=content%></p>")
    initialize: ->
      _.bindAll @
      that = @
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
    render: =>
      @$el.empty()
      for todo in @collection.models
        do (todo) =>
          @$el.append @template(todo.toJSON())
