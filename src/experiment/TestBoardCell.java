package experiment;

import java.util.Set;

//Class TestBoardCell (in experiment package)- represents one cell in your grid
public class TestBoardCell {
	
	private int row;
	private int column;
	private Set<TestBoardCell> adjacencyList;
	
    //A constructor that has passed into it the row and column for that cell.
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
    //A setter to add a cell to this cells adjacency list
	public void addAdjacency( TestBoardCell cell ) {
	
	}
	
    //returns the adjacency list for the cell
	public Set<TestBoardCell> getAdjList(){
		
		return null;		
	}
	
    //for indicating a cell is part of a room (void setRoom(boolean) and perhaps boolean isRoom()).	
	public boolean isRoom()
	{
	     return true;
	}
	
	//for seting a cell is occupied
	public void setOccupied(boolean occupied) {
		
	}
	
    //for indicating a cell is occupied by another player 
	public boolean getOccupied() {
		
		return true;
	}
	
}
