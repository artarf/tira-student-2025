package oy.interact.tira.util;

/**
 * An Tree is a data structure containing key-value -pairs,
 * ordered by the key in natural order.
 * 
 * It is possible to add these pairs to the data structure, and then
 * get values by the key.
 */
public interface Tree<K extends Comparable<K>, V> {

   /**
    * Adds or updates a value for a key in the tree.
    *
    * Note that if a key already exists in the tree, the old value is
    * replaced with the parameter value and the old value in the tree is
    * returned to the caller. 
    * 
    * The tree must not allow duplicate keys.
    * 
    * If a value is updated, the old value is returned. If key did not
    * exist in the tree, add() returns null.
    *
    * @param key The key to use in adding elements. Must not be null.
    * @param value The associated value for the key. Must not be null;
    * @return Old value of the key if it was updated, or null if key-value pair was added.
    * @throws IllegalArgumentException Throws IllegalArgumentException if key or value is null.
    * @throws OutOfMemoryError Throws OutOfMemoryError if memory runs out.
    */
   V add(K key, V value) throws IllegalArgumentException, OutOfMemoryError;

   /**
    * Gets the value for the given key or returns null if key 
    * was not found in the tree.
    *
    * @param key The key to search.
    * @return Returns the found value for the key or null if not found.
    * @throws IllegalArgumentException Throws if key to search is null.
    */
   V get(K key) throws IllegalArgumentException;

   /**
    * Returns the number of elements in the tree.
    * @return The number of elements in the collection.
    */
   int size();

   /**
    * For checking if the tree is empty.
    *
    * @return Returns true if tree is empty, otherwise returns false.
    */
   boolean isEmpty();

   /**
    * Empties the tree. After calling clear(), isEmpty() returns true,
    * size() returns 0, and toArray() returns an empty array of size 0.
    */
   void clear();

   /**
    * Returns the contents of the tree in a new array. If the 
    * tree is empty, returns an empty array of length 0.
    *
    * The contents of the tree's internal data structure are not to be
    * changed in any way.
    *
    * The array must contain only valid elements (not nulls, for example).
    * The length of the returned array equals the size of the tree.
    *
    * Note: since the key-value object references in the returned array are referring
    * to the same objects in the tree's internal data structure, changing
    * an element's key or value in the returned array also changes the element in the tree.
    * This may lead to issues, if the key is changed outside the tree, 
    * and the position of the pair in the tree, determined from the key, 
    * is no longer is valid in the internal data structure in the tree. 
    * Therefore, code handling the returned array should not change the keys,
    * unless the tree is erased or discarded after calling `toArray()`.
    *
    * For example, the key determines the position of the Pair in the tree. 
    * If key is changed, the position of the pair is no longer valid. 
    * 
    * @return The key-value pairs of the tree in an array, or an empty array.
    * @throws OutOfMemoryError If there is not enough memory for the array.
    */
   Pair<K,V> [] toArray() throws OutOfMemoryError;

   // An interface for the getStats to use.
   public interface Stats {
      String getStats();
   }

   /**
    * Get statistics about the tree. 
    * 
    * The method is used to analyse the effectiveness of the
    * data structure implementation.
    *
    * With Binary Search Tree implementation,
    * returned value must contain information about the tree
    * depths (min and max tree branch depths).
    *
    * @return Statistics about the data structure state.
    */
   Stats getStats();
}
