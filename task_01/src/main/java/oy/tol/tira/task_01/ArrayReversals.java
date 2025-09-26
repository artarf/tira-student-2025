package oy.tol.tira.task_01;

class ArrayReversals {

	/**
	 * Returns a new array where the elements from input array are in reversed order.
	 * 
	 * When executed, the elements in the returned array are the same, but
	 * in reverse order. Original array is not modified.
	 * 
	 * The array parameter:
	 * - must not be null
	 * - must have at least two elements
	 * - the elements in the array must be non-null.
	 * 
	 * @param input Array to reverse.
	 * @throws IllegalArgumentException If the array is not according to the specs.
	 * @return Returns a new array where the elements of the input array are in reversed order.
	 */	
	static Integer [] reversed(Integer [] input) {
		Integer [] output = new Integer[input.length];
		int index = 0;
		int addIndex = input.length - 1;
		for (/*empty*/; index < input.length - 1; index++) {
			output[index] = Integer.valueOf(input[index]);
		}
		return output;
	}

	static Integer [] copyOf(Integer [] array) {
		Integer [] newArray = new Integer[array.length];
		int addIndex = 0;
		for (Integer value : array) {
			newArray[addIndex++] = value;
		}
		return newArray;
	}

}
