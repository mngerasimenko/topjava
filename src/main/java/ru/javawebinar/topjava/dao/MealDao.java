package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal add(Meal meal);

    void delete(int mealId);

    List<Meal> getAll();

    Meal getById(int mealId);

    Meal update(Meal meal);
}