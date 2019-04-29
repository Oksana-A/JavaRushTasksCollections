package com.javarush.task.task35.task3507;

import org.jsoup.select.Evaluator;

import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> animalsSet = new HashSet<>();
        AnimalClassLoader classLoader = new AnimalClassLoader();

        File[] files = new File(pathToAnimals).listFiles();
        for (File f : files) {
            if (f.isFile() && f.toString().endsWith(".class")) {
                try {
                    Class clazz = classLoader.load(f.toPath());
                    if (Animal.class.isAssignableFrom(clazz)) {
                        try {
                            Constructor[] constructors = clazz.getConstructors();
                            for (Constructor c: constructors) {
                                if (c.getParameterCount() == 0)
                                    animalsSet.add((Animal)c.newInstance());
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return animalsSet;
    }
}
