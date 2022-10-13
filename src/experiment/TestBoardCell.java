/*
 * CSCI 306 Section B
 * C13A-2 Clue Paths 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/10/2022
 */
package experiment;

import java.util.HashSet;
import java.util.Set;

//Class TestBoardCell (in experiment package)- represents one cell in your grid
public class TestBoardCell {

	//Two int variables to hold row and column 
	private int row;
	private int col;
	//hold the cells room status.
	private Boolean isRoom; 
	//hold the cells occupied status.
	private Boolean isOccupied ;
	//A Set of board cells to hold adjacency list
	private Set<TestBoardCell> adjList;

	//A constructor that has passed into it the row and column for that cell.

	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		isOccupied = false;
		isRoom  = false;
		adjList = new HashSet<TestBoardCell>();
	}

	//returns the adjacency list for the cell
	public Set<TestBoardCell> getAdjList() {
		return adjList;  	
	}


	//calculating the adjacency list in the board since the important data is the grid, 
	//and then telling the cell what its adjacencies are
	public void addAdj(TestBoardCell adjCell) {
		adjList.add(adjCell);
	}

	//for indicating a cell is part of a room (void setRoom(boolean) and perhaps boolean isRoom()).	
	public void setIsRoom(boolean isRoom)
	{
		this.isRoom = isRoom;
	}


	public boolean getIsRoom()
	{
		return isRoom;
	}

	//for seting a cell is occupied
	public void setOccupied(boolean isOccupied) {

		this.isOccupied = isOccupied;
	}

	//for indicating a cell is occupied by another player 
	public boolean getOccupied() {

		return isOccupied;
	}

}
