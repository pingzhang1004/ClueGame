package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;


class BoardTestsExp {

	TestBoard board;

	@BeforeEach //run before each test
	void setUp() {
		board = new TestBoard();
	}


	//test the creation of adjacency lists for a 4x4 board, including:
	@Test
	void testAdjacency() {

		//test the creation of adjacency lists for the top left corner (i.e., location [0][0])
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell>  testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());

		//test the creation of adjacency lists for bottom right corner (i.e., location [3][3])
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());

		//test the creation of adjacency lists for a right edge (e.g., location [1][3])
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(3, testList.size());

		///test the creation of adjacency lists for a left edge (e.g., location [3][0])
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
		Assert.assertEquals(2, testList.size());

	}


	//	Methods (minimum 5) to test target creation on a 4x4 board (see examples below)
	
	
	//Test for behavior on empty 4x4 board.?????
	
	
	//Test for behavior with at least one cell being flagged as occupied.  A player cannot move into an occupied cell.
	@Test
	void testCellOccupied() {
		//set up occupied cells.
		board.getCell(0, 2).setOccupied(true);
		
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell>  targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		
		//Assert.assertFalse(targets.contains(board.getCell(0, 2)));		
		
	}
	

	//Test for behavior with at least one cell being flagged as a room. A player used up all movement points upon entering a room.
	void testCellFlaggedRoom() {
		//set up flagged room.
		board.getCell(0, 3).setIsRoom(true);
		
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell>  targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		
		//used up all movement points upon entering a room
		//Assert.assertFalse(targets.contains(board.getCell(0, 3)));		
		
	}
	
	
	
	
	@Test
	void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell>  targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}

	// Target test with rooms and occupied
	@Test
	void testTargetsMixed() {
		
		//set up occupied cells.
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell>  targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		
	}
	


}
