<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="ru">
<head>
    <link type="text/css" rel="stylesheet"/>

    <li><a href="<%=request.getContextPath()%>/meals">Meals</a></li>

    <title>Add/Edit meal</title>
</head>
<body>
<br/>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>

    ${meal.id == null ? 'Add Meal' : 'Edit Meal'}
    <br/>
    <form method="POST" action='meals' name="frmAddEditMeal">
        <br/>
        Дата : <input
            type="datetime-local" name="dateTime"
            value="${meal.dateTime}"/> <br/>
        Описание : <input
            type="text" name="description"
            value="${meal.description}"/> <br/>
        Калории : <input onkeyup="value=value.replace(/[^\d]/g,'')"
                         type="number" name="calories"
                         value="${meal.calories}"/> <br/>

        <input hidden type="text" name="mealId" value="${meal.id}"/> <br/>
        <button type="submit"> Сохранить</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</body>
</html>