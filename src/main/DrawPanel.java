package main;

import entities.Player;
import mapManager.MapManager;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

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
        g.setColor(Color.white);
        player.draw(g);
        mapManager.drawMap(g);
    }
}
