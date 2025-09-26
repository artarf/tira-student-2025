package oy.interact.tira.util;

import java.util.Comparator;

/**
 * For searching a number from an array of integers.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class LinearSearchArray {

   private LinearSearchArray() {
      // Empty
   }

   /**
    * Finds a number from the specified array using linear search, -1 if one could not be found.
    * @param aValue The value to find.
    * @param fromArray The array to search.
    * @param fromIndex The index to start searching from.
    * @param toIndex The index to search to in the array.
    * @return The index of the value in the array, -1 if not found.
    */
   public static <T> int search(T aValue, T [] fromArray, int fromIndex, int toIndex) {
      // Check preconditions:
      if (
         fromArray == null ||
         fromIndex > toIndex ||
         fromIndex < 0 || 
         fromIndex >= fromArray.length || 
         toIndex < 0 || 
         toIndex > fromArray.length) 
      {
         throw new IllegalArgumentException("Invalid arguments for search.");
      }
      for (int index = fromIndex; index < toIndex; index++) {
         if (fromArray[index].equals(aValue)) {
            return index;
         }
      }
      return -1;
   }

      /**
    * Finds a number from the specified array using linear search, -1 if one could not be found.
    * @param aValue The value to find.
    * @param fromArray The array to search.
    * @param fromIndex The index to start searching from.
    * @param toIndex The index to search to in the array.
    * @return The index of the value in the array, -1 if not found.
    */
   public static <T> int search(T aValue, T [] fromArray, int fromIndex, int toIndex, Comparator<T> comparator) {
            // Check preconditions:
      if (
         fromArray == null ||
         comparator == null ||
         fromIndex > toIndex ||
         fromIndex < 0 || 
         fromIndex >= fromArray.length || 
         toIndex < 0 || 
         toIndex > fromArray.length) 
      {
         throw new IllegalArgumentException("Invalid arguments for search.");
      }

      for (int index = fromIndex; index < toIndex; index++) {
         if (comparator.compare(fromArray[index], aValue) == 0) {
            return index;
         }
      }
      return -1;
   }

}
