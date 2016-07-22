var Component = AbstractSubject.extend({
    // constructor
    /**
     * @param {string} id
     * @param {ModelFacade} model
     */
    init: function(id, model) { // TODO Pass the root element also
        this._super();
        this.id = id;
        this.selector = selector;
        this.element = null;
        this.elements = {}; // child components (map of model-ids to elements) // TODO Consider renaming it to "children"
        this.model = model;
        this.modelIds = [];
        this.bind();
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
            that.modelIds.push(id);
        });
        this.registerListeners();
    },
    render: function () { // TODO Is it needed? Or only wrapping is supported?
        throw new Error("Cannot invoke an abstract method");
    },
    registerListeners: function () {
        // no listeners for abstract component
    },
    bind: function () {
        var that = this;
        this.modelIds.forEach(function (id) { // @compat es5
            var model = that.model[id];
            if (defined(model)) {
                model.observe(Event.SET, function (v) {
                    that.elementValue(id, v);
                });
            } else {
                console.warn("No model matching model-id '" + id
                    + "' found, bind it manually!"); // TODO Implement logger
            }
        });
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
    /**
     * @param {string} id  model-id of the element
     * @param {string} [v] value to set if defined
     */
    elementValue: function (id, v) {
        if (defined(v)) {
            // TODO Apply some validation
            return this.elements[id].val(v);
        }
        return this.elements[id].val();
    }
});

// statics
Component.MODEL_ATTR = "data-model";
