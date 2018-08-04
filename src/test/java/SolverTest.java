import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class SolverTest {
	@Test
	public void testEmptySolver() {
		Solver solver = new Solver(2);
		assertEquals("..\n..\n", solver.toString());
	}

	@Test
	public void testSolverFirstStepPlacesQueen() {
		Solver solver = new Solver(2);
		assertEquals(Solver.Step.OK, solver.doStep());
		assertEquals("X.\n..\n", solver.toString());
	}

	@Test
	public void testSolverFindsSolutionToBoardSize1() {
		Solver solver = new Solver(1);
		assertEquals(Solver.Step.SOLUTION, solver.doStep());
		assertEquals("X\n", solver.toString());
	}

	@Test
	public void testSolverDoesNotFindSolutionToBoardSize2() {
		Solver solver = new Solver(2);
		assertEquals(Solver.Step.OK, solver.doStep());
		assertEquals("X.\n..\n", solver.toString());
		assertEquals(Solver.Step.OK, solver.doStep());
		assertEquals(".X\n..\n", solver.toString());
		assertEquals(Solver.Step.NO_MORE, solver.doStep());
	}

	private int getSolutionCount(int size) {
		Solver solver = new Solver(size);
		int solutions = 0;
		loop: while (true) {
			switch (solver.doStep()) {
			case OK:
				break;
			case SOLUTION:
				solutions += 1;
				break;
			case NO_MORE:
				break loop;
			}
		}
		return solutions;
	}

	@Test
	public void testFoundSolutionCount() {
		// ref: https://en.wikipedia.org/wiki/Eight_queens_puzzle#Counting_solutions
		assertEquals(1, getSolutionCount(1));
		assertEquals(0, getSolutionCount(2));
		assertEquals(0, getSolutionCount(3));
		assertEquals(2, getSolutionCount(4));
		assertEquals(10, getSolutionCount(5));
	}
}
