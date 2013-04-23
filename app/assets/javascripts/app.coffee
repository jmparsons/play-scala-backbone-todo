define ["jquery", "underscore", "backbone"], ($, _, Backbone, Router) ->
  
  app = {
    loaded: false
    Models: {}
    Collections: {}
    Controllers: {}
    Views: {}
  }

  _.extend app,
    module: (additionalProps) ->
      _.extend({ Views: {} }, additionalProps);
  , Backbone.Events
