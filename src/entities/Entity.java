package entities;

import main.MainFrame;
import map.MapManager;
import map.Tile;

import java.awt.*;

public class Entity {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private Rectangle hitBox;
    private MainFrame mainFrame;
    private MapManager mapManager;
    private boolean colliding;

    public Entity(double x, double y, double velocityX, double velocityY, MainFrame mainFrame, MapManager mapManager) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.mainFrame = mainFrame;
        this.mapManager = mapManager;
    }

    public boolean collisionCheck(double deltaX, double deltaY) {
        double[] xValues = new double[]{hitBox.x, hitBox.x + hitBox.width, hitBox.x + hitBox.width, hitBox.x};
        double[] yValues = new double[]{hitBox.y, hitBox.y, hitBox.y + hitBox.height, hitBox.y + hitBox.height};

        boolean p1 = false;
        boolean p2 = false;
        boolean p3 = false;
        boolean p4 = false;

        for (Tile tile : mapManager.getCollisionArr()) {
            if (tile.getTileNum() != 0) {

                p1 = tile.getHitBox().contains(xValues[0] + deltaX, yValues[0] + deltaY);
                p2 = tile.getHitBox().contains(xValues[1] + deltaX, yValues[1] + deltaY);
                p3 = tile.getHitBox().contains(xValues[2] + deltaX, yValues[2] + deltaY);
                p4 = tile.getHitBox().contains(xValues[3] + deltaX, yValues[3] + deltaY);


                if (deltaY > 0 && (p3 || p4)) {
                    //velocityY = 0;
                    y = tile.getHitBox().getY() - hitBox.height;
                    return false;
                }

                if (deltaY < 0 && (p1 || p2)) {
                    velocityY = 0;
                    y = tile.getHitBox().getY() + hitBox.height;
                    return false;
                }

                if (deltaX < 0 && (p1 || p4)) {
                    velocityX = 0;
                    x = tile.getHitBox().getX() + hitBox.width;
                    return false;
                }
                if (deltaX > 0 && (p2 || p3)) {
                    velocityX = 0;
                    x = tile.getHitBox().getX() - hitBox.width;
                    return false;
                }
            }
        }
        colliding = false;
        return true;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
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

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
