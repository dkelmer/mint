import java.util.Arrays;

public class AnnealNeighborsFive {

static boolean DEBUG = true;
	
	double N = 3;
	boolean EXCHANGE_FLAG = false;
	Denomination best;
	long bestScore;
	int NUM_THREADS = 200; //in AnnealNotP it's actually not threads, just neighbors.

	public AnnealNeighborsFive(int n) {
		N = n;
		EXCHANGE_FLAG = false;
	}

	public AnnealNeighborsFive(int n, boolean exchange) {
		N = n;
		this.EXCHANGE_FLAG = exchange;
	}
	
	public double acceptanceProbability(int energy, int newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}
	
	void process() {
		long startTime = System.currentTimeMillis();
		double temp = 10000; // changed to 100000 from 1 million
		double coolingRate = 0.003;

		// Initialize initial solution
		Denomination currentSolution = new Denomination(EXCHANGE_FLAG);
		currentSolution.changeCoins(currentSolution.generateNeighbor(true)); //round to nearest multiple of five

		if (DEBUG) {
			System.out.println("Annealing Neighbors with Multiples of Five: Initial solution score: " + currentSolution.score(N));
			System.out.println("	Initial Result: " + Arrays.toString(currentSolution.coinsExact));
		}
		

		// Set as current best
		best = currentSolution;
		bestScore = currentSolution.score(N);

		// Loop until system has cooled
		while (temp > 1) {
			//System.out.println("HERE! temp = " + temp);
			// System.out.println("curr best: " + Arrays.toString(best.coins));
			Denomination[] neighbors = new Denomination[NUM_THREADS];
			
			for (int c = 0; c < NUM_THREADS; c++) { // create neighbors
				Denomination newSolution = null;
				if (temp < 10) {
					newSolution = new Denomination(currentSolution.generateNeighbor(true), EXCHANGE_FLAG);
				} else {
					newSolution = new Denomination(currentSolution.generateNeighbor(true), EXCHANGE_FLAG);
				}
				neighbors[c] = newSolution;
			}

			for (int c = 0; c < NUM_THREADS; c++) { //do comparison for each result.
				// Get energy of solutions
				Denomination newSolution = neighbors[c];
				int currentEnergy = currentSolution.score(N);
				int neighbourEnergy = newSolution.score(N);

				// Keep track of the best solution found
				if (currentEnergy < bestScore) {
					best = currentSolution;
					bestScore = best.score(N);
				}

				// Decide if we should accept the neighbour
				if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
					currentSolution = newSolution;
				}
			}
			// Cool system
			temp *= 1 - coolingRate;
		}

		if (DEBUG) {
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("	Elapsed Time: " + elapsedTime/1000 + " seconds");
			System.out.println("	Final solution score: " + best.score(N));
			System.out.println("	Result: " + Arrays.toString(best.coinsExact));
			System.out.println("	DP: " + best.printDp());
		}
	
	}

}
