package entities;

import main.MainFrame;
import map.MapManager;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Player extends Entity{
    private boolean sliding;
    private boolean touchedCorner;

    private double jumpHeightCount;
    private double superJumpCount;
    private boolean shouldWallJump;
    private boolean shouldSuperJump;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager, Rectangle hitbox) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager, hitbox);
        setFalling(true);
        jumpHeightCount = 0;
        shouldWallJump = false;
    }

    public void updatePosition() {
        if (isTouchingCorner()) {
            touchedCorner = true;
        }


        if ((((isTouchingWallRight() && isMovingRight()) || (isTouchingWallLeft() && isMovingLeft())) && !isTouchingFloor()) || (isMovingDown() && isTouchingFloor())) {
            setVelocityX(0);
        }
        else if ((!isMovingRight() && !isMovingLeft())) {
            stop();
        }
        else if (isMovingLeft() && isTouchingWallRight() && sliding) {
            if (isJumping()) {
                moveLeft(2, 0.2);
            }
        }
        else if (isMovingRight() && isTouchingWallLeft() && sliding) {
            if (isJumping()) {
                moveRight(2, 0.2);
            }
        }
        else if (isMovingLeft() && !isTouchingWallLeft()) {
            moveLeft(2, 0.2);
        }
        else if (isMovingRight() && !isTouchingWallRight()) {
            moveRight(2, 0.2);
        }


        if ((!isTouchingFloor() && !isJumping() && ((!isTouchingWallRight() && !isTouchingWallLeft()) || isMovingDown())) || isTouchingCeiling()) {
            shouldWallJump = false;
            setFalling(true);
            freeFall();
        }
        else {
            if (!sliding) {
                setVelocityY(0);
            }
            setFalling(false);
        }


        if ((isTouchingWallRight() || isTouchingWallLeft()) && !isTouchingFloor() && !isMovingDown() && !touchedCorner) {
            sliding = true;
            slide();
        }
        else {
            sliding = false;
        }


        if (isMovingDown()) {
            superJumpCount += 1;
            if (superJumpCount > 150) {
                shouldSuperJump = true;
            }
        }
        else {
            superJumpCount = 0;
        }
        if (shouldSuperJump && !isTouchingCeiling()) {
            jump(144, 4);
            superJumpCount = 0;
        }

        if ((isTouchingWallRight() || isTouchingWallLeft()) && !touchedCorner) {
            shouldWallJump = true;
        }

        if (isJumping() && shouldWallJump) {
            if (((isMovingRight() && !isTouchingWallRight()) || (isMovingLeft() && !isTouchingWallLeft())) && !(isMovingRight() && isMovingLeft())) {
                jump(48, 4);
            }
        }
        else if (isJumping() && !isMovingDown()) {
            jump(96, 4);
        }

    }

    public void stop() {
        if (getVelocityX() != 0) {
            if (getVelocityX() < 0) {
                setVelocityX(getVelocityX() + 0.2);
            }
            if (getVelocityX() > 0) {
                setVelocityX(getVelocityX() - 0.2);
            }
        }

        setX(getX() + getVelocityX());
    }

    public void moveRight(int maxSpeed, double delta) {
        if (getVelocityX() < maxSpeed) {
            setVelocityX(getVelocityX() + delta);
            if (getVelocityX() > maxSpeed) {
                setVelocityX(maxSpeed);
            }
        }

        setX(getX() + getVelocityX());
    }

    public void moveLeft(int maxSpeed, double delta) {
        if (getVelocityX() > -maxSpeed) {
            setVelocityX(getVelocityX() - delta);
            if (getVelocityX() < -maxSpeed) {
                setVelocityX(maxSpeed);
            }
        }

        setX(getX() + getVelocityX());
    }

    public void jump(int jumpHeightLimit, int jumpSpeed) {
        setVelocityY(-jumpSpeed);
        setY(getY() + getVelocityY());

        jumpHeightCount += getVelocityY();
        if (jumpHeightCount + getVelocityY() < -jumpHeightLimit) {
            setJumping(false);
            jumpHeightCount = 0;
            shouldSuperJump = false;
        }
        if (isTouchingCeiling()) {
            setVelocityY(0);
            setJumping(false);
        }
    }

    public void freeFall() {
        setVelocityY(getVelocityY() + 0.15);
        setY(getY() + getVelocityY());
    }


    public void slide() {
        setVelocityY(getVelocityY() + 0.01);
        setY(getY() + getVelocityY());
    }

    public void draw(Graphics g) {
        //System.out.println("canSlide: " + canSlide() + " | touchingfloor: " + isTouchingFloor() + " | velx: " + getVelocityX() +  " | vely: " + getVelocityY());
        //System.out.println("x: " + getX() + " | y: " + getY() + " | hitboxX: " + getHitBox().x + " | hitboxY: " + getHitBox().y +  " | xvel: " + getVelocityX() + " | yvel: " + getVelocityY() + " | left: " + isMovingLeft() + " | right: " + isMovingRight() + " | down: " + isMovingDown());
        //System.out.println("x: " + getX() + " | y: " + getY() +  " | xvel: " + getVelocityX() + " | yvel: " + getVelocityY() + " | superjumpcount: " + superJumpCount + " | should shuperjump : " + shouldSuperJump);
        getHitBox().x = (int) getX();
        getHitBox().y = (int) getY();

        g.setColor(Color.RED);
        g.fillRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);
    }

    public void update() {
        updateCollisionPoints();
        updateCollisionConstants();
        updatePosition();
    }
}