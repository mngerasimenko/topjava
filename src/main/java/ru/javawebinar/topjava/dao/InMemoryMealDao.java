package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDao implements MealDao {
    private final List<Meal> mealsList = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Meal add(Meal meal) {
        int id = idCounter.getAndIncrement();
        meal.setId(id);
        mealsList.add(meal);
        return meal;
    }

    @Override
    public List<Meal> addAll(List<Meal> meals) {
        meals.forEach(this::add);
        return getAll();
    }

    @Override
    public Meal update(Meal newMeal) {
        Meal meal = getById(newMeal.getId());
        meal.setDateTime(newMeal.getDateTime());
        meal.setDescription(newMeal.getDescription());
        meal.setCalories(newMeal.getCalories());
        return meal;
    }

    @Override
    public void delete(int mealId) {
        Meal meal = getById(mealId);
        mealsList.remove(meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsList);
    }

    @Override
    public Meal getById(int mealId) {
        return mealsList.stream()
                .filter(m -> m.getId() == mealId)
                .findFirst()
                .orElse(null);
    }
}
