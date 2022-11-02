package clueGame;

public class Card {

	//The standard deck of cards is composed on 9 rooms, 6 players and 6 weapons.  
	private String cardName;
	private CardType cardType;
	
	
	
	public Card(CardType cardType,String cardName) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}


	public boolean equals(Card target) {
		return true;
	}
}
