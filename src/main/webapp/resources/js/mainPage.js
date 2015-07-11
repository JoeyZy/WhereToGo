$(document).ready(function () {
    $("li").click(function () {
        $(this).find('#event').slideToggle();
    });
    $("li").find('#event').hide();

    updateElements();

    $('a.login-link').click(function () {

        // Getting the variable's value from a link
        var loginBox = $(this).attr('href');

        //Fade in the Popup and add close button
        $(loginBox).fadeIn(300);

        $('#username').focus();

        //Set the center alignment padding + border
        var popMargTop = ($(loginBox).height() + 24) / 2;
        var popMargLeft = ($(loginBox).width() + 24) / 2;

        $(loginBox).css({
            'margin-top': -popMargTop,
            'margin-left': -popMargLeft
        });

        // Add the mask to body
        $('body').append('<div id="mask"></div>');
        $('#mask').fadeIn(300);

        return false;
    });

    // When clicking on the button close or the mask layer the popup closed
    $('body').on('click', 'a.close, #mask', function () {
        $('#mask , .login-popup').fadeOut(300, function () {
            $('#mask').remove();
        });
        return false;
    });
    $('.signin').submit(function () {
        var email = $('#username').val();
        var password = $('#password').val();

        var json = {"email": email, "password": password};
        $.ajax({
            url: $('.signin').attr("action"),
            data: JSON.stringify(json),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (user) {
                if (user.length == 0) {
                    alert("Wrong user name");
                    return;
                }

                $('a.close').click();
                $('#user-first-name').text(user.firstName);
                $('#user-last-name').text(user.lastName);
                $('.login-link').hide();
                $('.logout-box').show();
                location.reload();
            },
            error: function () {
                alert("ERROR!");
            },
            complete: function () {
                updateElements();
            }
        });
        event.preventDefault();
    });
    $('.logout-link').click(function () {
        $.ajax({
            url: $('.logout-link').attr("href"),
            type: "GET",
            success: function (success) {
                if (success) {
                    $('.login-link').show();
                    $('.logout-box').hide();
                    return;
                }
                alert("logout error");
            },
            error: function () {
                alert("ERROR!");
            },
            complete: function () {
                updateElements();
            }
        });
        event.preventDefault();
    });
});

function updateElements() {
    $('#username').val('root@gmail.com');
    $('#password').val('root');


    var user = "";
    $.ajax({
        type: "GET",
        data: JSON,
        url: "/user",
        async: false,
        success: function (sessionUser) {
            user = sessionUser;
        }
    });

    if (user != "") {
        $('.login-link').hide();
        $('#add-event-btn').prop('disabled', false);
    } else {
        $('.logout-box').hide();
        $('#add-event-btn').prop('disabled', true);
    }
}