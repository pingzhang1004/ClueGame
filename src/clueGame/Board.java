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
	
	//private Map<Character, Set<BoardCell>> doorMap;

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
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	
		catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		roomEnter();
		calcAdjList();
		
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
		
		// Columns Exception------------------------
		int sum = 0;
		for (int i=0; i < numRows; i++) {
			sum = sum + lines.get(i).split(",").length;
		}
		if (sum / numRows != numColumns) {
			throw new BadConfigFormatException("The board layout file does not have the same number of columns in every row!");
		}
		else {
			loadCell(lines);
		}	
	}
	
	// Load all cells into the game board
	private void loadCell(ArrayList<String> lines) throws BadConfigFormatException {
		//allocate the grid size.
		grid = new BoardCell[numRows][numColumns];			
		//populate the data from the layoutConfigFile to board cells
		for(int i =0; i < numRows; i++) {
			for(int j =0; j< numColumns; j++) {

				// Legend Exception
				char charAtZero = lines.get(i).split(",")[j].charAt(0);
				if (!roomMap.keySet().contains(charAtZero)) {
					throw new BadConfigFormatException("The board layout refers to a room that is not in the setup file!");
				}
				
				BoardCell cell = new BoardCell(i,j);
				if (lines.get(i).split(",")[j].length() == 1) {						
					cell.setInitial(charAtZero);
				}
				else {
					cell.setInitial(charAtZero);
					char charAtOne = lines.get(i).split(",")[j].charAt(1);
					if(charAtOne == '*'){
						cell.setIsRoomCenter(true);
						getRoom(cell).setCenter(cell);
						roomMap.get(charAtZero);
					}
					else if (charAtOne == '#'){
						cell.setIsRoomLabel(true);
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
						cell.setIsSecretPassage(true);
					    
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
				// Entry Exception
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
    
	// Calculate adjacency list for all cells
	public void calcAdjList() {	
		for(int i =0; i< numRows; i++) {
			for(int j =0; j< numColumns; j++) {				
				calcAdj(i, j);
			}
		}
	}
	
	// Get door and secret passage for all rooms
    public void roomEnter() {
    	for(int i =0; i< numRows; i++) {
			for(int j =0; j< numColumns; j++) {	
				
				BoardCell cell = grid[i][j];
				DoorDirection doorDiret = cell.getDoorDirection();
				char roomLabel = cell.getInitial();
				
				if (cell.isDoorway()) {
					if(doorDiret == DoorDirection.DOWN)
					{
						roomLabel =  grid[i+1][j].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
					}
					else if(doorDiret == DoorDirection.UP)
					{
						roomLabel =  grid[i-1][j].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
					}
					else if(doorDiret == DoorDirection.LEFT) {
						roomLabel =  grid[i][j-1].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
					}
					else if(doorDiret == DoorDirection.RIGHT) {
						roomLabel =  grid[i][j+1].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
					}
				}
				
				if(cell.isSecretPassage()) {
					roomMap.get(roomLabel).setDoorList(cell);
				}
			}
		}
    	
	}

	//calculating the adjacency list in the board since the important data is the grid, 
	//and then telling the cell what its adjacencies are
	public void calcAdj(int row,int col) {
		BoardCell cell = getCell(row,col);
		BoardCell roomCenterCell;
		char roomLabel = cell.getInitial();

		// Cell is a center
		if(cell.isRoomCenter()) {
			for (BoardCell c : roomMap.get(roomLabel).getDoorList()) {
				if (c.isSecretPassage()) {
					cell.addAdj(roomMap.get(c.getSecretPassage()).getCenterCell());
				}
				else {
					cell.addAdj(c);
				}
			}
		}
		else {
			// Cell is a door way
			if(cell.isDoorway()== true) {
				DoorDirection doorDiret = cell.getDoorDirection();
				if(doorDiret == DoorDirection.DOWN)
				{
					roomLabel =  grid[row+1][col].getInitial();
					roomCenterCell = roomMap.get(roomLabel).getCenterCell();
					cell.addAdj(roomCenterCell);

					if (row-1 >= 0 && grid[row-1][col].getInitial() == 'W' && !grid[row-1][col].getOccupied() && !grid[row-1][col].getIsRoom()) {
						cell.addAdj(grid[row-1][col]);
					}				
					if(col-1 >= 0 && grid[row][col-1].getInitial() == 'W' && !grid[row][col-1].getOccupied() && !grid[row][col-1].getIsRoom()) {
						cell.addAdj(grid[row][col-1]);
					}
					if(col< numColumns-1 && grid[row][col+1].getInitial() == 'W' && !grid[row][col+1].getOccupied() && !grid[row][col+1].getIsRoom()) {
						cell.addAdj(grid[row][col+1]);
					}					
				}
				if(doorDiret == DoorDirection.UP)
				{
					roomLabel =  grid[row-1][col].getInitial();
					roomCenterCell = roomMap.get(roomLabel).getCenterCell();
					cell.addAdj(roomCenterCell);

					if(row < numRows-1 && grid[row+1][col].getInitial() == 'W' && !grid[row+1][col].getOccupied() && !grid[row+1][col].getIsRoom()) {
						cell.addAdj(grid[row+1][col]);
					}				
					if(col-1 >= 0 && grid[row][col-1].getInitial() == 'W' && !grid[row][col-1].getOccupied() && !grid[row][col-1].getIsRoom()) {
						cell.addAdj(grid[row][col-1]);
					}
					if(col< numColumns-1 && grid[row][col+1].getInitial() == 'W' && !grid[row][col+1].getOccupied() && !grid[row][col+1].getIsRoom()) {
						cell.addAdj(grid[row][col+1]);
					}	
				}
				if(doorDiret == DoorDirection.LEFT) {
					roomLabel =  grid[row][col-1].getInitial();
					roomCenterCell = roomMap.get(roomLabel).getCenterCell();
					cell.addAdj(roomCenterCell);

					if (row -1 >= 0 && grid[row-1][col].getInitial() == 'W' && !grid[row-1][col].getOccupied() && !grid[row-1][col].getIsRoom()) {
						cell.addAdj(grid[row-1][col]);
					}
					if(row < numRows-1 && grid[row+1][col].getInitial() == 'W' && !grid[row+1][col].getOccupied() && !grid[row+1][col].getIsRoom()) {
						cell.addAdj(grid[row+1][col]);
					}				
					if(col< numColumns-1 && grid[row][col+1].getInitial() == 'W' && !grid[row][col+1].getOccupied() && !grid[row][col+1].getIsRoom()) {
						cell.addAdj(grid[row][col+1]);
					}			
				}
				if(doorDiret == DoorDirection.RIGHT) {
					roomLabel =  grid[row][col+1].getInitial();
					roomCenterCell = roomMap.get(roomLabel).getCenterCell();
					cell.addAdj(roomCenterCell);				

					if (row -1 >= 0 && grid[row-1][col].getInitial() == 'W' && !grid[row-1][col].getOccupied() && !grid[row-1][col].getIsRoom()) {
						cell.addAdj(grid[row-1][col]);
					}
					if(row < numRows-1 && grid[row+1][col].getInitial() == 'W' && !grid[row+1][col].getOccupied() && !grid[row+1][col].getIsRoom()) {
						cell.addAdj(grid[row+1][col]);
					}				
					if(col-1 >= 0 && grid[row][col-1].getInitial() == 'W' && !grid[row][col-1].getOccupied() && !grid[row][col-1].getIsRoom()) {
						cell.addAdj(grid[row][col-1]);
					}
				}			
			}
			
			// Cell is walkway
			else {
				if(row-1 >= 0 && grid[row-1][col].getInitial() == 'W' && !grid[row-1][col].getOccupied() && !grid[row-1][col].getIsRoom()) {
					cell.addAdj(grid[row-1][col]);
				}
				if(row < numRows-1 && grid[row+1][col].getInitial() == 'W' && !grid[row+1][col].getOccupied() && !grid[row+1][col].getIsRoom()) {
					cell.addAdj(grid[row+1][col]);
				}
				if(col-1 >= 0 && grid[row][col-1].getInitial() == 'W' && !grid[row][col-1].getOccupied() && !grid[row][col-1].getIsRoom()) {
					cell.addAdj(grid[row][col-1]);
				}
				if(col< numColumns-1 && grid[row][col+1].getInitial() == 'W' && !grid[row][col+1].getOccupied() && !grid[row][col+1].getIsRoom()) {
					cell.addAdj(grid[row][col+1]);
				}	
			}
		}
	}

	// return adjacency list for a cell
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
		//call the recursive function for targets
		findAllTargets(startCell, pathlength); 
	}

	//recursive function to find all the targets
	public void findAllTargets(BoardCell startCell, int pathlength ) 
	{
		Set<BoardCell> adjList = startCell.getAdjList();
		for(BoardCell adjCell: adjList)
		{
			if (visited.contains(adjCell) == true || adjCell.getOccupied()==true) {
				continue;
			}
			visited.add(adjCell);

			if (pathlength == 1 || adjCell.isRoomCenter()) {
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
	public BoardCell getCell(int row, int col) {				
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
