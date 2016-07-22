var AbstractController = Class.extend({
    // constructor
    init: function(model, view) {
        this.model = model;
        this.view = view;
        this.bind();
    },
    bind: function () {
        // nothing to bind here
    }
});