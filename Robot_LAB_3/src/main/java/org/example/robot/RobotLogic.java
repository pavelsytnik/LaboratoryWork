package org.example.robot;

import org.example.utils.MyKeyListener;
import org.example.window.RobotPanel;

import java.util.*;

/**
 * Класс RobotLogic управляет движением робота и его логикой.
 * Он осуществляет управление роботом с помощью клавиатуры и автоматическое перемещение робота по заданному пути на карте.
 */
public class RobotLogic {
    int maze[][]; // Массив, представляющий карту лабиринта
    private List<Integer> pathX; // Список координат X пути движения робота
    private List<Integer> pathY; // Список координат Y пути движения робота
    List<String> dir; // Список направлений движения
    private String direction; // Текущее направление движения
    public MyRobot myRobot; // Объект робота
    private RobotMap robotMap; // Карта, на которой движется робот
    private MyKeyListener keyListener; // Слушатель клавиатуры
    public boolean flag; // Флаг, указывающий на наличие пути к цели
    public boolean finish = false; // Флаг, указывающий на завершение поиска пути
    private CollisionChecker collisionChecker; // Объект для проверки коллизий
    private TileManager tileManager; // Менеджер тайлов
    private RobotPanel robotPanel; // Панель с изображением робота на карте

    /**
     * Конструктор класса RobotLogic.
     * @param myRobot объект робота
     * @param robotMap карта, на которой движется робот
     * @param keyListener слушатель клавиатуры
     * @param collisionChecker объект для проверки коллизий
     * @param tileManager менеджер тайлов
     * @param robotPanel панель с изображением робота на карте
     */
    public RobotLogic(MyRobot myRobot, RobotMap robotMap, MyKeyListener keyListener, CollisionChecker collisionChecker, TileManager tileManager, RobotPanel robotPanel) {
        this.myRobot = myRobot;
        this.robotMap = robotMap;
        this.keyListener = keyListener;
        this.collisionChecker = collisionChecker;
        this.tileManager = tileManager;
        dir = new ArrayList<>();
        this.maze = tileManager.getMap();
        this.robotPanel = robotPanel;
    }

    /**
     * Метод robotMoveWithKey управляет движением робота с помощью клавиатуры.
     * Проверяет, свободно ли направление движения, и изменяет позицию робота.
     */
    public void robotMoveWithKey() {
        try {
            // Проверяем, какая клавиша нажата, и устанавливаем соответствующее направление движения
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

            // Проверяем, свободно ли направление движения, используя объект collisionChecker
            if (!collisionChecker.isFreeDirection(myRobot, robotMap, tileManager, direction)) {
                myRobot.collision = true; // Если направление занято, устанавливаем флаг коллизии
                return;
            }

            myRobot.collision = false; // Сбрасываем флаг коллизии
            // Изменяем позицию робота в соответствии с направлением движения
            if (keyListener.isLeft) {
                myRobot.setxPosition(myRobot.getxPosition() - myRobot.getSpeedRobot());
            } else if (keyListener.isRight) {
                myRobot.setxPosition(myRobot.getxPosition() + myRobot.getSpeedRobot());
            } else if (keyListener.isDown) {
                myRobot.setyPosition(myRobot.getyPosition() + myRobot.getSpeedRobot());
            } else if (keyListener.isUp) {
                myRobot.setyPosition(myRobot.getyPosition() - myRobot.getSpeedRobot());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод robotMove осуществляет автоматическое перемещение робота по найденному пути на карте.
     * Поиск пути выполняется с использованием алгоритма поиска в глубину.
     */
    public void robotMove() {
        try {
            pathX = new ArrayList<>();
            pathY = new ArrayList<>();
            boolean[][] visited = new boolean[robotMap.getCountTileInCol()][robotMap.getCountTileInRow()];
            // Находим координаты цели на карте
            int[] cord = tileManager.findCoordinateOfValue(maze, 2);
            // Вызываем метод поиска пути
            flag = findPath(tileManager.getMap(), myRobot.getxPosition() / robotMap.getTileSize(),
                    myRobot.getyPosition() / robotMap.getTileSize(), cord[0], cord[1], visited, pathX, pathY);
            Collections.reverse(pathX); // Разворачиваем список координат X пути
            Collections.reverse(pathY); // Разворачиваем список координат Y пути
            if (flag) {
                // Перемещаем робота по найденному пути
                for (int i = 0; i < pathX.size(); i++) {
                    myRobot.setxPosition(pathX.get(i) * robotMap.getTileSize());
                    myRobot.setyPosition(pathY.get(i) * robotMap.getTileSize());
                    robotPanel.repaint(); // Перерисовываем панель с роботом
                    try {
                        Thread.sleep(100); // Задержка для визуализации перемещения робота
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            finish = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }// Устанавливаем флаг завершения поиска пути
    }

    /**
     * Метод findPath выполняет поиск пути с использованием алгоритма поиска в глубину (DFS).
     * @param maze карта лабиринта
     * @param startX стартовая координата X
     * @param startY стартовая координата Y
     * @param endX конечная координата X
     * @param endY конечная координата Y
     * @param visited массив, отслеживающий посещенные клетки
     * @param pathX список координат X пути
     * @param pathY список координат Y пути
     * @return true, если путь найден, иначе false
     */
    boolean findPath(int[][] maze, int startX, int startY, int endX, int endY, boolean[][] visited, List<Integer> pathX, List<Integer> pathY) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Метод isValid проверяет, является ли клетка с координатами (x, y) допустимой для перемещения.
     * @param maze карта лабиринта
     * @param x координата X клетки
     * @param y координата Y клетки
     * @param visited массив, отслеживающий посещенные клетки
     * @return true, если клетка допустима для перемещения, иначе false
     */
    boolean isValid(int[][] maze, int x, int y, boolean[][] visited) {
        int n = maze.length;
        return x >= 0 && y >= 0 && x < n && y < n && maze[x][y] != 1 && !visited[x][y];
    }
}