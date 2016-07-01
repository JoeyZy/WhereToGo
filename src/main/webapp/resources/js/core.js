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
