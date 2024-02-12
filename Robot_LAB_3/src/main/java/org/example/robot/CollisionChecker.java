package org.example.robot;

public class CollisionChecker {

    public boolean isFreeDirection(MyRobot myRobot, RobotMap robotMap, TileManager tileManager, String direction) {
        int tileSize = robotMap.getTileSize();
        int[][] map = tileManager.getMap();
        int col = myRobot.getxPosition() / tileSize;
        int row = myRobot.getyPosition() / tileSize;

        // Determine the next position based on the direction
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
                return true; // No direction specified, so movement is allowed
        }

        // Check if the next position is within bounds
        if (nextCol < 0 || nextCol >= robotMap.getCountTileInCol() || nextRow < 0 || nextRow >= robotMap.getCountTileInRow()) {
            return false; // Next position is out of bounds
        }

        // Check if the next position is free (not a wall)
        return !tileManager.getTiles()[map[nextCol][nextRow]].collision;
    }
}