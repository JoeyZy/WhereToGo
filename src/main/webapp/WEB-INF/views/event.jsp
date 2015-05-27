<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
    <jsp:body>
        <div id="section">
            <div id="page-title"><h2>${category.name}</h2></div>
            <c:forEach var="event" items="${category.events}">
                <li>${event.name}</li>
            </c:forEach>
        </div>
    </jsp:body>
</t:template>