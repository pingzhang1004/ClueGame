package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		//These cells are LIGHT ORANGE on the planning spreadsheet
		// First, the HomeOffice that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(2, 3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(17, 20)));

		// now test the Living Room (note not marked since multiple test here)
		testList = board.getAdjList(18, 14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(13, 14)));
		assertTrue(testList.contains(board.getCell(21, 17)));

		// one more room, the Balcony
		testList = board.getAdjList(17, 20);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(13, 17)));
		assertTrue(testList.contains(board.getCell(2, 3)));
	}


	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(6, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(6, 6)));
		assertTrue(testList.contains(board.getCell(8, 3)));

		testList = board.getAdjList(18, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 3)));
		assertTrue(testList.contains(board.getCell(19, 3)));
		assertTrue(testList.contains(board.getCell(18, 6)));

		testList = board.getAdjList(5, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(5, 16)));
		assertTrue(testList.contains(board.getCell(5, 18)));
		assertTrue(testList.contains(board.getCell(2, 19)));
		assertTrue(testList.contains(board.getCell(6, 17)));
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(22, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(21, 3)));

		// Test near a door but not adjacent
		testList = board.getAdjList(11, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(11, 2)));
		assertTrue(testList.contains(board.getCell(11, 3)));
		assertTrue(testList.contains(board.getCell(12, 3)));

		// Test adjacent to walkways
		testList = board.getAdjList(13, 9);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 8)));
		assertTrue(testList.contains(board.getCell(13, 10)));
		assertTrue(testList.contains(board.getCell(12, 9)));
		assertTrue(testList.contains(board.getCell(14, 9)));

		// Test next to closet
		testList = board.getAdjList(7,14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 14)));
		assertTrue(testList.contains(board.getCell(8, 14)));
		assertTrue(testList.contains(board.getCell(7, 15)));

	}


	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInPlayRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(18, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(18, 3)));	

		// test a roll of 3
		board.calcTargets(board.getCell(18, 6), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(18, 3)));	
		assertTrue(targets.contains(board.getCell(16, 3)));
		assertTrue(targets.contains(board.getCell(20, 3)));
		assertTrue(targets.contains(board.getCell(11, 6)));
		assertTrue(targets.contains(board.getCell(12, 5)));	
		assertTrue(targets.contains(board.getCell(12, 7)));
		assertTrue(targets.contains(board.getCell(13, 4)));
		assertTrue(targets.contains(board.getCell(13, 8)));	
		

		// test a roll of 4
		board.calcTargets(board.getCell(18, 6), 4);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(15, 15)));	
	}

	@Test
	public void testTargetsInBalcony() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(13, 17)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 20), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 17)));
		assertTrue(targets.contains(board.getCell(14, 17)));	
		assertTrue(targets.contains(board.getCell(13, 16)));

		// test a roll of 4
		board.calcTargets(board.getCell(17, 20), 4);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(18, 16)));	
		assertTrue(targets.contains(board.getCell(16, 16)));
		assertTrue(targets.contains(board.getCell(2, 2)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(8, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(5, 17)));
		assertTrue(targets.contains(board.getCell(6, 16)));	
		assertTrue(targets.contains(board.getCell(6, 18)));	
		assertTrue(targets.contains(board.getCell(8, 19)));	

		// test a roll of 3
		board.calcTargets(board.getCell(8, 17), 3);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));	

		// test a roll of 4
		board.calcTargets(board.getCell(8, 17), 4);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(6, 11), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(6, 12)));
		assertTrue(targets.contains(board.getCell(6, 10)));	
		assertTrue(targets.contains(board.getCell(5, 11)));	

		// test a roll of 3
		board.calcTargets(board.getCell(11, 2), 3);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));	

		// test a roll of 4
		board.calcTargets(board.getCell(11, 2), 4);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 6)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(17, 20)));
		assertTrue(targets.contains(board.getCell(16, 17)));	
		assertTrue(targets.contains(board.getCell(18, 17)));	

		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));	

		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
	}

	
	// test to make sure occupied locations do not cause problems
	@Test
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(12, 6).setOccupied(true);
		board.calcTargets(board.getCell(11, 6), 1);
		board.getCell(12, 6).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 6)));
		assertTrue(targets.contains(board.getCell(12, 6)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		assertFalse( targets.contains(board.getCell(11, 7))) ;

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(20, 17).setOccupied(true);
		board.getCell(13, 16).setOccupied(true);
		board.calcTargets(board.getCell(12, 17), 2);
		board.getCell(20, 17).setOccupied(false);
		board.getCell(13, 16).setOccupied(false);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 16)));	
		assertTrue(targets.contains(board.getCell(12, 20)));	

		// check leaving a room with a blocked doorway
		board.getCell(5, 17).setOccupied(true);
		board.calcTargets(board.getCell(2, 19), 2);
		board.getCell(5, 17).setOccupied(false);
		targets= board.getTargets();
		//did not caculate by hand
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(8, 19)));	
		assertTrue(targets.contains(board.getCell(8, 15)));

	}
}
