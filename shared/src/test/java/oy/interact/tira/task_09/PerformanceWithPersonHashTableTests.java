package oy.interact.tira.task_09;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.factories.HashTableFactory;
import oy.interact.tira.student.HashTableDictionary;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.Dictionary;
import oy.interact.tira.util.PersonUtils;
import oy.interact.tira.util.WuTangNameGenerator;

@DisplayName("Testing hash table with person as the key")
@EnabledIf("checkIfImplemented")
public class PerformanceWithPersonHashTableTests {
	
	private static final int INITIAL_SIZE = 10_000;
	private static final int TEST_DATA_SIZE = 1000_000;
	private static final int INCREASE_SIZE = 10_000;
	private static String wuTangName;

	static boolean checkIfImplemented() {
		return HashTableFactory.createHashTable() != null;
	}

	@SuppressWarnings("unchecked")
	@Test
	// @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
   @DisplayName("Tests HashTable by adding, getting and toArray")
	void testPersonWuTangNames() {

		try {
			int currentSize = INITIAL_SIZE;
			System.out.println("--------------------------------");
			System.out.println("Testing hash table performance with Person as key");
			System.out.println("Take output to spreadsheet app for time efficiency analysis");
			System.out.println("--------------------------------");
			System.out.println("n,Time to add (ms),Time to get (ms),Fill factor,Initial collision count,Total collision count,Max probing steps");
			while (currentSize <= TEST_DATA_SIZE) {
				Person [] persons = PersonUtils.createPersons(currentSize);
				String [] wuTangNames = new String[currentSize];
				for (int counter = 0; counter < currentSize; counter++) {
					wuTangNames[counter] = WuTangNameGenerator.generateRandomProgrammerName();
				}
				Dictionary<Person,String> hashTable = HashTableFactory.createHashTable();
				long start = System.currentTimeMillis();
				for (int index = 0; index < currentSize; index++) {
					final int addIndex = index;
					assertDoesNotThrow(() -> hashTable.add(persons[addIndex], wuTangNames[addIndex]), "hashtable.add must not throw");
				}
				long addDuration = System.currentTimeMillis() - start;
				start = System.currentTimeMillis();
				assertEquals(currentSize, hashTable.size(), "Not all persons were added to the hashtable");
				for (int index = 0; index < currentSize; index++) {
					final int getIndex = index;
					assertDoesNotThrow(() -> wuTangName = hashTable.get(persons[getIndex]), "hashtable.get must not throw");
					assertEquals(wuTangNames[getIndex], wuTangName, "Person's assigned Wu Tang name was wrong");
				}
				long getDuration = System.currentTimeMillis() - start;
				Dictionary.Stats stats = hashTable.getStats();
				HashTableDictionary<Person,String>.HashTableStats hashStats = null;
				if (stats instanceof HashTableDictionary.HashTableStats) {
					hashStats = (HashTableDictionary<Person,String>.HashTableStats)stats;
					assertTrue(hashStats.addCollisions >= 0, "Your hashtable does not maintain the collision count stats properly");
					assertTrue(hashStats.fillFactor >= 0.0, "Your hashtable does not maintain the fill factor stats properly");
					assertTrue(hashStats.totalCollisions >= 0, "Your hashtable does not maintain the total collision count properly");
				}
				System.out.format(
					Locale.US,
					"%d,%d,%d,%.2f,%d,%d,%d%n", 
					hashTable.size(), 
					addDuration, 
					getDuration,
					hashStats.fillFactor,
					hashStats.addCollisions,
					hashStats.totalCollisions,
					hashStats.maxProbingSteps);
				currentSize += INCREASE_SIZE;
			}

		} catch (IOException e) {
			fail("Failed to create test data because " + e.getMessage());
		}

	}
	

}
