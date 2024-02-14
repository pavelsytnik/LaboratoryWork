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
        // Получаем размер плитки на карте
        int tileSize = robotMap.getTileSize();
        // Получаем карту с плитками
        int[][] map = tileManager.getMap();
        // Определяем столбец и строку, в которых находится робот
        int col = myRobot.getxPosition() / tileSize;
        int row = myRobot.getyPosition() / tileSize;
        // Определяем следующий столбец и строку в зависимости от выбранного направления
        int nextCol = col;
        int nextRow = row;
        switch (direction) {
            case "left":
                nextCol--;
                break;
            case "right":
                nextCol++;
                break;
            case "up":
                nextRow--;
                break;
            case "down":
                nextRow++;
                break;
            default:
                // Если направление неизвестно, считаем его свободным
                return true;
        }
        // Проверяем, выходит ли следующая позиция за границы карты
        if (nextCol < 0 || nextCol >= robotMap.getCountTileInCol() || nextRow < 0 || nextRow >= robotMap.getCountTileInRow()) {
            return false; // Следующая позиция за границами карты, направление недоступно
        }
        // Проверяем, доступна ли следующая позиция для перемещения (не является ли плитка препятствием)
        return !tileManager.getTiles()[map[nextCol][nextRow]].collision;
    }
}