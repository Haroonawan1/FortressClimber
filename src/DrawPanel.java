import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class DrawPanel extends JPanel {
    private Player player;

    public DrawPanel(Player player) {
        this.player = player;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.white);
        g.drawRect((int) player.getX(), (int) player.getY(), player.getHitBoxSize(), player.getHitBoxSize());
    }
}
