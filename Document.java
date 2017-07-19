///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             Document.java
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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data contained within one document on the server so that
 * it can be accessed and modified appropriately. 
 *
 * <p>Bugs: none known
 *
 * @author azietlow
 */

public class Document {

	private String docName;       //the name of this document
	private int rowSize, colSize; //the dimensions of this document's 2d array
	private List<User> userList;  //the list of Users who belong to this Doc
	private int[][] table;        //the table of data this document represents

	/**
	 * Constructs an empty Document object and checks that its parameters 
	 * are valid
	 */
	public Document(String docName, int rowSize, int colSize, List<User>
	userList) {

		if ((docName == null) || (docName.length() == 0)) {
			throw new IllegalArgumentException();
		}
		if ((rowSize < 0) || (colSize < 0)) {
			throw new IllegalArgumentException();
		}
		if ((userList == null) || (userList.size() == 0)) {
			throw new IllegalArgumentException();
		}
		this.docName  = docName;
		this.rowSize  = rowSize;
		this.colSize  = colSize;
		this.userList = userList;
		this.table = new int[rowSize][colSize];

	}

	/**
	 * Accessor method for some internal data of a Document's userList field.
	 * For each User belonging to this document, it collects their name and 
	 * adds it to a list. Not utilized by program's implementation.
	 * 
	 * @return (userIds) the list of usernames for this Document
	 */
	public List<String> getAllUserIds() {
		List<String> userIds = new ArrayList<String>();
		for(User o : this.userList) {
			userIds.add(o.getUserId());
		}
		return userIds;
	}

	/**
	 * Performs a user's operation on the document's table's data and records
	 * the change in a WAL object that is then stored in the user's appropriate
	 * WAL stack. 
	 *
	 * @param (operation) Operation object being passed in that has all the 
	 * required information to know what change to make to the table data
	 */
	public void update(Operation operation) {
		if (operation == null) throw new IllegalArgumentException();

		boolean validUser = false;
		for (int t = 0; t < this.userList.size(); t++) {
			if (userList.get(t).getUserId().equals(operation.getUserId())) 
				validUser = true;
		}
		if (!validUser) throw new IllegalArgumentException();

		User user = getUserByUserId(operation.getUserId());
		if (user == null) throw new IllegalArgumentException();
		int rowIndex;//the row of this operation
		int colIndex;//the col of this operation
		int data;    //value in row,col before op

		if ((operation.getOp() != Operation.OP.UNDO) && (operation.getOp() != 
				Operation.OP.REDO)) {
			user.clearAllRedoWAL();
			rowIndex = operation.getRowIndex();
			colIndex = operation.getColIndex(); 
			if ((rowIndex >= rowSize) || (colIndex >= colSize)) {
				throw new IllegalArgumentException();
			}
			data = table[rowIndex][colIndex];   
			user.pushWALForUndo(new WAL(rowIndex, colIndex, data));

			//handling the simpler non-redo/undo cases--
			switch (operation.getOp()) {
			case CLEAR:
				table[rowIndex][colIndex] = 0;
				break;

			case MUL:
				table[rowIndex][colIndex] = data * operation.getConstant();
				break;

			case DIV:
				if (operation.getConstant() == 0) throw new 
				IllegalArgumentException();
				table[rowIndex][colIndex] = data / operation.getConstant();
				break;

			case SUB:
				table[rowIndex][colIndex] = data - operation.getConstant();
				break;

			case ADD:
				table[rowIndex][colIndex] = data + operation.getConstant();
				break;

			case SET:
				table[rowIndex][colIndex] = operation.getConstant();
				break;

			default:
				break;
			}
		}

		//push or pop WAL according to operation case
		//create and store a new WAL using pre-operation values
		//perform change on document's values
		if (operation.getOp() == Operation.OP.UNDO) {
			WAL toDo = user.popWALForUndo();
			if (toDo == null) throw new IllegalArgumentException();
			rowIndex = toDo.getRowIndex();
			colIndex = toDo.getColIndex();
			data = table[rowIndex][colIndex];
			user.pushWALForRedo(new WAL(rowIndex, colIndex, data));
			int oldValue = toDo.getOldValue();
			table[rowIndex][colIndex] = oldValue;
		}
		if (operation.getOp() == Operation.OP.REDO) {
			WAL toDo = user.popWALForRedo();
			if (toDo == null) throw new IllegalArgumentException();
			rowIndex = toDo.getRowIndex();
			colIndex = toDo.getColIndex();
			data = table[rowIndex][colIndex];
			user.pushWALForUndo(new WAL(rowIndex, colIndex, data));
			int oldValue = toDo.getOldValue();
			table[rowIndex][colIndex] = oldValue;
		}
	}

	/**
	 * Accessor method for Document's docName field
	 * @return this Document's name
	 */
	public String getDocName() {
		return this.docName;
	}

	/**
	 * Searches through a Document's list of Users to find if any of them have
	 * a name that matches the passed string
	 *
	 * @param (userId) the username to check for
	 * @return the User object that has a matching name. Null if not found.
	 */
	public User getUserByUserId(String userId) {
		for (User o: this.userList) {
			if (o.getUserId().equals(userId)) return o;
		}
		return null;
	}

	/**
	 * Looks within the table array at a certain index and returns the value
	 * at that index. Not utilized by program's implementation.
	 *
	 * @param (rowIndex, colIndex) where to search in the table 2d array
	 * @return the value contained within the table field at the given position
	 */
	public int getCellValue(int rowIndex, int colIndex){
		if ((rowIndex < 0) || (rowIndex > rowSize - 1)) 
			throw new IllegalArgumentException();
		if ((colIndex < 0) || (colIndex > colSize - 1)) 
			throw new IllegalArgumentException();
		return table[rowIndex][colIndex];
	}

	/**
	 * Processes the document into a single string that is
	 * formatted for writing to output. Steps through each element in table to
	 * build.
	 *
	 * @return (tableString) the Document's string representation
	 */
	public String toString() {
		String tableString = "Document Name: " + docName + "\t" + "Size: " + 
				"[" + rowSize + "," + colSize + "]" + "\n" + "Table:" + "\n";
		for (int j = 0; j < rowSize; j++) {
			for (int i = 0; i < colSize; i++) {
				tableString += table[j][i];
				tableString += "\t";
			}
			tableString += "\n";
		}
		return tableString;
	}
}
