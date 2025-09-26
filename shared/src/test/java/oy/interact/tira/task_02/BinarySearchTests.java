package oy.interact.tira.task_02;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.ArrayUtils;
import oy.interact.tira.student.BinSearch;
import oy.interact.tira.util.LinearSearchArray;
import oy.interact.tira.util.SimpleSort;

@DisplayName("Tests for validating binary search correctness")
@EnabledIf("checkIfImplemented")
class BinarySearchTests {

	static boolean checkIfImplemented() {
		Integer [] array = {1};
		return BinSearch.searchRecursively(1, array, 0, 0) != Integer.MAX_VALUE;
	}

	static int index = 0;

	@Test
	@DisplayName("Test that binsearch preconditions are checked")
	void testPreconditions() {
		Integer [] numbers = new Integer[2];
		numbers[0] = Integer.valueOf(42);
		numbers[1] = Integer.valueOf(24);

		// array is null, must throw
		assertThrows(IllegalArgumentException.class, () -> BinSearch.searchRecursively(numbers[0], null, 0, 1));
		// searched element is null, must throw
		assertThrows(IllegalArgumentException.class, () -> BinSearch.searchRecursively(null, numbers, 0, 1, Comparator.naturalOrder()));
		// comparator is null, must throw
		assertThrows(IllegalArgumentException.class, () -> BinSearch.searchRecursively(1, numbers, 0, 1, null));
		// invalid first index, must throw
		assertThrows(IllegalArgumentException.class, () -> BinSearch.searchRecursively(42, numbers, -1, 0, Comparator.naturalOrder()));
		// invalid second index, must throw
		assertThrows(IllegalArgumentException.class, () -> BinSearch.searchRecursively(42, numbers, 0, 5, Comparator.naturalOrder()));

		// These must pass
		assertDoesNotThrow(() -> index = BinSearch.searchRecursively(42, numbers, 0, 0), "Must find element from array size 1 with correct params");
		assertEquals(0, index, "Array has 42 in index 0");
		assertDoesNotThrow(() -> index = BinSearch.searchRecursively(42, numbers, 0, 0, Comparator.reverseOrder()), "Must find element from array size 1 with correct params");
		assertEquals(0, index, "Array has 42 in index 0");
	}


	private static final int DATASET_SIZE = 50_000;
	private static final int NUMBERS_TO_SEARCH = 1_000;

	@Test
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@DisplayName("Very simple binary search tests for correctness")
	void simpleSearchTest() {
		Integer[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int index = BinSearch.searchRecursively(4, array, 0, array.length - 1);
		assertEquals(3, index, () -> "Number four must be in the index 3 in this case.");
		index = BinSearch.searchRecursively(1, array, 0, array.length - 1);
		assertEquals(0, index, () -> "Number one must be in the index 0 in this case.");
		index = BinSearch.searchRecursively(10, array, 0, array.length - 1);
		assertEquals(9, index, () -> "Number ten must be in the index 9 in this case.");
		index = BinSearch.searchRecursively(11, array, 0, array.length - 1);
		assertEquals(-1, index, () -> "Number eleven is not in array so must return -1.");
		index = BinSearch.searchRecursively(-1, array, 0, array.length - 1);
		assertEquals(-1, index, () -> "Number -1 is not in array so must return -1.");
	}

	@Test
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@DisplayName("Search from the middle when array has odd and even number of elements.")
	void searchFromMiddle() {
		Integer[] array = { 1, 2, 3, 4, 5 };
		int index = BinSearch.searchRecursively(3, array, 0, array.length - 1);
		assertEquals(2, index, () -> "Number three must be in the index 2 (middle of the array) in this case.");
		index = BinSearch.searchRecursively(2, array, 0, array.length - 1);
		assertEquals(1, index, () -> "Number two must be in the index 1 in this case.");
		index = BinSearch.searchRecursively(4, array, 0, array.length - 1);
		assertEquals(3, index, () -> "Number four must be in the index 3 in this case.");

		Integer[] array2 = { 1, 2, 3, 4, 5, 6 };
		index = BinSearch.searchRecursively(3, array2, 0, array2.length - 1);
		assertEquals(2, index, () -> "Number three must be in the index 2 (left from middle of the array) in this case.");
		index = BinSearch.searchRecursively(2, array, 0, array.length - 1);
		assertEquals(1, index, () -> "Number two must be in the index 1 in this case.");
		index = BinSearch.searchRecursively(4, array2, 0, array2.length - 1);
		assertEquals(3, index, () -> "Number three must be in the index 2 (left from middle of the array) in this case.");
	}

	@Test
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@DisplayName("Tests Integer search implementations (linear & binary)")
	void findFromIntArrayTests() {
		try {
			System.out.println("-- Starting the test with linear search --");
			Integer[] array = ArrayUtils.generateIntegerArray(DATASET_SIZE);
			ThreadLocalRandom tlr = ThreadLocalRandom.current();

			int counter = NUMBERS_TO_SEARCH;
			long start = 0;
			long linearDuration = 0;
			while (counter-- >= 0) {
				int toFind = tlr.nextInt(0, array.length);
				start = System.nanoTime();
				int linearIndex = LinearSearchArray.search(toFind, array, 0, array.length);
				assertTrue(linearIndex >= 0, () -> "Binary search could not find the element to search");
				linearDuration += System.nanoTime() - start;
				System.out.println("Index of " + toFind + " is: " + linearIndex);
			}
			linearDuration /= NUMBERS_TO_SEARCH;

			System.out.println("Sorting the array...");
			start = System.nanoTime();
			SimpleSort.insertionSort(array);
			System.out.println("Sorting the array took " + (System.nanoTime() - start) + " ns");

			System.out.println("-- Starting the test with binary search --");
			counter = NUMBERS_TO_SEARCH;
			start = 0;
			long binaryDuration = 0;
			while (counter-- >= 0) {
				int toFind = tlr.nextInt(1, array.length);
				start = System.nanoTime();
				int binaryIndex = BinSearch.searchRecursively(toFind, array, 0, array.length - 1);
				assertTrue(binaryIndex >= 0, () -> "Binary search could not find the element to search");
				binaryDuration += System.nanoTime() - start;
				System.out.println("Index of " + toFind + " is: " + binaryIndex);
				int libraryIndex = Arrays.binarySearch(array, toFind, Comparator.naturalOrder());
				assertEquals(libraryIndex, binaryIndex, () -> "Index is different from real index");
			}
			binaryDuration /= NUMBERS_TO_SEARCH;
			System.out.println("----------------------------------------------");
			System.out.format("Average linear search duration: %10d ns%n", linearDuration);
			System.out.format("Average binary search duration: %10d ns%n", binaryDuration);
			assertTrue(binaryDuration <= linearDuration, () -> "Binary search should be much faster in most cases.");
		} catch (Exception e) {
			fail("Something went wrong in the tests: " + e.getMessage());
		}
	}

	@Test
	// @Timeout(value = 120, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@DisplayName("Tests String search implementations (linear & binary)")
	void findFromStringArrayTests() {
		try {
			System.out.println("-- Starting the test with linear search --");
			String[] array = ArrayUtils.generateStringArray(DATASET_SIZE);
			ThreadLocalRandom tlr = ThreadLocalRandom.current();

			int counter = NUMBERS_TO_SEARCH;
			long start = 0;
			long linearDuration = 0;
			while (counter-- >= 0) {
				int toFind = tlr.nextInt(0, array.length);
				start = System.nanoTime();
				int linearIndex = LinearSearchArray.search(array[toFind], array, 0, array.length);
				assertTrue(linearIndex >= 0, () -> "Binary search could not find the element to search");
				linearDuration += System.nanoTime() - start;
				System.out.println("Index of " + array[toFind] + " is: " + linearIndex);
			}
			linearDuration /= NUMBERS_TO_SEARCH;

			System.out.println("Sorting the array...");
			start = System.nanoTime();
			// You must have implemented this as instructed in Exercise 01-arrays!
			SimpleSort.insertionSort(array);
			System.out.println("Sorting the array took " + (System.nanoTime() - start) + " ns");

			System.out.println("-- Starting the test with binary search --");
			counter = NUMBERS_TO_SEARCH;
			start = 0;
			long binaryDuration = 0;
			while (counter-- >= 0) {
				int toFind = tlr.nextInt(0, array.length);
				start = System.nanoTime();
				int binaryIndex = BinSearch.searchRecursively(array[toFind], array, 0, array.length - 1);
				assertTrue(binaryIndex >= 0, () -> "Binary search could not find the element to search");
				binaryDuration += System.nanoTime() - start;
				System.out.println("Index of " + array[toFind] + " is: " + binaryIndex);
				int libraryIndex = Arrays.binarySearch(array, array[toFind], Comparator.naturalOrder());
				assertEquals(libraryIndex, binaryIndex, () -> "Index is different from real index");
			}
			binaryDuration /= NUMBERS_TO_SEARCH;
			System.out.println("----------------------------------------------");
			System.out.format("Average linear search duration: %10d ns%n", linearDuration);
			System.out.format("Average binary search duration: %10d ns%n", binaryDuration);
			assertTrue(binaryDuration <= linearDuration, () -> "Binary search should be much faster in most cases.");

		} catch (Exception e) {
			fail("Something went wrong in the tests: " + e.getMessage());
		}
	}

}
