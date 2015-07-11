<%--@elvariable id="user" type="com.luxoft.wheretogo.models.User"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
    <jsp:body>
        <h3>User data:</h3>
        <table>
            <tr>
                <td>Email:</td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td>${user.firstName}</td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td>${user.lastName}</td>
            </tr>
        </table>
        <b>Events:</b>
        <ul>
            <c:forEach var="event" items="${user.events}">
                <li><a href="event?id=${event.id}">${event.name}</a></li>
            </c:forEach>
        </ul>
    </jsp:body>
</t:template>
