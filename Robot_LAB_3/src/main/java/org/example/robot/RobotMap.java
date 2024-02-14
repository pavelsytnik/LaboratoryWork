package org.example.robot;
/**
 * Класс RobotMap представляет собой карту, на которой размещается робот и другие объекты.
 * Он определяет параметры карты, такие как размер плитки, количество плиток в строке и столбце,
 * а также ширину и высоту карты в пикселях.
 */
public class RobotMap {
    // Размер одной плитки в пикселях
    private final int tile = 16;
    // Масштабирование размера плитки
    private final int scale = 3;
    // Размер плитки после масштабирования
    private final int tileSize = tile * scale;
    // Количество плиток в строке карты
    private final int countTileInRow = 13;
    // Количество плиток в столбце карты
    private final int countTileInCol = 13;
    // Ширина карты в пикселях
    private final int width = tileSize * countTileInCol;
    // Высота карты в пикселях
    private final int height = tileSize * countTileInRow;

    /**
     * Метод getTileSize возвращает размер плитки на карте.
     *
     * @return Размер плитки на карте.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Метод getWidth возвращает ширину карты в пикселях.
     *
     * @return Ширина карты в пикселях.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Метод getHeight возвращает высоту карты в пикселях.
     *
     * @return Высота карты в пикселях.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Метод getCountTileInRow возвращает количество плиток в строке карты.
     *
     * @return Количество плиток в строке карты.
     */
    public int getCountTileInRow() {
        return countTileInRow;
    }

    /**
     * Метод getCountTileInCol возвращает количество плиток в столбце карты.
     *
     * @return Количество плиток в столбце карты.
     */
    public int getCountTileInCol() {
        return countTileInCol;
    }
}