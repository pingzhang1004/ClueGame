/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import experiment.TestBoardCell;



public class BoardCell {

	// Variables
	private int row;
	private int col;
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

	private Rectangle rect;

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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
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


	//draw Player
	public void drawPlayer(String strPlayerColor,Color PlayerColor, Graphics g, int offsetX, int offsetY, int cellWidth, int cellHeight) {			
		
		Graphics2D g2d = (Graphics2D) g.create();	
		BufferedImage img = null;
		TexturePaint imgSlate;

		//read all the palyer from the folder playerImg
		File folder = new File("data/playerImg");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {	
			
			//image is named by the player color
			//eg: human player color is blue, so the blue.image represent the human player
			if (listOfFiles[i].getName().equals(strPlayerColor + ".jpg")) { 			
				try {

					img = ImageIO.read(new File("data/playerImg/"+ listOfFiles[i].getName()));
				}
				catch(Exception e) {
					e.printStackTrace();
				}	        	  
			} 
		}
		
		//draw the image and show the image in a circle
		imgSlate = new TexturePaint(img, new Rectangle(offsetX, offsetY, cellWidth, cellHeight));
		g2d.setPaint(imgSlate);
		g2d.fillOval(offsetX, offsetY, cellWidth, cellHeight);
	
		g2d.setColor(PlayerColor);
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawOval(offsetX, offsetY, cellWidth, cellHeight);	
		g2d.dispose();        
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

	// draw the target
	public void drawTarget(Graphics g, int offsetX, int offsetY, int cellWidth, int cellHeight) {
		g.setColor(Color.cyan);
		g.fillRect(offsetX, offsetY, cellWidth, cellHeight);
		g.setColor(Color.black);
		g.drawRect(offsetX,offsetY, cellWidth, cellHeight);
		rect = new Rectangle(offsetX, offsetY, cellWidth, cellHeight);
	}

	// draw the room target
	public void drawRoomTarget(Graphics g, int offsetX, int offsetY, int cellWidth, int cellHeight) {
		g.setColor(Color.cyan);
		g.fillRect(offsetX, offsetY, cellWidth, cellHeight);
		rect = new Rectangle(offsetX, offsetY, cellWidth, cellHeight);
	}

	// Check if the point of the cell is clicked
	public boolean containsClick(int mouseX, int mouseY) {
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
	}
}
