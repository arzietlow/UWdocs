///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             StackADT.java
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
 * and removed from the top.
 * @author CS367
 */
public interface StackADT<E> {
    /**
     * Adds item to the top of the stack.
     * @param item the item to add to the stack.
     * @throws IllegalArgumentException if item is null.
     */
    void push(E item);

    /**
     * Removes the item on the top of the Stack and returns it.
     * @return the top item in the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    E pop();

    /**
     * Returns the item on top of the Stack without removing it.
     * @return the top item in the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    E peek();

    /**
     * Returns true iff the Stack is empty.
     * @return true if stack is empty; otherwise false.
     */
    boolean isEmpty();

    /**
     * Removes all items on the stack leaving an empty Stack. 
     */
    void clear();

    /**
     * Returns the number of items in the Stack.
     * @return the size of the stack.
     */
    int size();
}
