package oy.interact.tira.student;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;

import oy.interact.tira.util.NotYetImplementedException;

public class ArrayUtils {

   /**
    * Swaps places of two elements in the array.
    *
    * @param array  The array to swap elements in
    * @param first  The first index to swap values of.
    * @param second The Second index to swap values of.
    * @throws IllegalArgumentException if the indices are invalid or array is null.
    */
   public static <T> void swap(T[] array, int first, int second) throws IllegalArgumentException {
      // Check preconditions:
      if (array == null ||
            first < 0 ||
            second < 0 ||
            first >= array.length ||
            second >= array.length) {
         throw new IllegalArgumentException("Invalid arguments for swapping values");
      }
      if (first == second) { return; }
      T temp = array[first];
      array[first] = array[second];
      array[second] = temp;
   }

   /**
    * Creates a copy of the input array.
    * 
    * @param array The array to copy.
    * @return A new array with identical length and elements than the input array.
    */
   public static <T> T[] copyOf(final T[] array) {
      if (array == null) {
         throw new IllegalArgumentException("Invalid arguments for copying an array");
      }
      @SuppressWarnings("unchecked")
      T[] tempArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
      int index = 0;
      for (final T item : array) {
         tempArray[index++] = item;
      }
      return tempArray;
   }

   public static <T> T[] copyOf(final T[] array, int length) {
      if (array == null || length < 0) {
         throw new IllegalArgumentException("Invalid arguments for copying an array");
      }
      @SuppressWarnings("unchecked")
      T[] tempArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length);
      int targetLength = array.length < length ? array.length : length;
      for (int index = 0; index < targetLength; index++) {
         tempArray[index] = array[index];
      }
      return tempArray;
   }

   /**
    * Finds the last value from the array that conforms to the bipredicate.
    *
    * Array must have at least one element, and then that is returned if no other elements
    * are in the array.
    *
    * If two or more values conform to the predicate, the last of those in the array is returned.
    *
    * @param fromArray The array to search, must not be null and have at least one element.
    * @param usingPredicate The predicate to use in the search, must not be null.
    * @return Last element in the array that fulfills the BiPredicate.
    * @throws IllegalArgumentException if parameters are not valid
    */
   public static <T> T findEdgeValue(T [] fromArray, BiPredicate<T, T> usingPredicate) throws IllegalArgumentException {
      throw new NotYetImplementedException("task_04 ArrayUtils.findEdgeValue() not yet implemented");
   }

   /**
    * Generates an array with specified size and fills it with randomly permutated
    * numbers.
    * 
    * @param size The size of the array.
    * @return Array filled with permutated numbers.
    */
   public static Integer[] generateIntegerArray(int size) {
      // DO NOT touch this method!
      Integer[] array = new Integer[size];
      ThreadLocalRandom tlr = ThreadLocalRandom.current();

      for (int counter = 0; counter < size; counter++) {
         array[counter] = counter + 1;
      }
      for (int counter = 0; counter < size - 1; counter++) {
         int indexToSwitch = tlr.nextInt(size);
         if (indexToSwitch != counter) {
            int value = array[indexToSwitch];
            array[indexToSwitch] = array[counter];
            array[counter] = value;
         }
      }
      return array;
   }

   /**
    * Generates an array of random strings.
    * 
    * @param size The size of the string array.
    * @return The array of random strings.
    */
   public static String[] generateStringArray(int size) {
      String[] array = new String[size];

      final int leftLimit = 97; // letter 'a'
      final int rightLimit = 122; // letter 'z'
      int targetStringLength = 10;
      Random random = new Random();

      for (int counter = 0; counter < size; counter++) {
         // From https://www.baeldung.com/java-random-string
         array[counter] = random.ints(leftLimit, rightLimit + 1)
               .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
               .limit(targetStringLength)
               .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
               .toString();
      }
      return array;
   }

   public static <T> void shuffle(T [] array) {
      for (int index = 0; index < array.length; index++) {
         int shuffleIndex = 42;
         do {
            shuffleIndex = ThreadLocalRandom.current().nextInt(array.length);
         } while (index == shuffleIndex);
         ArrayUtils.swap(array, index, shuffleIndex);
      }
   }

}
