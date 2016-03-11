<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <title>Events!</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400" rel="stylesheet">
    <link rel="stylesheet" href="resources/jquery/jquery-ui.css">
    <link rel="stylesheet" href="resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.css">
    <link rel="stylesheet" href="resources/multiselect-plugin/css/bootstrap-multiselect.css" type="text/css"/>
    <link href="resources/css/new_styles.css" rel="stylesheet">
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
                <a class="navbar-brand home" href="#">Events!</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <div class="add-event-container">
                            <button class="btn btn-block btn-add-event disabled" title="Please login to create an event">Add event</button>
                        </div>
                    </li>
                    <li>
                        <a class="userInfo" href="${pageContext.request.contextPath}/user"></a>
                    </li>
                    <li>
                        <a class="logout" href="${pageContext.request.contextPath}/logout"></a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-user=""><b>Login</b> <span
                                class="caret"></span></a>
                        <ul id="login-dp" class="dropdown-menu">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <form class="form" role="form" method="post" accept-charset="UTF-8"
                                              action="login" id="login-nav">
                                            <div class="form-group">
                                                <label class="sr-only" for="userEmail">Email address</label>
                                                <input type="email" class="form-control" id="userEmail"
                                                       placeholder="Email address" required>
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only" for="userPassword">Password</label>
                                                <input type="password" class="form-control" id="userPassword"
                                                       placeholder="Password" required>
                                            </div>
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-info btn-block">Sign in</button>
                                            </div>
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
            <div class="filters">
                <form>
                    <span>Categories</span>
                    <div class="filter-criteria">
                         <div id="filter-categories">
                            <script id="categories-list" type="x-handlebars-template">
                                {{#each this}}
                                <div class="categories-common {{name}}">
                                <label><input type="checkbox" name="category" value="{{name}}">{{name}}</label>
                                </div>
                                {{/each}}
                            </script>
                        </div>
                    </div>
                    <button class="btn">Clear filters</button>
                </form>
            </div>
        </div>

        <ul class="events-list">
            <script id="events-template" type="x-handlebars-template">
                {{#each this}}
                <li data-index="{{id}}" class="small_event {{category}}">
                    <%--<a href="#" class="event-photo"><img src="{{image.small}}" height="130" alt="{{name}}"/></a>--%>
                    <h2><a href="#"> {{name}} </a></h2>
                    <ul class="event-description">
                        <li><span>Category: </span>{{category}}</li>
                        <li><span>User Name: </span>{{owner}}</li>
                        <li><span>Begin: </span>{{startTime}}</li>
                        <li><span>End: </span>{{endTime}}</li>
                    </ul>
                    <div class="highlight"></div>
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
                    <li class="SinglePage__inputItem UserPage__events">
                        <label class="SinglePage__inputItem__label"><b>Events:</b></label>
                        <ul class="UserPage__events__list"></ul>
                    </li>
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
                                <option data-id="{{id}}">{{name}}</option>
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
                </ul>
                <ul class="errors"></ul>
                <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--edit"
                        onclick="this.blur();">Edit event
                </button>
                <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--delete"
                        onclick="this.blur();">Delete event
                </button>
                <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--apply"
                        onclick="this.blur();">Save changes
                </button>
                <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--cancelEditing"
                        onclick="this.blur();">Cancel
                </button>
                <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--attend"
                        onclick="this.blur();">I'll be there
                </button>
                <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--addUser"
                        onclick="this.blur();">Add user
                </button>
                <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--addEvent"
                        onclick="this.blur();">Add event
                </button>
            </form>
            <span class="edit"></span>
            <span class="close"></span>

            <div class="parentDisable">
                <div id="ConfirmationPopUp">
                    <span>Are you sure that you want to delete this event?</span>
                    <div class="confirmDeleteButtons">
                        <button type="submit" class="btn btn-action btn-danger SinglePage__button SinglePage__button--confirmDelete"
                                onclick="this.blur();">Yes
                        </button>
                        <button type="submit" class="btn btn-action btn-info SinglePage__button SinglePage__button--cancelDelete"
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
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="resources/js/ba-linkify.js"></script>
<script src="resources/jquery/jquery-ui.js"></script>
<script src="resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.js"></script>
<script src="resources/js/moment.js"></script>
<script type="text/javascript" src="resources/multiselect-plugin/js/bootstrap-multiselect.js"></script>
<script src="resources/js/script.js"></script>
<script>
    $('.dropdown-toggle').dropdown();
</script>
</body>
</html>
