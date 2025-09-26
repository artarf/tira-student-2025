package oy.interact.tira.task_04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.ArrayUtils;
import oy.interact.tira.util.NotYetImplementedException;

@DisplayName("Tests finding the edge values using ArrayUtils.findEdgeValue")
@EnabledIf("checkIfImplemented")
public class FindEdgeValueTests {
	
	private static Integer smallest;
	private static Integer largest;
	private static String shortestCourseName;
	private static String longestCourseName;

	static boolean checkIfImplemented() {
		try {
			Integer [] values = {1};
			ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) < 0);
		} catch (NotYetImplementedException e) {
			return false;
		}
		return true;
	}

	@Test
	void testPreconditionsChecks() {
		Object [] values = null;
		assertThrows(IllegalArgumentException.class, () -> ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.equals(value2)));
		assertThrows(IllegalArgumentException.class, () -> ArrayUtils.findEdgeValue(values, null));
	}

	@Test
	void testSingleValueArray() {
		Integer [] values = {1};
		
		assertDoesNotThrow(() -> smallest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) < 0));
		assertEquals(1, smallest, "Algorithm didn't find the correct smallest value");
		assertDoesNotThrow(() -> largest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) > 0));
		assertEquals(1, largest, "Algorithm didn't find the correct largest value");
		System.out.format("Smallest integer is %d%n", smallest);
		System.out.format("Largest integer is %d%n", largest);
	}

	@Test
	void testTwoValueArray() {
		Integer [] values = {2, 1};
		
		assertDoesNotThrow(() -> smallest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) < 0));
		assertEquals(1, smallest, "Algorithm didn't find the correct smallest value");
		assertDoesNotThrow(() -> largest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) > 0));
		assertEquals(2, largest, "Algorithm didn't find the correct largest value");
		System.out.format("Smallest integer is %d%n", smallest);
		System.out.format("Largest integer is %d%n", largest);
	}

	@Test
	void testEqualValuesArray() {
		Integer [] values = {2, 2};
		
		assertDoesNotThrow(() -> smallest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) < 0));
		assertEquals(2, smallest, "Algorithm didn't find the correct smallest value");
		assertDoesNotThrow(() -> largest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) > 0));
		assertEquals(2, largest, "Algorithm didn't find the correct largest value");
		System.out.format("Smallest integer is %d%n", smallest);
		System.out.format("Largest integer is %d%n", largest);
	}

	@Test
	void simpleTestWithIntegers() {
		Integer [] values = {1, 2, -6, 1, -7, 9, 0, 3, 10, 11, 5, 6};
		
		assertDoesNotThrow(() -> smallest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) < 0));
		assertEquals(-7, smallest, "Algorithm didn't find the correct smallest value");
		assertDoesNotThrow(() -> largest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) > 0));
		assertEquals(11, largest, "Algorithm didn't find the correct largest value");
		System.out.format("Smallest integer is %d%n", smallest);
		System.out.format("Largest integer is %d%n", largest);
	}

	@Test
	void simpleTestWithStrings() {
		String [] values = {"tietoturva", "tira", "ohjelmointi 1", "ohjelmointi 2", "laitteet ja tietoverkot", "tietomallintaminen"};

		assertDoesNotThrow(() -> shortestCourseName = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.length() < value2.length()));
		assertEquals("tira", shortestCourseName, "Algorithm didn't find the correct smallest value");
		assertDoesNotThrow(() -> longestCourseName = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.length() > value2.length()));
		assertEquals("laitteet ja tietoverkot", longestCourseName, "Algorithm didn't find the correct largest value");
		System.out.format("Shortest course name is %s%n", shortestCourseName);
		System.out.format("Longest course name is %s%n", longestCourseName);
	}

}
