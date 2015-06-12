<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <jsp:body>
        <div id="section">
            <div id="page-title"><h3>Welcome!!!</h3></div>
            <p>
                Go to nowhere to do nothing!
            </p>

            <div>
                <form action="/addEvent">
                    <input type="submit" value="+ Event">
                </form>
            </div>
            <div>
                <input type="submit" value="Take me somewhere!">
            </div>

            <div>
                <form action="/addUser">
                    <input type="submit" value="+ User">
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
