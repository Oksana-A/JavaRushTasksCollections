package com.javarush.task.task40.task4009;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getWeekdayOfBirthday("30.05.1984", "2015"));
    }

    public static String getWeekdayOfBirthday(String birthday, String year) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate localDate = LocalDate.parse(birthday, formatter1);
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        Year year1 = Year.parse(year);
        LocalDate localDate1 = year1.atMonthDay(MonthDay.of(month, day));
        return localDate1.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
    }
}
