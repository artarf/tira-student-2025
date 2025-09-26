package oy.interact.tira.factories;

import oy.interact.tira.util.QueueInterface;

public class ArrayQueueFactory {

	private ArrayQueueFactory() {
		// empty
	}

	public static QueueInterface<Integer> createIntegerQueue() {
		return null;
		// return new ArrayQueue<>();
	}

	public static QueueInterface<Integer> createIntegerQueue(int capacity) {
		return null;
		// return new ArrayQueue<>(capacity);
	}

	public static QueueInterface<String> createStringQueue() {
		return null;
		// return new ArrayQueue<>();
	}

	public static QueueInterface<String> createStringQueue(int capacity) {
		return null;
		// return new ArrayQueue<>(capacity);
	}

}
