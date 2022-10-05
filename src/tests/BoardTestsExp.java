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



}
