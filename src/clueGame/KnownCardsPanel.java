package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel {

	private int peopleInHandCardNum;
	private int peopleSeenCardNum;
	private int roomInHandCardNum;
	private int roomSeenCardNum;
	private int weaponInHandCardNum;
	private int weaponSeenCardNum;


	private JPanel peopleInHandPanel;
	private JPanel peopleSeenPanel;
	private JPanel roomInHandPanel;
	private JPanel weaponInHandPanel;
	private JPanel weaponSeenPanel;



	public KnownCardsPanel() {
		JPanel peopleInHandPanel = new JPanel();
		JPanel peopleSeenPanel= new JPanel();
		JPanel roomInHandPanel= new JPanel();
		JPanel weaponInHandPanel= new JPanel();
		JPanel weaponSeenPanel = new JPanel();
		Board board = new Board();
	}

	private JPanel createKnownCardPanel(Player humanPlayer) {
		//get the humanPlyer from Board
		Player hunmanPlayer  = Board.getInstance().getPlayersList().get(0);
		setInHandTextFieldsNum(hunmanPlayer.getMyCards());
		setSeenTextFieldsNum(hunmanPlayer.getSeenCards());

		//initialize the  knowCardsPanel 
		JPanel knowCardsPanel = new JPanel();
		knowCardsPanel.setLayout(new GridLayout(3,0));
		knowCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		//initialize the PeopleCardsPanel	
		JPanel peopleCardsPanel = new JPanel();
		peopleCardsPanel.setLayout(new GridLayout(2,0));
		//initialize the People InHand Panel		
		peopleInHandPanel.setLayout(new GridLayout(peopleInHandCardNum+1,0));
		JLabel peopleInHandCardLabel= new JLabel("In Hand: ");
		displayCardsInPanel(peopleInHandCardNum, hunmanPlayer.getMyCards(),CardType.PERSON,peopleInHandPanel);





		JPanel peopleSeenCardsPanel = new JPanel();

		//How to get size?
		//peopleSeenCardsPanel.setLayout(new GridLayout(peopleSeenCardNum.size()+1,0));
		JLabel peopleSeenCardsLabel= new JLabel("Seen: ");


		JPanel roomCardsPanel = new JPanel();
		JPanel weaponCardsPanel = new JPanel();
		return null;
	}


	public void setInHandTextFieldsNum(ArrayList<Card> myCards) {
		for(Card card: myCards) {
			switch (card.getCardType()) {
			case ROOM:
				roomInHandCardNum = roomInHandCardNum+1;
				break;
			case PERSON:
				peopleInHandCardNum = peopleInHandCardNum+1;
				break;
			case WEAPON:	
				weaponInHandCardNum =weaponInHandCardNum +1;
			default:
				break;
			}

		}
	}

	public void setSeenTextFieldsNum(ArrayList<Card> seenCards) {
		for(Card card: seenCards) {
			switch (card.getCardType()) {
			case ROOM:
				peopleSeenCardNum = roomInHandCardNum+1;
				break;
			case PERSON:
				peopleInHandCardNum = peopleInHandCardNum+1;
				break;
			case WEAPON:	
				weaponSeenCardNum =weaponInHandCardNum +1;
			default:
				break;
			}

		}
	}

	public void displayCardsInPanel(int textFieldsNum, ArrayList<Card> cards,CardType cardType,JPanel currentPanel) {
		Map<Card,Color> cardsMap = Board.getInstance().getCardMap();
		JTextField	textFiled = new JTextField();
		if( textFieldsNum==0) {
			textFiled = new JTextField("None");
			textFiled.setEditable(false);
			textFiled.setBackground(Color.GRAY);
			currentPanel.add(textFiled);

		}
		else {
			for(Card card : cards)
				if(card.getCardType() == cardType )	{
					textFiled = new JTextField(card.getCardName());
					textFiled.setEditable(false);
					textFiled.setBackground(cardsMap.get(card));
					currentPanel.add(textFiled);}
		}
	}


// Main to test the panel
public static void main(String[] args) {
	GameControlPanel panel = new GameControlPanel();  // create the panel
	JFrame frame = new JFrame();  // create the frame 
	frame.setContentPane(panel); // put the panel in the frame
	frame.setSize(750, 180);  // size the frame
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
	frame.setVisible(true); // make it visible

	// test filling in the data
	panel.setTurn(new ComputerPlayer("Miss Kate", "red", 0, 15), 5);
	panel.setGuess( "I have no guess!");
	panel.setGuessResult( "So you have nothing?");
}




}

