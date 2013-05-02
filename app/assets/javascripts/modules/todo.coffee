define ["app", "jquery", "underscore", "backbone", "../../templates/todoitem"], (app, $, _, Backbone) ->

  class app.Models.TodoModel extends Backbone.Model
    urlRoot: "/todos"

  class app.Collections.TodoCollection extends Backbone.Collection
    model: app.Models.TodoModel
    url: "/todos"
    comparator: (todo) ->
      todo.get("id")

  class app.Views.TodoItemView extends Backbone.View
    tagName: "li"
    initialize: ->
      _.bindAll @
      @model.bind "sync", @render
      @model.bind "remove", @unrender
    render: ->
      that = @
      dust.render "templates/todoitem", @model.toJSON(), (err, out) ->
        that.$el.html(if err then err else out).data "item-id", that.model.toJSON().id
        $(".edit", that.$el).editInPlace context: that, onChange: that.editTodo
      @
    remove: -> @model.destroy()
    unrender: -> @$el.remove()
    editTodo: (content) -> @model.set("content", content).save @model.toJSON()

  class app.Views.TodoListView extends Backbone.View
    el: ".todos"
    events:
      "submit .todoform" : "addTodo"
      "click a.delete" : "deleteTodo"
    initialize: ->
      _.bindAll @
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
    render: ->
      $(".todolist", @el).html "<ul></ul>"
      for model in @collection.models
        do (model) =>
          $(".todolist ul", @el).append new app.Views.TodoItemView(model: model).render().el
    addTodo: (event) ->
      event.preventDefault()
      return if $.trim($("#content", event.target).val()) is ""
      that = @
      todo = new app.Models.TodoModel content: $("#content", event.target).val()
      todo.save todo.toJSON(),
        success: (model, response, options) ->
          that.collection.add model
          $("#content", ".todoform").val("")
          $(".todolist ul", that.$el).append new app.Views.TodoItemView(model: model).render().el
    deleteTodo: (event) ->
      event.preventDefault()
      todo = @collection.get $(event.target).closest("li").data("item-id")
      todo.destroy()
