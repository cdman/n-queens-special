import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class SolutionBoardTest {
	@Test
	public void testEmptyBoard() {
		SolutionBoard board = new SolutionBoard(0);
		assertEquals("", board.toString());
		assertEquals(0, board.size());
	}

	@Test
	public void testToString() {
		SolutionBoard board = new SolutionBoard(2);
		board.addQueen(1, 0);
		assertEquals("..\nX.\n", board.toString());
	}

	@Test
	public void testQueensCanNotBeAddedOnTheSameColumn() {
		SolutionBoard board = new SolutionBoard(2);
		board.addQueen(0, 0);
		assertFalse(board.addQueen(1, 0));
		assertEquals("X.\n..\n", board.toString());
	}

	@Test
	public void testQueensCanNotBeAddedOnTheSameDiagonal() {
		SolutionBoard board = new SolutionBoard(2);
		board.addQueen(0, 0);
		assertFalse(board.addQueen(1, 1));
		assertEquals("X.\n..\n", board.toString());
	}

	@Test
	public void testQueensCanNotBeAddedOnTheSameCounterDiagonal() {
		SolutionBoard board = new SolutionBoard(2);
		board.addQueen(0, 1);
		assertFalse(board.addQueen(1, 0));
		assertEquals(".X\n..\n", board.toString());
	}

	@Test
	public void testGetCurrentCol() {
		SolutionBoard board = new SolutionBoard(2);
		board.addQueen(0, 1);
		assertEquals(1, board.getQueenCol(0));
	}

	@Test
	public void testRemoveQueen() {
		SolutionBoard board = new SolutionBoard(2);
		board.addQueen(0, 0);
		board.removeQueen(0);
		assertTrue(board.addQueen(1, 1));
	}

	@Test
	public void testHasColinearQueens() {
		SolutionBoard board = new SolutionBoard(8);
		assertTrue(board.addQueen(0, 1));
		assertTrue(board.addQueen(1, 3));
		assertTrue(board.addQueen(2, 5));
		assertTrue(board.hasColinearQueens());
	}

}
