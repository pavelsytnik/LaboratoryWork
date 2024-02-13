package org.example.robot;

import org.example.utils.MyKeyListener;
import org.example.window.RobotPanel;

import java.util.*;

public class RobotLogic {
    int maze[][];
    private List<Integer> pathX;
    private List<Integer> pathY;
    List<String> dir;
    private String direction;
    public MyRobot myRobot;
    private RobotMap robotMap;
    private MyKeyListener keyListener;
    public boolean flag;
    private CollisionChecker collisionChecker;
    private TileManager tileManager;
    private RobotPanel robotPanel;

    public RobotLogic(MyRobot myRobot, RobotMap robotMap, MyKeyListener keyListener, CollisionChecker collisionChecker, TileManager tileManager,RobotPanel robotPanel) {
        this.myRobot = myRobot;
        this.robotMap = robotMap;
        this.keyListener = keyListener;
        this.collisionChecker = collisionChecker;
        this.tileManager = tileManager;
        dir = new ArrayList<>();
        this.maze = tileManager.getMap();
        this.robotPanel = robotPanel;
    }

    public void robotMoveWithKey() {
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

        if (!collisionChecker.isFreeDirection(myRobot, robotMap, tileManager, direction)) {
            myRobot.collision = true;
            return;
        }

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

    public void robotMove() {
        pathX = new ArrayList<>();
        pathY = new ArrayList<>();
        boolean[][] visited = new boolean[robotMap.getCountTileInCol()][robotMap.getCountTileInRow()];
        int[] cord = tileManager.findCoordinateOfValue(maze, 2);
        flag = findPath(tileManager.getMap(), myRobot.getxPosition() / robotMap.getTileSize(),
                myRobot.getyPosition() / robotMap.getTileSize(), cord[0], cord[1], visited, pathX, pathY);
        Collections.reverse(pathX);
        Collections.reverse(pathY);
        if(flag) {
            for (int i = 0; i < pathX.size(); i++) {
                myRobot.setxPosition(pathX.get(i) * robotMap.getTileSize());
                myRobot.setyPosition(pathY.get(i) * robotMap.getTileSize());
                robotPanel.repaint();
                System.out.println(myRobot.xPosition / robotMap.getTileSize() + "," + myRobot.yPosition / robotMap.getTileSize());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    boolean findPath(int[][] maze, int startX, int startY, int endX, int endY, boolean[][] visited, List<Integer> pathX, List<Integer> pathY) {
        if (startX == endX && startY == endY) {
            pathX.add(startX);
            pathY.add(startY);
            flag = true;
            return true;
        }

        visited[startX][startY] = true;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int newX = startX + dir[0];
            int newY = startY + dir[1];
            if (isValid(maze, newX, newY, visited)) {
                if (findPath(maze, newX, newY, endX, endY, visited, pathX, pathY)) {
                    pathX.add(startX);
                    pathY.add(startY);
                    return true;
                } else {
                    visited[newX][newY] = false;
                }
            }
        }
        return false;
    }
    boolean isValid(int[][] maze, int x, int y, boolean[][] visited) {
        int n = maze.length;
        return x >= 0 && y >= 0 && x < n && y < n && maze[x][y] != 1 && !visited[x][y];
    }
}