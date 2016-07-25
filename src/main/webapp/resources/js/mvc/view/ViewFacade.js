var ViewFacade = Class.extend({
    // constructor
    init: function ViewFacade(model) {
        this.login = new LoginForm("login", model.login);
    }
});
