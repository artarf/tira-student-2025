package oy.interact.tira.util;

/**
 * A Dictionary is a data structure containing key-value -pairs.
 * It is possible to add these pairs to the data structure, and then
 * find values by the key.
 */
public interface Dictionary<K, V> {

   /**
    * Adds or updates a value for a key in the dictionary.
    *
    * Note that if a key already exists in the dictionary, the old value is
    * replaced with the parameter value and the old value in the Dictionary is
    * returned to the caller. 
    * 
    * The dictionary must not allow duplicate keys.
    * 
    * If a value is updated, the old value is returned. If key did not
    * exist in the Dictionary, add() returns null.
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
    * was not found in the Dictionary.
    *
    * @param key The key to search.
    * @return Returns the found value for the key or null if not found.
    * @throws IllegalArgumentException Throws if key to search is null.
    */
   V get(K key) throws IllegalArgumentException;

   /**
    * Returns the number of elements in the Dictionary.
    * @return The number of elements in the collection.
    */
   int size();

   /**
    * For checking if the dictionary is empty.
    *
    * @return Returns true if dictionary is empty, otherwise returns false.
    */
   boolean isEmpty();

   /**
    * Empties the Dictionary. After calling clear(), isEmpty() returns true,
    * size() returns 0, and toArray() returns an empty array of size 0.
    */
   void clear();

   /**
    * Returns the contents of the dictionary in a new array. If the 
    * dictionary is empty, returns an empty array of length 0.
    *
    * The contents of the Dictionary's internal data structure are not to be
    * changed in any way.
    *
    * The array must contain only valid elements (not nulls, for example).
    * The length of the returned array equals the size of the dictionary.
    *
    * Note: since the key-value object references in the returned array are referring
    * to the same objects in the Dictionary's internal data structure, changing
    * an element's key or value in the returned array also changes the element in the Dictionary.
    * This may lead to issues, if the key is changed outside the Dictionary, 
    * and the position of the pair in the dictionary, determined from the key, 
    * is no longer is valid in the internal data structure in the Dictionary. 
    * Therefore, code handling the returned array should not change the keys,
    * unless the dictionary is erased or discarded after calling `toArray()`.
    *
    * For example, if Dictionary is a Binary Search Tree, the key determines the
    * position of the Pair in the tree. If key is changed, the position is no longer
    * valid. Similarily, if the Dictionary is a hash table, and the key is changed, 
    * the pair should be moved to a new position in the hash table, recalculating the hash
    * of the changed key.
    * 
    * @return The key-value pairs of the Dictionary in an array, or an empty array.
    * @throws OutOfMemoryError If there is not enough memory for the array.
    */
   Pair<K,V> [] toArray() throws OutOfMemoryError;

   /**
    * Compresses the internal array so that it uses an optimal amout of 
    * memory. What that means is dependent on the implementation of this
    * interface.
    *
    * `compress()` may run out of memory since it needs temporary array
    * to do the compression.
    *
    * @throws OutOfMemoryError if compressing fails due to memory running out.
    */
   void compress() throws OutOfMemoryError;

   /** 
    * Returns the total capacity the Dictionary currently has.
    *
    * Total capacity means the number of elements the dictionary
    * currently could hold without reallocation of the internal data
    * structure.
    *
    * @return The capacity (max count of elements) of the dictionary.
    */
   int capacity();

   /**
    * An interface for getting statistics of the data structure.
    */
   public interface Stats {
      String getStats();
   }

   /**
    * Get statistics about the Dictionary. 
    * 
    * The method is used to analyse the effectiveness of the
    * data structure implementation.
    *
    * For example, with Binary Search Tree implementation,
    * returned value contains information about the tree
    * depth (min, max and average tree branch depths).
    * With hash table, returned value contains information about
    * the fill factor and e.g. number of collisions happened when
    * hashing elements into the internal array.
    *
    * @return Statistics about the data structure state.
    */
   Stats getStats();
}
