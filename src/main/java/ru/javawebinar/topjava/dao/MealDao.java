package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void add(Meal meal);

    void update(Meal meal) throws Exception;

    void delete(Meal meal);

    void delete(int mealId) throws Exception;

    List<Meal> getAll();

    Meal getById(int mealId) throws Exception;

}
