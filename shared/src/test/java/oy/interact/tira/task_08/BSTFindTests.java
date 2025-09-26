package oy.interact.tira.task_08;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.factories.BSTFactory;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.Pair;
import oy.interact.tira.util.PersonUtils;
import oy.interact.tira.util.Tree;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Find tests for BST using Person.getId() as the key")
@EnabledIf("checkIfImplemented")
class BSTFindTests {

	private static Person found = null;
	private static Pair<String,Person> [] persons2 = null;
	private static Tree<String, Person> fastBST = null;

	static boolean checkIfImplemented() {
		return BSTFactory.createStringPersonBST() != null;
	}

	@Test
	@Order(1)
	// @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void findWithBST() {
		try {
			System.out.println("Creating BST and adding to it...");
			fastBST = BSTFactory.createStringPersonBST();
			assertNotNull(fastBST,
					() -> "BSTFactory.createStringPersonBST() returns null, not yet implemented?");
			Person [] persons = PersonUtils.createPersons(100);
			for (final Person person : persons) {
				fastBST.add(person.getId(), person);
			}
			assertEquals(persons.length, fastBST.size(), "Test data array length and BST.size do not match");
			
			System.out.println("Testing BST.get(K)");
			for (final Person person : persons) {
				assertDoesNotThrow(() -> found = fastBST.get(person.getId()), "bst.get(K) must not throw");
				assertEquals(person, found, "Found value must be equal to the searched key");
			}
			Comparator<Person> idComparator = new Comparator<>() {
				@Override
				public int compare(Person o1, Person o2) {
					return o1.getId().compareTo(o2.getId());
				}
			};
			Arrays.sort(persons, idComparator);				
			System.out.println("Testing BST.toArray...");
			
			assertDoesNotThrow(() -> persons2 = fastBST.toArray(), "BST.toArray threw an exception while it should not have done so");
			assertNotNull(persons2, "Coders array from BST.toArray must not be null");
			assertEquals(persons.length, persons2.length, "Count of coders from JSON and from BST.toArray should be equal");
			for (int index = 0; index < persons.length; index++) {
				assertEquals(persons[index].getId(), persons2[index].getValue().getId(), "Coders in BST.toArray in wrong order");
			}
			System.out.println("Tests finished OK");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could execute test: " + e.getMessage());
		}
	}

}
