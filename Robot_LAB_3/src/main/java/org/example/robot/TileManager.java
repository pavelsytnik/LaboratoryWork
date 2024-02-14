package org.example.robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Класс TileManager управляет тайлами карты, их инициализацией и отображением.
 * Он также загружает карту из файла и предоставляет методы для работы с этой картой.
 */
public class TileManager extends RobotMap {
    private Tile[] tiles; // Массив тайлов
    private int[][] map; // Карта с расположением тайлов

    /**
     * Конструктор класса TileManager.
     * Инициализирует массив тайлов и загружает карту из файла.
     */
    public TileManager() {
        tiles = new Tile[3]; // Создание массива тайлов
        initTiles(); // Инициализация тайлов
        loadAndInitMap(); // Загрузка и инициализация карты
    }

    /**
     * Метод initTiles инициализирует тайлы, загружая их из файлов изображений.
     */
    private void initTiles() {
        try {
            // Инициализация тайла "белый"
            tiles[0] = new Tile();
            tiles[0].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tile/white.png"));

            // Инициализация тайла "стена"
            tiles[1] = new Tile();
            tiles[1].collision = true;
            tiles[1].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tile/wall.png"));

            // Инициализация тайла "флаг"
            tiles[2] = new Tile();
            tiles[2].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/utils/flag.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод loadAndInitMap загружает карту из текстового файла и инициализирует ее.
     */
    private void loadAndInitMap() {
        try (InputStream inputStream = getClass().getResourceAsStream("/map/map.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            map = new int[getCountTileInCol()][getCountTileInRow()]; // Создание массива для карты
            String line;
            int row = 0;
            // Чтение файла построчно и инициализация карты
            while ((line = bufferedReader.readLine()) != null && row < getCountTileInRow()) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < getCountTileInCol(); col++) {
                    int num = Integer.parseInt(numbers[col]);
                    map[col][row] = num; // Заполнение карты значениями из файла
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод drawTilesMap рисует карту с тайлами на графическом контексте.
     * @param graphics2D графический контекст
     */
    public void drawTilesMap(Graphics2D graphics2D) {
        int tileSize = getTileSize();
        // Проход по всей карте и отображение тайлов
        for (int col = 0; col < getCountTileInCol(); col++) {
            for (int row = 0; row < getCountTileInRow(); row++) {
                int tileNumber = map[col][row]; // Получение номера тайла из карты
                graphics2D.drawImage(tiles[tileNumber].bufferedImage, col * tileSize, row * tileSize, tileSize, tileSize, null);
            }
        }
    }

    /**
     * Метод findCoordinateOfValue находит координаты заданного значения в матрице.
     * @param matrix матрица
     * @param target значение, координаты которого нужно найти
     * @return массив из двух элементов: координаты найденного значения [x, y]
     */
    public int[] findCoordinateOfValue(int[][] matrix, int target) {
        int[] coordinates = new int[2];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    coordinates[0] = i; // Запись координаты X
                    coordinates[1] = j; // Запись координаты Y
                    return coordinates; // Возврат найденных координат
                }
            }
        }
        return null; // Если значение не найдено, возвращается null
    }

    /**
     * Метод checkCoordinateOfValue проверяет, содержит ли заданные координаты в матрице указанное значение.
     * @param matrix матрица
     * @param target значение, которое нужно проверить
     * @param x координата X
     * @param y координата Y
     * @return true, если значение находится по указанным координатам, иначе false
     */
    public boolean checkCoordinateOfValue(int[][] matrix, int target, int x, int y) {
        if (matrix[x][y] == target) {
            return true; // Значение находится по указанным координатам
        }
        return false; // Значение не находится по указанным координатам
    }

    /**
     * Метод getMap возвращает массив, представляющий карту тайлов.
     * @return двумерный массив карты тайлов
     */
    public int[][] getMap() {
        return map; // Возврат карты тайлов
    }

    /**
     * Метод getTiles возвращает массив объектов Tile, представляющих тайлы на карте.
     * @return массив тайлов
     */
    public Tile[] getTiles() {
        return tiles; // Возврат массива тайлов
    }
}