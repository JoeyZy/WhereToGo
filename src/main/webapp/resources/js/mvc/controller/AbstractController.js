var AbstractController = Class.extend({

    /**
     * @class AbstractController
     * @classdesc Abstract controller implementation:
     *
     * - _Binds_ {@link Model} & {@link Component View} together
     * - _Synchronizes_ Model with the View (by request only)
     * - Contains _business logic_
     *
     * @constructs AbstractController
     * @param {Model}     model  Model (not ModelFacade)
     * @param {Component} view   View (Component) (not ViewFacade)
     */
    init: function AbstractController(model, view) {
        this.model = model;
        this.view = view;
        this.bind();
    },

    /**
     * @summary Binds Model and View (your View's get observed here)
     * @memberof AbstractController#
     */
    bind: function () {
        // nothing to bind here
    },

    /**
     * @summary Updates the Model to have same value as has been set in View
     * (when invoked, synchronizes Model with the View)
     * @memberof AbstractController#
     */
    syncModel: function () {
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