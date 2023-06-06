package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDao implements MealDao {
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public InMemoryMealDao() {
        addAll(MealsUtil.getMealsList());
    }

    @Override
    public Meal add(Meal meal) {
        int id = idCounter.getAndIncrement();
        meal.setId(id);
        mealsMap.put(id, meal);
        return meal;
    }

    public List<Meal> addAll(List<Meal> meals) {
        meals.forEach(this::add);
        return getAll();
    }

    @Override
    public synchronized Meal update(Meal newMeal) {
        Meal meal = getById(newMeal.getId());
        meal.setDateTime(newMeal.getDateTime());
        meal.setDescription(newMeal.getDescription());
        meal.setCalories(newMeal.getCalories());
        return meal;
    }

    @Override
    public void delete(int mealId) {
        Meal meal = getById(mealId);
        mealsMap.remove(meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }

    @Override
    public Meal getById(int mealId) {
        return mealsMap.get(mealId);
    }
}
