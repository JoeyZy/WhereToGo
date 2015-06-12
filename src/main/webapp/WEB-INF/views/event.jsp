<%--@elvariable id="category" type="com.luxoft.wheretogo.models.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.models.Event"--%>
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
                <td><b>Categories:</b></td>
                <td>${event.categories}</td>
            </tr>
            <tr>
                <td><b>Owner:</b></td>
                <td>${event.owner.firstName} ${event.owner.lastName}</td>
            </tr>
        </table>

        <br/>
        <b>Description:</b>

        <p>${event.description}</p>
    </jsp:body>
</t:template>