package entities;

import main.MainFrame;

import java.awt.*;

public class Player {
    private MainFrame mainFrame;
    private Rectangle hitBox;
    private double velocityX;
    private double velocityY;
    private double x;
    private double y;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean jumping;
    private boolean falling;


    public Player(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        falling = true;
        int playerSize = mainFrame.getTileSize() * mainFrame.getTileScale();
        System.out.println(mainFrame.getTileSize());
        System.out.println(mainFrame.getTileScale());
        hitBox = new Rectangle((int) x, (int) y, playerSize, playerSize);
        velocityX = 2;
        velocityY = 0.25;
        x = 200;
        y = 300;
    }

    public void render(Graphics g) {
        hitBox.x = (int) x;
        hitBox.y = (int) y;
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
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
            velocityY = -5;
            jump();
        }
        if (falling) {
            freeFall();
            velocityY += 0.15;
        }
    }

    public void moveRight() {
        if ((x + hitBox.getWidth() + 16) + velocityX >= mainFrame.getFrameWidth()) {
            velocityX = mainFrame.getFrameWidth() - (x + hitBox.getWidth() + 16);
        }
        x += velocityX;
    }

    public void moveLeft() {
        if (x - velocityX < 0) {
            velocityX = x;
        }
        x -= velocityX;
    }

    public void jump() {
        y += velocityY;
    }

    public void freeFall() {
        if ((y + hitBox.getHeight() + 40) + velocityY <= mainFrame.getFrameHeight()) {
            y += velocityY;
            velocityX = 3;
        }
        else {
            falling = false;
            velocityY = 0.25;
            velocityX = 2;
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

    public Rectangle getHitBox() {
        return hitBox;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}