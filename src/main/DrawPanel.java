package main;

import entities.Player;
import map.MapManager;
import map.Tile;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Arrays;

public class DrawPanel extends JPanel {
    private Player player;
    private MapManager mapManager;

    public DrawPanel(Player player, MapManager mapManager) {
        this.player = player;
        this.mapManager = mapManager;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.blue);
        mapManager.drawMap(g);

        for (Tile tile : mapManager.getCollisionArr()) {
            if (tile.isOutLined()) {
                g.fillRect(tile.getHitBox().x, tile.getHitBox().y, tile.getHitBox().width, tile.getHitBox().height);
            }
            tile.setOutLined(false);
        }

        player.draw(g);
    }
}
