<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="ru">
<head>
    <link type="text/css" rel="stylesheet"/>

    <title>Add new user</title>
</head>
<body>

<form method="POST" action='MealServlet' name="frmAddMeal">
    Ид : <input readonly
                type="text" name="mealId"
                value="<c:out value="${meal.id}" />"/> <br/>
    Дата : <input
        type="datetime-local" name="dateTime"
        value="<c:out value="${meal.dateTime}" />"/> <br/>
    Описание : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Калории : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>

    <input type="submit" value="Сохранить"/>
</form>
</body>
</html>