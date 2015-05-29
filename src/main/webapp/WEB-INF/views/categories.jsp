<%--@elvariable id="category" type="com.luxoft.wheretogo.model.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.model.Event"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="resources/jquery/jquery-2.1.4.js"></script>
<script src="../../resources/js/ListAnimation.js"></script>
<div id="navigation-left">
    <div><h3 style="margin-bottom: -10px">Events:</h3></div>
    <ul>
        <c:forEach var="category" items="${categories}">
            <li>
                <span class="drop-list">${category.name}</span>
                <ul id="event">
                    <c:forEach var="event" items="${category.events}">
                        <li>
                            <a href="/event?name=${event.name}">${event.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </li>

        </c:forEach>
    </ul>
</div>
