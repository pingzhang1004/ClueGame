package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

class GameSetupTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

	@BeforeAll
	public static void setUpBeforeClass() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	//ensure People are loaded in, each player at the correct start location
	//6 player objects should there be, player is initialized based on people data
	@Test
	public void PeopleLoadtest() {		
		ArrayList<Player> testList = board.getPlayersList();		
		assertEquals(6, testList.size());
		assertTrue(testList.contains(board.getPlayer("Mr William", "blue", 0, 7)));
		assertTrue(testList.contains(board.getPlayer("Miss Kate", "red", 0, 15)));
		assertTrue(testList.contains(board.getPlayer("Mr Harry", "yellow", 11, 0)));
		assertTrue(testList.contains(board.getPlayer("Miss Megan", "pink", 10, 21)));
		assertTrue(testList.contains(board.getPlayer("Mr Charles", "green", 22, 9)));
		assertTrue(testList.contains(board.getPlayer("Miss Elizabeth", "white", 6, 0)));
	}


	//There are 21 cards are in the deck, 9 room cards, 6 weapon cards, 6 people cards
	@Test
	public void CardsLoadTest() {		
		ArrayList<Card> testList = board.getCardsList();		
		assertEquals(21, testList.size());
		assertTrue(testList.contains(board.getCard("Balcony", CardType.ROOM)));
		assertTrue(testList.contains(board.getCard("Home Office", CardType.ROOM)));
		assertTrue(testList.contains(board.getCard("Reception", CardType.ROOM)));

		assertTrue(testList.contains(board.getCard("Mr William", CardType.PERSON)));
		assertTrue(testList.contains(board.getCard("Mr Harry", CardType.PERSON)));
		assertTrue(testList.contains(board.getCard("Miss Elizabeth", CardType.PERSON)));

		assertTrue(testList.contains(board.getCard("rope", CardType.WEAPON)));
		assertTrue(testList.contains(board.getCard("scissor", CardType.WEAPON)));
		assertTrue(testList.contains(board.getCard("hammer", CardType.WEAPON)));		
	}

	//There is 1 random solution, the solution includes 1 room card, 1 weapon card, 1 people card
	@Test
	public void dealSolutionTest() {		

		ArrayList<Card> testCardList = board.getCardsList();
		ArrayList<Card> roomCardList = new ArrayList<Card>();
		ArrayList<Card> personCardList = new ArrayList<Card>();
		ArrayList<Card> weaponCardList = new ArrayList<Card>();

		Solution solutionTest = board.getTheAnswer();

		for(Card cardSort :testCardList) {
			switch(cardSort.getCardType()) {
			case ROOM:
				roomCardList.add(cardSort);
				break;
			case PERSON:
				personCardList.add(cardSort);
				break;
			case WEAPON:	
				weaponCardList.add(cardSort);
				break;
			default:
				break;
			}
		}

		assertTrue(roomCardList.contains(solutionTest.getRoomCard()));
		assertTrue(personCardList.contains(solutionTest.getPersonCard()));
		assertTrue(weaponCardList.contains(solutionTest.getWeaponCard()));			
	}

	//There is 6 players, each player has 3  random cards,the total include 18 cards.
	@Test
	public void dealPlayerCardTest() {		
		ArrayList<Player> testPlayersList = board.getPlayersList();
		assertEquals(6, testPlayersList.size());

		for(Player player :testPlayersList) {
			//each player has 3  random cards
			assertEquals(3, player.getMyCards().size());			
		}

		//The same card should not be given to >1 player
		for(int i=0; i< 6; i++)
		{
			for(Card playerCard : testPlayersList.get(i).getMyCards()) {				
				//any two players don't have same card
				for (int j =i+1; j<6; j++)
					assertFalse(testPlayersList.get(j).getMyCards().contains(playerCard));
			}
		}
	}

	//all cards are dealt
	@Test
	public void allCardDealtTest() {
		//There are 21 cards are in the deck
		ArrayList<Card> testCardList = board.getCardsList();
		//6 players,each one have 3 cards
		ArrayList<Player> testPlayersList = board.getPlayersList();
		// one solution has 3 cards
	
		//creat a dealAllCardList and copy all the 21 cards from testCardList to the dealAllCardList
		ArrayList<Card> dealAllCardList = new ArrayList<Card>();
		for(Card card: testCardList) {
			dealAllCardList.add(card);
		}
		
		
		//if a solution has 3 cards in the 21 cards, 
		// Then add the solution card ,the card list size will be 18
		Solution solutionTest = board.getTheAnswer();
		assertTrue(dealAllCardList.contains(solutionTest.getRoomCard()));
		assertTrue(dealAllCardList.contains(solutionTest.getPersonCard()));
		assertTrue(dealAllCardList.contains(solutionTest.getWeaponCard()));
		dealAllCardList.remove(solutionTest.getRoomCard());
		dealAllCardList.remove(solutionTest.getPersonCard());
		dealAllCardList.remove(solutionTest.getWeaponCard());
		//The 18 means the solution cards in the crad list has been dealt
		assertEquals(18, dealAllCardList.size());
		
		//if any player hold a card that is in the 18 cards, delete the card
		//then the card list size will be 0
		for(Player player: testPlayersList) {
			for(Card card :player.getMyCards()) {
				assertTrue(dealAllCardList.contains(card));
				dealAllCardList.remove(card);
			}				
		}
		//The 0 means all the cards in the crad list has been dealt
		assertEquals(0, dealAllCardList.size());
	}


}
