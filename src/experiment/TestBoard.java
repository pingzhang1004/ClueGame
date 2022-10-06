package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//TestBoard contains our board
public class TestBoard {
	
	private Set<TestBoardCell> TargetsList;
	private TestBoardCell cell;
	
		
	//A constructor that sets up the board
	public TestBoard() {
		super();
		TargetsList = new HashSet<TestBoardCell>();
		
	}

	//calculates legal targets for a move from startCell of length pathlength.
	//leave the method blank (if void)
	public void calcTargets( TestBoardCell startCell, int pathlength) 
	{
	
	}
	
	//  gets the targets last created by calcTargets()
	//return an empty object (example, an empty map instead of a null) 
	public Set<TestBoardCell> getTargets(){		
		
		return TargetsList;
		
	}
	
	//returns the cell from the board at row, col.
	public TestBoardCell getCell( int row, int col ) {
		
		cell = new TestBoardCell(row, col);
		return  cell;
	}
}
