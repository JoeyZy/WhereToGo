var Component = AbstractSubject.extend({
    // constructor
    /**
     * @param {string} id
     * @param {ModelFacade} model
     */
    init: function Component(id, model) { // TODO Pass the root element also
        this._super();
        this.id = id;
        this.selector = this.getSelector();
        this.element = null;
        this.elements = {}; // child components (map of model-ids to elements) // TODO Consider renaming it to "children"
        this.model = model;
        this.wrap(this.selector); // TODO Consider (possible) lazy wrapping
        this.bind();
    },
    wrap: function (selector) {
        if (defined(selector)) {
            this.element = $(this.selector);
        }
        this.validateElement();
        var that = this;
        var elements = $("[" + Component.MODEL_ATTR + "]", this.element);
        elements.each(function (i, e) { // @compat es5
            var element = $(e);
            var id = element.attr(Component.MODEL_ATTR);
            that.elements[id] = element;
        });
        // postpone registration to ensure the component is initialized
        setTimeout(function () {
            that.registerListeners();
        }, 0);
    },
    render: function () { // TODO Is it needed? Or only wrapping is supported?
        throw new Error("Cannot invoke an abstract method");
    },
    registerListeners: function () {
        // no listeners for abstract component
    },
    bind: function () {
        var that = this;
        this.getModelNames().forEach(function (id) { // @compat es5
            var model = that.model[id];
            if (model != null) {
                model.observe(ModelEvent.SET, function (v) {
                    that.elementValue(id, v);
                });
            } else {
                console.warn("No model matching model-id '" + id
                    + "' found, bind it manually!"); // TODO Implement logger
            }
        });
    },
    getModelNames: function () {
        var that = this, names = [];
        var modelIds = Object.keys(this.elements); // @compat es5
        modelIds.forEach(function (fullId) { // @compat es5
            var id = that.extractModelName(fullId);
            names.push(id);
        });
        return names;
    },
    extractModelName: function (modelId) {
        var name = null;
        var re = /\.([^\.]+)$/ig;
        var matches = re.exec(modelId);
        if (matches.length == 2) {
            name = matches[1];
        } else {
            console.warn("Can't extract model name from model ID '" + modelId + "'");
        }
        return name;
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
    /**
     * @param {string} name name of the model, e.g. "email" for the model-id "login.email"
     */
    getElementByModelName: function (name) {
        for (var key in this.elements) {
            if (this.elements.hasOwnProperty(key)
                && key.endsWith(name))
            {
                return this.elements[key];
            }
        }
        return null;
    },
    // WARNING: This will break in case this element does not have val()
    /**
     * @param {string} id  model-id of the element
     * @param {string} [v] value to set if defined
     */
    elementValue: function (id, v) {
        var element = this.getElementByModelName(id);
        if (defined(v)) {
            // TODO Apply some validation
            return element.val(v);
        }
        return element.val();
    }
});

// statics
Component.MODEL_ATTR = "data-model";
