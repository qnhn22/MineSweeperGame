package minesweeper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MineSweeperBoardTest {
	MineSweeperBoard board;

	@Before
	public void setUp() throws Exception {
		board = new MineSweeperBoard();
	}

	@Test
	public void tesConstructorDefault() {
		assertEquals(3, board.getRows());	
		assertEquals(4, board.getColumns());	
		assertEquals(2, board.getNumMines());	
		assertEquals(-2, board.getCell(0, 0));
		assertEquals(-2, board.getCell(2, 1));
		assertEquals(-1, board.getCell(2, 2));
	}
	
	@Test
	public void testConstructorBeginner() {
		MineSweeperBoard board1 = new MineSweeperBoard(1);
		assertEquals(3, board1.getNumMines());
		assertEquals(5, board1.getRows());
		assertEquals(10, board1.getColumns());
	}
	
	@Test
	public void testConstructorIntermidiate() {
		MineSweeperBoard board2 = new MineSweeperBoard(2);
		assertEquals(15, board2.getNumMines());
		assertEquals(10, board2.getRows());
		assertEquals(15, board2.getColumns());
	}
	
	@Test
	public void testConstructorAdvanced() {
		MineSweeperBoard board3 = new MineSweeperBoard(3);
		assertEquals(45, board3.getNumMines());
		assertEquals(15, board3.getRows());
		assertEquals(20, board3.getColumns());
	}
	
	@Test
	public void testGetCellValid() {
		assertEquals(-1, board.getCell(1, 2));
	}
	
	@Test
	public void testGetCellInvalid() {
		assertEquals(-10, board.getCell(10, 10));
	}
	
	@Test
	public void testNumAdjMines() {
		assertEquals(2, board.numAdjMines(1, 1));
		assertEquals(1, board.numAdjMines(0, 1));
		assertEquals(0, board.numAdjMines(1, 3));
		
	}
	
	@Test 
	public void testUncoverCellMine() {
		assertEquals(MineSweeperBoard.MINE, board.getCell(0, 0));
		board.uncoverCell(0, 0);
		assertEquals(MineSweeperBoard.UNCOVERED_MINE, board.getCell(0, 0));
	}
	
	@Test 
	public void testUncoverCellNotMine() {
		assertEquals(MineSweeperBoard.COVERED_CELL, board.getCell(2, 2));
		board.uncoverCell(2, 2);
		assertEquals(1, board.getCell(2, 2));
		assertEquals(MineSweeperBoard.COVERED_CELL, board.getCell(1, 1));
		board.uncoverCell(1, 1);
		assertEquals(2, board.getCell(1, 1));
	}
	
	@Test 
	public void testFlagCell() {
		board.flagCell(0, 0);
		assertEquals(MineSweeperBoard.FLAGGED_MINE, board.getCell(0, 0));
		board.flagCell(0, 2);
		assertEquals(MineSweeperBoard.FLAG, board.getCell(0, 2));
		board.flagCell(0, 0);
		assertEquals(MineSweeperBoard.MINE, board.getCell(0, 0));
		board.flagCell(0, 2);
		assertEquals(MineSweeperBoard.COVERED_CELL, board.getCell(0, 2));
	}

	@Test
	public void testRevealBoard() {
		board.revealBoard();
		int count = 0;
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getColumns(); col++) {
				if (board.getCell(row, col) == MineSweeperBoard.COVERED_CELL || board.getCell(row, col) == MineSweeperBoard.MINE) {
					count++;
				}
			}
		}
		assertEquals(0, count);
	}
	
	@Test
	public void testGameLost() {
		board.uncoverCell(0, 0);
		assertTrue(board.gameLost());
	}

	@Test
	public void testGameWonTrue() {
		board.flagCell(0, 0);
		board.flagCell(2, 1);
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getColumns(); col++) {
				board.uncoverCell(row, col);
			}
		}
		assertTrue(board.gameWon());
	}
	
	@Test
	// Test when uncovering mine
	public void testGameWonFalse1() {
		board.uncoverCell(0, 0);
		assertFalse(board.gameWon());	
	}
	
	@Test
	// Test when there is a wrong flag 
	public void testGameWonFalse2() {
		board.flagCell(0, 0);
		board.flagCell(2, 1);
		board.flagCell(1, 1);
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getColumns(); col++) {
				board.uncoverCell(row, col);
			}
		}
		assertFalse(board.gameWon());
	}
	
	@Test
	// Test when all non-flagged cells haven't been uncovered
	public void testGameWonFalse3() {
		board.flagCell(0, 0);
		board.flagCell(2, 1);
		board.flagCell(1, 1);
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getColumns(); col++) {
				board.uncoverCell(row, col);
			}
		}
		board.flagCell(1, 1); 
		assertFalse(board.gameWon());
	}
	
}
