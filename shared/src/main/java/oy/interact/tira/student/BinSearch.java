package oy.interact.tira.student;

import java.util.Comparator;

public class BinSearch {
	private BinSearch() {
		// empty
	}


   ///////////////////////////////////////////
   // Recursive binary search bw indices
   ///////////////////////////////////////////

   /**
    * Does a binary search on the array, returning the index of the found
    * value or -1 if one cannot be found.
    *
    * The array to search, must not be null, nor contain null elements in
    * between fromIndex...toIndex, inclusive.
    *
    * Array must be sorted in natural order, based on T.compareTo.
    *
    * @param aValue A value to find, must not be null.
    * @param fromArray The array to search, must not be null.
    * @param fromIndex The index to start the search from, inclusive.
    * @param toIndex The index to search to, inclusive.
    * @return The index of the element found, or -1 if one was not found.
    */
   public static <T extends Comparable<T>> int searchRecursively(T aValue, T[] fromArray, int fromIndex,
         int toIndex) {
      // STUDENTS TODO recursive implementation
      if (aValue == null) throw new IllegalArgumentException("aValue is null");
      if (fromArray == null) throw new IllegalArgumentException("fromArray is null");
      if (fromIndex < 0
             || toIndex < -1
             || fromIndex > toIndex
             || (fromArray.length > 0
                 && toIndex >= fromArray.length)) {
         throw new IllegalArgumentException("index out of range");
      }
      return recursiveComparable(aValue, fromArray, fromIndex, toIndex);
   }

   ///////////////////////////////////////////
   // Binary search using a Comparator
   ///////////////////////////////////////////

   /**
    * Does a binary search on the array, returning the index of the found
    * value or -1 if one cannot be found.
    *
    * The array to search, must not be null, nor contain null elements in
    * between fromIndex...toIndex, inclusive.
    *
    * Array must be sorted in the same order than the Comparator specifies.
    *
    * @param aValue A value to find, must not be null.
    * @param fromArray The array to search, must not be null.
    * @param fromIndex The index to start the search from, inclusive.
    * @param toIndex The index to search to, inclusive.
    * @param comparator The comparator to use in search.
    * @return The index of the element found, or -1 if one was not found.
    */
   public static <T> int searchRecursively(T aValue, T[] fromArray, int fromIndex,
         int toIndex, Comparator<T> comparator) {
      // STUDENTS TODO recursive implementation
      if (aValue == null) {
          throw new IllegalArgumentException("aValue is null");
      }
      if (fromArray == null) {
          throw new IllegalArgumentException("fromArray is null");
      }
      if (comparator == null) {
          throw new IllegalArgumentException("comparator is null");
      }
      if (fromIndex < 0 || toIndex < -1 || fromIndex > toIndex || (fromArray.length > 0 && toIndex >= fromArray.length)) {
          throw new IllegalArgumentException("index out of range");
      }
      return recursiveWithComparator(aValue, fromArray, fromIndex, toIndex, comparator);
   }

   private static <T> int recursiveWithComparator(T value, T[] arr, int lo, int hi, Comparator<T> cmp) {
      if (lo > hi) return -1;
      int mid = lo + ((hi - lo) >>> 1);
      T midVal = arr[mid];
      int c = cmp.compare(midVal, value);
      if (c == 0) return mid;
      if (c > 0) return recursiveWithComparator(value, arr, lo, mid - 1, cmp);
      return recursiveWithComparator(value, arr, mid + 1, hi, cmp);
   }

   private static <T extends Comparable<T>> int recursiveComparable(T value, T[] arr, int lo, int hi) {
      if (lo > hi) return -1;
      int mid = lo + ((hi - lo) >>> 1);
      T midVal = arr[mid];
      int cmp = midVal.compareTo(value);
      if (cmp == 0) return mid;
      if (cmp > 0) return recursiveComparable(value, arr, lo, mid - 1);
      return recursiveComparable(value, arr, mid + 1, hi);
   }
}
