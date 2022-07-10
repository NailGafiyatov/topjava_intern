<%--
  Created by IntelliJ IDEA.
  User: nailgafiatov
  Date: 08.07.2022
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new meal</title>
</head>
<body>

<script>
    $(function() {
        $('input[name=dateTime]').datepicker();
    });
</script>

<form method="POST" action='meals' name="frmAddMeal">
    Meal ID : <input type="hidden" readonly="readonly" name="id"
                     value="${mealTo.id}" /> <br />
    Date and Time : <input
        type="text" name="dateTime"
        value="${mealTo.dateTime}" /> <br />
    Description : <input
        type="text" name="description"
        value="${mealTo.description}" /> <br />
    Calories : <input
        type="text" name="calories"
        value="${mealTo.calories}" /> <br />
</form>
<tr>
    <td><a href="meals?action=create&Id=<c:out value="${mealTo.Id}"/>">Add</a></td>
</tr>


</body>
</html>
