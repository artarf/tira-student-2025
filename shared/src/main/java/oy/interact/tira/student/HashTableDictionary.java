package oy.interact.tira.student;

import oy.interact.tira.util.Pair;
import oy.interact.tira.util.Dictionary;

public class HashTableDictionary<K, V> implements Dictionary<K,V> {


	// STUDENT TODO: remember to clear the counters below in
	// - clear()
	// - just before reallocation happens
	// - maintain the values when adding to the hash table!
	// - the values are used in getStats and printed out by some tests!

	/** How many times adding collided in the _first_ index that was tried? */
	private int addCollided = 0;
	/** How many times collisions happended _including_ when probing? */
	private int totalCollisionCount = 0;
	/** What was the max number of probing steps add had to go through before free slot was found? */
	private int maxProbingSteps = 0;

	public HashTableDictionary() {
		// STUDENT TODO: implement initialization of hashtable correctly
		// DO NOT use too large a size for the default size. Values between 8-32 are ok.
	}

	@SuppressWarnings("unchecked")
	public HashTableDictionary(int capacity) {
		// STUDENT TODO: implement initialization of hashtable correctly
	}
 
	@Override
	public V add(K key, V value) throws OutOfMemoryError, IllegalArgumentException {
		// STUDENT TODO: implement adding to hashtable correctly
		return null;
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		// STUDENT TODO: implement getting from a hashtable correctly
		return null;
	}

	@Override
	public int size() {
		// STUDENT TODO: implement this correctly
		return 0;
	}

	@Override
	public void clear() {
		// STUDENT TODO: implement this correctly
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pair<K,V> [] toArray() throws OutOfMemoryError {
		// STUDENT TODO: implement this correctly
		return null;
	}

	@Override
	public boolean isEmpty() {
		// STUDENT TODO: implement this correctly
		return false;
	}

	@Override
	public void compress() throws OutOfMemoryError {
		// STUDENT TODO: implement this correctly
	}

	@Override
	public int capacity() {
		// STUDENT TODO: implement this correctly
		return 0;
	}

	// STUDENT: No need to touch this
	public class HashTableStats implements Dictionary.Stats {
		public int arrayLength = 0;
		public double fillFactor = 0; // in percents
		public int addCollisions = 0;
		public int totalCollisions = 0;
		public int maxProbingSteps = 0;
		public int size = 0;

		@Override
		public String getStats() {
			return String.format
			(
				"Array length: %d%nElements: %,d%nFill factor: %.2f%nAdd collisions: %d%nTotal collisions: %d%nMax Probing Steps: %d%n",
				this.arrayLength,
				this.size,
				this.fillFactor,
				this.addCollisions,
				this.totalCollisions,
				this.maxProbingSteps
			);
		}
	}

	@Override
	public Stats getStats() {
		HashTableStats stats = new HashTableStats();
		// STUDENT TODO: make sure you calculate, collect and maintain these values
		// in your implementation correctly! Set the values of the 
		// stats from the hashtable member variables and the stats values you maintain:
		stats.arrayLength = -1;
		stats.size = -1;
		stats.fillFactor = -1;
		stats.addCollisions = addCollided;
		stats.totalCollisions = totalCollisionCount;
		stats.maxProbingSteps = maxProbingSteps;
		return stats;
	}
	
}
