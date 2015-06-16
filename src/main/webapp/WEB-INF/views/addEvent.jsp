<%--@elvariable id="category" type="com.luxoft.wheretogo.models.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.models.Event"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.models.User"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:template>
    <jsp:attribute name="head">
        <link rel="stylesheet" href="../../resources/jquery/jquery-ui.css">
        <script src="resources/jquery/jquery-ui.js"></script>
        <script>
            $(document).ready(function () {
                $("#date").datepicker({
                    dateFormat: "dd-mm-yy"
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h1>Enter event data:</h1>
        <form:form commandName="event">
            <table>
                <tr>
                    <td>Owner:</td>
                    <td>${sessionScope.user.firstName} ${sessionScope.user.lastName}</td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><form:input path="name"/></td>
                </tr>
                <tr>
                    <td>Category:</td>
                    <td><form:select path="category">
                        <form:options items="${categories}" itemValue="id"
                                      itemLabel="name"></form:options></form:select>
                    </td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td><form:textarea path="description"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add event"/></td>
                </tr>
            </table>
        </form:form>
    </jsp:body>
</t:template>
