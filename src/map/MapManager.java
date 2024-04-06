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
    private BufferedImage tileSet;
    private BufferedImage[][] tileArray;
    private Tile[] collisionArr;
    private int finalTileSize;

    public MapManager(String filePath, MainFrame mainFrame) {
        collisionArr = new Tile[mainFrame.getNumTileWidth() * mainFrame.getNumTileHeight()];
        this.mainFrame = mainFrame;
        tileSet = loadTileSet(filePath);
        tileArray = loadTileArray();
        finalTileSize = mainFrame.getTileSize() * mainFrame.getTileScale();
    }

    public BufferedImage loadTileSet(String filePath) {
        tileSet = null;
        try {
            return tileSet = ImageIO.read(new File(filePath));
        }
        catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
        return tileSet;
    }

    public BufferedImage[][] loadTileArray() {
        tileArray = new BufferedImage[15][18];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 18; col++) {
                int tileSize = mainFrame.getTileSize();
                tileArray[row][col] = tileSet.getSubimage(row * tileSize, col * tileSize, tileSize, tileSize);
            }
        }
        return tileArray;
    }

    public String[][] loadMapData(String mapDataFileName) {
        File mapData = new File(mapDataFileName);
        Scanner s = null;
        try {
            s = new Scanner(mapData);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<>();
        while (s.hasNextLine()) {
            fileData.add(s.nextLine());
        }

        String[][] mapDataArr = new String[mainFrame.getNumTileHeight()][mainFrame.getNumTileWidth()];
        for (int i = 0; i < mapDataArr.length; i++) {
            mapDataArr[i] = fileData.get(i).split(" ");
        }
        return mapDataArr;
    }

    public void drawMap(Graphics g, String mapDataFileName) {
        String[][] mapDataArr = loadMapData(mapDataFileName);

        for (int row = 0; row < mapDataArr.length; row++) {
            for (int col = 0; col < mapDataArr[0].length; col++) {
                int tileNum = Integer.parseInt(mapDataArr[row][col]);
                int tileSheetY = 0;
                int tileSheetX = tileNum;

                while (tileSheetX > mainFrame.getNumTileWidth()) {
                    tileSheetY++;
                    tileSheetX -= mainFrame.getNumTileWidth();
                }

                finalTileSize = mainFrame.getTileSize() * mainFrame.getTileScale();
                g.drawImage(tileArray[tileSheetY][tileSheetX], col * finalTileSize, row * finalTileSize, finalTileSize, finalTileSize,  null);
            }
        }
    }

    public void update() {
        updateCollisionArr(loadMapData("data/mapData/map1"));
    }

    public void updateCollisionArr(String[][] mapDataArr) {
        for (int row = 0; row < mapDataArr.length; row++) {
            for (int col = 0; col < mapDataArr[0].length; col++) {
                int tileNum = Integer.parseInt(mapDataArr[row][col]);
                finalTileSize = mainFrame.getTileSize() * mainFrame.getTileScale();
                collisionArr[(row * 15) + col] = new Tile(col * finalTileSize, row * finalTileSize, finalTileSize, finalTileSize, tileNum);
            }
        }
    }

    public Tile[] getCollisionArr() {
        return collisionArr;
    }
}
