var ModelFacade = Class.extend({
    /**
     * @class ModelFacade
     * @classdesc Aggregates all the Model's in app
     *
     * @property {LoginModel} login - model for Login
     */
    init: function ModelFacade() {
        this.login = new LoginModel("", ""); // you may set initial values here
    }
});
