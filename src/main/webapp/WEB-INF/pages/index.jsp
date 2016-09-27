<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <%--Spring Security csrf meta data--%>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
        <%--Google Maps API meta data for Android & iOS--%>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <title>WhereToGo</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400" rel="stylesheet">
    <link rel="stylesheet" href="../../resources/jquery/jquery-ui.css">
    <link rel="stylesheet" href="../../resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.css">
    <link rel="stylesheet" href="../../resources/multiselect-plugin/css/bootstrap-multiselect.css" type="text/css"/>
    <link href='../../resources/css/fullcalendar.min.css' rel='stylesheet'/>
    <link href='../../resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>
    <link href="../../resources/css/new_styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" type="text/css" href="../../resources/dropdown-checkboxes-plugin/css/drop_down.css">
        <link rel="stylesheet" type="text/css" href="../../resources/comments-plugin/css/jquery-comments.css">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
</head>
<body>
<header>
    <nav class="navbar navbar-default navbar-inverse" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand home" href="#"><img class="icons" src="resources/images/list.png"><span> All Events</span></a>
                <a class="navbar-brand my-events" href="#" style="display: none"><img class="icons" src="resources/images/star.png"><span> My Events</span></a>
                <a class="navbar-brand groups" href="#" style="display: none"><img class="icons" src="resources/images/list.png"><span> Groups</span></a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-left">
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="user-dropdown">
                        <ul class="profile-menu dropdown-toggle" style="display: none;" data-toggle="dropdown" data-user="" >
                            <li>
                                <img class="profile-photo">
                                <a class="userInfo"></a><span class="caret"></span>
                            </li>
                        </ul>
                        <ul class="dropdown-menu" id = "prof-menu">
                            <li>
                                <a class="profile" >Profile</a>
                            </li>
                            <li>
                                <c:url value="/j_spring_security_logout" var="logoutUrl" />
                                <!-- csrt for log out-->
                                <form action="${logoutUrl}" method="post" id="logoutForm">
                                    <input type="hidden"
                                           name="${_csrf.parameterName}"
                                           value="${_csrf.token}" />
                                </form>
                                <script>
                                    function formSubmit() {
                                        document.getElementById("logoutForm").submit();
                                    }
                                </script>
                                <c:if test="${pageContext.request.userPrincipal.name != null}">
                                    <a href="javascript:formSubmit()">Logout</a>
                                </c:if>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-user=""><b>Login</b> <span
                                class="caret"></span></a>
                        <ul id="login-dp" class="dropdown-menu">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <form class="form" role="form" method="post" accept-charset="UTF-8"
                                              action="<c:url value='/j_spring_security_check' />" id="login-nav">
                                            <div class="form-group">
                                                <label class="sr-only" for="userEmail">Email address</label>
                                                <input type="email" class="form-control" id="userEmail"
                                                       name="userEmail" placeholder="Email address" required>
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only" for="userPassword">Password</label>
                                                <input type="password" class="form-control" id="userPassword"
                                                       name="userPassword" placeholder="Password" required>
                                            </div>
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-info btn-block">Sign in</button>
                                            </div>
                                            <input type="hidden"
                                                   name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        </form>
                                    </div>
                                    <div class="col-md-12 bottom">
                                        <button class="btn btn-info btn-block btn-add-user">Register</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="main-content">
    <div class="all-events page">
        <div class="nav-left side-bar">
            <div class="filters bs-callout bs-callout-default">
                <form>
                    <lable>Categories</lable>
                    <div class="filter-criteria">
                        <div class="filter-categories">
                            <script id="categories-list" type="x-handlebars-template">
                                {{#each this}}
                                <div class="categories-common {{category}}">
                                    <label><input type="checkbox" name="category"
                                                  value="{{category}}">{{category}}</label><span class="badge
                                    {{category}}">{{counter}}</span>
                                </div>
                                {{/each}}
                            </script>
                        </div>
                    </div>
                </form>
            </div>

            <div class="archives bs-callout bs-callout-default">
                <form>
                    <lable>Archive</lable>
                    <div class="filter-criteria">
                        <div class="categories-common Archive">
                            <div id="show-archive">
                                <label><input type="checkbox" class="show-archive-input"/>Show old events</label>
                            </div>

                            <div id="archiveDateFilters" class="archive date filters hidden">
                                <form class="ui-datepicker">
                                    <p class="archived datepicker">FROM <input type="text" id="datepickerFrom" readonly="readonly"></p>
                                    <p class="archived datepicker">TO <input type="text" id="datepickerTo" readonly="readonly"></p>
                                </form>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </div>

        <ul class="events-list">
            <div class="clearfix">
                <div class="total-counter"></div>
                <a href="" class="button-back prev" style="display: none;">Back</a>


                <div class="navbar-btn-container calendar">
                    <button class="btn btn-info btn-calendar" title="Open calendar">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </button>
                </div>
                <div class="navbar-btn-container show-all">
                    <button class="btn btn-show-all" title="Show all events">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </button>
                </div>
                        <div class="navbar-btn-container add-event-div">
                            <button class="btn btn-block btn-add-event"
                                    title="Please login with active profile to create an event">
                                <span class="glyphicon glyphicon-plus"></span>
                                Event
                            </button>
                        </div>
                <div class="navbar-btn-container add-group-div not-displayed">
                    <button class="btn btn-block btn-add-group btn-visit"
                            title="Please login with active profile to create a group">
                        <span class="glyphicon glyphicon-plus"></span>
                        Group
                    </button>
                </div>
            </div>
                <script id="events-template" type="x-handlebars-template">
                {{#each this }}
                <li data-index="{{id}}" class="small_event">
                    <%--<a href="#" class="event-photo"><img src="{{image.small}}" height="130" alt="{{name}}"/></a>--%>
                        <span class="content">
			                <div class="event-box-img">
                                <div class="event-img {{category}}" style="background-image:url({{picture}})">
                                    <div class="event-box-content">
                                        <h2 class="event-box-title" title="{{name}}"><span> {{name}} </span></h2>

                                        <div class = "event-target-group" title="{{targetGroup}}">{{targetGroup}}</div>
                                        <div class="event-box-location-and-by">
                                            <div class="location" title="{{location}}"><img class="icons"
                                                                                        src="resources/images/location.png"> {{location}}</div>
                                            <div class="created-by" title="{{owner}}"><img class="icons"
                                                                                       src="resources/images/black.png"><span> Created by </span>{{owner.firstName}} {{owner.lastName}}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="category_color {{category}}"></div>

                            <div class="start">
                                <span>START <br></span>
                                <div class="start_date"><img class="icons" src="../../resources/images/calendar.png"> {{actualStartDate}}</div>
                                <div class="start_time"><img class="icons" src="../../resources/images/time.png"> {{actualStartTime}}</div>
                            </div>

                            <div class="end">
                                <span>END <br></span>
                                <div class="end_date"><img class="icons" src="../../resources/images/calendar.png">  {{actualEndDate}}</div>
                                <div class="end_time"><img class="icons" src="../../resources/images/time.png">  {{actualEndTime}}</div>

                            </div>
                        </span>
                    <!--<div class="highlight"></div> - with hover buttons became unclickable -->
                        <span class="button_group" visit={{attends}}>
                            <button type="button" class="btn assign-action-btn btn-success btn-visit">
                                <span class="glyphicon glyphicon-ok assign-action-img"/>
                                    Visit
                            </button>
                            <button type="button" class="btn assign-action-btn btn-default">
                                <span class="glyphicon glyphicon-remove assign-action-img"/>
                                    Cancel visit
                            </button>
                        </span>
                </li>
                {{/each}}
            </script>
        </ul>
        <ul class="pagination-page"></ul>
    </div>

    <div class="all-groups page">

        <ul class="groups-list all-groups-list">
            <div id="all-groups-header" class="groups-header"><h4>All Groups</h4></div>
            <div class="total-counter-all-groups-list" id="groups-hide"></div>
            <script id="groups-template" type="x-handlebars-template">
                {{#each this }}
                <li data-index="{{id}}" class="small_group">
                    <div class="place_for_acc">
                    </div>
                    <div class="single_group">
                        <div class="single_group_header">
                                <span class="content group-content">
                                    <h2 class="group-box-title" title="{{name}}"><span> {{name}} </span></h2>
                                </span>
                        </div>
                        <div class="single_group_body">
                            <div class="single_group_info">
                                <div class="single_group_info_block">
                                    <p><b>Participants:</b> {{groupParticipants.length}}</p>
                                    <p class = "single_group_owner"><b>Owner:</b> {{owner.firstName}}</p>
                                </div>
                                <div class="single_group_img_block">
                                    <img class="single_group_picture" src="{{picture}}">
                                </div>
                            </div>
                            <div class = "single_group_extra_info">
                                <span class = "single_group_location"><b>Location:</b> {{location}}</span>
                            </div>
                        </div>
                    </div>
                </li>
                {{/each}}
            </script>
        </ul>

        <ul class="groups-list my-groups-list">
            <div id="my-groups-header" class="groups-header"><h4>My Groups</h4></div>
            <div class="total-counter-my-groups-list" id="groups-hide1"></div>
            <!--<script id="groups-template" type="x-handlebars-template">
                {{#each this }}
                <li data-index="{{id}}" class="small_group">
                    <div>
                        <span class="content group-content">
                            <h2 class="group-box-title" title="{{name}}"><span class="clickGroupName"> {{name}} </span></h2>

                        </span>
                    </div>
                </li>
                {{/each}}
            </script>-->
        </ul>
    </div>

    <div class="Page">
        <div class="Overlay"></div>
        <div class="SinglePage">
            <form class="SinglePage__inputForm" novalidate>
                <input class="SinglePage__title reset" placeholder="Event title" required=""/>
                <ul class="SinglePage__inputItemsList UserPage">
                    <li class="user_top">
                        <div class="user_top_left">
                            <div class="user_top_left_top">
                                <div class="user_top_left_top_name">
                                    <span><b>Name*</b></span><br>
                                    <input class="UserPage__name__first reset editable form-control"
                                           placeholder="First name" required/>
                                    <input class="UserPage__name__last reset editable form-control"
                                           placeholder="Last name" required/>
                                </div>
                                <div class="user_top_left_top_email">
                                    <span><b>Email*</b></span><br>
                                    <input class="UserPage__email__input reset editable form-control"
                                           type="email" placeholder="User email" required/>
                                </div>
                            </div>
                            <div class="user_top_left_middle user_blocks">
                                <div class="user_top_left_middle_password">
                                    <span><b>Password*</b></span><br>
                                    <input class="UserPage__password__input reset editable form-control"
                                           type="password" placeholder="User password" required/>
                                </div>
                                <div class="user_top_left_middle_password_confirm">
                                    <span><b>Password confirmation*</b></span><br>
                                    <input class="UserPage__password__confirm__input reset editable form-control"
                                           type="password" placeholder="User password" required/>
                                </div>
                            </div>
                            <div class="user_top_left_bottom">
                                <div class="user_top_left_bottom_phone user_blocks">
                                    <div class="user_top_phone_number">
                                        <span><b>Phone number</b></span><br>
                                        <input class="UserPage__phone__input reset editable form-control"
                                               placeholder="Phone number" required/>
                                    </div>
                                    <div class="user_top_view_birthday">
                                        <span><b>Birthday</b></span><br>
                                        <input class="UserPage__birthday UserPage__birthday__holder reset editable form-control"
                                               readonly/>
                                    </div>
                                </div>
                                <div class="user_top_left_bottom_birthday user_blocks">
                                    <span><b>Birthday</b></span><br>
                                    <%--<input class="UserPage__birthday UserPage__birthday__holder reset editable form-control"--%>
                                           <%--readonly/>--%>
                                    <style>
                                        .custom-combobox {
                                            position: relative;
                                            display: inline-block;
                                        }
                                        .custom-combobox-toggle {
                                            position: absolute;
                                            top: 0;
                                            bottom: 0;
                                            margin-left: -1px;
                                            padding: 0;
                                        }
                                        .custom-combobox-input {
                                            margin: 0;
                                            padding: 5px 10px;
                                        }
                                    </style>
                                    <script>
                                        $( function() {
                                            var i = 1;
                                            function generateMonth(){
                                                var i = 1, m = $("#month");
                                                for(;i<=12;i++){
                                                    var opt = $("<option></option>").val(i)
                                                            .text(i);
                                                    m.append(opt);
                                                }
                                            }
                                            function generateDay(){
                                                var i = 1,
                                                        m = $("#day");
                                                for(;i<=31;i++){
                                                    var opt = $("<option></option>").val(i)
                                                            .text(i);
                                                    m.append(opt);
                                                }

                                            }
                                            function generateYear(){
                                                var i = 1940,
                                                        m = $("#year");
                                                for(;i<=2016;i++){
                                                    var opt = $("<option></option>").val(i)
                                                            .text(i);
                                                    m.append(opt);
                                                }
                                            }
                                            generateMonth();
                                            generateDay();
                                            generateYear();
                                            $.widget( "custom.combobox", {
                                                _create: function() {
                                                    var local = i;
                                                    i++;
                                                    this.wrapper = $( "<span>" )
                                                            .addClass( "custom-combobox" )
                                                            .css("margin-left", "40px")
                                                            .css("width", "120px")
                                                            .insertAfter( this.element );

                                                    this.element.hide();
                                                    this._createAutocomplete(local);
                                                    this._createShowAllButton();
                                                },

                                                _createAutocomplete: function(local) {
                                                    var selected = this.element.children( ":selected" ),
                                                            value = selected.val() ? selected.text() : "";

                                                    this.input = $( "<input>" )
                                                            .appendTo( this.wrapper )
                                                            .val( value )
                                                            .attr( "title", "" )
                                                            .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                                                            .css("width","inherit")
                                                            .autocomplete({
                                                                delay: 0,
                                                                minLength: 0,
                                                                source: $.proxy( this, "_source" )
                                                            })
                                                            .tooltip({
                                                                classes: {
                                                                    "ui-tooltip": "ui-state-highlight"
                                                                }
                                                            });
                                                    if(local==1) this.input.addClass("UserPage__birthday__day").attr("placeholder", "Day");
                                                    else if(local==2) this.input.addClass("UserPage__birthday__month").attr("placeholder", "Month");
                                                    else if (local==3) this.input.addClass("UserPage__birthday__year").attr("placeholder", "Year");
                                                    this._on( this.input, {
                                                        autocompleteselect: function( event, ui ) {
                                                            ui.item.option.selected = true;
                                                            this._trigger( "select", event, {
                                                                item: ui.item.option
                                                            });
                                                        },

                                                        autocompletechange: "_removeIfInvalid"
                                                    });
                                                },

                                                _createShowAllButton: function() {
                                                    var input = this.input,
                                                            wasOpen = false;

                                                    $( "<a>" )
                                                            .attr( "tabIndex", -1 )
                                                            .attr( "title", "Show All Items" )
                                                            .tooltip()
                                                            .appendTo( this.wrapper )
                                                            .button({
                                                                icons: {
                                                                    primary: "ui-icon-triangle-1-s"
                                                                },
                                                                text: false
                                                            })
                                                            .removeClass( "ui-corner-all" )
                                                            .addClass( "custom-combobox-toggle ui-corner-right" )
                                                            .on( "mousedown", function() {
                                                                wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                                                            })
                                                            .on( "click", function() {
                                                                input.trigger( "focus" );

                                                                // Close if already visible
                                                                if ( wasOpen ) {
                                                                    return;
                                                                }

                                                                // Pass empty string as value to search for, displaying all results
                                                                input.autocomplete( "search", "" );
                                                            });
                                                },

                                                _source: function( request, response ) {
                                                    var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                                                    response( this.element.children( "option" ).map(function() {
                                                        var text = $( this ).text();
                                                        if ( this.value && ( !request.term || matcher.test(text) ) )
                                                            return {
                                                                label: text,
                                                                value: text,
                                                                option: this
                                                            };
                                                    }) );
                                                },

                                                _removeIfInvalid: function( event, ui ) {

                                                    // Selected an item, nothing to do
                                                    if ( ui.item ) {
                                                        return;
                                                    }

                                                    // Search for a match (case-insensitive)
                                                    var value = this.input.val(),
                                                            valueLowerCase = value.toLowerCase(),
                                                            valid = false;
                                                    this.element.children( "option" ).each(function() {
                                                        if ( $( this ).text().toLowerCase() === valueLowerCase ) {
                                                            this.selected = valid = true;
                                                            return false;
                                                        }
                                                    });

                                                    // Found a match, nothing to do
                                                    if ( valid ) {
                                                        return;
                                                    }

                                                    // Remove invalid value
                                                    this.input
                                                            .val( "" )
                                                            .attr( "title", value + " didn't match any item" )
                                                            .tooltip( "open" );
                                                    this.element.val( "" );
                                                    this._delay(function() {
                                                        this.input.tooltip( "close" ).attr( "title", "" );
                                                    }, 2500 );
                                                    this.input.autocomplete( "instance" ).term = "";
                                                },

                                                _destroy: function() {
                                                    this.wrapper.remove();
                                                    this.element.show();
                                                }
                                            });
                                            $( "#day" ).combobox();
                                            $( "#month" ).combobox();
                                            $( "#year" ).combobox();
                                        } );
                                    </script>

                                    <div class="ui-widget ui-front">
                                        <select id="day">
                                            <option value="">Select one...</option>
                                        </select>
                                        <select id="month">
                                            <option value="">Select one...</option>
                                        </select>
                                        <select id="year">
                                            <option value="">Select one...</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="user_top_right">
                            <div contentEditable="false" id="userPicture" class='user_pic'  data-toggle="tooltip">
                                <img style=''
                                     class="event_pic uploadPlaceholderEvent image"/>
                                <input style='display:none;' type='file' class="SinglePage__button--upload"
                                       accept="image/jpeg,image/png"/>
                            </div>
                        </div>
                    </li>

                    <li class="user_middle">
                        <div class="user_middle_left">
                            <div class="user_middle_left_city user_blocks">
                                 <span><b>City</b></span><br>
                                <input class="form-control" contentEditable="false" id="user-location" title="Location:" type="text"
                                       placeholder="">
                                <div class = "show-location-map" id="show-user-location-map">
                                    <input type = "checkbox" checked="checked" title = "Show on map"/>
                                   Show on map
                                </div>
                            </div>
                            <div class="user_middle__left_interesting_categories user_blocks">
                                <span><b>Interesting categories</b></span><br>
                                <div class="interestingCategoriesMultiselect display_inline"></div>
                                <a href="#" class="activeBackground">
                                    <span class="SinglePage__inputItem__inputField UserPage__Interesting reset activeBackground"></span>
                                </a>
                            </div>
                        </div>
                        <div class="user_middle_right">
                            <div id = "user-location-map-holder">
                                <div class = "location-map" id="user-location-map"></div>
                            </div>
                        </div>

                    </li>

                    <li class="user_bottom">
                        <div class="user_bottom_about user_blocks">
                            <span><b>About me</b></span><br>
                            <textarea placeholder="Short info about yourself" rows="4" cols="40" class="reset editable form-control"></textarea>
                        </div>
                    </li>
                    <%--<li class="SinglePage__inputItem UserPage__email">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Email:*</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__email__input reset editable"--%>
                               <%--type="email" placeholder="User email" required/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__password">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Password:*</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__password__input reset editable"--%>
                               <%--type="password" placeholder="User password" required/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__password__confirm">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Password confirmation:*</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__password__confirm__input reset editable"--%>
                               <%--type="password" placeholder="User password" required/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__name">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Name:*</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__name__first reset editable"--%>
                               <%--placeholder="First name" required/>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__name__last reset editable"--%>
                               <%--placeholder="Last name" required/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__Interesting">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Interesting categories:</b></label>--%>
                            <%--<div class="interestingCategoriesMultiselect display_inline"></div>--%>
                            <%--<a href="#" class="activeBackground">--%>
                            <%--<span class="SinglePage__inputItem__inputField UserPage__Interesting reset activeBackground"></span>--%>
                            <%--</a>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__Location">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>City:</b></label>--%>
                        <%--<input contentEditable="false" id="user-location" title="Location:" type="text"--%>
                               <%--placeholder="">--%>
                        <%--<div class = "show-location-map" id="show-user-location-map">--%>
                            <%--<input type = "checkbox" checked="checked" title = "Show on map"/>--%>
                            <%--Show on map--%>
                        <%--</div>--%>
                        <%--<div id = "user-location-map-holder">--%>
                            <%--<div class = "location-map" id="user-location-map"></div>--%>
                        <%--</div>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__phone">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Phone number:</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__phone__input reset editable"--%>
                               <%--placeholder="Phone number" required/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__birthday">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Birthday:</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField UserPage__birthday UserPage__birthday__holder reset editable"--%>
                                <%--readonly/>--%>
                        <!--<input class="SinglePage__inputItem__inputField UserPage__birthday UserPage__birthday__month reset editable"
                               placeholder="MM" required autocomplete="off" pattern="\d*" step="1" maxlength="2"/>
                        <input class="SinglePage__inputItem__inputField UserPage__birthday UserPage__birthday__year reset editable"
                               placeholder="YYYY" required autocomplete="off" pattern="\d*" step="1" maxlength="4"/>-->

                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem UserPage__about">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>About me:</b></label>--%>
                        <%--<textarea placeholder="Short info about yourself" rows="4" cols="40" class=" UserPage__about__input reset editable"></textarea>--%>
                    <%--</li>--%>
                </ul>

                <ul class="SinglePage__inputItemsList Calendar">
                    <div id='calendar'></div>
                </ul>

                <ul class="SinglePage__inputItemsList EventPage" data-id="">
                    <li class='event_header'>
                        <div class="button_event_edit">
                            <button type="submit" class="btn SinglePage__button SinglePage__button--edit"
                                    onclick="this.blur();">Edit event
                            </button>
                        </div>
                        <div contentEditable="false" id="picture" class='event_pic'>
                            <img style='' class="event_pic uploadPlaceholderEvent image"  data-toggle="tooltip"//>
                            <input style='display:none;' type='file' class="SinglePage__button--upload"
                                   accept="image/jpeg,image/png"/>
                        </div>
                        <div class="event_header_left">
                            <div class="location" title="">
                                <img class="icons" src="resources/images/location.png">
                                <input contentEditable="false" id="event-location" title="Location:" type="text"
                                       placeholder="">
                                <div class = "show-location-map" id="show-event-location-map">
                                    <input type = "checkbox" checked="checked" title = "Show on map"/>
                                    Show
                                </div>
                            </div>
                            <div class="start">
                                <span>START <br></span>
                                <div class="start_date"><img class="icons" src="../../resources/images/calendar.png"><span></span></div>
                                <div class="start_time"><img class="icons" src="../../resources/images/time.png"><span></span></div>
                            </div>
                        </div>
                        <div id = "event-location-map-holder">
                            <div class = "location-map" id="event-location-map"></div>
                        </div>
                        <div class="event_header_center">
                            <div class="created-by" title="">
                                <img class="icons" src="resources/images/black.png">
                                <input class="SinglePage__inputItem__inputField EventPage__owner__name" readonly/>
                            </div>
                            <div class="view_event_category">
                                <span class="icons fa fa-street-view"></span>
                                <span class="view_event_category_inner"></span>
                            </div>
                            <div class="end">
                                <span>END <br></span>
                                <div class="end_date"><img class="icons" src="../../resources/images/calendar.png"><span></span></div>
                                <div class="end_time"><img class="icons" src="../../resources/images/time.png"><span></span></div>

                            </div>
                        </div>
                        <div class="event_header_right">
                            <div class="event_shared_in" title="">
                                <span class="icons fa fa-university"></span>
                                <span class="event_shared_in_inner"></span>
                            </div>
                            <div class="event_cost">
                                <span>COST <br></span>
                                <div class="event_cost_inner"><span class="fa fa-dollar"></span></div>
                            </div>
                        </div>
                    </li>
                    <li class="event_description">
                        <span>DESCRIPTION <br><br></span>
                        <span class="event_description_inner">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</span>
                    </li>
                    <li class="event_participants">
                        <span class="event_participants_inner">PARTICIPANTS <br><br></span>
                        <ul class="EventPage__events__list">
                        </ul>
                        <script id="participants" type="x-handlebars-template">
                            {{#each this}}
                            <li data-id="{{id}}">{{firstName}} {{lastName}}</li>
                            {{/each}}
                        </script>
                    </li>

                <%--<li class="SinglePage__inputItem EventPage__owner">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>User Name:</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField EventPage__owner__name" readonly/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem EventPage__category">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Category:</b></label>--%>
                        <%--<select id="event-categories">--%>
                            <%--<script id="event-categories-list" type="x-handlebars-template">--%>
                                <%--{{#each this}}--%>
                                <%--<option data-id="{{id}}">{{category}}</option>--%>
                                <%--{{/each}}--%>
                            <%--</script>--%>
                        <%--</select>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem EventPage__start">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Start:</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField reset editable" id="start"--%>
                               <%--placeholder="When event starts" readonly/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem EventPage__end">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>End:</b></label>--%>
                        <%--<input class="SinglePage__inputItem__inputField reset editable" id="end"--%>
                               <%--placeholder="When event ends" readonly/>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem EventPage__description">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Description:</b></label>--%>
                        <%--<div contentEditable="false" id="description" title="Description:"></div>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Location:</b></label>--%>
                        <%--<input contentEditable="false" id="event-location" title="Location:" type="text"--%>
                            <%--placeholder="">--%>
                        <%--<div class = "show-location-map" id="show-event-location-map">--%>
                            <%--<input type = "checkbox" checked="checked" title = "Show on map"/>--%>
                            <%--Show on map--%>
                        <%--</div>--%>
                        <%--<div id = "event-location-map-holder">--%>
                            <%--<div class = "location-map" id="event-location-map" style="width: 550px;"></div>--%>
                        <%--</div>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem EventPage__cost">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Cost:</b></label>--%>
                        <%--<input id="cost" class="form-control" type="number" min="0" max="100000"--%>
                               <%--placeholder="Estimated cost"--%>
                               <%--onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>--%>
                        <%--<select id="currencies" class="form-control">--%>
                            <%--<script id="curr-list" type="x-handlebars-template">--%>
                                <%--{{#each this}}--%>
                                <%--<option data-id="{{id}}">{{name}}</option>--%>
                                <%--{{/each}}--%>
                            <%--</script>--%>
                        <%--</select>--%>
                    <%--</li>--%>
                    <%--<li class="SinglePage__inputItem">--%>
                        <%--<label class="SinglePage__inputItem__label"><b>Shared in:</b></label>--%>
                        <%--<select contentEditable="false" id="target-group" class = "form-control" title="Shared in:">--%>
                            <%--<script id="group-list-script" type="x-handlebars-template">--%>
                                <%--{{#each this}}--%>
                                <%--<option data-id="{{id}}">{{name}}</option>--%>
                                <%--{{/each}}--%>
                            <%--</script>--%>
                        <%--</select>--%>
                    <%--</li>--%>
                    <!--<li class='event_pic'>
                        <div contentEditable="false" id="picture" class='event_pic'>
                            <img style=''
                                 class="event_pic uploadPlaceholderEvent"/>
                            <input style='display:none;' type='file' class="SinglePage__button--upload"
                                   accept="image/jpeg,image/png"/>
                        </div>
                    </li>-->
                </ul>
                <%--bla--%>

                <ul class="SinglePage__inputItemsList EditEventPage" data-id="">
                    <li class='event_header'>
                        <div contentEditable="false" id="editEventPicture" class='edit_event_pic'>
                            <img style='' class="event_pic uploadPlaceholderEvent image"/>
                            <input style='display:none;' type='file' class="SinglePage__button--upload"
                                   accept="image/jpeg,image/png"/>
                        </div>
                        <div class="edit_event_header_left">
                            <div>
                                <span>EVENT NAME<br>
                                    <input class = "edit_event_name form-control"/>
                                </span>
                            </div>
                            <div class="edit_event_category">
                                <span>CATEGORY <br>
                                    <select id="event-categories">
                                        <script id="event-categories-list" type="x-handlebars-template">
                                           {{#each this}}
                                           <option data-id="{{id}}">{{category}}</option>
                                           {{/each}}
                                        </script>
                                    </select>
                                </span>
                            </div>
                        </div>
                        <div class="edit_event_header_right">
                            <div>
                                <span class="SinglePage__inputItem__label">START<br>
                                    <input class="SinglePage__inputItem__inputField reset editable" id="start"
                                      placeholder="When event starts" readonly/>
                                </span>
                            </div>
                            <div>
                                 <span class="SinglePage__inputItem__label">END<br>
                                    <input class="SinglePage__inputItem__inputField reset editable" id="end"
                                      placeholder="When event ends" readonly/>
                                 </span>
                            </div>
                        </div>
                    </li>
                    <li class="event_description">
                        <div class="edit_event_left_bottom">
                            <div>
                                <div class="edit_event_location">
                                     <span>LOCATION <br>
                                           <input contentEditable="true" class="form-control"  id="edit-event-location" type="text"
                                                   placeholder="">
                                     </span>
                                     <div class = "edit-show-location-map" id="edit-show-event-location-map">
                                        <input type = "checkbox" checked="checked" title = "Show on map"/>
                                             Show
                                     </div>
                                     <br>
                                </div>
                                <div class="edit_event_cost">
                                    <span>COST
                                          <input id="cost" class="form-control" type="number" min="0" max="100000"
                                                 placeholder=""
                                                 onkeypress='return (event.charCode >= 48 && event.charCode <= 57) || event.keyCode == 8'/>
                                    </span>
                                    <span>CURRENCY
                                       <select id="currencies" class="form-control">
                                          <script id="curr-list" type="x-handlebars-template">
                                                   {{#each this}}
                                             <option data-id="{{id}}">{{name}}</option>
                                                   {{/each}}
                                          </script>
                                       </select>
                                    </span>
                                </div>
                            </div>
                            <div>
                                <span>DESCRIPTION<br>
                                    <textarea  rows="8" cols="60" id="description"></textarea>
                                </span>
                            </div>
                        </div>
                        <div class="edit_event_right_bottom">
                            <span>SHARED IN <br>
                                <select contentEditable="false" id="target-group" class = "form-control" title="Shared in:">
                                    <script id="group-list-script" type="x-handlebars-template">
                                        {{#each this}}
                                        <option data-id="{{id}}">{{name}}</option>
                                        {{/each}}
                                    </script>
                                </select>
                            </span>
                            <div id = "edit-event-location-map-holder">
                                <div class = "location-map" id="edit-event-location-map"></div>
                            </div>
                        </div>
                    </li>
                </ul>

                <ul class="SinglePage__inputItemsList GroupPage" data-id="">
                    <li class="group_left">
                        <div>
                            <div class="group_left_header_left">
                                <span><b>OWNER</b></span> <br>
                                <input class="SinglePage__inputItem__inputField GroupPage__owner__name" readonly/>
                            </div>
                            <div class="group_left_header_right">
                                <span><b>LOCATION</b></span>
                                <input contentEditable="false" id="group-location" title="Location:" type="text"
                                       placeholder="">
                                <div class = "show-location-map" id="show-group-location-map">
                                    <input type = "checkbox" checked="checked" title = "Show on map"/>
                                    Show on map
                                </div>
                            </div>
                        </div>

                        <div>
                            <span><b>DESCRIPTION</b></span><br>
                            <div contentEditable="false" id="GroupDescription" title="Description:"></div>
                        </div>

                        <br>
                        <span><b>PARTICIPANTS</b></span>
                        <div class="GroupPage__groups__list group_participants">
                        </div>
                        <script id="groupParticipants" type="x-handlebars-template">
                            {{#each this}}
                            <div data-id="{{id}}" style="display:inline-block;width:70px; height:70px"><img class="profile-photo moveImgToCenter" style="display:inline-block;width: 60px; height: 60px" src="userImageById?id={{id}}"><figcaption class="nowrapTextForGroupParticipants">{{firstName}} {{lastName}}</figcaption></div>
                            {{/each}}
                        </script>
                    </li>
                    <li class="group_right">
                        <div class="event_pic">
                            <div contentEditable="false" id="groupPicture" class=''  data-toggle="tooltip">
                                <img style=''
                                     class="event_pic uploadPlaceholderEvent image"/>
                                <input style='display:none;' type='file' class="SinglePage__button--upload"
                                       accept="image/jpeg,image/png"/>
                            </div>
                        </div>

                        <div id = "group-location-map-holder">
                            <div class = "location-map" id="group-location-map"></div>
                        </div>
                    </li>

                    <li class="GroupPage__events">
                        <label class="SinglePage__inputItem__label"><b>Events:</b></label>
                        <ul class="GroupPage__groups__events__list ">
                        </ul>
                        <script id="groupEvents" type="x-handlebars-template">
                            {{#each this}}
                            <!--<li data-id="{{id}}"><a class = "event-link" href="#event/{{id}}">{{name}}</a></li>-->

                            <li data-index="{{id}}" class="event-link small_event ">
                                <%--<a href="#" class="event-photo"><img src="{{image.small}}" height="130" alt="{{name}}"/></a>--%>
                                <span class="content">
                                    <div class="event-box-img">
                                        <div class="event-img {{category}}" style="background-image:url({{picture}})">
                                            <div class="event-box-content">
                                                <h2 class="event-box-title prev-title" title="{{name}}"><span> {{name}} </span></h2>

                                                <div class = "event-target-group prev-info" title="{{targetGroup}}">{{targetGroup}}</div>
                                                <div class="event-box-location-and-by prev-info">
                                                    <div class="location" title="{{location}}"><img class="icons"
                                                                                                    src="resources/images/location.png"> {{location}}</div>
                                                    <div class="created-by" title="{{owner}}"><img class="icons"
                                                                                                   src="resources/images/black.png"><span> Created by </span>{{owner.firstName}} {{owner.lastName}}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="category_color {{category}}"></div>

                                    <div class="start prev-info">
                                        <span>START <br></span>
                                        <div class="start_date"><img class="icons" src="../../resources/images/calendar.png"> {{actualStartDate}}</div>
                                        <div class="start_time"><img class="icons" src="../../resources/images/time.png"> {{actualStartTime}}</div>
                                    </div>

                                    <div class="end prev-info">
                                        <span>END <br></span>
                                        <div class="end_date"><img class="icons" src="../../resources/images/calendar.png">  {{actualEndDate}}</div>
                                        <div class="end_time"><img class="icons" src="../../resources/images/time.png">  {{actualEndTime}}</div>

                                    </div>
                                </span>
                                        <!--<div class="highlight"></div> - with hover buttons became unclickable -->
                                <span class="button_group" visit={{attends}}>
                                    <button type="button" class="btn assign-action-btn btn-success">
                                        <span class="glyphicon glyphicon-ok assign-action-img"/>
                                            Visit
                                    </button>
                                    <button type="button" class="btn assign-action-btn btn-default">
                                        <span class="glyphicon glyphicon-remove assign-action-img"/>
                                            Cancel visit
                                    </button>
                                </span>
                            </li>

                            {{/each}}
                        </script>
                    </li>
                    <!--Group image here-->

                </ul>
                <%--<ul class="SinglePage__inputItemsList ">--%>
                    <%--<li class='event_pic'>--%>
                        <%--<div contentEditable="false" id="userPicture" class='user_pic'  data-toggle="tooltip">--%>
                            <%--<img style=''--%>
                                 <%--class="event_pic uploadPlaceholderEvent image"/>--%>
                            <%--<input style='display:none;' type='file' class="SinglePage__button--upload"--%>
                                   <%--accept="image/jpeg,image/png"/>--%>
                        <%--</div>--%>
                    <%--</li>--%>

                <%--</ul>--%>
                <%--bla--%>
                <ul class="errors"></ul>
                <div class="SinglePage__all_buttons">
                    <%--<button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--edit"--%>
                            <%--onclick="this.blur();">Edit event--%>
                    <%--</button>--%>
                    <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--editGroup"
                            onclick="this.blur();">Edit group
                    </button>
                    <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--editUser"
                            onclick="this.blur();">Edit user
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--delete"
                            onclick="this.blur();">Delete event
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--deleteGroup"
                            onclick="this.blur();">Delete group
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--apply"
                            onclick="this.blur();">Save changes
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--applyGroup"
                            onclick="this.blur();">Save changes
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--applyUser"
                            onclick="this.blur();">Save changes
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--cancelEditing"
                            onclick="this.blur();">Cancel
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--cancelEditingGroup"
                            onclick="this.blur();">Cancel
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--cancelEditingUser"
                            onclick="this.blur();">Cancel
                    </button>
                    <button type="submit" class="btn btn-action btn-visit SinglePage__button SinglePage__button--attend"
                            onclick="this.blur();">Visit this event
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--subscribe"
                            onclick="this.blur();">Subscribe
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--unsubscribe"
                            onclick="this.blur();">Unsubscribe
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-info SinglePage__button SinglePage__button--cancelAttend"
                            onclick="this.blur();">Cancel visit
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--addUser"
                            onclick="this.blur();">Add user
                    </button>
                </div>
                <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--addEvent"
                        onclick="this.blur();">Add event
                </button>
                <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--addGroup"
                        onclick="this.blur();">Add group
                </button>
            </form>
            <span class="edit"></span>
            <span class="close"></span>

            <div class="parentDisable">
                <div id="ConfirmationPopUp">
                    <span>Are you sure that you want to delete this event?</span>
                    <div class="confirmDeleteButtons">
                        <button type="submit"
                                class="btn btn-action btn-danger SinglePage__button SinglePage__button--confirmDelete"
                                onclick="this.blur();">Yes
                        </button>
                        <button type="submit"
                                class="btn btn-action btn-info SinglePage__button SinglePage__button--cancelDelete"
                                onclick="this.blur();">No
                        </button>
                    </div>
                </div>
                <div id="ConfirmationPopUpGroup">
                    <span>Are you sure that you want to delete this group?</span>
                    <div class="confirmDeleteButtons">
                        <button type="submit"
                                class="btn btn-action btn-danger SinglePage__button SinglePage__button--confirmDelete"
                                onclick="this.blur();">Yes
                        </button>
                        <button type="submit"
                                class="btn btn-action btn-info SinglePage__button SinglePage__button--cancelDelete"
                                onclick="this.blur();">No
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="ErrorPage">
        <h3>Sorry, something went wrong :(</h3>
    </div>

</div>
<script src='resources/google-maps-api/location.js'></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAtyykOfK3JCWrnV_AQ28U9A9-2WC22ofo&language=en&libraries=places&callback=initGoogleMapsService"
        async defer></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="resources/js/ba-linkify.js"></script>
<script src="resources/jquery/jquery-ui.js"></script>
<script src="resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.js"></script>
<script src="resources/js/moment.js"></script>
<script src="resources/multiselect-plugin/js/bootstrap-multiselect.js" type="text/javascript"></script>
<script src='resources/fullcalendar/fullcalendar.min.js'></script>
<script src="../../resources/comments-plugin/js/jquery-comments.js"></script>
<script src="resources/js/script.js"></script>
<script>
    $('.dropdown-toggle').dropdown();
</script>
</body>
</html>
