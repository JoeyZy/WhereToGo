/*
 Dropdown with Multiple checkbox select with jQuery - May 27, 2013
 (c) 2013 @ElmahdiMahmoud
 license: http://www.opensource.org/licenses/mit-license.php
 */

$(".dropdown-checkboxes dt a").on('click', function() {
    $(".dropdown-checkboxes dd ul").slideToggle('fast');
});

$(".dropdown-checkboxes dd ul li a").on('click', function() {
    $(".dropdown-checkboxes dd ul").hide();
});

function getDropDownSelectedValue(id) {
    return $("#" + id).find("dt a span.value").html();
}

$(document).bind('click', function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("dropdown")) $(".dropdown-checkboxes dd ul").hide();
});

$('.mutliSelect input[type="checkbox"]').on('click', function() {

    var title = $(this).closest('.mutliSelect').find('input[type="checkbox"]').val(),
        title = $(this).val() + ",";

    if ($(this).is(':checked')) {
        var html = '<span title="' + title + '">' + title + '</span>';
        $('.multiSel').append(html);
        $(".hida").hide();
    } else {
        $('span[title="' + title + '"]').remove();
        var ret = $(".hida");
        $('.dropdown-checkboxes dt a').append(ret);

    }
});