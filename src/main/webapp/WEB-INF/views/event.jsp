<%--@elvariable id="category" type="com.luxoft.wheretogo.model.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.model.Event"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
    <jsp:body>
        <h1></h1>
        <table>
            <tr>
                <td><b>Event:</b></td>
                <td>${event.name}</td>
            </tr>
            <tr>
                <td><b>Category:</b></td>
                <td>${event.category.name}</td>
            </tr>
            <tr>
                <td><b>Organizer:</b></td>
                <td>${event.organizer.name} ${event.organizer.lastName}</td>
            </tr>
            <tr>
                <td><b>Category:</b></td>
                <td>${event.category.name}</td>
            </tr>
        </table>

        <br/>
        <b>Description:</b>

        <p>${event.description}</p>
        <c:if test="${not empty event.comers}">
            <ul>
                <h3><b>Should be there:</b></h3>
                <c:forEach var="comer" items="${event.comers}">
                    <li>${comer.name} ${comer.lastName}</li>
                </c:forEach>
            </ul>
        </c:if>
    </jsp:body>
</t:template>