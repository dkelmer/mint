import java.util.Arrays;

public class AnnealP {

	static boolean DEBUG = true;
	
	double N = 3;
	boolean EXCHANGE_FLAG = false;
	int NUM_THREADS = 200;
	DenominationP best;
	double bestScore;
	
	public AnnealP(double n) {
		N = n;
		EXCHANGE_FLAG = false;
	}

	public AnnealP(double n, boolean exchange) {
		N = n;
		this.EXCHANGE_FLAG = exchange;
	}
	
	public double acceptanceProbability(double energy, double newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}

	void process() throws InterruptedException {
		long startTime = System.currentTimeMillis();

		double temp = 10000; // changed to 100000 from 1 million
		double coolingRate = 0.003;

		// Initialize initial solution
		DenominationP currentSolution = new DenominationP(EXCHANGE_FLAG);
		currentSolution.run();

		if (DEBUG) {
			System.out.println("Annealing Parallel: Initial solution score: " + currentSolution.score(N));
			System.out.println("	Initial Result: " + Arrays.toString(currentSolution.coinsExact));
		}
		

		// Set as current best
		best = currentSolution;
		bestScore = currentSolution.score(N);

		// Loop until system has cooled
		while (temp > 1) {
			//System.out.println("HERE! temp = " + temp);
			// System.out.println("curr best: " + Arrays.toString(best.coins));
			int c = 0;
			Thread[] threads = new Thread[NUM_THREADS]; //we execute these.
			DenominationP[] neighbors = new DenominationP[NUM_THREADS];
			
			for (c = 0; c < NUM_THREADS; c++) { // create neighbors
				DenominationP newSolution = null;
				if (temp < 10) {
					newSolution = new DenominationP(currentSolution.generateNeighbor(1), EXCHANGE_FLAG);
				} else {
					newSolution = new DenominationP(currentSolution.generateNeighbor(), EXCHANGE_FLAG);
				}
				neighbors[c] = newSolution;
				threads[c] = new Thread(newSolution);
			}

			for (c = 0; c < NUM_THREADS; c++) { //init each thread. (call run() for each thread).
				threads[c].start();
				threads[c].join();
			}

			for (c = 0; c < NUM_THREADS; c++) { //do comparison for each result.
				// Get energy of solutions
				DenominationP newSolution = neighbors[c];
				double currentEnergy = currentSolution.score(N);
				double neighbourEnergy = newSolution.score(N);

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
