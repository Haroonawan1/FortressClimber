package entities;

import main.MainFrame;
import map.MapManager;
import map.Tile;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity{
    private boolean sliding;
    private boolean climbing;
    private boolean touchedCorner;

    private double jumpHeightCount;
    private double superJumpCount;
    private boolean shouldWallJump;
    private boolean shouldSuperJump;
    private boolean justJumped;

    private MainFrame mainFrame;
    private boolean xScreenMoving;
    private boolean yScreenMoving;

    private BufferedImage playerAniSheet;
    private BufferedImage[][] playerAniArr;

    private int selectedAni;
    private int aniLength;
    private int currentAniSprite;
    private int aniCount;

    private boolean facingRight;
    private boolean facingLeft;

    public Player(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager, Rectangle hitbox) {
        super(x, y, velocityX, velocityY, mainFrame, mapManager, hitbox);
        this.mainFrame = mainFrame;
        setFalling(true);
        jumpHeightCount = 0;
        shouldWallJump = false;

        loadPlayerAniSheet();
        loadPlayerAniArr();
    }


    public void updateAcceleration() {
        if (isFalling()) {
            setAccelerationY(0.15);
        }
        else if (sliding) {
            setAccelerationY(0.005);
        }
        else if (isJumping()) {
            setAccelerationY(-2);
        }
        else {
            setAccelerationY(0);
        }

        if (!isTouchingFloor()) {
            if (isMovingRight() || isMovingLeft() && !(isTouchingWallLeft() || isTouchingWallRight()) && !climbing) {
                if (shouldSuperJump) {
                    setAccelerationX(0.05);
                }
                else {
                    setAccelerationX(0.01);
                }
            }
            else if ((isTouchingWallLeft() || isTouchingWallRight()) && !(isMovingRight() || isMovingLeft())) {
                setAccelerationX(0);
            }
            else if (isTouchingWallLeft() && isMovingRight()) {
                setAccelerationX(1.5);
            }
            else if (isTouchingWallRight() && isMovingLeft()) {
                setAccelerationX(1.5);
            }
            if (!sliding) {
                justJumped = true;
            }

        }
        else {
            if (isMovingRight() || isMovingLeft() || justJumped) {
                setAccelerationX(0.1);
            }
            else if (!isMovingRight() && !isMovingLeft() && getVelocityX() == 0){
                setAccelerationX(0);
            }
        }
    }

    public void updatePosition() {
        interactableCheck();
        updateMaxSpeeds();
        updateScreenMovement();

        if (!climbing) {
            updateYAxisMovement();
        }
        updateXAxisMovement();

        emptyInteractableTiles();
    }


    public void interactableCheck() {
        if (getInteractables().isEmpty()) {
            climbing = false;
        }

        for (Tile tile : getInteractables()) {
            if ((tile.getTileID() == 140 || tile.getTileID() == 170) && isMovingUp()) {
                System.out.println("hi");
                climbing = true;
                climb();
            }
            else {
                climbing = false;
            }

        }
    }

    public void updateMaxSpeeds() {
        if (isTouchingFloor()) {
            setMaxXSpeed(2);
        }
        else {
            setMaxXSpeed(2);
        }
    }

    public void updateScreenMovement() {
        if (getHitBox().x + getHitBox().width >= mainFrame.getXBoundRight() && getVelocityX() > 0) {
            xScreenMoving = true;
            mainFrame.setXMapOffset((int) (mainFrame.getXMapOffset() + getVelocityX()));
        }
        else if (getHitBox().x <= mainFrame.getXBoundLeft() && getVelocityX() < 0) {
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
    }

    public void updateXAxisMovement() {
        if (((((isTouchingWallRight() && isMovingRight()) || (isTouchingWallLeft() && isMovingLeft())) && !isTouchingFloor()) || (isMovingDown() && isTouchingFloor())) ) {
            setVelocityX(0);
        }
        else if (!isMovingRight() && !isMovingLeft()) {
            stop();
        }
        else if (isMovingLeft() && isTouchingWallRight() && sliding) {
            if (isJumping()) {
                moveLeft();
            }
        }
        else if (isMovingRight() && isTouchingWallLeft() && sliding) {
            if (isJumping()) {
                moveRight();
            }
        }
        else if (isMovingLeft() && !isTouchingWallLeft()) {
            moveLeft();
        }
        else if (isMovingRight() && !isTouchingWallRight()) {
            moveRight();
        }
    }

    public void updateYAxisMovement() {

        if (isTouchingCorner()) {
            touchedCorner = true;
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
            if (superJumpCount == 150) {
                shouldSuperJump = true;
                superJumpCount = 0;
            }

        }
        else {
            superJumpCount = 0;
        }
        if (shouldSuperJump && !isTouchingCeiling()) {
            jump(2.25, 6);
        }

        if ((isTouchingWallRight() || isTouchingWallLeft()) && !touchedCorner) {
            shouldWallJump = true;
        }

        if (isJumping() && shouldWallJump) {
            if (((isMovingRight() && !isTouchingWallRight()) || (isMovingLeft() && !isTouchingWallLeft())) && !(isMovingRight() && isMovingLeft())) {
                jump(.5, 3);
            }
        }
        else if (isJumping() && !isMovingDown()) {
            jump(1, 3);
        }

        if (isTouchingFloor() || (isTouchingWallLeft() || isTouchingWallRight())) {
            jumpHeightCount = 0;
        }

    }

    public void stop() {
        if (justJumped && isTouchingFloor()) {
            setVelocityX(round(getVelocityX(), 1));
            justJumped = false;
        }

        if (getVelocityX() != 0) {
            if (getVelocityX() < 0) {
                setVelocityX(round(getVelocityX() + getAccelerationX(), 3));
            }
            if (getVelocityX() > 0) {
                setVelocityX(round(getVelocityX() - getAccelerationX(), 3));
            }
        }

        if (!xScreenMoving) {
            setX(getX() + getVelocityX());
        }

    }

    public void moveRight() {
        facingLeft = false;
        facingRight = true;

        if (getVelocityX() > getMaxXSpeed()) {
            setVelocityX(getMaxXSpeed());
        }
        if (getVelocityX() < getMaxXSpeed()) {
            setVelocityX(getVelocityX() + getAccelerationX());
            if (getVelocityX() > getMaxXSpeed()) {
                setVelocityX(getMaxXSpeed());
            }
        }

        if (!xScreenMoving) {
            setX(getX() + getVelocityX());
        }

    }

    public void moveLeft() {
        facingLeft = true;
        facingRight = false;

        if (getVelocityX() < -getMaxXSpeed()) {
            setVelocityX(-getMaxXSpeed());
        }
        if (getVelocityX() > -getMaxXSpeed()) {
            setVelocityX(getVelocityX() - getAccelerationX());
            if (getVelocityX() < -getMaxXSpeed()) {
                setVelocityX(-getMaxXSpeed());
            }
        }

        if (!xScreenMoving) {
            setX(getX() + getVelocityX());
        }
    }

    public void jump(double tileJumpHeight, int jumpSpeed) {
        setVelocityY(-jumpSpeed);
        double pixelAmt = tileJumpHeight * mainFrame.getFinalTileSize();

        jumpHeightCount += getVelocityY();
        if (jumpHeightCount < -pixelAmt) {
            setJumping(false);
            shouldSuperJump = false;
        }
        if (isTouchingCeiling()) {
            setJumping(false);
        }

        if (!yScreenMoving && isJumping()) {
            setY(getY() + getVelocityY());
        }
    }

    public void climb() {
        setVelocityY(-1);

        if (!yScreenMoving) {
            setY(getY() + getVelocityY());
        }
    }

    public void freeFall() {
        setVelocityY(getVelocityY() + getAccelerationY());

        if (!yScreenMoving) {
            setY(getY() + getVelocityY());
        }
    }

    public void slide() {
        if (justJumped && getVelocityY() != 0) {
            setVelocityY(0);
            justJumped = false;
        }

        setVelocityY(getVelocityY() + getAccelerationY());

        if (!yScreenMoving) {
            setY(getY() + getVelocityY());
        }
    }

    public void draw(Graphics g) {
        getHitBox().x = (int) getX();
        getHitBox().y = (int) getY();

        g.setColor(Color.WHITE);
        //g.drawRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);
        g.drawImage(playerAniArr[selectedAni][currentAniSprite], getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height, null);
    }


    private void loadPlayerAniSheet() {
        playerAniSheet = null;
        try {
            playerAniSheet = ImageIO.read(new File("data/entityData/playerSprite.png"));
        }
        catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    private void loadPlayerAniArr() {
        playerAniArr = new BufferedImage[10][6];

        for (int row = 0; row < playerAniArr.length; row++) {
            for (int col = 0; col < playerAniArr[0].length; col++) {
                playerAniArr[row][col] = playerAniSheet.getSubimage(col * 8, row * 8, 8, 8);
            }
        }
    }

    private void updateSelectedAni() {
        int originalSelectedAni = selectedAni;

        if (climbing) {
            selectedAni = 9;
            aniLength = 2;
        }
        else if (isTouchingWallLeft() && sliding) {
            selectedAni = 8;
            aniLength = 2;
        }
        else if (isTouchingWallRight() && sliding) {
            selectedAni = 7;
            aniLength = 2;
        }
        else if (getVelocityY() < 0 && facingLeft) {
            selectedAni = 2;
            aniLength = 1;
        }
        else if (getVelocityY() < 0 && facingRight) {
            selectedAni = 1;
            aniLength = 1;
        }
        else if (getVelocityY() > 0 && facingRight) {
            selectedAni = 3;
            aniLength = 1;
        }
        else if (getVelocityY() > 0 && facingLeft) {
            selectedAni = 4;
            aniLength = 1;
        }
        else if (isMovingRight() && !isMovingDown()) {
            selectedAni = 0;
            aniLength = 5;
        }

        else if (isMovingLeft() && !isMovingDown()) {
            selectedAni = 6;
            aniLength = 5;
        }
        else {
            selectedAni = 5;
            aniLength = 6;
        }

        if (selectedAni != originalSelectedAni) {
            currentAniSprite = 0;
        }
    }

    private void updateCurrentAniSprite() {
        aniCount++;
        if (aniCount > 30) {
            currentAniSprite++;
            if (currentAniSprite >= aniLength) {
                currentAniSprite = 0;
            }
            aniCount = 0;
        }
    }

    public void updateEntities() {
        for (Entity entity : mainFrame.getEntities()) {
            if (!(entity instanceof Player)) {
                entity.setVelocityY(getVelocityY());
                entity.setVelocityX(getVelocityX());
            }
        }
    }

    public void update() {
        updateCollisionPoints();
        updateCollisionConstants();

        updateSelectedAni();
        updateCurrentAniSprite();

        updateEntities();
        updateAcceleration();
        updatePosition();

        //System.out.println("acelx: " + getAccelerationX() + " | velx: " + getVelocityX() + " | acely: " + getAccelerationY() + " | vely: " + getVelocityY() +  " | ceiling: " + isTouchingCeiling() + " | jump: " + isJumping() + " | sliding: " + sliding + " | touchingwall: " + (isTouchingWallRight() || isTouchingWallLeft()));
    }
}