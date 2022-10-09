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
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
	}

	//returns the cell from the board at row, col.
	public TestBoardCell getCell( int row, int col ) {

		if(grid[row][col] == null) {
			TestBoardCell cell = new TestBoardCell(row,col);
			grid[row][col] = cell;
		}		
		return grid[row][col];
	}

	//add the start location to the visited list (so no cycle through this cell)
	public void setStartCellVisited(TestBoardCell startCell) {
		this.visited.add(startCell);
	}

	//calculates legal targets for a move from startCell of length pathlength.
	public void calcTargets( TestBoardCell startCell, int pathlength) 
	{
		startCell.setAdjList(this);		
		Set<TestBoardCell> adjList= startCell.getAdjList();
		for(TestBoardCell adjCell: adjList)
		{
			if (visited.contains(adjCell) ==true) {
				continue;
			}
			visited.add(adjCell);
			if (pathlength == 1) {
				targets.add(adjCell);       		   
			}
			else {

				calcTargets(adjCell,pathlength-1);
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
