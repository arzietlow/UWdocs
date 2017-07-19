///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             SimpleQueue.java
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
 * and removed from the front. Implements a chain of listnodes.
 */
public class SimpleQueue<E> implements QueueADT<E> {

	private Listnode<E> qEnd;   //pointer to the last node of the queue
	private Listnode<E> qFront; //pointer to the last node of the queue
	private int numItems;       //number of listnodes in the queue

	/**
	 * Constructs a new SimpleQueue object with initial conditions
	 */
	public SimpleQueue() {
		this.qEnd = new Listnode<E>(null);
		this.qFront = new Listnode<E>(null);
		this.numItems = 0;
		//throw new RuntimeException("SimpleQueue constructor not implemented");
	}

	/**
	 * Adds an item to the rear of the queue.
	 * @param item the item to add to the queue.
	 * @throws IllegalArgumentException if item is null.
	 */
	public void enqueue(E item) {
		if (item == null) throw new IllegalArgumentException();
		Listnode<E> toAdd = new Listnode<E>(item);
		if (numItems == 0) {
			qFront = toAdd;
			qEnd = toAdd;
		}
		else {
			toAdd.setPrev(qEnd);
			qEnd.setNext(toAdd);
			qEnd = toAdd;
		}
		numItems++;
	}

	/**
	 * Removes an item from the front of the Queue and returns it.
	 * @return the front item in the queue.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public E dequeue() {
		if (numItems == 0) throw new EmptyQueueException();
		E result = qFront.getData();
		qFront = qFront.getNext();
		numItems--;
		return result;
	}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * @return the front item in the queue.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public E peek() {
		if (numItems == 0) throw new EmptyQueueException();
		return qFront.getData();
	}

	/**
	 * Returns true iff the Queue is empty.
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty() {
		return numItems <= 0;
	}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear() {
		while (qFront.getNext() != null) {	//these 4 lines are not necessary
			qFront = qFront.getNext();		//would remove in later version
			numItems--;
		}  
		qFront = null;
		qEnd = qFront;
		numItems = 0;
	}

	/**
	 * Returns the number of items in the Queue.
	 * @return the size of the queue.
	 */
	public int size() {
		return numItems;
	}
}
