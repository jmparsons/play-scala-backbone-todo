require ["domReady", "app", "router"], (domReady, app, Router) ->
  
  domReady ->
    app.router = new Router()
    app.router.on "all", (eventName) ->
      app.loaded = true
    Backbone.history.start
      pushState: true
      root: "/"
    # here
    