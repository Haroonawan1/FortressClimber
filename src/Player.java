public class Player {
    private MainFrame mainFrame;
    private int hitBoxSize;
    private double velocityX;
    private double velocityY;
    private double x;
    private double y;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean jumping;
    private boolean falling;


    public Player(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        falling = true;
        hitBoxSize = 75;
        velocityX = 2;
        velocityY = 0.25;
        x = 200;
        y = 300;
    }

    public void update() {
        updatePosition();
    }

    public void updatePosition() {
        if (movingLeft) {
            moveLeft();
        }
        if (movingRight) {
            moveRight();
        }

        if (jumping) {
            velocityY = -5;
            jump();
        }
        if (falling) {
            freeFall();
            velocityY += 0.15;
        }
    }

    public void moveRight() {
        if ((x + hitBoxSize + 16) + velocityX >= mainFrame.getFrameWidth()) {
            velocityX = mainFrame.getFrameWidth() - (x + hitBoxSize + 16);
        }
        x += velocityX;
    }

    public void moveLeft() {
        if (x - velocityX < 0) {
            velocityX = x;
        }
        x -= velocityX;
    }

    public void jump() {
        y += velocityY;
    }

    public void freeFall() {
        if ((y + hitBoxSize + 40) + velocityY <= mainFrame.getFrameHeight()) {
            y += velocityY;
            velocityX = 3;
        }
        else {
            falling = false;
            velocityY = 0.25;
            velocityX = 2;
        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public int getHitBoxSize() {
        return hitBoxSize;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}