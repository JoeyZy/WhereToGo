var ObserverMap = Class.extend({
    // constructor
    init: function ObserverMap() {
        this.map = {};
    },
    /**
     * TODO Write docs
     * TODO Add support of multiple observers per key?
     * TODO Add validations
     */
    put: function (event, observer) {
        this.map[event] = observer;
    },
    /**
     * @param {string} event    ModelEvent that occurred
     * @param         [data]    Data to be passed to the event (undefined is allowed)
     */
    invoke: function (event, data) {
        this.map[event](data);
    },
    remove: function (event) {
        delete this.map[event];
    },
    clear: function () {
        this.map = {}; // the old value will be GC'ed eventually
    }
});