/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {

	// Variables
	public int row;
	public int col;
	private char initial;
	private DoorDirection doorDirection;
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
		doorDirection= DoorDirection.NONE;
		adjList = new HashSet<BoardCell>();
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
		this.doorDirection = doordDirection;
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
		if (doorDirection == DoorDirection.NONE) {
			return false;			
		}
		else {
			return true;
		}
	}

	// return a door direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	// return label status
	public boolean isRoomLabel() {
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

	//draw the cell
	//Pass information to the board cell, like the cell size and perhaps a board offset.
	public void drawCell(int cellWidth, int cellHeight, int offsetX,int offsetY, Graphics g) {		
		switch (initial) {
		case 'X':
			g.setColor(Color.black);
			g.drawRect(offsetX,offsetY, cellWidth, cellHeight);
			g.fillRect(offsetX, offsetY, cellWidth, cellHeight);				
			break;
		case 'W':		
			g.setColor(Color.yellow);
			g.fillRect(offsetX, offsetY, cellWidth, cellHeight);	
			g.setColor(Color.black);
			g.drawRect(offsetX,offsetY, cellWidth, cellHeight);
			break;
		default:
			g.setColor(Color.lightGray);
			g.drawRect(offsetX,offsetY, cellWidth, cellHeight);
			g.fillRect(offsetX, offsetY, cellWidth, cellHeight);	
			break;
		}
	}

	//draw Door
	public void drawDoor(int cellWidth, int cellHeight, int offsetX,int offsetY, Graphics g) {		

		int doorSize = Math.round(cellWidth / 8);
		
		switch(doorDirection) {
		case UP:
			g.setColor(Color.blue);
			g.fillRect(offsetX,offsetY-doorSize, cellWidth, doorSize);
			break;
		case DOWN:
			g.setColor(Color.blue);
			g.fillRect(offsetX,offsetY+cellHeight, cellWidth, doorSize);
			break;
		case LEFT:
			g.setColor(Color.blue);
			g.fillRect(offsetX-doorSize,offsetY, doorSize, cellHeight);
			break;
		case RIGHT:
			g.setColor(Color.blue);
			g.fillRect(offsetX+cellWidth,offsetY, doorSize, cellHeight);
			break;
		default:
			break;

		}
	}


	//draw room name
	public void drawRoomLabel(int offsetX, int offsetY, int size, String roomName, Graphics g) {
		g.setColor(Color.blue);
		g.setFont(new Font("Verdana", Font.PLAIN, size));
		g.drawString(roomName, offsetX, offsetY);
	}


	//draw room Player
	public void drawPlayer(Color playerColor, Graphics g, int offsetX, int offsetY, int cellWidth, int cellHeight) {			
		g.setColor(playerColor);
		g.fillOval(offsetX, offsetY, cellWidth, cellHeight);
		g.setColor(Color.black);
		g.drawOval(offsetX, offsetY, cellWidth, cellHeight);
	}

	//draw secret path
	public void drawSecretCell(int cellWidth, int cellHeight,int offsetX, int offsetY, Graphics g) {
		//draw secret cell polygon
		g.setColor(Color.black);
		g.drawRect(offsetX,offsetY, cellWidth, cellHeight);
		g.fillRect(offsetX, offsetY, cellWidth, cellHeight);
		//draw secret door polygon
		// x coordinates of vertices
		g.setColor(Color.red);
		int x[] = {offsetX, (int)(offsetX+0.8*cellWidth),  (int)(offsetX+0.8*cellWidth),offsetX };		  
		// y coordinates of vertices
		int y[] = {offsetY, (int)(offsetY+0.2*cellHeight), (int)(offsetY+0.8*cellHeight), offsetY+cellHeight};	  
		// number of vertices	  
		g.drawPolygon(x, y, 4);		
		g.fillPolygon(x, y, 4);
		
		//draw secret cell label
		g.setColor(Color.blue);
		g.setFont(new Font("Verdana", Font.PLAIN, 16));
		g.drawString("S", (int)(offsetX+0.4*cellWidth), (int)(offsetY+0.7*cellHeight));		
		
	}

}
