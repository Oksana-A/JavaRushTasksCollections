package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;
import java.util.Set;

public class Model {
    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("..\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task34\\task3410\\res\\levels.txt"));

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restartLevel(currentLevel);
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player, direction))
            return;
        if (checkBoxCollisionAndMoveIfAvaliable(direction))
            return;
        changeLocation(player, direction);
        checkCompletion();

    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction))
                return true;
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction) {
        Player player = gameObjects.getPlayer();
        GameObject stoped = null;
        for (GameObject gameObject : gameObjects.getAll()) {
            if (!(gameObject instanceof Player) && !(gameObject instanceof Home) && player.isCollision(gameObject, direction)) {
                stoped = gameObject;
            }
        }

        if ((stoped == null)) {
            return false;
        }

        if (stoped instanceof Box) {
            Box stopedBox = (Box) stoped;
            if (checkWallCollision(stopedBox, direction)) {
                return true;
            }
            for (Box box : gameObjects.getBoxes()) {
                if (stopedBox.isCollision(box, direction)) {
                    return true;
                }
            }

            changeLocation(stopedBox, direction);
        }
        return false;

    }

    private void changeLocation(GameObject gameObject, Direction direction) {
        switch (direction) {
            case DOWN:
                gameObject.setY(gameObject.getY() + FIELD_CELL_SIZE);
                break;
            case UP:
                gameObject.setY(gameObject.getY() - FIELD_CELL_SIZE);
                break;
            case LEFT:
                gameObject.setX(gameObject.getX() - FIELD_CELL_SIZE);
                break;
            case RIGHT:
                gameObject.setX(gameObject.getX() + FIELD_CELL_SIZE);
            break;
        }
    }

    public void checkCompletion() {
        Set<Box> boxSet = gameObjects.getBoxes();
        Set<Home> homeSet = gameObjects.getHomes();
        int count = 0;
        for (Box b : boxSet) {
            for (Home h : homeSet) {
                if (b.getX() == h.getX() && b.getY() == h.getY())
                    count++;
            }
        }
        boolean flag = homeSet.size() == count;
        if (flag)
            eventListener.levelCompleted(currentLevel);
    }

}
