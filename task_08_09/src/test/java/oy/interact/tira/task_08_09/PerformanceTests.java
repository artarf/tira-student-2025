package oy.interact.tira.task_08_09;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Performancetests for BST and Hashtable based Books using different Book files growing in size")
@EnabledIf("checkIfImplemented")
public class PerformanceTests {
    
    private static final String outputFileName = "compare.csv";
    private static final String separator = ",";
    private static int currentIndex = 0;
    private static BufferedWriter writer = null;
    private static final String[] testFiles = {
        "word.txt",
        "tiny.txt",
        "sapmirussian.txt",
        "japanese.txt",
        "small.txt",
        "MetaMorph.txt",
        "Species.txt",
        "Ulysses.txt",
        "WarPeace.txt",
        "UlyssesWarPeace.txt",
        "UlyssesWarPeaceSpecies.txt",
        "Bulk.txt",
    };

    static boolean checkIfImplemented() {
        return BookFactory.createBSTBook() != null && BookFactory.createHashTableBook() != null;
    }
    
    @BeforeAll
    static void openOutputFile() {
        try {
            writer = new BufferedWriter(new FileWriter(outputFileName));
            writer.append("Book,Bytes,Words (n),Unique words (m),Time BST (ms),Time Hashtable (ms),log2(n),log2(n) + m log2(m)");
            writer.newLine();
        } catch (IOException e) {
            fail("Could not open test output file for writing");
        }
    }

    @Test 
    void handleTestFiles() {
        if (null != writer) {
            try {
                while (currentIndex < testFiles.length) {
                    System.out.println("==> Starting to analyse book " + testFiles[currentIndex]);
                    String path = getFullPathToTestFile(testFiles[currentIndex]);
                    File file = new File(path);
                    long fileSize = file.length();
                    file = null;

                    // Test BST first
                    BookBase testBook = BookFactory.createBSTBook();
                    String ignoreFile = getFullPathToTestFile("ignore-words.txt");
                    long start = System.currentTimeMillis();
                                
                    testBook.setSource(path, ignoreFile);
                    testBook.countUniqueWords();
                    testBook.report();
                    long end = System.currentTimeMillis();
                    long durationBST = end - start;
                    
                    /*
                    testfile,bytes,words,unique words,time,nˆ2,n*m,log(n*m), n*log(n*m)
                    */
                    long totalWordsBST = testBook.getTotalWordCount();
                    long uniqueWordsBST = testBook.getUniqueWordCount();
                    testBook.close();
                    testBook = null;

                    // Write BST results
                    writer.append(testFiles[currentIndex]);
                    writer.append(separator);
                    writer.append(Long.toString(fileSize));
                    writer.append(separator);
                    writer.append(Long.toString(totalWordsBST));
                    writer.append(separator);
                    writer.append(Long.toString(uniqueWordsBST));
                    writer.append(separator);
                    writer.append(Long.toString(durationBST));
                    writer.append(separator);

                    // Test HasHtable
                    // Test BST first
                    testBook = BookFactory.createHashTableBook();
                    start = System.currentTimeMillis();

                    testBook.setSource(path, ignoreFile);
                    testBook.countUniqueWords();
                    testBook.report();
                    end = System.currentTimeMillis();
                    long durationHashTable = end - start;

                    /*
                     * testfile,bytes,words,unique words,time,nˆ2,n*m,log(n*m), n*log(n*m)
                     */
                    long totalWordsHashTable = testBook.getTotalWordCount();
                    long uniqueWordsHashTable = testBook.getUniqueWordCount();
                    writer.append(Long.toString(durationHashTable));
                    writer.append(separator);

                    assertEquals(totalWordsBST, totalWordsHashTable, "Total word count for BST and HashTable books should be the same");
                    assertEquals(
                            uniqueWordsBST, 
                            uniqueWordsHashTable,
                            "Unique word count for BST and HashTable books should be the same");
                    testBook.close();
                    testBook = null;

                    // log(n), bst time efficiency
                    long base2LogTotalWords = (long)(Math.log10(totalWordsBST) / Math.log10(2));
                    writer.append(Long.toString(base2LogTotalWords));
                    writer.append(separator);
                    // bst add time efficiency + quicksort, log(n) + m log(m)
                    long bstAddPlusFastSort = base2LogTotalWords;
                    bstAddPlusFastSort += uniqueWordsBST * ((long)(Math.log10(uniqueWordsBST) / Math.log10(2)));
                    writer.append(Long.toString(bstAddPlusFastSort));
                    writer.newLine();
                    writer.flush();
                    currentIndex++;
                }
            } catch (Exception e) {
                fail("Could not write test output file: " + e.getMessage());
            }    
        } else {
            fail("Cannot run tests since opening output file writer failed.");
        }
    }

    @AfterAll
    static void closeOutputFile() {
        try {
            writer.close();
        } catch (IOException e) {
            fail("Could not close test output file");
        }
    }

    private String getFullPathToTestFile(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }
}
