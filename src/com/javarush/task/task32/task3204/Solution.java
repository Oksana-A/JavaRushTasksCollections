package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

/* 
Генератор паролей
*/
public class Solution {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8);
        outputStream.write('0');
        outputStream.write('a');
        outputStream.write('V');
            for( int i = 0; i < 5; i++ )
                outputStream.write(AB.charAt( rnd.nextInt(AB.length())));
            outputStream.close();
            return outputStream;
    }
}