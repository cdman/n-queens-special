import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

public class Main {
	private static final Board.Solution POISON_PILL = new Board(0).getSolution();
	
	public static void main(String[] args) throws Exception {
		final int size = Integer.parseInt(args[0]);

		final int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores);
		BlockingQueue<Board.Solution> results = new ArrayBlockingQueue<>(10);

		for (int col = 0; col < size; ++col) {
			executor.execute(createTask(size, col, results));
		}

		Thread resultPrinter = new Thread(getResultPrinter(results));
		resultPrinter.start();

		executor.shutdown();
		executor.awaitTermination(10000 * 365, TimeUnit.DAYS);
		results.add(POISON_PILL);
		resultPrinter.join();
	}

	private static Runnable createTask(int size, int col, BlockingQueue<Board.Solution> results) {
		return new Runnable() {
			@Override
			public void run() {
				Board board = new Board(size);
				boolean added = board.addQueen(0, col);
				Preconditions.checkArgument(added);

				Solver solver = new Solver(board);
				while (solver.hasNext()) {
					try {
						results.put(solver.next());
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		};
	}

	private static Runnable getResultPrinter(BlockingQueue<Board.Solution> results) {
		return new Runnable() {
			@Override
			public void run() {
				int solutionCount = 0;
				while (true) {
					Board.Solution solution;
					try {
						solution = results.take();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					if (solution == POISON_PILL) {
						break;
					}
					solutionCount += 1;
					System.out.println("Solution " + solutionCount + ":");
					System.out.println(solution);
				}
			}
		};
	}
}
