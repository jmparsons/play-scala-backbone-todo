define ["jquery", "underscore", "backbone"], ($, _, Backbone, Router) ->
  
  $.fn.serializeObject = ->
    o = {}
    a = @serializeArray()
    $.each a, ->
      if o[@name] isnt `undefined`
        o[@name] = [o[@name]]  unless o[@name].push
        o[@name].push @value or ""
      else
        o[@name] = @value or ""

    o
  
  app = {
    loaded: false
    Models: {}
    Collections: {}
    Controllers: {}
    Views: {}
  }
