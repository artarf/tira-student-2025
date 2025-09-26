package oy.interact.tira.factories;

import oy.interact.tira.util.StackInterface;

public class StackFactory {
	private StackFactory() {
		// Empty
	}

	public static StackInterface<String> createStringStack() {
		return null;
		// return new StackImplementation<>();
	}

	public static StackInterface<Integer> createIntegerStack() {
		return null;
		// return new StackImplementation<>();
	}

	public static StackInterface<Integer> createIntegerStack(int capacity) {
		return null;
		// return new StackImplementation<>(capacity);
	}

	public static StackInterface<Character> createCharacterStack() {
		return null;
		// return new StackImplementation<>();
	}

	public static StackInterface<Character> createCharacterStack(int capacity) {
		return null;
		// return new StackImplementation<>(capacity);
	}

}
