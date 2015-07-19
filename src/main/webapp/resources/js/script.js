$(function () {
    // Globals variables 
    // 	An array containing objects with information about the events.
    var events = [],
        filters = {},
        user = "";
    $('.home').click(function () {
        loadEvents();
    });
    $('.userInfo').click(function (e) {
        alert('ololo');
        e.preventDefault();
    });
    var checkboxes = $('.all-events input[type=checkbox]');
    var loginForm = $('#login-nav');
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
            },
            error: function () {
            },
            complete: function () {
            }
        });
        e.preventDefault();
    });
    //	Event handlers for frontend navigation
    //	Checkbox filtering
    checkboxes.click(function () {
        var that = $(this),
            specName = that.attr('name');
        // When a checkbox is checked we need to write that in the filters object;
        if (that.is(":checked")) {
            // If the filter for this specification isn't created yet - do it.
            if (!(filters[specName] && filters[specName].length)) {
                filters[specName] = [];
            }
            //	Push values into the chosen filter array
            filters[specName].push(that.val());
            // Change the url hash;
            createQueryHash(filters);
        }
        // When a checkbox is unchecked we need to remove its value from the filters object.
        if (!that.is(":checked")) {
            if (filters[specName] && filters[specName].length && (filters[specName].indexOf(that.val()) != -1)) {
                // Find the checkbox value in the corresponding array inside the filters object.
                var index = filters[specName].indexOf(that.val());
                // Remove it.
                filters[specName].splice(index, 1);
                // If it was the last remaining value for this specification,
                // delete the whole array.
                if (!filters[specName].length) {
                    delete filters[specName];
                }
            }
            // Change the url hash;
            createQueryHash(filters);
        }
    });
    // When the "Clear all filters" button is pressed change the hash to '#' (go to the home page)
    $('.filters button').click(function (e) {
        e.preventDefault();
        window.location.hash = '#';
    });
    // Single event page buttons
    var singleEventPage = $('.single-event');
    singleEventPage.on('click', function (e) {
        e.preventDefault();
        if (singleEventPage.hasClass('visible')) {
            var clicked = $(e.target);
            // If the close button or the background are clicked go to the previous page.
            if (clicked.hasClass('close') || clicked.hasClass('overlay')) {
                // Change the url hash with the last used filters.
                createQueryHash(filters);
            }
            if (clicked.hasClass('edit')) {
                singleEventPage.find('.apply-btn').css('visibility', 'visible');
            }
        }
    });
    // Get data about our events from events.json.
    function loadEvents() {
        $.getJSON("events", function (data) {
            // Write the data into our global variable.
            events = data;
            // Call a function to create HTML for all the events.
            generateAlleventsHTML(events);
            // Manually trigger a hashchange to start the app.
            $(window).trigger('hashchange');
        });
    }

    // An event handler with calls the render function on every hashchange.
    // The render function will show the appropriate content of out page.
    $(window).on('hashchange', function () {
        render(window.location.hash);
    });
    // Navigation 
    function render(url) {
        // Get the keyword from the url.
        var temp = url.split('/')[0];
        // Hide whatever page is currently shown.
        $('.visible').removeClass('visible');
        var map = {
            // The "Homepage".
            '': function () {
                // Clear the filters object, uncheck all checkboxes, show all the events
                filters = {};
                checkboxes.prop('checked', false);
                //       loadEvents();
                renderEventsPage(events);
            },
            // Single events page.
            '#event': function () {
                // Get the index of which event we want to show and call the appropriate function.
                var index = url.split('#event/')[1].trim();
                renderSingleEventPage(index, events);
            },
            // Add event
            '#addEvent': function () {
                renderSingleEventPage();
            },
            // Page with filtered events
            '#filter': function () {
                // Grab the string after the '#filter/' keyword. Call the filtering function.
                url = url.split('#filter/')[1].trim();
                // Try and parse the filters object from the query string.
                try {
                    filters = JSON.parse(url);
                }
                    // If it isn't a valid json, go back to homepage ( the rest of the code won't be executed ).
                catch (err) {
                    window.location.hash = '#';
                    return;
                }
                renderFilterResults(filters, events);
            }
        };
        // Execute the needed function depending on the url keyword (stored in temp).
        if (map[temp]) {
            map[temp]();
        }
        // If the keyword isn't listed in the above - render the error page.
        else {
            renderErrorPage();
        }
        checkSession();
    }

    // This function is called only once - on page load.
    // It fills up the events list via a handlebars template.
    // It recieves one parameter - the data we took from events.json.
    function generateAlleventsHTML(data) {
        var list = $('.all-events .events-list');
        var theTemplateScript = $('#events-template').html();
        //Compile the template​
        var theTemplate = Handlebars.compile(theTemplateScript);
        list.find('li').remove();
        list.append(theTemplate(data));
        // Each events has a data-index attribute.
        // On click change the url hash to open up a preview for this event only.
        // Remember: every hashchange triggers the render function.
        list.find('li').on('click', function (e) {
            e.preventDefault();
            var eventIndex = $(this).data('index');
            window.location.hash = 'event/' + eventIndex;
        });
        var header = $('header');
        header.find('.login-link').on('click', function (e) {
            e.preventDefault();
            window.location.hash = 'login/';
        });
        $('.btn-add-event').on('click', function (e) {
            e.preventDefault();
            window.location.hash = 'addEvent/';
        });
    }

    // This function receives an object containing all the event we want to show.
    function renderEventsPage(data) {
        if (typeof data == 'undefined') {
            return;
        }
        var page = $('.all-events'),
            allEvents = $('.all-events .events-list > li');
        // Hide all the events in the events list.
        allEvents.addClass('hidden');
        // Iterate over all of the events.
        // If their ID is somewhere in the data object remove the hidden class to reveal them.
        allEvents.each(function () {
            var that = $(this);
            data.forEach(function (item) {
                if (that.data('index') == item.id) {
                    that.removeClass('hidden');
                }
            });
        });
        // Show the page itself.
        // (the render function hides all pages so we need to show the one we want).
        page.addClass('visible');
    }

    // Opens up a preview for one of the events.
    // Its parameters are an index from the hash and the events object.
    function renderSingleEventPage(index, data) {
        var page = $('.single-event'),
            container = $('.preview-large');
        var $eventTitle = container.find('#title');
        var $eventOwner = container.find('#owner');
        var $eventStart = container.find('#start');
        var $eventEnd = container.find('#end');
        var $eventDescription = container.find("#description");
        // Find the wanted event by iterating the data object and searching for the chosen index.
        if (typeof data != 'undefined' && data.length) {
            data.forEach(function (item) {
                if (item.id == index) {
                    $.getJSON("event", {id: item.id}, function (event) {
                        if (user.email === event.owner.email) {
                            container.find('.edit').css('visibility', 'visible');
                        }
                        populateSinglePageEventPage(container, event);
                    });
                    container.find('.edit').css('visibility', 'hidden');
                    var actionButton = container.find(".btn-action");
                    actionButton.addClass('btn-info');
                    actionButton.html("I'll be there");
                }
            });
        } else {
            container.find('.edit').css('visibility', 'hidden');
            populateSinglePageEventPage(container);
            var actionButton = container.find(".btn-action");
            actionButton.addClass('btn-info');
            actionButton.html("Apply");
            actionButton.on('click', function () {
                var eventJson = {
                    "name": $eventTitle.val(),
                    "startDateTime": $eventStart.val(),
                    "endDateTime": $eventEnd.val(),
                    "description": $eventDescription.val()
                };
                $.ajax({
                    url: "addEvent",
                    data: JSON.stringify(eventJson),
                    type: "POST",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                    },
                    success: function () {
                        alert("success");
                    },
                    error: function () {
                        alert("ERROR!");
                    },
                    complete: function () {

                    }
                });
            });
        }
        // Show the page.
        page.addClass('visible');

        function populateSinglePageEventPage(container, event) {
            if (typeof event != 'undefined') {
                container.find('#title').val(event.name);
                container.find('#description').html(event.description);
                container.find('#owner').val(event.owner.firstName + " " + event.owner.lastName);
                var startDate = moment(new Date(event.startDateTime)).format('DD/MM/YYYY HH:mm');
                container.find('#start').val(startDate);
                var endDate = moment(new Date(event.endDateTime)).format('DD/MM/YYYY HH:mm');
                container.find('#end').val(endDate);
            } else {
                container.find('#title').val("Title");
                container.find('.event-information').trigger("reset");
                container.find('#description').empty();
                if (typeof user != 'undefined') {
                    container.find('#owner').val(user.firstName + " " + user.lastName);
                }
            }

            function makeDataEditable() {

            }
        }
    }

    // Find and render the filtered data results. Arguments are:
    // filters - our global variable - the object with arrays about what we are searching for.
    // events - an object with the full events list (from event.json).
    function renderFilterResults(filters, events) {
        // This array contains all the possible filter criteria.
        var criteria = ['category', 'startTime'],
            results = [],
            isFiltered = false;
        // Uncheck all the checkboxes.
        // We will be checking them again one by one.
        checkboxes.prop('checked', false);
        criteria.forEach(function (c) {
            // Check if each of the possible filter criteria is actually in the filters object.
            if (filters[c] && filters[c].length) {
                // After we've filtered the events once, we want to keep filtering them.
                // That's why we make the object we search in (events) to equal the one with the results.
                // Then the results array is cleared, so it can be filled with the newly filtered data.
                if (isFiltered) {
                    events = results;
                    results = [];
                }
                // In these nested 'for loops' we will iterate over the filters and the events
                // and check if they contain the same values (the ones we are filtering by). 
                // Iterate over the entries inside filters.criteria (remember each criteria contains an array).
                filters[c].forEach(function (filter) {
                    // Iterate over the events.
                    events.forEach(function (item) {
                        // If the event has the same specification value as the one in the filter
                        // push it inside the results array and mark the isFiltered flag true. 
                        if (typeof item[c] == 'number') {
                            if (item[c] == filter) {
                                results.push(item);
                                isFiltered = true;
                            }
                        }
                        if (typeof item[c] == 'string') {
                            if (item[c].toLowerCase().indexOf(filter) != -1) {
                                results.push(item);
                                isFiltered = true;
                            }
                        }
                        if (Array.isArray(item[c])) {
                            item[c].forEach(function (i) {
                                if (i.toLowerCase().indexOf(filter) != -1) {
                                    results.push(item);
                                    isFiltered = true;
                                }
                            });
                        }
                    });
                    // Here we can make the checkboxes representing the filters true,
                    // keeping the app up to date.
                    if (c && filter) {
                        $('input[name=' + c + '][value=' + filter + ']').prop('checked', true);
                    }
                });
            }
        });
        // Call the renderEventsPage.
        // As it's argument give the object with filtered events.
        renderEventsPage(results);
    }

    // Shows the error page.
    function renderErrorPage() {
        var page = $('.error');
        page.addClass('visible');
    }

    // Get the filters object, turn it into a string and write it into the hash.
    function createQueryHash(filters) {
        // Here we check if filters isn't empty.
        if (!$.isEmptyObject(filters)) {
            // Stringify the object via JSON.stringify and write it after the '#filter' keyword.
            window.location.hash = '#filter/' + JSON.stringify(filters);
        }
        else {
            // If it's empty change the hash to '#' (the homepage).
            window.location.hash = '#';
        }
    }

    function setUser(sessionUser) {
        user = sessionUser;
        $('.userInfo').text(user.firstName + " " + user.lastName);
        $('.dropdown').toggle();
        grantRightsToUser();
    }

    function checkSession() {
        if (user.length != 0) {
            grantRightsToUser();
            return;
        }
        $.ajax({
            url: "/user",
            type: "GET",
            success: function (sessionUser) {
                if (sessionUser.length == 0) {
                    return;
                }
                setUser(sessionUser);
            },
            error: function () {
            },
            complete: function () {
            }
        });
    }

    function grantRightsToUser() {
        if (typeof user == 'undefined') {
            return;
        }
        $('.btn-add-event').addClass('visible');
    }

    loadEvents();

    var startDateTextBox = $('#start');
    var endDateTextBox = $('#end');

    $.timepicker.datetimeRange(
        startDateTextBox,
        endDateTextBox,
        {
            minDate: 0,
            dateFormat: 'dd/mm/yy',
            timeFormat: 'HH:mm',
            start: {}, // start picker options
            end: {} // end picker options
        }
    );

    $('#categories').multiselect();

});