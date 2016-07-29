/**
 * The domain-specific representation of the information on which the application operates.
 * The model is another name for the domain layer.
 * This is the value you send with AJAX calls; based on this value, you update your view.
 *
 * The Model is an implementation of Observable: Adds val() method for updating the value of the model
 */
var Model = Observable.extend({
    // constructor
    /**
     * Constructor
     * @param {any} [v] value for the model; {} if absent
     */
    init: function Model(v) {
        this._super();
        var that = this;
        setTimeout(function () { // wait until the view is bound
            that.val(v);
        }, 0);
    },

    /**
     * Sets the value of the model and fires SET event
     * @param [v] value to set
     * @return this.value
     */
    val: function (v) {
        if (defined(v)) {
            this.value = v;
            this.notify(ModelEvent.SET, v);
        }
        return this.value;
    }
});
