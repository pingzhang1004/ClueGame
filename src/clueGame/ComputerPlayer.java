package clueGame;

public class ComputerPlayer extends Player {

	
	
	public ComputerPlayer(String name, String strColor, int row, int column) {
		super(name, strColor, row, column);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateHand(Card card) {
		super.cards.add(card);
		
	}

}
