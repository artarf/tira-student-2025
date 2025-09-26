package oy.interact.tira.task_04;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.Person;
import oy.interact.tira.student.FastSort;
import oy.interact.tira.util.NotYetImplementedException;
import oy.interact.tira.util.PersonUtils;


@DisplayName("Testing quicksort algorithm with Comparators")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIf("checkIfImplemented")
class PersonsFastSortBasicTests {
	
	private int currentCount = 1000;
	private Person[] personArray;

	static boolean checkIfImplemented() {
		try {
			Integer [] array = {3, 2, 1};
			FastSort.sort(array, 0, 2);
		} catch (NotYetImplementedException e) {
			return false;
		}
		return true;
	}

	// //throw new NotYetImplementedException
	@BeforeAll
	static void printTest() {
		System.out.println("Testing FAST sorting with Persons and Comparator");
		System.out.println("Testing sort correctness with Persons");
	}

	@BeforeEach
	void loadTestData() {
		try {
			personArray = PersonUtils.createPersons(currentCount);
			currentCount += 1000;
		} catch (IOException e) {
			fail("Could not read Persons to memory for the test");
		}
	}

	@Test
	@Order(1)
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testDefaultSortOrder() {
		System.out.println("--\nSorting by DEFAULT sort order, printing first 10 Persons, verify order:");
		FastSort.sort(personArray, 0, personArray.length - 1);
		printPersons(0, personArray.length);
		assertTrue(PersonUtils.isInOrder(personArray, Comparator.naturalOrder()), "Array contained nulls, or sort order was wrong");
	}

	@Test
	@Order(2)
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testPersonNameAscendingComparator() {
		System.out.println("--\nSorting with ASCENDING comparator by name, printing first 10 Persons, verify order:");
		Comparator<Person> comparator = new Comparator<Person>() {
			@Override
			public int compare(Person first, Person second) {
				return first.compareTo(second);
			}
		};
		FastSort.sort(personArray, 0, personArray.length - 1, comparator);
		printPersons(0, personArray.length);
		assertTrue(PersonUtils.isInOrder(personArray, comparator), "Array contained nulls, or sort order was wrong");
	}

	@Test
	@Order(3)
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testPersonNameDescendingComparator() {
		System.out.println("--\nSorting DESCENDING comparator by name, printing first 10 Persons, verify order:");
		Comparator<Person> comparator = new Comparator<Person>() {
			@Override
			public int compare(Person first, Person second) {
				return second.compareTo(first);
			}
		};
		FastSort.sort(personArray, 0, personArray.length - 1, comparator);
		printPersons(0, personArray.length);
		assertTrue(PersonUtils.isInOrder(personArray, comparator), "Array contained nulls, or sort order was wrong");
	}

	@Test
	@Order(4)
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testWithAgeComparisonOldest() {
		System.out.println("--\nPrinting first 10 oldest Persons:");
		Comparator<Person> comparator = new Comparator<Person>() {
			@Override
			public int compare(Person first, Person second) {
				return first.getAge() - second.getAge();				
			}
		};
		FastSort.sort(personArray, 0, personArray.length - 1, comparator);
		printPersons(0, personArray.length);
		assertTrue(PersonUtils.isInOrder(personArray, comparator), "Array contained nulls, or sort order was wrong");
	}

	@Test
	@Order(5)
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void testWithAgeComparisonYoungest() {
		System.out.println("--\nPrinting first 10 oldest Persons:");
		Comparator<Person> comparator = new Comparator<Person>() {
			@Override
			public int compare(Person first, Person second) {
				return second.getAge() - first.getAge();				
			}
		};
		FastSort.sort(personArray, 0, personArray.length - 1, comparator);
		printPersons(0, personArray.length);
		assertTrue(PersonUtils.isInOrder(personArray, comparator), "Array contained nulls, or sort order was wrong");
	}

	private void printPersons(int from, int to) {
		System.out.format("%3s %3s  %s%n", "ind", "Age", "Person name");
		for (int index = from; index < to; index++) {
			System.out.format("%3d. %3d  %s %n", index, personArray[index].getAge(), personArray[index]);
			if (index == 10) {
				break;
			}
		}
		System.out.println("...");
	}

}
