package oy.interact.tira.task_04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.FastSort;
import oy.interact.tira.util.NotYetImplementedException;

@DisplayName("Very simple Algorithms.fastSort tests with Strings")
@EnabledIf("checkIfImplemented")
class SimpleFastSortTests {
	
	static boolean checkIfImplemented() {
		try {
			Integer [] array = {3, 2, 1};
			FastSort.sort(array, 0, 2);
		} catch (NotYetImplementedException e) {
			return false;
		}
		return true;
	}

	@Test
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@DisplayName("Testing Algorithms.fastSort with String arrays to natural order")
	void testSimpleStringArraySortAsc() {
		System.out.println("Sorting String array to natural order...");
		String [] array = {"8", "4", "1", "9", "3", "7", "2", "5", "6", "10"};
		final String [] expected = {"1", "10", "2", "3", "4", "5", "6", "7", "8", "9"};
		FastSort.sort(array, 0, array.length - 1);
		assertEquals(expected.length, array.length, "After sort, array lengths must match");
		Set<String> testSet = new HashSet<>();
		for (final String item : array) {
			assertNotNull(item, "Array must not contain null values");
			assertTrue(testSet.add(item), "Test data has no duplicates but sorted array does, so data is messed up in sorting.");
		}
		System.out.format("Expected array: %s%n", Arrays.toString(expected));
		System.out.format("Sorted array  : %s%n", Arrays.toString(array));
		assertTrue(Arrays.equals(array, expected), "Algorithms.fastSort does not sort correctly");
	}

	@Test
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	@DisplayName("Testing Algorithms.fastSort with String arrays to reverse order")
	void testSimpleStringArraySortDesc() {
		System.out.println("Sorting String array to reverse order...");
		String [] array = {"8", "4", "1", "9", "3", "7", "2", "5", "6", "10"};
		final String [] expected = {"9", "8", "7", "6", "5", "4", "3", "2", "10", "1"};
		FastSort.sort(array, Comparator.reverseOrder());
		assertEquals(expected.length, array.length, "After sort, array lenghts must match");
		Set<String> testSet = new HashSet<>();
		for (final String item : array) {
			assertNotNull(item, "Array must not contain null values");
			assertTrue(testSet.add(item), "Test data has no duplicates but sorted array does, so data is messed up in sorting.");
		}
		System.out.format("Expected array: %s%n", Arrays.toString(expected));
		System.out.format("Sorted array  : %s%n", Arrays.toString(array));
		assertTrue(Arrays.equals(array, expected), "Algorithms.fastSort does not sort correctly");
	}
}
