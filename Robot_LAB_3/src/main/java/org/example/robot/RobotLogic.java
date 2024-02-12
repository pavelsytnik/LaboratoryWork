package org.example.robot;

import org.example.utils.MyKeyListener;

import java.util.*;

public class RobotLogic {
    private List<String> directions;

    private int[][] maze;
    private int currentDirectionIndex;
    private MyRobot myRobot;
    private RobotMap robotMap;
    private CollisionChecker collisionChecker;
    private TileManager tileManager;

    private static final int WALL = 1;
    private static final int VISITED = 3;
    private static final int EXIT = 2;

    public RobotLogic(MyRobot myRobot, RobotMap robotMap, MyKeyListener keyListener, CollisionChecker collisionChecker, TileManager tileManager) {
        this.myRobot = myRobot;
        this.robotMap = robotMap;
        this.collisionChecker = collisionChecker;
        this.tileManager = tileManager;
        this.maze = tileManager.getMap();
        updatePath();
    }

    public void updatePath() {
        directions = solveMazeAndGenerateDirections(myRobot.getyPosition() / robotMap.getTileSize(), myRobot.getxPosition() / robotMap.getTileSize());
        System.out.println(directions);
        currentDirectionIndex = 0;
    }

    public void robotMove() {
        if (directions != null && currentDirectionIndex < directions.size()) {
            String direction = directions.get(currentDirectionIndex);
            if (collisionChecker.isFreeDirection(myRobot, robotMap, tileManager, direction)) {
                moveRobotInDirection(direction);
            }
            currentDirectionIndex++;
        }
    }

    private void moveRobotInDirection(String direction) {
        int speed = myRobot.getSpeedRobot();
        int tileSize = robotMap.getTileSize();

        // Получаем текущие координаты робота
        int currentX = myRobot.getxPosition() / tileSize;
        int currentY = myRobot.getyPosition() / tileSize;

        // Вычисляем новые координаты в зависимости от направления
        switch (direction) {
            case "left":
                currentX -= 1;
                break;
            case "right":
                currentX += 1;
                break;
            case "up":
                currentY -= 1;
                break;
            case "down":
                currentY += 1;
                break;
            default:
                // Неизвестное направление, не выполняем никаких действий
                return;
        }

        // Проверяем, что новые координаты находятся в пределах карты
        if (currentX >= 0 && currentX < robotMap.getCountTileInCol() && currentY >= 0 && currentY < robotMap.getCountTileInRow()) {
            // Проверяем, что новая позиция свободна от препятствий
            if (!collisionChecker.isFreeDirection(myRobot, robotMap, tileManager, direction)) {
                // Если путь заблокирован, просто завершаем метод
                return;
            }

            // Обновляем координаты робота
            myRobot.setxPosition(currentX * tileSize);
            myRobot.setyPosition(currentY * tileSize);

            // Выводим новые координаты робота
            System.out.println(currentY + "," + currentX);
        }
    }

    private List<String> solveMazeAndGenerateDirections(int row, int col) {
        List<String> path = new ArrayList<>();
        ruleRightHand(col, row, path); // Обращение к координатам в правильном порядке
        return generateDirectionsFromPath(path);
    }
    private boolean ruleRightHand(int row, int col, List<String> path) {
        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length || maze[col][row]  == WALL || maze[col][row]  == VISITED) {
            return false;
        }

        if (maze[col][row] == EXIT) {
            path.add("[" + row + ", " + col + "]");
            System.out.println("[" + row + ", " + col + "]");
            return true;
        }

        maze[col][row]  = VISITED; // Mark current cell as visited

        int[][] rightHandDirections = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] rightHandDirection : rightHandDirections) {
            int newRow = row + rightHandDirection[0];
            int newCol = col + rightHandDirection[1];
            if (ruleRightHand(newRow, newCol, path)) {
                path.add("[" + col + ", " + row + "]"); // Add current cell to the path before returning true
                return true;
            }
        }
        return false;
    }
    private List<String> generateDirectionsFromPath(List<String> path) {
        List<String> directions = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            String current = path.get(i);
            String next = path.get(i + 1);
            int currentCol = Integer.parseInt(current.substring(1, current.indexOf(",")));
            int currentRow = Integer.parseInt(current.substring(current.indexOf(",") + 2, current.length() - 1));
            int nextCol = Integer.parseInt(next.substring(1, next.indexOf(",")));
            int nextRow = Integer.parseInt(next.substring(next.indexOf(",") + 2, next.length() - 1));

            if (nextCol > currentCol) {
                directions.add("up");
            } else if (nextCol < currentCol) {
                directions.add("down");
            } else if (nextRow < currentRow) {
                directions.add("right");
            } else if (nextRow > currentRow) {
                directions.add("left");
            }
        }
        return directions;
    }
}