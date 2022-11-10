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
	private JPanel roomSeenPanel;
	private JPanel weaponInHandPanel;
	private JPanel weaponSeenPanel;

	private Board board;
	private HumanPlayer hunmanPlayer;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;

	public KnownCardsPanel() {
		peopleInHandPanel = new JPanel();
		peopleSeenPanel= new JPanel();
		roomInHandPanel= new JPanel();
		roomSeenPanel= new JPanel();
		weaponInHandPanel= new JPanel();
		weaponSeenPanel = new JPanel();
		board = new Board();
		JPanel panel  = createKnownCardPanel();
		add(panel);
	}

	private JPanel createKnownCardPanel() {
		//get the humanPlyer from Board
		setInHandTextFieldsNum();
		setSeenTextFieldsNum();

		//initialize the  knowCardsPanel 
		JPanel knowCardsPanel = new JPanel();
		knowCardsPanel.setLayout(new GridLayout(3,0));
		knowCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));

		//initialize the PeoplePanel	
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(2,0));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		//initialize the People InHand Panel		
		peopleInHandPanel.setLayout(new GridLayout(peopleInHandCardNum+1,0));
		JLabel peopleInHandLabel= new JLabel("In Hand: ");
		peopleInHandPanel.add(peopleInHandLabel);
		//displayCardsInPanel(peopleInHandCardNum, hunmanPlayer.getMyCards(),CardType.PERSON,peopleInHandPanel);
		displayCardsInPanel(peopleInHandCardNum, myCards,CardType.PERSON,peopleInHandPanel);
		//initialize the People InHand Panel		
		peopleSeenPanel.setLayout(new GridLayout(peopleSeenCardNum+1,0));
		JLabel peopleSeenLabel= new JLabel("Seen: ");
		peopleSeenPanel.add(peopleSeenLabel);
		//displayCardsInPanel(peopleSeenCardNum, hunmanPlayer.getSeenCards(),CardType.PERSON,peopleSeenPanel);
		displayCardsInPanel(peopleSeenCardNum, seenCards,CardType.PERSON,peopleSeenPanel);
		//add the people InHand Panel and people Seen Panel to people Cards Panel
		peoplePanel.add(peopleInHandPanel);
		peoplePanel.add(peopleSeenPanel);

		//initialize the Room Panel	
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(2,0));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		//initialize the room InHand Panel		
		roomInHandPanel.setLayout(new GridLayout(roomInHandCardNum+1,0));
		JLabel roomInHandLabel= new JLabel("In Hand: ");
		roomInHandPanel.add(roomInHandLabel);
		//displayCardsInPanel(roomInHandCardNum, hunmanPlayer.getMyCards(),CardType.ROOM,roomInHandPanel);
		displayCardsInPanel(roomInHandCardNum, myCards,CardType.ROOM,roomInHandPanel);
		//initialize the room InHand Panel		
		roomSeenPanel.setLayout(new GridLayout(roomSeenCardNum+1,0));
		JLabel roomSeenLabel= new JLabel("Seen: ");
		roomSeenPanel.add(roomSeenLabel);
		//displayCardsInPanel(roomSeenCardNum, hunmanPlayer.getSeenCards(),CardType.ROOM,roomSeenPanel);
		displayCardsInPanel(roomSeenCardNum,seenCards,CardType.ROOM,roomSeenPanel);
		//add the room InHand Panel and room SeenPanel room CardsPanel
		roomPanel.add(roomInHandPanel);
		roomPanel.add(roomSeenPanel);


		//initialize the Weapon Panel	
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(2,0));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		//initialize the room InHand Panel		
		weaponInHandPanel.setLayout(new GridLayout(weaponInHandCardNum+1,0));
		JLabel weaponInHandLabel= new JLabel("In Hand: ");
		weaponInHandPanel.add(weaponInHandLabel);
		//displayCardsInPanel(weaponInHandCardNum, hunmanPlayer.getMyCards(),CardType.WEAPON,weaponInHandPanel);
		//displayCardsInPanel(weaponInHandCardNum, hunmanPlayer.getMyCards(),CardType.WEAPON,weaponInHandPanel);
		//initialize the room InHand Panel		
		weaponSeenPanel.setLayout(new GridLayout(weaponSeenCardNum+1,0));
		JLabel weaponSeenLabel= new JLabel("Seen: ");
		weaponSeenPanel.add(weaponSeenLabel);
		displayCardsInPanel(weaponSeenCardNum, hunmanPlayer.getSeenCards(),CardType.WEAPON,weaponSeenPanel);
		//add the room InHand Panel and room SeenPanel room CardsPanel
		weaponPanel.add(weaponInHandPanel);
		weaponPanel.add(weaponSeenPanel);

		knowCardsPanel.add(peoplePanel);
		knowCardsPanel.add(roomPanel);
		knowCardsPanel.add(weaponPanel);

		return knowCardsPanel;
	}

	public void setInHandTextFieldsNum() {
		ArrayList<Card> myCards = hunmanPlayer.getMyCards();
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

	public void setSeenTextFieldsNum() {
		ArrayList<Card> seenCards = hunmanPlayer.getMyCards();		
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
		Map<Card,Color> cardsMap = board.getCardMap();
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
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 600);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		panel.setHumanPlayer();
		panel.setHumanCard();
		
	}


	public void setHumanCard() {
        myCards = new ArrayList<Card>();
		Card myCard = new Card (CardType.ROOM, "Professor Plum");
		 myCards.add(myCard);
		myCard = new Card (CardType.ROOM, "Miss Scarlett");
		 myCards.add(myCard);
		 myCard = new Card (CardType.WEAPON, "Wrench");
		 myCards.add(myCard);
	}

	public void setHumanPlayer() {
		hunmanPlayer = new HumanPlayer("Miss Kate", "red", 0, 15);
	}



}

