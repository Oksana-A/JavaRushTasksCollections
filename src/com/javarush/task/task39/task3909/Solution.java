package com.javarush.task.task39.task3909;

/* 
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("owsana", "oksana"));
    }

    public static boolean isOneEditAway(String first, String second) {
        boolean flag = false;
        if (first.length() == second.length()) {
            if (first.equals(second)) {
                flag = true;
            }
            else {
                int count = 0;
                for (int i = 0; i < first.length(); i++) {
                    if (first.charAt(i) != second.charAt(i))
                        count++;
                }
                if (count < 2)
                    flag = true;
            }
        }
        else {
            String shortStr = null;
            String longStr = null;
            if (first.length() < second.length()) {
                shortStr = first;
                longStr = second;
            }
            else {
                shortStr = second;
                longStr = first;
            }

            if ((longStr.length() - shortStr.length()) == 1) {
                if (shortStr.equals(longStr.substring(0, longStr.length()-1)))
                    flag = true;
                else {
                    for (int i = 0; i < shortStr.length(); i++) {
                        if (shortStr.charAt(i) != longStr.charAt(i)) {
                            for (int k = i; k < shortStr.length(); k++) {
                                if (shortStr.charAt(k) != longStr.charAt(k+1))
                                    return false;
                            }
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
