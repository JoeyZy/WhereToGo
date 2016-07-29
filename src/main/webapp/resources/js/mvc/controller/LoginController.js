var LoginController = AbstractController.extend({

    /**
     * @class LoginController
     * @extends AbstractController
     * @classdesc Controller for the Login form
     *
     * @constructs LoginController
     * @param {Model}     model  Model (not ModelFacade)
     * @param {Component} view   View (Component) (not ViewFacade)
     */
    init: function LoginController(model, view) {
        this._super(model, view);
    },

    /**
     * @inheritdoc
     * @memberof LoginController#
     */
    bind: function () {
        var that = this;
        this.view.observe(ModelEvent.SUBMIT, function () {
            that.syncModel();
            that.login();
        });
        // TODO Not working for now! Need to fire this event on clicking "Sign up" button (add Button component)...
        this.view.observe(ModelEvent.ADD, function () { // TODO Consider having a special "ADD_USER" event
            that.signup();
        });
    },

    /**
     * @summary Signs in a user containing in the model
     * @memberof LoginController#
     */
    login: function () {
        $.ajax({ // TODO Create AJAX wrapper running on promises
            url: this.view.action(), // action is not modified, no sense to hold it in model
            data: JSON.stringify({
                email   : this.model.email.val(),
                password: this.model.password.val()
            }),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (sessionUser) {
                if (sessionUser.length == 0) {
                    alert('Wrong credentials');
                    return;
                }
                setUser(sessionUser); // TODO check it
                loadEvents();
            },
            error: function () {
            },
            complete: function () {
            }
        });
    },

    /**
     * @summary Signs up a user containing in the model
     * @memberof LoginController#
     */
    signup: function () {
        window.location.hash = "addUser"; // TODO Move it to utilities
    }
});
