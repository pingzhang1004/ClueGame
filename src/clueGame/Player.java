package clueGame;

import java.awt.Color;
import java.util.ArrayList;

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
	
	public Player(String name, String strColor, int row, int column) {
		super();
		this.name = name;
		this.strColor = strColor;
		this.row = row;
		this.column = column;
		myCards = new ArrayList<Card>(); 
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
	public void setUnseenCard(ArrayList<Card> unseenCards) {
		for (Card card : myCards) {
			unseenCards.remove(card);
			seenCards.add(card);
		}

		//		for (Card card : seenCards) {
		//			unseenCards.remove(card);
		//		}
		//		
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
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		if (myCards.contains(suggestion.getPersonCard())) {
			disproveCards.add(suggestion.getPersonCard());
		}
		if (myCards.contains(suggestion.getRoomCard())) {
			disproveCards.add(suggestion.getRoomCard());
		}
		if (myCards.contains(suggestion.getWeaponCard())) {
			disproveCards.add(suggestion.getWeaponCard());
		}
	
		if (disproveCards.size() == 0) {
			return null;
		}
		else {
			int randomNum = (int)(Math.random() * disproveCards.size());
			return disproveCards.get(randomNum);
		}
	}
	
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



}
