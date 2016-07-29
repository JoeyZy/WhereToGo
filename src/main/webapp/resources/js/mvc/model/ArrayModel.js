var ArrayModel = Model.extend({
    /**
     * @class ArrayModel
     * @classdesc Implementation of the Model for multiple elements (array)
     * @param {array} [a] value for the model; [] if absent
     * @constructs ArrayModel
     */
    init: function ArrayModel(a) {
        this._super(a);
    },

    /**
     * @summary Adds an item to the model and fires ADD event
     * @param {Array|any} v  item to be added to the model (if array, all elements are added separately)
     *
     * @fires ModelEvent.ADD
     * @memberof ArrayModel#
     */
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
