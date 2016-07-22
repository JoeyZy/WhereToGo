var LoginModel = Model.extend({
    // constructor
    init: function(email, password) {
        this._super();
        this.email = new Model(email);
        this.password = new Model(password);
    }
});