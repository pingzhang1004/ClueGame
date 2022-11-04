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

	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}

	
	
}
