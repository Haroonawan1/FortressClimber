import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel drawPanel;
    private Player player;
    private Input input;
    private Thread thread;

    public MainFrame() {
        super("Fortress Climber");

        player = new Player();
        drawPanel = new DrawPanel(player);
        input = new Input(player);

        this.add(drawPanel);
        this.addKeyListener(input);

        int frameWidth = 500;
        int frameHeight = 500;
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
}