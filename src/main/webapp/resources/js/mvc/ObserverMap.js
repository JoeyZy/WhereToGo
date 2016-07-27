/**
 * ObserverMap holds observers for the supported events,
 * and provides means to execute those callbacks with data provided.
 */
var ObserverMap = Class.extend({

    // constructor
    init: function ObserverMap() {
        this.map = {};
    },

    /**
     * Adds an observer for an event
     * TODO Add validations
     */
    put: function (event, observer) {
        this.map[event] = observer;
    },

    /**
     * Invokes an observer with the data provided
     *
     * @param {string} event    ModelEvent that occurred
     * @param         [data]    Data to be passed to the event (undefined is allowed)
     */
    invoke: function (event, data) {
        this.map[event](data);
    },

    /**
     * Removes an observer of an event
     */
    remove: function (event) {
        delete this.map[event];
    },

    /**
     * Removes all observers (of all events)
     */
    clear: function () {
        this.map = {}; // the old value will be GC'ed eventually
    }
});