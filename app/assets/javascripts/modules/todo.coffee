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
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
    render: =>
      @$el.find("#todoList").empty()
      for todo in @collection.models
        do (todo) =>
          @$el.find("#todoList").append @template(todo.toJSON())
    addTodo: (event) ->
      event.preventDefault()
      console.log event
      newTodo = $(event.currentTarget).serializeObject()
      bob = $(event.currentTarget).serialize()
      console.log newTodo
      console.log bob
      todo = new app.Models.TodoModel
      dude = new app.Models.TodoModel(
        title: $(event.currentTarget).find("#title").val()
        content: $(event.currentTarget).find("#content").val()
      )
      console.log dude.toJSON()
      # todo.save newTodo,
      todo.save dude.toJSON(),
        success: (model, response, options) ->
          console.log model
          console.log response
          console.log options
        error: (model, xhr, options) ->
          console.log model
          console.log xhr
          console.log options
      
    events: 'submit #todoForm' : 'addTodo'
