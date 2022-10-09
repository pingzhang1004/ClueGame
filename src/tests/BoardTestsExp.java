/*
 * CSCI 306 Section B
 * C13A-2 Clue Paths 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/10/2022
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;


public class BoardTestsExp {

	TestBoard board;

	@BeforeEach //run before each test
	public void setUp() {
		board = new TestBoard();
	}


	//test the creation of adjacency lists for a 4x4 board, including:	
	@Test
	public void testAdjacency_1() {
		// test the creation of adjacency lists for the top left corner --- [0,0]
		TestBoardCell cell = board.getCell(0, 0);
		cell.setAdjList(board);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}	

	@Test
	public void testAdjacency_2() {
		// test the creation of adjacency lists for bottom right corner --- [3,3]
		TestBoardCell cell = board.getCell(3, 3);
		cell.setAdjList(board);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency_3() {
		// test the creation of adjacency lists for a right edge --- [1,3]
		TestBoardCell cell = board.getCell(1, 3);
		cell.setAdjList(board);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(3, testList.size());
	}

	@Test
	public void testAdjacency_4() {
		// test the creation of adjacency lists for a middle of grid --- [1,1]
		TestBoardCell cell = board.getCell(1, 1);
		cell.setAdjList(board);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertEquals(4, testList.size());
	}

	@Test
	public void testAdjacency_5() {
		// test the creation of adjacency lists for a left edge --- [2,0]
		TestBoardCell cell = board.getCell(2, 0);
		cell.setAdjList(board);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertEquals(3, testList.size());

	}


	// Methods to test target creation on a 4x4 board

	// Test target creation behavior on empty 4x4 board.
	// Test target creation for cell(0,0) and pathlength = 1
	@Test
	public void testTargetsEmpty_1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.setStartCellVisited(cell);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}

	// Test target creation for cell(3,3) and pathlength = 2
	@Test
	public void testTargetsEmpty_2() {		
		TestBoardCell cell = board.getCell(3, 3);
		board.setStartCellVisited(cell);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
	}

	// Test target creation for cell(0,0) and pathlength = 3
	@Test
	public void testTargetsEmpty_3() {		
		TestBoardCell cell = board.getCell(0, 0);
		board.setStartCellVisited(cell);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
	}

	// Test target creation for cell(0,0) and pathlength = 3
	@Test
	public void testTargetsEmpty_4() {		
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
	}

	// Test target creation for cell(1,3) and pathlength = 4
	@Test
	public void testTargetsEmpty_5() {		
		TestBoardCell cell = board.getCell(1, 3);
		board.setStartCellVisited(cell);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}

	// Test target creation for cell(1,1) and pathlength = 5
	@Test
	public void testTargetsEmpty_6() {		
		TestBoardCell cell = board.getCell(1, 1);
		board.setStartCellVisited(cell);
		board.calcTargets(cell, 5);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
	}

	// Test target creation for cell(2,0) and pathlength = 6
	@Test
	public void testTargetsEmpty_7() {		
		TestBoardCell cell = board.getCell(2, 0);
		board.setStartCellVisited(cell);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
	}


	// Test for behavior with at least one cell being flagged as occupied.
	//start Cell[2,1] and pathlength = 3 
	// Target test with occupied --- [2,2]
	@Test
	public void testTargetsOccupied_1() {
		board.getCell(2, 2).setOccupied(true);		
		TestBoardCell cell = board.getCell(2, 1);
		board.setStartCellVisited(cell);			
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));


	}

	//start Cell[2,1] and pathlength = 2 
	// Target test with occupied --- [2,2] and [1,0]
	@Test
	public void testTargetsOccupied_2() {
		board.getCell(2, 2).setOccupied(true);
		board.getCell(1, 0).setOccupied(true);
		TestBoardCell cell = board.getCell(2, 1);
		board.setStartCellVisited(cell);		
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
	}

	// Test for behavior with at least one cell being flagged as a room.
	//start Cell[2,2] and pathlength = 1 
	// Target test with rooms -- [1,2]
	@Test
	public void testTargetsIsRoom_1() {
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(2, 2);
		board.setStartCellVisited(cell);		
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
	}

	//start Cell[2,2] and pathlength = 3
	// Target test with rooms -- [3,3] and [1,2]
	@Test
	public void testTargetsIsRoom_2() {
		board.getCell(3, 3).setIsRoom(true);
		board.getCell(1, 3).setIsRoom(true);
		TestBoardCell cell = board.getCell(2, 2);
		board.setStartCellVisited(cell);		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}

	//start Cell[3,3] and pathlength = 2
	// Target test with rooms -- [3,2] and occupied --- [2,1]
	@Test
	public void testTargetsMixed_1() {

		board.getCell(2, 1).setOccupied(true);
		board.getCell(3, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(3, 3);
		board.setStartCellVisited(cell);		
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
	}

	//start Cell[1,0] and pathlength = 3
	// Target test with rooms -- [3,1] and occupied --- [2,0],
	@Test
	public void testTargetsMixed_2() {
		board.getCell(2, 0).setOccupied(true);
		board.getCell(3, 0).setIsRoom(true);
		TestBoardCell cell = board.getCell(1, 0);
		board.setStartCellVisited(cell);		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
	}


}
