package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.length() == 0)
            return 0;
        List<Integer> list = s.chars().boxed().collect(Collectors.toList());
        int count = 0;
        int tmp = 0;
        int start = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                tmp++;
                continue;
            }
            if (list.subList(start, i).contains(list.get(i)) || i == list.size()-1) {
                if (tmp > count) count = tmp;
                int x = start;
                start = list.subList(x, i).indexOf(list.get(i)) + 1 + x;
                tmp = tmp - (start - x);
            }
            tmp++;
        }
        return count;
    }
}
