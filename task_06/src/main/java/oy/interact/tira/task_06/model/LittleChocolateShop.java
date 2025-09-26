package oy.interact.tira.task_06.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import oy.interact.tira.factories.ArrayQueueFactory;
import oy.interact.tira.util.QueueInterface;

public class LittleChocolateShop {
 
    public enum RefreshReason {
        ENQUEUED,
        DEQUEUED,
        REFRESHING
    }

    // STUDENT: TODO: Modifiable parameter is preparedQueues.
    // Find out what is the optimal total cost of serving the
    // customers; how many queues should be enabled with other
    // parameters kept constant. Must be at least one (1).
    private int preparedQueues = 1;

    // DO NOT change these parameters in your analysis
    private static final long INITIAL_DELAY = 1000;
    private static final long TIME_UNIT = 20;
    private static final long TIME_UNITS_OPEN = 2_600;
    private static final double CUSTOMER_TIME_IN_QUEUE_UNIT_COST = 0.02;
    private static final double SINGLE_QUEUE_FIXED_COST = 710.0;
    private static final double OVERTIME_COSTS_PER_QUEUE = 10.0;

    private int ticketNumber = 1;
    private int currentCustomerCount = 0;

    private Map<String, QueueInterface<Integer>> queues;

    // Costs
    private double customersInQueuesTotalCosts = 0.0;
    private String csvCostsString = new String("");

    // Timing
    private Timer timer;
    private int timeUnits = 0;
    private boolean doPrintouts = true;

    private ShopListener listener;

    // Used by the app and "manual" testing
    public LittleChocolateShop(ShopListener listener) {
        this.listener = listener;
        queues = new HashMap<>();
        doPrintouts = true;
    }

    // Used by the unit tests in this module
    public LittleChocolateShop(int queues, ShopListener listener) {
        this(listener);
        preparedQueues = queues;
        doPrintouts = false;
    }

    public int getCustomersInQueues() {
        return currentCustomerCount;
    }

    public String getCostsCSVString() {
        return csvCostsString;
    }

    public final Set<Map.Entry<String, QueueInterface<Integer>>> getQueues() {
        return queues.entrySet();
    }

    public void open() {
        customersInQueuesTotalCosts = 0.0;
        ticketNumber = 1;
        currentCustomerCount = 0;
        csvCostsString = "";
        prepareQueues(preparedQueues);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer("CustomerEventTimer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                customersInQueuesTotalCosts += calculateCustomersInQueuesCosts();
                // Check if shop should be closed
                if (timeUnits > TIME_UNITS_OPEN && areQueuesEmpty()) {
                    refreshViews(RefreshReason.REFRESHING, null);
                    close();
                    return;
                }
                timeUnits++;
                // If shop still open, allow new customers in
                if (timeUnits < TIME_UNITS_OPEN && timeUnits % 20 == 0) {
                    newCustomerEntered();
                    return;
                }
                // Serve customers in queues
                if (timeUnits % 55 == 0) {
                    customersServed();
                    return;
                }
                if (timeUnits % 10 == 0) {
                    refreshViews(RefreshReason.REFRESHING, null);
                    return;
                }
            }
        }, INITIAL_DELAY, TIME_UNIT);
        listener.shopOpened();
    }

    public void close() {
        double overtimeCosts = 0.0;
        if (timeUnits > TIME_UNITS_OPEN) {
            overtimeCosts = (timeUnits - TIME_UNITS_OPEN) * OVERTIME_COSTS_PER_QUEUE * preparedQueues;
        }
        if (doPrintouts) {
            System.out.format("Serving %d customers took %d time units in queues%n", ticketNumber, timeUnits);
            System.out.format(
                "Unit Costs: time customers in queues: %.2f, queue fixed cost: %,.2f%n",
                CUSTOMER_TIME_IN_QUEUE_UNIT_COST,
                SINGLE_QUEUE_FIXED_COST
            );
            System.out.format("Overtime cost per queue: %.2f%n", OVERTIME_COSTS_PER_QUEUE);
            System.out.format(
                "Total costs: time in queues: %,.2f%nQueue fixed costs (%d queues used): %,.2f%nOvertime costs: %.2f%nTOTAL sum of costs: %,.2f%n",
                customersInQueuesTotalCosts,
                preparedQueues,
                preparedQueues * SINGLE_QUEUE_FIXED_COST,
                overtimeCosts,
                overtimeCosts + customersInQueuesTotalCosts + preparedQueues * SINGLE_QUEUE_FIXED_COST
            );
        }
        // number of queues, queue fixed costs, customers in queues costs, overtimecosts, totalcosts
        csvCostsString = String.format(Locale.US,
            "%d,%.2f,%.2f,%.2f,%.2f", 
            preparedQueues,
            preparedQueues * SINGLE_QUEUE_FIXED_COST,
            customersInQueuesTotalCosts,
            overtimeCosts,
            overtimeCosts + customersInQueuesTotalCosts + preparedQueues * SINGLE_QUEUE_FIXED_COST
        );
        
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        listener.shopIsClosing();
    }

    private void refreshViews(RefreshReason reason, final List<String> queueNames) {
        listener.shopQueueStateChanged(reason, queueNames);        
    }

    private boolean areQueuesEmpty() {
        for (QueueInterface<Integer> queue : queues.values()) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private int nonEmptyQueueCount() {
        int count = 0;
        for (QueueInterface<Integer> queue : queues.values()) {
            if (!queue.isEmpty()) {
                count++;
            }
        }
        return count;

    }

    private double calculateCustomersInQueuesCosts() {
        double costs = 0.0;
        for (QueueInterface<Integer> queue: queues.values()) {
            costs += CUSTOMER_TIME_IN_QUEUE_UNIT_COST * (double)queue.size();
        }
        return costs;
    }

    /**
     * Prepares a number of named Integer queues to be used in the
     * simulation. The queues are stored in the Map (dictionary)
     * data structure, where key is the name of the queue (String),
     * and value is a queue created using ArrayQueueFactory from
     * the `shared` component.
     * 
     * The Map member variable `queues`is already initalised, 
     * so you can just use the Map's put method to add the name
     * and the integer queue to the map as a key-value pair.
     * 
     * The queue names MUST be unique, like "Queue 1",
     * "Queue 2" and so on.
     * @param count The number of queues to create into the Map.
     */
    private void prepareQueues(int count) {
        queues.clear();
        // STUDENT: TODO: prepare the queues, following the
        // documentation in the method comments.        

        // Then refresh the GUI
        refreshViews(RefreshReason.REFRESHING, null);
    }

    private void newCustomerEntered() {
        int shortestQueueLength = Integer.MAX_VALUE;
        String shortestName = "";
        for (Map.Entry<String,QueueInterface<Integer>> entry : queues.entrySet()) {
            if (entry.getValue().size() < shortestQueueLength) {
                shortestQueueLength = entry.getValue().size();
                shortestName = entry.getKey();
            }
        }
        // STUDENTS TODO: get the queue with the shortest name
        // from the `queues` using get method,
        // and add the customer's ticket number to the queue.
        
        
        // No need to touch the code below.
        List<String> list = new ArrayList<>();
        list.add(shortestName);
        refreshViews(RefreshReason.ENQUEUED, list);
        if (doPrintouts) System.out.format("[%4d/%4d] Customer # %d entered queue %s%n", timeUnits, TIME_UNITS_OPEN, ticketNumber, shortestName);
        ticketNumber++;
        currentCustomerCount++;
    }

    protected void customersServed() {
        if (currentCustomerCount <= 0) {
            return;
        }
        int toServeCount = Math.min(queues.size(), 2 + ThreadLocalRandom.current().nextInt(nonEmptyQueueCount()));
        List<String> updatedQueues = new ArrayList<>();
        do {
            // Randomly select a queue to finish serving the firstmost customer.
            Object [] queueNames = queues.keySet().toArray();
            int queueToServe = ThreadLocalRandom.current().nextInt(queueNames.length);
            final QueueInterface<Integer> queue = queues.get(queueNames[queueToServe]);
            if (queue != null && queue.size() > 0) {
                // STUDENTS TODO From the `queue`, dequeue the customer's
                // ticket number to `servedCustomer` variable.
                Integer servedCustomer = -1;

                // No need to touch this code below.
                final String name = (String)queueNames[queueToServe];
                updatedQueues.add(name);
                if (doPrintouts) System.out.format("[%4d/%4d] Served customer # %d from queue %s%n", timeUnits, TIME_UNITS_OPEN, servedCustomer, name);
                toServeCount--;
                currentCustomerCount--;
            }
        } while (toServeCount > 0 && currentCustomerCount > 0);
        refreshViews(RefreshReason.DEQUEUED, updatedQueues);
    }

}
