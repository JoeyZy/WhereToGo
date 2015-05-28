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
    <h1>Events!</h1>
    <ul id="menu">
        <li><a href="#home">Home</a></li>
        <li><a href="#statistics">Statistics</a></li>
        <li><a href="#gallery">Gallery</a></li>
        <li><a href="#ratings">Ratings</a></li>
    </ul>
</div>
<div id="body">
    <jsp:include page="/categories"/>
    <jsp:doBody/>
</div>
<div id="footer">
    Thanks (c)
</div>
</body>
</html>
