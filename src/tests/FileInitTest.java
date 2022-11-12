/*
 * CSCI 306 Section B
 * C14A-2 Clue Init 1 (Clue Pair)
 * Author: Yonghao Li; Ping Zhang
 * 10/09/2022
 */

package tests;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;
public class FileInitTest {

	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 22;

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	// To ensure data is correctly loaded, test retrieving a few rooms
	// test several entries including first and last in file
	@Test
	public void testRoomLoaded() {
		assertEquals(LEGEND_SIZE, board.getRoomMap().size());
		assertEquals("Balcony", board.getRoom('B').getName());
		assertEquals("Play Room", board.getRoom('P').getName());
		assertEquals("Home Office", board.getRoom('H').getName());
		assertEquals("Living Room", board.getRoom('I').getName());
		assertEquals("Reception", board.getRoom('R').getName());
		assertEquals("Walkway", board.getRoom('W').getName());
	}
	
	//ensure the correct number of rows/columns have been read
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// three cells that are not a doorway.
	// These cells are purple on the spreadsheet
	@Test
	public void testDoorDirections() {
		// Fitness Room
		BoardCell cell = board.getCell(13, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		// Balcony
		cell = board.getCell(13, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		// Home Office
		cell = board.getCell(5, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		// Laundry
		cell = board.getCell(6, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(2, 7);
		assertFalse(cell.isDoorway());
		cell = board.getCell(9, 14);
		assertFalse(cell.isDoorway());
		cell = board.getCell(19, 10);
		assertFalse(cell.isDoorway());
	}
	
	// check that the correct number of doors have been loaded.
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(13, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// check some of the cells to ensure they have the correct initial
		BoardCell cell = board.getCell(20, 1);
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		Room room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Fitness Room");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		cell = board.getCell(4, 21);
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Garage");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		// check that rooms have the proper label cell
		cell = board.getCell(8, 2);
		assertTrue(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Movie Room");
		assertTrue(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		cell = board.getCell(8, 18);
		assertTrue(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Laundry");
		assertTrue(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		// check that rooms have the proper center cell
		cell = board.getCell(2, 3);
		assertFalse(cell.isRoomLabel());
		assertTrue(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Home Office");
		assertFalse(room.getLabelCell() == cell);
		assertTrue(room.getCenterCell() == cell);
		
		cell = board.getCell(17, 20);
		assertFalse(cell.isRoomLabel());
		assertTrue(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Balcony");
		assertFalse(room.getLabelCell() == cell);
		assertTrue(room.getCenterCell() == cell);
		
		// this is a secret passage test
		cell = board.getCell(0, 0);
		assertTrue(cell.getSecretPassage() == 'B');
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Home Office");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		cell = board.getCell(22, 0);
		assertTrue(cell.getSecretPassage() == 'G');
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Fitness Room");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		// test a walkway
		cell = board.getCell(5, 20);
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Walkway");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		cell = board.getCell(22, 17);
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Walkway");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		// test a closet
		cell = board.getCell(0, 5);
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);
		
		cell = board.getCell(13, 21);
		assertFalse(cell.isRoomLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(room.getLabelCell() == cell);
		assertFalse(room.getCenterCell() == cell);

	}
}
