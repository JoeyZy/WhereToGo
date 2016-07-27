var Model = Observable.extend({
    // constructor
    /**
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
