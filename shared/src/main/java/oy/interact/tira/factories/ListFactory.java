package oy.interact.tira.factories;

import java.awt.Point;
import oy.interact.tira.util.Car;
import oy.interact.tira.util.LinkedListInterface;

/**
 * This class creates different types of lists implementing the {@code LinkedListInterface} interface.
 * 
 * @author Antti Juustila
 */
public class ListFactory {

   private ListFactory() {
      // Empty
   }
   
   /**
    * Creates an instance of ListImplementation for String type.
    * @return The list object.
    */
   public static LinkedListInterface<String> createStringLinkedList() {
      // - Instantiates your list implementation, 
      // - and return the linked list object to the caller.
      return null;
      // return new LinkedListImplementation<>();
   }

   public static LinkedListInterface<Car> createCarLinkedList() {
      return null;
      // return new LinkedListImplementation<>();
   }

   public static LinkedListInterface<Point> createPointLinkedList() {
      return null;
      // return new LinkedListImplementation<>();
   }

}
