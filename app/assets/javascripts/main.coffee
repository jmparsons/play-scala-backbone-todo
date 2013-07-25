require.config
  shim:
    backbone:
      deps: ["underscore", "jquery"]
      exports: "Backbone"
    app:
      deps: ["backbone", "dust"]
      exports: "app"

require ["app", "router"], (app, Router) ->
  $ ->
    app.router = new Router()
    Backbone.history.start
      pushState: true
      root: "/"
