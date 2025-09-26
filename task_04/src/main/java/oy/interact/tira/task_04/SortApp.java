package oy.interact.tira.task_04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

import oy.interact.tira.student.Partition;
import oy.interact.tira.student.Person;
import oy.interact.tira.student.FastSort;
import oy.interact.tira.student.ArrayUtils;
import oy.interact.tira.util.PersonUtils;
import oy.interact.tira.util.SimpleSort;

public class SortApp {

	private static class Measurement {
		int n;
		long algorithm1Duration;
		long algorithm2Duration;
		long algorithm3Duration;

		@Override
		public String toString() {
			return String.format("%d,%d,%d,%d", n, algorithm1Duration, algorithm2Duration, algorithm3Duration);
		}
	}

	private static FileWriter writer = null;
	private static Measurement currentMeasurement = new Measurement();

	private static boolean printDetails = true;

	public static void main(String[] args) {

		try {
			// To see examples of how to use Comparator, uncomment the line below:
			// comparatorExamples();

			// For writing time performance measurements to file.
			writer = new FileWriter(new File("measurements.csv"));
			writer.write("N,InsertionSort,FastSort,Filtering\n");

			final int MIN_VALUE = 100_000;
			final int MAX_VALUE = 100_000;
			final int STEP_INCREASE = 1_000;
			for (int size_of_n = MIN_VALUE; size_of_n <= MAX_VALUE; size_of_n += STEP_INCREASE) {
				Person[] persons = PersonUtils.createPersons(size_of_n);
				currentMeasurement.n = persons.length;
				ArrayUtils.shuffle(persons);
				if (printDetails) System.out.println("\nStarting to count by using SimpleSort.insertionSort...");
				countByInsertionSorting(persons);

				ArrayUtils.shuffle(persons);
				if (printDetails) System.out.println("\nStarting to count by using FastSort.sort...");
				countByFastSorting(persons);

				ArrayUtils.shuffle(persons);
				if (printDetails) System.out.println("\nStarting to count by using Partition.filter...");
				countByFiltering(persons);

				writer.write(currentMeasurement.toString());
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.format("\n*** ERROR: something went wrong!: %s%n", e.getMessage());
		}
	}

	private static void countByInsertionSorting(final Person[] persons) {
		int totalCount = 0;
		long start = System.currentTimeMillis();


		// 1. Sort the array using insertion sort, by last name length, in ascending
		// (natural) order.
		// 2. Then print out the person with shortest last name (first in array),
		// 3. and print also the person with the longest last name (last in array)
		// and also the length of those names.
		// 4. Then go through the array in order, and count the occurrence of each name
		// length, printing out the length of the last name and the occurrence count
		// of the name, as in the readme example.
		//
		// Do not print out all the names in the array, but only when the name
		// changes, then print the previous name and the count.
		// See the readme for an example what should be printed out.


		if (printDetails) System.out.format("Handled %,d persons out of %,d%n", totalCount, persons.length);
		long duration = System.currentTimeMillis() - start;
		currentMeasurement.algorithm1Duration = duration;
		System.out.format("InsertionSort: N: %d - duration of counting all name lengths: %d ms%n", persons.length,
				duration);
	}

	private static void countByFastSorting(final Person[] persons) {
		int totalCount = 0;
		long start = System.currentTimeMillis();

		// 1. Sort the array using fast sort algorithm, by last name length,
		// in ascending (natural) order.
		// 2. Then print out the person with shortest last name (first in array),
		// 3. and print also the person with the longest last name (last in array)
		// and also the length of those names.
		// 4. Then go through the array in order, and count the occurrence of each name
		// length, printing out the length of the last name and the occurrence count
		// of the name, as in the readme example.
		//
		// Do not print out all the names in the array, but only when the name
		// changes, then print the name and the count.
		// See the readme for an example what should be printed out.

		if (printDetails) System.out.format("Handled %,d persons out of %,d%n", totalCount, persons.length);
		long duration = System.currentTimeMillis() - start;
		currentMeasurement.algorithm2Duration = duration;
		System.out.format("FastSort: N: %d - duration of counting all name lengths: %d ms%n", persons.length, duration);
	}

	private static void countByFiltering(final Person[] persons) {
		int totalCount = 0;
		long start = System.currentTimeMillis();

		// 1. Do _not_ sort the array at all, that is not needed here.
		// 2. Use ArrayUtils.findEdgeValue to find the person with
		// the shortest last name. Get the length of that last name.
		// 3. Use ArrayUtils.findEdgeValue to find the person with the
		// longest last name. Get the length of that name.
		// 4. In a loop, _filter_ the array by the last name lengths,
		// between the values shortestNameLength...longestNameLength,
		// one last name length at a time. filter returns an array
		// with only those last names with the specified name length.
		// Print out the length of the last name and count of those names.
		//
		// Do not print out all the names in the array, but only when the name
		// changes, then print the name and the count.
		// See the readme for an example what should be printed out.
		
		if (printDetails) System.out.format("Handled %,d persons out of %,d%n", totalCount, persons.length);
		long duration = System.currentTimeMillis() - start;
		currentMeasurement.algorithm3Duration = duration;
		System.out.format("filtering: N: %d - duration of counting all name lengths: %d ms%n", persons.length, duration);
	}

	// Examples of using Comparator in different ways
	// Use these as samples to follow in how you create and use Comparators.
	private static void comparatorExamples() {

		final Integer[] pi = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 4 };

		// Sort to to natural order without comparators, using Integer.compareTo
		Integer[] array = ArrayUtils.copyOf(pi);
		SimpleSort.insertionSort(array);
		printArray(array, ", ");

		// Sort to to natural order using Comparator.naturalOrder()
		array = ArrayUtils.copyOf(pi);
		SimpleSort.insertionSort(array, Comparator.naturalOrder());
		printArray(array, ", ");

		// Sort to to reverse order using Comparator.reverseOrder()
		array = ArrayUtils.copyOf(pi);
		SimpleSort.insertionSort(array, Comparator.reverseOrder());
		printArray(array, ", ");

		// Sort to to natural order using our own Comparator
		array = ArrayUtils.copyOf(pi);
		class AscendingOrderComparatorForIntegers implements Comparator<Integer> {
			@Override
			public int compare(Integer first, Integer second) {
				return first - second;
			}
		}
		SimpleSort.insertionSort(array, new AscendingOrderComparatorForIntegers());
		printArray(array, ", ");

		// Sort to to reverse order using our own Comparator
		array = ArrayUtils.copyOf(pi);
		class DescendingOrderComparatorForIntegers implements Comparator<Integer> {
			@Override
			public int compare(Integer first, Integer second) {
				return second - first;
			}
		}
		SimpleSort.insertionSort(array, new DescendingOrderComparatorForIntegers());
		printArray(array, ", ");

		// Sort to natural order by creating the comparator in the parameter
		array = ArrayUtils.copyOf(pi);
		SimpleSort.insertionSort(array, (first, second) -> first - second);
		printArray(array, ", ");

		// Sort to age order by using Comparator.comparingInt
		try {
			Person [] personArray = PersonUtils.createPersons(10);
			Comparator<Person> comparator = Comparator.comparingInt(Person::getAge);
			SimpleSort.insertionSort(personArray, comparator);
			printArray(personArray, "\n");
		} catch (IOException e) {
			System.err.format("Failed to create person array: %s%n", e.getMessage());
		}
	}

	private static <T> void printArray(final T [] array, final String separator) {
		int counter = 0;
		System.out.print("[");
		for (T element : array) {
			System.out.format("%s", element);
			if (counter < array.length - 1) {
				System.out.print(separator);
			}
		}
		System.out.println("]");
	}
}
