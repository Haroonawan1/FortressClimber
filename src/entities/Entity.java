package entities;

import main.MainFrame;
import map.MapManager;
import map.Tile;
import java.awt.Rectangle;

public class Entity {
    private Rectangle hitBox;
    private MainFrame mainFrame;
    private MapManager mapManager;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private boolean touchingFloor;
    private boolean touchingCeiling;
    private boolean touchingWall;

    public Entity(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.mainFrame = mainFrame;
        this.mapManager = mapManager;
    }

    public boolean collisionCheck() {
        double[] xValues = new double[]{hitBox.x, hitBox.x + hitBox.width, hitBox.x + hitBox.width, hitBox.x};
        double[] yValues = new double[]{hitBox.y, hitBox.y, hitBox.y + hitBox.height, hitBox.y + hitBox.height};
        Tile[] collisionArr = mapManager.getCollisionArr();
        String solidTileIDs = ":16:17:18:20:21:31:32:33:35:36:46:47:48:50:51:76:77:78:91:92:93:106:107:108:";

        for (Tile tile : collisionArr) {
            touchingFloorCheck(tile, solidTileIDs);

            if (solidTileIDs.contains(":" + tile.getTileID() + ":")) {

                boolean p1 = tile.getHitBox().contains(xValues[0] + velocityX, yValues[0] + (velocityY * 2));
                boolean p2 = tile.getHitBox().contains(xValues[1] + velocityX, yValues[1] + (velocityY * 2));
                boolean p3 = tile.getHitBox().contains(xValues[2] + velocityX, yValues[2] + (velocityY * 2));
                boolean p4 = tile.getHitBox().contains(xValues[3] + velocityX, yValues[3] + (velocityY * 2));

                if (velocityY > 0 && (p3 || p4)) {
                    y = tile.getHitBox().getY() - hitBox.height - 1;
                    return false;
                }
                if (velocityY < 0 && (p1 || p2)) {
                    y = tile.getHitBox().getY() + hitBox.height + 1;
                    velocityY = 0;
                    touchingCeiling = true;
                    return false;
                }
                else {
                    touchingCeiling = false;
                }

                if (velocityX < 0 && (p1 || p4)) {
                    x = tile.getHitBox().getX() + hitBox.width + 1;
                    touchingWall = true;
                    velocityX = 0;
                    velocityY = 0;
                    return false;
                }
                else {
                    touchingWall = false;
                }

                if (velocityX > 0 && (p2 || p3)) {
                    x = tile.getHitBox().getX() - hitBox.width - 1;
                    touchingWall = true;
                    velocityX = 0;
                    velocityY = 0;
                    return false;
                }
                else {
                    touchingWall = false;
                }
            }
        }
        return true;
    }

    // summed up issue: the player is still touching the floor for an additional tile (48 pixels) when the player jumps also double jump
    // touching floor works as expected 85 percent sure

    public void touchingFloorCheck(Tile tile, String solidTileIDs) {
        boolean height = tile.getHitBox().y == hitBox.y + hitBox.height + 1;
        boolean bottomLeft = tile.getHitBox().x <= hitBox.x && hitBox.x <= tile.getHitBox().x + tile.getHitBox().width;
        boolean bottomRight = tile.getHitBox().x <= hitBox.x + hitBox.width && hitBox.x + hitBox.width <= tile.getHitBox().x + tile.getHitBox().width;

        if (height && (bottomLeft || bottomRight) ){
            touchingFloor = solidTileIDs.contains(":" + tile.getTileID() + ":");

            //System.out.println("playery: " + hitBox.y + " | player y + height + 1: " + (hitBox.y + hitBox.height + 1) + " | tile height: " + tile.getHitBox().y + " | height: " + height + " | botleft: " + bottomLeft + " | botRight: " + bottomRight + " | toouchingfloor: " + touchingFloor + " | solid?: " + (solidTileIDs.contains(":" + tile.getTileID() + ":")) );
        }
        //System.out.println("height: " + height + " | tile hitbox y: " + tile.getHitBox().y + " | player y: " + y + " | solid?: " + (solidTileIDs.contains(":" + tile.getTileID() + ":")));
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
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
}
