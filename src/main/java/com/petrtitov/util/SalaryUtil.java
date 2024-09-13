package com.petrtitov.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@UtilityClass
public class SalaryUtil {
    public final static double DAYS_AVG = 29.3;

    //https://mintrud.gov.ru/labour/82
    public static Map<LocalDate, String> HOLIDAYS = Map.ofEntries(
            Map.entry(LocalDate.of(2024, 1, 1), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 1, 2), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 1, 3), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 1, 4), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 1, 5), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 1, 6), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 1, 7), "Рождество Христово"),
            Map.entry(LocalDate.of(2024, 1, 8), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 2, 23), "День защитника Отечества"),
            Map.entry(LocalDate.of(2024, 3, 8), "Международный женский день"),
            Map.entry(LocalDate.of(2024, 4, 29), "Праздник Весны и Труда"),
            Map.entry(LocalDate.of(2024, 4, 30), "Праздник Весны и Труда"),
            Map.entry(LocalDate.of(2024, 5, 1), "Праздник Весны и Труда"),
            Map.entry(LocalDate.of(2024, 5, 9), "День Победы"),
            Map.entry(LocalDate.of(2024, 5, 10), "День Победы"),
            Map.entry(LocalDate.of(2024, 6, 12), "День России"),
            Map.entry(LocalDate.of(2024, 11, 4), "День народного единства"),
            Map.entry(LocalDate.of(2024, 12, 30), "Новогодние каникулы"),
            Map.entry(LocalDate.of(2024, 12, 31), "Новогодние каникулы")

    );

    public static long countWorkingDays(LocalDate startDate, LocalDate endDate) {
        var days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        var holidaysInVacation = HOLIDAYS.entrySet().stream().filter(e -> isBetween(e.getKey(), startDate, endDate)).count();
        return days - holidaysInVacation;
    }

    public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
        return (value.compareTo(start) >= 0) && (value.compareTo(end) <= 0);
    }
}
