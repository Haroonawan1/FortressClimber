package entities;

import main.MainFrame;
import map.MapManager;
import java.awt.Rectangle;
import java.awt.Graphics;

public class Player extends Entity{
    private boolean movingLeft;
    private boolean movingRight;
    private boolean jumping;
    private boolean falling;
    private int jumpHeightCount;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager, Rectangle hitbox) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager, hitbox);
        falling = true;
        jumpHeightCount = 0;
    }

    public void updatePosition() {

        if (isTouchingWall()) {
            setVelocityX(0);
        }
        else if (movingLeft) {
            moveLeft();
        }
        else if (movingRight) {
            moveRight();
        }

        if (isTouchingWall() && getHitBox().y != 575) {
            sliding();
        }


        if (jumping) {
            jump(144);

        }


        if ((!isTouchingFloor() && !jumping && !isTouchingWall()) || isTouchingCeiling()) {
            falling = true;
        }
        else {
            setVelocityY(0);
            falling = false;
        }

        if (falling) {
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

    public void jump(int limit) {
        setVelocityY(-4);
        setY(getY() + getVelocityY());
        jumpHeightCount += (int) getVelocityY();
        setTouchingFloor(false);
        if (jumpHeightCount + getVelocityY() < -limit) {
            jumping = false;
            jumpHeightCount = 0;
        }
        if (isTouchingCeiling()) {
            jumping = false;
        }
    }

    public void freeFall() {
        setVelocityY(getVelocityY() + 0.15);
        setY(getY() + getVelocityY());
    }


    // currently you are not linearly speeding up as you fall because the logic for setting falling is setting velocityY to 0
    public void sliding() {
        setVelocityY(getVelocityY() + 0.1);
        setY(getY() + getVelocityY());
    }

    public void draw(Graphics g) {
        //System.out.println("canSlide: " + canSlide() + " | touchingfloor: " + isTouchingFloor() + " | velx: " + getVelocityX() +  " | vely: " + getVelocityY());
        //System.out.println("x: " + getX() + " | y: " + getY() + " | xvel: " + getVelocityX() + " | yvel: " + getVelocityY() + " | left: " + movingLeft + " | right: " + movingRight + " | wall: " + isTouchingWall());
        getHitBox().x = (int) getX();
        getHitBox().y = (int) getY();
        g.fillRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);
    }

    public void update() {
        updateCollisionPoints();
        updateCollisionConstants();
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