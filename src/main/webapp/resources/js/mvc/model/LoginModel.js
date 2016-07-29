var LoginModel = Model.extend({
    /**
     * @class LoginModel
     * @extends Model
     * @classdesc Model for the Login Form
     *
     * @property {Model} email    - email on the Login form
     * @property {Model} password - password on the Login form
     *
     * @constructs LoginModel
     */
    init: function LoginModel(email, password) {
        this._super();

        this.email = new Model(email);
        this.password = new Model(password);
    }
});