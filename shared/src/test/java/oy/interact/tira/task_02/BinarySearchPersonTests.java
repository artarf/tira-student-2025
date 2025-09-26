package oy.interact.tira.task_02;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.BinSearch;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.LinearSearchArray;
import oy.interact.tira.util.PersonUtils;
import oy.interact.tira.util.SimpleSort;

@DisplayName("Testing binary search with Person objects")
@EnabledIf("checkIfImplemented")
class BinarySearchPersonTests {

	static boolean checkIfImplemented() {
		Person test = new Person("null", "null", "null", 10);
		if (test.compareTo(test) == Integer.MIN_VALUE) {
			return false;
		}
		Person [] testArray = {test};
		return BinSearch.searchRecursively(test, testArray, 0, 0, Comparator.naturalOrder()) != Integer.MAX_VALUE;
	}

	static Person searchResult;
	static int foundIndex;

	static Person [] originalPersons;

	@Test
	@DisplayName("Testing if Person algorithms equals and compareTo are correctly implemented")
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testPersonComparisonAlgorithms() {
		Person [] persons = new Person[7];
		persons[0] = new Person("James", "Jameson", "Third", 87);
		persons[1] = new Person("Heikki", "Hedberg", "Hessu", 42);
		persons[2] = new Person("James", "Jameson", "Second", 67);
		persons[3] = new Person("Antti", "Autio", "Anakonda", 37);
		persons[4] = new Person("Gabriel", "Gatorman", "Güllenberg", 17);
		persons[5] = new Person("James", "Jameson", "First", 67);
		persons[6] = new Person("Jill", "Jameson", "Jennifer", 49);

		System.out.println("\n-- Unsorted Person array:");
		for (final Person person : persons) {
			System.out.println(person);
		}

		// Test equals implementation using linear search:
		int index = LinearSearchArray.search(persons[4], persons, 0, persons.length);
		assertEquals(4, index, "Linear search returned wrong index, check Person.equals implementation");
		index = LinearSearchArray.search(persons[5], persons, 0, persons.length);
		assertEquals(5, index, "Linear search returned wrong index, check Person.equals implementation");
		index = LinearSearchArray.search(persons[2], persons, 0, persons.length);
		assertEquals(2, index, "Linear search returned wrong index, check Person.equals implementation");

		// Making sure Person.compareTo has been implemented correctly
		assertDoesNotThrow(() -> SimpleSort.insertionSort(persons), "Sorting person array should not throw");

		System.out.println("\n-- Sorted Person array:");
		for (final Person person : persons) {
			System.out.println(person);
		}

		assertEquals("Autio", persons[0].getLastName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Gatorman", persons[1].getLastName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Hedberg", persons[2].getLastName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Jameson", persons[3].getLastName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("First", persons[3].getMiddleName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Jameson", persons[4].getLastName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Second", persons[4].getMiddleName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Jameson", persons[5].getLastName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Third", persons[5].getMiddleName(), "Sort order is not correct, check Person.compareTo implementation");
		assertEquals("Jameson, Jill Jennifer", persons[6].getFullName(), "Sort order is not correct, check Person.compareTo implementation");
	}

	@Test
	@DisplayName("Simple binary search test using ascending order")
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testBinarySearchAscendingWithPersons() {
		assertDoesNotThrow(() -> originalPersons = PersonUtils.createPersons(1000));
		Person [] container = new Person[originalPersons.length];
		int index = 0;
		for (final Person person : originalPersons) {
			container[index++] = new Person(person);
		}

		Comparator<Person> comparator = new Comparator<Person>() {
			@Override
			public int compare(final Person first, final Person second) {
				return first.compareTo(second);
			}
		};
		assertDoesNotThrow(() -> SimpleSort.insertionSort(container, comparator),
				"Sorting the array with comparator must not throw");

		for (final Person original : originalPersons) {
			assertDoesNotThrow(() -> foundIndex = BinSearch.searchRecursively(original, container, 0, container.length - 1, comparator),
					"binarySearchRecursiveImpl should now throw");
			assertTrue((foundIndex >= 0 && foundIndex < container.length),
					"The returned index must be >= 0 && < container.length");
			assertEquals(original, container[foundIndex], "The found element was not the one searched for, this is an error");
		}

		assertEquals(-1, BinSearch.searchRecursively(new Person("N", "N", "N", 0), container, 0, container.length - 1, comparator),
				"The string to find is not in container");
	}

	@Test
	@DisplayName("Simple binary search test using descending order")
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testBinarySearchDescendingWithPersons() {
				assertDoesNotThrow(() -> originalPersons = PersonUtils.createPersons(1000));
		Person [] container = new Person[originalPersons.length];
		int index = 0;
		for (final Person person : originalPersons) {
			container[index++] = new Person(person);
		}

		Comparator<Person> comparator = new Comparator<Person>() {
			@Override
			public int compare(final Person first, final Person second) {
				return first.compareTo(second);
			}
		}.reversed();
		assertDoesNotThrow(() -> SimpleSort.insertionSort(container, comparator),
				"Sorting the array with comparator must not throw");

		for (final Person original : originalPersons) {
			assertDoesNotThrow(() -> foundIndex = BinSearch.searchRecursively(original, container, 0, container.length - 1, comparator),
					"binarySearchRecursiveImpl should now throw");
			assertTrue((foundIndex >= 0 && foundIndex < container.length),
					"The returned index must be >= 0 && < container.length");
			assertEquals(original, container[foundIndex], "The found element was not the one searched for, this is an error");
		}

		assertEquals(-1, BinSearch.searchRecursively(new Person("N", "N", "N", 0), container, 0, container.length - 1, comparator),
				"The string to find is not in container");
	}

}
