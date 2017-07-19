///////////////////////////////////////////////////////////////////////////////
//                   
// Title:            UWdocs (Server.java)
// Files:            Server.java, Database.java, Document.java, User.java,
//					 StackADT.java, Listnode.java, SimpleStack.java,
//					 EmptyQueueException.java, WAL.java, QueueADT.java,
//					 EmptyStackException.java, SimpleQueue.java, Operation.java
//
// Semester:         CS 367 - Fall 2015
//
// Author:           Andrew Zietlow
// Email:            arzietlow@wisc.edu
// CS Login:         azietlow
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the server on which all the documents are hosted. Handles the
 * operations of each user on each of those documents and updates accordingly.
 *
 * <p>Bugs: none known
 *
 * @author azietlow
 */

public class Server {

	private Database database;  //the collection of documents on this server
	private SimpleQueue<Operation> opQueue;       //line Ops. to perform
	private String inputFileName, outputFileName; //names of files being used 

	/**
	 * Constructs a new server object, only one needed, that will be used to
	 * store parsed input and create new output.
	 */
	public Server(String inputFileName, String outputFileName) {

		this.database = new Database();
		this.opQueue = new SimpleQueue<Operation>();
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;

	}

	/**
	 * Encompasses parsing input and then formatting output. The entireity of
	 * the program.
	 */
	public void run(){
		initialize();
		process();
	}

	/**
	 * Parses the input file for Document objects and then for Operation objects
	 */
	public void initialize() {
		
		File inputFile = new File(inputFileName);
		Scanner scnr = null;
		try {
			scnr = new Scanner(inputFile);
		}
		catch (FileNotFoundException e) {

		}
		
		String docLine; //the current line of the input file being read for doc
		final int USER_START_INDEX = 3; //index of docline where users begin
		String docName; //the name of the doc being read from the input file
		int docRows;    //the number of rows the doc being read will have
		int docCols;    //the number of cols the doc being read will have

		//initializing database with documents at top of input file
		//if document has duplicate users listed, passes an invalid document
		if (scnr.hasNextInt()) {
			int numDocs = scnr.nextInt();
			scnr.nextLine();
			for (int i = 0; i < numDocs; i++) {
				boolean hasDupes = false;
				List<String> userIdList = new ArrayList<String>();
				List<User> docUsers = new ArrayList<User>();
				docLine = scnr.nextLine();
				String[] tolkens = docLine.split(",");
				docName = tolkens[0];
				docRows = Integer.parseInt(tolkens[1]);
				docCols = Integer.parseInt(tolkens[2]);
				for (int j = USER_START_INDEX; j < tolkens.length; j++) {
					for (int t = 0; t < userIdList.size(); t++) {
						if (userIdList.contains(tolkens[j])) {
							hasDupes = true;
						}
					}
					userIdList.add(tolkens[j]);
					User newUser = new User(tolkens[j]);
					docUsers.add(newUser);
				}
				if (hasDupes) docUsers = null;
				Document toAdd = new Document(docName, docRows, docCols,
						docUsers);
				database.addDocument(toAdd);
			}
		}
		//moving on to list of operations in input file (below document lines)

		//Initializes the operation objects to a queue 
		while (scnr.hasNextLine()) {
			//creates a new Operation object with 7/6/4 arguments
			String currLine = scnr.nextLine();
			String[] args = currLine.split(",");
			int timestamp = Integer.parseInt(args[0]);
			String userId = args[1];
			docName = args[2];
			String op = args[3];
			int col = 0;
			int row = 0;
			int constant = 0;
			Operation.OP oper = null;
			switch (op) {

			case "set": 
				oper = Operation.OP.SET;
				break;

			case "clear": 
				oper = Operation.OP.CLEAR;
				break;

			case "add": 
				oper = Operation.OP.ADD;
				break;

			case "sub": 
				oper = Operation.OP.SUB;
				break;

			case "mul": 
				oper = Operation.OP.MUL;
				break;

			case "div": 
				oper = Operation.OP.DIV;
				break;

			case "undo": 
				oper = Operation.OP.UNDO;
				break;

			case "redo": 
				oper = Operation.OP.REDO;
				break;

			default : oper = null;	
			}
			if (args.length > 4) {
				row = Integer.parseInt(args[4]);
				col = Integer.parseInt(args[5]);
				if (args.length > 6) {
					constant = Integer.parseInt(args[6]);
				}
			}
			Operation toAdd = null;
			if (args.length == 4) {
				toAdd = new Operation(docName, userId, oper, timestamp);
			}
			else if (args.length == 6) {
				toAdd = new Operation(docName, userId, oper, row, col,
						timestamp);
			}
			else if (args.length == 7) {
				toAdd = new Operation(docName, userId, oper, row, col, constant,
						timestamp);
			}
			if (toAdd != null) opQueue.enqueue(toAdd);
		}
		scnr.close();
	}

	/**
	 * Processes the operations created from initialize() until there are none
	 * left to process. Processes each operation one by one. 
	 * Writes result to a file.
	 */
	public void process() {

		PrintWriter writer;
		try {
			writer = new PrintWriter(outputFileName);
			while (!opQueue.isEmpty()) {
				Operation temp = opQueue.dequeue();
				//Document tempDoc = database.getDocumentByDocumentName(temp.getDocName());
				writer.println("----------Update Database----------");
				//print header to output file
				writer.println(temp.toString());
				writer.println();
				//print operation description (operation toString())
				writer.println(database.update(temp));
				//print document toString
			}
			writer.close();
		} catch (FileNotFoundException e) {
			//do nothing
		}
	}

	/**
	 * Runs the server using the input/output filenames as runtime arguments
	 */
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Usage: java Server [input.txt] [output.txt]");
			System.exit(0);
		}
		Server server = new Server(args[0], args[1]);
		server.run();
	}
}
