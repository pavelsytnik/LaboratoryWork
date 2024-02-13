package org.example.robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyRobot extends RobotMap {
    BufferedImage image;
    public  String direction = "null" ;
    public int xPosition;
    public int yPosition;
    private int speedRobot;
    public boolean collision = false;

    public MyRobot(int start,int finish) {
        xPosition = start * this.getTileSize();
        yPosition = finish * this.getTileSize();
        speedRobot = this.getTileSize();
    }

    public String moveLeft() {
        direction = "left";
        return direction;
    }

    public String moveRight() {
        direction = "right";
        return direction;
    }

    public String moveUp() {
        direction = "up";
        return direction;
    }

    public String moveDown() {
        direction = "down";
        return direction;
    }

    public String moveWait() {
        direction = "null";
        return direction;
    }

    public void drawImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/char/robot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) throws IOException {
        drawImage();
        g.setColor(Color.BLACK);
        g.drawImage(image,xPosition, yPosition, this.getTileSize(), this.getTileSize(),null);
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


    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

}
