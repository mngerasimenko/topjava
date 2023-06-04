package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsInMemoryDao implements MealDao {

    private static final List<Meal> mealsList = new CopyOnWriteArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public void add(Meal meal) {
        int id = idCounter.getAndIncrement();
        meal.setId(id);
        mealsList.add(meal);
    }

    @Override
    public void update(Meal newMeal) throws Exception {
        Meal meal = getById(newMeal.getId());
        meal.setDateTime(newMeal.getDateTime());
        meal.setDescription(newMeal.getDescription());
        meal.setCalories(newMeal.getCalories());
    }

    @Override
    public void delete(Meal meal) {
        mealsList.remove(meal);
    }

    @Override
    public void delete(int mealId) throws Exception {
        Meal meal = getById(mealId);
        delete(meal);
    }

    @Override
    public List<Meal> getAll() {
        return mealsList;
    }

    @Override
    public Meal getById(int mealId) throws Exception {
        return mealsList.stream()
                .filter(m -> m.getId() == mealId)
                .findFirst()
                .orElseThrow(() -> new Exception("Unable to find Meal by id"));
    }

}
