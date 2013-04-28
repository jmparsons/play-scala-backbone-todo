define ["app", "jquery", "underscore", "backbone"], (app, $, _, Backbone) ->

  class app.Models.TodoModel extends Backbone.Model
    urlRoot: "/todos"
    defaults:
      "content":""

  class app.Collections.TodoCollection extends Backbone.Collection
    model: app.Models.TodoModel
    url: "/todos"


  class app.Views.TodoItemView extends Backbone.View
    tagName: "li"
    template: _.template('<%=id%>. <%=content%> <span class="tools"><a href="#" data-delete-id="<%=id%>" class="delete">delete</a></span>')
    initialize: ->
      _.bindAll @
      @model.bind 'change', @render
      @model.bind 'remove', @unrender
    render: ->
      @$el.html @template(@model.toJSON())
    unrender: =>
      $(@el).remove()
    remove: -> @model.destroy()

  class app.Views.TodoListView extends Backbone.View
    el: ".todos"
    initialize: ->
      _.bindAll @
      @collection = new app.Collections.TodoCollection
      @collection.fetch success: @render
    render: ->
      @$el.find(".todolist").html "<ul></ul>"
      for todo in @collection.models
        do (todo) =>
          itemView = new app.Views.TodoItemView model: todo
          @$el.find(".todolist ul").append itemView.render
    addTodo: (event) ->
      event.preventDefault()
      that = @
      todo = new app.Models.TodoModel
        content: $(event.currentTarget).find("#content").val()
      todo.save todo.toJSON(),
        success: (model, response, options) ->
          console.log model, response, options
          that.collection.add model
          # $(".todoform #content").val("")
          console.log "addTodo", "success"
          itemView = new app.Views.TodoItemView model: model
          that.$el.find(".todolist ul").append itemView.render
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

