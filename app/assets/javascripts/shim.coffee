require.config
  shim:
    backbone:
      deps: ["underscore", "jquery", "dust"]
      exports: "Backbone"
  optimize: "uglify2"
