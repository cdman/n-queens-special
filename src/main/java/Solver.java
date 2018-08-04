
final class Solver {
	enum Step {
		OK, NO_MORE, SOLUTION
	};

	private final SolutionBoard board;
	private int currentRow;

	Solver(int size) {
		this.board = new SolutionBoard(size);
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
		return currentRow == board.size();
	}

	@Override
	public String toString() {
		return board.toString();
	}
}
