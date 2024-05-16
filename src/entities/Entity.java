package entities;

import main.MainFrame;
import map.MapManager;
import map.Tile;
import java.awt.Rectangle;
import java.awt.Point;

public class Entity {
    private MainFrame mainFrame;
    private MapManager mapManager;
    private Rectangle hitBox;

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    private boolean touchingFloor;
    private boolean touchingCeiling;
    private boolean touchingWall;

    private Point topLeft;
    private Point topRight;
    private Point botRight;
    private Point botLeft;

    private boolean p1;
    private boolean p2;
    private boolean p3;
    private boolean p4;

    private boolean movingLeft;
    private boolean movingRight;
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

        topLeft = new Point(hitBox.x, hitBox.y);
        topRight = new Point(hitBox.x + hitBox.width, hitBox.y);
        botRight = new Point(hitBox.x + hitBox.width, hitBox.y + hitBox.height);
        botLeft = new Point(hitBox.x, hitBox.y + hitBox.height);

        p1 = false;
        p2 = false;
        p3 = false;
        p4 = false;

        movingLeft = false;
        movingRight = false;
        falling = true;
        jumping = false;
    }

    public void updateCollisionPoints() {
        topLeft.setLocation(topLeft.getX() + velocityX, topLeft.getY() + velocityY);
        topRight.setLocation(topRight.getX() + velocityX, topRight.getY() + velocityY);
        botRight.setLocation(botRight.getX() + velocityX, botRight.getY() + velocityY);
        botLeft.setLocation(botLeft.getX() + velocityX, botLeft.getY() + velocityY);

        String solidTileIDs = ":16:17:18:20:21:31:32:33:35:36:46:47:48:50:51:76:77:78:91:92:93:106:107:108:";
        for (Tile tile : mapManager.getCollisionArr()) {
            //touchingFloorCheck(tile, solidTileIDs);
            if (solidTileIDs.contains(":" + tile.getTileID() + ":")) {
                p1 = tile.getHitBox().contains(topLeft.getX() + velocityX, topLeft.getY() + velocityY);
                p2 = tile.getHitBox().contains(topRight.getX() + velocityX, topRight.getY() + velocityY);
                p3 = tile.getHitBox().contains(botRight.getX() + velocityX, botRight.getY() + velocityY);
                p4 = tile.getHitBox().contains(botLeft.getX() + velocityX, botLeft.getY() + velocityY);

                if (p1 || p2 || p3 || p4) {
                    System.out.println(tile + " | p1: " + p1 + " p2: " + p2  + " p3: " + p3 + " p4: " + p4 );
                    break;

                }
            }
        }
    }

    public void updateCollisionConstants() {

        if ((p3 || p4) && falling) {
            touchingFloor = true;
            y = botLeft.getY() - hitBox.height - 1;
        }
        else if ((p1 || p2) && jumping) {
            y = topLeft.getY() + hitBox.height + 1;
            velocityY = 0;
            touchingCeiling = true;
        }
        else {
            touchingFloor = false;
            touchingCeiling = false;
        }


        if ((p1 || p4) && movingLeft) {
            x = topLeft.getX() + hitBox.width;
            touchingWall = true;
        }
        else if ((p2 || p3) && movingRight) {
            x = topRight.getX() - hitBox.width;
            touchingWall = true;
        }
        else {
            touchingWall = false;
        }
    }

    // setting touchingFloor to false in the updatePosition method and can't make it work otherwise
    public void touchingFloorCheck(Tile tile, String solidTileIDs) {
        boolean height = tile.getHitBox().y == hitBox.y + hitBox.height + 1;
        boolean bottomLeft = tile.getHitBox().x <= hitBox.x && hitBox.x <= tile.getHitBox().x + tile.getHitBox().width;
        boolean bottomRight = tile.getHitBox().x <= hitBox.x + hitBox.width && hitBox.x + hitBox.width <= tile.getHitBox().x + tile.getHitBox().width;

        if (height && (bottomLeft || bottomRight)){
            touchingFloor = solidTileIDs.contains(":" + tile.getTileID() + ":");
        }

        //System.out.println("playery: " + hitBox.y + " | player y + height + 1: " + (hitBox.y + hitBox.height + 1) + " | tile height: " + tile.getHitBox().y + " | height: " + height + " | botleft: " + bottomLeft + " | botRight: " + bottomRight + " | toouchingfloor: " + touchingFloor + " | solid?: " + (solidTileIDs.contains(":" + tile.getTileID() + ":")) );
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

    public boolean isTouchingWall() {
        return touchingWall;
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