package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("Create {}", meal);
        checkNew(meal);
        return this.service.create(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        validateUser(meal);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        validateUser(get(id));
        service.delete(id);
    }

    public Collection<Meal> getAll() {
        return service.getAllByUserId(SecurityUtil.authUserId());
    }

    public Collection<MealTo> getAllMealTo() {
        return MealsUtil.getTos(getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}