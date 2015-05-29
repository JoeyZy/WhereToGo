<%--@elvariable id="category" type="com.luxoft.wheretogo.model.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.model.Event"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
    <jsp:body>
        <div id="page-title"><h1>${event.category.name}</h1></div>
        <h2>Org: ${event.organizer.name} ${event.organizer.lastName}</h2>

        <h3> Description:</h3>

        <p>${event.description}</p>

        <ul>
            <h3>Should be there:</h3>
            <c:forEach var="comer" items="${event.comers}">
                <li>${comer.name} ${comer.lastName}</li>
            </c:forEach>
        </ul>
    </jsp:body>
</t:template>