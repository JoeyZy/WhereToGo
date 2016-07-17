var Component = Class.extend({
    // constructor
    /**
     * @param {string} id
     */
    init: function(id) { // TODO Pass the root element also
        this.id = id;
        this.selector = selector;
        this.elements = {}; // child components (elements)
        this.wrap(this.selector); // TODO Consider (possible) lazy wrapping
    },
    wrap: function (selector) {
        if (defined(selector)) {
            this.element = $(this.selector);
        }
        this.validateElement();
        var that = this;
        var elements = $("[" + Component.MODEL_ATTR + "]", this.element);
        elements.forEach(function (e) { // @compat es5
            var id = e.attr(Component.MODEL_ATTR);
            that.elements[id] = e;
        });
    },
    render: function () { // TODO Is it needed? Or only wrapping is supported?
        throw new Error("Cannot invoke an abstract method");
    },
    registerListeners: function () {
        throw new Error("Cannot invoke an abstract method");
    },
    /**
     * @param {string} [id]
     * @return {object} jQuery element
     */
    getElement: function (id) {
        if (defined(id)) {
            return $(this.getSelector(id), this.element);
        }
        return this.element;
    },
    /**
     * @param {string} [id] model-id of a child component/element; this.id if undefined
     */
    getSelector: function (id) {
        var childId = defined(id) ? (this.id + "." + id) : this.id;
        return "[" + Component.MODEL_ATTR + "=" + childId + "]"; // TODO Use selector's context
    },
    validateElement: function () {
        if (this.element.length == 0) { // TODO Move this check to a (prototype's) method
            throw new Error("No element found by selector '{}'"
                .replace(new RegExp("{}", "g"), this.selector)); // TODO Write utility String.format
        }
    },
    // WARNING: This will break in case this element does not have val()
    // TODO Rename to elementValue: current name is confusing
    /**
     * @param {string} id  model-id of the element
     * @param {string} [v] value to set if defined
     */
    value: function (id, v) {
        if (defined(v)) {
            // TODO Apply some validation
            return this.elements[id].val(v);
        }
        return this.elements[id].val();
    }
});

// statics
Component.MODEL_ATTR = "data-model";
