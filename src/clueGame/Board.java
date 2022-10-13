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
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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

	//A set of board cells to hold the visited list
	//It is used to avoid backtracking.
	private	Set<BoardCell> visited;
	private Set<BoardCell> targets;
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	public Board() {
		super();
		//room = new Room();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */

	// Set all config files
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = layout;
		setupConfigfile = setup;
	}

	public void initialize()
	{	
		try {
			loadSetupConfig();
			loadLayoutConfig();
			calcAdjList();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	
		catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	// End from Canvas

	// Load layout file
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
			loadCell(lines);
		}	
	}
	private void loadCell(ArrayList<String> lines) throws BadConfigFormatException {
		//delacoate the grid size.
		grid = new BoardCell[numRows][numColumns];			
		//populate the data from the layoutConfigFile to board cells
		for(int i =0; i < numRows; i++) {
			for(int j =0; j< numColumns; j++) {

				// Legend Exception----------------------
				char charAtZero = lines.get(i).split(",")[j].charAt(0);
				if (!roomMap.keySet().contains(charAtZero)) {
					throw new BadConfigFormatException("The board layout refers to a room that is not in the setup file!");
				}
				// Legend Exception End+++++++++++++++++++++
				BoardCell cell = new BoardCell(i,j);
				//cell = getCell(i,j);
				if (lines.get(i).split(",")[j].length() == 1) {						
					cell.setInitial(charAtZero);
				}
				else {
					cell.setInitial(charAtZero);
					char charAtOne = lines.get(i).split(",")[j].charAt(1);
					if(charAtOne == '*'){
						cell.setRoomCenter(true);
						getRoom(cell).setCenter(cell);
					}
					else if (charAtOne == '#'){
						cell.setRoomLabel(true)	;
						getRoom(cell).setLabel(cell);
					}
					else if(charAtOne == 'v'){
						cell.setDoordDirection(DoorDirection.DOWN);							
					}
					else if(charAtOne == '^'){
						cell.setDoordDirection(DoorDirection.UP);							
					}
					else if(charAtOne == '>'){
						cell.setDoordDirection(DoorDirection.RIGHT);							
					}
					else if (charAtOne == '<'){
						cell.setDoordDirection(DoorDirection.LEFT);							
					}
					else{
						cell.setSecretPassage(charAtOne);
					}

				}
				grid[i][j] = cell; 		
			}
		}
	}

	// Load setup file
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		//you need to build the grid array with the layout file. But how big do you make it, i.e. number of rows and columns?
		//You almost need to know this information before you even read the file.
		roomMap = new TreeMap();
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
				if (!strSplit[0].equalsIgnoreCase("Room") && !strSplit[0].equalsIgnoreCase("Space")) {
					throw new BadConfigFormatException("An entry in either file does not have the proper format!");
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


	public void calcAdjList() {		
		for(int i =0; i< numRows; i++)
			for(int j =0; j< numColumns; j++) {				
				calcAdj(i, j);	
			}
	}


	//calculating the adjacency list in the board since the important data is the grid, 
	//and then telling the cell what its adjacencies are
	public void calcAdj(int row,int col) {
		BoardCell cell = getCell(row,col);

		if(row -1 >= 0) {
			cell.addAdj(grid[row-1][col]);
		}
		if(row < numRows-1) {
			cell.addAdj(grid[row+1][col]);
		}
		if(col-1 >= 0) {
			cell.addAdj(grid[row][col-1]);
		}
		if(col< numColumns-1) {
			cell.addAdj(grid[row][col+1]);
		}	
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		
		return grid[row][col].getAdjList();  	
	}


	//calculates legal targets for a move from startCell of length pathlength.
	public void calcTargets(BoardCell startCell, int pathlength) 
	{
		//A set of board cells to hold the visited list
		//It is used to avoid backtracking.
		visited = new HashSet<BoardCell>();
		//A set of board cells to hold the resulting targets from TargetCalc()
		targets = new HashSet<BoardCell>() ;

		//add the start location to the visited list (so no cycle through this cell)
		visited.add(startCell);
		//call recursive function
		findAllTargets(startCell, pathlength); 
	}

	//recursive function to find all the targets
	public void findAllTargets(BoardCell startCell, int pathlength ) 
	{
		Set<BoardCell> adjList = startCell.getAdjList();
		for(BoardCell adjCell: adjList)
		{
			if (visited.contains(adjCell) == true ||adjCell.getOccupied()==true) {
				continue;
			}
			visited.add(adjCell);
			if (pathlength == 1 || adjCell.getIsRoom()==true) {
				targets.add(adjCell);       		   
			}
			else {
				findAllTargets(adjCell,pathlength-1);
			}
			visited.remove(adjCell);    
		}
	}


	//return the targets
	public Set<BoardCell> getTargets() {
		
		return	targets;
	}

	//returns the cell from the board at row, col.
	public BoardCell getCell( int row, int col ) {				
		return grid[row][col];
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

	// return 
	public Map<Character, Room> getRoomMap() {

		return roomMap;
	}


}
