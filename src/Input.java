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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D -> player.changeX(10);
            case KeyEvent.VK_A -> player.changeX(-10);
            case KeyEvent.VK_W -> player.changeY(-10);
            case KeyEvent.VK_S -> player.changeY(10);

        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
