package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {

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
		public void checkAccusationTest() {
			board.setAnswer(williamCard, garageCard, batCard);
			// solution that is correct
			assertTrue(board.checkAccusation(new Solution(garageCard, williamCard, batCard)));
			// solution with wrong room
			assertFalse(board.checkAccusation(new Solution(balconyCard, williamCard, batCard)));
			// solution with wrong person
			assertFalse(board.checkAccusation(new Solution(garageCard, kateCard, batCard)));
			// solution with wrong weapon
			assertFalse(board.checkAccusation(new Solution(garageCard, williamCard, ropeCard)));
		}
		
		@Test
		public void disproveSuggestionTest() {
			Solution suggestion = new Solution(garageCard, williamCard, batCard);
			Player william = new HumanPlayer("Mr William", "blue", 0, 7);
			Player kate = new ComputerPlayer("Miss Kate", "red", 0, 15);
			Player harry = new ComputerPlayer("Mr Harry", "yellow", 11, 0);
			
			// 0 match
			william.updateHand(balconyCard);
			william.updateHand(ropeCard);
			william.updateHand(kateCard);
			
			// 1 match
			kate.updateHand(gunCard);
			kate.updateHand(batCard);
			kate.updateHand(laundryCard);
			
			// 3 match
			harry.updateHand(garageCard);
			harry.updateHand(williamCard);
			harry.updateHand(batCard);
			
			// 0 card
			assertEquals(null, william.disproveSuggestion(suggestion));
			// 1 card
			assertEquals(batCard, kate.disproveSuggestion(suggestion));
			// multiple cards
			int countGarage = 0;
			int countWilliam = 0;
			int countBat = 0;
			
			for (int i=0; i<100; i++) {
				Card disproveCard = harry.disproveSuggestion(suggestion);
				if (garageCard.equals(disproveCard)) {
					countGarage ++;
				}
				else if (williamCard.equals(disproveCard)) {
					countWilliam ++;
				}
				else if (batCard.equals(disproveCard)) {
					countBat ++;
				}
			}
			
			assertEquals(100, countGarage+countWilliam+countBat);
			assert(countGarage > 15);
			assert(countWilliam > 15);
			assert(countBat > 15);
		}


		@Test
		public void handleSuggestionTest() {
			
		}
}
