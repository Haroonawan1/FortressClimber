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

    private MainFrame mainFrame;
    private boolean xScreenMoving;
    private boolean yScreenMoving;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager, Rectangle hitbox) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager, hitbox);
        this.mainFrame = mainFrame;
        setFalling(true);
        jumpHeightCount = 0;
        shouldWallJump = false;
    }

    public void updatePosition() {

        if (getHitBox().x + getHitBox().width >= mainFrame.getXBoundRight() && isMovingRight()) {
            xScreenMoving = true;
            mainFrame.setXMapOffset((int) (mainFrame.getXMapOffset() + getVelocityX()));
        }
        else if (getHitBox().x <= mainFrame.getXBoundLeft() && isMovingLeft()) {
            xScreenMoving = true;
            mainFrame.setXMapOffset((int) (mainFrame.getXMapOffset() + getVelocityX()));
        }
        else {
            xScreenMoving = false;
        }


        if (getHitBox().y <= mainFrame.getYBoundTop() && getVelocityY() < 0) {
            yScreenMoving = true;
            mainFrame.setYMapOffset((int) (mainFrame.getYMapOffset() + getVelocityY()));
        }
        else if (getHitBox().y + getHitBox().height >= mainFrame.getYBoundBottom() && getVelocityY() > 0) {
            yScreenMoving = true;
            mainFrame.setYMapOffset((int) (mainFrame.getYMapOffset() + getVelocityY()));
        }
        else {
            yScreenMoving = false;
        }

        System.out.println("yoffset: " + mainFrame.getYMapOffset() + " | y: " + getY());

        if (isTouchingCorner()) {
            touchedCorner = true;
        }


        if (((((isTouchingWallRight() && isMovingRight()) || (isTouchingWallLeft() && isMovingLeft())) && !isTouchingFloor()) || (isMovingDown() && isTouchingFloor())) ) {
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

        if (!xScreenMoving) {
            setX(getX() + getVelocityX());
        }

    }

    public void moveLeft(int maxSpeed, double delta) {
        if (getVelocityX() > -maxSpeed) {
            setVelocityX(getVelocityX() - delta);
            if (getVelocityX() < -maxSpeed) {
                setVelocityX(maxSpeed);
            }
        }

        if (!xScreenMoving) {
            setX(getX() + getVelocityX());
        }
    }

    public void jump(int jumpHeightLimit, int jumpSpeed) {
        setVelocityY(-jumpSpeed);

        if (!yScreenMoving) {
            setY(getY() + getVelocityY());
        }


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

        if (!yScreenMoving) {
            setY(getY() + getVelocityY());
        }
    }


    public void slide() {
        setVelocityY(getVelocityY() + 0.01);

        if (!yScreenMoving) {
            setY(getY() + getVelocityY());
        }
    }

    public void draw(Graphics g) {
        //System.out.println("canSlide: " + canSlide() + " | touchingfloor: " + isTouchingFloor() + " | velx: " + getVelocityX() +  " | vely: " + getVelocityY());
        //System.out.println("x: " + getX() + " | y: " + getY() + " | hitboxX: " + getHitBox().x + " | hitboxY: " + getHitBox().y +  " | xvel: " + getVelocityX() + " | yvel: " + getVelocityY() + " | left: " + isMovingLeft() + " | right: " + isMovingRight() + " | down: " + isMovingDown());
        //System.out.println("x: " + getX() + " | y: " + getY() +  " | xvel: " + getVelocityX() + " | yvel: " + getVelocityY() + " | screenMoving: " + screenMoving + " | offset : " + mainFrame.getMapOffset());
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