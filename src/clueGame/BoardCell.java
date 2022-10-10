package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {

	private int row;
	private int col;
	private char initial;
	private DoorDirection doordDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell() {
		
	}
	
	public BoardCell(int row, int col) {
		super();
//		this.row = row;
//		this.col = col;
//		isOccupied = false;
//		isRoom  = false;
//		adjList = new HashSet<TestBoardCell>();
	}
	public void addAdj(BoardCell adj) {
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}
	
	public boolean isLabel() {
		return true;
	}
	public boolean isRoomCenter() {
		return true;
	}
	
	public char getSecretPassage() {
		return ' ';
	}
}
