var ViewFacade = Class.extend({
    // constructor
    init: function() {
        this._super();
        this.login = new LoginForm("login");
    }
});
