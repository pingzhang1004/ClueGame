package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {


	private ArrayList<Card> cards;
	
	public HumanPlayer(String name, String strColor, int row, int column) {
		super(name, strColor, row, column);
		cards = new ArrayList<Card>();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void updateHand(Card card) {
		
		cards.add(card);
	}

}
