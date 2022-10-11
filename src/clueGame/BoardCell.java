/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {

	// Variables
	private int row;
	private int col;
	private char initial;
	private DoorDirection doordDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	// default constructor
	public BoardCell() {
		
	}
	
	// constructor
	public BoardCell(int row, int col) {
		super();
		roomLabel = false;
		roomCenter = false;
		doordDirection= DoorDirection.NONE;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setDoordDirection(DoorDirection doordDirection) {
		this.doordDirection = doordDirection;
	}

	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	// Add adj cell to list
	public void addAdj(BoardCell adj) {
	}
	
	// return door way status
	public boolean isDoorway() {
		if(doordDirection == DoorDirection.NONE) {
			return false;			
		}
		else {
			return true;
		}
	}
	
	// return a door direction
	public DoorDirection getDoorDirection() {
		return doordDirection;
	}
	
	// return label status
	public boolean isLabel() {
		return roomLabel;
	}
	// return room center way status
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	// return a secret passage
	public char getSecretPassage() {
		return secretPassage;
	}
}
