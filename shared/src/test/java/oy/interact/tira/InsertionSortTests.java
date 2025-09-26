package oy.interact.tira;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import oy.interact.tira.util.SimpleSort;

@DisplayName("Insertion sort tests")
class InsertionSortTests {
	
	@Test 
	@DisplayName("Testing sorting to natural order")
	void testSimpleInsertionSortNaturalOrder() {
		System.out.println("\ntestSimpleInsertionSortNaturalOrder");
		Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
		System.out.format("Array before sorting: %s%n", Arrays.toString(array));
		SimpleSort.insertionSort(array);
		assertTrue(isInOrder(array, Comparator.naturalOrder()), "Array was not in order");
		System.out.format("Array after sorting: %s%n", Arrays.toString(array));
	}

	@Test 
	@DisplayName("Testing sorting to descending order")
	void testComparableInsertionSortDescendingOrder() {
		System.out.println("\ntestComparableInsertionSortDescendingOrder");
		Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
		System.out.format("Array before sorting: %s%n", Arrays.toString(array));
		SimpleSort.insertionSort(array, Comparator.reverseOrder());
		assertTrue(isInOrder(array, Comparator.reverseOrder()), "Array was not in order");
		System.out.format("Array after sorting: %s%n", Arrays.toString(array));
	}

	@Test 
	@DisplayName("Testing sorting to natural order with comparator")
	void testComparableInsertionSortNaturalOrder() {
		System.out.println("\ntestComparableInsertionSortNaturalOrder");
		Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
		System.out.format("Array before sorting: %s%n", Arrays.toString(array));
		SimpleSort.insertionSort(array, Comparator.naturalOrder());
		assertTrue(isInOrder(array, Comparator.naturalOrder()), "Array was not in order");
		System.out.format("Array after sorting: %s%n", Arrays.toString(array));
	}

	@Test 
	@DisplayName("Testing sorting whole array with indices")
	void testWholeRangeInsertionSortNaturalOrder() {
		System.out.println("\ntestWholeRangeInsertionSortNaturalOrder");
		Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
		System.out.format("Array before sorting: %s%n", Arrays.toString(array));
		SimpleSort.insertionSort(array, 0, array.length, Comparator.reverseOrder());
		assertTrue(isInOrder(array, Comparator.reverseOrder()), "Array was not in order");
		System.out.format("Array after sorting: %s%n", Arrays.toString(array));
	}

	@Test 
	@DisplayName("Testing sorting whole array with indices to natural order")
	void testPartialRangeInsertionSortNaturalOrder() {
		System.out.println("\ntestPartialRangeInsertionSortNaturalOrder");
		Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
		final int from = 2;
		final int to = array.length - 1;
		System.out.format("Sorting array to natural order between %d..<%d%n", from, to);
		System.out.format("Array before sorting: %s%n", Arrays.toString(array));
		SimpleSort.insertionSort(array, from, to, Comparator.naturalOrder());
		assertTrue(isInOrder(array, from, to, Comparator.naturalOrder()), "Array was not in order");
		System.out.format("Array after sorting: %s%n", Arrays.toString(array));
	}

		@Test 
	@DisplayName("Testing sorting whole array with indices to reverse order")
	void testPartialRangeInsertionSortReverseOrder() {
		System.out.println("\ntestPartialRangeInsertionSortReverseOrder");
		Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
		final int from = 2;
		final int to = array.length - 1;
		System.out.format("Sorting array to reverse order between %d..<%d%n", from, to);
		System.out.format("Array before sorting: %s%n", Arrays.toString(array));
		SimpleSort.insertionSort(array, from, to, Comparator.reverseOrder());
		assertTrue(isInOrder(array, from, to, Comparator.reverseOrder()), "Array was not in order");
		System.out.format("Array after sorting: %s%n", Arrays.toString(array));
	}

	/////////////////
	// Helper methods
	/////////////////

	private boolean isInOrder(Integer [] array, Comparator<Integer> comparator) {
		for (int index = 1; index < array.length; index++) {
			if (array[index-1] == null || array[index] == null) {
				return false;
			}
			if (comparator.compare(array[index-1], array[index]) > 0) {
				return false;
			}
		}
		return true;
	}

	private boolean isInOrder(Integer [] array, int fromIndex, int toIndex, Comparator<Integer> comparator) {
		for (int index = fromIndex + 1; index < toIndex; index++) {
			if (array[index-1] == null || array[index] == null) {
				return false;
			}
			if (comparator.compare(array[index-1], array[index]) > 0) {
				return false;
			}
		}
		return true;
	}

}
