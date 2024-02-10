package org.example.robot;

import org.example.utils.MyKeyListener;

import java.awt.*;

public class MyRobot extends RobotMap{

    private Rectangle rectangle;
    private String direction;
    public int xPosition;
    public int yPosition;
    private int speedRobot;
    public boolean collision = false;
    public MyRobot() {
        xPosition = this.getCountTileInCol() / 2 * this.getTileSize();
        yPosition = this.getCountTileInRow() / 2 * this.getTileSize();
        speedRobot = this.getTileSize();
    }

    public String moveLeft() {
        direction = "left";
        return  direction;
    }

    public String moveRight() {
        direction = "right";
        return  direction;
    }

    public String moveUp() {
        direction = "up";
        return  direction;
    }

    public String moveDown() {
        direction = "down";
        return  direction;
    }
    public String moveWait() {
        direction = "null";
        return  direction;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(xPosition, yPosition, this.getTileSize(), this.getTileSize());
    }
    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getSpeedRobot() {
        return speedRobot;
    }

    public String getDirection() {
        return direction;
    }
}
