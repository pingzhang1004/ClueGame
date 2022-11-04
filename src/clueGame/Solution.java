package clueGame;

public class Solution {

	//Each type of card (room, weapon, person) is separately shuffled and one of each are removed.  
	//These three cards are the solution for the game.
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}

	public Card getRoomCard() {
		return room;
	}

	public Card getPersonCard() {
		return person;
	}

	public Card getWeaponCard() {
		return weapon;
	}

	
	
}
