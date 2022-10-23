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
	public int row;
	public int col;
	private char initial;
	private DoorDirection doordDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean secret;
	private char secretPassage;

	//hold the cells room status.
	private Boolean isRoom; 
	//hold the cells occupied status.
	private Boolean isOccupied ;
	//A Set of board cells to hold adjacency list
	private Set<BoardCell> adjList;

	// default constructor
	public BoardCell() {

	}

	// constructor
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		roomLabel = false;
		roomCenter = false;
		secret = false;
		doordDirection= DoorDirection.NONE;
		adjList = new HashSet();
		isOccupied = false;
		isRoom = false;
	}

	// Get room letter
	public void setInitial(char initial) {
		this.initial = initial;
	}

	// return a room letter
	public char getInitial() {
		return initial;
	}

	// ASet door direction
	public void setDoordDirection(DoorDirection doordDirection) {
		this.doordDirection = doordDirection;
	}

	// Set is room label
	public void setIsRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	// Set is room center
	public void setIsRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
    
	// Set secret passage 
	public void setIsSecretPassage(boolean secret) {
		this.secret = secret;
	}
	
	// set secret passage letter
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	//for indicating a cell is part of a room (void setRoom(boolean) and perhaps boolean isRoom()).	
	public void setIsRoom(boolean isRoom)
	{
		this.isRoom = isRoom;
	}

	//for setting a cell is occupied
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	// add adjacency cell
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	//returns the adjacency list for the cell
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	// return door way status
	public boolean isDoorway() {
		if (doordDirection == DoorDirection.NONE) {
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

	// return room status
	public boolean getIsRoom()
	{
		return isRoom;
	}

	// return secret passage status
	public boolean isSecretPassage() {
		return secret;
	}
		
	//for indicating a cell is occupied by another player 
	public boolean getOccupied() {
		if (roomCenter) {
			return false;
		}
		return isOccupied;
	}

}
