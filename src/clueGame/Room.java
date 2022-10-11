package clueGame;

import experiment.TestBoardCell;

public class Room {

	// Variables
	private String name;
	private String label;
	private BoardCell centerCell;
	private BoardCell lableCell;
	private BoardCell cell;
	
	// default constructor
	public Room() {
		cell = new BoardCell();
	}
	
	// return a room name
	public String getName() {
		return name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	// return a label cell
	public BoardCell getLabelCell() {
		return cell;
	}
	
	// return a center cell
	public BoardCell getCenterCell() {
		return cell;
	}
}

