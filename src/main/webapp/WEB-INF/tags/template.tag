<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="navigationTop" fragment="true" %>
<%@attribute name="navigationLeft" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <link rel="stylesheet" type="text/css" href="resources/css/loginForm.css">
    <script src="/resources/jquery/jquery-2.1.4.js"></script>
    <script src="/resources/js/mainPage.js"></script>
    <!-- Custom css and javascript -->
    <jsp:invoke fragment="head"/>
</head>
<body>
<div id="header">
    <h1 id="welcome">Events!</h1>
    <ul id="menu">
        <li><a href="/">Home</a></li>
        <li><a href="#statistics">Statistics</a></li>
        <li><a href="#gallery">Gallery</a></li>
        <li><a href="#ratings">Ratings</a></li>
        <li><a href="/addUser">Registration</a></li>
        <jsp:include page="../views/loginForm.jsp"/>

    </ul>
</div>
<div id="body">
    <div id="navigation-left">
        <jsp:include page="/categories"/>
    </div>

    <div id="section">
        <jsp:doBody/>
    </div>
</div>
<%--<div id="footer">--%>
<%--Thanks (c)--%>
<%--</div>--%>
</body>
</html>
