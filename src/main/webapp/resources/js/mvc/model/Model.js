var Model = Observable.extend({
    /**
     * @class Model
     * @extends Observable
     *
     * @classdesc The domain-specific representation of the information on which the application operates.
     * The model is another name for the domain layer.
     * This is the value you send with AJAX calls; based on this value, you update your view.
     *
     * The Model is an implementation of Observable: Adds val() method for updating the value of the model
     *
     * @constructs Model
     * @param {any} [v] value for the model
     */
    init: function Model(v) {
        this._super();
        var that = this;
        setTimeout(function () { // wait until the view is bound
            that.val(v);
        }, 0);
    },

    /**
     * @summary Sets the value of the model and fires SET event
     * @param [v] value to set
     * @return this.value
     * @fires ModelEvent.SET
     * @memberof Model#
     */
    val: function (v) {
        if (defined(v)) {
            this.value = v;
            this.notify(ModelEvent.SET, v);
        }
        return this.value;
    }
});
