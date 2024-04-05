package main;

import entities.Player;
import input.Input;
import main.DrawPanel;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel drawPanel;
    private Player player;
    private Input input;
    private Thread thread;

    private int fps;
    private int ups;
    private int numTileWidth;
    private int numTileHeight;
    private int tileSize;
    private int tileScale;
    private int frameWidth;
    private int frameHeight;

    public MainFrame() {
        super("Fortress Climber");

        fps = 120;
        ups = 200;
        numTileHeight = 10;
        numTileWidth = 15;
        tileSize = 16;
        tileScale = 3;
        frameWidth = numTileWidth * (tileSize * tileScale);
        frameHeight = numTileHeight * (tileSize * tileScale);

        player = new Player(this);
        drawPanel = new DrawPanel(player);
        input = new Input(player);


        this.add(drawPanel);
        this.addKeyListener(input);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(600, 250);
        this.setResizable(false);
        this.setVisible(true);

        startThread();
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void update() {
        player.update();
    }

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

    public int getTileSize() {
        return tileSize;
    }

    public int getTileScale() {
        return tileScale;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }
}