<%--@elvariable id="category" type="com.luxoft.wheretogo.models.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.models.Event"--%>
<%--@elvariable id="user" type="com.luxoft.wheretogo.models.User"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
    <jsp:attribute name="head">
        <link rel="stylesheet" href="../../resources/jquery/jquery-ui.css">
        <link rel="stylesheet" href="../../resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.css">
        <script src="resources/jquery/jquery-ui.js"></script>
        <script src="resources/jquery/addon/ui-timepicker/jquery-ui-timepicker-addon.js"></script>
        <script>
            $(document).ready(function () {
                var startDateTextBox = $('#start');
                var endDateTextBox = $('#end');

                $.timepicker.datetimeRange(
                        startDateTextBox,
                        endDateTextBox,
                        {
                            dateFormat: 'dd M yy',
                            timeFormat: 'HH:mm',
                            start: {}, // start picker options
                            end: {} // end picker options
                        }
                );
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h1>Enter event data:</h1>
        <form:form commandName="event">
            <table>
                <tr>
                    <td>Name:</td>
                    <td><form:input path="name"/></td>
                    <td><form:errors path="name"/></td>
                </tr>
                <tr>
                    <td>Owner:</td>
                    <form:input path="owner" value="${sessionScope.user.id}" type="hidden"/>
                    <td>${sessionScope.user.firstName} ${sessionScope.user.lastName}</td>
                    <td><form:errors path="owner"/></td>
                </tr>
                <tr>
                    <td>Category:</td>
                    <td><form:select path="categories" multiple="true">
                        <form:options items="${categories}" itemValue="id"
                                      itemLabel="name"/></form:select>
                    </td>
                    <td><form:errors path="categories"/></td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td><form:textarea path="description"/></td>
                    <td><form:errors path="description"/></td>

                </tr>
                <tr>
                    <td>Start:</td>
                    <td><form:input id="start" path="startDateTime"/></td>
                    <td><form:errors path="startDateTime"/></td>
                </tr>
                <tr>
                    <td>End:</td>
                    <td><form:input id="end" path="endDateTime"/></td>
                    <td><form:errors path="endDateTime"/></td>
                </tr>
                <c:set var="subvmitButtonText"/>
                <tr>
                    <td colspan="2"><input type="submit" value="${not empty event ? "Edit event" : "Add event"}"/></td>
                </tr>
            </table>
        </form:form>
    </jsp:body>
</t:template>
