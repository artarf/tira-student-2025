package oy.interact.tira.task_03;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.ArrayUtils;
import oy.interact.tira.student.Partition;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.PersonUtils;

@DisplayName("Test the Partition.filter algorithm time efficiency against for loop")
@EnabledIf("checkIfImplemented")
public class FilterEfficiencyTests {

    static private final int DATASET_SIZE = 50_000;
    static private Person[] persons = null;
    static Person [] filtered = null;
    static private int indexOfFirstNonConforming = -1;
    static private long partitioningSpeed = 0;
    static private long loopSpeed = 0;

    static boolean checkIfImplemented() {
        Integer[] testArray = { 1 };
        return Partition.filter(testArray, 1, integer -> integer == 1) != null;
    }

    @BeforeAll
    static void printInfo() {
        System.out.println("\n*** Comparing filtering/partitioning to for loop speed ***");
        System.out.println(" - Test is repeated five (5) times to get average speed of algorithms");
        System.out.format(" - Data set size is %,d so wait a while for the results...%n", DATASET_SIZE);
        partitioningSpeed = 0;
        loopSpeed = 0;
    }

    @BeforeEach
    void createPersons() {
        try {
            persons = PersonUtils.createPersons(DATASET_SIZE);      
        } catch (Exception e) {
            fail("Test cannot be executed since person array cannot be created");
        }
    }

    @RepeatedTest(5)
    // @Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testWithPersonShortFirstNameLengths(RepetitionInfo repetitionInfo) {
        System.out.format("Repetition %d/%d%n", repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions());

        ArrayUtils.shuffle(persons);
        System.out.println(" --- starting to partition...");
        long start = System.currentTimeMillis();
        assertDoesNotThrow(() -> filtered = Partition.filter(persons, persons.length,
                (person -> person.getFirstName().length() < 5)), "Partition.byRule must not throw");
        long currentSpeed = System.currentTimeMillis() - start;
        partitioningSpeed += currentSpeed;
        indexOfFirstNonConforming = filtered.length;
        System.out.format("   --- time: %d ms, %,d persons out of %,d with short first name%n",
                currentSpeed, indexOfFirstNonConforming, persons.length);
        verifyResult(filtered, indexOfFirstNonConforming);

        ArrayUtils.shuffle(persons);
        System.out.println(" --- starting the for loop partitioning...");
        start = System.currentTimeMillis();
        for (int index = persons.length - 1; index >= 0; index--) {
            if (persons[index].getFirstName().length() >= 5) {
                persons[index] = null;
                for (int shift = index + 1; shift < persons.length; shift++) {
                    persons[shift - 1] = persons[shift];
                    persons[shift] = null;
                }
            }
        }
        currentSpeed = System.currentTimeMillis() - start;
        loopSpeed += currentSpeed;
        System.out.format("   --- loop took %d ms%n", currentSpeed);
        verifyResult(persons, indexOfFirstNonConforming);

    }

    @AfterAll
    static void verifySpeed() {
        partitioningSpeed /= 5;
        loopSpeed /= 5;
        System.out.println("\n FINISHED: Compare the speeds of filter/partition and for loop implementation");
        System.out.println(" - Filter/partition should be way faster than the for loop");
        System.out.format(" - Filter/partition speed average: %5d ms%n", partitioningSpeed);
        System.out.format(" - for loop speed average    : %5d ms%n", loopSpeed);
        double difference = loopSpeed / (double)partitioningSpeed;
        System.out.format(" - Difference: %.2f%n", difference);
        if (DATASET_SIZE > 5_000) {
            assertTrue(difference > 100.0, "Difference is small, are you sure your filter/partitioning is implemented correctly"); 
        }
    }

    // Helper funcs

    private void verifyResult(Person[] persons, int partitioningIndex) {
        for (int index = 0; index < partitioningIndex; index++) {
            assertTrue(persons[index].getFirstName().length() < 5,
                    String.format("Person at index %d first name %s was longer than four characters", index, persons[index].getFirstName()));
        }
        boolean errorFound = false;
        for (int index = indexOfFirstNonConforming; index < persons.length; index++) {
            if (persons[index] != null) {
                System.out.format("ERROR: first name len < 5 found at index %d: %s", index,
                        persons[index].getFirstName());
                errorFound = true;
            }
        }
        assertFalse(errorFound, "Found persons at/after partitioned index");
    }

}
