$(document).ready(function () {
	// Globals variables
	// 	An array containing objects with information about the events.
	var events = [],
		groups = [],
		myGroups = [],
		filters = {},
		filterColors = {},
		user = undefined;
	var adminRole = 'admin';
	var oldLocationHash = "#";
	$('.userInfo').hide();
	$('.logout').hide();

	// Find all event fields
	var $myEventsLink = $('.my-events');
	var $allGroupsLink = $('.groups');
	var $singlePage = $('.Page');
	var $eventPage = $singlePage.find('.EventPage');
	var $eventCategories = $singlePage.find('#event-categories');
	var $eventCategoriesMultiselect = $singlePage.find('.btn-group');
	var $singlePageTitle = $singlePage.find('.SinglePage__title');
	var $eventStart = $singlePage.find('#start');
	var $eventEnd = $singlePage.find('#end');
	var $eventDescription = $singlePage.find("#description");
	var $eventLocation = $singlePage.find("#location");
	var $eventTargetGroup = $singlePage.find("#target-group");
	var $eventCost = $singlePage.find("#cost");
	var $eventCostCurrency = $singlePage.find("#currencies");
	var $eventPageParticipants = $('.EventPage__participants');
	var $userPage = $singlePage.find(".UserPage");
	var $calendarPage = $singlePage.find(".Calendar");

	var $groupPage = $singlePage.find(".GroupPage");
	var $groupDescription = $singlePage.find("#GroupDescription");
	var $groupPageParticipants = $('.GroupPage__participants');
	var $groupLocation = $singlePage.find("#GroupLocation");
	var $buttonEditGroup = $singlePage.find('.SinglePage__button--editGroup');
	var $buttonDeleteGroup = $singlePage.find('.SinglePage__button--deleteGroup');
	var $buttonApplyGroup = $singlePage.find('.SinglePage__button--applyGroup');

	var $buttons = $singlePage.find("button");
	var $errors = $singlePage.find('.errors');
	var $buttonEdit = $singlePage.find(".SinglePage__button--edit");
	var $buttonDelete = $singlePage.find(".SinglePage__button--delete");
	var $buttonApply = $singlePage.find('.SinglePage__button--apply');
	var $buttonAttend = $singlePage.find('.SinglePage__button--attend');
	var $buttonSubscribe = $singlePage.find('.SinglePage__button--subscribe');
	var $buttonUnSubscribe = $singlePage.find('.SinglePage__button--unsubscribe');
	var $buttonCancelAttend = $singlePage.find('.SinglePage__button--cancelAttend');
	var $buttonAddEvent = $singlePage.find('.SinglePage__button--addEvent');
	$buttonAddEvent.on('click', function () {

		var categoriesList = [];
		$eventCategories.find(":selected").each(function (i, selected) {
			categoriesList[i] = {"id": $(selected).attr("data-id"), "name": $(selected).text()};
		});
		var eventJson = {
			"id": $singlePage.find('.EventPage').attr('data-id'),
			"name": $singlePageTitle.val(),
			"categories": categoriesList,
			"startTime": $eventStart.val(),
			"endTime": $eventEnd.val(),
			"description": $eventDescription.text(),
			"targetGroup": $eventTargetGroup.text(),
			"location": $eventLocation.text(),
			"cost": $eventCost.val(),
			"currency": {"id": getCurrencyId(), "name": $eventCostCurrency.val()},
			"picture": isDefaultPicture() ? "" : $picture.attr('src')
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
				loadEvents();
				createQueryHash(filters);
			},
			error: function (error) {
				alert("ERROR!" + error);
			},
			complete: function () {
			}
		});

		/*if (typeof user !== "undefined") {
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
				"targetGroup": $eventTargetGroup.text(),
				"location": $eventLocation.text(),
				"cost": $eventCost.val(),
				"currency": {"id": getCurrencyId(), "name": $eventCostCurrency.val()},
				"picture": isDefaultPicture() ? "" : $picture.attr('src')
			};
			saveEvent(eventJson, "addEvent");
		}*/
		return false;
	});


	var $buttonAddGroup = $singlePage.find('.SinglePage__button--addGroup');

	$buttonAddGroup.on('click', function () {
		if (typeof user !== "undefined") {
			var groupJson = {
				"id": $singlePage.find('.GroupPage').attr('data-id'),
				"name": $singlePageTitle.val(),
				"description": $groupDescription.text(),
				"location": $groupLocation.text()
			};
			saveGroup(groupJson, "addGroup");
		}
		return false;
	});

	var $buttonAddUser = $singlePage.find('.SinglePage__button--addUser');
	var $buttonConfirmDelete = $singlePage.find('.SinglePage__button--confirmDelete');
	var $buttonCancelDelete = $singlePage.find('.SinglePage__button--cancelDelete');
	var $buttonCancelEditing = $singlePage.find('.SinglePage__button--cancelEditing');
	var $buttonUploadPicture = $singlePage.find('.SinglePage__button--upload');
	var $pictureUploadPlaceholder = $singlePage.find('.uploadPlaceholderEvent');
	var $pictureParent = $singlePage.find('li.event_pic');
	var $picture = $singlePage.find('img.event_pic');

	// Spring security. Attach csrf token to all request
	$(function () {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});
	
	var $buttonCancelEditingGroup = $singlePage.find('.SinglePage__button--cancelEditingGroup');
	
	var $buttonUploadPicture = $singlePage.find('.SinglePage__button--upload');

	$buttonEdit.on('click', function (event) {
		makeEventPageEditable();
		$buttonEdit.hide();
		$buttonDelete.hide();
		$buttonApply.show();
		$buttonCancelEditing.show();
		$buttonAttend.hide();
		return false;
	});

	$buttonEditGroup.on('click', function(group){
		makeGroupPageEditable();
		$buttonEditGroup.hide();
		$buttonDeleteGroup.hide();
		$buttonApplyGroup.show();
		$buttonCancelEditingGroup.show();
		$buttonSubscribe.hide();
		$buttonUnSubscribe.hide();
		return false;
	});
	
	$buttonApply.on('click', function () {
		updateEvent(true);
		return false;
	});

	$buttonApplyGroup.on('click', function () {
		updateGroup(true);
		return false;
	});

	$buttonCancelEditing.on('click', function (event) {
		makeEventPageUneditable();
		var index = (window.location.hash).split('#event/')[1].trim();
		renderSingleEventPage(index, events);
		$buttonEdit.show();
		$buttonDelete.show();
		if (user && $participants.find("[data-id=" + user.id + "]").length == 0) {
			$buttonAttend.show();
		}
		$buttonApply.hide();
		$buttonCancelEditing.hide();
		return false;
	});

	$buttonCancelEditingGroup.on('click', function (group) {
		makeGroupPageUneditable();
		var index = (window.location.hash).split('#group/')[1].trim();
		renderSingleGroupPage(index,groups);
		$buttonEditGroup.show();
		$buttonDeleteGroup.show();
		if (user && $participants.find("[data-id=" + user.id + "]").length == 0) {
			$buttonSubscribe.show();
		} else {
			$buttonUnSubscribe.show();
		}
		$buttonApplyGroup.hide();
		$buttonCancelEditingGroup.hide();
		return false;
	});

	$buttonDelete.on('click', function () {
		renderConfirmationPage();
		return false;
	});

	$buttonDeleteGroup.on('click', function () {
		renderConfirmationGroupPage();
		return false;
	});

	$pictureUploadPlaceholder.on('click', function () {
		$buttonUploadPicture.click();
		return false;
	});


	$buttonUploadPicture.on('change', function () {
		var input = ($buttonUploadPicture)[0];
		if (input.files && input.files[0]) {
			if (input.files[0].size / 1024 / 1024 > 3) {
				alert('You can download file with size less then 3Mb');
				return;
			}

			var reader = new FileReader();
			reader.onload = function () {
				$picture.attr('src', reader.result);
			};
			reader.readAsDataURL(input.files[0]);
		}
	});

	function isDefaultPicture() {
		return !$picture.attr('src') || ($picture.attr('src') == 'resources/images/camera.png');
	}

	function renderConfirmationPage() {
		/*var parentDisable = $('.parentDisable');
		parentDisable.addClass('visible');*/
		var confirmationPopUp = $('#ConfirmationPopUp');
		confirmationPopUp.addClass('visible');
		$buttonCancelDelete.show();
		$buttonConfirmDelete.show();

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
			return false;
		});
	}
	

	function renderConfirmationGroupPage() {
		/*var parentDisable = $('.parentDisable');
		parentDisable.addClass('visible');*/
		var confirmationPopUp = $('#ConfirmationPopUpGroup');
		confirmationPopUp.addClass('visible');
		$buttonCancelDelete.show();
		$buttonConfirmDelete.show();

		$buttonConfirmDelete.on('click', function () {
			updateGroup(false);
			window.location.hash = 'groups';
			loadGroups();
		});

		$buttonCancelDelete.on('click', function () {
			var confirmationPopUp = $('#ConfirmationPopUpGroup');
			confirmationPopUp.removeClass('visible');
			var parentDisable = $('.parentDisable');
			parentDisable.removeClass('visible');
			$buttonEditGroup.show();
			$buttonDeleteGroup.show();
			return false;
		});
	}
	

	function updateEvent(deleted) {
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
			"target-group": $eventDescription.text(),
			"cost": $eventCost.val(),
			"currency": {
				"id": getCurrencyId(), "name": $eventCostCurrency.val()
			},
			"picture": isDefaultPicture() ? "" : $picture.attr('src')
		};
		deleted ? saveEvent(eventJson, "updateEvent") : saveEvent(eventJson, "deleteEvent");
	}

	function updateGroup(deleted){
		var groupJson = {
			"id" : $singlePage.find('.GroupPage').attr('data-id'),
			"name" : $singlePageTitle.val(),
			"description" : $groupDescription.text(),
			"location": $groupLocation.text()
		}
		deleted ? saveGroup(groupJson,"updateGroup") : saveGroup(groupJson,"deleteGroup");
	}

	function resetSinglePage() {
		$eventCategoriesMultiselect = $singlePage.find('.btn-group');
		//reset inputs
		$singlePage.find(".reset").val("");
		// Empty description

		$eventPage.hide();
		$groupPage.hide();

		$eventDescription.empty();
		$groupDescription.empty();
		$groupLocation.empty();
		$eventLocation.empty();
		$eventTargetGroup.empty(),
		$eventCost.val("");
		$eventCostCurrency.val("");
		$buttons.hide();
		$errors.hide();
		$pictureParent.hide();

		$picture.attr('src', "resources/images/camera.png");
	}

	resetSinglePage();
	
	var $showArchiveCheckbox = $('#show-archive');
	var archiveDateFilters = $('#archiveDateFilters');
	$showArchiveCheckbox.on('click', function () {
		if ($showArchiveCheckbox.find("input").prop("checked")) {
			showArchiveDateFilters();
			makeEventPageUneditable();
			makeGroupPageUneditable();
			hideShowSinglePageAllButtons(true);
		}
		else {
			archiveDateFilters.addClass('hidden');
			$archiveFrom.val(null);
			$archiveTo.val(null);
			loadEvents();
			makeEventPageEditable();
			makeGroupPageEditable();
			hideShowSinglePageAllButtons(false);
		}
	});

	var $archiveFrom = $("#datepickerFrom");
	var $archiveTo = $("#datepickerTo");

	function showArchiveDateFilters() {
		archiveDateFilters.removeClass('hidden');
		populateArchveDateDefaultValues();
		loadEvents("archivedEvents", $archiveFrom.val(), $archiveTo.val());
	}

	$archiveFrom.datepicker({
		maxDate: '+0m +0w +0d',
		dateFormat: 'dd-mm-y',
		onSelect: function (selected, evnt) {
			if (selected != $archiveTo.val() != null) {
				if ($archiveFrom.datepicker("getDate") > $archiveTo.datepicker("getDate")) {
					$archiveFrom.val($archiveTo.val());
				}
				loadEvents("archivedEvents", $archiveFrom.val(), $archiveTo.val());
			}
		}
	});

	$archiveTo.datepicker({
		maxDate: '+0m +0w +0d',
		dateFormat: 'dd-mm-y',
		onSelect: function (selected, evnt) {
			if (selected != $archiveFrom.val() != null) {
				if ($archiveTo.datepicker("getDate") < $archiveFrom.datepicker("getDate")) {
				$archiveTo.val($archiveFrom.val());
				}
				loadEvents("archivedEvents", $archiveFrom.val(), $archiveTo.val());
			}
		}
	});

	function populateArchveDateDefaultValues() {
		var searchTo = new Date();
		var monthAgo = searchTo.getMonth() - 1,
			year = searchTo.getFullYear();
		if (monthAgo < 0) {
			monthAgo = 11;
			year -= 1;
		}
		var searchFrom = new Date(year, monthAgo, searchTo.getDate());

		$archiveFrom.val($.datepicker.formatDate('dd-mm-y', searchFrom));
		$archiveTo.val($.datepicker.formatDate('dd-mm-y', searchTo));
	}

	function hideShowSinglePageAllButtons(isHidden) {
		if (isHidden) {
			$singlePage.find('.SinglePage__all_buttons').addClass("hidden");
		}
		else {
			$singlePage.find('.SinglePage__all_buttons').removeClass("hidden");
		}
	}

	function hideSideBar() {
		$(".side-bar").addClass('not-displayed');
	}

	function showSideBar() {
		$(".side-bar").removeClass('not-displayed');
	}
	
	$('.home').click(function () {
		hideArchiveElements();
		loadEvents();

		showSideBar()
	});

	$allGroupsLink.on("click", function (group) {
		hideArchiveElements();
		group.preventDefault();
		window.location.hash = 'groups';
		loadGroups();

		hideSideBar();
	});

	function hideArchiveElements() {
		$showArchiveCheckbox.find("input").prop("checked", false);
		archiveDateFilters.addClass('hidden');
	}

	$myEventsLink.on("click", function (event) {
		hideArchiveElements();
		event.preventDefault();
		window.location.hash = 'myEvents';
		loadEvents("myEvents");

		showSideBar()
	});
	$.getJSON("eventsCategories", function (data) {
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
	var $loginDropDown = $('.dropdown');
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
	// Single event $singlePage buttons
	$singlePage.on('click', function (e) {
		var top = $(window).scrollTop();
		if ($singlePage.hasClass('visible')) {
			var clicked = $(e.target);
			// If the close button or the background are clicked go to the previous $singlePage.
			if (clicked.hasClass('close') || clicked.hasClass('Overlay')) {
				// Change the url hash with the last used filters.

				$(window).scrollTop(top);
				window.location.hash = oldLocationHash;
			}
		}
	});	
	function loadEvents(type, searchFrom, searchTo) {
		var type = type;
		if (!type) {
			type = "events"
		}
		if(oldLocationHash==="#myEvents") {
			if (type === "archivedEvents") {
				type = "archivedUsersEvents";
			}
			else {
				type = 'myEvents';
			}
		}

		$.getJSON(type, {searchFrom: searchFrom, searchTo: searchTo}, function (data) {
			events = data;
			events.sort(function (a, b) {
				return moment(a.startTime, "DD/MM/YY HH:mm").isAfter(moment(b.startTime, "DD/MM/YY HH:mm"));
			});
			events.forEach(function (item) {
				item.actualStartDate = moment(item.startTime, "DD/MM/YY HH:mm").utc().format("DD MMMM YYYY");
				item.actualStartTime = moment(item.startTime, "DD/MM/YY HH:mm").utc().format("HH:mm");
				item.actualEndDate = moment(item.endTime, "DD/MM/YY HH:mm").utc().format("DD MMMM YYYY");
				item.actualEndTime = moment(item.endTime, "DD/MM/YY HH:mm").utc().format("HH:mm");
			});
			// Call a function to create HTML for all the events.
			displayCategoriesListFilter(type, searchFrom, searchTo);
			generateAlleventsHTML(events);

		});
	}
	function loadGroups(type) {
		var type = type;
		if (!type) {
			type = "groups"
		}

		$.getJSON("groups", function (data) {
			groups = data;
			groups.sort(function (a, b) {
				return moment(a.name).isAfter(moment(b.name));
			});

			generateGroupsHTML("all-groups-list", "groups", groups);
		});

		$.getJSON("myGroups", function (data) {
			myGroups = data;
			myGroups.sort(function (a, b) {
				return moment(a.name).isAfter(moment(b.name));
			});

			generateGroupsHTML("my-groups-list", "myGroups", myGroups);
		});


	}
	

	// An event handler with calls the render function on every hashchange.
	// The render function will show the appropriate content of out $singlePage.
	$(window).on('hashchange', function () {
		render(window.location.hash);
	});

	function getEventId() {
		$.ajax({
			url: "getEventId",
			type: "GET",
			success: function (eventId) {
				renderSingleEventPage(eventId);
			},
			error: function () {
				renderSingleEventPage();
			}
		});
	}

	function getGroupId() {
		$.ajax({
			url: "getGroupId",
			type: "GET",
			success: function (groupId) {
				renderSingleGroupPage(groupId);
			},
			error: function () {
				renderSingleGroupPage();
			}
		});
	}


	// Navigation
	function render(url) {
		// Get the keyword from the url.
		var temp = url.split('/')[0];
		// Hide whatever $singlePage is currently shown.
		checkSession();
		$('.visible').removeClass('visible');
		var singlePage = $('.SinglePage');

		moveSinglePageToCenter();
		$(window).scroll(moveSinglePageToCenter);
		$(window).resize(moveSinglePageToCenter);

		var map = {
			// The "Homepage".
			'': function () {
				// Clear the filters object, uncheck all checkboxes, show all the events
				filters = {};
				oldLocationHash = "";
				$('.filters input[type=checkbox]').prop('checked', false);

				if($showArchiveCheckbox.find("input").prop("checked")) {
					loadEvents("archivedEvents", $archiveFrom.val(), $archiveTo.val())
				}
				else {
					loadEvents();
				}
				renderEventsPage(events);
			},
			"#myEvents": function () {
				if (typeof user == "undefined") {
					window.location.hash = '#';
				}
				filters = {};
				oldLocationHash = "#myEvents";
				if($showArchiveCheckbox.find("input").prop("checked")) {
					loadEvents("archivedEvents", $archiveFrom.val(), $archiveTo.val())
				}

				else {
					loadEvents("myEvents");
				}

				renderEventsPage(events);
			},
			"#groups": function () {
				if (typeof user == "undefined") {
					window.location.hash = '#';
				}
				oldLocationHash = "#groups";
					loadGroups("groups");


				renderGroupsPage(groups);
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
			'#group': function () {
				// Get the index of which group we want to show and call the appropriate function.
				var index = url.split('#group/')[1].trim();
				renderSingleGroupPage(index, groups);
			},
			// Add event
			'#addEvent': function () {
				getEventId();
			},
			'#addGroup': function () {
				getGroupId();
			},
			// Calendar
			'#calendar': function () {
				renderCalendarPage();
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

		function moveSinglePageToCenter() {
			var top = 25 + $(window).scrollTop();
			singlePage.css({
				top: top
			});
		}
	}


	// This function is called only once - on $singlePage load.
	// It fills up the events list via a handlebars template.
	// It recieves one parameter - the data we took from events.json.
	function generateAlleventsHTML(data) {
		$('.total-counter').empty();
		$('.total-counter').append("Total " +data.length + " events");
		var list = $('.all-events .events-list');
		var theTemplateScript = $('#events-template').html();
		var theGroupTemplateScript = $('#groups-template').html();
		//Compile the template​
		var theTemplate = Handlebars.compile(theTemplateScript);
		list.find('li').remove();
		list.append(theTemplate(data));
		if($showArchiveCheckbox.find("input").prop("checked")) {
			list.find(".event-img").addClass("Outdated");
		}
		else {
			list.find(".event-img").removeClass("Outdated");
		}
		// Each events has a data-index attribute.
		// On click change the url hash to open up a preview for this event only.
		// Remember: every hashchange triggers the render function.
		$.each(list.find('li'), function (index, item) {
			$(item).find('span.content').on('click', function (e) {
				e.preventDefault();
				var eventIndex = $(item).data('index');
				window.location.hash = 'event/' + eventIndex;
			});
		});
		showInlineAssignments();
		var header = $('header');

		$('.btn-add-event').on('click', function () {
			window.location.hash = 'addEvent';
			return false;
		});
		

		$('.btn-calendar').on('click', function (event) {
			event.preventDefault();
			window.location.hash = 'calendar';
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
					showInlineAssignments();
				},
				error: function () {
					alert('Something wrong');
				},
				complete: function () {
				}
			});
		});

		attachClickEventToButtons();

	}

	function generateGroupsHTML(listType, dataType, groupsData) {
		$('.total-counter-'+listType).empty();
		$('.total-counter-'+listType).append(groupsData.length + " groups");
		var GroupsList = $('.'+listType);
		var theTemplateScript = $('#groups-template').html();
		//Compile the template​
		var theTemplate = Handlebars.compile(theTemplateScript);
		GroupsList.find('li').remove();
		GroupsList.append(theTemplate(groupsData));

		// Each group has a data-index attribute.
		// On click change the url hash to open up a preview for this group only.
		// Remember: every hashchange triggers the render function.
		$.each(GroupsList.find('li'), function (index, item) {
			$(item).find('span.content').on('click', function (e) {
				e.preventDefault();
				var groupIndex = $(item).data('index');
				window.location.hash = 'group/' + groupIndex;
			});
		});

		showInlineAssignments(); //check!

		showInlineAssignments();
		var header = $('header');

		$('.btn-add-event').on('click', function () {
			window.location.hash = 'addEvent';
			return false;
		});

		$('.btn-add-group').on('click', function () {
			window.location.hash = 'addGroup';
			return false;
		});

		$('.btn-calendar').on('click', function (event) {
			event.preventDefault();
			window.location.hash = 'calendar';
		});

		$('.userInfo').click(function (e) {
			e.preventDefault();
			window.location.hash = 'user/email=' + $(this).groupsData('user');
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
					showInlineAssignments();
				},
				error: function () {
					alert('Something wrong');
				},
				complete: function () {
				}
			});
		});
		

	}

//--------------------------INLINE ASSIGNMENT FUNCTIONALITY------------------------------------//
	function attachClickEventToButtons() {
		$('.assign-action-btn.btn-success').on("click", function (event) {
			var eventId = $(event.target).parent().parent().attr('data-index');
			assignUnassignEvent('assignEventToUser', eventId, inlineAssignmentCallBackFunction);
		});

		$('.assign-action-btn.btn-default').on("click", function (event) {
			var eventId = $(event.target).parent().parent().attr('data-index');
			assignUnassignEvent('unassignEventFromUser', eventId, inlineAssignmentCallBackFunction);
		});
	}

	function showInlineAssignments() {
		var list = $('.all-events .events-list');
		$.each(list.find('li'), function (index, item) {
			alterAssignmentButtonsVisibility($(item));
		});
	}

	function alterAssignmentButtonsVisibility(nodeLI) {
		if (!$showArchiveCheckbox.find("input").prop("checked")) {
			if (user) {
				if (nodeLI.find('.button_group').attr('visit') == 'true') {
					nodeLI.find('.assign-action-btn.btn-success').hide();
					nodeLI.find('.assign-action-btn.btn-default').show();
				} else {
					nodeLI.find('.assign-action-btn.btn-default').hide();
					nodeLI.find('.assign-action-btn.btn-success').show();
				}
				return;
			}
		}
		nodeLI.find('.assign-action-btn.btn-success').hide();
		nodeLI.find('.assign-action-btn.btn-default').hide();
	}

	function alterVisitAttribute(id, value) {
		var li = $('li[data-index=' + id + ']');
		$(li.find('.button_group')).attr('visit', value);
	}

	function inlineAssignmentCallBackFunction(event) {
		var li = $('li[data-index=' + event.id + ']');
		alterVisitAttribute(event.id, $.grep(event.participants, function (e) {
				return e.id == user.id
			}).length != 0);
		alterAssignmentButtonsVisibility(li);
	}

	function assignUnassignEvent(action, id, callback) {
		var json = {
			id: id
		};
		$.ajax({
			url: action,
			data: JSON.stringify(json),
			type: "POST",
			beforeSend: function (xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success: function (event) {
				callback(event)
			},
			error: function () {
			},
			complete: function () {
			}
		});
		return false;
	}
	function assignUnassignGroup(action, id, callback) {
		var json = {
			id: id
		};
		$.ajax({
			url: action,
			data: JSON.stringify(json),
			type: "POST",
			beforeSend: function (xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success: function (group) {
				callback(group)
			},
			error: function () {
			},
			complete: function () {
			}
		});
		return false;
	}


	//$('.assign-action-btn.btn-default').on("click",
	//			assignUnassignEvent('unassignEventFromUser', 
	//                       nodeLI.attr('data-index'), inlineAssignmentCallBackFunction));


//--------------------------END INLINE ASSIGNMENT FUNCTIONALITY------------------------------------//

	function displayCategoriesListFilter(type, searchFrom, searchTo) {
		type = type + "Categories";
			var categoriesFilter = $('.filter-categories');
			var badgeSelector = ".badge";
		//didn't find another way of matching categories and colors
		function getColorsOfCategories() {
			$(badgeSelector).each(function (index, value) {
				var fullClass = $(value).attr('class');
				var filterName = fullClass.slice(badgeSelector.length).toLowerCase();
				filterColors[filterName] = $(value).css('background-color');
			})
		}
		$.getJSON(type, {searchFrom: searchFrom, searchTo: searchTo}, function (data) {
			var categoriesListElementTemplate = $('#categories-list').html();
			var categoriesListElement = Handlebars.compile(categoriesListElementTemplate);
			categoriesFilter.find('div').remove();
			categoriesFilter.append(categoriesListElement(data));

			var checkboxes = $('.filter-categories input[type=checkbox]');
			getColorsOfCategories();
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
		if (!user.active) {
			return;
		}
		var $addEventBtn = $('.btn-add-event');
		$addEventBtn.prop('disabled', false);
		$addEventBtn.addClass('btn-success');
		$addEventBtn.removeAttr('title');
	}
	function enableAddGroupsBtn() {
		if (!user.active) {
			return;
		}
		var $addGroupBtn = $('.btn-add-group');
		$addGroupBtn.prop('disabled', false);
		$addGroupBtn.addClass('btn-success');
		$addGroupBtn.removeAttr('title');
	}

	function disableAddEventsBtn() {
		var $addEventBtn = $('.btn-add-event');
		$addEventBtn.removeClass('btn-success');
		$addEventBtn.prop('disabled', true);
		$addEventBtn.attr('title', 'Please login to create an event');
	}
	function disableAddGroupsBtn() {
		var $addGroupBtn = $('.btn-add-group');
		$addGroupBtn.removeClass('btn-success');
		$addGroupBtn.prop('disabled', true);
		$addGroupBtn.attr('title', 'Please login to create a group');
	}

	function disableArchiveCheckbox() {
		hideArchiveElements();
		$showArchiveCheckbox.find("input").prop('disabled', true);
	}

	function enableArchiveCheckbox() {
		$showArchiveCheckbox.find("input").prop('disabled', false);
	}

	// This function receives an object containing all the event we want to show.
	function renderEventsPage(data) {
		if (typeof data == 'undefined') {
			return;
		}

		if (typeof user == "undefined") {
			disableAddEventsBtn();
			disableArchiveCheckbox();
			disableAddGroupsBtn();
		}
		var page = $('.all-events'),
			allEvents = $('.all-events .events-list > li'),
			allGroups = $('.all-groups .groups-list > li');
		// Hide all the events in the events list.
		allEvents.addClass('hidden');
		allGroups.addClass('hidden');
		$('.add-group-div').addClass('not-displayed');
		$('.add-event-div').removeClass('not-displayed');
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
	function renderGroupsPage(data) {
		if (typeof data == 'undefined') {
			return;
		}

		if (typeof user == "undefined") {
			disableAddEventsBtn();
			disableArchiveCheckbox();
			disableAddGroupsBtn();
		}
		var page = $('.all-groups'),
			allGroups = $('.all-groups .groups-list > li'),
			allEvents = $('.all-events .events-list > li');
		// Hide all the events in the events list.
		allGroups.addClass('hidden');
		allEvents.addClass('hidden');
		$('.add-group-div').removeClass('not-displayed');
		$('.add-event-div').addClass('not-displayed');
		// Iterate over all of the events.
		// If their ID is somewhere in the data object remove the hidden class to reveal them.
		allGroups.each(function () {
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

	var $participantsTemplateScript = $('#groupParticipants').html();
	var $participantsTemplate = Handlebars.compile($participantsTemplateScript);
	var $participants = $('.EventPage__events__list');

	var $groupParticipantsTemplateScript = $('#groupParticipants').html();
	var $groupParticipantsTemplate = Handlebars.compile($groupParticipantsTemplateScript);
	var $groupParticipants = $('.GroupPage__groups__list');

	function saveEvent(eventJson, newEvent) {
		if (!validateEventFields(eventJson)) {
			$errors.show();
			$buttonAddEvent.removeAttr('disabled');
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
			if (user.role !== adminRole && $singlePage.find('.EventPage__owner__name').val() !== user.firstName + " " + user.lastName) {
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

			var eventCost = event.cost.trim();
			if (eventCost <= 0) {
				addErrorListItem("Event cost must be greater then zero");
				valid = false;
			}

			var eventCurrencyId = event.currency.id;
			if (eventCurrencyId === undefined) {
				addErrorListItem("Event currency is not choosen");
				valid = false;
			}
			return valid;
		}

		function addErrorListItem(message) {
			$errors.append('<li>' + message + '</li>');
		}
	}

	function saveGroup(groupJson, newGroup) {
		if (!validateGroupFields(groupJson)) {
			$errors.show();
			$buttonAddGroup.removeAttr('disabled');
			return;
		}
		$.ajax({
			url: newGroup,
			data: JSON.stringify(groupJson),
			type: "POST",
			beforeSend: function (xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success: function () {
				window.location.hash = '#groups';
				loadGroups();
			},
			error: function (error) {
				alert("ERROR!" + error);
			},
			complete: function () {
			}
		});
		function validateGroupFields(group) {
			const GROUP_NAME_LENGTH_MIN = 3;
			const GROUP_NAME_LENGTH_MAX = 40;
			const GROUP_DESCRIPTION_LENGTH_MIN = 10;
			const GROUP_DESCRIPTION_LENGTH_MAX = 250;
			const GROUP_LOCATION_LENGTH_MIN = 7;
			const GROUP_LOCATION_LENGTH_MAX = 100;
			var valid = true;
			$errors.empty();


			var groupName = group.name.trim();
			if (!groupName || groupName.length < GROUP_NAME_LENGTH_MIN || groupName.length > GROUP_NAME_LENGTH_MAX) {
				addErrorListItem("Group name should be greater than " + GROUP_NAME_LENGTH_MIN +
					" and less than " + GROUP_NAME_LENGTH_MAX + " symbols");
				valid = false;
			}
			var description = group.description.trim();
			if (!description || description.length < GROUP_DESCRIPTION_LENGTH_MIN || description.length > GROUP_DESCRIPTION_LENGTH_MAX) {
				addErrorListItem("Description should be greater than " + GROUP_DESCRIPTION_LENGTH_MIN +
					" and less than " + GROUP_DESCRIPTION_LENGTH_MAX + " symbols");
				valid = false;
			}

			var location = group.location.trim();
			if (!location || location.length < GROUP_LOCATION_LENGTH_MIN || location.length > GROUP_LOCATION_LENGTH_MAX) {
				addErrorListItem("Location should be greater than " + GROUP_LOCATION_LENGTH_MIN +
					" and less than " + GROUP_LOCATION_LENGTH_MAX + " symbols");
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
		$eventTargetGroup.attr('contenteditable', true);
		$eventStart.datepicker('enable');
		$eventEnd.datepicker('enable');
		$eventDescription.addClass('editable');
		$eventLocation.addClass('editable');
		$eventTargetGroup.addClass('editable');
		$eventCost.prop('disabled', false);
		$eventCostCurrency.prop('disabled', false);
		$eventCategories.multiselect('enable');
		$eventCategories.multiselect('refresh');
		$pictureUploadPlaceholder.on('click', function () {
			$buttonUploadPicture.click();
			return false;
		});
		$pictureParent.show();
		$picture.attr('title', 'Click Me to change!');
	}

	function makeEventPageUneditable() {
		$singlePageTitle.attr('readonly', true);
		$eventDescription.attr('contenteditable', false);
		$eventDescription.removeClass('editable');
		$eventLocation.attr('contenteditable', false);
		$eventLocation.removeClass('editable');
		$eventTargetGroup.attr('contenteditable', false);
		$eventTargetGroup.removeClass('editable');
		$eventCategories.multiselect('disable');
		$eventCost.prop('disabled', true);
		$eventCostCurrency.prop('disabled', true);
		$eventPage.find('editable').attr('readonly', true);
		$eventStart.datepicker('disable');
		$eventEnd.datepicker('disable');
		$eventCategories.multiselect('refresh');
		$pictureUploadPlaceholder.off('click');
		$picture.attr('title', '');

		if (isDefaultPicture()) {
			$pictureParent.hide();
		}

	}

	function makeGroupPageUneditable() {
		$singlePageTitle.attr('readonly', true);
		$groupDescription.attr('contenteditable', false);
		$groupDescription.removeClass('editable');
		$groupLocation.attr('contenteditable', false);
		$groupLocation.removeClass('editable');
		$groupDescription.attr('contenteditable', false);
		$groupLocation.attr('contenteditable', false);
		$groupPage.find('editable').attr('readonly', true);
		$pictureUploadPlaceholder.off('click');
		$picture.attr('title', '');

		if (isDefaultPicture()) {
			$pictureParent.hide();
		}

	}

	function makeGroupPageEditable() {
		$singlePageTitle.attr('readonly', false);
		$groupDescription.attr('contenteditable', true);
		$groupLocation.attr('contenteditable', true);
		$groupDescription.addClass('editable');
		$groupLocation.addClass('editable');
		$pictureUploadPlaceholder.on('click', function () {
			$buttonUploadPicture.click();
			return false;
		});
		$pictureParent.show();
		$picture.attr('title', 'Click Me to change!');
	}

	function hideCalendarPage() {
		$calendarPage.hide();
		$calendarPage.find('#calendar').hide();
	}

	function showCalendarPage() {
		$calendarPage.show();
		$calendarPage.find('#calendar').show();
	}

	// Opens up a preview for one of the groups.
	// Its parameters are an index from the hash and the groups object.

	function renderSingleGroupPage(index, data, addGroup) {
		resetSinglePage();
		hideCalendarPage();

		$singlePageTitle.attr('placeholder', 'Group title');
		$singlePage.find('.GroupPage').attr("data-id", index);
		$singlePage.find('.UserPage').hide();
		$singlePage.find('.GroupPage').show();

		if (typeof data != 'undefined' && data.length) {
			// Find the wanted event by iterating the data object and searching for the chosen index.
			renderShowGroupPage(data);
		} else {
			if (typeof user === "undefined") {
				window.location.hash = '';
				return;
			}
			renderAddGroupPage();
		}
		// Show the $singlePage.
		$singlePage.addClass('visible');
		//$groupDescription.attr('contentEditable', 'true');

		function renderAddGroupPage() {
			$eventPage.hide();
			populateSinglePageGroupPage($singlePage)
		}


		function renderShowGroupPage(data) {
			data.forEach(function (item) {
				if (item.id == index) {
					$.getJSON("group", {id: item.id}, function (group) {
						$groupParticipants.html($groupParticipantsTemplate(group.groupParticipants));
						populateSinglePageGroupPage($singlePage, group);
						$buttonSubscribe.off();
						$buttonUnSubscribe.off();
						$buttonSubscribe.on('click', function (group) {
							group.preventDefault();
							assignUnassignGroup('assignUserToGroup', item.id, refreshGroupParticipantsList);
						});
						$buttonUnSubscribe.on('click', function (group) {
							group.preventDefault();
							assignUnassignGroup('unassignUserFromGroup', item.id, refreshGroupParticipantsList);
						});
					});
				}
			})
		}
		function refreshGroupParticipantsList(group) {
			$groupParticipants.html($groupParticipantsTemplate(group.groupParticipants));
			allowSubscribeGroup();
		}

		function populateSinglePageGroupPage(singlePage, group) {
			if (typeof group != 'undefined') {
				makeGroupPageUneditable();
				$singlePageTitle.val(group.name);
				$groupDescription.html(linkify(group.description));
				$groupLocation.html(linkify(group.location));
				$groupPageParticipants.show();
				if (group.picture.length) {
					$picture.attr('src', group.picture);
					$pictureParent.show();
				} else {
					$pictureParent.hide();
				}				
				singlePage.find('.GroupPage__owner__name').val(group.owner.firstName + " " + group.owner.lastName);
				
				if (user && (user.id === group.owner.id)) {
					$singlePage.find('.SinglePage__all_buttons').removeClass("hidden");
					$buttonEditGroup.show();
					$buttonDeleteGroup.show();
				}
				allowSubscribeGroup();
			}
			else {
				makeGroupPageEditable();
				$buttonAddGroup.show();
				$groupPageParticipants.hide();
				if(typeof user !== 'undefined'){
					singlePage.find('.GroupPage__owner__name').val(user.firstName + " " + user.lastName);
				}
			}

		}
	}

	function renderSingleEventPage(index, data, addEvent) {
		resetSinglePage();
		hideCalendarPage();

		$singlePageTitle.attr('placeholder', 'Event title');
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
			$groupPage.hide();
			populateSinglePageEventPage($singlePage);

		}

		function isDefaultPicture() {
			return !$picture.attr('src') || ($picture.attr('src') == 'resources/images/camera.png');
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
							assignUnassignEvent('assignEventToUser', item.id, refreshParticipantsList);
							//assignEvent(item.id);
						});

						$buttonCancelAttend.on('click', function (event) {
							event.preventDefault();
							assignUnassignEvent('unassignEventFromUser', item.id, refreshParticipantsList);
							//unassignEvent(item.id);
						});
					});
				}
			});
		}


//-------------------------- ASSIGNMENT FUNCTIONALITY------------------------------------//
		function refreshParticipantsList(event) {
			$participants.html($participantsTemplate(event.participants));
			allowAttendEvent();
		}

//--------------------------END ASSIGNMENT FUNCTIONALITY------------------------------------//

		function populateSinglePageEventPage(singlePage, event) {
			if (typeof event != 'undefined') {
				makeEventPageUneditable();
				$singlePageTitle.val(event.name);
				$eventCategories.val(getEventCategoriesAsList(event.categories));
				$eventPageParticipants.show();
				$eventDescription.text(linkify(event.description));
				$eventLocation.text(linkify(event.location));
				$eventTargetGroup.text(linkify(event.targetGroup));
				if (event.picture.length) {
					$picture.attr('src', event.picture);
					$pictureParent.show();
				} else {
					$pictureParent.hide();
				}
				singlePage.find('.EventPage__owner__name').val(event.owner.firstName + " " + event.owner.lastName);
				var startDate = event.startDateTime;
				$eventStart.val(startDate);
				var endDate = event.endDateTime;
				$eventEnd.val(endDate);
				$eventCost.val(event.cost);
				$eventCostCurrency.val(event.currency.name);
				if (user && (user.id === event.owner.id || user.role === adminRole)) {
					$buttonEdit.show();
					$buttonDelete.show();
				}
				allowAttendEvent();
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

	function allowAttendEvent() {
		$buttonCancelAttend.hide();
		$buttonAttend.hide();
		if (user) {
			if ($participants.find("[data-id=" + user.id + "]").length == 0) {
				$buttonAttend.show();
			} else {
				$buttonCancelAttend.show();
			}
		}
	}
	function allowSubscribeGroup() {
		$buttonSubscribe.hide();
		$buttonUnSubscribe.hide();
		if (user) {
			if ($groupParticipants.find("[data-id=" + user.id + "]").length == 0) {
				$buttonSubscribe.show();
			} else {
				$buttonUnSubscribe.show();
			}
		}
	}

	function getCurrencyId() {
		return $eventCostCurrency.find("option:contains('" + $eventCostCurrency.val() + "')").attr("data-id");
	}

	function renderCalendarPage() {
		$eventPage.hide();
		$userPage.hide();
		$groupPage.hide();
		$singlePage.find(".SinglePage__button").each(function () {
			$(this).hide();
		});

		$singlePageTitle.val('Calendar');
		$singlePageTitle.attr('readonly', true);

		renderFullCalendar(events);

		showCalendarPage();
		$singlePage.addClass('visible');
	}

	function renderFullCalendar(events) {
		var fcEventList = [];
		var fcEvent;
		events.forEach(function (e) {
			fcEvent = {
				id: e.id,
				url: "#event/" + e.id,
				title: e.name,
				start: moment(e.startTime, "DD/MM/YY HH:mm"),
				end: moment(e.endTime, "DD/MM/YY HH:mm"),
				color: getEventColor(e),
				dragScroll: false,
				editable: false
			};
			fcEventList.push(fcEvent);
		});

		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,basicWeek,basicDay'
			},
			//defaultDate: moment().format("YYYY-MM-DD"),
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events: fcEventList,
			firstDay: 1, // first day of the week Monday=1
			titleRangeSeparator: "\u2013",
			timeFormat: ' ' // don't show start time near events' names
		});
		$('#calendar').fullCalendar('today'); // show current day when calendar is opened

		function getEventColor(event) {
			var eventCategory = event.category[0];
			return filterColors[eventCategory.toLowerCase()];
		}
	}

	var $userEmail = $userPage.find('.UserPage__email__input');
	var $userPassword = $userPage.find('.UserPage__password__input');
	var $userFirstName = $userPage.find('.UserPage__name__first');
	var $userLastName = $userPage.find('.UserPage__name__last');

	function renderSingleUserPage(user) {
		resetSinglePage();
		hideCalendarPage();
		$groupPage.hide();
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
			return false;
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
		$myEventsLink.show();
		$allGroupsLink.show();
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
					$myEventsLink.hide();
					$allGroupsLink.hide();
					return;
				}
				setUser(sessionUser);
				$loginDropDown.hide();
				$showArchiveCheckbox.find("input").prop('disabled', false);
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
			disableAddGroupsBtn();
			disableArchiveCheckbox();
			return;
		}
		enableAddEventsBtn();
		enableAddGroupsBtn();
		enableArchiveCheckbox();
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
	$(window).trigger('hashchange');
});