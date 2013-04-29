define ["app", "jquery", "underscore", "backbone", "dust", "../../templates/todoitem"], (app, $, _, Backbone) ->

  class app.Models.TodoModel extends Backbone.Model
    urlRoot: "/todos"
    defaults:
      "content":""

  class app.Collections.TodoCollection extends Backbone.Collection
    model: app.Models.TodoModel
    url: "/todos"


  class app.Views.TodoItemView extends Backbone.View
    tagName: "li"
    initialize: ->
      _.bindAll @
      @model.bind 'change', @render
      @model.bind 'remove', @unrender
    render: ->
      that = @
      dust.render "templates/todoitem", @model.toJSON(), (err, out) ->
        that.$el.html("" + ((if err then err else out))).data "item-id", that.model.toJSON().id
        $(".edit", that.$el).editInPlace
          context: that
          onChange: that.editTodo
      @
    unrender: =>
      @$el.remove()
    remove: -> @model.destroy()
    editTodo: (content) ->
      @model.set "content", content
      @model.save @model.toJSON(),
        success: (model, response, options) ->
          console.log model, response, options
          console.log "editTodo", "success"
        error: (model, xhr, options) ->
          console.log model, xhr, options
          console.log "editTodo", "error"
      console.log @model
      # @$el.data "item-id"
      console.log content
    # editTodo: (event) ->
      # console.log @$el.find(".edit")
      # @$el.editInPlace("edit")
      # @$el.data "item-id"
    # events:
      # 'dblclick .edit' : 'editTodo'

  class app.Views.TodoListView extends Backbone.View
    el: ".todos"
    initialize: ->
      _.bindAll @
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
    render: ->
      $(".todolist", @el).html "<ul></ul>"
      for todo in @collection.models
        do (todo) =>
          itemView = new app.Views.TodoItemView model: todo
          $(".todolist ul", @el).append itemView.render().el
    addTodo: (event) ->
      event.preventDefault()
      return if $.trim($("#content", event.target).val()) is ""
      that = @
      todo = new app.Models.TodoModel
        content: $("#content", event.target).val()
      todo.save todo.toJSON(),
        success: (model, response, options) ->
          console.log model, response, options
          that.collection.add model
          $("#content", ".todoform").val("")
          console.log "addTodo", "success"
          itemView = new app.Views.TodoItemView model: model
          $(".todolist ul", that.$el).append itemView.render().el
        error: (model, xhr, options) ->
          console.log model, xhr, options
          console.log "addTodo", "error"
    deleteTodo: (event) ->
      event.preventDefault()
      todo = @collection.get $(event.target).closest("li").data("item-id")
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

