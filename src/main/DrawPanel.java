package main;

import entities.Player;
import map.MapManager;
import javax.swing.JPanel;
import java.awt.*;

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
        g.setColor(Color.red);
        mapManager.drawMap(g, "data/mapData/map1");
        player.draw(g);
    }
}
