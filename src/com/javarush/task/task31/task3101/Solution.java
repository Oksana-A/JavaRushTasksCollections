package com.javarush.task.task31.task3101;

import java.io.*;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.TreeSet;

/*
Проход по дереву файлов
1. На вход метода main подаются два параметра.
Первый - path - путь к директории, второй - resultFileAbsolutePath - имя (полный путь) существующего файла, который будет содержать результат.
2. Для каждого файла в директории path и в ее всех вложенных поддиректориях выполнить следующее:
Если у файла длина в байтах НЕ больше 50, то для всех таких файлов:
2.1. Отсортировать их по имени файла в возрастающем порядке, путь не учитывать при сортировке.
2.2. Переименовать resultFileAbsolutePath в 'allFilesContent.txt' (используй метод FileUtils.renameFile, и, если понадобится, FileUtils.isExist).
2.3. В allFilesContent.txt последовательно записать содержимое всех файлов из п. 2.2.1. После каждого тела файла записать "\n".
Все файлы имеют расширение txt.
В качестве разделителя пути используй "/".
*/
public class Solution {


    public static void main(String[] args) throws IOException{
        String path = args[0];//path to file
        String resultFileAbsolutePath = args[1];//file for result
        File[] fileList = new File(path).listFiles();
        File resultFile = new File(resultFileAbsolutePath);

        if (fileList != null) { //add files in the fileMap
            for (File file : fileList) {
                lookForFile(file);
            }
        }

        File newFile = new File(resultFile.getParent() + "/allFilesContent.txt");
            FileUtils.renameFile(resultFile, newFile);


        // write date from files in the fileMap in the resultFile
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
            for (File f : FileUtils.fileMap.values()) {
                FileInputStream fileInputStream = new FileInputStream(f);
                while (fileInputStream.available() > 0) {
                    fileOutputStream.write(fileInputStream.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                fileOutputStream.flush();

                fileInputStream.close();
            }
        }

    }

    public static void addFileToMap(File file) {
        if (file.length() <= 50)
        FileUtils.fileMap.put(file.getName(), file);
    }

    public static void lookForFile(File file) {
        if (file.isFile())
            addFileToMap(file);
        else {
            File[] files = file.listFiles();
            for (File f : files)
                lookForFile(f);
        }
    }
}
