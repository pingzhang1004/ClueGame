package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {

	private ArrayList<Card> cards;
	
	
	public ComputerPlayer(String name, String strColor, int row, int column) {
		super(name, strColor, row, column);
		cards = new ArrayList<Card>(); 
	}

	@Override
	public void updateHand(Card card) {
		cards.add(card);
		
	}

}
