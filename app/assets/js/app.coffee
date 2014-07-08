define ["jquery"], ($) ->

  # From the zentasks application
  $.fn.editInPlace = (method, options...) ->
    this.each ->
      methods =
        init: (options) ->
          valid = (e) =>
            newValue = @input.val()
            options.onChange.call(options.context, newValue)
          cancel = (e) =>
            @el.show()
            @input.hide()
          @el = $(this).dblclick(methods.edit)
          @input = $("<input type='text' />")
            .insertBefore(@el)
            .keyup (e) ->
              switch(e.keyCode)
                # Enter key
                when 13 then $(this).blur()
                # Escape key
                when 27 then cancel(e)
            .blur(valid)
            .hide()
        edit: ->
          @input
            .val(@el.text())
            .show()
            .focus()
            .select()
          @el.hide()
        close: (newName) ->
          @el.text(newName).show()
          @input.hide()
      # jQuery approach: http://docs.jquery.com/Plugins/Authoring
      if ( methods[method] )
        return methods[ method ].apply(this, options)
      else if (typeof method == 'object')
        return methods.init.call(this, method)
      else
        $.error("Method " + method + " does not exist.")

  app = {
    Models: {}
    Collections: {}
    Controllers: {}
    Views: {}
  }
