///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Server.java
// File:             Database.java
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
 * Stores the server's collection of document objects. 
 * Responsible for propagating updates to individual documents.
 *
 * <p>Bugs: none known
 *
 * @author azietlow
 */

public class Database {
	
	private List<Document> docList; //The database's list of Documents

	/**
	 * Constructs a new empty database
	 */
	public Database() {
		this.docList = new ArrayList<Document>();
	}

	/**
	 * Adds a Document object to the database
	 * @param (doc) the Document to add to the database's list
	 * @throws IllegalArgumentException if doc is null.
	 */
	public void addDocument(Document doc) {
		if (doc == null) throw new IllegalArgumentException();
		docList.add(doc);
	}

	/**
	 * Accessor method for the Database's docList field
	 * @return this Database's docList
	 */
	public List<Document> getDocumentList() {
		return this.docList;
	}

	/**
	 * Takes an Operation and performs it on the Document that is specified 
	 * within that Operation. Updates the database with this new change.
	 *
	 * @param (operation) the operation to perform
	 * @throws IllegalArgumentException if operation or doc is null.
	 * @return the representation of the newly-updated document
	 */
	public String update(Operation operation) {
		if (operation == null) throw new IllegalArgumentException();
		Document doc = getDocumentByDocumentName(operation.getDocName());
		if (doc == null) throw new IllegalArgumentException();
		doc.update(operation);
		return doc.toString();
	}

	/**
	 * Finds a Document within the list of Documents that has the same name
	 * as the keyword being passed in.
	 *
	 * @param (docName) the name being searched for 
	 * @return the matching Document if found, null if not found
	 */
	public Document getDocumentByDocumentName(String docName) {
		for (int i = 0; i < docList.size(); i++) {
			if (docList.get(i).getDocName().equals(docName)) {
				return docList.get(i);
			}
		}
		return null;
	}
	
}
