package clueGame;

import experiment.TestBoardCell;

public class Room {

	// Variables
	private String name;
	//private char label;
	private BoardCell centerCell;
	private BoardCell lableCell;
	
	//private BoardCell SecretRoomCell;
	
	
	// default constructor
	public Room() {
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

}

