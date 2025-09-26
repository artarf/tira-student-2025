package oy.interact.tira.student;

import java.util.function.Predicate;

public class Partition {

	private Partition() {
		// empty
	}

	/**
	 * Partition an array using the rule specified with a Predicate.
	 * 
	 * Moves all elements that conform to the given rule, to the
	 * beginning of the array. Implementation MUST be stable, that is
	 * the order of the moved elements remain the same as in the original
	 * array.
	 * 
	 * Array may contain null elements. One usage of this algorithm may be,
	 * in fact, to move all the nulls to the end of the array. For example:
	 * 
	 * Integer [] array = {3, 1, 4, null, 1, 5, 9, null, 2, 6, 5, null, 4};
    * System.out.println(Arrays.toString(array));
    * int index = Partition.byRule(array, number -> number != null );
    * System.out.format("Index of first null element: %d%n", index);
    * System.out.println(Arrays.toString(array));
	 * 
	 * prints out:
	 * 
	 * [3, 1, 4, null, 1, 5, 9, null, 2, 6, 5, null, 4]
    * Index of first null element: 10
    * [3, 1, 4, 1, 5, 9, 2, 6, 5, 4, null, null, null]
	 *
	 * As you can see, the conforming values (non-nulls) are in the same order as before,
	 * so the algorithm is stable.
	 * 
	 * @param array The array to partition, must not be null. 
	 * @param size The count of elements in the array; index up to (not including) the array is partitioned to.
	 * @param rule Rule to use in determining which elements should be moved to prefix of array.
	 * @return The index that contains the first element _not_ conforming to the rule, 
	 * or the value of parameter size, if no conforming elements were found.
	 * @throws IllegalArgumentException if either the array or rule is null.
	 */
	public static <T> int byRule(T[] array, final int size, Predicate<T> rule) throws IllegalArgumentException {
		return -1;
		// STUDENTS TODO implement partitioning by rule
   }

	/**
	 * Filters content from the input array to a new array, using a predicate.
	 * 
	 * The input array must not change in any way during the filtering.
	 * 
	 * @param array The array to filter contents from.
	 * @param count The number of elements in the array, filtering is done between 0..<count.
	 * @param predicate The predicate to use in determining which items to include in the filtered array.
	 * @return A new array with content from parameter arrary, with only elements conforming to the predicate.
	 * @throws IllegalArgumentException if either the array or rule is null.
	 */
	public static <T> T [] filter(final T [] array, int count, Predicate<T> predicate) throws IllegalArgumentException {
		return null;
		// STUDENTS TODO implement filtering by rule, using Partition.byRule
	}

}
