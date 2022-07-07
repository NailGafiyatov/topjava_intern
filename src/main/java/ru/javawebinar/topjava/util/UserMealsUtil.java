package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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

        List<MealTo> mealsToCycles = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        printList(mealsToCycles);

        List<MealTo> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        printList(mealsToStream);
    }

    public static List<MealTo> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesOfDay = new HashMap<>();
        meals.forEach(meal -> sumCaloriesOfDay.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        List<MealTo> mealsTo = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                mealsTo.add(createTo(meal, sumCaloriesOfDay.get(meal.getDate()) > caloriesPerDay));
            }
        }
        return mealsTo;
    }

    public static List<MealTo> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesOfDay = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, sumCaloriesOfDay.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(UserMeal userMeal, boolean excess) {
        return new MealTo(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    private static void printList(List<MealTo> mealsTo) {
        for (MealTo meal : mealsTo) {
            if (meal.isExcess()) {
                System.out.println("\u001B[31m" + meal + "\u001B[0m");
            } else {
                System.out.println("\u001B[32m" + meal + "\u001B[0m");
            }
        }
    }
}
