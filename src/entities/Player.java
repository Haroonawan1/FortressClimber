package entities;

import main.MainFrame;
import map.MapManager;
import java.awt.Rectangle;
import java.awt.Graphics;

public class Player extends Entity{
    private int jumpHeightCount;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager, Rectangle hitbox) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager, hitbox);
        setFalling(true);
        jumpHeightCount = 0;
    }

    public void updatePosition() {

        if (isTouchingWall() || (!isMovingLeft() && !isMovingRight())) {
            setVelocityX(0);
        }
        else if (isMovingLeft()) {
            moveLeft();
        }
        else if (isMovingRight()) {
            moveRight();
        }

        if (isTouchingWall()) {
            sliding();
        }



        if ((!isTouchingFloor() && !isJumping() && !isTouchingWall()) || isTouchingCeiling()) {
            setFalling(true);
        }
        else {
            setVelocityY(0);
            setFalling(false);
        }

        if (isJumping()) {
            jump(144);
        }

        if (isFalling()) {
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
        //setTouchingFloor(false);
        if (jumpHeightCount + getVelocityY() < -limit) {
            setJumping(false);
            jumpHeightCount = 0;
        }
        if (isTouchingCeiling()) {
            setJumping(false);
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
}