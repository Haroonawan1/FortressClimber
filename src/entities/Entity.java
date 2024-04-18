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


            // this only checks one tile but the player maybe spread over two tiles so figure that out

            boolean check1 = tile.getHitBox().y == hitBox.y + hitBox.height + 1;
            boolean check2 = hitBox.x >= tile.getHitBox().x;
            boolean check3 = hitBox.x <= tile.getHitBox().x + tile.getHitBox().width;

            if (check1 && (check2 && check3)) {
                boolean p5 = tile.getHitBox().contains(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1);
                boolean p6 = tile.getHitBox().contains(hitBox.x, hitBox.y + hitBox.height + 1);
                if (p5 || p6) {
                    touchingFloor = true;
                }
                else {
                    touchingFloor = false;
                }
                System.out.println("tile y: " + tile.getHitBox().y + " | check: " + (hitBox.y + hitBox.height + 1) + " | player x: " + hitBox.x + " | check 1: " + tile.getHitBox().x + " | check 2: " + (tile.getHitBox().x + tile.getHitBox().width) + " | b1: " + check1 + " | b2: " + check2 + " | b3: " + check3 + " | botLeft: " + p5 + " | botright: " + p6 + " | touching: " + touchingFloor);
            }

            if (solidTileIDs.contains(":" + tile.getTileID() + ":")) {
                boolean p1 = tile.getHitBox().contains(xValues[0] + velocityX, yValues[0] + velocityY);
                boolean p2 = tile.getHitBox().contains(xValues[1] + velocityX, yValues[1] + velocityY);
                boolean p3 = tile.getHitBox().contains(xValues[2] + velocityX, yValues[2] + velocityY);
                boolean p4 = tile.getHitBox().contains(xValues[3] + velocityX, yValues[3] + velocityY);





                if (velocityY > 0 && (p3 || p4)) {
                    velocityY = 0;
                    y = tile.getHitBox().getY() - hitBox.height - 1;
                    return false;
                }

                if (velocityY < 0 && (p1 || p2)) {
                    y = tile.getHitBox().getY() + hitBox.height + 1;
                    return false;
                }

                if (velocityX < 0 && (p1 || p4)) {
                    x = tile.getHitBox().getX() + hitBox.width;
                    return false;
                }
                if (velocityX > 0 && (p2 || p3)) {
                    x = tile.getHitBox().getX() - hitBox.width;
                    return false;
                }
            }
        }
        return true;
}

    public boolean isTouchingFloor() {
        return touchingFloor;
    }

    public void setTouchingFloor(boolean touchingFloor) {
        this.touchingFloor = touchingFloor;
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
