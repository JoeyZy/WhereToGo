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
                <a class="navbar-brand my-events" href="#"><img class="icons" src="resources/images/star.png"><span> My Events</span></a>
                <a class="navbar-brand groups" href="#"><img class="icons" src="resources/images/list.png"><span> All Groups</span></a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-left">
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <div class="navbar-btn-container">
                            <button class="btn btn-info btn-calendar" title="Open calendar">
                                <span class="glyphicon glyphicon-calendar"></span>
                                Calendar
                            </button>
                        </div>
                    </li>
                    <li>
                        <div class="navbar-btn-container">
                            <button class="btn btn-block btn-add-group"
                                    title="Please login with active profile to create a group">
                                <span class="glyphicon glyphicon-plus"></span>
                                Group
                            </button>
                        </div>
                    </li>
                    <li>
                        <div class="navbar-btn-container">
                            <button class="btn btn-block btn-add-event"
                                    title="Please login with active profile to create an event">
                                <span class="glyphicon glyphicon-plus"></span>
                                Event
                            </button>
                        </div>
                    </li>
                    <li>
                        <a class="userInfo" href="${pageContext.request.contextPath}/user"></a>
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
                            <a <%--class="logout" --%>href="javascript:formSubmit()">Logout</a>
                        </c:if>
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
        <div class="nav-left">
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
            <div class="total-counter"></div>
            <script id="events-template" type="x-handlebars-template">
                {{#each this }}
                <li data-index="{{id}}" class="small_event">
                    <%--<a href="#" class="event-photo"><img src="{{image.small}}" height="130" alt="{{name}}"/></a>--%>

                        <span class="content">
			                <div class="event-box-img">
                                <div class="event-img {{category}}" style="background-image:url({{picture}})">
                                    <div class="event-box-content">
                                        <h2 class="event-box-title" title="{{name}}"><span> {{name}} </span></h2>

                                        <div class="event-box-location-and-by">
                                            <div class="location" title="{{location}}"><img class="icons"
                                                                                        src="resources/images/location.png"> {{location}}</div>
                                            <div class="created-by" title="{{owner}}"><img class="icons"
                                                                                       src="resources/images/black.png"><span> Created by </span>{{owner}}</div>
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
        </ul>
    </div>

    <div class="all-groups page">

        <ul class="groups-list">
            <div class="total-counter-groups" id="groups-hide"></div>
            <script id="groups-template" type="x-handlebars-template">
                {{#each this }}
                <li data-index="{{id}}" class="small_group">

                        <span class="content">
			                <div class="group-box-img">
                                <div class="event-img {{Nature}}" style="background-image:url({{picture}})">
                                    <div class="group-box-content">
                                        <h2 class="group-box-title" title="{{name}}"><span> {{name}} </span></h2>

                                        <div class="group-box-location-and-by">
                                            <div class="location" title="{{location}}"><img class="icons"
                                                                                            src="resources/images/location.png"> {{location}}</div>
                                            <div class="created-by" title="{{owner}}"><img class="icons"
                                                                                           src="resources/images/black.png"><span> Created by </span>{{owner}}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="category_color Nature"></div>

                            <div class="description" title="{{description}}">
                               {{description}}
                            </div>
                        </span>
                    <!--<div class="highlight"></div> - with hover buttons became unclickable -->
                        <span class="button_group" visit={{attends}}>
                            <button type="button" class="btn assign-action-btn btn-success">
                                <span class="glyphicon glyphicon-ok assign-action-img"/>
                                    Join
                            </button>
                            <button type="button" class="btn assign-action-btn btn-default">
                                <span class="glyphicon glyphicon-remove assign-action-img"/>
                                    Cancel join
                            </button>
                        </span>
                </li>
                {{/each}}
            </script>
        </ul>
    </div>

    <div class="Page">
        <div class="Overlay"></div>
        <div class="SinglePage">
            <form class="SinglePage__inputForm" novalidate>
                <input class="SinglePage__title reset" placeholder="Event title" required=""/>
                <ul class="SinglePage__inputItemsList UserPage">
                    <li class="SinglePage__inputItem UserPage__email">
                        <label class="SinglePage__inputItem__label"><b>Email:</b></label>
                        <input class="SinglePage__inputItem__inputField UserPage__email__input reset editable"
                               type="email" placeholder="User email" required/>
                    </li>
                    <li class="SinglePage__inputItem UserPage__password">
                        <label class="SinglePage__inputItem__label"><b>Password:</b></label>
                        <input class="SinglePage__inputItem__inputField UserPage__password__input reset editable"
                               type="password" placeholder="User password" required/>
                    </li>
                    <li class="SinglePage__inputItem UserPage__name">
                        <label class="SinglePage__inputItem__label"><b>Name:</b></label>
                        <input class="SinglePage__inputItem__inputField UserPage__name__first reset editable"
                               placeholder="First name" required/>
                        <input class="SinglePage__inputItem__inputField UserPage__name__last reset editable"
                               placeholder="Last name" required/>
                    </li>
                </ul>

                <ul class="SinglePage__inputItemsList Calendar">
                    <div id='calendar'></div>
                </ul>

                <ul class="SinglePage__inputItemsList EventPage" data-id="">
                    <li class="SinglePage__inputItem EventPage__owner">
                        <label class="SinglePage__inputItem__label"><b>User Name:</b></label>
                        <input class="SinglePage__inputItem__inputField EventPage__owner__name" readonly/>
                    </li>
                    <li class="SinglePage__inputItem EventPage__category">
                        <label class="SinglePage__inputItem__label"><b>Category:</b></label>
                        <select id="event-categories">
                            <script id="event-categories-list" type="x-handlebars-template">
                                {{#each this}}
                                <option data-id="{{id}}">{{category}}</option>
                                {{/each}}
                            </script>
                        </select>
                    </li>
                    <li class="SinglePage__inputItem EventPage__start">
                        <label class="SinglePage__inputItem__label"><b>Start:</b></label>
                        <input class="SinglePage__inputItem__inputField reset editable" id="start"
                               placeholder="When event starts"/>
                    </li>
                    <li class="SinglePage__inputItem EventPage__end">
                        <label class="SinglePage__inputItem__label"><b>End:</b></label>
                        <input class="SinglePage__inputItem__inputField reset editable" id="end"
                               placeholder="When event ends"/>
                    </li>
                    <li class="SinglePage__inputItem EventPage__description">
                        <label class="SinglePage__inputItem__label"><b>Description:</b></label>
                        <div contentEditable="false" id="description" title="Description:"></div>
                    </li>
                    <li class="SinglePage__inputItem">
                        <label class="SinglePage__inputItem__label"><b>Location:</b></label>
                        <div contentEditable="false" id="location"></div>
                    </li>
                    <li class="SinglePage__inputItem EventPage__cost">
                        <label class="SinglePage__inputItem__label"><b>Cost:</b></label>
                        <input id="cost" class="form-control" type="number" min="0" max="100000"
                               placeholder="Estimated cost"
                               onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
                        <select id="currencies" class="form-control">
                            <script id="curr-list" type="x-handlebars-template">
                                {{#each this}}
                                <option data-id="{{id}}">{{name}}</option>
                                {{/each}}
                            </script>
                        </select>
                    </li>
                    <li class="SinglePage__inputItem EventPage__participants">
                        <label class="SinglePage__inputItem__label"><b>Participants:</b></label>
                        <ul class="EventPage__events__list">
                        </ul>
                        <script id="participants" type="x-handlebars-template">
                            {{#each this}}
                            <li data-id="{{id}}">{{firstName}} {{lastName}}</li>
                            {{/each}}
                        </script>
                    </li>
                    <li class='event_pic'>
                        <div contentEditable="false" id="picture" class='event_pic'>
                            <img style=''
                                 class="event_pic uploadPlaceholderEvent"/>
                            <input style='display:none;' type='file' class="SinglePage__button--upload"
                                   accept="image/jpeg,image/png"/>
                        </div>
                    </li>
                </ul>
                <%--bla--%>

                <ul class="SinglePage__inputItemsList GroupPage" data-id="">
                    <li class="SinglePage__inputItem GroupPage__owner">
                        <label class="SinglePage__inputItem__label"><b>Owner</b></label>
                        <input class="SinglePage__inputItem__inputField GroupPage__owner__name" readonly/>
                    </li>
                    <li class="SinglePage__inputItem GroupPage__description">
                        <label class="SinglePage__inputItem__label"><b>Description:</b></label>
                        <div contentEditable="false" id="GroupDescription" title="Description:"></div>
                    </li>
                    <li class="SinglePage__inputItem">
                        <label class="SinglePage__inputItem__label"><b>Location:</b></label>
                        <div contentEditable="false" id="GroupLocation"></div>
                    </li>
                    <li class="SinglePage__inputItem GroupPage__participants">
                        <label class="SinglePage__inputItem__label"><b>Participants:</b></label>
                        <ul class="GroupPage__groups__list">
                        </ul>
                        <script id="groupParticipants" type="x-handlebars-template">
                            {{#each this}}
                            <li data-id="{{id}}">{{firstName}} {{lastName}}</li>
                            {{/each}}
                        </script>
                    </li>

                </ul>
                <%--bla--%>
                <ul class="errors"></ul>
                <div class="SinglePage__all_buttons">
                    <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--edit"
                            onclick="this.blur();">Edit event
                    </button>
                    <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--editGroup"
                            onclick="this.blur();">Edit group
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
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--cancelEditing"
                            onclick="this.blur();">Cancel
                    </button>
                    <button type="submit"
                            class="btn btn-action btn-danger SinglePage__button SinglePage__button--cancelEditingGroup"
                            onclick="this.blur();">Cancel
                    </button>
                    <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--attend"
                            onclick="this.blur();">Visit
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
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="resources/js/ba-linkify.js"></script>
<script src="resources/jquery/jquery-ui.js"></script>
<script src="resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.js"></script>
<script src="resources/js/moment.js"></script>
<script src="resources/multiselect-plugin/js/bootstrap-multiselect.js" type="text/javascript"></script>
<script src="resources/js/script.js"></script>
<script src='resources/fullcalendar/fullcalendar.min.js'></script>
<script>
    $('.dropdown-toggle').dropdown();
</script>
</body>
</html>
