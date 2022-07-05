package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

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

        List<UserMealWithExcess> mealsToCycles = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        printList(mealsToCycles);

        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        printList(mealsToStream);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        ArrayList<UserMealWithExcess> listUserMealWithExcess = new ArrayList<>();

        HashMap<LocalDate, Integer> map = getMapCaloriesOfDay(meals);

        map.forEach((key, value) -> {
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.toLocalTime(), startTime, endTime) && key.equals(meal.toLocalDate())) {
                listUserMealWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), value > caloriesPerDay));
            }
        }
        });

        return listUserMealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        ArrayList<UserMealWithExcess> listUserMealWithExcess = new ArrayList<>();

        HashMap<LocalDate, Integer> map = getMapCaloriesOfDay(meals);



        map.forEach((key, value) -> {
            meals.stream().filter(meal ->
                            TimeUtil.isBetweenHalfOpen(meal.toLocalTime(), startTime, endTime) && key.equals(meal.toLocalDate()))
                            .map(f ->
                                    listUserMealWithExcess.add(new UserMealWithExcess(f.getDateTime(), f.getDescription(), f.getCalories(), value > caloriesPerDay))).collect(Collectors.toList());
        });

        return listUserMealWithExcess;
    }

    private static HashMap<LocalDate, Integer> getMapCaloriesOfDay(List<UserMeal> meals) {
        HashMap<LocalDate, Integer> map = new HashMap<>();
        meals.forEach(meal -> map.merge(meal.toLocalDate(), meal.getCalories(), Integer::sum));
        return map;
    }

    private static void printList(List<UserMealWithExcess> mealsTo) {
        for (UserMealWithExcess meal : mealsTo) {
            if (meal.isExcess()) {
                System.out.println("\u001B[31m" + meal + "\u001B[0m");
            } else {
                System.out.println("\u001B[32m" + meal + "\u001B[0m");
            }
        }
    }
}
