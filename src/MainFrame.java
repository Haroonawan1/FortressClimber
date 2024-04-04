import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel drawPanel;
    private Player player;
    private Input input;
    private Thread thread;


    private int frameWidth;
    private int frameHeight;

    public MainFrame() {
        super("Fortress Climber");

        player = new Player(this);
        drawPanel = new DrawPanel(player);
        input = new Input(player);

        frameWidth = 500;
        frameHeight = 500;

        this.add(drawPanel);
        this.addKeyListener(input);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(0, 0);
        this.setResizable(false);
        this.setVisible(true);

        startThread();
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            drawPanel.repaint();
        }
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }
}