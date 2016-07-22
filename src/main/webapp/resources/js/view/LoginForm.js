var LoginForm = Form.extend({
    // constructor
    init: function(id, model) {
        this._super(id, model);
    },
    email: function (v) {
        return this.elementValue("email", v);
    },
    password: function (v) {
        return this.elementValue("password", v);
    }
});
