package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

class GameSetupTests {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
	
	@BeforeEach
	void setUp() throws Exception {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	//ensure People are loaded in, each player at the correct start location
	//6 player objects should there be, 1 hunman player and 5 computer player
	@Test
	public void PeopleLoadtest() {		
		ArrayList testList = board.getPlayersList();		
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
	public void CardsLoadtest() {		
		ArrayList testList = board.getCardsList();		
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

	



	
	//What are the requirements for the Solution?
	//The solution to the game is dealt
	
	
//What are the requirements for a correct deal?
	
//All cards should be dealt.
//The other cards are dealt to the players.
	
	
//All players should have roughly the same number of cards
	
//The same card should not be given to >1 player
	
//Deck size could easily change (e.g., we could add more weapons). So try to avoid hard-coding # of cards/player.


	
	

	
	

	
	

	
	


}
