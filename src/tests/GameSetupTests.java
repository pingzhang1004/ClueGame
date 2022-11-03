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

	//ensure People are loaded in
	//6 player objects should there be, 1 hunman player and 5 computer player
	@Test
	public void test() {		
		ArrayList testList = board.getPlayersList();		
		assertEquals(6, testList.size());
		assertTrue(testList.contains(board.getPlayer("Mr William", "blue", 0, 7)));
		assertTrue(testList.contains(board.getPlayer("Miss Kate", "red", 0, 15)));
		assertTrue(testList.contains(board.getPlayer("Mr Harry", "yellow", 11, 0)));
		assertTrue(testList.contains(board.getPlayer("Miss Megan", "pink", 10, 21)));
		assertTrue(testList.contains(board.getPlayer("Mr Charles", "green", 22, 9)));
		assertTrue(testList.contains(board.getPlayer("Miss Elizabeth", "white", 6, 0)));
	}
	
	
//How many player objects should there be and of what type?
	
//Proper Human or Computer player is initialized based on people data


//Are the number of players correct?
//Is Player 0 human and the others computer (you don’t need to test all of them)?
//Is the player at the correct start location?
//Is the players name and color correct?

	
//Deck of all cards is created (composed of rooms, weapons, and people)	
//How many cards are in the deck? Are they represented properly?


	
	//What are the requirements for the Solution?
	//The solution to the game is dealt
	
	
//What are the requirements for a correct deal?
	
//All cards should be dealt.
//The other cards are dealt to the players.
	
	
//All players should have roughly the same number of cards
	
//The same card should not be given to >1 player
	
//Deck size could easily change (e.g., we could add more weapons). So try to avoid hard-coding # of cards/player.


	
	

	
	

	
	

	
	


}
