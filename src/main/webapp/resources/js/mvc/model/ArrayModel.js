var ArrayModel = Model.extend({
    // constructor
    /**
     * @param {array} [a] value for the model; [] if absent
     */
    init: function ArrayModel(a) {
        this._super(a);
    },
    add: function (v) {
        if (Array.isArray(v)) { // @compat es5
            var that = this;
            v.forEach(function (e) { // @compat es5
                that.val().push(e);
                that.notify(ModelEvent.ADD, e);
            });
        } else {
            this.val().push(v);
            this.notify(ModelEvent.ADD, v);
        }
    }
});
