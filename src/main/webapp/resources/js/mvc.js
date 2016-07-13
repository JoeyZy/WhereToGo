/*
 * TODO Break the file down to a number of files, one per entity;
 * TODO During the build, combine all the files into one
 */
var Event = {
    SET   : "set",
    ADD   : "add",
    CHANGE: "change",
    CLEAR : "clear"
};

function ObserverMap() {
    this.map = {}; // it can hardly be misused so why hide it
}
ObserverMap.prototype = {
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
};

function AbstractSubject() {
    var observers = new ObserverMap();

    this.observe = function (event, callback) {
        observers.put(event, callback);
    };

    this.unobserve = function (event) {
        observers.remove(event);
    };

    /**
     * @param {string} event    Event that occurred
     * @param         [data]    Data to be passed to the event (undefined is allowed)
     */
    this.notify = function (event, data) {
        observers.invoke(event, data);
    };
}

/**
 * @param [v] value for the model; {} if absent
 */
function Model(v) {
    var value = deepCopy(v);

    /**
     * @param [v] value to set
     * @return this.value
     */
    this.val = function (v) {
        if (defined(v)) {
            value = v;
        }
        return value;
    };
}
Model.prototype = Object.create(new AbstractSubject()); // @compat es5
Model.prototype.constructor = Model;

/**
 * @param {array} [a] value for the model; [] if absent
 */
function ArrayModel(a) {
    // TODO Validate args
    Model.call(this, [a || []]);
}
ArrayModel.prototype = Object.create(Model.prototype);
ArrayModel.prototype.add = function (v) {
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
};

var model = {
    user   : new Model(),
    events : new ArrayModel(),
    filters: new ArrayModel()
};
