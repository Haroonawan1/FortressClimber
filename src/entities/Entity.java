package entities;

import main.MainFrame;
import map.MapManager;
import map.Tile;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Entity {
    private MainFrame mainFrame;
    private MapManager mapManager;
    private Rectangle hitBox;   

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    // going to use these later
    private double maxSpeedX;
    private double maxSpeedY;
    private double accelerationX;
    private double accelerationY;

    private boolean touchingFloor;
    private boolean touchingCeiling;
    private boolean touchingWallRight;
    private boolean touchingWallLeft;

    private boolean p1;
    private boolean p2;
    private boolean p3;
    private boolean p4;
    private ArrayList<Tile> collidingTiles;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingDown;
    private boolean falling;
    private boolean jumping;

    public Entity(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager, Rectangle hitBox) {
        this.mainFrame = mainFrame;
        this.mapManager = mapManager;
        this.hitBox = hitBox;

        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        p1 = false;
        p2 = false;
        p3 = false;
        p4 = false;
        collidingTiles = new ArrayList<>();

        movingLeft = false;
        movingRight = false;
        falling = true;
        jumping = false;
    }

    public void updateCollisionPoints() {
        String solidTileIDs = ":16:17:18:20:21:31:32:33:35:36:46:47:48:50:51:76:77:78:91:92:93:106:107:108:";
        for (Tile tile : mapManager.getCollisionArr()) {
            if (solidTileIDs.contains(":" + tile.getTileID() + ":")) {
                p1 = tile.getHitBox().contains(hitBox.getX() + velocityX, hitBox.getY() + velocityY);
                p2 = tile.getHitBox().contains(hitBox.getX() + hitBox.width + velocityX, hitBox.getY() + velocityY);
                p3 = tile.getHitBox().contains(hitBox.getX() + hitBox.width + velocityX, hitBox.getY() + hitBox.height + velocityY);
                p4 = tile.getHitBox().contains(hitBox.getX() + velocityX, hitBox.getY() + hitBox.height + velocityY);

                if (p1 || p2 || p3 || p4) {
                    collidingTiles.add(tile);
                    tile.setOutLined(true);
                }
            }
        }

        p1 = false;
        p2 = false;
        p3 = false;
        p4 = false;

        for (Tile tile : collidingTiles) {
            if (tile.getHitBox().contains(hitBox.getX() + velocityX, hitBox.getY() + velocityY)) {
                p1 = true;
            }
            if (tile.getHitBox().contains(hitBox.getX() + hitBox.width + velocityX, hitBox.getY() + velocityY)) {
                p2 = true;
            }
            if (tile.getHitBox().contains(hitBox.getX() + hitBox.width + velocityX, hitBox.getY() + hitBox.height + velocityY)) {
                p3 = true;
            }
            if (tile.getHitBox().contains(hitBox.getX() + velocityX, hitBox.getY() + hitBox.height + velocityY)) {
                p4 = true;
            }
        }

        //System.out.println(botLeft.getX() + " , " + botLeft.getY());
        //System.out.print("   p1: " + p1 + " p2: " + p2  + " p3: " + p3 + " p4: " + p4 );
        //System.out.println(" | falling: " + falling +  " | x: " + x  + " | y: " + y + " | velx: " + velocityX + " | vely: " + velocityY + " | wallRight: " + touchingWallRight + " | floor: " + touchingFloor + " | movingleft: " + movingLeft);
    }

    public void updateCollisionConstants() {
        if (p1 && p4) {
            double smallestX = collidingTiles.get(0).getHitBox().getX();
            for (Tile tile: collidingTiles) {
                if (tile.getHitBox().getX() < smallestX) {
                    smallestX = tile.getHitBox().getX();
                }
            }

            // Have to get rid of one because pixel placement is weird
            // Either everything is 1 pixel to the left (most likely not)
            // getting the X of a hitBox gives will not equate to the X in which you collide (more likely)
            x = smallestX + hitBox.width - 1;
            touchingWallLeft = true;
        }
        else if (p2 && p3) {
            double largestX = collidingTiles.get(0).getHitBox().getX();
            for (Tile tile: collidingTiles) {
                if (tile.getHitBox().getX() > largestX) {
                    largestX = tile.getHitBox().getX();
                }
            }

            x = largestX - hitBox.width;
            touchingWallRight = true;
        }
        else if (isTouchingWallRight() && (p2 || p3)) {
            touchingWallRight = true;
        }
        else if (isTouchingWallLeft() && (p1 || p4)) {
            touchingWallLeft = true;
        }
        else {
            touchingWallLeft = false;
            touchingWallRight = false;
        }


        if (((p3 || p4) && (!touchingWallRight && !touchingWallLeft)) || ((p1 || p2) && (p3 && p4))) {
            double biggestY = collidingTiles.get(0).getHitBox().getY();
            for (Tile tile: collidingTiles) {
                if (tile.getHitBox().getY() > biggestY) {
                    biggestY = tile.getHitBox().getY();
                }
            }

            y = biggestY - hitBox.height;
            touchingFloor = true;
            touchingCeiling = false;
        }
        else if ((p1 || p2) && (!touchingWallRight && !touchingWallLeft) && !movingDown) {
            velocityY = 0;

            y = collidingTiles.get(0).getHitBox().getY() + hitBox.height;
            touchingFloor = false;
            touchingCeiling = true;
        }
        else {
            touchingFloor = false;
            touchingCeiling = false;
        }

        emptyCollidingTiles();
    }

    public void emptyCollidingTiles() {
        for (int i = 0; i < collidingTiles.size(); i++) {
            collidingTiles.remove(i);
            i--;
        }
    }

    public double round(double num) {
        return Math.round(num * Math.pow(10, 2)) / Math.pow(10,2);
    }

    public boolean isTouchingFloor() {
        return touchingFloor;
    }

    public void setTouchingFloor(boolean touchingFloor) {
        this.touchingFloor = touchingFloor;
    }

    public boolean isTouchingCeiling() {
        return touchingCeiling;
    }

    public boolean isTouchingWallRight() {
        return touchingWallRight;
    }

    public boolean isTouchingWallLeft() {
        return touchingWallLeft;
    }

    public void setTouchingWallLeft(boolean touchingWallLeft) {
        this.touchingWallLeft = touchingWallLeft;
    }

    public void setTouchingWallRight(boolean touchingWallRight) {
        this.touchingWallRight = touchingWallRight;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = round(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = round(y);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = round(velocityX);
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = round(velocityY);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}