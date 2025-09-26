package oy.interact.tira.task_06;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import oy.interact.tira.task_06.view.QueuePanel;

/**
 * Shows QueuePanel(s) from top down. One queue is automatically created on start.
 * Timer inserts elements in random shedule bw 1...5 secs, removal is also random.
 * - plus button adds new queue to bottom
 * - queues start working automatically
 */
public class StoreQueuesApp {

    public static final int QUEUE_HEIGHT = 32;
    public static final int CONTENT_HEIGHT = 600;
    public static final int CONTENT_WIDTH = 1200;

    private JPanel queueContainerPanel;

    public static void main(String [] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StoreQueuesApp().execute();
            }
        });
    }

    public StoreQueuesApp() {
        // Empty
    }

    void execute() {
        JFrame mainFrame = new JFrame("Little Chocolate Shop Queueing Simulator");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container rootPane = mainFrame.getContentPane();
        queueContainerPanel = new QueuePanel();
        queueContainerPanel.setSize(CONTENT_WIDTH, CONTENT_HEIGHT);
        rootPane.add(queueContainerPanel);
        mainFrame.setMinimumSize(queueContainerPanel.getSize());
        mainFrame.pack();
        mainFrame.setVisible(true);
    }


}