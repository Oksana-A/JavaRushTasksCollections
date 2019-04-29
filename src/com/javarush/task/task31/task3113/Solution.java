package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {
    private static int filesCount = 0;
    private static int folderCount = 0;
    private static long byteCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();
        Path path = Paths.get(str);
        if (Files.isDirectory(path)){
            Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isDirectory())
                    folderCount++;
//                    if (attrs.isRegularFile()) {
//                        filesCount++;
//                        byteCount += attrs.size();
//                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    filesCount++;
                    byteCount += Files.size(file);
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("Всего папок - " + (folderCount - 1));
            System.out.println("Всего файлов - " + filesCount);
            System.out.println("Общий размер - " + byteCount);

        }
        else {
            System.out.println(str + " - не папка");
        }
    }


}

