///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             Operation.java
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
 * Represents a single user's (single) change to be made to a Document object
 *
 * <p>Bugs: none known
 *
 * @author azietlow
 */

public class Operation {

	// Enumeration of operator type.
	public enum OP {
		SET,   //set,row,col,const -> set [row,col] to const
		CLEAR, //clear,row,col -> set [row,col] to 0
		ADD,   //add,row,col,const -> add [row,col] by const
		SUB,   //sub,row,col,const -> sub [row,col] by const
		MUL,   //mul,row,col,const -> mul [row,col] by const
		DIV,   //div,row,col,const -> div [row,col] by const
		UNDO,  //undo the last operation
		REDO   //redo the last undo
	}

	private OP     op;       //represents which operator was chosen by the user
	private int    rowIndex; //indices of Doc's 2d array at which to operate
	private int    colIndex;     //row and col must be non-negative
	
	private int    constant; //the key value to operate on an index with
						     //used by all operations except undo/redo/clear
	
	private long   timestamp;//represents the time an operation was made at
	private String docName;  //name of the document being operated on
	                             //cannot be null or empty 
	
	private String userId;   //name of the user to whom this operation belongs
							     //cannot be null or empty

	/**
	 * Constructs an Operation that performs a change at the given index. 
	 * What this change entails is determined by the op and constant fields.
	 */
	public Operation(String docName, String userId, OP op, int rowIndex, int
			colIndex, int constant, long timestamp) {

		this.docName   = docName;
		this.userId    = userId;
		this.op        = op;
		this.rowIndex  = rowIndex;
		this.colIndex  = colIndex;
		this.constant  = constant;
		this.timestamp = timestamp;
		if (!isValid(docName, userId, op, rowIndex, colIndex)) 
			throw new IllegalArgumentException();
	}

	/**
	 * Constructs an Operation object used for the "clear" op argument
	 */
	public Operation(String docName, String userId, OP op, int rowIndex, int
			colIndex, long timestamp) {

		this.docName   = docName;
		this.userId    = userId;
		this.op        = op;
		this.rowIndex  = rowIndex;
		this.colIndex  = colIndex;
		this.timestamp = timestamp;
		this.constant  = -1; //field is not used by this operation type
		if (!isValid(docName, userId, op, rowIndex, colIndex)) 
			throw new IllegalArgumentException();

	}
	
	/**
	 * Constructs an Operation object to be used for undo or redo op arguments
	 */
	public Operation(String docName, String userId, OP op, long timestamp) {

		this.docName   = docName;
		this.userId    = userId;
		this.op        = op;
		this.timestamp = timestamp;
		this.rowIndex  = -1; //field is not used by this operation type
		this.colIndex  = -1; //field is not used by this operation type
		this.constant  = -1; //field is not used by this operation type
		if (!isValid(docName, userId, op, 1, 1)) 
			throw new IllegalArgumentException();

	}

	/**
	 * Accessor method for Operation object's docName field
	 * @return this Operation's document being worked on
	 */
	public String getDocName() {
		return this.docName;
	}

	/**
	 * Accessor method for Operation object's userId field
	 * @return the username of whoever is utilizing this Operation
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Accessor method for Operation object's op field
	 * @return the "flavor" of this Operation object. What change it causes.
	 */
	public OP getOp() {
		return this.op;
	}

	/**
	 * Accessor method for Operation object's rowIndex field
	 * @return the 1st index of a Doc's 2d array representing location of change
	 * -1 if this Operation does not involve an index
	 */
	public int getRowIndex() {
		return this.rowIndex;
	}

	/**
	 * Accessor method for Operation object's colIndex field
	 * @return the 2nd index of a Doc's 2d array representing location of change
	 * -1 if this Operation does not involve an index
	 */
	public int getColIndex() {
		return this.colIndex;
	}


	/**
	 * Accessor method for Operation object's timestamp field
	 * @return the whole-number representation of the time this op was made
	 */
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Accessor method for Operation object's constant field
	 * @return the key value that would be used by certain ops of this Operation
	 * -1 if this Operation does not involve a constant
	 */
	public int getConstant() {
		return this.constant;
	}

	/**
	 * Uses this Operation's op field to determine how it will be represented
	 * in output to the written file.
	 * 
	 * @return (result) the formatted string representing this Operation's 
	 * effect on a Document object
	 */
	public String toString() {
	
		String result = null;
		switch(this.op) {
		//<timestamp>\t<document name>\t<user>\t<op>\t[<Row Index>,<Col Index>]
		// + \t<Constant>
		case ADD:
		case SUB:
		case MUL:
		case DIV:
		case SET:
			result = timestamp + "\t" + docName + "\t" + userId + "\t" + 
					getOpName() + "\t" + "[" + rowIndex + "," + colIndex + "]" 
					+ "\t" + constant;
			break;

		//<timestamp>\t<document name>\t<user>\t<op>
		case UNDO:
		case REDO:
			result = timestamp + "\t" + docName + "\t" + userId + "\t" + 
					getOpName();
			break;

		//<timestamp>\t<document name>\t<user>\t<op>\t[<Row Index>,<Col Index>]
		case CLEAR:
			result = timestamp + "\t" + docName + "\t" + userId + "\t" + 
					"clear" + "\t" + "[" + rowIndex + "," + colIndex + "]";
			break;

		}
		return result;
	}

	/**
	 * Translates the op enum value of this Operation into its string 
	 * representation. Used for outputting correct toString.
	 * 
	 * @return the op name in string form
	 */
	private String getOpName() {
		switch(op) {
		case ADD: return "add";
		case SUB: return "sub";
		case MUL: return "mul";
		case DIV: return "div";
		case SET: return "set";
		case UNDO: return "undo";
		case REDO: return "redo";
		default: return null;
		}
	}

	/**
	 * Error-checking method for use by the constructors of Operation objects.
	 *
	 * @param (doc)  the docName field of the Operation being checked
	 * @param (user) the UserId field of the Operation being checked
	 * @param (op)   the op field of the Operation being checked
	 * @param (row, col) the row and col fields of the Operation being checked
	 * @return true if Operation has valid arguments, false otherwise
	 */
	private boolean isValid(String doc, String user, OP op, int row, int col) {
		if ((doc == null) || (doc.length() == 0)) return false;
		if ((user == null) || (user.length() == 0)) return false;
		if (op == null) return false;
		if ((row < 0) || (col < 0)) return false;
		return true;
	}
}
