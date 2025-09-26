package oy.interact.tira.task_08;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.factories.BSTFactory;
import oy.interact.tira.student.Person;
import oy.interact.tira.student.BinarySearchTree;
import oy.interact.tira.util.Tree;
import oy.interact.tira.util.PersonUtils;
import oy.interact.tira.util.WuTangNameGenerator;

@DisplayName("Testing BST with Person as the key")
@EnabledIf("checkIfImplemented")
public class PerformanceWithPersonIdBSTTests {
	
	private static final int INITIAL_SIZE = 10_000;
	private static final int TEST_DATA_SIZE = 1000_000;
	private static final int INCREASE_SIZE = 10_000;

	private static Person found;

	static boolean checkIfImplemented() {
		return BSTFactory.createStringPersonBST() != null;
	}

	@SuppressWarnings("unchecked")
	@Test
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
   @DisplayName("Tests BST time performance")
	void testPersonIdAsKey() {

		try {
			int currentSize = INITIAL_SIZE;
			System.out.println("--------------------------------");
			System.out.println("Testing BST performance with Person.id as key");
			System.out.println("Take output to spreadsheet app for time efficiency analysis");
			System.out.println("--------------------------------");
			System.out.println("n,Time to add (ms), Time to get (ms),Min depth,Max depth");
			while (currentSize <= TEST_DATA_SIZE) {
				Person [] persons = PersonUtils.createPersons(currentSize);
				// Allocate also wutang names to keep memory complexity comparable
				// to the similar hash table test.
				String [] wuTangNames = new String[currentSize];				
				for (int counter = 0; counter < currentSize; counter++) {
					wuTangNames[counter] = WuTangNameGenerator.generateRandomProgrammerName();
				}
				Tree<String, Person> tree = BSTFactory.createStringPersonBST();
				long start = System.currentTimeMillis();
				for (int index = 0; index < currentSize; index++) {
					final int addIndex = index;
					assertDoesNotThrow(() -> tree.add(persons[addIndex].getId(), persons[addIndex]), "tree.add must not throw");
				}
				long addDuration = System.currentTimeMillis() - start;
				start = System.currentTimeMillis();
				assertEquals(currentSize, tree.size(), "Not all persons were added to the BST");
				for (int index = 0; index < currentSize; index++) {
					final int getIndex = index;
					assertDoesNotThrow(() -> found = tree.get(persons[getIndex].getId()), "tree.get must not throw");
					assertTrue(persons[getIndex].compareTo(found) == 0, "The person gotten from the tree must equal the searched one");
				}
				long getDuration = System.currentTimeMillis() - start;

				Tree.Stats stats = tree.getStats();
				BinarySearchTree<String,Integer>.BSTStats bstStats = null;
            if (stats instanceof BinarySearchTree.BSTStats) {
					 bstStats = (BinarySearchTree<String,Integer>.BSTStats)stats;
					 assertTrue(bstStats.minDepth >= 0, "Tree min depth not calculated by your implementation.");
					 assertTrue(bstStats.maxDepth >= 1, "Tree min depth not calculated by your implementation.");
				}

				System.out.format("%d,%d,%d,%d,%d%n", tree.size(), addDuration, getDuration, bstStats.minDepth, bstStats.maxDepth);
				currentSize += INCREASE_SIZE;
			}

		} catch (IOException e) {
			fail("Failed to create test data because " + e.getMessage());
		}

	}
	

}
