package com.javarush.task.task39.task3908;

import java.util.HashSet;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isPalindromePermutation("aavvbbuullh5"));
    }

    public static boolean isPalindromePermutation(String s) {
        if (s == null || s.length() == 0) return true;
        boolean flag = false;
        String str = s.replaceAll(" ", "").toLowerCase();

            char[] chars = str.toCharArray();
            HashSet<Character> uniqueChars = new HashSet<>();
            for (char ch : chars) {
                if (Character.isLetterOrDigit(ch))
                    uniqueChars.add(ch);
            }
                int count = 0;
                for (Character uniqueCh : uniqueChars) {
                    int numberOfSweeps = 0;
                    for (int k = 0; k < chars.length; k++) {
                        if (uniqueCh == chars[k])
                            numberOfSweeps++;

                    }
                    if (numberOfSweeps%2 != 0)
                        count++;
                }
                if (count == 1 || count == 0)
                    flag = true;

        return flag;
    }
}
