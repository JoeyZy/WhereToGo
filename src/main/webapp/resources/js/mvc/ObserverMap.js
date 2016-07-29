var ObserverMap = Class.extend({
    /**
     * @class ObserverMap
     * @classdesc ObserverMap holds observers for the supported events,
     * and provides means to execute those callbacks with data provided.
     *
     * @constructs ObserverMap
     */
    init: function ObserverMap() {
        /** @access private*/ this.map = {};
    },

    /**
     * @summary Adds an observer for an event
     *
     * @todo TODO Add validations
     * @memberof ObserverMap#
     */
    put: function (event, observer) {
        this.map[event] = observer;
    },

    /**
     * @summary Invokes an observer with the data provided
     * @memberof ObserverMap#
     *
     * @param {string} event    ModelEvent that occurred
     * @param         [data]    Data to be passed to the event (undefined is allowed)
     */
    invoke: function (event, data) {
        this.map[event](data);
    },

    /**
     * @summary Removes an observer of an event
     * @memberof ObserverMap#
     *
     * @param {string} event    ModelEvent that occurred
     */
    remove: function (event) {
        delete this.map[event];
    },

    /**
     * @summary Removes all observers (of all events)
     * @memberof ObserverMap#
     */
    clear: function () {
        this.map = {}; // the old value will be GC'ed eventually
    }
});