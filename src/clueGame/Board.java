/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import experiment.TestBoardCell;

public class Board {

	// Variables
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigfile;
	private Map<Character, Room> roomMap;
	private Room room;
	private BoardCell cell;

	// Start from Canvas
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
		roomMap = new TreeMap();
		room = new Room();
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
		try {
		    loadSetupConfig();
		    loadLayoutConfig();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	
		catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	// End from Canvas

	// Load setup file
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {	
		FileReader reader = null;
		Scanner in = null;
		String line;
		String strSplit[];
		
		//read the layoutConfigFile first time to get the numColumns and numRows;		
		reader = new FileReader("data/" + layoutConfigFile);
		in = new Scanner(reader);
		ArrayList<String> lines = new ArrayList<String>();
		while (in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		numRows = lines.size();		
		numColumns = lines.get(0).split(",").length;
		System.out.println(numRows);
		System.out.println(numColumns);
		// Columns Exception------------------------
		int sum = 0;
		for (int i=0; i < numRows; i++) {
			sum = sum + lines.get(i).split(",").length;
		}

		if (sum / numRows != numColumns) {
			throw new BadConfigFormatException("The board layout file does not have the same number of columns in every row!");
		}
		// Columns Exception End++++++++++++++++++++++

		else {
			//delacoate the grid size.
			grid = new BoardCell[numRows][numColumns];			
			//populate the data from the layoutConfigFile to board cells
			for(int i =0; i < numRows; i++) {
				for(int j =0; j< numColumns; j++) {
					
					// Legend Exception----------------------
					if (!roomMap.keySet().contains(lines.get(i).split(",")[j].charAt(0))) {
				    	throw new BadConfigFormatException("The board layout refers to a room that is not in the setup file!");
				    }
					// Legend Exception End+++++++++++++++++++++
					cell = new BoardCell(i,j);
					//cell = getCell(i,j);
					if (lines.get(i).split(",")[j].length() == 1) {						
						cell.setInitial(lines.get(i).split(",")[j].charAt(0));
					}
					else {
						cell.setInitial(lines.get(i).split(",")[j].charAt(0));
						if(lines.get(i).split(",")[j].charAt(1) == '*'){
							cell.setRoomCenter(true);
							getRoom(cell).setCenter(cell);
						}
						else if (lines.get(i).split(",")[j].charAt(1) == '#'){
							cell.setRoomLabel(true)	;
							getRoom(cell).setLabel(cell);
						}
						else if(lines.get(i).split(",")[j].charAt(1) == 'v'){
							cell.setDoordDirection(DoorDirection.DOWN);							
						}
						else if(lines.get(i).split(",")[j].charAt(1) == '^'){
							cell.setDoordDirection(DoorDirection.UP);							
						}
						else if(lines.get(i).split(",")[j].charAt(1) == '>'){
							cell.setDoordDirection(DoorDirection.RIGHT);							
						}
						else if (lines.get(i).split(",")[j].charAt(1) == '<'){
							cell.setDoordDirection(DoorDirection.LEFT);							
						}
						else{
							cell.setSecretPassage(lines.get(i).split(",")[j].charAt(1));
						}

					}
					grid[i][j] = cell; 		
				}
			}
		}

	
	}

	// Load layout file
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		//you need to build the grid array with the layout file. But how big do you make it, i.e. number of rows and columns?
		//You almost need to know this information before you even read the file.
		FileReader reader = null;
		Scanner in = null;
		
		reader = new FileReader("data/"+ setupConfigfile);
		in = new Scanner(reader);
		String line;
		String[] strSplit; 
		while (in.hasNextLine()) {
			line = in.nextLine();
			if(!line.startsWith("//")){
				
				strSplit  = line.split(", ");
				System.out.println(strSplit[0]);
				if (!strSplit[0].equalsIgnoreCase("Room") && !strSplit[0].equalsIgnoreCase("Space")) {
					System.out.println("False");
					//throw new BadConfigFormatException("An entry in either file does not have the proper format!");
				}
				else {
					Room room = new Room();
					room.setName(strSplit[1]);	
					char roomLabel =strSplit[2].charAt(0);
					roomMap.put(roomLabel, room);
				}
			}
		}

	}

	// Set all config files
	public void setConfigFiles(String layout, String setup) {

		layoutConfigFile = layout;
		setupConfigfile = setup;

	}

	// return a room object by passing a char
	public Room getRoom(char letter) {
		
		return roomMap.get(letter);
	}

	// return a room object by passing a BoardCell
	public Room getRoom(BoardCell cell) {
		
		return roomMap.get(cell.getInitial());
	}

	// return the number of rows
	public int getNumRows() {	
		return numRows;
	}

	// return the number of columns
	public int getNumColumns() {	
		return numColumns;
	}

	//returns the cell from the board at row, col.
	public BoardCell getCell( int row, int col ) {
		return  grid[row] [col];
	}

	// return 
	public Map<Character, Room> getRoomMap() {

		return roomMap;
	}
//	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
//		Board board = Board.getInstance();
//		board.setConfigFiles("ClueLayout.csv", "ClueSetup306.txt");
//		
//		//board.initialize();
//		board.loadSetupConfig();
//		//board.loadLayoutConfig();
//		//System.out.println(board.getNumRows());
//		//System.out.println(board.getNumColumns());
//		System.out.println(board.getRoomMap().size());
//		System.out.println(board.getRoomMap().keySet());
//	}
}
