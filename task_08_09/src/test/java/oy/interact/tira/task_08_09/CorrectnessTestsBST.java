package oy.interact.tira.task_08_09;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

/**
 * Unit test for BooksAndWords.
 */
@DisplayName("Correctness tests for BST book")
@EnabledIf("checkIfImplemented")
public class CorrectnessTestsBST 
{
    /**
     * Test using small.txt
     */
    private int totalWordCount = -1;
    private int uniqueWordCount = -1;
    private String expectedWord = "";
    private int expectedCount = -1;

    static boolean checkIfImplemented() {
		return BookFactory.createBSTBook() != null;
	}

    @Test 
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testFilesNotGiven() {
        BookBase testTarget = BookFactory.createBSTBook();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertThrows(Exception.class, () -> { testTarget.countUniqueWords(); }, "Should throw if no files given.");
        assertDoesNotThrow(() -> { testTarget.close(); }, "Should not throw even though files not set/wrong." );
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testEmptyBookFile() {
        System.out.println("Starting to test emptybook.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("emptybook.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertEquals(0, totalWordCount, () -> "Total word count not expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertEquals(0,uniqueWordCount, () -> "Unique word count not expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(0); }, "Failed to get word.");
        assertNull(expectedWord, () -> "For empty book, getWordInListAt(0) must return null" );
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(0); }, "Failed to get word count.");
        assertEquals(-1, expectedCount, () -> "Unique word count not expected, should be -1 for an empty book file.");
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testOneWordBookFile() {
        System.out.println("Starting to test word.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("word.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertEquals(1, totalWordCount, () -> "Total word count not expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertEquals(1,uniqueWordCount, () -> "Unique word count not expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
        assertEquals("heidän", expectedWord, () -> "For one word book, getWordInListAt(0) must return the one word in the book" );
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
        assertEquals(1, expectedCount, () -> "Should be 1 for a book file with one word.");
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testNonExistentBookFile() {
        BookBase testTarget = BookFactory.createBSTBook();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertThrows(Exception.class, () -> { testTarget.setSource("dfgsehjstjsr43sh5sth.txt", "dfsy56ksthserg4fgsd.txt"); }, "Must throw if book file does not exist.");
        assertThrows(Exception.class, () -> { testTarget.countUniqueWords(); }, "Should throw if using invalid files.");
        totalWordCount = -1; 
        assertDoesNotThrow(() -> { testTarget.close(); }, "Should not throw even though files not set/wrong." );
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testTinyBookFile() {
        System.out.println("Starting to test tiny.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("tiny.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        long start = System.currentTimeMillis();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertEquals(38, totalWordCount, () -> "Total word count not expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertEquals(29,uniqueWordCount, () -> "Unique word count not what expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
        assertEquals("words", expectedWord, () -> "Expected \"words\" as the top-1 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
        assertEquals(4, expectedCount, () -> "Unique word count not expected.");
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Test took " + duration + " ms");
        if (duration > 100) {
            System.out.println("****** WARNING your implementation may be too slow!!");
        }
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testSmallFile()
    {
        try {
            System.out.println("Starting to test small.txt...");
            BookBase testTarget = BookFactory.createBSTBook();
            String testBook = getFullPathToTestFile("small.txt");
            String ignoreFile = getFullPathToTestFile("ignore-words.txt");
            long start = System.currentTimeMillis();
            assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
            assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
            assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
            assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
            totalWordCount = -1; 
            assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
            assertTrue(Math.abs(totalWordCount - 2308) < 5, () -> "Total word count too different from expected.");
            assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
            assertTrue(Math.abs(uniqueWordCount - 233) < 2, () -> "Unique word count too different from expected.");
            assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
            assertEquals("in", expectedWord, () -> "Expected \"in\" as the top-1 word but got " + expectedWord);
            assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
            assertTrue(Math.abs(expectedCount - 78) < 2, () -> "Unique word count too different from expected.");
            long end = System.currentTimeMillis();
            long duration = end - start;
            System.out.println("Test took " + duration + " ms");
            if (duration > 100) {
                System.out.println("****** WARNING your implementation may be too slow!!");
            }
            assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
        } catch (Exception e) {
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testSapmiRussianFile() {
        System.out.println("Starting to test sapmirussian.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("sapmirussian.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        long start = System.currentTimeMillis();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertTrue(Math.abs(totalWordCount - 455) < 3, () -> "Total word count too different from expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertTrue(Math.abs(uniqueWordCount - 347) < 3, () -> "Unique word count too different from expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(3); }, "Failed to get word.");
        assertEquals( "lea", expectedWord, () -> "Expected \"lea\" as the top-3 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(3); }, "Failed to get word count.");
        assertEquals(7, expectedCount, () -> "Unique word count not what expected.");

        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
        assertEquals("ja", expectedWord, () -> "Expected \"ja\" as the top-1 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
        assertEquals(17, expectedCount, () -> "Unique word count for top-11 too different from expected.");

        assertDoesNotThrow(() -> uniqueWordCount = testTarget.getWordCountForWord("внутренних"),
                "getWordCountForWord must not throw");
        assertEquals(3, uniqueWordCount, "Word count for word \"внутренних\" must be 3");

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Test took " + duration + " ms");
        if (duration > 500) {
            System.out.println("****** WARNING your implementation may be too slow!!");
            System.out.println("****** WARNING Check the execution time against a very slow implementation.");
        }
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    @Test
    // @Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testJapaneseFile() {
        System.out.println("Starting to test japanese.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("japanese.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        long start = System.currentTimeMillis();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertTrue(Math.abs(totalWordCount - 556) < 3, () -> "Total word count too different from expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertTrue(Math.abs(uniqueWordCount - 134) < 3, () -> "Unique word count too different from expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(4); }, "Failed to get word.");
        assertEquals( "suru", expectedWord, () -> "Expected \"suru\" as the top-4 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(3); }, "Failed to get word count.");
        assertEquals(24, expectedCount, () -> "Unique word count not what expected.");

        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
        assertEquals("no", expectedWord, () -> "Expected \"no\" as the top-1 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
        assertEquals(50, expectedCount, () -> "Unique word count for top-11 too different from expected.");

        assertDoesNotThrow(() -> uniqueWordCount = testTarget.getWordCountForWord("kōsuwāku"), "getWordCountForWord must not throw");
        assertEquals(3, uniqueWordCount, "Word count for word \"kōsuwāku\" must be 3");

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Test took " + duration + " ms");
        if (duration > 500) {
            System.out.println("****** WARNING your implementation may be too slow!!");
            System.out.println("****** WARNING Check the execution time against a very slow implementation.");
        }
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    @Test
    // @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testWarPeaceFile() {
        System.out.println("Starting to test WarPeace.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("WarPeace.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        long start = System.currentTimeMillis();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertTrue(Math.abs(totalWordCount - 480967) < 20, () -> "Total word count too different from expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertTrue(Math.abs(uniqueWordCount - 17560) < 5, () -> "Unique word count too different from expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
        assertEquals( "to", expectedWord, () -> "Expected \"to\" as the top-1 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
        assertTrue(Math.abs(expectedCount - 16755) < 3, () -> "Unique word count too different from expected.");

        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(10); }, "Failed to get word.");
        assertEquals("her", expectedWord, () -> "Expected \"her\" as the top-10 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(10); }, "Failed to get word count.");
        assertTrue(Math.abs(expectedCount - 4725) < 2, () -> "Unique word count for top-10 too different from expected.");

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Test took " + duration + " ms");
        if (duration > 500) {
            System.out.println("****** WARNING your implementation may be too slow!!");
            System.out.println("****** WARNING Check the execution time against a very slow implementation.");
        }
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    @Test
    // @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void testBulkFile() {
        System.out.println("Starting to test Bulk.txt...");
        BookBase testTarget = BookFactory.createBSTBook();
        String testBook = getFullPathToTestFile("Bulk.txt");
        String ignoreFile = getFullPathToTestFile("ignore-words.txt");
        long start = System.currentTimeMillis();
        assertNotNull(testTarget, () -> "BookFactory.createBSTBook() returned null.");
        assertDoesNotThrow( () -> { testTarget.setSource(testBook, ignoreFile); }, "Setting test files failed.");
        assertDoesNotThrow( () -> { testTarget.countUniqueWords(); }, "Failed to process the book/ignore files.");
        assertDoesNotThrow( () -> { testTarget.report(); }, "Failed to print out the report.");
        totalWordCount = -1; 
        assertDoesNotThrow( () -> { totalWordCount = testTarget.getTotalWordCount(); }, "Failed to get total word count.");
        assertTrue(Math.abs(totalWordCount - 2378668) < 10, () -> "Total word count too different (over 100) from expected.");
        assertDoesNotThrow( () -> { uniqueWordCount = testTarget.getUniqueWordCount(); }, "Failed to get unique word count.");
        assertTrue(Math.abs(uniqueWordCount - 97115) < 10, () -> "Unique word count too different (over 50) from expected.");
        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(1); }, "Failed to get word.");
        assertEquals( "ja", expectedWord, () -> "Expected \"ja\" as the top-1 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(1); }, "Failed to get word count.");
        assertTrue(Math.abs(expectedCount - 62796) < 10, () -> "Unique word count too different from expected.");

        assertDoesNotThrow( () -> { expectedWord = testTarget.getWordInListAt(42); }, "Failed to get word.");
        assertEquals("heidan", expectedWord, () -> "Expected \"heidan\" as the top-42 word but got " + expectedWord);
        assertDoesNotThrow( () -> { expectedCount = testTarget.getWordCountInListAt(42); }, "Failed to get word count.");
        assertTrue(Math.abs(expectedCount - 6987) < 4, () -> "Unique word count for top-42 too different from expected.");

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Test took " + duration + " ms");
        if (duration > 1000) {
            System.out.println("****** WARNING your implementation may be too slow!!");
            System.out.println("****** WARNING Check the execution time against a very slow implementation.");
        }
        assertDoesNotThrow(() -> { testTarget.close(); }, "Closing the book should not throw.");
    }

    private String getFullPathToTestFile(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }
}
