package oy.interact.tira.util;

import java.util.Comparator;

import oy.interact.tira.student.ArrayUtils;

public class SimpleSort {

	private SimpleSort() {
		// empty
	}

	public static <T extends Comparable<T>> void insertionSort(T[] array) {
		if (null == array) {
			throw new IllegalArgumentException("Cannot sort a null array");
		}
	   if (array.length < 2) {
	      return; // Not an error, no need to sort an array with one element.
	   }
	   insertionSort(array, 0, array.length, Comparator.naturalOrder());
	}

	public static <T extends Comparable<T>> void insertionSort(T[] array, int fromIndex, int toIndex) {
		if (
			null == array || 
			fromIndex < 0 || 
			toIndex < 0 || 
			fromIndex >= array.length ||
			toIndex > array.length)
		{
			throw new IllegalArgumentException("Invalid parameters to insertionSort");
		}
	   if (toIndex - fromIndex <= 0) {
	      return; // Not an error, but no need to sort a range of one or less
	   }
	   for (int outer = fromIndex + 1; outer < toIndex; outer++) {
	      T value = array[outer];
	      int inner = outer - 1;
	      while (inner >= fromIndex && array[inner].compareTo(value) > 0) {
	         ArrayUtils.swap(array, inner + 1, inner);
	         inner -= 1;
	      }
	      array[inner + 1] = value;
	   }
	}

	public static <T> void insertionSort(T[] array, Comparator<T> comparator) {
		if (null == array) {
			throw new IllegalArgumentException("Cannot sort a null array");
		}
	   if (array.length < 2) {
	      return; // Not an error, no need to sort an array with one element.
	   }
	   insertionSort(array, 0, array.length, comparator);
	}

	public static <T> void insertionSort(T[] array, int fromIndex, int toIndex, Comparator<T> comparator) {
		if (
			null == array || 
			fromIndex < 0 || 
			toIndex < 0 || 
			fromIndex >= array.length ||
			toIndex > array.length)
		{
			throw new IllegalArgumentException("Invalid parameters to insertionSort");
		}
	   if (array.length < 2) {
	      return; // Not an error, no need to sort an array with one element.
	   }
      for (int outer = fromIndex + 1; outer < toIndex; outer++) {
         T value = array[outer];
         int inner = outer - 1;
         while (inner >= fromIndex && comparator.compare(array[inner], value) > 0) {
            ArrayUtils.swap(array, inner + 1, inner);
            inner -= 1;
         }
         array[inner + 1] = value;
      }
   }

	
}
