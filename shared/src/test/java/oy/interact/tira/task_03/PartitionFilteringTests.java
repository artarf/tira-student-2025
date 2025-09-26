package oy.interact.tira.task_03;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.Partition;

@DisplayName("Tests Partition.filter algorithm")
@EnabledIf("checkIfImplemented")
public class PartitionFilteringTests {

    static boolean checkIfImplemented() {
        Integer [] testArray = {1};
        return Partition.filter(testArray, 1, integer -> integer == 1) != null;
    }

    static int indexOfFirstNonConforming = -1;
    static Integer [] filtered = null;

    @Test
    @DisplayName("Testing precondition checks")
    void testPreconditions() {
        final Integer [] piIntegers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 6 };
        assertThrows(IllegalArgumentException.class, () -> Partition.filter(null, 42, null));
        assertThrows(IllegalArgumentException.class, () -> Partition.filter(piIntegers, 42, null));
        assertThrows(IllegalArgumentException.class, () -> Partition.filter(piIntegers, piIntegers.length + 1, value -> value % 2 == 0));
        assertDoesNotThrow(() -> filtered = Partition.filter(piIntegers, piIntegers.length, value -> value % 2 == 0));        
        assertEquals(4, filtered.length, "Filtered array should have four even numbers");
    }

    @Test
    @DisplayName("Test filtering")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testFiltering() {
        final Integer [] piIntegers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 6 };
        final Integer [] piIntegersAndNulls = {null, 3, 1, null, null, 4, 1, 5, 9, null, 2, 6, null, 5, 3, 6, null};
        final Integer [] backupArray = Arrays.copyOf(piIntegersAndNulls, piIntegersAndNulls.length);
        System.out.println(Arrays.toString(piIntegersAndNulls));
        assertDoesNotThrow(() -> filtered = Partition.filter(piIntegersAndNulls, piIntegersAndNulls.length, (number -> number != null ) ), "Partition.filter must not throw");
        System.out.println(Arrays.toString(filtered));
        assertEquals(Arrays.toString(piIntegers), Arrays.toString(filtered), "The numbers in filtered array are wrong");
        assertEquals(Arrays.toString(backupArray), Arrays.toString(piIntegersAndNulls), "The numbers in partitioned array are in wrong order");
    }
    
    @Test
    @DisplayName("Testing with partial array having nulls at end")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testFilteringWithPartialArray() {
        Integer[] piIntegers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 6, null, null, null };
        final Integer[] expected = { 4, 2, 6, 6 };
        System.out.println(Arrays.toString(piIntegers));
        // Pick out even numbers only
        assertDoesNotThrow(() -> filtered = Partition.filter(
                piIntegers,
                11,
                (number -> number % 2 == 0)
            ), "Partition.filter must not throw"
        );
        System.out.println(Arrays.toString(filtered));
        assertEquals(Arrays.toString(expected),
                Arrays.toString(filtered),
                "The numbers in partitioned array are wrong or in wrong order");
    }

    private final String vowels = "[a, e, i, o, u, y, å, ä, ö]";

    @Test
    @DisplayName("Filtering vowel letters according to task intro in material")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testFilteringvowels() {
        Character[] array = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö' };

        System.out.println(toString(array, array.length));
        Character [] filteredvowels = Partition.filter(array, array.length, (character -> vowels.indexOf(character) >= 0));
        assertEquals(9, filteredvowels.length, "There are nine wowels in Finnish so the filtering went wrong");
        for (final Character character : filteredvowels) {
            assertTrue((vowels.indexOf(character) >= 0), "Filtered character is not a vowel");
        }
        System.out.println(toString(filteredvowels, filteredvowels.length));
    }

    @Test
    @DisplayName("Filtering vowel letters according to task intro in material")
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testFilteringConsonants() {
        Character[] array = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö' };
        System.out.println(toString(array, array.length));
        Character [] filteredConsonants = Partition.filter(array, array.length, (character -> vowels.indexOf(character) < 0));
        assertEquals(20, filteredConsonants.length, "There are 20 consonants in Finnish so the filtering went wrong");
        for (final Character character : filteredConsonants) {
            assertTrue((vowels.indexOf(character) < 0), "Filtered character is not a consonant");
        }
        System.out.println(toString(filteredConsonants, filteredConsonants.length));
    }

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


}
