package oy.interact.tira.task_09;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.factories.HashTableFactory;
import oy.interact.tira.student.FastSort;
import oy.interact.tira.util.Pair;
import oy.interact.tira.util.Dictionary;


@DisplayName("Testing that the implementation is really generic.")
@EnabledIf("checkIfImplemented")
class HashTableGenericTests {

    static boolean checkIfImplemented() {
		return HashTableFactory.createHashTable() != null;
	}

    // Implementations to test:
    static Dictionary<String, Integer> hashTable = null;
    static final int TEST_COUNT = 10_000;
    static Pair<String, Integer> [] testArray;
    static int testIndex = 0;
    static Integer testValue;
    static boolean testResult;
    static Comparator<String> comparator = new Comparator<>() {
        @Override
        public int compare(String first, String second) {
            return first.compareTo(second);
        }
    };

    @BeforeAll
    static void allocateDataStructure() {
        hashTable = HashTableFactory.createHashTable();
    }

    @Test
    @Order(1)
    // @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @DisplayName("Tests HashTable by adding, getting and toArray")
    void testHashTable() {
        try {
            hashTable.clear();
            List<String> randomList = new ArrayList<>();
            for (int index = 0; index < TEST_COUNT; index++) {
                randomList.add(String.valueOf(index));
            }
            Collections.shuffle(randomList);
            for (int index = 0; index < TEST_COUNT; index++) {
                final int findValue = index;
                assertDoesNotThrow(() -> hashTable.add(randomList.get(findValue), Integer.parseInt(randomList.get(findValue))), "HashTable add must not throw");
            }
            System.out.println(">> Testing HashTable with " + hashTable.size() + " entries");
            assertEquals(TEST_COUNT, hashTable.size(), () -> "Inserted count must match");
            assertTrue(hashTable.size() < hashTable.capacity(), "Hashtable capacity must be greater than it's size");
            for (int index = 0; index < TEST_COUNT; index++) {
                final int findValue = index;
                assertDoesNotThrow( () -> testValue = hashTable.get(String.valueOf(findValue)), "HashTable find must not throw");
                assertEquals(index, testValue, () -> "Inserted and retrieved values must match");
            }
            assertNull(hashTable.get(String.valueOf(TEST_COUNT + 42)), () -> "Must return null when element not in tree");
            assertDoesNotThrow( () -> testArray = hashTable.toArray(), "HashTable toArray must not throw");
            assertNotNull(testArray, () -> "Returned array from toSortedArray must not be null");
            assertEquals(randomList.size(), testArray.length, () -> "Test array and toSortedArray lengths do not match");
            String [] originalArray = new String[randomList.size()];
            int index = 0;
            for (String item : randomList) {
                originalArray[index++] = item;
            }
            FastSort.sort(originalArray, 0, originalArray.length - 1);
            String [] keys = new String[testArray.length];
            index = 0;
            for (Pair<String,Integer> pair : testArray) {
                keys[index++] = pair.getKey();
            }
            FastSort.sort(keys, 0, keys.length - 1);
            for (index = 0; index < TEST_COUNT; index++) {
                assertNotNull(testArray[index], "Array from toSortedArray must not contain null elements");
                assertEquals(originalArray[index], keys[index], () -> "Array elements do not match");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Something went wrong in the test." + e.getMessage());
        } finally {
            hashTable.clear();
        }
    }

    @Test
    @Order(2)
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @DisplayName("Test that adds identical keys correctly")
    void testAddingIdenticalValues() {
        hashTable.clear();
        String duplicateUUID = UUID.randomUUID().toString();
        String notUsedUUID = UUID.randomUUID().toString();

        assertDoesNotThrow(() -> testValue = hashTable.add(duplicateUUID, 21), "Adding to HashTable must not throw");
        assertNull(testValue, "Old value must not have been in the dictionary in this test");

        assertDoesNotThrow(() -> testValue = hashTable.add(duplicateUUID, 42), "Adding to HashTable must not throw");
        assertNotNull(testValue, "Old value for this key must not be null. it was erased by different value");
        assertEquals(21, testValue, "After adding two identical keys, returned old value must be correct");

        assertEquals(1, hashTable.size(), "After adding two identical keys, HashTable size must be one (1)");

        assertDoesNotThrow(() -> testValue = hashTable.get(notUsedUUID));
        assertNull(testValue, "Value must not be in the dictionary in this test");

        assertDoesNotThrow(() -> testValue = hashTable.get(duplicateUUID));
        assertNotNull(testValue, "Value for this key must not be null. it was erased by duplicate value, different value");
        assertEquals(42, testValue, "The last value put to hashtable with the same key should be in table");

        hashTable.clear();
    }

    @Test
    @Order(3)
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testCompress() {
        hashTable = HashTableFactory.createHashTableWithCapacity(10_000);
        hashTable.add("one", 1);
        hashTable.add("two", 2);
        hashTable.add("three", 3);
        int oldCapacity = hashTable.capacity();
        int oldSize = hashTable.size();
        assertDoesNotThrow(() -> hashTable.compress(), "Hashtable compress() must not throw with this data set size");
        int newCapacity = hashTable.capacity();
        int newSize = hashTable.size();
        System.out.format("Original capacity %d and size %d, compressed capacity %d and size %d%n", oldCapacity, oldSize,
                newCapacity, newSize);
        assertTrue(newCapacity < oldCapacity, "After compress(), hashtable capacity must be smaller");
        assertEquals(oldSize, newSize, "Compress must not change the size() of the hashtable");

    }
}
