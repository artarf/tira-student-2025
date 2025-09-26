package oy.interact.tira.student;

import oy.interact.tira.util.Pair;

import oy.interact.tira.util.Tree;

public class BinarySearchTree<K extends Comparable<K>, V> implements Tree<K, V> {

	int size;
	int maxDepth = 0;
	int depthSum = 0;

	public BinarySearchTree() {
		// STUDENT TODO finish the implementation of this method
		size = 0;
	}

	@Override
	public V add(K key, V value) throws OutOfMemoryError, IllegalArgumentException {
		// STUDENT TODO finish the implementation of this method
		return null;
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		// STUDENT TODO finish the implementation of this method
		return null;
	}

	@Override
	public int size() {
		// STUDENT TODO finish the implementation of this method
		return 0;
	}

	@Override
	public void clear() {
		// STUDENT TODO finish the implementation of this method
	}

	@Override
	public boolean isEmpty() {
		// STUDENT TODO finish the implementation of this method
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pair<K, V>[] toArray() throws OutOfMemoryError {
		// STUDENT TODO finish the implementation of this method
		return null;
	}

	@Override
	public BSTStats getStats() {
		BSTStats stats = new BSTStats();
		// STUDENT TODO finish the implementation of this method
		stats.minDepth = -1;
		stats.maxDepth = -1;
		stats.size = -1;
		return stats;
	}

	public class BSTStats implements Tree.Stats {
		public int minDepth = Integer.MAX_VALUE;
		public int maxDepth = 0;
		public int size = 0;

		@Override
		public String getStats() {
			return String.format
			(
				"Tree size: %,d%nMin Depth: %d%nMax Depth: %d%n",
				this.size,
				this.minDepth,
				this.maxDepth
			);
		}
	}

}
