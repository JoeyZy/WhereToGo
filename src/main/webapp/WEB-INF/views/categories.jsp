<%--@elvariable id="category" type="com.luxoft.wheretogo.models.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.models.Event"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <div><h3>Categories:</h3></div>
    <ul>
        <c:forEach var="category" items="${categories}">
            <li>
                <span class="drop-list">${category.name}</span>
                <ul id="event">
                    <c:forEach var="event" items="${category.events}">
                        <li>
                            <a href="/event?id=${event.id}&name=${event.name}">${event.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </li>

        </c:forEach>
    </ul>
</div>
