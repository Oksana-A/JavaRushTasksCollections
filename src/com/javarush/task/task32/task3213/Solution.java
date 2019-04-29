package com.javarush.task.task32.task3213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor#Dpljr#&C,₷B'3");
        System.out.println(decode(reader, -3));  //Hello Amigo #@)₴?$0
    }

    public static String decode(StringReader reader, int key) throws IOException {
        String result = "";
        if(reader != null) {
            BufferedReader bufReader = new BufferedReader(reader);
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = bufReader.readLine()) != null) {
                strBuilder.append(line);
            }
            char[] buffer = strBuilder.toString().toCharArray();
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (char) (buffer[i] + key);
            }
            result = new String(buffer);
        }
        return result;
    }
}
