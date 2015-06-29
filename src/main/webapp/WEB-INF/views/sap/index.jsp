<!DOCTYPE html>
<html>

<head lang="en">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">

    <title>Events!</title>

    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400" rel="stylesheet">
    <link href="../../../resources/css/new_styles.css" rel="stylesheet">
</head>

<body>

<header class="compact">
    <h1><a class="home" href="#">Events!</a></h1>
</header>

<div class="main-content">

    <div class="all-events page">

        <div class="filters">
            <form>

                <div class="filter-criteria">
                    <span>Category</span>
                    <label><input type="checkbox" name="category" value="nature">Nature</label>
                    <label><input type="checkbox" name="category" value="movie">Movie</label>
                    <label><input type="checkbox" name="category" value="theatre">Theatre</label>
                    <label><input type="checkbox" name="category" value="pub">Pub</label>
                    <label><input type="checkbox" name="category" value="sport">Sport</label>
                    <label><input type="checkbox" name="category" value="other">Other</label>
                </div>

                <div class="filter-criteria">
                    <span>Date</span>
                    <label><input type="checkbox" value="1" name="camera">Today</label>
                    <label><input type="checkbox" value="7" name="camera">Week</label>
                    <label><input type="checkbox" value="30" name="camera">Month</label>
                </div>

                <button>Clear filters</button>

            </form>

        </div>

        <ul class="events-list">
            <script id="events-template" type="x-handlebars-template">​
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
                <button>Open event</button>
                <%--<p class="event-price">{{price}}$</p>--%>
                <div class="highlight"></div>
            </li>
            {{/each}}
            </script>
             
        </ul>

    </div>


    <div class="single-event page">

        <div class="overlay"></div>

        <div class="preview-large">
            <h3>Single event view</h3>
            <%--<img src=""/>--%>
            <form class="event-information">
                <ul>
                    <li>
                        <label><b>Owner:</b></label>
                        <input id="owner"/>
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
                        <textarea id="description" title="Description:"></textarea>
                    </li>
                </ul>
            </form>

            <span class="close">x</span>
        </div>

    </div>

    <div class="error page">
        <h3>Sorry, something went wrong :(</h3>
    </div>

</div>


<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.min.js"></script>
<script src="../../../resources/js/moment.js"></script>
<script src="../../../resources/js/script.js"></script>


</body>
</html>
