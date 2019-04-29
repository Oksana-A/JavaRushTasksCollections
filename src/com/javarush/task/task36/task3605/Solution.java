package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        try (FileReader reader = new FileReader(fileName)) {
            char[] buffer = new char[1024];
            reader.read(buffer);
            TreeSet<Character> set = new TreeSet();
            for (int i = 0; i < buffer.length; i++) {
                if (Character.isLetter(buffer[i]))
                    set.add(Character.toLowerCase(buffer[i]));
            }
            set.stream().limit(5).forEach(System.out::print);
        }
    }
}
