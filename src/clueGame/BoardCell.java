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
	}
	
	
	
	public void setInitial(char initial) {
		this.initial = initial;
	}

	// Add adj cell to list
	public void addAdj(BoardCell adj) {
	}
	
	// return door way status
	public boolean isDoorway() {
		return true;
	}
	
	// return a door direction
	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}
	
	// return label status
	public boolean isLabel() {
		return true;
	}
	// return room center way status
	public boolean isRoomCenter() {
		return true;
	}
	
	// return a secret passage
	public char getSecretPassage() {
		return ' ';
	}
}
