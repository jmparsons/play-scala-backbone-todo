define ["app", "jquery", "underscore", "backbone"], (app, $, _, Backbone) ->

  class app.Models.TodoModel extends Backbone.Model
    urlRoot: "/todos"
    defaults:
      "content":""

  class app.Collections.TodoCollection extends Backbone.Collection
    model: app.Models.TodoModel
    url: "/todos"

  class app.Views.TodoListView extends Backbone.View
    tagName: "ul"
    initialize: ->
      console.log "initialize"
    render: ->
      console.log "render"

  class app.Views.TodoItemView extends Backbone.View
    tagName: "li"
    template: _.template('<%=content%> <span class="tools"><a href="#" data-delete-id="<%=id%>" class="delete">delete</a></span>')
    initialize: ->
      console.log "initialize"
    render: ->
      console.log "render"

  class app.Views.TodoView extends Backbone.View
    el: ".todos"
    template: _.template('<p class="todoitem"><%=content%> <span class="tools"><a href="#" data-delete-id="<%=id%>" class="delete">delete</a></span></p>')
    initialize: ->
      _.bindAll @, "render"
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
      @collection.bind "add", @render
      @collection.bind "remove", @render
    render: ->
      @$el.find(".todolist").empty()
      for todo in @collection.models
        do (todo) =>
          @$el.find(".todolist").append @template(todo.toJSON())
    addTodo: (event) ->
      event.preventDefault()
      that = @
      todo = new app.Models.TodoModel
        content: $(event.currentTarget).find("#content").val()
      todo.save todo.toJSON(),
        success: (model, response, options) ->
          console.log model, response, options
          that.collection.add model
          $(".todoform #content").val("")
          console.log "addTodo", "success"
        error: (model, xhr, options) ->
          console.log model, xhr, options
          console.log "addTodo", "error"
    deleteTodo: (event) ->
      event.preventDefault()
      todo = @collection.get $(event.target).data("delete-id")
      that = @
      console.log todo
      console.log @collection.length
      todo.destroy
        success: (model, response, options) ->
          console.log "destroy", "success"
          console.log model, response, options
        error: (model, xhr, options) ->
          console.log "destroy", "error"
          console.log model, xhr, options
      console.log @collection.length
    events:
      'submit .todoform' : 'addTodo'
      'click a.delete' : 'deleteTodo'

