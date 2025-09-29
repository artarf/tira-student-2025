package oy.tol.tira.task_01;

public class ArrayReversalClient {

	public static void main(String [] args) {
		System.out.println("Testing ArrayReversals.reversed()");
		testReversed();
	}

	// ************************************
	// Using the ArrayReversals.reversed()
	// ************************************

	static void testReversed() {
		testNullArrayReversed();
		testEmptyArrayReversed();
		testReversedEvenNumberOfElements();
		testReversedOddNumberOfElements();
	}

	static void testNullArrayReversed() {
		System.out.println("1: Testing null array reversed()");
		try {
			ArrayReversals.reversed(null);
			System.out.println("FAIL: Expected that the method throws since array is null");
		} catch (IllegalArgumentException e) {
			System.out.println("Expected ArrayReversals.reversed to throw and it did, all is OK");
		} catch (Exception e) {
			System.out.println("FAIL: Expected that the method throws IllegalArgumentException since array size is zero,");
			System.out.println("        but threw instead: " + e.toString());
		}
	}


	static void testEmptyArrayReversed() {
		System.out.println("2: Testing empty array reversed()");
		Integer [] array = {};
		try {
			array = ArrayReversals.reversed(array);
			printArray("Reversed:", array);
			System.out.println("FAIL: Expected that the method throws since array size is zero");
		} catch (IllegalArgumentException e) {
			System.out.println("Expected ArrayReversals.reversed to throw and it did, all is OK");
		} catch (Exception e) {
			System.out.println("FAIL: Expected that the method throws IllegalArgumentException since array size is zero,");
			System.out.println("        but threw instead: " + e.toString());
		}
	}

	static void testReversedEvenNumberOfElements() {
		Integer [] array = {1,2,3,4,5,6};
		Integer [] expected = {6,5,4,3,2,1};

		System.out.format("3: Testing array with even (%d) number of elements reversed()%n", array.length);

		printArray("Original:", array);
		array = ArrayReversals.reversed(array);
		printArray("Reversed:", array);
		printArray("Expected:", expected);
		System.out.println("");
	}

	static void testReversedOddNumberOfElements() {
		Integer [] array = {1,2,3,4,5};
		Integer [] expected = {5,4,3,2,1};

		System.out.format("4: Testing array with odd (%d) number of elements reversed()%n", array.length);

		printArray("Original:", array);
		array = ArrayReversals.reversed(array);
		printArray("Reversed:", array);
		printArray("Expected:", expected);
		System.out.println("");
	}

	// Utilities

	private static void printArray(final String label, Integer [] array) {
		System.out.println(label);
		System.out.print("[");
		for (int index = 0; index < array.length; index++) {
			System.out.print(array[index]);
			if (index < array.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.print("]");
		System.out.println("");
	}
}
