package clueGame;

import java.util.Map;

import experiment.TestBoardCell;

public class Board {
	private TestBoardCell[][] grid;

	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigfile;
	private Map<Character, Room> roomMap;
	private Room room = new Room();
	private BoardCell cell;
	
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
	
	public void loadSetupConfig() {	
	}
	
	public void loadLayoutConfig() {
	}
	
	public void setConfigFiles(String layout, String setup) {
		
	}
	
	public Room getRoom(char letter) {
		return room;
	}
	public Room getRoom(BoardCell cell) {
		return room;
	}
	
	public int getNumRows() {	
		return 0;
	}
	public int getNumColumns() {	
		return 0;
	}

	//returns the cell from the board at row, col.
	public BoardCell getCell( int row, int col ) {
		cell = new BoardCell(row, col);
		return  cell;
	}
}
