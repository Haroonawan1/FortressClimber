package main;

import entities.Player;
import map.MapManager;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel drawPanel;
    private MapManager mapManager;
    private Player player;
    private Input input;
    private Thread thread;
    private int fps;
    private int ups;
    private int numTileWidth;
    private int numTileHeight;
    private int tileSize;
    private int tileScale;
    private int finalTileSize;
    private int frameWidth;
    private int frameHeight;

    public MainFrame() {
        super("Fortress Climber");

        fps = 120;
        ups = 200;
        numTileHeight = 15;
        numTileWidth = 30;
        tileSize = 16;
        tileScale = 3;
        finalTileSize = tileSize * tileScale;
        frameWidth = numTileWidth * (tileSize * tileScale);
        frameHeight = numTileHeight * (tileSize * tileScale);

        mapManager = new MapManager("data/mapData/dungeonTileSet.png", this);
        player = new Player(100, 100, 0, 0, this, mapManager);
        drawPanel = new DrawPanel(player, mapManager);
        input = new Input(player);

        this.add(drawPanel);
        this.addKeyListener(input);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("data/miscData/fortressIcon.png").getImage());
        // not sure why I need to add these
        this.setSize(frameWidth + 14, frameHeight + 38);
        this.setLocation(300, 200);
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
}