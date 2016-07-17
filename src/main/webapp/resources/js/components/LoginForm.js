var LoginForm = Component.extend({
    // constructor
    init: function(id) {
        this._super(id);
    },
    email: function (v) {
        return this.value("email", v);
    },
    password: function (v) {
        return this.value("password", v);
    }
});
