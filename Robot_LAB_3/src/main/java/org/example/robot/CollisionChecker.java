package org.example.robot;

public class CollisionChecker {

    public boolean isFreeDirection(MyRobot myRobot, RobotMap robotMap, TileManager tileManager, String direction) {
        int tileSize = robotMap.getTileSize();
        int[][] map = tileManager.getMap();
        int col = myRobot.getxPosition() / tileSize;
        int row = myRobot.getyPosition() / tileSize;
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
                return true;
        }
        if (nextCol < 0 || nextCol >= robotMap.getCountTileInCol() || nextRow < 0 || nextRow >= robotMap.getCountTileInRow()) {
            return false;
        }
        return !tileManager.getTiles()[map[nextCol][nextRow]].collision;
    }
}