/*
 * CSCI 306 Section B
 * C13A-2 Clue Paths 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/10/2022
 */

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
	//It is used to avoid backtracking.
	private Set<TestBoardCell> visited;
	//Constants for the grid size
	final static int COLS = 4;
	final static int ROWS = 4;

	//A constructor that sets up the board
	public TestBoard() {
		super();		
		grid = new TestBoardCell[ROWS][COLS];


		for(int i =0; i< ROWS; i++)
			for(int j =0; j< COLS; j++) {
				TestBoardCell cell = new TestBoardCell(i,j);
				grid[i][j] = cell;
			}

		for(int i =0; i< ROWS; i++)
			for(int j =0; j< COLS; j++) {				
				CalcAdjList(i, j);				
				//grid[i][j].get;				
			}

	}

	
	//calculating the adjacency list in the board since the important data is the grid, 
	//and then telling the cell what its adjacencies are
	public void CalcAdjList(int row, int col) {
		TestBoardCell cell = getCell(row,col);
		
		if(row -1 >= 0) {
			cell.addAdj(grid[row-1][col]);
		}
		if(row < ROWS-1) {
			cell.addAdj(grid[row+1][col]);
		}
		if(col-1 >= 0) {
			cell.addAdj(grid[row][col-1]);
		}
		if(col< COLS-1) {
			cell.addAdj(grid[row][col+1]);
		}		
	}


	//returns the cell from the board at row, col.
	public TestBoardCell getCell( int row, int col ) {				
		return grid[row][col];
	}

	//calculates legal targets for a move from startCell of length pathlength.
	public void calcTargets(TestBoardCell startCell, int pathlength) 
	{
		//A set of board cells to hold the visited list
		//It is used to avoid backtracking.
		visited = new HashSet<TestBoardCell>();
		//A set of board cells to hold the resulting targets from TargetCalc()
		targets = new HashSet<TestBoardCell>() ;

		//add the start location to the visited list (so no cycle through this cell)
		visited.add(startCell);
		//call recursive function to find all the targets
		findAllTargets(startCell, pathlength); 
	}

	//recursive function to find all the targets
	public void findAllTargets(TestBoardCell startCell, int pathlength ) 
	{
		Set<TestBoardCell> adjList = startCell.getAdjList();
		for(TestBoardCell adjCell: adjList)
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

	//  gets the targets last created by calcTargets()
	//return an empty object (example, an empty map instead of a null) 
	public Set<TestBoardCell> getTargets(){		

		return targets;

	}
}
