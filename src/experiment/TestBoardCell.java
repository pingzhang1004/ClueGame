package experiment;

import java.util.HashSet;
import java.util.Set;

//Class TestBoardCell (in experiment package)- represents one cell in your grid
public class TestBoardCell {

	private Set<TestBoardCell> adjacencyList;
	
    //A constructor that has passed into it the row and column for that cell.
	public TestBoardCell(int row, int column) {
		super();
 	   adjacencyList = new HashSet<TestBoardCell>();
	}
	
    //A setter to add a cell to this cells adjacency list
	public void addAdjacency( TestBoardCell cell ) {
	}
	
    //returns the adjacency list for the cell
	public Set<TestBoardCell> getAdjList(){	
		return adjacencyList;		
	}
	
    //for setting a cell is part of a room 
	public void setIsRoom(boolean isRoom)
	{   
	}
	
	//for indicating a cell is part of a room 
	public boolean getIsRoom() {
	     return true;
	}	
	
	//for setting a cell is occupied
	public void setOccupied(boolean isOccupied) {
	}
	
    //for indicating a cell is occupied by another player 
	public boolean getOccupied() {
		return true;
	}
	
}
