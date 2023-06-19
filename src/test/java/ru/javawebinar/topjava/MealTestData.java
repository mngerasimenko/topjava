package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int OTHER_USER_ID = START_SEQ + 1;
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;
    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2023, Month.JUNE, 18, 9, 6), "Завтрак", 500);
    public static final Meal meal_2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2023, Month.JUNE, 19, 13, 6), "Обед", 1000);
    public static final Meal meal_3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2023, Month.JUNE, 20, 18, 6), "Ужин", 400);


    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, Month.JANUARY, 1, 1, 1), "new", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2023, Month.JANUARY, 1, 1, 1));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }
}
