package oy.interact.tira.task_08_09;

/**
 * TODO: This class is used to create an instance of your implementation of the Book interface.
 * <p>
 * Implement the <code>createBook()</code> method to return your instance of the Book interface.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public final class BookFactory {
    private BookFactory() {
    }

    /*
     * STUDENT TODO: You must implement these method so that it returns an instance of 
     * your concrete class implementing the BookBase abstract class.
     * @return Your subclass implementations of the BookBase.
     */

    public static BookBase createBSTBook() {
        return null;
        // return new BSTBookImplementation();
    }

    public static BookBase createHashTableBook() {
        return null;
        // return new HashTableBookImplementation();
    }

    public static BookBase createBadBook() {
        return new BadBookImplementation();
    }
}
