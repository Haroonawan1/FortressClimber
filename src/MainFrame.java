import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel p;
    private Thread thread;

    public MainFrame() {
        super("Fortress Climber");
        int frameWidth = 500;
        int frameHeight = 500;
        p = new DrawPanel();
        this.add(p);
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
            p.repaint();
        }
    }
}