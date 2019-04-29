package com.javarush.task.task31.task3106;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) {
        String resultFileName = args[0];
        String[] fileNamePart = Arrays.copyOfRange(args, 1, args.length);

        for (String str : fileNamePart) {
            try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(str))) {
                    FileOutputStream fout = new FileOutputStream(resultFileName);
                    for (int c = zipIn.read(); c != -1; c = zipIn.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    fout.close();
                    zipIn.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
