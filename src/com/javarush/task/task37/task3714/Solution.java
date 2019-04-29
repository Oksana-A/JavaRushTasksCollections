package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));

    }

    public static int romanToInteger(String s) {
        char[] str = s.toUpperCase().toCharArray();
        int answer = 0;
        for (int i = str.length - 1; i >= 0; i--) {
            switch (str[i]) {
                case 'I':
                    answer += 1;
                    break;
                case 'V':
                    answer += 5;
                    if ((i - 1) >= 0 && str[i - 1] == 'I') {
                        do {
                            answer = answer - 1;
                            i--;
                        }
                        while ((i - 1) >= 0 && str[i - 1] == 'I');
                    }
                    break;
                case 'X':
                    answer += 10;
                    if ((i - 1) >= 0 && str[i - 1] == 'I') {
                        do {
                            answer = answer - 1;
                            i--;
                        }
                        while ((i - 1) >= 0 && str[i - 1] == 'I');
                    }
                    break;
                case 'L':
                    answer += 50;
                    if ((i - 1) >= 0 && str[i - 1] == 'X') {
                        do {
                            answer = answer - 10;
                            i--;
                        }
                        while ((i - 1) >= 0 && str[i - 1] == 'X');
                    }
                    break;
                case 'C':
                    answer += 100;
                    if ((i - 1) >= 0 && str[i - 1] == 'X') {
                        do {
                            answer = answer - 10;
                            i--;
                        }
                        while ((i - 1) >= 0 && str[i - 1] == 'X');
                    }
                    break;
                case 'D':
                    answer += 500;
                    if ((i - 1) >= 0 && str[i - 1] == 'C') {
                        do {
                            answer = answer - 100;
                            i--;
                        }
                        while ((i - 1) >= 0 && str[i - 1] == 'C');
                    }
                    break;
                case 'M':
                    answer += 1000;
                    if ((i - 1) >= 0 && str[i - 1] == 'C') {
                        do {
                            answer = answer - 100;
                            i--;
                        }
                        while ((i - 1) >= 0 && str[i - 1] == 'C');
                    }
                    break;

            }
        }
        return answer;
    }
}
