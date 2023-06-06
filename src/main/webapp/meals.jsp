<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>Meals</h2>

<li><a href="<%=request.getContextPath()%>/meals">Meals</a></li>
<table>
    <style type="text/css">
        BODY {
            background: white;
        }

        TABLE {
            border-collapse: collapse;
            border: 1px solid black;
            width: 500px;
        }

        TD, TH {
            padding: 3px;
            border: 1px solid maroon;
            text-align: left;
        }
    </style>
    <p><a href="meals?action=new">Add Meal</a></p>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach items="${mealToList}" var="mealTo">
        <tr style="color:${mealTo.excess ? 'red' : 'green'}">
            <td>
                <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/>
            </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=edit&mealId=${mealTo.id}">Edit</a></td>
            <td><a href="meals?action=delete&mealId=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>