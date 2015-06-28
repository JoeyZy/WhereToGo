<%--@elvariable id="category" type="com.luxoft.wheretogo.models.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.models.Event"--%>
<%--@elvariable id="user" type="com.luxoft.wheretogo.models.User"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:template>
    <jsp:body>
        <h1></h1>
        <table>
            <tr>
                <td><b>Event:</b></td>
                <td>${event.name}</td>
            </tr>
            <tr>
                <td><b>Owner:</b></td>
                <td>${event.owner.firstName} ${event.owner.lastName}</td>
            </tr>
            <tr>
                <td><b>Begin:</b></td>
                <fmt:formatDate value="${event.startDateTime}" var="startDateTime" pattern="dd/MM/yyyy HH:mm" />
                <td>${startDateTime}</td>
            </tr>
            <tr>
                <td><b>End:</b></td>
                <%--<fmt:formatDate value="${event.endDateTime}" var="endDateTime" pattern="dd/MM/yyyy HH:mm" />--%>
                <td>${event.endDateTime}</td>
            </tr>
        </table>


        <b>Categories:</b>
        <ul>
            <c:forEach var="category" items="${event.categories}">
                <li>${category.name}</li>
            </c:forEach>
        </ul>

        <br/>
        <b>Description:</b>

        <p>${event.description}</p>

        <b>Participants:</b>
        <ul>
            <c:forEach var="participant" items="${event.participants}">
                <li><a href="userInfo?id=${participant.id}">${participant.firstName} ${participant.lastName}</a></li>
            </c:forEach>
        </ul>

        <c:if test="${!currentUserIsParticipant}">
            <div>
                <form action="/addEventToUser">
                    <input id="eventId" name="eventId" type="hidden" value="${event.id}">
                    <input id="add-event-btn" type="submit" value="+ 1">
                </form>
            </div>
        </c:if>
        <c:if test="${event.owner.id == sessionScope.user.id}">
            <div>
                <form action="/addEvent">
                    <input id="eventId" name="eventId" type="hidden" value="${event.id}">
                    <input id="add-event-btn" type="submit" value="Edit Event">
                </form>
            </div>
        </c:if>
    </jsp:body>
</t:template>