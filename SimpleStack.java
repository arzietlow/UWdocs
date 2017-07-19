///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             SimpleStack.java
// Semester:         CS 367 Fall 2015
//
// Author:           Andrew Zietlow arzietlow@wisc.edu
// CS Login:         azietlow
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//
// Pair Partner:     N/A
//
// External Help:   None
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * An ordered collection of items, where items are both added
 * and removed from the top. Implements a chain of listnodes.
 * @author CS367
 */
public class SimpleStack<E> implements StackADT<E> {

	private Listnode<E> topNode; //Front node representing the top of the stack
	private int numItems;		 //Number of listnodes in the stack

	/**
	 * Constructs a new SimpleStack object with initial conditions.
	 */
	public SimpleStack() {
		this.topNode = new Listnode<E>(null);
		this.numItems = 0;
	}

	/**
	 * Adds item to the top of the stack.
	 * @param item the item to add to the stack.
	 * @throws IllegalArgumentException if item is null.
	 */
	public void push(E item) {
		if (item == null) throw new IllegalArgumentException();
		Listnode<E> toAdd = new Listnode<E>(item);
		if (numItems == 0) {
			topNode = toAdd;
		}
		else {
			topNode.setPrev(toAdd);
			toAdd.setNext(topNode);
			topNode = toAdd;
		}
		numItems++;
	}

	/**
	 * Removes the item on the top of the Stack and returns it.
	 * @return the top item in the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public E pop() {
		if (numItems <= 0) throw new EmptyStackException();
		E result = topNode.getData();
		if (numItems == 1) topNode = new Listnode<E>(null);
		else {
			topNode = topNode.getNext();
			topNode.setPrev(null);
		}
		numItems--;
		return result;
	}

	/**
	 * Returns the item on top of the Stack without removing it.
	 * @return the top item in the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public E peek() {
		if (numItems <= 0) throw new EmptyStackException();
		return topNode.getData();
	}

	/**
	 * Returns true iff the Stack is empty.
	 * @return true if stack is empty; otherwise false.
	 */
	public boolean isEmpty() {
		return numItems < 1;
	}

	/**
	 * Removes all items on the stack leaving an empty Stack. 
	 */
	public void clear() {
		topNode = null;
		numItems = 0;
	}

	/**
	 * Returns the number of items in the Stack.
	 * @return the size of the stack.
	 */
	public int size() {
		return numItems;
	}
}
