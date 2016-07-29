var Observable = Class.extend({
    /**
     * @class Observable
     * @classdesc Basic implementation of Observable:
     *    - Call observe() to have this entity observed (for a specific event)
     *    - Call notify () to notify all the observers of this observable (for a specific event)
     * @constructs Observable
     * @property {ObserverMap} observers Associated ObserverMap
     */
    init: function Observable() {
        /** @private */ this.observers = new ObserverMap();
    },

    /**
     * @summary Start observing this (for a specified event)
     * @memberof Observable#
     *
     * @param {string} event ModelEvent to be observed
     * @param {function} callback Listener
     */
    observe: function (event, callback) {
        this.observers.put(event, callback);
    },

    /**
     * @summary Stop observing this (for a specified event)
     * @memberof Observable#
     *
     * @param {string} event ModelEvent to be observed
     */
    unobserve: function (event) {
        this.observers.remove(event);
    },

    /**
     * @summary Notifies all observers of this observable that a specified event has occurred
     * @memberof Observable#
     *
     * @param {string} event    ModelEvent that occurred
     * @param {any}   [data]    Data to be passed to the event (undefined is allowed)
     */
    notify: function (event, data) {
        this.observers.invoke(event, data);
    }
});