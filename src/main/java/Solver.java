import java.util.Iterator;

import com.google.common.base.Preconditions;

final class Solver implements Iterator<Board.Solution> {
	enum Step {
		OK, NO_MORE, SOLUTION
	};

	private final Board board;
	private int currentRow;
	private Board.Solution nextSolution;

	Solver(int size) {
		this(new Board(size));
	}

	Solver(Board board) {
		int firstEmptyRow = 0;
		while (firstEmptyRow < board.size() && board.hasQueenInRow(firstEmptyRow)) {
			firstEmptyRow += 1;
		}

		for (int i = firstEmptyRow; i < board.size(); ++i) {
			Preconditions.checkArgument(board.hasQueenInRow(i) == false);
		}

		this.board = board;
		this.currentRow = firstEmptyRow;
	}

	Step doStep() {
		if (currentRow == board.size()) {
			return tryStepBack();
		}

		for (int col = 0; col < board.size(); ++col) {
			if (!board.addQueen(currentRow, col)) {
				continue;
			}

			currentRow += 1;
			return isSolution() ? Step.SOLUTION : Step.OK;
		}

		return tryStepBack();
	}

	private Step tryStepBack() {
		while (currentRow > 0) {
			currentRow -= 1;
			int currentCol = board.getQueenCol(currentRow);
			board.removeQueen(currentRow);
			for (int col = currentCol + 1; col < board.size(); ++col) {
				if (board.addQueen(currentRow, col)) {
					assert !isSolution();
					currentRow += 1;
					return Step.OK;
				}
			}
		}
		return Step.NO_MORE;
	}

	private boolean isSolution() {
		return currentRow == board.size() && !board.hasColinearQueens();
	}

	@Override
	public String toString() {
		return board.getSolution().toString();
	}

	@Override
	public boolean hasNext() {
		if (nextSolution != null) {
			return true;
		}

		while (true) {
			switch (doStep()) {
			case OK:
				continue;
			case SOLUTION:
				nextSolution = board.getSolution();
				return true;
			case NO_MORE:
				return false;
			}
		}
	}

	@Override
	public Board.Solution next() {
		Board.Solution result = nextSolution;
		nextSolution = null;
		return result;
	}
}
