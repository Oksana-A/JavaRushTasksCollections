package com.javarush.task.task35.task3507;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AnimalClassLoader extends ClassLoader {

    public Class load (Path path){
        byte[] buffer = null;
        try {
           buffer = Files.readAllBytes(path);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return defineClass(null, buffer, 0, buffer.length);
    }
}
