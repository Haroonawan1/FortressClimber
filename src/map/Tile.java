package map;

import java.awt.Rectangle;

public class Tile {
    private Rectangle hitBox;
    private int tileID;
    private boolean outLined;
    private String color;

    public Tile(int x, int y, int width, int height, int tileID) {
        hitBox = new Rectangle(x, y, width, height);
        this.tileID = tileID;
    }

    public boolean isOutLined() {
        return outLined;
    }

    public void setOutLined(boolean outLined, String color) {
        this.outLined = outLined;
        this.color = color;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getTileID() {
        return tileID;
    }

    public String toString() {
        return "x: " + hitBox.getX() + " | y: " + hitBox.getY() + " | ID: " + tileID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }
}
