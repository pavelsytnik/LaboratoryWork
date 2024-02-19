package org.example.robot;

/**
 * Класс CollisionChecker предназначен для проверки доступности направления движения робота.
 * Он определяет, свободно ли выбранное направление для передвижения робота на карте.
 */
public class CollisionChecker {

    /**
     * Метод isFreeDirection проверяет, свободно ли выбранное направление для передвижения робота.
     *
     * @param myRobot     Робот, чье перемещение проверяется.
     * @param robotMap    Карта, на которой находится робот.
     * @param tileManager Менеджер плиток, содержащий информацию о карте и плитках.
     * @param direction   Направление, которое требуется проверить (left, right, up, down).
     * @return true, если выбранное направление свободно для передвижения робота, иначе - false.
     */
    public boolean isFreeDirection(MyRobot myRobot, RobotMap robotMap, TileManager tileManager, String direction) {
        if (myRobot == null || robotMap == null || tileManager == null) {
            // Обработка случая, когда переданный объект равен null
            throw new IllegalArgumentException("One or more arguments are null");
        }

        int tileSize = robotMap.getTileSize();
        int[][] map = tileManager.getMap();
        int col = myRobot.getxPosition() / tileSize;
        int row = myRobot.getyPosition() / tileSize;

        // Проверка допустимости направления
        switch (direction) {
            case "left":
            case "right":
            case "up":
            case "down":
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        int nextCol = col;
        int nextRow = row;

        // Проверка на допустимость размера карты и позиции робота
        if ("left".equals(direction)) {
            nextCol--;
        } else if ("right".equals(direction)) {
            nextCol++;
        } else if ("up".equals(direction)) {
            nextRow--;
        } else if ("down".equals(direction)) {
            nextRow++;
        }

        if (nextCol < 0 || nextCol >= robotMap.getCountTileInCol() || nextRow < 0 || nextRow >= robotMap.getCountTileInRow()) {
            return false;
        }

        try {
            // Обработка исключительных ситуаций при доступе к массивам
            return !tileManager.getTiles()[map[nextCol][nextRow]].collision;
        } catch (ArrayIndexOutOfBoundsException e) {
            // Обработка выхода за границы массива
            return false;
        }
    }
}