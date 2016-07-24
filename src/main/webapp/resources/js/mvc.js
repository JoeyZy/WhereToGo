/*
 * TODO Break the file down to a number of files, one per entity;
 * TODO During the build, combine all the files into one
 */
var Event = {
    SET   : "set",
    ADD   : "add",
    CHANGE: "change",
    CLEAR : "clear",
    SUBMIT: "submit"
};

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
     * @param {string} event    Event that occurred
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
     * @param {string} event    Event that occurred
     * @param         [data]    Data to be passed to the event (undefined is allowed)
     */
    notify: function (event, data) {
        this.observers.invoke(event, data);
    }
});

var Model = AbstractSubject.extend({
    // constructor
    /**
     * @param {any} [v] value for the model; {} if absent
     */
    init: function Model(v) {
        this._super();
        this.value = defined(v)? deepCopy(v) : {};
    },

    /**
     * @param [v] value to set
     * @return this.value
     */
    val: function (v) {
        if (defined(v)) {
            this.value = v;
            this.notify(Event.SET, v);
        }
        return this.value;
    }
});

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
                that.notify(Event.ADD, e);
            });
        } else {
            this.val().push(v);
            this.notify(Event.ADD, v);
        }
    }
});
