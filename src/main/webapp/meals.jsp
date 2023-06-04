<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>Meals</h2>

<table>
    <style type="text/css">
        BODY {
            background: white;
        }

        TABLE {
            border-collapse: collapse;
            border: 1px solid black;
            width: 450px;
        }

        TD, TH {
            padding: 3px;
            border: 1px solid maroon;
            text-align: left;
        }
    </style>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <jsp:useBean id="mealTos" scope="request" type="java.util.List"/>
    <c:forEach items="${mealTos}" var="mealTo">
        <tr style="color:${mealTo.excess ? 'red' : 'green'}">
            <td>
                <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${parsedDateTime}"/>
            </td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td><a href="MealServlet?action=edit&mealId=<c:out value="${mealTo.id}"/>">Edit</a></td>
            <td><a href="MealServlet?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="MealServlet?action=insert">Add Meal</a></p>
</body>
</html>