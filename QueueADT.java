///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             QueueADT.java
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
 * An ordered collection of items, where items are added to the rear
 * and removed from the front.
 * @author CS367
 * 
 */
public interface QueueADT<E> {
    /**
     * Adds an item to the rear of the queue.
     * @param item the item to add to the queue.
     * @throws IllegalArgumentException if item is null.
     */
    void enqueue(E item);

    /**
     * Removes an item from the front of the Queue and returns it.
     * @return the front item in the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    E dequeue();

    /**
     * Returns the item at front of the Queue without removing it.
     * @return the front item in the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    E peek();

    /**
     * Returns true iff the Queue is empty.
     * @return true if queue is empty; otherwise false.
     */
    boolean isEmpty();
    
    /**
     * Removes all items in the queue leaving an empty queue.
     */
    void clear();

    /**
     * Returns the number of items in the Queue.
     * @return the size of the queue.
     */
    int size();
}
