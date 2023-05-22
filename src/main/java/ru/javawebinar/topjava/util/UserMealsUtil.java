package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("___________________________");

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealWithExcessList = new ArrayList<>();
        Map<LocalDate, Integer> countCaloriesForDay = new HashMap<>();

        for (UserMeal userMeal : meals) {
            LocalDateTime mealDateTime = userMeal.getDateTime();

            LocalDate mealDate = mealDateTime.toLocalDate();
            if (!countCaloriesForDay.containsKey(mealDate)) {
                countCaloriesForDay.put(mealDate, userMeal.getCalories());
            } else {
                countCaloriesForDay.put(mealDate, countCaloriesForDay.get(mealDate) + userMeal.getCalories());
            }

            if (TimeUtil.isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                mealWithExcessList.add(new UserMealWithExcess(mealDateTime, userMeal.getDescription(), userMeal.getCalories(), false));
            }
        }

        for (UserMealWithExcess userMealWithExcess : mealWithExcessList) {
            LocalDate mealDate = userMealWithExcess.getDateTime().toLocalDate();
            Integer caloriesForDay = countCaloriesForDay.get(mealDate);
            if (caloriesForDay > caloriesPerDay) {
                userMealWithExcess.setExcess(true);
            }
        }

        return mealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> countCaloriesForDay = meals.stream().collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return meals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(), countCaloriesForDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

}
