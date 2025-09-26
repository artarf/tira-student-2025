package oy.interact.tira.task_08_09;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Locale;

import oy.interact.tira.student.ArrayUtils;
import oy.interact.tira.student.FastSort;
import oy.interact.tira.util.Pair;

public abstract class BookBase {

    private String bookFile = null;
    private String wordsToIgnoreFile = null;
    private WordFilter filter = null;
    private static final int MAX_WORD_LEN = 100;
    private int ignoredWordsTotal = 0;
    private static final int TOP_WORD_COUNT = 100;
    private Pair<String, Integer> [] topWords = null;

    private int totalWordCount = 0;

    /**
     * Method must create a BST or a Hashtable to be used in counting unique words.
     */
    protected abstract void createDataStructure();
    /**
     * Method must add a word to the data structure or update the occurrence
     * count of the word in the data structure (if it already was in the data).
     * @param word The word to add or update.
     */
    protected abstract void insert(final String word);
    /**
     * Returns the count of occurrences for the word or zero.
     * @param word The word to get the occurrence count for.
     * @return
     */
    protected abstract int getWordCount(final String word);
    /**
     * Use this to get the count of unique words in the data structure.
     * @return The count of unique words.
     */
    protected abstract int uniqueWordCount();
    /**
     * Returns the count of all words handled by the data structure
     * @return The count of all words handled.
     */
    protected abstract Pair<String,Integer> [] getAllWordCounts();
    /**
     * Resets the data structure used; clears the memory and erases the object to null.
     */
    protected abstract void resetDataStructure();

    /**
     * Returns the name of the implementation for printing.
     * @return The name, e.g. "LinearArray", "BST", "Hashtable".
     */
    public abstract String getImplementationName();

    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        // Check if both files exist. If not, throw an exception.
        boolean success = false;
        if (checkFile(fileName)) {
            bookFile = fileName;
            if (checkFile(ignoreWordsFile)) {
                wordsToIgnoreFile = ignoreWordsFile;
                success = true;
            }
        }
        if (!success) {
            throw new FileNotFoundException("Have you unzipped the book files!? Cannot find the specified files");
        }
        totalWordCount = 0;
        resetDataStructure();
    }

    public void countUniqueWords() throws IOException, OutOfMemoryError {
        if (bookFile == null || wordsToIgnoreFile == null) {
            throw new IOException("No file(s) specified");
        }
        // Reset the counters
        totalWordCount = 0;
        ignoredWordsTotal = 0;
        // Create the filter class to handle filtering.
        filter = new WordFilter();
        // Read the words to filter.
        filter.readFile(wordsToIgnoreFile);
        // Create the concrete data structure
        createDataStructure();

        // Read the book files using a Java FileReader class with UTF-8 encoding (specified in StandardCharsets).
        FileReader reader = new FileReader(bookFile, StandardCharsets.UTF_8);
        // Character read from the file
        int character;
        // Array holds the code points of the UTF-8 encoded chars.
        int[] wordCharsArray = new int[MAX_WORD_LEN];
        // currentIndex is the index where the currently read char was put into wordCharsArray
        int currentIndex = 0;
        while ((character = reader.read()) != -1) {
            // STUDENT: TODO implement this loop by building a word from the characters
            // as specified in the instructions and comments, calling insert(word) when a
            // word is ready. insert is an abstract method so your subclasses
            // (BST and Hashtable based) will handle the word.

            // If the character is a letter, then add it to the array...
            // ...otherwise a word break was met, so handle the word just read.
                // If a word was actually read, then create a string out of the codepoints,
                // normalizing the word to lowercase.
                // Reset the word chars array counter for the next word read.
                // Filter out too short words or words in filter list (do not insert them)
                // Inserting only a non-filtered word into the data structure.
        }

        // STUDENT TODO: Must check the last word in the file too here!
        // There may be chars in the array, not yet handled in the loop, 
        // when filereder read() returns -1 to indicate EOF.

        // REMEMBER to keep track of total and ignored word counts in the loop!

        // Close the file reader.
        reader.close();
    }

    public void report() {
        if (uniqueWordCount() == 0) {
            System.out.println("*** No words to report! ***");
            return;
        }
        System.out.println("Listing words from a file: " + bookFile);
        System.out.println("Ignoring words from a file: " + wordsToIgnoreFile);
        System.out.println("Sorting the results...");
        // STUDENTS TODO:
        // First get the array from concrete implementation by calling getAllWordCounts,
        // and then sort words to descending order by word count. If words have
        // equal count, then compare by the word in ascending (alphabetical order)

        System.out.println("...sorted.");
        // STUDENTS TODO
        // calculate the size for the results (<= TOP_WORD_COUNT),
        // allocate a new array for the results to member variable topWords (!)
        // and print out the results. Results must be in topWords member variable since
        // they are used later after report() has finished (see Methods for tests below).

        System.out.format("Using implementation: %s%n", getImplementationName());
        System.out.println("Count of words in total: " + totalWordCount);
        System.out.println("Count of words to ignore:    " + filter.ignoreWordCount());
        System.out.println("Ignored words count:      " + ignoredWordsTotal);
        System.out.format("Unique word count: %d%n", uniqueWordCount());
    }

    public void close() {
        bookFile = null;
        wordsToIgnoreFile = null;
        resetDataStructure();
        if (filter != null) {
            filter.close();
        }
        filter = null;
        topWords = null;
    }

    // Methods for tests - gets the unique word count
    // from the concrete implementation class (BST or hashtable)
    public int getUniqueWordCount() {
        return uniqueWordCount(); // Concrete subclass must implement this!
    }

    // Methods for tests - return the total count
    // of words maintained in reading the book files.
    public int getTotalWordCount() {
        return totalWordCount;
    }

    // Methods for tests - report() must generate the content
    // to topWords. This method then uses that informtion to 
    // check that the results are correct.
    public String getWordInListAt(int position) {
        if (topWords != null && position > 0 && position-1 < topWords.length) {
            return topWords[position-1].getKey();
        }
        return null;
    }

    // Methods for tests - report() must generate the content
    // to topWords. This method then uses that informtion to 
    // check that the results are correct.
    public int getWordCountInListAt(int position) {
        if (topWords != null && position > 0 && position-1 < topWords.length) {
            return topWords[position-1].getValue();
        }
        return -1;
    }

    // Methods for tests - report() must generate the content
    // to topWords. This method then uses that informtion to 
    // check that the results are correct.
    public int getWordCountForWord(final String word) {
        for (final Pair<String,Integer> wordCount : topWords) {
            if (wordCount.getKey().equals(word)) {
                return wordCount.getValue();
            }
        }
        return -1;
    }

    private boolean checkFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists() && !file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

}
