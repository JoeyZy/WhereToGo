<%--@elvariable id="category" type="com.luxoft.wheretogo.model.Category"--%>
<%--@elvariable id="event" type="com.luxoft.wheretogo.model.Event"--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <jsp:body>
        <link rel="stylesheet" href="../../resources/jquery/jquery-ui.css">
        <script src="../../resources/jquery/jquery-2.1.4.js"></script>
        <script src="../../resources/jquery/jquery-ui.js"></script>
        <form>
            Subject:<br>
            <input type="text" name="subject">
            <br>
            Category:<br>
            <input type="text" name="category">
            <br>
            Date:<br>
            <input type="text" id="datepicker">
            <br>
            Time:<br>
            <input type="time" name="lastname">
        </form>
        <script>
            $(function () {
                $("#datepicker").datepicker({
                    dateFormat: "dd-mm-yy"
                });
            });
        </script>
    </jsp:body>
</t:template>
