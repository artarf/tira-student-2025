package oy.interact.tira.task_07;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.factories.ListFactory;
import oy.interact.tira.util.LinkedListInterface;

@DisplayName("Testing basic functionality of the list reverse().")
@EnabledIf("checkIfImplemented")
public class ReversedListTests {
	
	static boolean checkIfImplemented() {
		return ListFactory.createStringLinkedList() != null;
	}

	@Test
   // @Timeout(value = 30, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
   @DisplayName("Testing the reversed()")
   void reversedListTest() {
      try {
         List<String> originalListOfGrapes = fillWithTestData("ArrayList");
			LinkedListInterface<String> linkedListToTest = ListFactory.createStringLinkedList();
         long start = System.nanoTime();
         for (String item : originalListOfGrapes) {
            // assertDoesNotThrow(() -> linkedListToTest.addLast(item.trim()), "Could not add item to list");
            linkedListToTest.addLast(item);
         }
         long duration = System.nanoTime() - start;
         System.out.format("Your linked list: adding grapes took\t%10d ns.\n", duration);
         assertEquals(originalListOfGrapes.size(), linkedListToTest.size(), () -> "Elements got lost in translation!");
         // Check that lists are in same order
         for (int index = 0; index < originalListOfGrapes.size(); index++) {
            final String javaItem = originalListOfGrapes.get(index);
            final String yourListItem = linkedListToTest.get(index);
            assertEquals(javaItem, yourListItem, "Elements in Java linked list and yours are not in same order");
         }
         System.out.println("Linked list has " + linkedListToTest.size() + " items.");

			LinkedListInterface<String> reversed = ListFactory.createStringLinkedList();
			assertDoesNotThrow(() -> linkedListToTest.reversedTo(reversed), "reversedTo threw an exception, something wrong in implementation");
         // Testing reversed
			Collections.reverse(originalListOfGrapes);
			// Check that reversed list is in correct order
			assertEquals(originalListOfGrapes.size(), reversed.size(), "Reversed list does not have correct size");
         for (int index = 0; index < reversed.size(); index++) {
            final String javaItem = originalListOfGrapes.get(index);
            final String yourListItem = reversed.get(index);
            assertEquals(javaItem, yourListItem, "Elements in reversed Java linked list and yours are not in same order");
         }
      } catch (IOException e) {
         fail("Cannot execute the test due to " + e.getMessage());
      }   
   }

   /**
    * Utility method to create a list with random test data.
    * 
    * @param itemCount Number of items to put into the testa data list.
    * @return A list of test data to use with the test list.
    * @throws IOException
    */
   private List<String> fillWithTestData(String whichJavaList) throws IOException {
      String toCheck;
      toCheck = new String(getClass().getClassLoader().getResourceAsStream("Grapes.txt").readAllBytes());
      List<String> testData = null;
      if ("ArrayList".equalsIgnoreCase(whichJavaList)) {
         testData = new ArrayList<String>();
      } else if ("LinkedList".equalsIgnoreCase(whichJavaList)) {
         testData = new LinkedList<String>();
      } else {
         throw new IOException("Not supported");
      }
      String[] grapes = toCheck.split("/|\\r?\\n");
      long start = System.nanoTime();
      for (String str : grapes) {
         testData.add(str.trim());
      }
      long duration = System.nanoTime() - start;
      System.out.format("Java %9s : adding grapes took\t%10d ns.\n", whichJavaList, duration);
      return testData;
  }
}
