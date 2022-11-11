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

import org.junit.jupiter.api.BeforeAll;

public class KnownCardsPanel extends JPanel {

	private JPanel knownCardsPanel;	
	private JPanel peoplePanel;
	private JPanel roomPanel;
	private JPanel weaponPanel;

	private static Board board;

	public KnownCardsPanel() {
		knownCardsPanel = new JPanel();		
		peoplePanel = new JPanel();
		roomPanel = new JPanel();
		weaponPanel  = new JPanel();
		knownCardsPanel = createKnownCardPanel();
		add(knownCardsPanel);

	}

	private JPanel createKnownCardPanel() {

		//initialize the  knowCardsPanel 
		knownCardsPanel.setLayout(new GridLayout(3,0));
		knownCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));

		//initialize the PeoplePanel	
		peoplePanel.setLayout(new GridLayout(0,1));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JLabel peopleInHandLabel= new JLabel("In Hand: ");		
		JLabel peopleSeenLabel= new JLabel("Seen: ");
		//add the people InHand card people Seen card to people Panel
		knownCardsPanel.add(peoplePanel);
		peoplePanel.add(peopleInHandLabel);
		displayCardFields(board.getPlayersList().get(0).getMyCards(),peoplePanel);
		peoplePanel.add(peopleSeenLabel);
		displayCardFields(board.getPlayersList().get(0).getSeenCards(),peoplePanel);

		//initialize the Room Panel	
		roomPanel.setLayout(new GridLayout(0,1));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));	
		JLabel roomInHandLabel= new JLabel("In Hand: ");		
		JLabel roomSeenLabel= new JLabel("Seen: ");	

		//add the room InHand card and room Seen card to room Panel	
		knownCardsPanel.add(roomPanel);
		roomPanel.add(roomInHandLabel);
		displayCardFields(board.getPlayersList().get(0).getMyCards(),roomPanel);
		roomPanel.add(roomSeenLabel);
		displayCardFields(board.getPlayersList().get(0).getSeenCards(),roomPanel);


		//initialize the Weapon Panel	
		weaponPanel.setLayout(new GridLayout(0,1));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JLabel weaponInHandLabel= new JLabel("In Hand: ");		
		JLabel weaponSeenLabel= new JLabel("Seen: ");		

		//add the room InHand Panel and room SeenPanel room CardsPanel		
		knownCardsPanel.add(weaponPanel);
		weaponPanel.add(weaponInHandLabel);
		displayCardFields(board.getPlayersList().get(0).getMyCards(),weaponPanel);
		weaponPanel.add(weaponSeenLabel);
		displayCardFields(board.getPlayersList().get(0).getSeenCards(),weaponPanel);

		return knownCardsPanel;
	}



	//add CardFields in different size automatically 
	public void displayCardFields(ArrayList<Card> cards, JPanel currentPanel) { 
		JTextField	textFiled;
		int cardTypeNum =0;
		//add panels number changing to roomPanel
		if(currentPanel == peoplePanel) {
			for(Card card : cards) {
				if(card.getCardType() == CardType.PERSON) {
					cardTypeNum = addFilledField(currentPanel, cardTypeNum, card);
				}
			}
			addNoneField(currentPanel, cardTypeNum);
		}
		//add panels number changing to roomPanel
		if(currentPanel == roomPanel) {
			for(Card card : cards) {
				if(card.getCardType() == CardType.ROOM) {
					cardTypeNum = addFilledField(currentPanel, cardTypeNum, card);
				}					
			}	
			addNoneField(currentPanel, cardTypeNum);			
		}
		//add panels number changing to rweaponPanel
		if(currentPanel == weaponPanel) {
			for(Card card : cards) {
				if(card.getCardType() == CardType.WEAPON) {
					cardTypeNum = addFilledField(currentPanel, cardTypeNum, card);
				}
			}
			addNoneField(currentPanel, cardTypeNum);
		}
	}

	private int addFilledField(JPanel currentPanel, int cardTypeNum, Card card) {
		JTextField textFiled;
		textFiled = new JTextField(15);
		textFiled.setEditable(false);
		textFiled.setText(card.getCardName());
		if(card.getCardHolder()!= null)
			textFiled.setBackground(card.getCardHolder().getColor());
		else
			textFiled.setBackground(Color.gray);//it means the card is a solution card
		currentPanel.add(textFiled);
		cardTypeNum = cardTypeNum+1;
		return cardTypeNum;
	}

	private void addNoneField(JPanel currentPanel, int cardTypeNum) {
		JTextField textFiled;
		if(cardTypeNum == 0) {
			textFiled = new JTextField(15);
			textFiled.setEditable(false);
			textFiled.setText("None");
			textFiled.setBackground(Color.white);
			currentPanel.add(textFiled);
		}
	}

    //update the Known Cards panel when add a new seenCard to the hunmanPlayer
	public void updatePanels() {
		updatePanel(peoplePanel,CardType.PERSON);
		updatePanel(roomPanel,CardType.ROOM);
		updatePanel(weaponPanel,CardType.WEAPON);
	}

	//rebuild the people panel/room panel/ weapon panel
	public void updatePanel(JPanel currentPanel,CardType cardType) {
		currentPanel.removeAll();	
		//currentPanel.add(currentPanel);
	};

	// Main to test the panel
	public static void main(String[] args) {
		// Board is singleton, get the only instance
		//Board board = Board.getInstance();
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();

		//create seenCards for testing: add all the cards into the sennCards
		for(Card card : board.getCardsList()) {					
			board.getPlayersList().get(0).updateSeen(card);					
		}
		//		
		//		for(Card card :  board.getPlayersList().get(0).getUnseenRooms()) {					
		//			testBoard.getPlayersList().get(0).updateSeen(card);					
		//		}

		// create the panel
		KnownCardsPanel panel = new KnownCardsPanel(); 
		// create the frame 
		JFrame frame = new JFrame(); 
		// put the panel in the frame
		frame.setContentPane(panel); 
		// size the frame
		frame.setSize(200, 600);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// make it visible
		frame.setVisible(true); 
		//		
	}




}

