/**
 * Basic implementation of Observable:
 *    - Call observe() to have this entity observed (for a specific event)
 *    - Call notify () to notify all the observers of this observable (for a specific event)
 */
var Observable = Class.extend({

    // constructor
    init: function Observable() {
        this.observers = new ObserverMap();
    },

    /**
     * Start observing this (for a specified event)
     */
    observe: function (event, callback) {
        this.observers.put(event, callback);
    },

    /**
     * Stop observing this (for a specified event)
     */
    unobserve: function (event) {
        this.observers.remove(event);
    },

    /**
     * Notifies all observers of this observable that a specified event has occurred
     *
     * @param {string} event    ModelEvent that occurred
     * @param {any}   [data]    Data to be passed to the event (undefined is allowed)
     */
    notify: function (event, data) {
        this.observers.invoke(event, data);
    }
});