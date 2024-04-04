import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private Player player;

    public Input(Player player) {
        this.player = player;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int change = player.getPlayerSpeed();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D -> player.updatePosition("e", change);
            case KeyEvent.VK_A -> player.updatePosition("w", change);
            case KeyEvent.VK_W -> player.updatePosition("n", change);
            case KeyEvent.VK_S -> player.updatePosition("s", change);

        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
