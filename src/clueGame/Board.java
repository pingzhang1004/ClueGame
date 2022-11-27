/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import experiment.TestBoardCell;

public class Board extends JPanel{

	// Variables
	private static final char walkwayChar = 'W';
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigfile;
	private Map<Character, Room> roomMap;	

	//A set of board cells to hold the visited list
	//It is used to avoid backtracking.
	private	Set<BoardCell> visited;
	private Set<BoardCell> targets;


	//the answer is for store the suggestion
	private Solution theAnswer;

	// There are 6 players, one HumanPlayer, 5 computerPlayer
	private ArrayList<Player> players;
	//There are 21 cards：9 room cards, 6 player cards, 6 weapon cards
	private  ArrayList<Card> cards;
	
	private Player currentPlayer;
	
	private int roll;
	private final int MAX_ROLL = 6;
	private final int MIN_ROLL = 1;
	
	private boolean findSolution;
	private boolean finishedTurn;
	public boolean enabled;
	private int playerIndex;

	
	//private targetListener mouseCliker;

	//private JPanel boardPanel;
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	public Board() {
		super();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */


	// Set all config files
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = layout;
		setupConfigfile = setup;
	}

	// Initialize the game board
	public void initialize()
	{	
		//boardPanel = new JPanel();
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	
		catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		roomEnter();
		calcAdjList();
		deal();
		finishedTurn = false;
		findSolution = false;
		playerIndex = 0;
		addMouseListener(new targetListener());	
		//mouseCliker = new targetListener();
	}
	// End from Canvas

	// Load layout file
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {	
		FileReader reader = null;
		Scanner in = null;

		//read the layoutConfigFile first time to get the numColumns and numRows;		
		reader = new FileReader("data/" + layoutConfigFile);
		in = new Scanner(reader);
		ArrayList<String> lines = new ArrayList<String>();
		while (in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		numRows = lines.size();		
		numColumns = lines.get(0).split(",").length;

		// Columns Exception------------------------
		int sum = 0;
		for (int i=0; i < numRows; i++) {
			sum = sum + lines.get(i).split(",").length;
		}
		if (sum / numRows != numColumns) {
			throw new BadConfigFormatException("The board layout file does not have the same number of columns in every row!");
		}
		else {
			loadCells(lines);
		}
	}

	// Load all cells into the game board
	private void loadCells(ArrayList<String> lines) throws BadConfigFormatException {
		//allocate the grid size.
		grid = new BoardCell[numRows][numColumns];			
		//populate the data from the layoutConfigFile to board cells
		for(int i =0; i < numRows; i++) {
			for(int j =0; j< numColumns; j++) {

				// Legend Exception
				char charAtZero = lines.get(i).split(",")[j].charAt(0);
				if (!roomMap.keySet().contains(charAtZero)) {
					throw new BadConfigFormatException("The board layout refers to a room that is not in the setup file!");
				}

				BoardCell cell = new BoardCell(i,j);
				if (lines.get(i).split(",")[j].length() == 1) {						
					cell.setInitial(charAtZero);
					loadRoomCells(charAtZero,cell);
				}
				else {
					cell.setInitial(charAtZero);
					char charAtOne = lines.get(i).split(",")[j].charAt(1);

					switch (charAtOne) {

					case '*':
						cell.setIsRoomCenter(true);
						getRoom(cell).setCenter(cell);
						//roomMap.get(charAtZero);
						loadRoomCells(charAtZero,cell);
						break;

					case '#':
						cell.setIsRoomLabel(true);
						getRoom(cell).setLabel(cell);
						loadRoomCells(charAtZero,cell);
						break;

					case 'v':
						cell.setDoordDirection(DoorDirection.DOWN);
						break;

					case '^':
						cell.setDoordDirection(DoorDirection.UP);
						break;

					case '>':
						cell.setDoordDirection(DoorDirection.RIGHT);
						break;

					case '<':
						cell.setDoordDirection(DoorDirection.LEFT);
						break;

					default:
						cell.setSecretPassage(charAtOne);
						cell.setIsSecretPassage(true);
					}
				}
				
				grid[i][j] = cell; 		
			}
		}
	}

	// Add all room cell to the specific room
	public void loadRoomCells(char initial, BoardCell cell) {
		for (char key : roomMap.keySet()) {
			if (key == initial) {
				roomMap.get(key).addRoomCells(cell);
				break;
			}
		}
	}
	
	// Load setup file
 	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		//you need to build the grid array with the layout file. But how big do you make it, i.e. number of rows and columns?
		//You almost need to know this information before you even read the file.
		roomMap = new TreeMap<Character, Room>();
		players = new ArrayList<Player>();
		cards = new ArrayList<Card>();
		FileReader reader = null;
		Scanner in = null;

		reader = new FileReader("data/"+ setupConfigfile);
		in = new Scanner(reader);
		String line;
		String[] strSplit; 
		while (in.hasNextLine()) {
			line = in.nextLine();
			if(!line.startsWith("//")){			
				strSplit  = line.split(", ");
				Room room;
				char roomLabel;
				switch(strSplit[0]){					 
				case "Room": 
					//create Room
					room = new Room();
					room.setName(strSplit[1]);	
					roomLabel =strSplit[2].charAt(0);
					roomMap.put(roomLabel, room);						
					//create Person cards at the time we load the room info.
					//card = new Card(CardType.ROOM,strSplit[1]);
					cards.add(new Card(CardType.ROOM,strSplit[1]));
					break;  
				case "HumanPlayer":
					//create HumanPlayer				
					players.add(new HumanPlayer(strSplit[1],strSplit[2], Integer.parseInt(strSplit[3]),Integer.parseInt(strSplit[4])));
					cards.add(new Card(CardType.PERSON,strSplit[1]));
					break; 
				case "ComputerPlayer": 
					//create ComputerPlayer
					players.add(new ComputerPlayer(strSplit[1],strSplit[2], Integer.parseInt(strSplit[3]),Integer.parseInt(strSplit[4])));
					cards.add(new Card(CardType.PERSON,strSplit[1]));
					break;         
				case "Weapon":
					//create Weapon cards
					cards.add(new Card(CardType.WEAPON,strSplit[1]));
					break; 
				case "Space":
					room = new Room();
					room.setName(strSplit[1]);	
					roomLabel =strSplit[2].charAt(0);
					roomMap.put(roomLabel, room);	
					break;
				default:
					throw new BadConfigFormatException("An entry in either file does not have the proper format!");						//break;
				}					
			}
		}
	}
	//	}

	// Calculate adjacency list for all cells
	public void calcAdjList() {	
		for(int i =0; i< numRows; i++) {
			for(int j =0; j< numColumns; j++) {				
				calcAdj(i, j);
			}
		}
	}

	// Get door and secret passage for all rooms
	public void roomEnter() {
		for(int i =0; i< numRows; i++) {
			for(int j =0; j< numColumns; j++) {	

				BoardCell cell = grid[i][j];
				DoorDirection doorDiret = cell.getDoorDirection();
				char roomLabel = cell.getInitial();
				// Add cell into door list
				if (cell.isDoorway()) {
					switch (doorDiret) {

					case DOWN:
						roomLabel =  grid[i+1][j].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
						break;

					case UP:
						roomLabel =  grid[i-1][j].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
						break;

					case LEFT:
						roomLabel =  grid[i][j-1].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
						break;

					case RIGHT:
						roomLabel =  grid[i][j+1].getInitial();
						roomMap.get(roomLabel).setDoorList(cell);
						break;

					default:
						break;
					}
				}

				if(cell.isSecretPassage()) {
					BoardCell secretPassageCell = roomMap.get(cell.getSecretPassage()).getCenterCell();
					roomMap.get(roomLabel).setSecretPassageCell(secretPassageCell);
					roomMap.get(roomLabel).setDoorList(roomMap.get(roomLabel).getSecretPassageCell());
				}
			}
		}

	}

	//calculating the adjacency list in the board since the important data is the grid, 
	//and then telling the cell what its adjacencies are
	public void calcAdj(int row,int col) {
		BoardCell cell = getCell(row,col);
		//		BoardCell roomCenterCell;
		char roomLabel = cell.getInitial();

		// Cell is a center
		if(cell.isRoomCenter()) {
			for (BoardCell c : roomMap.get(roomLabel).getDoorList()) {
				cell.addAdj(c);
			}
		}
		// Cell is a walkway
		else {
			// cell is a doorway
			addDoorwayAdj(cell, row, col);

			// add adjacency of a walkway
			if (row < numRows-1 && checkCell(grid[row+1][col])) {
				cell.addAdj(grid[row+1][col]);
			}	
			if (row-1 >= 0 && checkCell(grid[row-1][col])) {
				cell.addAdj(grid[row-1][col]);
			}				
			if(col-1 >= 0 && checkCell(grid[row][col-1])) {
				cell.addAdj(grid[row][col-1]);
			}
			if(col< numColumns-1 && checkCell(grid[row][col+1])) {
				cell.addAdj(grid[row][col+1]);
			}			
		}
	}

	// Check if a cell is valid
	public boolean checkCell(BoardCell cell) {
		boolean valid = false;
		if (cell.getInitial() == walkwayChar && !cell.getOccupied() && !cell.getIsRoom()) {
			valid = true;
		}
		return valid;
	}

	// Add doorway adjacency
	public void addDoorwayAdj(BoardCell cell, int row, int col) {
		BoardCell roomCenterCell;
		char roomLabel = cell.getInitial();
		switch (cell.getDoorDirection()) {
		case UP:
			roomLabel = grid[row-1][col].getInitial();
			break;
		case DOWN:
			roomLabel = grid[row+1][col].getInitial();
			break;
		case LEFT:
			roomLabel = grid[row][col-1].getInitial();
			break;
		case RIGHT:
			roomLabel = grid[row][col+1].getInitial();
			break;
		default:
			return;
		}
		roomCenterCell = roomMap.get(roomLabel).getCenterCell();
		cell.addAdj(roomCenterCell);
	}

	// return adjacency list for a cell
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();  	
	}

	//calculates legal targets for a move from startCell of length pathlength.
	public void calcTargets(BoardCell startCell, int pathlength) 
	{
		//A set of board cells to hold the visited list
		//It is used to avoid backtracking.
		visited = new HashSet<BoardCell>();
		//A set of board cells to hold the resulting targets from TargetCalc()
		targets = new HashSet<BoardCell>();

		//add the start location to the visited list (so no cycle through this cell)
		visited.add(startCell);
		//call the recursive function for targets
		findAllTargets(startCell, pathlength); 
	}

	//recursive function to find all the targets
	public void findAllTargets(BoardCell startCell, int pathlength ) 
	{
		Set<BoardCell> adjList = startCell.getAdjList();
		for(BoardCell adjCell: adjList)
		{
			if (visited.contains(adjCell) == true || adjCell.getOccupied()==true) {
				continue;
			}
			visited.add(adjCell);

			if (pathlength == 1 || adjCell.isRoomCenter()) {
				targets.add(adjCell);       		   
			}
			else {
				findAllTargets(adjCell,pathlength-1);
			}
			visited.remove(adjCell);    
		}
	}


	//deal The solution to the game,
	//deal other cards to the players.
	public void deal() {

		//solution card include 1 Room Card, 1 player Card, 1 weapon Card
		Card solutionRoomCard = new Card();		
		Card solutionPlayerCard  = new Card();
		Card solutionWeaponCard  = new Card();

		//sort the cards by card type
		ArrayList<Card> roomCards = new ArrayList<Card>();
		ArrayList<Card> playerCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();

		ArrayList<Card> dealPlayCards = new ArrayList<Card>();
		//dealPlayCards = cards;

		for(Card cardSort :cards) {
			switch(cardSort.getCardType()) {
			case ROOM:
				roomCards.add(cardSort);
				dealPlayCards.add(cardSort);
				break;
			case PERSON:
				playerCards.add(cardSort);
				dealPlayCards.add(cardSort);
				break;
			case WEAPON:	
				weaponCards.add(cardSort);
				dealPlayCards.add(cardSort);
				break;
			default:
				break;
			}
		}

		// generating different type cards Randomly to get the solution
		Random randomGenerator = new Random();	
		int RandomIndex;

		//generating RoomCard Randomly		
		if(!roomCards.isEmpty()) {
			RandomIndex = randomGenerator.nextInt(roomCards.size());
			solutionRoomCard = roomCards.get(RandomIndex);
			dealPlayCards.remove(solutionRoomCard);
		}


		//generating PlayCard Randomly
		if(!playerCards.isEmpty()) {
			RandomIndex = randomGenerator.nextInt(playerCards.size());
			solutionPlayerCard = playerCards.get(RandomIndex);
			dealPlayCards.remove(solutionPlayerCard);
		}

		//generating WeaponCard Randomly
		if(!weaponCards.isEmpty()) {
			RandomIndex = randomGenerator.nextInt(weaponCards.size());
			solutionWeaponCard = weaponCards.get(RandomIndex);
			dealPlayCards.remove(solutionWeaponCard);
		}
		//using the random room card,random player card and random weapon card to assign a solution
		setAnswer(solutionPlayerCard, solutionRoomCard, solutionWeaponCard);

		//deal the left card randomly to 6 players, each player has 3 cards
		//Card randomPlayerCard = new Card();		
		for(Player player : players) {			
			assignCardsToPlayerRandomly(dealPlayCards, player);		
			assignCardsToPlayerRandomly(dealPlayCards, player);			
			assignCardsToPlayerRandomly(dealPlayCards, player);
			player.setUnseenCard(cards);
		}

	}

	//deal the left card randomly to each player,no card dealt twice
	private void assignCardsToPlayerRandomly(ArrayList<Card> dealPlayCards,  Player currentPlayer) {
		Random randomGenerator = new Random();
		int RandomIndex;
		Card randomPlayerCard;
		if(!dealPlayCards.isEmpty()) {
			RandomIndex = randomGenerator.nextInt(dealPlayCards.size());
			randomPlayerCard = dealPlayCards.get(RandomIndex);
			randomPlayerCard.setCardHolder(currentPlayer);
			currentPlayer.updateHand(randomPlayerCard);
			dealPlayCards.remove(randomPlayerCard);
		}
	}

	//return the targets
	public Set<BoardCell> getTargets() {
		return	targets;
	}

	//returns the cell from the board at row, col.
	public BoardCell getCell(int row, int col) {				
		return grid[row][col];
	}

	// return a room object by passing a char
	public Room getRoom(char letter) {
		return roomMap.get(letter);
	}

	// return a room object by passing a BoardCell
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	// return the number of rows
	public int getNumRows() {	
		return numRows;
	}

	// return the number of columns
	public int getNumColumns() {	
		return numColumns;
	}

	//return playerList
	public ArrayList<Player> getPlayersList() {
		return players;
	}

	//return CardList
	public ArrayList<Card> getCardsList() {
		return cards;
	}

	// return a room list
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	//return solution
	public Solution getTheAnswer() {
		return theAnswer;
	}

	// Set the solution
	public void setAnswer(Card person, Card room, Card weapon) {
		theAnswer = new Solution(room, person, weapon);
	}

	// Return a player
	public Player getPlayer(String Name, String color, int row, int column) {
		for(Player player : players) {
			if(Name.equals(player.getName()) && color.equals(player.getStrColor()) && row==(player.getRow()) && column==(player.getColumn())) {
				return player ;
			}
		}
		return null;
	}

	// When we needed a card, we would use our getCard() to return a reference from the deck. 
	//parameter(s) might be for getCard() without creating fixed constants to reference the cards.  
	public Card getCard(String cardName, CardType cardTpye) {
		for(Card currentCard : cards) {
			if(cardName.equals(currentCard.getCardName()) && cardTpye.equals(currentCard.getCardType()))
				return currentCard ;
		}
		return null;

		//return cards.get(cards.indexOf(cardName));
	}

	// Set players for testing
	public void setPlayers(ArrayList<Player> testPlayers) {
		this.players = testPlayers;
	}
	
	// Set the current player
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	// Get the current player
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	// returns true if accusation matches theAnswer
	public boolean checkAccusation(Solution accusation) {
		boolean checkPerson = theAnswer.getPersonCard().equals(accusation.getPersonCard());
		boolean checkRoom = theAnswer.getRoomCard().equals(accusation.getRoomCard());
		boolean checkWeapon = theAnswer.getWeaponCard().equals(accusation.getWeaponCard());
		// Check the answer
		if (checkPerson && checkRoom && checkWeapon) {
			return true;
		}
		return false;
	}

	// Process all the players in turn, each to see if they can dispute the suggestion. 
	// If return null, no player can dispute the suggestion. 
	// Otherwise return the first card that disputed the suggestion.
	public Card handleSuggestion(Player player, Solution suggestion) {
		int index = players.indexOf(player);
		// Check players after the current player
		for (int i=index+1; i<players.size(); i++) {
			Card disproveCard = players.get(i).disproveSuggestion(suggestion);
			if (disproveCard != null) {
				disproveCard.setCardHolder(players.get(i));
				return disproveCard;
			}
		}
		// Check players before the current player
		for (int i=0; i<index; i++) {
			Card disproveCard = players.get(i).disproveSuggestion(suggestion);
			if (disproveCard != null) {
				disproveCard.setCardHolder(players.get(i));
				return disproveCard;

			}
		}
		return null;
	}

	// Set roll to a integer between 1 and 6 randomly
	public void setRoll() {
		int randomNum = (int)((Math.random() * MAX_ROLL) + MIN_ROLL);
		this.roll = randomNum;
	}
	
	// Get the roll value
	public int getRoll() {
		return roll;
	}
	
	// Game process setting
	public void setSolutionProcess() {
		this.findSolution = true;
	}
	public void setTurnProcess(boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
	}
	public boolean getTurnProcess() {
		return finishedTurn;
	}
	public boolean getSolutionProcess() {
		return findSolution;
	}
	
	// GUI part**********************************************************************************
	//Add a paintComponent() method to draw the board and players.
	public void paintComponent(Graphics g) {
		//boardPanel.setPreferredSize(new Dimension(1000, 1000));
		super.paintComponent(g);

		// use an object-oriented approach that has each BoardCell object draw itself.
		int boardWidth = getWidth();
		int boardHeight = getHeight();
		
		// Set game board background color
		g.fillRect(0, 0, boardWidth, boardHeight);
		g.setColor(Color.black);
		
		// Calculation for cell size and position
		int cellWidth = boardWidth / numColumns;
		int	cellHeight = boardHeight / numRows;
		
		// cell side
		int side = Math.min(cellWidth, cellHeight);
		
		// top left corner point
		int originalX = (boardWidth - (numColumns * side)) / 2;
		int originalY = (boardHeight - (numRows * side)) / 2;
		
		int offsetY;
		int offsetX;
		
		// draw the board 
		BoardCell cell = new BoardCell(); 
		for(int i =0; i< numRows; i++) {
			offsetY = i*side + originalY;
			for(int j =0; j< numColumns; j++) {	
				offsetX = j * side + originalX;
				cell = grid[i][j];
				cell.drawCell(side, side, offsetX,offsetY, g);
			}
		}

		// draw the targets
		if (targets != null) {
			for (BoardCell targetCell : targets) {
				
				if (targetCell.isRoomCenter()) {
					char initial = targetCell.getInitial();
					Set<BoardCell> roomCells = roomMap.get(initial).getRoomCells();
					for (BoardCell roomCell : roomCells) {
						offsetX = roomCell.getCol() * side + originalX;				
						offsetY = roomCell.getRow() * side + originalY;
						roomCell.drawRoomTarget(g,offsetX,offsetY,side,side);
					}
				}
				else {
					offsetX = targetCell.getCol() * side + originalX;				
					offsetY = targetCell.getRow() * side + originalY;
					targetCell.drawTarget(g,offsetX,offsetY,side,side);
				}
			}
		}
		
		// draw the room name
		for(int i =0; i< numRows; i++) {
			offsetY = i*side + originalY;
			for(int j =0; j< numColumns; j++) {	
				offsetX = j * side + originalX;
				cell = grid[i][j];
				//draw the door
				cell.drawDoor(side, side, offsetX,offsetY, g);
				//draw the roomLabel
				if(cell.isRoomLabel()== true) {
					String roomName = roomMap.get(cell.getInitial()).getName();
					cell.drawRoomLabel(offsetX,offsetY,Math.round(side/2), roomName,g);						
				}
				//draw the secrect pass
				if(cell.isSecretPassage()== true) {
					cell.drawSecretCell(side, side, offsetX,offsetY, g);	
				}
			}
	
		}
				
		//draw the player
		int offset = side / 2;
		for(Player player : players) {
			offsetX = player.getColumn() * side + originalX;				
			offsetY = player.getRow() * side + originalY;
			cell = grid[player.getRow()][player.getColumn()];
			
			ArrayList<Player> roomPlayers= new ArrayList<Player>();
			
			if (cell.isRoomCenter()) {
				BoardCell playerCell = new BoardCell();
				// get all players in the room
				for (Player p : players) {
					playerCell = grid[p.getRow()][p.getColumn()];
					if (playerCell.equals(cell)) {
						roomPlayers.add(p);
					}
				}
				int index = roomPlayers.indexOf(player);
			    offsetX = offsetX+(index*offset);
			}
			cell.drawPlayer(player.getColor(),g,offsetX,offsetY,side,side);
		}
	}
	
	// When a point on the board panel is clicked
	private class targetListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			// When the mouseListener is off
			if (!enabled) {
				return;
			}
			// When the mouseListener is on
			else {
				boolean moved = false;
				BoardCell cell = new BoardCell();
				// Check if a target is clicked
				for (BoardCell target : targets) {
					// check targets that is a room cell
					if (target.isRoomCenter()) {
						char initial = target.getInitial();
						Set<BoardCell> roomCells = roomMap.get(initial).getRoomCells();
						for (BoardCell roomCell : roomCells) {
							if (roomCell.containsClick(e.getX(), e.getY())) {
								moved = true;
								enabled = false;
								cell = target;
								break;
							}
						}
						if (moved) {
							break;
						}
					}
					// check target that is not a room cell
					else {
						if (target.containsClick(e.getX(), e.getY())) {
							moved = true;
							enabled = false;
							cell = target;
							break;
						}
					}	
				}

				if (moved) {
					// Set the new position for player
					currentPlayer.setRow(cell.getRow());
					currentPlayer.setColumn(cell.getCol());
					cell.setOccupied(true);
					targets.clear();
					repaint();
					// the turn is finished
					finishedTurn = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "That is not a target");
				}
			}
	    }
	}
	
//	public targetListener gettargetListener() {
//		return mouseCliker;
//	}
	
	// game control center
	public void startTurn() {
		Player player = players.get(playerIndex);
		
		// Set information for turn
		setCurrentPlayer(player);
		setRoll();
		
		//removeMouseListener(gettargetListener());
		BoardCell playerCell = getCell(currentPlayer.getRow(), currentPlayer.getColumn());
		calcTargets(playerCell, getRoll());
		//endTurn(player, playerCell);
		
	}
	public void endTurn(Player player) {
		BoardCell playerCell = getCell(currentPlayer.getRow(), currentPlayer.getColumn());
		if (targets.size() == 0) {
			// clicker on
			enabled = false;
			
			repaint();
			if (player.equals(players.get(0))) {
				JOptionPane.showMessageDialog(null, "You are blocked!");
			}
			finishedTurn = true;
		}
		else if (player.equals(players.get(0)) && !finishedTurn) {
		    repaint();
		    
		    // clicker off
			enabled = true;
			
			playerCell.setOccupied(false);
			
		}
		else {
			// clicker off
			enabled = false;
			
			BoardCell targetCell = player.selectTarget(targets, roomMap);
			playerCell.setOccupied(false);
			player.setRow(targetCell.getRow());
			player.setColumn(targetCell.getCol());
			targetCell.setOccupied(true);
			targets.clear();
			repaint();
			finishedTurn = true;
		}
		
		// Get the next player
		playerIndex = (playerIndex + 1) % players.size();
	}
	
	public void suggestionControl(Player player) {
		
	}
	
	

	




}