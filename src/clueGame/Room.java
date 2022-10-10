package clueGame;

import experiment.TestBoardCell;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell lableCell;
	private BoardCell cell;
	
	public Room() {
		cell = new BoardCell();
	}
	
	
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return cell;
	}
	
	public BoardCell getCenterCell() {
		return cell;
	}
}

