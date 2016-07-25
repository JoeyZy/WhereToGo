var Form = Component.extend({
    /**
     * @override
     * Constructor
     */
    init: function Form(id, model) {
        this._super(id, model);
        this.form = this.element.is("form")
            ? this.element
            : $("form", this.element);
    },

    registerListeners: function () {
        var that = this;
        this.form.on("submit", function (event) {
            that.notify(ModelEvent.SUBMIT);
            event.preventDefault(); // we have a 1-page app!
        });
    },

    action: function () {
        return this.form.attr("action");
    }
});
