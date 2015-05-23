<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <jsp:attribute name="header">
         <h1>Events!</h1>
    </jsp:attribute>
    <jsp:attribute name="navigationTop">
         <ul id="menu">
             <li><a href="/hello">Home</a></li>
             <li><a href="#statistics">Statistics</a></li>
             <li><a href="#gallery">Gallery</a></li>
             <li><a href="#ratings">Ratings</a></li>
         </ul>
    </jsp:attribute>
    <jsp:body>
        <div id="section">
            <div id="page-title"><h2>Nature</h2></div>
            <p>
                Go to nowhere to do nothing!
            </p>
        </div>
    </jsp:body>
</t:template>
</html>
