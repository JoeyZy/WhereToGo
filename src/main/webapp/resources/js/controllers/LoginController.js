var LoginController = Class.extend({
    // constructor
    init: function() {
        this._super();
        this.form = new LoginForm("login");
    },
    bind: function () {
        // ...
    }
});

// BEFORE REFACTORING
/*
var loginForm = $('#login-nav');
var $loginDropDown = $('.dropdown');
// Login handler
loginForm.submit(function (e) {
    var email = loginForm.find("#userEmail").val();
    var password = loginForm.find("#userPassword").val();
    var json = {"email": email, "password": password};
    $.ajax({
        url: loginForm.attr("action"),
        data: JSON.stringify(json),
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
            setUser(sessionUser);
            loadEvents();
        },
        error: function () {
        },
        complete: function () {
        }
    });
    e.preventDefault();
});
var register = $('.btn-add-user');
register.on('click', function () {
    window.location.hash = 'addUser';
});
*/