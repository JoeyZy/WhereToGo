<%--@elvariable id="user" type="com.luxoft.wheretogo.models.User"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:template>
    <jsp:body>
        <h2>Enter user data:</h2>
        <form:form commandName="user">
            <table>
                <tr>
                    <td>Email:</td>
                    <td><form:input path="email"/></td>
                    <td><form:errors path="email"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:input path="password"/></td>
                    <td><form:errors path="password"/></td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><form:input path="firstName"/></td>
                    <td><form:errors path="firstName"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><form:input path="lastName"/></td>
                    <td><form:errors path="lastName"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add user"/></td>
                </tr>
            </table>
        </form:form>
    </jsp:body>
</t:template>
