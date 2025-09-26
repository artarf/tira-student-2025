package oy.interact.tira.task_03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.Partition;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.PersonUtils;

@DisplayName("Test the Partition.byRule algorithm")
@EnabledIf("checkIfImplemented")
public class PartitionByRuleTests {

    static private int indexOfFirstNonConforming = -1;

    static boolean checkIfImplemented() {
        Integer [] testArray = {1};
        return Partition.byRule(testArray, 1, integer -> integer == 1) != -1;
    }

    @Test
    @DisplayName("Lecture sample 1 test")
    void testLectureSample1() {
        Integer [] testArray = {10, 5, 4, 12, 7, 9, 3, 11};
        System.out.println(Arrays.toString(testArray));
        assertDoesNotThrow(() -> indexOfFirstNonConforming = Partition.byRule(testArray, testArray.length, value -> value % 2 == 0));        
        System.out.println(Arrays.toString(testArray));
        assertEquals(3, indexOfFirstNonConforming, "First not even number should be in index 3");
        Integer [] expectedFirstPart = {10, 4, 12};
        Integer [] resultBegin = Arrays.copyOf(testArray, 3);
        assertArrayEquals(expectedFirstPart, resultBegin, "Expected partitioned array's first part is not correct");
    }

    @Test
    @DisplayName("Lecture sample 2 test")
    void testLectureSample2() {
        Integer [] testArray = {10, 5, 4, 12, 7, 9, 3, 11, 6};
        System.out.println(Arrays.toString(testArray));
        assertDoesNotThrow(() -> indexOfFirstNonConforming = Partition.byRule(testArray, testArray.length, value -> value % 2 == 0));        
        System.out.println(Arrays.toString(testArray));
        assertEquals(4, indexOfFirstNonConforming, "First not even number should be in index 4");
        Integer [] expectedFirstPart = {10, 4, 12, 6};
        Integer [] resultBegin = Arrays.copyOf(testArray, 4);
        assertArrayEquals(expectedFirstPart, resultBegin, "Expected partitioned array's first part is not correct");
    }

    @Test
    @DisplayName("Testing precondition checks for Partition.byRule")
    void testPreconditions() {
        final Integer [] piIntegers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 6 };
        assertThrows(IllegalArgumentException.class, () -> Partition.byRule(null, 42, null));
        assertThrows(IllegalArgumentException.class, () -> Partition.byRule(piIntegers, 42, null));
        assertThrows(IllegalArgumentException.class, () -> Partition.byRule(piIntegers, piIntegers.length + 1, value -> value % 2 == 0));
        assertDoesNotThrow(() -> indexOfFirstNonConforming = Partition.byRule(piIntegers, piIntegers.length, value -> value % 2 == 0));        
        assertEquals(4, indexOfFirstNonConforming, "First not even number should be in index 4");
    }

    @Test
    @DisplayName("Testing if can partition nulls to the end of the array of Integers")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testWithPiNumbersAndNulls() {
        final Integer [] piIntegers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 6 };
        Integer [] piIntegersAndNulls = {null, 3, 1, null, null, 4, 1, 5, 9, null, 2, 6, null, 5, 3, 6, null};
        System.out.println(Arrays.toString(piIntegersAndNulls));
        assertDoesNotThrow(() -> indexOfFirstNonConforming = Partition.byRule(piIntegersAndNulls, piIntegersAndNulls.length, (number -> number != null ) ), "Partition.byRule must not throw");
        System.out.println(Arrays.toString(piIntegersAndNulls));
        assertEquals(11, indexOfFirstNonConforming, "Returned index from Partition.byRule is not correct");
        assertEquals(Arrays.toString(piIntegers), Arrays.toString(Arrays.copyOf(piIntegersAndNulls, indexOfFirstNonConforming)), "The numbers in partitioned array are in wrong order");
    }

    @Test
    @DisplayName("Test partitioning even numbers, with array having nulls at end")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testWithPartialArray() {
        Integer[] piIntegers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 6, null, null, null };
        final Integer [] expected = { 4, 2, 6, 6 };
        System.out.println(Arrays.toString(piIntegers));
        // Pick out even numbers only
        assertDoesNotThrow(() -> indexOfFirstNonConforming = Partition.byRule(
                piIntegers,
                11, (number -> number % 2 == 0)
            ), "Partition.byRule must not throw"
        );
        System.out.println(Arrays.toString(piIntegers));
        assertEquals(4, indexOfFirstNonConforming, "Returned index from Partition.byRule is not correct");
        assertEquals(Arrays.toString(expected),
                Arrays.toString(Arrays.copyOf(
                        piIntegers, indexOfFirstNonConforming)),
                "The numbers in partitioned array are wrong or in wrong order");
    }

    @Test
    @DisplayName("Testing partition persons with short first names to the beginning of the array")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testWithPersonShortFirstNameLengths() {
        try {
            Person [] persons = PersonUtils.createPersons(100);
            assertDoesNotThrow(() -> indexOfFirstNonConforming = Partition.byRule(persons, persons.length, ( person -> person.getFirstName().length() < 5 ) ), "Partition.byRule must not throw");
            System.out.format("Found %d persons out of %d, with first name shorter than 5 letters:%n", indexOfFirstNonConforming - 1, persons.length);
            for (int index = 0; index < indexOfFirstNonConforming; index++) {
                System.out.print(persons[index].getFirstName());
                if (index < indexOfFirstNonConforming - 1) {
                    System.out.print(", ");
                }
                assertTrue(persons[index].getFirstName().length() < 5, "Person's first name was longer than four characters");
            }
            System.out.println("");
            boolean errorFound = false;
            for (int index = indexOfFirstNonConforming; index < persons.length; index++) {
                if (persons[index].getFirstName().length() < 5) {
                    System.out.format("ERROR: first name len < 5 found at index %d: %s", index, persons[index].getFirstName());
                    errorFound = true;
                }
            }
            assertFalse(errorFound, "Found persons with first name length < 5 ay/after index returned by Partition.byRule");
        } catch (IOException e) {
            fail("Creating persons failed for reason: " + e.getMessage());
        }
    }

    private final String vowels = "[a, e, i, o, u, y, å, ä, ö]";

    @Test
    @DisplayName("Partitioning letters according to task intro in material")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testPartitionvowels() {
        Character[] array = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö' };

        printArrayAndIndices(array);

        int firstConsonantIndex = Partition.byRule(array, array.length, character -> vowels.indexOf(character) >= 0);
        if (firstConsonantIndex < 0) { return; }

        printArrayAndIndices(array);

        int ops = 0;
        for (int index = firstConsonantIndex; index < array.length; index++) {
            array[index] = null;
            ops++;
        }
        printArrayAndIndices(array);
        System.out.format("** Partitioning: add this removal count to partition ops: %d%n", ops);
        assertEquals(vowels, toString(array, firstConsonantIndex));
        verifyCharArrayResult(array, vowels, 9);
    }

    @Test
    @DisplayName("Test with task character array and upwards for loop")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testvowelsWithSlowForLoop() {
        Character[] array = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö' };
        printArrayAndIndices(array);
        int ops = 0;
        for (int index = 0; index < array.length; index++) {
            // This is not buggy but slow, since it does the removal
            // and shifting elements down for _each_ element removed.
            if (array[index] != null && vowels.indexOf(array[index]) < 0) {
                for (int shift = index; shift < array.length - 1; shift++) {
                    array[shift] = array[shift+1];
                    array[shift+1] = null;
                    ops += 2;
                }
                index -= 1; // since for loop increases index by one
            }
        }
        System.out.format("** Slow for loop ops count total: %d%n", ops);
        printArrayAndIndices(array);
        assertEquals(vowels, toString(array, 9));
        verifyCharArrayResult(array, vowels, 9);
    }

    @Test
    @DisplayName("Test with task character array and downwards for loop")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testWithFasterForLoop() {
        Character[] array = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö' };
        int ops = 0;
        for (int index = array.length - 1; index >= 0; index--) {
            // This is not buggy but slow, since it does the removal
            // and shifting elements down for _each_ element removed.
            if (vowels.indexOf(array[index]) < 0) {
                array[index] = null;
                ops += 1;
                for (int shift = index + 1; shift < array.length; shift++) {
                    array[shift-1] = array[shift];
                    array[shift] = null;
                    ops += 2;
                }
            }
        }
        System.out.format("** Fast for loop ops count total: %d%n", ops);
        printArrayAndIndices(array);
        assertEquals(vowels, toString(array, 9));
        verifyCharArrayResult(array, vowels, 9);
    }

    // Helper funcs

    private String toString(Character[] array, int count) {
        StringBuilder builder = new StringBuilder("[");
        int counter = 0;
        for (final Character character : array) {
            builder.append(character);
            counter++;
            if (counter < count) {
                builder.append(", ");
            } else {
                break;
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private void printArrayAndIndices(final Character[] array) {
        for (final Character c : array) {
            if (c != null) {
                System.out.format("%3c", c);
            } else {
                System.out.format("%3c", '_');
            }
        }
        System.out.println("");
        for (int index = 0; index < array.length; index++) {
            System.out.format("%3d", index);
        }
        System.out.println("");
    }
    
    private void verifyCharArrayResult(Character [] array, final String vowels, int partitioningIndex) {
        for (int index = 0; index < partitioningIndex; index++) {
            if (array[index] == null) {
                throw new RuntimeException("Nulls in array prefix, not correctly partitioned char array");
            }
            if (vowels.indexOf(array[index]) < 0) {
                throw new RuntimeException("Not a vowel in array prefix, not correctly partitioned char array");
            }
        }
        for (int index = partitioningIndex; index < array.length; index++) {
            if (array[index] != null && vowels.indexOf(array[index]) >= 0) {
                throw new RuntimeException("vowel in array suffix, not correctly partitioned char array");
            }
        }
    }

}
