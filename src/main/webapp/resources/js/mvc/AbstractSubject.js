var AbstractSubject = Class.extend({
    // constructor
    init: function AbstractSubject() {
        this.observers = new ObserverMap();
    },

    observe: function (event, callback) {
        this.observers.put(event, callback);
    },

    unobserve: function (event) {
        this.observers.remove(event);
    },

    /**
     * @param {string} event    ModelEvent that occurred
     * @param         [data]    Data to be passed to the event (undefined is allowed)
     */
    notify: function (event, data) {
        this.observers.invoke(event, data);
    }
});