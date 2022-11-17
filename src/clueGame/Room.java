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
	private BoardCell secretPassageCell;

	private Set<BoardCell> roomCells;
	
	// default constructor
	public Room() {
		doorList = new HashSet<BoardCell>();
		roomCells = new HashSet<BoardCell>();
	}

	// get all room cell
	public Set<BoardCell> getRoomCells() {
		return roomCells;
	}

	// add room cell into the room
	public void addRoomCells(BoardCell cell) {
		roomCells.add(cell);
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
    
    // Set secret passage cell
    public void setSecretPassageCell(BoardCell secretPassageCell) {
    	this.secretPassageCell = secretPassageCell;
    }
    
    // return a secret passage cell
    public BoardCell getSecretPassageCell() {
    	return secretPassageCell;
    }

}
