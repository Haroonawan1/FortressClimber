package map;

import entities.Entity;
import entities.Player;
import main.MainFrame;
import javax.imageio.ImageIO;
import java.awt.*;
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

        loadTileSetImage(filePath);
        mapDataFileArr = loadMapDataFile("data/mapData/map1");
        tileSetArr = loadTileSetArr();
        collisionArr = new Tile[mapDataFileArr.length * mapDataFileArr[0].length];
        finalTileSize = mainFrame.getFinalTileSize();
        mapEntityCheck();
    }

    public void loadTileSetImage(String filePath) {
        tileSetImage = null;
        try {
            tileSetImage = ImageIO.read(new File(filePath));
        }
        catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    public BufferedImage[][] loadTileSetArr() {
        tileSetArr = new BufferedImage[18][15];
        for (int row = 0; row < 18; row++) {
            for (int col = 0; col < 15; col++) {
                int tileSize = mainFrame.getTileSize();
                tileSetArr[row][col] = tileSetImage.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize);
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


    public void mapEntityCheck() {
        for (int row = 0; row < mapDataFileArr.length; row++) {
            for (int col = 0; col < mapDataFileArr[0].length; col++) {
                int tileID = Integer.parseInt(mapDataFileArr[row][col]);

                if (tileID == 160 || tileID == 145) {
                    int x = col * finalTileSize;
                    int y = row * finalTileSize;

                    mainFrame.getEntities().add(new Entity(x, y, 0, 0, mainFrame, this, new Rectangle(x, y, finalTileSize, finalTileSize)));
                }

            }
        }
    }


    public void updateCollisionArr(String[][] mapDataFileArr) {
        for (int row = 0; row < mapDataFileArr.length; row++) {
            for (int col = 0; col < mapDataFileArr[0].length; col++) {
                int tileID = Integer.parseInt(mapDataFileArr[row][col]);
                int index = (row * mapDataFileArr[0].length) + col;

                Tile tile = new Tile((col * finalTileSize) - mainFrame.getXMapOffset(), (row * finalTileSize) - mainFrame.getYMapOffset(), finalTileSize, finalTileSize, tileID);

                if (tileID == 145 || tileID == 160) {
                    tile.setTileID(0);
                }

                collisionArr[index] = tile;
            }
        }
    }

    public void drawMap(Graphics g) {
        for (int row = 0; row < mapDataFileArr.length; row++) {
            for (int col = 0; col < mapDataFileArr[0].length; col++) {
                int tileSetCol = Integer.parseInt(mapDataFileArr[row][col]);
                int tileSetRow = 0;

//                if (tileSetCol == 160 || tileSetCol == 145) {
//                    tileSetCol = 0;
//                }

                while (tileSetCol > tileSetArr[0].length) {
                    tileSetRow++;
                    tileSetCol -= tileSetArr[0].length;

                }

                g.drawImage(tileSetArr[tileSetRow][tileSetCol], (col * finalTileSize) - mainFrame.getXMapOffset(), (row * finalTileSize) - mainFrame.getYMapOffset(), finalTileSize, finalTileSize,  null);
            }
        }
    }

    public void update() {
        //updateEntities();
        updateCollisionArr(loadMapDataFile("data/mapData/map1"));
    }

    public Tile[] getCollisionArr() {
        return collisionArr;
    }
}
