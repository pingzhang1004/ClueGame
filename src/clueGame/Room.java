package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class Room {

	// Variables
	private String name;
	//private char label;
	private BoardCell centerCell;
	private BoardCell lableCell;
	private Set<BoardCell> doorList;
	
	//private BoardCell SecretRoomCell;
	
	
	// default constructor
	public Room() {
		doorList = new HashSet();
	}
	
//	public Room(String name) {
//		//this.label = label;
//		this.name = name;
//	}
	
	
	// return a room name
	public String getName() {
		return name;
	}
	
//	public char getLabel() {
//		return label;
//	}
//	
	
//	public BoardCell getSecretCell() {
//		return SecretRoomCell;
//	}
//
//	public void setSecretCell(BoardCell secretCell) {
//		SecretRoomCell = secretCell;
//		
//	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(BoardCell lableCell) {
		this.lableCell = lableCell;
	}
	
	public void setCenter(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	// return a label cell
	public BoardCell getLabelCell() {
		return lableCell;
	}
	
	// return a center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public void setDoorList(BoardCell cell) {
		doorList.add(cell);
	}

    public Set<BoardCell> getDoorList() {
		return doorList;
	}
}

