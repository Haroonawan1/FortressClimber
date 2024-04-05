import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel drawPanel;
    private Player player;
    private Input input;
    private Thread thread;
    private int fps;
    private int ups;
    private int frameWidth;
    private int frameHeight;

    public MainFrame() {
        super("Fortress Climber");

        player = new Player(this);
        drawPanel = new DrawPanel(player);
        input = new Input(player);

        fps = 120;
        ups = 200;
        frameWidth = 1000;
        frameHeight = 600;

        this.add(drawPanel);
        this.addKeyListener(input);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(600, 250);
        this.setResizable(false);
        this.setVisible(true);

        startThread();
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void update() {
        player.update();
    }

    public void run() {
        double timePerFrame = 1000000000.0 / fps;
        double timePerUpdate = 1000000000.0 / ups;
        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaU >= 1){
                update();
                deltaU--;
            }

            if (deltaF >= 1){
                drawPanel.repaint();
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
            }
        }
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }
}