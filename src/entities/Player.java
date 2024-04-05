package entities;

import main.MainFrame;
import java.awt.*;

public class Player extends Entity{
    private boolean movingLeft;
    private boolean movingRight;
    private boolean jumping;
    private boolean falling;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame) {
        super(x, y, velocityX, velocityY, mainFrame);
        int playerSize = mainFrame.getTileSize() * mainFrame.getTileScale();
        setHitbox(new Rectangle((int) x, (int) y, playerSize, playerSize));
        falling = true;
    }

    public void draw(Graphics g) {
        getHitbox().x = (int) getX();
        getHitbox().y = (int) getY();
        g.drawRect(getHitbox().x, getHitbox().y, getHitbox().width, getHitbox().height);
    }

    public void update() {
        updatePosition();
    }

    public void updatePosition() {
        if (movingLeft) {
            moveLeft();
        }
        if (movingRight) {
            moveRight();
        }

        if (jumping) {
            super.setVelocityY(-5);
            jump();
        }
        if (falling) {
            freeFall();
            setVelocityY(getVelocityY() + 0.15);;
        }
    }

    public void moveRight() {
        if ((getX() + getHitbox().getWidth() + 16) + getVelocityX() >= getMainFrame().getFrameWidth()) {
            setVelocityX(getMainFrame().getFrameWidth() - (getX() + getHitbox().getWidth() + 16));
        }
        setX(getX() + getVelocityX());
    }

    public void moveLeft() {
        if (getX() - getVelocityX() < 0) {
            setVelocityX(getX());
        }
        setX(getX() - getVelocityX());
    }

    public void jump() {
        setY(getY() + getVelocityY());
    }

    public void freeFall() {
        if ((getY() + getHitbox().getHeight() + 40) + getVelocityY() <= getMainFrame().getFrameHeight()) {
            setY(getY() + getVelocityY());
            setVelocityX(3);
        }
        else {
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