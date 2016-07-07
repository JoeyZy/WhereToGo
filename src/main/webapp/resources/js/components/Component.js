function Component(selector) {
    var root = $(selector);

    if (root.length == 0) { // TODO Move this check to a (prototype's) method
        throw new Error("No element found by selector '{}'"
            .replace(new RegExp("{}", "g"), selector)); // TODO Write utility String.format
    }

    this.get = function () {
        return element;
    };
}

Component.prototype = {
    render: function () {
        throw new Error("Cannot invoke an abstract method");
    },
    registerListeners: function () {
        throw new Error("Cannot invoke an abstract method");
    }
};