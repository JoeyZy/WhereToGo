var Form = Component.extend({
    /**
     * @override
     * Constructor
     */
    init: function(id, model) {
        this._super(id, model);
        this.form = this.element.is("form") ? this.element : $("form", this);
    },

    registerListeners: function () {
        var that = this;
        this.form.on("submit", function (event) {
            that.notify(Event.SUBMIT);
            event.preventDefault(); // we have a 1-page app!
        });
    },

    action: function () {
        return this.form.attr("action");
    }
});
