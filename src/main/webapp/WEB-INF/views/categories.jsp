<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="navigation-left">
    <div><h3 style="margin-bottom: -10px">Events:</h3></div>
    <ul>
        <c:forEach var="category" items="${categories}">
            <li>${category.name}</li>
        </c:forEach>
    </ul>
</div>
