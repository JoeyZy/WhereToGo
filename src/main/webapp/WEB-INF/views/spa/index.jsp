<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <title>Events!</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400" rel="stylesheet">
    <link rel="stylesheet" href="../../../resources/jquery/jquery-ui.css">
    <link rel="stylesheet" href="../../../resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.css">
    <link rel="stylesheet" href="../../../resources/multiselect-plugin/css/bootstrap-multiselect.css" type="text/css"/>
    <link href="../../../resources/css/new_styles.css" rel="stylesheet">
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
                    <li><a href="#">Ratings</a></li>
                    <li><a href="#">Gallery</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="userInfo" href="/user"></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-user=""><b>Login</b> <span
                                class="caret"></span></a>
                        <ul id="login-dp" class="dropdown-menu">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <form class="form" role="form" method="post" accept-charset="UTF-8"
                                              action="/login" id="login-nav">
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
                                        <button class="btn btn-info btn-block">Register</button>
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
            <button class="btn btn-success btn-block btn-add-event">Add event</button>
            <div class="filters">
                <form>
                    <div class="filter-criteria">
                        <span>Category</span>

                        <div id="filter-categories">
                            <script id="categories-list" type="x-handlebars-template">
                                {{#each this}}
                                <label><input type="checkbox" name="category" value="{{name}}">{{name}}</label>
                                {{/each}}
                            </script>
                        </div>
                    </div>
                    <div class="filter-criteria">
                        <span>Date</span>
                        <label><input type="checkbox" value="1" name="date">Today</label>
                        <label><input type="checkbox" value="7" name="date">Week</label>
                        <label><input type="checkbox" value="30" name="date">Month</label>
                    </div>
                    <button class="btn btn-info">Clear filters</button>
                </form>
            </div>
        </div>


        <ul class="events-list">
            <script id="events-template" type="x-handlebars-template">
                {{#each this}}
                <li data-index="{{id}}">
                    <%--<a href="#" class="event-photo"><img src="{{image.small}}" height="130" alt="{{name}}"/></a>--%>
                    <h2><a href="#"> {{name}} </a></h2>
                    <ul class="event-description">
                        <li><span>Category: </span>{{category}}</li>
                        <li><span>Owner: </span>{{owner}}</li>
                        <li><span>Begin: </span>{{startTime}}</li>
                        <li><span>End: </span>{{endTime}}</li>
                    </ul>
                    <button class="btn btn-info ">Open event</button>
                    <div class="highlight"></div>
                </li>
                {{/each}}
            </script>
        </ul>
    </div>

    <div class="single-event page">
        <div class="overlay"></div>
        <div class="preview-large">
            <input id="title"/>
            <form class="user-information">
                <ul>
                    <li>
                        <label><b>Email:</b></label>
                        <input id="email" readonly/>
                    </li>
                    <li>
                        <label><b>Events:</b></label>
                        <ul>

                        </ul>
                    </li>
                </ul>
                <button class="btn btn-action btn-info"></button>
            </form>
            <form class="event-information">
                <ul>
                    <li>
                        <label><b>Owner:</b></label>
                        <input id="owner" readonly/>
                    </li>
                    <li>
                        <label><b>Category:</b></label>
                        <select id="event-categories" multiple="multiple">
                            <script id="event-categories-list" type="x-handlebars-template">
                                {{#each this}}
                                <option data-id="{{id}}">{{name}}</option>
                                {{/each}}
                            </script>
                        </select>
                        <input id="event-categories-string" readonly>
                    </li>
                    <li>
                        <label><b>Start:</b></label>
                        <input id="start"/>
                    </li>
                    <li>
                        <label><b>End:</b></label>
                        <input id="end"/>
                    </li>
                    <li>
                        <label><b>Description:</b></label>

                        <div contentEditable="true" id="description" title="Description:"></div>
                    </li>
                </ul>
                <button class="btn btn-action btn-info"></button>
            </form>
            <span class="edit"></span>
            <span class="close"></span>
        </div>
    </div>
    <div class="error page">
        <h3>Sorry, something went wrong :(</h3>
    </div>
</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="../../../resources/jquery/jquery-ui.js"></script>
<script src="../../../resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.js"></script>
<script src="../../../resources/js/moment.js"></script>
<script type="text/javascript" src="../../../resources/multiselect-plugin/js/bootstrap-multiselect.js"></script>
<script src="../../../resources/js/script.js"></script>
<script>
    $('.dropdown-toggle').dropdown();
</script>
</body>
</html>
