import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class BoardTest {
	@Test
	public void testEmptyBoard() {
		Board board = new Board(0);
		assertEquals("", board.getSolution().toString());
		assertEquals(0, board.size());
	}

	@Test
	public void testToString() {
		Board board = new Board(2);
		board.addQueen(1, 0);
		assertEquals("..\nX.\n", board.getSolution().toString());
	}

	@Test
	public void testQueensCanNotBeAddedOnTheSameColumn() {
		Board board = new Board(2);
		board.addQueen(0, 0);
		assertFalse(board.addQueen(1, 0));
		assertEquals("X.\n..\n", board.getSolution().toString());
	}

	@Test
	public void testQueensCanNotBeAddedOnTheSameDiagonal() {
		Board board = new Board(2);
		board.addQueen(0, 0);
		assertFalse(board.addQueen(1, 1));
		assertEquals("X.\n..\n", board.getSolution().toString());
	}

	@Test
	public void testQueensCanNotBeAddedOnTheSameCounterDiagonal() {
		Board board = new Board(2);
		board.addQueen(0, 1);
		assertFalse(board.addQueen(1, 0));
		assertEquals(".X\n..\n", board.getSolution().toString());
	}

	@Test
	public void testGetCurrentCol() {
		Board board = new Board(2);
		board.addQueen(0, 1);
		assertEquals(1, board.getQueenCol(0));
	}

	@Test
	public void testRemoveQueen() {
		Board board = new Board(2);
		board.addQueen(0, 0);
		board.removeQueen(0);
		assertTrue(board.addQueen(1, 1));
	}

	@Test
	public void testHasColinearQueens() {
		Board board = new Board(8);
		assertTrue(board.addQueen(0, 1));
		assertTrue(board.addQueen(1, 3));
		assertTrue(board.addQueen(2, 5));
		assertTrue(board.hasColinearQueens());
	}

}
