package map;

import java.awt.Rectangle;

public class Tile {
    private Rectangle hitBox;
    private int tileID;

    public Tile(int x, int y, int width, int height, int tileID) {
        hitBox = new Rectangle(x, y, width, height);
        this.tileID = tileID;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getTileID() {
        return tileID;
    }
}
