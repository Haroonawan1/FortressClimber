public class Player {
    private int x;
    private int y;

    public Player() {
        x = 0;
        y = 0;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void changeX(int x) {
        this.x += x;
    }

    public void changeY(int y) {
        this.y += y;
    }
}
