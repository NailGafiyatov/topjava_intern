<%--
  Created by IntelliJ IDEA.
  User: nailgafiatov
  Date: 07.07.2022
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Приемы пищи</h2>

<%--<c:if test="${!empty itemList}">--%>
<table class="meal-table">
    <tr>
        <th>Дата</th>
        <th>Название</th>
        <th>Калории</th>
    </tr>


    <%--@elvariable id="mealsTo" type="java.util.List"--%>
    <c:forEach items="${mealsTo}" var="mealTo">
        <tr style="background-color:${mealTo.excess ? 'red' : 'green'}">
            <td><c:out value="${mealTo.dateTime}"/></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td><a href="meals?action=update&Id=${mealTo.Id}">Update</a></td>
            <td><a href="meals?action=delete&Id=${mealTo.Id}">Delete</a></td>
        </tr>
    </c:forEach>

</table>
<%--</c:if>--%>
<p><a href="./">Home</a></p>
<p><a href="meal.jsp">Create Meal</a></p>
</body>
</html>
