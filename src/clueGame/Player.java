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
	private ArrayList<Card> cards;	

	
	 public Player(String name, String strColor, int row, int column) {
		super();
		this.name = name;
		this.strColor = strColor;
		this.row = row;
		this.column = column;
		cards = new ArrayList<Card>(); 
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
		cards.add(card);
	}
	 
	public ArrayList<Card> getCards() {
		return cards;
	}
	
}
