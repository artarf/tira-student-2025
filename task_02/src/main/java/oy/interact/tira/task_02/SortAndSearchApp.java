package oy.interact.tira.task_02;

import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import oy.interact.tira.student.BinSearch;
import oy.interact.tira.util.LinearSearchArray;
import oy.interact.tira.student.Person;
import oy.interact.tira.util.PersonUtils;
import oy.interact.tira.util.SimpleSort;

/**
 * Hello world!
 *
 */
public class SortAndSearchApp {

    private static final int STARTING_COUNT = 1000;
    private static final int INCREASED_BY_COUNT = 1000;
    private static final int END_COUNT = 50_000;
    private static final int SEARCHES_PER_LOOP = 100;

    public static void main( String[] args ) {
        doPerformanceTest();
        doYourSearch();        
    }

    private static void doPerformanceTest() {
        try {
            long linearSearchDuration = 0;
            long sortDuration = 0;
            long binarySearchDuration = 0;

            System.out.println( "\nStarting to compare linear and binary search time efficiency");
            System.out.println( "Time unit used is microseconds (us), 1 s is == 1 000 000 us");
            System.out.println( " - 1 second == 1 000 000 us");
            System.out.println( " - 1 millisecond == 1 000 us");
            System.out.format("Max dataset size (n) is %,d%n", END_COUNT);
            System.out.format( "Searching %d times from each dataset%n", SEARCHES_PER_LOOP);
            System.out.format("n,Linear search,Insertion sort,Binary search%n");

            long totalStart = System.currentTimeMillis();

            for (int count = STARTING_COUNT; count <= END_COUNT; count += INCREASED_BY_COUNT) {
                Person [] persons = PersonUtils.createPersons(count);
	
                int searchCounter = SEARCHES_PER_LOOP;
                long start = System.nanoTime();
                while (searchCounter-- > 0) {
                    int indexToSearch = ThreadLocalRandom.current().nextInt(persons.length);
                    Person toSearch = persons[indexToSearch];
                    int result = LinearSearchArray.search(toSearch, persons, 0, persons.length);
                    if (indexToSearch != result) {
                        throw new RuntimeException("Linear search failed!");
                    }
                }
                linearSearchDuration = (System.nanoTime() - start) / 1000;
                start = System.nanoTime();
                SimpleSort.insertionSort(persons);
                sortDuration = (System.nanoTime() - start) / 1000;
                searchCounter = SEARCHES_PER_LOOP;
                start = System.nanoTime();
                while (searchCounter-- > 0) {
                    int indexToSearch = ThreadLocalRandom.current().nextInt(persons.length);
                    Person toSearch = persons[indexToSearch];
                    int result = BinSearch.searchRecursively(toSearch, persons, 0, persons.length - 1);
                    if (indexToSearch != result) {
                        throw new RuntimeException("Binary search failed!");
                    }
                }
                binarySearchDuration = (System.nanoTime() - start) / 1000;
                System.out.format("%d,%d,%d,%d%n", persons.length, linearSearchDuration, sortDuration, binarySearchDuration);
            }
            System.out.format("\n\nTotal duration spent in task: %,d ms%n", System.currentTimeMillis() - totalStart);
        } catch (IOException e) {
            System.err.format("Failed to do the task because %s%n", e.getMessage());
        }
    }

    private static void doYourSearch() {
        /*
        try {
            
            Person [] persons = PersonUtils.createPersons(100);            

            // TODO: create a comparator comparing person ages.
            // Simplest way to do that is to use Comparator.comparingInt...
            

            // Then sort using that comparator...
            SimpleSort.insertionSort(persons, comparator);

            // Then print out the first 20 persons
            for (int index = 0; index < Math.min(20, persons.length); index++) {
                System.out.println(persons[index]);
            }

            // Try different ages if no one is found
            int ageToFind = 42;

            // Person toSearch = WHAT SHOULD BE HERE...?
            
            // First try to find with linear search:
            int index = LinearSearchArray.search(toSearch, persons, 0, persons.length, comparator);
            if (index >= 0) {
                System.out.format("First person found at %d with linear search is %s%n", index, persons[index]);
            } else {
                System.out.format("Unfortunately there is no one with age %d in data%n", ageToFind);
            }
                
            // Then find with binary search:
            index = BinSearch.searchRecursively(toSearch, persons, 0, persons.length - 1, comparator);
            if (index >= 0) {
                System.out.format("First person found at %d with binary search is %s%n", index, persons[index]);
            } else {
                System.out.format("Unfortunately there is no one with age %d in data%n", ageToFind);
            }

        } catch (IOException e) {
            System.err.format("Failed to do the task because %s%n", e.getMessage());
        }
        */
    }


}
