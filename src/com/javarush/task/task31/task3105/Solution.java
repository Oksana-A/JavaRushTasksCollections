package com.javarush.task.task31.task3105;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
/*

        }

public static void main(String[] args) throws Exception {
        ZipFile war = new ZipFile("war.zip");
        ZipOutputStream append = new ZipOutputStream(new FileOutputStream("append.zip"));

        // first, copy contents from existing war
        Enumeration<? extends ZipEntry> entries = war.entries();
        while (entries.hasMoreElements()) {
        ZipEntry e = entries.nextElement();
        System.out.println("copy: " + e.getName());
        append.putNextEntry(e);
        if (!e.isDirectory()) {
        copy(war.getInputStream(e), append);
        }
        append.closeEntry();
        }

        // now append some extra content
        ZipEntry e = new ZipEntry("answer.txt");
        System.out.println("append: " + e.getName());
        append.putNextEntry(e);
        append.write("42\n".getBytes());
        append.closeEntry();

        // close
        war.close();
        append.close();
        }*/
public class Solution {

    private static final byte[] BUFFER = new byte[4096 * 1024];

    public static void main(String[] args) throws IOException {
        ZipFile oldZip = new ZipFile(args[1]);
        ZipOutputStream newZip = new ZipOutputStream(new FileOutputStream(args[0]));

        Enumeration<? extends ZipEntry> entries = oldZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            newZip.putNextEntry(e);
            if (!e.isDirectory()) {
                copy(oldZip.getInputStream(e), newZip);
            }
            newZip.closeEntry();
        }

        // now append some extra content
        ZipEntry e = new ZipEntry("new/" + args[0]);
        newZip.putNextEntry(e);
        newZip.write(args[0].getBytes());
        newZip.closeEntry();

        // close
        oldZip.close();
        newZip.close();

    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        int bytesRead;
        while ((bytesRead = input.read(BUFFER)) != -1) {
            output.write(BUFFER, 0, bytesRead);
        }
    }
}
