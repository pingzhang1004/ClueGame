package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class Room {

	// Variables
	private String name;
	//private char label;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Set<BoardCell> doorList;
	
	
	// assumed not to be a room (to account for spaces)
		//private boolean Room = false;
		private boolean hasSecretPassage = false;
		private BoardCell secretPassage;
	
	// default constructor
	public Room() {
		doorList = new HashSet<BoardCell>();
	}

	// return a room name
	public String getName() {
		return name;
	}

	// Set room name
	public void setName(String name) {
		this.name = name;
	}

	// Set label cell
	public void setLabel(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	// Set center cell
	public void setCenter(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	// return a label cell
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	// return a center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	// Add cell into door list
	public void setDoorList(BoardCell cell) {
		doorList.add(cell);
	}

	// return door list
    public Set<BoardCell> getDoorList() {
		return doorList;
	}
    
    
    public BoardCell getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(BoardCell secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setHasSecretPassage(boolean hasSecretPassage) {
		this.hasSecretPassage = hasSecretPassage;
	}
	
	public boolean roomHasSecretPassage() {
		return hasSecretPassage;
	}
	
}

