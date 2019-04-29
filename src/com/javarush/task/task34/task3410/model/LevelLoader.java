package com.javarush.task.task34.task3410.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;
    private int[] startLine = new int[60];

    public LevelLoader(Path levels) {
        this.levels = levels;
        try{
            FileInputStream fstream = new FileInputStream(levels.toString());
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int i = 0;
            int k = 0;
            while ((strLine = br.readLine()) != null){
                if (strLine.contains("Maze:")) {
                    startLine[i] = k+7;
                    i++;
                }
                k++;
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public GameObjects getLevel(int level) {
        Set<Wall> wallSet = new HashSet<>();
        Set<Home> homeSet = new HashSet<>();
        Set<Box> boxSet = new HashSet<>();
        Player player = null;
        if (level > 60) {
            int a = level / 60;
            level = level - 60*a;
        }
        int start = startLine[level-1];
        try{
            FileInputStream fstream = new FileInputStream(levels.toString());
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            for (int i = 0; i < start; i++) {
                br.readLine();
            }

            int y = Model.FIELD_CELL_SIZE / 2;
            while (true) {
                strLine = br.readLine();
                if (strLine.length() <= 2)
                    break;

                int x = Model.FIELD_CELL_SIZE / 2;
                for (char ch : strLine.toCharArray()) {
                    switch (ch) {
                        case 'X' :
                            wallSet.add(new Wall(x, y));
                            break;
                        case '*' :
                            boxSet.add(new Box(x, y));
                            break;
                        case '.' :
                            homeSet.add(new Home(x, y));
                            break;
                        case '&' :
                            boxSet.add(new Box(x, y));
                            homeSet.add(new Home(x, y));
                            break;
                        case '@' :
                            player = new Player(x, y);
                            break;
                    }
                    x += Model.FIELD_CELL_SIZE;
                }
                y += Model.FIELD_CELL_SIZE;
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return new GameObjects(wallSet, boxSet, homeSet, player);
    }
}
