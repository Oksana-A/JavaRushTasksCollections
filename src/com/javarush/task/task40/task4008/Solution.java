package com.javarush.task.task40.task4008;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("9.10.2017 5:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        String regexDateTime= "\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}";
        String regexDate= "\\d{1,2}\\.\\d{1,2}\\.\\d{4}";
        String regexTime= "\\d{1,2}:\\d{1,2}:\\d{1,2}";

        DateTimeFormatter formatter = null;

        if (date.matches(regexDateTime)) {
           String[] str = date.split(" ");
           formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
           printDate(LocalDate.parse(str[0], formatter));
           formatter = DateTimeFormatter.ofPattern("H:m:s");
           printTime(LocalTime.parse(str[1], formatter));
        }
        if (date.matches(regexDate)) {
            formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            printDate(LocalDate.parse(date, formatter));
        }
        if (date.matches(regexTime)) {
            formatter = DateTimeFormatter.ofPattern("H:m:s");
            printTime(LocalTime.parse(date, formatter));
        }
        if (formatter == null)
            System.out.println("Неподдерживаемый тип формата");
    }

    public static void printDate(LocalDate localDate) {
        System.out.println("День: " + localDate.getDayOfMonth());
        System.out.println("День недели: " + localDate.getDayOfWeek().getValue());
        System.out.println("День месяца: " + localDate.getDayOfMonth());
        System.out.println("День года: " + localDate.getDayOfYear());
        System.out.println("Неделя месяца: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth()));
        System.out.println("Неделя года: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
        System.out.println("Месяц: " + localDate.getMonthValue());
        System.out.println("Год: " + localDate.getYear());
    }

    public static void printTime(LocalTime localTime) {
        System.out.println("AM или PM: " + (localTime.getHour() <=12 ? "AM" : "PM"));
        System.out.println("Часы: " + (localTime.getHour() <=12 ? localTime.getHour() : localTime.getHour()-12));
        System.out.println("Часы дня: " + localTime.getHour());
        System.out.println("Минуты: " + localTime.getMinute());
        System.out.println("Секунды: " + localTime.getSecond());
    }
}
