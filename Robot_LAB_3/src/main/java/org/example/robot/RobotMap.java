package org.example.robot;

import java.awt.*;

public class RobotMap {
    private final int tile = 16;
    private final int scale = 3;
    private final int tileSize = tile * scale;
    private final int countTileInRow = 16;
    private final int countTileInCol = 16;
    private final int width = tileSize * countTileInCol;

    private final int height = tileSize * countTileInRow;

    public int getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCountTileInRow() {
        return countTileInRow;
    }

    public int getCountTileInCol() {
        return countTileInCol;
    }


}
