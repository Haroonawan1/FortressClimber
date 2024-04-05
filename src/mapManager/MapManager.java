package mapManager;

import main.MainFrame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapManager {
    private MainFrame mainFrame;
    private BufferedImage tileSet;
    private BufferedImage[][] tileArray;

    public MapManager(String filePath, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        tileSet = loadTileSet(filePath);
        tileArray = loadTileArray();

    }

    public BufferedImage loadTileSet(String filePath) {
        tileSet = null;
        try {
            return tileSet = ImageIO.read(new File(filePath));
        }
        catch (IOException e) {
            System.out.println("Your file failed");
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

    public void drawMap(Graphics g) {
        g.drawImage(tileArray[0][0], 0, 0, null);
    }

}
