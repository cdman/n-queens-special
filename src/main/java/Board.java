import java.util.Arrays;
import java.util.BitSet;

import com.google.common.base.Preconditions;

final class Board {
	private final int size, lastIndex;
	private final int[] colPosition;
	private final BitSet colOccupied, diagonalOccupied, counterDiagonalOccupied;

	Board(int size) {
		Preconditions.checkArgument(size >= 0);
		Preconditions.checkArgument(2 * (long) size < Integer.MAX_VALUE);
		this.size = size;
		this.lastIndex = size - 1;
		this.colPosition = new int[size];
		Arrays.fill(this.colPosition, -1);
		this.colOccupied = new BitSet(size);
		this.diagonalOccupied = new BitSet(2 * size);
		this.counterDiagonalOccupied = new BitSet(2 * size);
	}

	boolean addQueen(int row, int col) {
		Preconditions.checkArgument(colPosition[row] < 0);
		if (colOccupied.get(col)) {
			return false;
		}
		int diagonalIndex = getDiagonalIndex(row, col);
		if (diagonalOccupied.get(diagonalIndex)) {
			return false;
		}
		int counterDiagonalIndex = getCounterDiagonalIndex(row, col);
		if (counterDiagonalOccupied.get(counterDiagonalIndex)) {
			return false;
		}
		colPosition[row] = col;
		colOccupied.set(col);
		diagonalOccupied.set(diagonalIndex);
		counterDiagonalOccupied.set(counterDiagonalIndex);
		return true;
	}

	void removeQueen(int row) {
		int col = colPosition[row];
		Preconditions.checkArgument(col >= 0);
		colPosition[row] = -1;
		colOccupied.clear(col);
		diagonalOccupied.clear(getDiagonalIndex(row, col));
		counterDiagonalOccupied.clear(getCounterDiagonalIndex(row, col));
	}

	private int getDiagonalIndex(int row, int col) {
		if (row >= col) {
			return row - col;
		} else {
			return col - row + lastIndex;
		}
	}

	private int getCounterDiagonalIndex(int row, int col) {
		int counterDiagonalCol = lastIndex - row;
		if (col <= counterDiagonalCol) {
			return counterDiagonalCol - col;
		} else {
			int counterDiagonalRow = lastIndex - col;
			assert counterDiagonalRow < row;
			return lastIndex + (row - counterDiagonalRow);
		}
	}

	int size() {
		return size;
	}

	boolean hasQueenInRow(int row) {
		return colPosition[row] >= 0;
	}
	
	int getQueenCol(int row) {
		assert hasQueenInRow(row);
		int col = colPosition[row];
		return col;
	}

	boolean hasColinearQueens() {
		for (int row1 = 0; row1 < size - 1; ++row1) {
			for (int row2 = row1 + 1; row2 < size; ++row2) {
				int deltaRow = row2 - row1;
				int deltaCol = getQueenCol(row2) - getQueenCol(row1);

				int row = row2 + deltaRow, col = getQueenCol(row2) + deltaCol;
				while (row < size && col >= 0 && col < size) {
					if (getQueenCol(row) == col) {
						// the queens from row1, row2 and row are on the same line
						return true;
					}
					row += deltaRow;
					col += deltaCol;
				}
			}
		}
		return false;
	}

	final Solution getSolution() {
		return new Solution(this);
	}

	final static class Solution {
		private final int size;
		private final int[] colPosition;

		private Solution(Board board) {
			this.size = board.size;
			this.colPosition = new int[size];
			System.arraycopy(board.colPosition, 0, colPosition, 0, size);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(size * (size + 1));
			for (int row = 0; row < size; ++row) {
				for (int col = 0; col < size; ++col) {
					builder.append(col == colPosition[row] ? 'X' : '.');
				}
				builder.append('\n');
			}
			return builder.toString();
		}
	}
}
