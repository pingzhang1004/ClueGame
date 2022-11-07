package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

public class ComputerAITest {

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	private static Card williamCard, kateCard, harryCard, meganCard, balconyCard, garageCard, laundryCard, homeCard, ropeCard, gunCard, batCard, daggerCard;

	private static Player kate;
	private static ArrayList<Card> unseenCards;
	
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
		meganCard = new Card(CardType.PERSON, "Miss Megan");

		balconyCard = new Card(CardType.ROOM, "Balcony");
		garageCard = new Card(CardType.ROOM, "Garage");
		laundryCard = new Card(CardType.ROOM, "Laundry");
		homeCard = new Card(CardType.ROOM, "Home Office");

		ropeCard = new Card(CardType.WEAPON, "rope");
		gunCard = new Card(CardType.WEAPON, "gun");
		batCard = new Card(CardType.WEAPON, "bat");
		daggerCard = new Card(CardType.WEAPON, "dagger");
		
		kate = new ComputerPlayer("Miss Kate", "red", 2, 19);
		
		kate.updateHand(balconyCard);
		kate.updateHand(batCard);
		kate.updateHand(laundryCard);
		
		unseenCards = new ArrayList<Card>();
		
	}


	@Test
	public void selectTargetsTest() {
		unseenCards.add(homeCard);
		kate.setUnseenCard(unseenCards);
		
		//if no rooms in list, select randomly
		board.calcTargets(board.getCell(6, 11), 1);
		Set<BoardCell> targets= board.getTargets();
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		for (int i=0; i<100; i++) {
			BoardCell cell = kate.selectTarget(targets, board.getRoomMap());
			if (board.getCell(6, 10).equals(cell)) {
				count1 ++;
			}
			else if (board.getCell(6, 12).equals(cell)) {
				count2 ++;
			}
			else if (board.getCell(5, 11).equals(cell)) {
				count3 ++;
			}
		}
		assertEquals(100, count1+count2+count3);
		assert(count1 > 10);
		assert(count2 > 10);
		assert(count3 > 10);
		
		// if room in list 
		board.calcTargets(board.getCell(17, 20), 2);
		targets= board.getTargets();
		// that has not been seen, select it
		assertEquals(board.getCell(2, 3), kate.selectTarget(targets, board.getRoomMap()));
		// that has been seen, each target (including room) selected randomly
		kate.updateSeen(homeCard);
		count1 = 0;
		count2 = 0;
		count3 = 0;
		
		for (int i=0; i<100; i++) {
			BoardCell cell = kate.selectTarget(targets, board.getRoomMap());
			if (board.getCell(12, 17).equals(cell)) {
				count1 ++;
			}
			else if (board.getCell(14, 17).equals(cell)) {
				count2 ++;
			}
			else if (board.getCell(13, 16).equals(cell)) {
				count3 ++;
			}
			else if (board.getCell(2, 3).equals(cell)) {
				count4 ++;
			}
		}
		assertEquals(100, count1+count2+count3+count4);
		assert(count1 > 10);
		assert(count2 > 10);
		assert(count3 > 10);
		assert(count4 > 10);
	}
	
	@Test
	public void createSuggestionTest() {
		Room currentRoom = board.getRoomMap().get('G');
		Room currentLocation = board.getRoom(board.getCell(kate.getRow(), kate.getColumn()));
		// Room matches current location
		assertEquals(currentRoom, currentLocation);
		
		// If multiple weapons not seen, one of them is randomly selected
		unseenCards.add(ropeCard);
		unseenCards.add(gunCard);
		unseenCards.add(daggerCard);
		unseenCards.add(kateCard);
		unseenCards.add(harryCard);
		unseenCards.add(meganCard);
		kate.setUnseenCard(unseenCards);
		
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		for (int i=0; i<100; i++) {
			Card card = kate.createSuggestion(currentLocation).getWeaponCard();
			if (ropeCard.equals(card)) {
				count1 ++;
			}
			else if (gunCard.equals(card)) {
				count2 ++;
			}
			else if (daggerCard.equals(card)) {
				count3 ++;
			}
		}
		assertEquals(100, count1+count2+count3);
		assert(count1 > 10);
		assert(count2 > 10);
		assert(count3 > 10);
		
		// If multiple persons not seen, one of them is randomly selected
		count1 = 0;
		count2 = 0;
		count3 = 0;
		for (int i=0; i<100; i++) {
			Card card = kate.createSuggestion(currentLocation).getPersonCard();
			if (kateCard.equals(card)) {
				count1 ++;
			}
			else if (harryCard.equals(card)) {
				count2 ++;
			}
			else if (meganCard.equals(card)) {
				count3 ++;
			}
		}
		assertEquals(100, count1+count2+count3);
		assert(count1 > 10);
		assert(count2 > 10);
		assert(count3 > 10);
		
		// if only one weapon not seen, it's selected
		kate.updateSeen(gunCard);
		kate.updateSeen(daggerCard);
		kate.updateSeen(kateCard);
		kate.updateSeen(meganCard);
		assertTrue(kate.createSuggestion(currentLocation).getWeaponCard().equals(ropeCard));
		// If only one person not seen, it's selected 
		assertTrue(kate.createSuggestion(currentLocation).getPersonCard().equals(harryCard));
		
	}
	
}
