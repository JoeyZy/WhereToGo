/**
 * @param {any} [o]
 */
function Ref(o) {
    var value = o;
    this.get = function () {
        return value;
    };
    this.set = function (o) {
        value = o;
    };
}

/**
 * TODO
 */
function defined(o) {
    return typeof(o) != "undefined";
}

/**
 * TODO
 */
function deepCopy(o) {
    return (typeof o == "object") ? JSON.parse(JSON.stringify(o)) : o;
}
