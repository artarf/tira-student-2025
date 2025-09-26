package oy.interact.tira.factories;

import oy.interact.tira.util.Dictionary;

public class HashTableFactory {
	private HashTableFactory() {
		// empty
	}

	public static <K, V> Dictionary<K,V> createHashTable() {
		return null;
		// return new HashTableDictionary<>();
	}

	public static <K, V> Dictionary<K, V> createHashTableWithCapacity(int capacity) {
		return null;
		// return new HashTableDictionary<>(capacity);
	}

}
