package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Color color;
	
	//player Strating location
	private int row;
	private int column;
	
	//each player hold multiple cards
	private ArrayList<Card> cards;	
	
	 abstract public void updateHand(Card card) ;	 
	 

	//Either way, it will need to be converted to a Java AWT color value.
	public void strColor(String strColor) {		 
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
	    }				
	}
	 
}
