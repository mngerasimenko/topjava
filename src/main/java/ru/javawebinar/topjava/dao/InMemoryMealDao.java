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

    private void addAll(List<Meal> meals) {
        meals.forEach(this::add);
    }

    @Override
    public Meal add(Meal meal) {
        int id = idCounter.getAndIncrement();
        meal.setId(id);
        mealsMap.put(id, meal);
        return meal;
    }

    @Override
    public Meal update(Meal newMeal) {
        Meal meal = getById(newMeal.getId());
        return mealsMap.replace(meal.getId(), meal, newMeal)
                ? getById(meal.getId())
                : null;
    }

    @Override
    public void delete(int mealId) {
        mealsMap.remove(mealId);
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
