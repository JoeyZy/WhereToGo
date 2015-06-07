<%--@elvariable id="event" type="com.luxoft.wheretogo.model.User"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:template>
    <jsp:body>
        <h1>Enter user data:</h1>
        <form:form commandName="user">
            <table>
                <tr>
                    <td>Login:</td>
                    <td><form:input path="login"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:input path="password"/></td>
                    </td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><form:input path="firstName"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><form:input path="lastName"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add user"/></td>
                </tr>
            </table>
        </form:form>
    </jsp:body>
</t:template>
