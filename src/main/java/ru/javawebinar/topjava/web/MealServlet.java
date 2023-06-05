package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private MealDao dao;

    public void init() {
        dao = new InMemoryMealDao();
        dao.addAll(MealsUtil.getMealsList());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = getAction(request);
        switch (action.toLowerCase()) {
            case "delete":
                delete(request);
                forward = LIST_MEALS;
                break;
            case "edit":
                edit(request);
                forward = INSERT_OR_EDIT;
                break;
            case "insert":
                insert(request);
                forward = INSERT_OR_EDIT;
                break;
            default:
                showList(request);
                forward = LIST_MEALS;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return action == null ? "" : action;
    }

    private void delete(HttpServletRequest request) {
        int mealId = Integer.parseInt(request.getParameter("mealId"));
        dao.delete(mealId);
        log.debug("Delete meal id: {}", mealId);

        request.setAttribute("mealTos", MealsUtil.convertToMealTo(dao.getAll(), MealsUtil.MAX_CALORIES_PER_DAY));
    }

    private void edit(HttpServletRequest request) {
        int mealId = Integer.parseInt(request.getParameter("mealId"));
        Meal meal = dao.getById(mealId);
        log.debug("Edit meal id: {}", mealId);

        request.setAttribute("meal", meal);
    }

    private void insert(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.now(), "", 0);

        log.debug("Insert new meal");

        request.setAttribute("meal", meal);
    }

    private void showList(HttpServletRequest request) {
        request.setAttribute("mealTos", MealsUtil.convertToMealTo(dao.getAll(), MealsUtil.MAX_CALORIES_PER_DAY));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(dateTime, description, calories);
            dao.add(meal);
            log.debug("Added new meal {}", meal);
        } else {
            Meal newMeal = new Meal(Integer.parseInt(mealId), dateTime, description, calories);
            Meal meal = dao.update(newMeal);
            log.debug("Update meal {}", meal);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("mealTos", MealsUtil.convertToMealTo(dao.getAll(), MealsUtil.MAX_CALORIES_PER_DAY));
        requestDispatcher.forward(request, response);
    }
}
