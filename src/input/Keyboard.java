package input;

import entities.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private Player player;

    public Keyboard(Player player) {
        this.player = player;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                player.setMovingUp(true);
                if ((player.isTouchingFloor() || player.isTouchingWallRight() || player.isTouchingWallLeft()) && !player.isJumping()) {
                    player.setJumping(true);
                }
            }
            case KeyEvent.VK_A -> player.setMovingLeft(true);
            case KeyEvent.VK_S -> player.setMovingDown(true);
            case KeyEvent.VK_D -> player.setMovingRight(true);

        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                player.setJumping(false);
                player.setMovingUp(false);
            }
            case KeyEvent.VK_A -> player.setMovingLeft(false);
            case KeyEvent.VK_S -> player.setMovingDown(false);
            case KeyEvent.VK_D -> player.setMovingRight(false);
        }
    }
}
