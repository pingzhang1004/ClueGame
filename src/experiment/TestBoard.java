package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//TestBoard contains our board
public class TestBoard {

	//A two dimensional array to hold the board cells in a grid
	private TestBoardCell[][] grid;
	//A set of board cells to hold the resulting targets from TargetCalc()
	private Set<TestBoardCell> targets;
	//A set of board cells to hold the visited list
	private Set<TestBoardCell> visited;
	//Constants for the grid size
	final static int COLS = 4;
	final static int ROWS = 4;


	//private Set<TestBoardCell> adjList;

	private int row;
	private int col;

	//A constructor that sets up the board
	public TestBoard() {
		super();		
		grid = new TestBoardCell[ROWS][COLS];
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
	}

	//returns the cell from the board at row, col.
	public TestBoardCell getCell( int row, int col ) {
		
		if(grid[row][col] == null) {
			TestBoardCell cell = new TestBoardCell(row,col);
			grid[row][col] = cell;
		}		
		return grid[row][col] ;
	}


	//What about calculating the adjacency list in the board since the important data is the grid, 
	//and then telling the cell what its adjacencies are
	public void calcAdjacency(TestBoardCell cell){

		cell = grid[row][col];	
	}

	//calculates legal targets for a move from startCell of length pathlength.
	//leave the method blank (if void)
	public void calcTargets( TestBoardCell startCell, int pathlength) 
	{


	}

	//  gets the targets last created by calcTargets()
	//return an empty object (example, an empty map instead of a null) 
	public Set<TestBoardCell> getTargets(){		

		return targets;

	}





}
