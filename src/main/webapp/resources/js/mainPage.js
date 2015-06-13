$(document).ready(function () {
  $("li").click(function () {
    $(this).find('#event').slideToggle();
  });
  $("li").find('#event').hide();

  $('a.login-window').click(function () {

    // Getting the variable's value from a link
    var loginBox = $(this).attr('href');

    //Fade in the Popup and add close button
    $(loginBox).fadeIn(300);

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
  $('.signin').submit(function() {
    var login = $('#username').val();
    var password = $('#password').val();

    var json = {"login":login,"password":password};
    $.ajax({
      url: $('.signin').attr("action"),
      data: JSON.stringify(json),
      type: "POST",
      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(user) {
        if (user.length == 0) {
          alert("Wrong user name");
          return;
        }
        var respContent = "";

        respContent += "<span>";
        respContent += user.firstName + " ";
        respContent += user.lastName;
        respContent += "</span>";

        $('a.close').click();
        $(".login-window").html(respContent);
      }
    });
    event.preventDefault();
  })
});