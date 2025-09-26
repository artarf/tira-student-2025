package oy.interact.tira.task_08;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.factories.BSTFactory;
import oy.interact.tira.student.BinarySearchTree;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.Pair;
import oy.interact.tira.util.Tree;


@DisplayName("Testing that the implementations are really generic.")
@EnabledIf("checkIfImplemented")
class BSTGenericTests {

    // Implementations to test:
    static Tree<String, Integer> bst = null;
    static final int MIN_TEST_COUNT = 100;
    static final int TEST_COUNT = 999;
    static Pair<String, Integer> [] testArray;
    static int testIndex = 0;
    static Integer testValue;
    static Person testPerson;
    static boolean testResult;

    static boolean checkIfImplemented() {
		return BSTFactory.createStringIntegerBST() != null;
	}

    @BeforeAll
    static void allocateDataStructure() {
        bst = BSTFactory.createStringIntegerBST();
    }

    @Test
    // @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @DisplayName("Tests BST calling add, size, get and toArray")
    void testBST() {
        try {
            bst.clear();
            List<String> randomList = new ArrayList<>();
            final int CURRENT_TEST_COUNT = ThreadLocalRandom.current().nextInt(TEST_COUNT) + MIN_TEST_COUNT;
            for (int index = 0; index < CURRENT_TEST_COUNT; index++) {
                randomList.add(String.valueOf(index));
            }
            Collections.shuffle(randomList);
            for (int index = 0; index < CURRENT_TEST_COUNT; index++) {
                final int findValue = index;
                assertDoesNotThrow(() -> bst.add(randomList.get(findValue), Integer.parseInt(randomList.get(findValue))), "BST add must not throw");
            }
            System.out.println(">> Testing BST with " + bst.size() + " entries");
            assertEquals(CURRENT_TEST_COUNT, bst.size(), () -> "Inserted count must match");
            for (int index = 0; index < CURRENT_TEST_COUNT; index++) {
                final int findValue = index;
                assertDoesNotThrow( () -> testValue = bst.get(String.valueOf(findValue)), "BST find must not throw");
                assertEquals(index, testValue, () -> "Inserted and retrieved values must match");
            }
            assertNull(bst.get(String.valueOf(CURRENT_TEST_COUNT + 42)), () -> "Must return null when element not in tree");
            assertDoesNotThrow( () -> testArray = bst.toArray(), "BST toSortedArray must not throw");
            assertNotNull(testArray, () -> "Returned array from toSortedArray must not be null");
            assertTrue(isSorted(testArray), () -> "KeyValueBSearchTree.toSortedArray() does not sort correctly");
            assertEquals(randomList.size(), testArray.length, () -> "Test array and toSortedArray lengths do not match");
            Object [] originalArray = randomList.toArray();
            Arrays.sort(originalArray);
            for (int index = 0; index < CURRENT_TEST_COUNT; index++) {
                assertNotNull(testArray[index], "Array from toSortedArray must not contain null elements");
                assertEquals(originalArray[index], testArray[index].getKey(), () -> "Array elements do not match");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @DisplayName("Test that get(int index) works also in cases where the bst forms a linked list")
    void testBSTAsLinkedList() {
        try {
            // BST will be a linked list where 10 is at root, numbers going down in left side edge of the tree.
            // Strings have preceding zeroes to make sort order correct.
            final String [] testArray1 = {"10", "09", "08", "07", "06", "05", "04", "03", "02", "01"};
            // Indices should be     9     8     7     6     5     4     3     2     1     0
            System.out.println("-- Testing bst forming a linked list to left side");
            bst.clear();
            for (String item : testArray1) {
                bst.add(item, Integer.parseInt(item));
            }
            assertEquals(10, bst.size(), "BST does not have expected size");
            // Check that order of elements is correct even in bst as linked list
            // order must be "01", "02",...
            assertDoesNotThrow(() -> testArray = bst.toArray(), "bst.toArray must not throw");

            for (int index = 1; index < testArray.length; index++) {
                assertTrue(testArray[index].getKey().compareTo(testArray[index-1].getKey()) > 0, "bst.toArray elements must be in natural order");
            }

            // Check that tree stats are as expected
            Tree.Stats stats = bst.getStats();
            if (stats instanceof BinarySearchTree.BSTStats) {
                BinarySearchTree<String,Integer>.BSTStats bstStats = (BinarySearchTree<String,Integer>.BSTStats)stats;
                System.out.println(bstStats.getStats());
                assertEquals(0, bstStats.minDepth,"Min branch dept with this tree is zero");
                assertEquals(9, bstStats.maxDepth,"Max branch dept with this tree is nine");
            }

            // Reverse the array and get elements by index from both array and bst -- should get same results from both.
            bst.clear();

            // Test so that bst is linked list to right side.
            System.out.println("-- Testing bst forming a linked list to right side");
            final String [] testArray2 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
            for (String item : testArray2) {
                bst.add(item, Integer.parseInt(item));
            }
            assertEquals(10, bst.size(), "BST does not have expected size");
            // Check that order of elements is correct even in bst as linked list
            // order must be "01", "02",...
            assertDoesNotThrow(() -> testArray = bst.toArray(), "bst.toArray must not throw");
            for (int index = 1; index < testArray.length; index++) {
                assertTrue(testArray[index].getKey().compareTo(testArray[index-1].getKey()) > 0, "bst.toArray elements must be in natural order");
            }
            stats = bst.getStats();
            if (stats instanceof BinarySearchTree.BSTStats) {
                BinarySearchTree<String,Integer>.BSTStats bstStats = (BinarySearchTree<String,Integer>.BSTStats)stats;
                System.out.println(bstStats.getStats());
                assertEquals(0, bstStats.minDepth,"Min branch dept with this tree is zero");
                assertEquals(9, bstStats.maxDepth,"Max branch dept with this tree is nine");
            }
            System.out.println("** Tests succeeded!");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @DisplayName("Test that tries to add identical values (not keys)")
    void testAddingIdenticalValues() {
        Tree<String, Person> bst = BSTFactory.createStringPersonBST();
        Person antti = new Person("Antti", "zu Juustila", "von Hindenburg", 42);
        Person identicallyNamedYoungerAntti = new Person("Antti", "zu Juustila", "von Hindenburg", 24);
        assertDoesNotThrow(() -> testPerson = bst.add(antti.getFullName(), antti), "Adding to BST must not throw");
        assertNull(testPerson, "Adding a key first time so BST.add must return null");
        assertDoesNotThrow(() -> testPerson = bst.add(identicallyNamedYoungerAntti.getFullName(), identicallyNamedYoungerAntti), "Adding to BST must not throw");
        assertNotNull(testPerson, "Adding a key second time time so BST.add must return non-null");
        assertEquals(antti, testPerson, "BST.add must return the old value when new is added with the same key");
        assertEquals(antti.getAge(), testPerson.getAge(), "The old value returned must match with the inserted old value age property");
        assertEquals(1, bst.size(), "After trying to add two identical values (that are equal()), BST size must be one (1)");
        assertDoesNotThrow(() -> testPerson = bst.get(identicallyNamedYoungerAntti.getFullName()));
        assertNotNull(testPerson, "Value must be in the dictionary in this test");
        assertEquals(identicallyNamedYoungerAntti, testPerson, "Added value should be in BST when adding the same value twice with different keys");
        assertEquals(24, testPerson.getAge(), "The last added identical value must be in the BST.");
    }

    private <T extends Comparable<T>> boolean isSorted(Pair<String, Integer> [] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i].getKey().compareTo(array[i + 1].getKey()) >= 0)
                return false;
        }
        return true;
    }
}
