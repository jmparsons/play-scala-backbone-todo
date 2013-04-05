define ["jquery", "underscore", "backbone"], ($, _, Backbone) ->
  
  app = {
    loaded: false
  }
  _.extend app,
    module: (additionalProps) ->
      _.extend({ Views: {} }, additionalProps);
  , Backbone.Events
