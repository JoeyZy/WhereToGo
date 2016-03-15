$(document).ready(function () {
    // Globals variables 
    // 	An array containing objects with information about the events.
    var events = [],
        filters = {},
        user = undefined;
    $('.userInfo').hide();
    $('.logout').hide();

    // Find all event fields
    var $singlePage = $('.Page');
    var $eventPage = $singlePage.find('.EventPage');
    var $eventCategories = $singlePage.find('#event-categories');
    var $eventCategoriesMultiselect = $singlePage.find('.btn-group');
    var $singlePageTitle = $singlePage.find('.SinglePage__title');
    var $eventStart = $singlePage.find('#start');
    var $eventEnd = $singlePage.find('#end');
    var $eventDescription = $singlePage.find("#description");
    var $eventLocation = $singlePage.find("#location");
    var $eventCost = $singlePage.find("#cost");
    var $eventCostCurrency = $singlePage.find("#currencies");
    var $eventPageParticipants = $('.EventPage__participants');
    var $userPage = $singlePage.find(".UserPage");
    var $buttons = $singlePage.find("button");
    var $errors = $singlePage.find('.errors');
    var $buttonEdit = $singlePage.find(".SinglePage__button--edit");
    var $buttonDelete = $singlePage.find(".SinglePage__button--delete");
    var $buttonApply = $singlePage.find('.SinglePage__button--apply');
    var $buttonAttend = $singlePage.find('.SinglePage__button--attend');
    var $buttonAddEvent = $singlePage.find('.SinglePage__button--addEvent');
    var $buttonAddUser = $singlePage.find('.SinglePage__button--addUser');
    var $buttonConfirmDelete = $singlePage.find('.SinglePage__button--confirmDelete');
    var $buttonCancelDelete = $singlePage.find('.SinglePage__button--cancelDelete');
    var $buttonCancelEditing = $singlePage.find('.SinglePage__button--cancelEditing');

    $buttonEdit.on('click', function (event) {
        event.preventDefault();
        makeEventPageEditable();
        $buttonEdit.hide();
        $buttonDelete.hide();
        $buttonApply.show();
        $buttonCancelEditing.show();
        $buttonAttend.hide();
    });

  $buttonApply.on('click', function () {
    updateEvent(true);
  });

  $buttonCancelEditing.on('click', function (event) {
    event.preventDefault();
    makeEventPageUneditable();
    $buttonEdit.show();
    $buttonDelete.show();
    if (user && $participants.find("[data-id=" + user.id + "]").length == 0) {
      $buttonAttend.show();
    }
    $buttonApply.hide();
    $buttonCancelEditing.hide();
  });

  $buttonDelete.on('click', function () {
    $buttonEdit.hide();
    $buttonDelete.hide();
    $buttonApply.hide();
    $buttonCancelEditing.hide();
    renderConfirmationPage();
  });


  function renderConfirmationPage() {
    var parentDisable = $('.parentDisable');
    parentDisable.addClass('visible');
    var confirmationPopUp = $('#ConfirmationPopUp');
    confirmationPopUp.addClass('visible');
    $buttonCancelDelete.show();
    $buttonConfirmDelete.show();
  }

  $buttonConfirmDelete.on('click', function () {
    updateEvent(false);
    createQueryHash(filters);
  });

  $buttonCancelDelete.on('click', function () {
    var confirmationPopUp = $('#ConfirmationPopUp');
    confirmationPopUp.removeClass('visible');
    var parentDisable = $('.parentDisable');
    parentDisable.removeClass('visible');
    $buttonEdit.show();
    $buttonDelete.show();
  });

  function updateEvent(deleted) {
    event.preventDefault();
    var categoriesList = [];
    $eventCategories.find(":selected").each(function (i, selected) {
      categoriesList[i] = {"id": $(selected).attr("data-id"), "name": $(selected).text()};
    });
    var eventJson = {
      "id": $singlePage.find('.EventPage').attr('data-id'),
      "name": $singlePageTitle.val(),
      "categories": categoriesList,
      "startDateTime": $eventStart.val(),
      "endDateTime": $eventEnd.val(),
      "description": $eventDescription.text(),
      "location": $eventLocation.text(),
      "cost": $eventCost.val(),
      "currency": {
        "id": getCurrencyId(), "name": $eventCostCurrency.val()
      }
    };
    deleted ? saveEvent(eventJson, "updateEvent") : saveEvent(eventJson, "deleteEvent");
  }


    var buttonAddEventStateController = function() {
        $(".SinglePage__inputItem__inputField").on("change keyup paste", function() {
            var fieldsAreFilled = true;
            if ($(".SinglePage__title").val() == '') {
                return;
            }
            $(".SinglePage__inputItemsList:visible").find(".SinglePage__inputItem__inputField").each(function() {
                var inputFieldValue = $(this).is('input') ? $(this).val() : $(this).html();
                if (inputFieldValue == '') {
                    fieldsAreFilled = false;
                    return false;
                }
            });
            fieldsAreFilled ? $buttonAddEvent.removeAttr('disabled') : $buttonAddEvent.attr('disabled', 'disabled');
        });
    }();

    function resetSinglePage() {
        $eventCategoriesMultiselect = $singlePage.find('.btn-group');
        //reset inputs
        $singlePage.find(".reset").val("");
        // Empty description
        $eventDescription.empty();
        $eventLocation.empty();
        $eventCost.val("");
        $eventCostCurrency.val("");
        $buttons.hide();
        $errors.hide();
    }

    resetSinglePage();

    $('.home').click(function () {
        loadEvents();
    });
    $.getJSON("categories", function (data) {
        var categoriesListElementTemplate = $('#event-categories-list').html();
        var categoriesListElement = Handlebars.compile(categoriesListElementTemplate);
        var categoriesFilter = $('#event-categories');
        categoriesFilter.html(categoriesListElement(data));
        $eventCategories.multiselect();
    });
    $.getJSON("currencies", function (data) {
        var currTemplate = $('#curr-list').html();
        var currListElement = Handlebars.compile(currTemplate);
        $eventCostCurrency.html(currListElement(data));
    });
    var checkboxes = $('.filters input[type=checkbox]');
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
    // When the "Clear all filters" button is pressed change the hash to '#' (go to the home $singlePage)
    $('.filters button').click(function (e) {
        e.preventDefault();
        window.location.hash = '#';
    });
    // Single event $singlePage buttons
    $singlePage.on('click', function (e) {
        if ($singlePage.hasClass('visible')) {
            var clicked = $(e.target);
            // If the close button or the background are clicked go to the previous $singlePage.
            if (clicked.hasClass('close') || clicked.hasClass('Overlay')) {
                // Change the url hash with the last used filters.
                createQueryHash(filters);
            }
        }
    });
    // Get data about our events from events.json.
  function loadEvents() {
    $.getJSON("events", function (data) {
      // Write the data into our global variable.
      events = data;
      events.sort(function (a, b) {
        return moment(a.startTime, "DD/MM/YY HH:mm").isAfter(moment(b.startTime, "DD/MM/YY HH:mm"));
      });
      // Call a function to create HTML for all the events.
      generateAlleventsHTML(events);
      // Manually trigger a hashchange to start the app.
      $(window).trigger('hashchange');
    });
  }

    // An event handler with calls the render function on every hashchange.
    // The render function will show the appropriate content of out $singlePage.
    $(window).on('hashchange', function () {
        render(window.location.hash);
    });
    // Navigation 
    function render(url) {
        // Get the keyword from the url.
        var temp = url.split('/')[0];
        // Hide whatever $singlePage is currently shown.
        checkSession();
        $('.visible').removeClass('visible');
        var map = {
            // The "Homepage".
            '': function () {
                // Clear the filters object, uncheck all checkboxes, show all the events
                filters = {};
                $('.filters input[type=checkbox]').prop('checked', false);

                renderEventsPage(events);
            },
            '#user': function () {
                renderSingleUserPage(user);
            },
            '#addUser': function () {
                renderSingleUserPage();
            },
            // Single events $singlePage.
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
                    filters = JSON.parse(decodeURIComponent(url));
                }
                    // If it isn't a valid json, go back to homepage ( the rest of the code won't be executed ).
                catch (err) {
                    alert("Can't parse filters data!" + err);
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
        // If the keyword isn't listed in the above - render the error $singlePage.
        else {
            filters = {};
            checkboxes.prop('checked', false);

            renderEventsPage(events);
        }
    }

    // This function is called only once - on $singlePage load.
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
        $('.btn-add-event').on('click', function (event) {
            event.preventDefault();
            window.location.hash = 'addEvent';
        });
        $('.userInfo').click(function (e) {
            e.preventDefault();
            window.location.hash = 'user/email=' + $(this).data('user');
        });
        $('.logout').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: $(this).attr('href'),
                type: "GET",
                success: function (sessionUser) {
                    user = undefined;
                    $('.userInfo').hide();
                    $('.logout').hide();
                    $loginDropDown.show();
                    $(window).trigger('hashchange');
                },
                error: function () {
                    alert('Something wrong');
                },
                complete: function () {
                }
            });
        });
    }

    function displayCategoriesListFilter() {
        $.getJSON("categories", function (data) {
            var categoriesListElementTemplate = $('#categories-list').html();
            var categoriesListElement = Handlebars.compile(categoriesListElementTemplate);
            var categoriesFilter = $('#filter-categories');
            categoriesFilter.html(categoriesListElement(data));
            var checkboxes = $('.all-events input[type=checkbox]');
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
        });
    }

    function enableAddEventsBtn() {
        var addEventBtn = $('.btn-add-event');
        addEventBtn.removeClass('disabled');
        addEventBtn.addClass('btn-success');
        addEventBtn.removeAttr('title');
    }

    function disableAddEventsBtn() {
        var addEventBtn = $('.btn-add-event');
        addEventBtn.removeClass('btn-success');
        addEventBtn.addClass('disabled');
        addEventBtn.attr('title', 'Please login to create an event');
    }

    // This function receives an object containing all the event we want to show.
    function renderEventsPage(data) {
        if (typeof data == 'undefined') {
            return;
        }

        if (typeof user == "undefined") {
            disableAddEventsBtn();
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
        // Show the $singlePage itself.
        // (the render function hides all pages so we need to show the one we want).
        page.addClass('visible');
    }

    var $participantsTemplateScript = $('#participants').html();
    var $participantsTemplate = Handlebars.compile($participantsTemplateScript);
    var $participants = $('.EventPage__events__list');

    function saveEvent(eventJson, newEvent) {
        if (!validateEventFields(eventJson)) {
            $errors.show();
            return;
        }
        $.ajax({
            url: newEvent,
            data: JSON.stringify(eventJson),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function () {
                loadEvents();
                createQueryHash(filters);
            },
            error: function (error) {
                alert("ERROR!" + error);
            },
            complete: function () {
            }
        });
        function validateEventFields(event) {
            const EVENT_NAME_LENGTH_MIN = 3;
            const EVENT_NAME_LENGTH_MAX = 20;
            const DESCRIPTION_LENGTH_MIN = 10;
            const DESCRIPTION_LENGTH_MAX = 1000;
            const LOCATION_LENGTH_MIN = 7;
            const LOCATION_LENGTH_MAX = 100;

            var valid = true;
            $errors.empty();
            if ($singlePage.find('.EventPage__owner__name').val() !== user.firstName + " " + user.lastName) {
                addErrorListItem("Owner field is wrong");
                valid = false;
            }

            var eventName = event.name.trim();
            if (!eventName || eventName.length < EVENT_NAME_LENGTH_MIN || eventName.length > EVENT_NAME_LENGTH_MAX) {
                addErrorListItem("Event name should be greater than " + EVENT_NAME_LENGTH_MIN +
                    " and less than " + EVENT_NAME_LENGTH_MAX + " symbols");
                valid = false;
            }
            if (event.categories.length == 0) {
                addErrorListItem("Choose a category");
                valid = false;
            }
            if (!event.startDateTime) {
                addErrorListItem("Add date for event");
                valid = false;
            }

            var description = event.description.trim();
            if (!description || description.length < DESCRIPTION_LENGTH_MIN || description.length > DESCRIPTION_LENGTH_MAX) {
                addErrorListItem("Description should be greater than " + DESCRIPTION_LENGTH_MIN +
                    " and less than " + DESCRIPTION_LENGTH_MAX + " symbols");
                valid = false;
            }

            var location = event.location.trim();
            if (!location || location.length < LOCATION_LENGTH_MIN || location.length > LOCATION_LENGTH_MAX) {
                addErrorListItem("Location should be greater than " + LOCATION_LENGTH_MIN +
                    " and less than " + LOCATION_LENGTH_MAX + " symbols");
                valid = false;
            }

            return valid;
        }

        function addErrorListItem(message) {
            $errors.append('<li>' + message + '</li>');
        }
    }

    function makeEventPageEditable() {
        $singlePageTitle.attr('readonly', false);
        $eventDescription.attr('contenteditable', true);
        $eventLocation.attr('contenteditable', true);
        $eventStart.datepicker('enable');
        $eventEnd.datepicker('enable');
        $eventDescription.addClass('editable');
        $eventLocation.addClass('editable');
        $eventCost.prop('disabled', false);
        $eventCostCurrency.prop('disabled', false);
        $eventCategories.multiselect('enable');
        $eventCategories.multiselect('refresh');
    }

    function makeEventPageUneditable() {
        $singlePageTitle.attr('readonly', true);
        $eventDescription.attr('contenteditable', false);
        $eventDescription.removeClass('editable');
        $eventLocation.attr('contenteditable', false);
        $eventLocation.removeClass('editable');
        $eventCategories.multiselect('disable');
        $eventDescription.attr('contenteditable', false);
        $eventLocation.attr('contenteditable', false);
        $eventCost.prop('disabled', true);
        $eventCostCurrency.prop('disabled', true);
        $eventPage.find('editable').attr('readonly', true);
        $eventStart.datepicker('disable');
        $eventEnd.datepicker('disable');
        $eventCategories.multiselect('refresh');
    }

    // Opens up a preview for one of the events.
    // Its parameters are an index from the hash and the events object.
    function renderSingleEventPage(index, data, addEvent) {
        resetSinglePage();
        $singlePage.find('.EventPage').attr("data-id", index);
        $singlePage.find('.UserPage').hide();
        $singlePage.find('.EventPage').show();
        if (typeof data != 'undefined' && data.length) {
            // Find the wanted event by iterating the data object and searching for the chosen index.
            renderShowEventPage(data);
        } else {
            if (typeof user === "undefined") {
                window.location.hash = '';
                return;
            }
            renderAddEventPage();
        }
        // Show the $singlePage.
        $singlePage.addClass('visible');

        function renderAddEventPage() {
            populateSinglePageEventPage($singlePage);
            $buttonAddEvent.attr('disabled', 'disabled');
            $buttonAddEvent.on('click', function (event) {
                event.preventDefault();
                if (typeof user !== "undefined") {
                    var categoriesList = [];
                    $eventCategories.find(":selected").each(function (i, selected) {
                        categoriesList[i] = {"id": $(selected).attr("data-id"), "name": $(selected).text()};
                    });
                    var eventJson = {
                        "name": $singlePageTitle.val(),
                        "categories": categoriesList,
                        "startDateTime": $eventStart.val(),
                        "endDateTime": $eventEnd.val(),
                        "description": $eventDescription.text(),
                        "location": $eventLocation.text(),
                        "cost": $eventCost.val(),
                        "currency": {"id":  getCurrencyId(), "name": $eventCostCurrency.val()}
                    };
                    saveEvent(eventJson, "addEvent");
                }
            });
        }

        function renderShowEventPage(data) {
            data.forEach(function (item) {
                if (item.id == index) {
                    $.getJSON("event", {id: item.id}, function (event) {
                        $participants.html($participantsTemplate(event.participants));
                        populateSinglePageEventPage($singlePage, event);

                        $buttonAttend.off();
                        $buttonAttend.on('click', function (event) {
                            event.preventDefault();
                            var json = {
                                id: item.id
                            };
                            $.ajax({
                                url: 'addEventToUser',
                                data: JSON.stringify(json),
                                type: "POST",
                                beforeSend: function (xhr) {
                                    xhr.setRequestHeader("Accept", "application/json");
                                    xhr.setRequestHeader("Content-Type", "application/json");
                                },
                                success: function () {
                                    $.getJSON("event", {id: item.id}, function (event) {
                                        $buttonAttend.hide();
                                        $participants.html($participantsTemplate(event.participants));
                                    });
                                },
                                error: function () {

                                },
                                complete: function () {
                                }
                            });
                        });
                    });
                }
            });
        }

        function populateSinglePageEventPage(singlePage, event) {
            if (typeof event != 'undefined') {
                makeEventPageUneditable();
                $singlePageTitle.val(event.name);
                $eventCategories.val(getEventCategoriesAsList(event.categories));
                $eventPageParticipants.show();
                $eventDescription.html(linkify(event.description));
                $eventLocation.html(linkify(event.location));
                singlePage.find('.EventPage__owner__name').val(event.owner.firstName + " " + event.owner.lastName);
                var startDate = event.startDateTime;
                $eventStart.val(startDate);
                var endDate = event.endDateTime;
                $eventEnd.val(endDate);
                $eventCost.val(event.cost);
                $eventCostCurrency.val(event.currency.name);
                if (user && user.id === event.owner.id) {
                    $buttonEdit.show();
                    $buttonDelete.show();
                }
                if (user && $participants.find("[data-id=" + user.id + "]").length == 0) {
                    $buttonAttend.show();
                }
                $eventCategories.multiselect('refresh');
            } else {
                makeEventPageEditable();
                $eventCategories.val('');
                $eventPageParticipants.hide();
                $buttonAddEvent.show();
                if (typeof user !== 'undefined') {
                    singlePage.find('.EventPage__owner__name').val(user.firstName + " " + user.lastName);
                }
            }

            function getEventCategoriesAsList(categories) {
                var res = categories[0].name;
                for (var i = 1; i < categories.length; i++) {
                    res += ',' + categories[i].name;
                }
                return res.split(',');
            }
        }
    }

    function getCurrencyId() {
      return $eventCostCurrency.find("option:contains('" + $eventCostCurrency.val() + "')").attr("data-id");
    }

    var $userEmail = $userPage.find('.UserPage__email__input');
    var $userPassword = $userPage.find('.UserPage__password__input');
    var $userFirstName = $userPage.find('.UserPage__name__first');
    var $userLastName = $userPage.find('.UserPage__name__last');

    function renderSingleUserPage(user) {
        resetSinglePage();
        $singlePage.find('.EventPage').hide();
        $singlePage.find('.UserPage').show();
        $singlePageTitle.val('User Information');
        $singlePageTitle.attr('readonly', true);
        $('.user-information').val('');
        if (typeof user !== 'undefined') {
            renderUserInfoPage(user);
        } else {
            renderAddUserPage();
        }

        function renderUserInfoPage(user) {
            $.getJSON("userInfo", {email: user.email}, function (user) {
                $userEmail.val(user.email);
                $userEmail.attr("readonly", true);
                $userPage.find('.UserPage__password').hide();
                $userFirstName.val(user.firstName + ' ' + user.lastName);
                $userFirstName.attr("readonly", true);
                $userLastName.hide();
                $userPage.find('.UserPage__events').show();
                $singlePage.addClass('visible');
            });
        }

        function renderAddUserPage() {
            $userPage.find('.UserPage__password').show();
            $userPage.find('.UserPage__name__last').show();
            $userPage.find('.UserPage__events').hide();
            $buttonAddUser.show();
            $userEmail.attr("readonly", false);
            $userFirstName.attr("readonly", false);
            $buttonAddUser.on('click', function (event) {
                event.preventDefault();
                if (!validateEventFields()) {
                    $errors.show();
                    return;
                }
                var userJson = {
                    "email": $userEmail.val(),
                    "password": $userPassword.val(),
                    "firstName": $userFirstName.val(),
                    "lastName": $userLastName.val()
                };
                $.ajax({
                    url: "addUser",
                    data: JSON.stringify(userJson),
                    type: "POST",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                    },
                    success: function () {
                        loadEvents();
                        createQueryHash(filters);
                    },
                    error: function (error) {
                        alert("ERROR!" + error);
                    },
                    complete: function () {
                    }
                });
                function validateEventFields() {
                    var valid = true;
                    $errors.empty();
                    if ($userEmail.val().length == 0) {
                        addErrorListItem("Email mustn't be empty");
                        valid = false;
                    }
                    if ($userPassword.val().length == 0) {
                        addErrorListItem("Enter password");
                        valid = false;
                    }
                    if ($userFirstName.val().length == 0) {
                        addErrorListItem("Enter First Name");
                        valid = false;
                    }
                    if ($userLastName.val().length == 0) {
                        addErrorListItem("Enter Last Name");
                        valid = false;
                    }
                    return valid;
                }

                function addErrorListItem(message) {
                    $errors.append('<li>' + message + '</li>');
                }
            });
            // Show the $singlePage.
            $singlePage.addClass('visible');
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
                            if (item[c].indexOf(filter) != -1) {
                                results.push(item);
                                isFiltered = true;
                            }
                        }
                        if (Array.isArray(item[c])) {
                            item[c].forEach(function (i) {
                                if (i.indexOf(filter) != -1) {
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

    // Shows the error $singlePage.
    function renderErrorPage() {
        var page = $('.ErrorPage');
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
        $('.userInfo').data('user', user.email);
        $('.userInfo').show();
        $('.logout').text('Logout');
        $('.logout').show();
        $loginDropDown.toggle();
        grantRightsToUser();
    }

    function checkSession() {
        if (typeof user !== 'undefined') {
            grantRightsToUser();
            return;
        }
        $.ajax({
            url: "user",
            type: "GET",
            success: function (sessionUser) {
                if (sessionUser.length == 0) {
                    return;
                }
                setUser(sessionUser);
                $loginDropDown.hide();
            },
            error: function () {
            },
            complete: function () {
            }
        });
    }

    function grantRightsToUser() {
        if (typeof user === 'undefined') {
            disableAddEventsBtn();
            return;
        }
        enableAddEventsBtn();
    }

    loadEvents();
    displayCategoriesListFilter();

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

});