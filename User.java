///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             User.java
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
 * Represents a user on the server and what changes they have made to the 
 * documents they are members of
 *
 * <p>Bugs: none known
 *
 * @author azietlow
 */

public class User {

	private String userId;         //name of this user
	private SimpleStack<WAL> redo; //list of changes user can redo
	private SimpleStack<WAL> undo; //list of changes user can undo

	/**
	 * Constructs a new User object using passed string as their name. 
	 * Initializes stacks to be empty.
	 * @param (userId) the new User's name
	 * @throws IllegalArgumentException if name is null or empty
	 */
	public User(String userId) {
		if ((userId == null) || (userId.length() == 0)) 
				throw new IllegalArgumentException();
		this.userId = userId;
		this.redo = new SimpleStack<WAL>();
		this.undo = new SimpleStack<WAL>();
	}

	/**
	 * Removes one undo WAL from User's undo stack
	 * @return the top WAL in the User's undo stack, null if stack is empty
	 */
	public WAL popWALForUndo() {
		if (!undo.isEmpty()) return undo.pop();
		return null;
	}

	/**
	 * Removes one undo WAL from User's redo stack
	 * @return the top WAL in the User's redo stack, null if stack is empty
	 */
	public WAL popWALForRedo() {
		if (!redo.isEmpty()) return redo.pop();
		return null;
	}

	/**
	 * Adds one WAL to User's undo stack
	 * @param (trans) the WAL to push onto the stack
	 * @throws IllegalArgumentException if trans is null
	 */
	public void pushWALForUndo(WAL trans) {
		if (trans == null) throw new IllegalArgumentException();
		undo.push(trans);
	}

	/**
	 * Adds one WAL to User's redo stack
	 * @param (trans) the WAL to push onto the stack
	 * @throws IllegalArgumentException if trans is null
	 */
	public void pushWALForRedo(WAL trans) {
		if (trans == null) throw new IllegalArgumentException();
		redo.push(trans);
	}

	/**
	 * Resets the User's redo stack to its initial conditions
	 */
	public void clearAllRedoWAL() {
		redo.clear();
	}

	/**
	 * Resets the User's undo stack to its initial conditions
	 */
	public void clearAllUndoWAL() {
		undo.clear();
	}

	/**
	 * Accessor method for a User's userId field
	 * @return this User's name
	 */
	public String getUserId() {
		return this.userId;
	}
}
