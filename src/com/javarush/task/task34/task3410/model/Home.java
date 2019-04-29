package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x + Model.FIELD_CELL_SIZE/2, y + Model.FIELD_CELL_SIZE/2, 2, 2);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.drawOval(getX(), getY(),getWidth(), getHeight());
    }
}
