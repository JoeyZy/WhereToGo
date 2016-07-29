/**
 * Aggregates all the VIew's (Component's) in app
 *
 * @property {LoginForm} login - view (Component) for Login
 */
var ViewFacade = Class.extend({
    // constructor
    /**
     * Constructor
     * @param {ModelFacade} model ModelFacade
     */
    init: function ViewFacade(model) {
        this.login = new LoginForm("login", model.login);
    }
});
