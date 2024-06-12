package main;

import entities.Entity;
import entities.Player;
import map.MapManager;
import map.Tile;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class DrawPanel extends JPanel {
    private Player player;
    private MapManager mapManager;
    private MainFrame mainFrame;

    public DrawPanel(Player player, MapManager mapManager, MainFrame mainFrame) {
        this.player = player;
        this.mapManager = mapManager;
        this.mainFrame = mainFrame;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color bgColor = new Color(24, 20, 37);
        this.setBackground(bgColor);
        mapManager.drawMap(g);

//        for (Tile tile : mapManager.getCollisionArr()) {
//            if (tile.isOutLined()) {
//                g.fillRect(tile.getHitBox().x, tile.getHitBox().y, tile.getHitBox().width, tile.getHitBox().height);
//            }
//            tile.setOutLined(false, "");
//        }
//
//        for (Entity entity : mainFrame.getEntities()) {
//            g.fillRect(entity.getHitBox().x, entity.getHitBox().y, entity.getHitBox().width, entity.getHitBox().height);
//        }

        player.draw(g);
    }
}
