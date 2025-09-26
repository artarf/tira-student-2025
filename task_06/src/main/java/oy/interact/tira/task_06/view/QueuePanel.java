package oy.interact.tira.task_06.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import oy.interact.tira.task_06.StoreQueuesApp;
import oy.interact.tira.task_06.model.LittleChocolateShop;
import oy.interact.tira.task_06.model.ShopListener;
import oy.interact.tira.task_06.model.LittleChocolateShop.RefreshReason;
import oy.interact.tira.util.QueueInterface;

/**
 * Draws a queue from left to right, entering from left, exiting from right.
 *  - timer in App fires every second, and then randomly insert/remove to shortest queue.
 *  - change bg color of leftmost to red for one timer click to highlight adding.
 *  - change bg color of rightmost to blue for one timer click to highlight removal.
 * Elements are Integers to keep track of item counts and order is visible.
 */
public class QueuePanel extends JPanel implements ShopListener {

    private LittleChocolateShop shop;
    private LittleChocolateShop.RefreshReason reasonForRefresh = LittleChocolateShop.RefreshReason.REFRESHING;
    private List<String> queuesRefreshed = null;

    // TODO: STUDENT:
    // - to compare manually, keep measureAndCompare as false
    // - to compare automatically using 1...8 queues, set 
    //   measureAndCompare to true. Then you won't see printouts
    //   or GUI changes, but in console can view the measurements as csv data
    //   you can take to a spreadsheet app for graphing.
    private boolean measureAndCompare = false;
    private static final int MIN_QUEUES = 1;
    private static final int MAX_QUEUES = 8;
    private int currentCueues = MIN_QUEUES;

    public QueuePanel() {
        if (measureAndCompare) {
            shop = new LittleChocolateShop(MIN_QUEUES, this);    
            shop.open();
        } else {
            shop = new LittleChocolateShop(this);    
            shop.open();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {     
        super.paintComponent(g);

        Rectangle myArea = getBounds();
        myArea.grow(-2, -2);

        Point current = new Point(myArea.x, myArea.y);
        for (Map.Entry<String, QueueInterface<Integer>> queueEntry : shop.getQueues()) {
            current.x = 2;
            // "%1$" + length + "s"
            final String queueNameString = String.format("%s [%2$3d]:", queueEntry.getKey(), queueEntry.getValue().size());
            g.drawString(
                queueNameString, 
                current.x, 
                current.y + StoreQueuesApp.QUEUE_HEIGHT / 2
            );
            int stringWidth = g.getFontMetrics().stringWidth(queueNameString);
            current.x = stringWidth + 5;
            final QueueInterface<Integer> queue = queueEntry.getValue();
            String queueElements = queue.toString();
            queueElements = queueElements.replace('[', ' ');
            queueElements = queueElements.replace(']', ' ');
            queueElements.trim();
            final String[] elements = queueElements.split(", ");
            int elementCounter = 0;
            for (String element : elements) {
                if (queuesRefreshed != null && queuesRefreshed.contains(queueEntry.getKey())) {
                    if (reasonForRefresh == LittleChocolateShop.RefreshReason.DEQUEUED && elementCounter == 0) {
                        Color old = g.getColor();
                        g.setColor(Color.RED);
                        g.fillRect(current.x, current.y, StoreQueuesApp.QUEUE_HEIGHT, StoreQueuesApp.QUEUE_HEIGHT);
                        g.setColor(old);
                    } else if (reasonForRefresh == LittleChocolateShop.RefreshReason.ENQUEUED && elementCounter == elements.length - 1){
                        Color old = g.getColor();
                        g.setColor(Color.BLUE);
                        g.fillRect(current.x, current.y, StoreQueuesApp.QUEUE_HEIGHT, StoreQueuesApp.QUEUE_HEIGHT);
                        g.setColor(old);
                    } else {
                        g.drawRect(current.x, current.y, StoreQueuesApp.QUEUE_HEIGHT, StoreQueuesApp.QUEUE_HEIGHT);                    
                    }
                } else {
                    g.drawRect(current.x, current.y, StoreQueuesApp.QUEUE_HEIGHT, StoreQueuesApp.QUEUE_HEIGHT);                    
                }
                final String trimmed = element.trim();
                stringWidth = g.getFontMetrics().stringWidth(trimmed);
                g.drawString(
                    trimmed, 
                    current.x + (StoreQueuesApp.QUEUE_HEIGHT / 2) - (stringWidth / 2),
                    current.y + StoreQueuesApp.QUEUE_HEIGHT / 2
                );
                current.x += StoreQueuesApp.QUEUE_HEIGHT;
                elementCounter++;
            }
            current.y += StoreQueuesApp.QUEUE_HEIGHT;
        }
    }

    private Semaphore guard = new Semaphore(1);

    @Override
    public void shopQueueStateChanged(RefreshReason reason, final List<String> queueNames) {
        try {
            guard.acquire();
            reasonForRefresh = reason;
            queuesRefreshed = queueNames;
            repaint();
            guard.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shopOpened() {
        reasonForRefresh = LittleChocolateShop.RefreshReason.REFRESHING;
        queuesRefreshed = null;
        repaint();
    }

    @Override
    public void shopIsClosing() {
        reasonForRefresh = LittleChocolateShop.RefreshReason.REFRESHING;
        queuesRefreshed = null;
        repaint();
        if (measureAndCompare) {
            if (currentCueues == 1) {
                System.out.println("Number of queues,Queue fixed costs,Customers in queues costs,Overtime costs,Totalcosts");
            }
            final String cvs = shop.getCostsCSVString();
            System.out.format(Locale.US, "%s%n", cvs);
            currentCueues++;
            if (currentCueues <= MAX_QUEUES) {
                shop = new LittleChocolateShop(currentCueues, this);
                shop.open();
            }
        }
    }

}
