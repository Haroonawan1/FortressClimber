package main;

import entities.Player;
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
            case KeyEvent.VK_D -> player.setMovingRight(true);
            case KeyEvent.VK_A -> player.setMovingLeft(true);
            case KeyEvent.VK_W -> {
                if (player.isTouchingFloor() && !player.isJumping()) {
                    player.setJumping(true);
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D -> {
                player.setMovingRight(false);
                player.setVelocityX(0);
            }
            case KeyEvent.VK_A -> {
                player.setMovingLeft(false);
                player.setVelocityX(0);
            }
            case KeyEvent.VK_W -> {
                //player.setJumping(false);
                player.setTouchingFloor(false);
            }
        }
    }
}
