package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal);

    // false if meal does not belong to userId
    boolean delete(int id);

    boolean deleteAllByUserId(int userId);

    // null if meal does not belong to userId
    Meal get(int id);

    Collection<Meal> getAllByUserId(int usedId);

    // ORDERED dateTime desc
    Collection<Meal> getAll();
}
