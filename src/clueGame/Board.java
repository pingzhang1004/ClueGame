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
		loadSetupConfig();
		loadLayoutConfig();
	}
	// End from Canvas

	// Load setup file
	public void loadLayoutConfig() {	
		FileReader reader = null;
		Scanner in = null;
		String line;
		String strSplit[];
		try {
			//read the layoutConfigFile first time to get the numColumns and numRows;		
			reader = new FileReader("data/" + layoutConfigFile);
			in = new Scanner(reader);
			ArrayList<String> lines = new ArrayList<String>();
			while (in.hasNextLine()) {
				lines.add(in.nextLine());
			}
			numRows = lines.size();		
			numColumns = lines.get(0).split(",").length;

			//delacoate the grid size.
			grid = new BoardCell[numRows][numColumns];			

			//grid = new BoardCell[numRows][numColumns];
			//populate the data from the layoutConfigFile to board cells
			for(int i =0; i < numRows; i++) {
				for(int j =0; j< numColumns; i++) {
					cell = new BoardCell(i,j);
					if (lines.get(i).split(",")[j].length() == 1) {						
						cell.setInitial(lines.get(i).split(",")[j].charAt(0));
					}
					else {
						cell.setInitial(lines.get(i).split(",")[j].charAt(0));
						if(lines.get(i).split(",")[j].charAt(1) == '*'){
							cell.setRoomCenter(true);							
						}
						else if (lines.get(i).split(",")[j].charAt(1) == '#'){
							cell.setRoomLabel(true)	;					
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
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	
	}

	// Load layout file
	public void loadSetupConfig() {
		//you need to build the grid array with the layout file. But how big do you make it, i.e. number of rows and columns?
		//You almost need to know this information before you even read the file.
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader("data/"+ setupConfigfile);
			in = new Scanner(reader);
			String line;
			String[] strSplit; 
			while (in.hasNextLine()) {
				line = in.nextLine();
				if(line.startsWith("//")){
					continue;
				}				
				else{					
					strSplit  = line.split(", ");
					room.setName(strSplit[1]);				
					String roomName = room.getName();
					char roomLabel =strSplit[2].charAt(0);
					roomMap.put(roomLabel, new Room(roomName));				
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	
	}

	// Set all config files
	public void setConfigFiles(String layout, String setup) {

		theInstance.layoutConfigFile = layout;
		theInstance.setupConfigfile = setup;

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
