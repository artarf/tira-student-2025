package oy.interact.tira.task_09;

import static org.junit.jupiter.api.Assertions.fail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.Person;
import oy.interact.tira.util.PersonUtils;

@DisplayName("Testing Person.hashCode() implementation")
@EnabledIf("checkIfImplemented")
class PersonHashTests {

	private static final int MAX_PERSONS_TO_TEST = 1_000_000;

	static boolean checkIfImplemented() {
		final Person test = new Person("Test", "Person", "Testable", 42);
		return test.hashCode() != 0;
	}

	@Test
	void testHashUniqueness() {
		try {
			Person [] persons = PersonUtils.createPersons(MAX_PERSONS_TO_TEST);
			System.out.format(
					"==> Starting to analyse Person.hashCode with %,d persons %s%n",
					persons.length,
					new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			// Check that id hash is not the same has person.hash
			Integer [] idHashes = new Integer[persons.length];
			Integer [] coderHashes = new Integer[persons.length];
			// get hash both from coder.hashCode() and coder.getId().hashCode()
			int index = 0;
			int negativeHashesCount = 0;
			for (final Person person : persons) {
				idHashes[index] = person.getId().hashCode();
				coderHashes[index] = person.hashCode();
				if (coderHashes[index] < 0) {
					negativeHashesCount++;
				}
				index++;
			}
			if (negativeHashesCount == 0) {
				fail("Your Person.hashCode() limits hashes to values >= 0 which is not good at all");
			}
			// Count how many times the coder.hashCode is identical to coder.getId().hashCode()
			int sameHashCount = 0;
			for (index = 0; index < idHashes.length; index++) {
				if (idHashes[index].equals(coderHashes[index])) {
					sameHashCount++;
				}
			}
			if (sameHashCount == idHashes.length) {
				fail("You have not implemented Person.hashCode() correctly, but just call id.hashCode(). READ THE INSTRUCTIONS!");
			}
			// Also, count how many times the coder.hashCode() returns the same value
			Set<Integer> coderHashesInSet = new HashSet<>();
			coderHashesInSet.addAll(Arrays.asList(coderHashes));
			if (coderHashesInSet.size() == 1) {
				fail("Your Person.hashCode() returns the same value always. Implement a proper hash function!");
			}		
			coderHashesInSet = null;	
			long start = System.currentTimeMillis();
			Set<Integer> hashes = new HashSet<>();
			for (final Person person : persons) {
				hashes.add(person.hashCode());
			}
			long end = System.currentTimeMillis();
			long duration = end - start;
			System.out.format("  Testing Person.hashCode took %d ms%n", duration);
			System.out.format("  For %,d persons, there were %d duplicate hashes%n%n", persons.length, persons.length - hashes.size());
		} catch (OutOfMemoryError oom) {
			System.out.println("\n*** ERROR: Run out of memory while handling test data\n");
		} catch (Exception e) {
			e.printStackTrace();
			fail("*** Could not execute tests: " + e.getMessage());
		}
	}

}
