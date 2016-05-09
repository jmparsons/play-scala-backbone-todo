define ["app", "jquery", "underscore", "backbone", "todoitem"], (app, $, _, Backbone) ->

  class app.Models.TodoModel extends Backbone.Model
    urlRoot: "/todos"

  class app.Collections.TodoCollection extends Backbone.Collection
    model: app.Models.TodoModel
    url: "/todos"
    comparator: (todo) -> todo.get("id")

  class app.Views.TodoItemView extends Backbone.View
    tagName: "li"
    initialize: ->
      _.bindAll.apply _, [@].concat(_.functions(@))
      @model.bind "sync", @render
      @model.bind "remove", @unrender
    render: ->
      dust.render "templates/todoitem", @model.toJSON(), (err, out) =>
        @$el.html(if err then err else out).data "item-id", @model.toJSON().id
        $(".edit", @$el).editInPlace context: @, onChange: @editTodo
      @$el
    remove: -> @model.destroy()
    unrender: -> @$el.remove()
    editTodo: (content) -> if content is "" then @model.toJSON() else @model.set("content", content).save @model.toJSON()

  class app.Views.TodoListView extends Backbone.View
    el: ".todos"
    events:
      "submit .todoform" : "addTodo"
      "click a.delete" : "deleteTodo"
    initialize: ->
      _.bindAll.apply _, [@].concat(_.functions(@))
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
    render: ->
      for model in @collection.models
        do (model) =>
          $(".todolist ul", @el).append new app.Views.TodoItemView(model: model).render()
    addTodo: (event) ->
      event.preventDefault()
      return if $.trim($("#content", event.target).val()) is ""
      todo = new app.Models.TodoModel content: $("#content", event.target).val()
      todo.save todo.toJSON(),
        success: (model, response, options) =>
          @collection.add model
          $("#content", ".todoform").val("")
          $(".todolist ul", @$el).append new app.Views.TodoItemView(model: model).render()
    deleteTodo: (event) ->
      event.preventDefault()
      todo = @collection.get $(event.target).closest("li").data("item-id")
      todo.destroy()
