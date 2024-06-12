package main;

import entities.Entity;
import entities.Player;
import input.Keyboard;
import map.MapManager;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame implements Runnable {
    private MapManager mapManager;
    private Player player;
    private DrawPanel drawPanel;
    private Keyboard keyboard;
    private Thread thread;
    private ArrayList<Entity> entities;

    private int fps;
    private int ups;

    private int tileSize;
    private int tileScale;
    private int finalTileSize;
    private double playerSize;

    private int numTileWidth;
    private int numTileHeight;
    private int frameWidth;
    private int frameHeight;

    private int xBoundLeft;
    private int xBoundRight;
    private int yBoundTop;
    private int yBoundBottom;
    private int xMapOffset;
    private int yMapOffset;


    public MainFrame() {
        super("Fortress Climber");

        fps = 100;
        ups = 200;

        tileSize = 16;
        tileScale = 3;
        finalTileSize = tileSize * tileScale;
        playerSize = tileSize * 2.5;

        numTileHeight = 15;
        numTileWidth = 30;
        frameWidth = numTileWidth * finalTileSize;
        frameHeight = numTileHeight * finalTileSize;

        xBoundLeft = finalTileSize * 12;
        xBoundRight = frameWidth - xBoundLeft;
        yBoundTop = finalTileSize * 4;
        yBoundBottom = frameHeight - yBoundTop;
        xMapOffset = 0;
        yMapOffset = 0;

        entities = new ArrayList<>();

        mapManager = new MapManager("data/mapData/dungeonTileSet.png", this);
        player = new Player(600, 400, 0, 0, this, mapManager, new Rectangle(100, 100, (int) playerSize, (int) playerSize));
        drawPanel = new DrawPanel(player, mapManager, this);
        keyboard = new Keyboard(player);

        entities.add(player);


        this.add(drawPanel);
        this.addKeyListener(keyboard);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("data/miscData/fortressIcon.png").getImage());
        // not sure why I need to add these
        this.setSize(frameWidth + 14, frameHeight + 38);    // W: 1454
        this.setLocation(500, 200);
        this.setResizable(false);
        this.setVisible(true);

        startThread();
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void update() {
        mapManager.update();
        player.update();
    }

    // REMEMBER TO SAY YOU RIPPED THIS OFF
    public void run() {
        double timePerFrame = 1000000000.0 / fps;
        double timePerUpdate = 1000000000.0 / ups;
        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaU >= 1){
                update();
                deltaU--;
            }

            if (deltaF >= 1){
                drawPanel.repaint();
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
            }
        }
    }

    public int getNumTileWidth() {
        return numTileWidth;
    }

    public int getNumTileHeight() {
        return numTileHeight;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getTileScale() {
        return tileScale;
    }

    public int getFinalTileSize(){
        return finalTileSize;
    }

    public int getXBoundLeft() {
        return xBoundLeft;
    }

    public int getXBoundRight() {
        return xBoundRight;
    }

    public int getYBoundTop() {
        return yBoundTop;
    }

    public int getYBoundBottom() {
        return yBoundBottom;
    }

    public int getXMapOffset() {
        return xMapOffset;
    }

    public void setXMapOffset(int xMapOffset) {
        this.xMapOffset = xMapOffset;
    }

    public int getYMapOffset() {
        return yMapOffset;
    }

    public void setYMapOffset(int yMapOffset) {
        this.yMapOffset = yMapOffset;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}