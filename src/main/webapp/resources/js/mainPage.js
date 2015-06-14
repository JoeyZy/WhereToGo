$(document).ready(function () {
    $("li").click(function () {
        $(this).find('#event').slideToggle();
    });
    $("li").find('#event').hide();

    var user = $('.login-div').attr("data-user");

    if (user) {
        $('.login-link').hide();
    } else {
        $('.logout-link').hide();
    }

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
        var login = $('#username').val();
        var password = $('#password').val();

        var json = {"login": login, "password": password};
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
                $('.logout-link').show();
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
                    alert("logout!");
                    $('.login-link').show();
                    $('.logout-link').hide();
                    return;
                }
                alert("logout error");
            },
            error: function () {
                alert("ERROR!");
            }
        });
        event.preventDefault();
    });
});