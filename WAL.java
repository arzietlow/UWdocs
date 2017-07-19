///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             WAL.java
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
 * Represents a single change in the Database made by executing an Operation
 *
 * <p>Bugs: WAL does not specify document on which it is relevant
 *
 * @author azietlow
 */

public class WAL {

	private int rowIndex; //indices where the operation took place
	private int colIndex;     //must be non-negative
	private int oldValue; //the value at those indices before operation

	/**
	 * Constructs a new WAL with the given parameters
	 * @param (rowIndex, colIndex) the indices of the change
	 * @param (oldValue) the previous value
	 * @throws IllegalArgumentException if any index is negative.
	 * @return (description of the return value)
	 */
	public WAL(int rowIndex, int colIndex, int oldValue) {

		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.oldValue = oldValue;
		if ((rowIndex < 0) || (colIndex < 0)) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Accessor for WAL's oldValue field
	 * @return this WAL's oldValue value
	 */
	public int getOldValue() {
		return this.oldValue;
	}

	/**
	 * Accessor for WAL's rowIndex field
	 * @return this WAL's rowIndex value
	 */
	public int getRowIndex() {
		return this.rowIndex;
	}

	/**
	 * Accessor for WAL's colIndex field
	 * @return this WAL's colIndex value
	 */
	public int getColIndex() {
		return this.colIndex;
	}

}
