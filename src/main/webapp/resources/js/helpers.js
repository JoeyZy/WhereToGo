/**
 * Abstract holder that hides its value.
 * Use case: Create an empty Ref, capture it in a closure; now the Ref's value may be set later!
 * @param {any} [o] the value
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
 * @param {any} o any object
 * @return {boolean} true if o is defined (not "undefined")
 */
function defined(o) {
    return typeof(o) != "undefined";
}

/**
 * Makes a deep copy of an object
 * @param {any} o any object or primitive
 * @return {any} either a deep copy of a passed object, or the exact value of a passed primitive
 */
function deepCopy(o) {
    return (typeof o == "object") ? JSON.parse(JSON.stringify(o)) : o; // @compat es5
}
