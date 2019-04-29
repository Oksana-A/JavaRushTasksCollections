package com.javarush.task.task34.task3410.model;

import java.awt.*;

public abstract class CollisionObject extends GameObject {

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        boolean flag = false;
        if (direction == Direction.DOWN) {
            if (this.getY()+ Model.FIELD_CELL_SIZE == gameObject.getY() && this.getX() == gameObject.getX())
                flag = true;
        }
        if (direction == Direction.UP) {
            if (this.getY()- Model.FIELD_CELL_SIZE == gameObject.getY() && this.getX() == gameObject.getX())
                flag = true;
        }
        if (direction == Direction.LEFT) {
            if (this.getX()- Model.FIELD_CELL_SIZE == gameObject.getX() && this.getY() == gameObject.getY())
                flag = true;
        }
        if (direction == Direction.RIGHT) {
            if (this.getX()+ Model.FIELD_CELL_SIZE == gameObject.getX() && this.getY() == gameObject.getY())
                flag = true;
        }
        return flag;
    }
}
