package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public abstract class Player {

	private String name;
	private String strColor;
	private Color color;
	
	//player Strating location
	private int row;
	private int column;
	
	//each player hold multiple cards
	private ArrayList<Card> myCards;	
	private ArrayList<Card> seenCards;

	private ArrayList<Card> unseenPeople;
	private ArrayList<Card> unseenRooms;
	private ArrayList<Card> unseenWeapons;
	
	// Constructor
	public Player(String name, String strColor, int row, int column) {
		super();
		this.name = name;
		this.strColor = strColor;
		this.row = row;
		this.column = column;
		myCards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
		unseenRooms = new ArrayList<Card>(); 
		unseenPeople = new ArrayList<Card>(); 
		unseenWeapons = new ArrayList<Card>(); 
		this.convertStrToColor(strColor);

	}


	//Either way, it will need to be converted to a Java AWT color value.
	public void convertStrToColor(String strColor) {		 
		switch(strColor){
		case "blue": 
			color = Color.blue;
			break;  
		case "green": 
			color = Color.green;
			break;       
		case "red": 
			color = Color.red;
			break;       
		case "pink": 
			color = Color.pink;
			break;   
		case "yellow":
			color = Color.yellow;
			break;   
		case "white":
			color = Color.white;
			break;
		default:
			color = Color.GRAY;

		}				
	}

	//adding cards to the players after initializing the setup.txt
	public void updateHand(Card card) {		
		myCards.add(card);
	}

	// update seen card list
	public void updateSeen(Card card) {
		seenCards.add(card);
		switch (card.getCardType()) {
		case ROOM:
			unseenRooms.remove(card);
			break;
		case PERSON:
			unseenPeople.remove(card);
			break;
		case WEAPON:	
			unseenWeapons.remove(card);
			break;
		default:
			break;
		}
	}
	
	// Set unseen card list
	public void setUnseenCard(ArrayList<Card> cards) {
		ArrayList<Card> unseenCards = new ArrayList<Card>();
		unseenCards.addAll(cards);
		
		for (Card card : myCards) {
			unseenCards.remove(card);
			seenCards.add(card);
		}
	
		for (Card card : unseenCards) {
			switch (card.getCardType()) {
			case ROOM:
				unseenRooms.add(card);
				break;
			case PERSON:
				unseenPeople.add(card);
				break;
			case WEAPON:	
				unseenWeapons.add(card);
				break;
			default:
				break;
			}
		}
	}
	
	// A player tries to dispute a suggestion with the cards in their hand. 
	// If the player cannot, null is returned. 
	// If a player can, the player returns the card. 
	// If is more than one card that can dispute the suggestion, one is randomly chosen.
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		// If the card can be disprove, then add into the disprove card list
		if (myCards.contains(suggestion.getPersonCard())) {
			disproveCards.add(suggestion.getPersonCard());
		}
		if (myCards.contains(suggestion.getRoomCard())) {
			disproveCards.add(suggestion.getRoomCard());
		}
		if (myCards.contains(suggestion.getWeaponCard())) {
			disproveCards.add(suggestion.getWeaponCard());
		}
	
		// No card can be disproved
		if (disproveCards.size() == 0) {
			return null;
		}
		// Card can be disproved
		else {
			int randomNum = (int)(Math.random() * disproveCards.size());
			return disproveCards.get(randomNum);
		}
	}
	
	// getters
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	
	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public ArrayList<Card> getUnseenPeople() {
		return unseenPeople;
	}
	
	public ArrayList<Card> getUnseenRooms() {
		return unseenRooms;
	}
	
	public ArrayList<Card> getUnseenWeapons() {
		return unseenWeapons;
	}


	public String getName() {
		return name;
	}
	
	public String getStrColor() {
		return strColor;
	}

	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	abstract public BoardCell selectTarget(Set<BoardCell> targets, Map<Character, Room> roomMap);

	abstract public Solution createSuggestion(Room room);
}
