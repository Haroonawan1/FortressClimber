package entities;

import main.MainFrame;
import map.MapManager;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Entity{
    private boolean movingLeft;
    private boolean movingRight;
    private boolean jumping;
    private boolean falling;
    private int jumpHeightCount;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager);
        int playerSize = mainFrame.getFinalTileSize();
        setHitBox(new Rectangle((int) x, (int) y, playerSize, playerSize));
        falling = true;
        jumpHeightCount = 0;
    }

    public void updatePosition() {
        if (movingLeft && collisionCheck()) {
            moveLeft();
        }
        if (movingRight && collisionCheck()) {
            moveRight();
        }

        if (isTouchingFloor()) {
            setVelocityY(0);
            falling = false;
        }
        if ((!isTouchingFloor() && !jumping) || isTouchingCeiling()) {
            falling = true;
        }

        if (jumping && collisionCheck()) {
            jump();
            jumpHeightCount += getVelocityY();
        }
        if (jumpHeightCount + getVelocityY() < -144) {
            jumping = false;
            jumpHeightCount = 0;
        }
        if (isTouchingCeiling()) {
            jumping = false;
            //jumpHeightCount = 0;
        }
        if (!jumping) {
            jumpHeightCount = 0;
        }

        if (falling && collisionCheck()) {
            freeFall();
        }

    }

    public void moveRight() {
        setVelocityX(2);
        setX(getX() + getVelocityX());
    }

    public void moveLeft() {
        setVelocityX(-2);
        setX(getX() + getVelocityX());
    }

    public void jump() {
        setVelocityY(-4);
        setY(getY() + getVelocityY());
    }

    public void freeFall() {
        setVelocityY(getVelocityY() + 1);
        setY(getY() + getVelocityY());
    }

    public void draw(Graphics g) {
        System.out.println("x: " + getX() + " | y: " + getY() + " | xvel: " + getVelocityX() + " | yvel: " + getVelocityY() + " | jumpcount: " + jumpHeightCount + " | jumping: " + jumping + " | touchingflorr: " + isTouchingFloor());
        getHitBox().x = (int) getX();
        getHitBox().y = (int) getY();
        g.fillRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);
    }

    public void update() {
        updatePosition();
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}