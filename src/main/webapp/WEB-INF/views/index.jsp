<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <jsp:body>
        <div id="section">
            <div id="page-title"><h2>Welcome!!!</h2></div>
            <p>
                Go to nowhere to do nothing!
            </p>

            <div>
                <form action="/addEvent">
                    <input id="add-event-btn" type="submit" value="+ Event">
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
