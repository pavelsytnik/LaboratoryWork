package org.example.robot;

import org.example.utils.MyKeyListener;

public class RobotLogic {
    private String direction;
    public MyRobot myRobot;
    private RobotMap robotMap;
    private MyKeyListener keyListener;

    private CollisionChecker collisionChecker;

    private TileManager tileManager;

    public RobotLogic(MyRobot myRobot, RobotMap robotMap, MyKeyListener keyListener, CollisionChecker collisionChecker, TileManager tileManager) {
        this.myRobot = myRobot;
        this.robotMap = robotMap;
        this.keyListener = keyListener;
        this.collisionChecker = collisionChecker;
        this.tileManager = tileManager;
    }

    public void robotMove() {
        if (keyListener.isLeft) {
            direction = myRobot.moveLeft();
        } else if (keyListener.isRight) {
            direction = myRobot.moveRight();
        } else if (keyListener.isDown) {
            direction = myRobot.moveDown();
        } else if (keyListener.isUp) {
            direction = myRobot.moveUp();
        } else {
            direction = myRobot.moveWait();
        }

        // Check for collision
        if (!collisionChecker.isFreeDirection(myRobot, robotMap, tileManager, direction)) {
            myRobot.collision = true;
            return; // Do not move if collision detected
        }

        // Move the robot if no collision
        myRobot.collision = false;
        if (keyListener.isLeft) {
            myRobot.xPosition -= myRobot.getSpeedRobot();
        } else if (keyListener.isRight) {
            myRobot.xPosition += myRobot.getSpeedRobot();
        } else if (keyListener.isDown) {
            myRobot.yPosition += myRobot.getSpeedRobot();
        } else if (keyListener.isUp) {
            myRobot.yPosition -= myRobot.getSpeedRobot();
        }
    }
}