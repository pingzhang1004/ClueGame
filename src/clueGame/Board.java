/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package clueGame;

import java.util.Map;
import java.util.TreeMap;

import experiment.TestBoardCell;

public class Board {
	
	// Variables
	private TestBoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigfile;
	private Map<Character, Room> roomMap = new TreeMap();
	private Room room = new Room();
	private BoardCell cell;
	
	// Start from Canvas
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize()
	{
	}
	// End from Canvas
	
	// Load setup file
	public void loadSetupConfig() {	
	}
	
	// Load layout file
	public void loadLayoutConfig() {
	}
	
	// Set all config files
	public void setConfigFiles(String layout, String setup) {	
	}
	
	// return a room object by passing a char
	public Room getRoom(char letter) {
		return room;
	}
	
	// return a room object by passing a BoardCell
	public Room getRoom(BoardCell cell) {
		return room;
	}
	
	// return the number of rows
	public int getNumRows() {	
		return 0;
	}
	
	// return the number of columns
	public int getNumColumns() {	
		return 0;
	}

	//returns the cell from the board at row, col.
	public BoardCell getCell( int row, int col ) {
		cell = new BoardCell(row, col);
		return  cell;
	}
	
	// return 
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}
}
