package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class c) {
        boolean flag = false;
        Annotation prepareMyTest = c.getAnnotation(PrepareMyTest.class);
        if (prepareMyTest != null) {
           String[] fullyQualifiedNames = ((PrepareMyTest) prepareMyTest).fullyQualifiedNames();
            Arrays.stream(fullyQualifiedNames).forEach(System.out::println);
            flag = true;
        }
        return flag;
    }

    public static boolean printValues(Class c) {
        boolean flag = false;
        Annotation prepareMyTest = c.getAnnotation(PrepareMyTest.class);
        if (prepareMyTest != null) {
            Class[] classes = ((PrepareMyTest) prepareMyTest).value();
            Arrays.stream(classes).forEach(System.out::println);
            flag = true;
        }
        return flag;
    }
}
