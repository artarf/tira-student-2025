package oy.interact.tira.util;

import java.util.NoSuchElementException;

/**
 * An interface of a linked list data structure.
 * 
 * indexOf(E) and remove(E) use the elements' equals to find the
 * element in question.
 * 
 * Note that the elements put in the list may also be null.
 * 
 */
public interface LinkedListInterface<E> {
	
	/**
	 * Addds the specified element as the first in the list.
	 * @param element The element to add.
	 */
	void addFirst(E element);

	/**
	 * Addds the specified element as the last in the list.
	 * @param element The element to add.
	 */
	void addLast(E element);

	/**
	 * Adds the specified element in the specified index in the list.
	 * 
	 * Allows adding after the current last element. So if list has 10 elements,
	 * valid indices are 0...9, calling add(10, item) is valid, adding as the
	 * last element of the list.
	 * 
	 * @param index The index where to add the element.
	 * @param element The element to add.
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	void add(int index, E element) throws IndexOutOfBoundsException;

	/**
	 * Gets the first element of the list.
	 * @return The first element.
	 * @throws NoSuchElementException if the list is empty.
	 */
	E getFirst() throws NoSuchElementException;

	/**
	 * Gets the last element of the list.
	 * @return The last element.
	 * @throws NoSuchElementException if the list is empty.
	 */
	E getLast() throws NoSuchElementException;

	/**
	 * Gets the element at the specified index in the list.
	 * @return The element in that index.
	 * @throws IndexOutOfBoundsException If the index is invalid.
	 */
	E get(int index) throws IndexOutOfBoundsException;

	/**
	 * Gets the index of the first equal element in the list.
	 * 
	 * @return The element in that index, -1 if element is not found.
	 */
	int indexOf(E element);

	/**
	 * Removes and returns the first element of the list.
	 * @return The first element.
	 * @throws NoSuchElementException if the list is empty.
	 */
	E removeFirst() throws NoSuchElementException;

	/**
	 * Removes and returns the last element of the list.
	 * @return The last element.
	 * @throws NoSuchElementException if the list is empty.
	 */
	E removeLast() throws NoSuchElementException;

	/**
	 * Finds, removes and returns the specified element in the list.
	 * 
	 * If the list contains several elements of equal value, the first
	 * one is removed.
	 * 
	 * The element to search and remove may also be null.
	 * 
	 * @return True if the element was found and removed, false otherwise.
	 * @throws NoSuchElementException if the list is empty.
	 */
	boolean remove(E element);

	/**
	 * Removes an element by index, returning the removed element.
	 * 
	 * @param index The index of the element to remove.
	 * @return The element in the index.
	 * @throws IndexOutOfBoundsException If the index is invalid.
	 */
	E remove(int index);

	/**
	 * The number of elements in the list.
	 * @return The number of elements in the list.
	 */
	int size();

	/**
	 * Clears the contents of the list to empty.
	 */
	void clear();

	/**
	 * Reverses the order of elements in this linked list.
	 * 
	 * Implementation MUST NOT use a temporary array, list or
	 * other structure to do the reverse, but MUST do it 
	 * in-place, just reversing the links between nodes, maintaining
	 * the first and last references.
	 */
	void reverse();

	/**
	 * Places the elements from this linked list to the one
	 * in the parameter, in reversed order.
	 * 
	 * The algorithm clears the target list before adding to it.
	 * 
	 * Note that if the *elements* are modified in either of the
	 * list, both lists see the changes. This is because in Java
	 * objects are references. Adding an element from one list to another
	 * means that both lists refer to the same object.
	 * 
	 * @param target The linked list to place elements to.
	 */
	void reversedTo(LinkedListInterface<E> target);
}
