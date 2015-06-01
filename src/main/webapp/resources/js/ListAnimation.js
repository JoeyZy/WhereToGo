$(document).ready(function () {
    $("li").click(function () {
        $(this).find('#event').slideToggle();
    });
    $("li").find('#event').hide();
});