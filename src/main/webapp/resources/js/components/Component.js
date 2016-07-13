/**
 * @param {string} id
 * @param {string} [selector] CSS (jQuery) selector of the root element of component
 */
function Component(id, selector) {
    this.id = id;
    this.selector = selector;
    this.wrap(this.selector);
}

Component.prototype = {
    /**
     * @param {string} [id]
     */
    get: function (id) {
        if (defined(id)) {
            return $(this.id + "." + id, this.element);
        }
        return this.element;
    },
    wrap: function (selector) {
        if (defined(selector)) {
            this.element = $(this.selector);
        }
        this.validateElement();
    },
    render: function () { // TODO Is it needed? A good architectural question
        throw new Error("Cannot invoke an abstract method");
    },
    registerListeners: function () {
        throw new Error("Cannot invoke an abstract method");
    },
    validateElement: function () {
        if (this.element.length == 0) { // TODO Move this check to a (prototype's) method
            throw new Error("No element found by selector '{}'"
                .replace(new RegExp("{}", "g"), this.selector)); // TODO Write utility String.format
        }
    }
};