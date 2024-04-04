public class Player {
    private MainFrame mainFrame;

    private int playerSpeed;
    private int hitBoxLength;
    private int x;
    private int y;

    public Player(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        playerSpeed = 10;
        hitBoxLength = 100;
        x = 0;
        y = 0;
    }

    public void updatePosition(String direction, int change) {
        switch (direction) {
            case "n" -> {
                if (y - change < 0) {
                    change = y;
                }
                y -= change;
            }
            case "s" -> {
                if ((y + hitBoxLength + 40) + change >= mainFrame.getFrameHeight() ) {
                    change = mainFrame.getFrameHeight() - (y + hitBoxLength + 40);
                }
                y += change;
            }
            case "e" -> {
                if ((x + hitBoxLength + 18) + change >= mainFrame.getFrameWidth()) {
                    change = mainFrame.getFrameWidth() - (x + hitBoxLength + 18);
                }
                x += change;
            }
            case "w" -> {
                if (x - change < 0) {
                    change = x;
                }
                x -= change;
            }
        }
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public int getHitBoxLength() {
        return hitBoxLength;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
