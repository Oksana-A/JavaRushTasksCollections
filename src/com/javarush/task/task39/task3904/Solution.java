package com.javarush.task.task39.task3904;

import java.util.Arrays;
import java.util.Map;

/* 
Лестница
*/
public class Solution {
    private static int n = 70;

    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        long answer = 0;
        if (n >= 0) {
            long[] buffer = new long[n + 1];
            if (n == 0 || n == 1) answer = 1;
            if (n == 2) answer = 2;
            if (n > 2) {
                buffer[0] = 1;
                buffer[1] = 1;
                buffer[2] = 2;
                for (int i = 3; i <= n; i++) {
                    buffer[i] = buffer[i - 1] + buffer[i - 2] + buffer[i - 3];
                }
                answer = buffer[n];
            }
        }
        return answer;
    }
}

