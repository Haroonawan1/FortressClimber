package map;

import java.awt.Rectangle;

public class Tile {
    private Rectangle hitBox;
    private int tileNum;

    public Tile(int x, int y, int width, int height, int tileNum) {
        hitBox = new Rectangle(x, y, width, height);
        this.tileNum = tileNum;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getTileNum() {
        return tileNum;
    }
}
