var LoginController = AbstractController.extend({
    // constructor
    /**
     * {ModelFacade} model
     * {ViewFacade} view
     */
    init: function (model, view) {
        this._super(model, view);
    },
    bind: function () {
        var that = this;
        this.view.login.observe(Event.SUBMIT, function () {
            that.login();
        });
        this.view.login.observe(Event.ADD, function () { // TODO Consider having a special "ADD_USER" event
            this.signup();
        });
        // ...
    },

    login: function () {
        $.ajax({ // TODO Create AJAX wrapper running on promises
            url: that.view.login.action(), // action is not modified, no sense to hold it in model
            data: JSON.stringify({
                email   : that.model.login.email,
                password: that.model.login.password
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

    signup: function () {
        window.location.hash = "addUser"; // TODO Move it to utilities
    }
});
