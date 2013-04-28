define ["jquery", "underscore", "backbone"], ($, _, Backbone, Router) ->

  app = {
    loaded: false
    Models: {}
    Collections: {}
    Controllers: {}
    Views: {}
  }
