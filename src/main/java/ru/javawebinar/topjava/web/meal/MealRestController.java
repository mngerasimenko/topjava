package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal, int userId) {
        log.info("Create {}", meal);
        checkNew(meal);
        return this.service.create(meal, userId);
    }

    public void update(Meal meal, int id, int userId) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }
}