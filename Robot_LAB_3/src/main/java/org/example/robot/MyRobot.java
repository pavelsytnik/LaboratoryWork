package org.example.robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Класс MyRobot представляет робота на карте.
 * Унаследован от класса RobotMap для доступа к параметрам карты.
 */
public class MyRobot extends RobotMap {
    private BufferedImage image; // Изображение робота
    public String direction = "null"; // Текущее направление движения робота
    private int xPosition; // Позиция робота по оси X
    private int yPosition; // Позиция робота по оси Y
    private int speedRobot = this.getTileSize(); // Скорость перемещения робота
    public boolean collision = false; // Флаг, указывающий на столкновение робота с препятствием

    /**
     * Конструктор класса MyRobot.
     */
    public MyRobot() {
        // Конструктор без параметров
    }

    /**
     * Методы moveLeft, moveRight, moveUp, moveDown и moveWait
     * определяют направление движения робота.
     */
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

    /**
     * Метод drawImage загружает изображение робота из файла.
     */
    public void drawImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/char/robot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод draw отрисовывает робота на графическом контексте g.
     */
    public void draw(Graphics2D g) throws IOException {
        drawImage(); // Загрузка изображения робота
        g.setColor(Color.BLACK);
        g.drawImage(image, xPosition, yPosition, this.getTileSize(), this.getTileSize(), null); // Отрисовка робота
    }

    // Геттеры и сеттеры для доступа к полям класса

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