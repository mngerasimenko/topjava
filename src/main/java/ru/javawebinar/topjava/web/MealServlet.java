package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealsInMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

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
    private final MealDao dao;

    public MealServlet() {
        super();
        dao = new MealsInMemoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        switch(action.toLowerCase()){
            case "delete":
                delete(request);
                forward = LIST_MEALS;
                break;
            case "edit":
                edit(request);
                forward = INSERT_OR_EDIT;
                break;
            case "meals":
                showList(request);
                forward = LIST_MEALS;
                break;
            default:
                forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void delete(HttpServletRequest request) {
        int mealId = Integer.parseInt(request.getParameter("mealId"));
        try {
            dao.delete(mealId);
            log.debug("Delete meal id: {}", mealId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("mealTos", MealsUtil.convertToMealTo(dao.getAll()));
    }

    private void edit(HttpServletRequest request) {
        int mealId = Integer.parseInt(request.getParameter("mealId"));
        Meal meal;
        try {
            meal = dao.getById(mealId);
            log.debug("Edit meal id: {}", mealId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("meal", meal);
    }

    private void showList(HttpServletRequest request) {
        request.setAttribute("mealTos", MealsUtil.convertToMealTo(dao.getAll()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), TimeUtil.DATE_TIME_FORMAT);

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(dateTime, description, calories);
            dao.add(meal);
            log.debug("Added new meal {}", meal);
        } else {
            try {
                Meal meal = dao.getById(Integer.parseInt(mealId));
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);

                dao.update(meal);
                log.debug("Update meal {}", meal);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("mealTos", MealsUtil.convertToMealTo(dao.getAll()));
        view.forward(request, response);
    }

}
