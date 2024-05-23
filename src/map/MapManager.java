package map;

import main.MainFrame;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapManager {
    private MainFrame mainFrame;

    private String[][] mapDataFileArr;
    private BufferedImage tileSetImage;
    private BufferedImage[][] tileSetArr;
    private Tile[] collisionArr;

    private int finalTileSize;

    public MapManager(String filePath, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        tileSetImage = loadTileSetImage(filePath);
        mapDataFileArr = loadMapDataFile("data/mapData/map1");
        tileSetArr = loadTileSetArr();
        collisionArr = new Tile[tileSetArr.length * tileSetArr[0].length];

        finalTileSize = mainFrame.getFinalTileSize();
    }

    public BufferedImage loadTileSetImage(String filePath) {
        tileSetImage = null;
        try {
            return tileSetImage = ImageIO.read(new File(filePath));
        }
        catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
        return tileSetImage;
    }

    public BufferedImage[][] loadTileSetArr() {
        tileSetArr = new BufferedImage[mapDataFileArr.length][mapDataFileArr[0].length];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 18; col++) {
                int tileSize = mainFrame.getTileSize();
                tileSetArr[row][col] = tileSetImage.getSubimage(row * tileSize, col * tileSize, tileSize, tileSize);
            }
        }
        return tileSetArr;
    }

    public String[][] loadMapDataFile(String fileName) {
        File mapDataFile = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(mapDataFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }

        ArrayList<String> tempArr = new ArrayList<>();
        while (s.hasNextLine()) {
            tempArr.add(s.nextLine());
        }

        int row = tempArr.size();
        int col = tempArr.get(0).split(" ").length;

        String[][] mapDataFileArr = new String[row][col];
        for (int i = 0; i < mapDataFileArr.length; i++) {
            mapDataFileArr[i] = tempArr.get(i).split(" ");
        }
        return mapDataFileArr;
    }

    public void updateCollisionArr(String[][] mapDataFileArr) {
        for (int row = 0; row < mapDataFileArr.length; row++) {
            for (int col = 0; col < mapDataFileArr[0].length; col++) {
                int tileID = Integer.parseInt(mapDataFileArr[row][col]);
                int index = (row * mapDataFileArr[0].length) + col;

                Tile tile = new Tile(col * finalTileSize, row * finalTileSize, finalTileSize, finalTileSize, tileID);
                collisionArr[index] = tile;
            }
        }
    }

    public void drawMap(Graphics g) {
        for (int row = 0; row < mapDataFileArr.length; row++) {
            for (int col = 0; col < mapDataFileArr[0].length; col++) {
                int tileId = Integer.parseInt(mapDataFileArr[row][col]);
                int tileSetX = 0;
                int tileSetY = tileId;

                while (tileSetY > tileSetArr.length) {
                    tileSetX++;
                    tileSetY -= tileSetArr.length;
                }

                g.drawImage(tileSetArr[tileSetY][tileSetX], col * finalTileSize, row * finalTileSize, finalTileSize, finalTileSize,  null);
            }
        }
    }

    public void update() {
        updateCollisionArr(loadMapDataFile("data/mapData/map1"));
    }

    public Tile[] getCollisionArr() {
        return collisionArr;
    }
}
