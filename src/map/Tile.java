package map;

import java.awt.Rectangle;

public class Tile {
    private Rectangle hitBox;
    private int tileID;
    private boolean outLined;

    public Tile(int x, int y, int width, int height, int tileID) {
        hitBox = new Rectangle(x, y, width, height);
        this.tileID = tileID;
    }

    public boolean isOutLined() {
        return outLined;
    }

    public void setOutLined(boolean outLined) {
        this.outLined = outLined;
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
}
