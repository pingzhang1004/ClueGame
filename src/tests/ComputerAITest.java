package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

public class ComputerAITest {

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	private static Card williamCard, kateCard, harryCard, balconyCard, garageCard, laundryCard, ropeCard, gunCard, batCard;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();

		// Create some card constants for test
		williamCard = new Card(CardType.PERSON, "Mr William");
		kateCard = new Card(CardType.PERSON, "Miss Kate");
		harryCard = new Card(CardType.PERSON, "Mr Harry");

		balconyCard = new Card(CardType.ROOM, "Balcony");
		garageCard = new Card(CardType.ROOM, "Garage");
		laundryCard = new Card(CardType.ROOM, "Laundry");

		ropeCard = new Card(CardType.WEAPON, "rope");
		gunCard = new Card(CardType.WEAPON, "gun");
		batCard = new Card(CardType.WEAPON, "bat");
	}


	@Test
	public void createSuggestionTest() {
		board.setAnswer(williamCard, garageCard, batCard);
		assertTrue(board.checkAccusation(new Solution(garageCard, williamCard, batCard)));
		assertFalse(board.checkAccusation(new Solution(balconyCard, williamCard, batCard)));
		assertFalse(board.checkAccusation(new Solution(garageCard, kateCard, batCard)));
		assertFalse(board.checkAccusation(new Solution(garageCard, williamCard, ropeCard)));
	}
}
