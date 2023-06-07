package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealDao dao;

    public void init() {
        dao = new InMemoryMealDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (getAction(request)) {
            case "new":
                showNewForm(request, response);
                break;
            case "delete":
                deleteMeal(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listMeals(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return action == null ? "" : action;
    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<MealTo> mealsList = MealsUtil.convertToMealTo(dao.getAll(), MealsUtil.MAX_CALORIES_PER_DAY);
        request.setAttribute("mealToList", mealsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
        request.setAttribute("meal", meal);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meal.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("mealId"));
        Meal meal = dao.getById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meal.jsp");
        request.setAttribute("meal", meal);
        dispatcher.forward(request, response);
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("mealId"));
        dao.delete(id);
        log.debug("Delete meal id={}", id);
        response.sendRedirect(request.getContextPath() + "/meals");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String mealId = request.getParameter("mealId");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(dateTime, description, calories);
            insertMeal(meal);
        } else {
            Meal meal = new Meal(Integer.parseInt(mealId), dateTime, description, calories);
            updateMeal(meal);
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }

    private void insertMeal(Meal newMeal) {
        dao.add(newMeal);
        log.debug("Added new meal {}", newMeal);
    }

    private void updateMeal(Meal meal) {
        dao.update(meal);
        log.debug("Update meal {}", meal);
    }
}
