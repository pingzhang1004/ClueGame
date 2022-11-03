package clueGame;

public class Card {

	//The standard deck of cards is composed on 9 rooms, 6 players and 6 weapons.  
	private String cardName;
	private CardType cardType;
	
	
	
	public Card() {
		super();
	}


	public Card(CardType cardType,String cardName) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}


	//returned a reference to the specific card we wanted
	//compare Cards which were the same, yet had two different references
	public boolean equals(Card target) {		
		
		if(target.cardName.equals(cardName))			
				return true;
		else
			return false;
	}


	public CardType getCardType() {
		return cardType;
	}
	
	
	
	 
	
}
