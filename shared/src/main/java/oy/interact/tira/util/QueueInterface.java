package oy.interact.tira.util;

/**
 * A generic interface to queue class. Queues work following 
 * the first-in-first-out principle.
 * Students: Implement this interface in a separate <code>QueueImplementation.java</code> file. 
 */
public interface QueueInterface<E> {

   /**
    * For querying the current capacity of the queue.
    * For non-array based queues, returns Integer.MAX_VALUE;
    * 
    * @return The number of elements the queue can currently hold.
    */
   public int capacity();
   
   /**
    * Add an element to the queue.
    * Time complexity is O(1), unless an array based implementation is used,
    * then if reallocation is needed, O(n).
    * 
    * @param element The element to add, must not be null.
    * @throws OutOfMemoryError     If the allocation for the queue element failed.
    * @throws NullPointerException If the element is null.
    */
   public void enqueue(E element) throws OutOfMemoryError, NullPointerException;

   /**
    * Removes an element from the queue.
    * Time complexity is O(1).
    *
    * @return The element from the head of the queue.
    * @throws IllegalStateException If the queue is empty.
    */
   public E dequeue() throws IllegalStateException;

   /**
    * Returns the element at the head of the queue, not removing it from the queue.
    * Time complexity is O(1).
    * 
    * @return The element in the head of the queue.
    * @throws IllegalStateException If the queue is empty.
    */
   public E element() throws IllegalStateException;

   /**
    * Returns the count of elements currently in the queue.
    * Time complexity is O(1).
    * 
    * @return Count of elements in the queue.
    */
   public int size();

   /**
    * Can be used to check if the queue is empty.
    * Time complexity is O(1).
    * 
    * @return True if the queue is empty, false otherwise.
    */
   public boolean isEmpty();

   /**
    * Resets the queue to empty state, removing the objects.
    * If an array based implementation is used, the size of the array
    * must be shrinked to small default size.
    *
    * Time complexity is O(1).
    */
   public void clear();

}
