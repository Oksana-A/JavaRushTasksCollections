package com.javarush.task.task40.task4007;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* 
Работа с датами
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("15:56:45");
    }

    public static void printDate(String date) {
        String regex1= "\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}";
        String regex2= "\\d{1,2}\\.\\d{1,2}\\.\\d{4}";
        String regex3= "\\d{1,2}:\\d{1,2}:\\d{1,2}";

        SimpleDateFormat dateFormat = null;
        Calendar calendar = Calendar.getInstance();

        try {
            if (date.matches(regex1)) {
                dateFormat = new SimpleDateFormat("d.M.yyyy HH:mm:ss");
                calendar.setTime(dateFormat.parse(date));
                Solution.printDate(calendar);
                Solution.printTime(calendar);
            }
            if (date.matches(regex2)) {
                dateFormat = new SimpleDateFormat("d.M.yyyy");
                calendar.setTime(dateFormat.parse(date));
                Solution.printDate(calendar);
            }
            if (date.matches(regex3)) {
                dateFormat = new SimpleDateFormat("HH:mm:ss");
                calendar.setTime(dateFormat.parse(date));
                Solution.printTime(calendar);
            }
        }
        catch (ParseException e) {}
        if (dateFormat == null){
            System.out.println("Неподдерживаемый формат");
        }
    }

    private static void printDate(Calendar calendar) {
        System.out.println("День: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("День недели: " + ((calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1));
        System.out.println("День месяца: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("День года: " + calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println("Неделя месяца: " + calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("Неделя года: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("Месяц: " + (calendar.get(Calendar.MONTH) + 1));
        System.out.println("Год: " + calendar.get(Calendar.YEAR));
    }

    private static void printTime(Calendar calendar) {
        System.out.println("AM или PM: " + (calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM"));
        System.out.println("Часы: " + calendar.get(Calendar.HOUR));
        System.out.println("Часы дня: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("Минуты: " + calendar.get(Calendar.MINUTE));
        System.out.println("Секунды: " + calendar.get(Calendar.SECOND));
    }


}
