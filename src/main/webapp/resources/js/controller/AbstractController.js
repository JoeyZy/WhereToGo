var AbstractController = Class.extend({
    // constructor
    init: function AbstractController(model, view) {
        this.model = model;
        this.view = view;
        this.bind();
    },
    bind: function () {
        // nothing to bind here
    },
    sync: function () {
        var that = this;
        var names = this.view.getModelNames();
        names.forEach(function (id) { // @compat es5
            if (!(that.model[id] instanceof Model)) {
                throw new Error("Model and view are out of sync!");
            }
            that.model[id].val(that.view[id]());
        });
    }
});