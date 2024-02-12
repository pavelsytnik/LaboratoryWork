package org.example.robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager extends RobotMap {
    private Tile[] tiles;
    private int[][] map;

    public TileManager() {
        tiles = new Tile[4]; // Adjust size based on the number of tiles
        initTiles();
        loadAndInitMap();
    }

    private void initTiles() {
        try {
            tiles[0] = new Tile();
            tiles[0].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tile/white.png"));

            tiles[1] = new Tile();
            tiles[1].collision = true;
            tiles[1].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tile/wall.png"));

            tiles[2] = new Tile();
            tiles[2].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/utils/flag.png"));

            tiles[3] = new Tile();
            tiles[3].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tile/white.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAndInitMap() {
        try (InputStream inputStream = getClass().getResourceAsStream("/map/map.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            map = new int[getCountTileInCol()][getCountTileInRow()];
            String line;
            int row = 0;
            while ((line = bufferedReader.readLine()) != null && row < getCountTileInRow()) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < getCountTileInCol(); col++) {
                    int num = Integer.parseInt(numbers[col]);
                    map[col][row] = num;
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTilesMap(Graphics2D graphics2D) {
        int tileSize = getTileSize();
        for (int col = 0; col < getCountTileInCol(); col++) {
            for (int row = 0; row < getCountTileInRow(); row++) {
                int tileNumber = map[col][row];
                graphics2D.drawImage(tiles[tileNumber].bufferedImage, col * tileSize, row * tileSize, tileSize, tileSize, null);

            }
        }

    }

    public int[][] getMap() {
        return map;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}