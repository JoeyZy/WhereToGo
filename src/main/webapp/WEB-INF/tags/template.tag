<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="navigationTop" fragment="true" %>
<%@attribute name="navigationLeft" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
</head>
<body>
<div id="header">
    <jsp:invoke fragment="header"/>
</div>
<div id="navigation-top">
    <jsp:invoke fragment="navigationTop"/>
</div>
<div id="body">
    <div id="navigation-left">
        <div><h3 style="margin-bottom: -10px">Events:</h3></div>
        <ul id="events-list">
            <li>Nature</li>
            <li>Movie</li>
            <li>Theatre</li>
            <li>Pub</li>
            <li>Sport</li>
        </ul>
    </div>
    <jsp:doBody/>
</div>
<div id="footer">
    Thanks!
</div>
</body>
</html>
