package entities;

import main.MainFrame;
import map.MapManager;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Entity{
    private int playerSize;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean jumping;
    private boolean falling;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager);
        playerSize = mainFrame.getTileSize() * mainFrame.getTileScale();
        setHitBox(new Rectangle((int) x, (int) y, playerSize, playerSize));
        falling = true;
    }

    public void draw(Graphics g) {
        getHitBox().x = (int) getX();
        getHitBox().y = (int) getY();
        g.fillRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);
    }

    public void update() {
        updatePosition();
    }

    public void updatePosition() {
        if (movingLeft && collisionCheck(getVelocityX() * -1, 0)) {
            moveLeft();
        }
        if (movingRight && collisionCheck(getVelocityX(), 0)) {
            moveRight();
        }

        if (jumping && collisionCheck(0, getVelocityY())) {
            jump();
        }
        if (falling && collisionCheck(0, getVelocityY())) {
            freeFall();
            setVelocityY(getVelocityY() + 0.15);
        }
    }

    public void moveRight() {
        setVelocityX(2);
        setX(getX() + getVelocityX());
    }

    public void moveLeft() {
        setVelocityX(2);
        setX(getX() - getVelocityX());
    }

    public void jump() {
        setVelocityY(-5);
        setY(getY() + getVelocityY());
    }

    public void freeFall() {
        if ((getY() + getHitBox().getHeight()) + getVelocityY() <= getMainFrame().getFrameHeight()) {
            setY(getY() + getVelocityY());
            setVelocityX(3);
        }
        else {
            setY(getMainFrame().getFrameHeight() - playerSize);
            falling = false;
            setVelocityY(0.25);
            setVelocityX(2);
        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}