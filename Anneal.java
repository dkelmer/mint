import java.util.Arrays;

public class Anneal {
	
	static boolean DEBUG = true;
	
	double N = 3;
	boolean EXCHANGE_FLAG = false;
	Denomination best;
	double bestScore;

	public Anneal(double n) {
		N = n;
		EXCHANGE_FLAG = false;
	}

	public Anneal(double n, boolean exchange) {
		N = n;
		this.EXCHANGE_FLAG = exchange;
	}

	public double acceptanceProbability(double energy, double newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}

	void process() {
		double temp = 1000000; // changed to 100000 from 1 million
		double coolingRate = 0.00002;

		// Initialize initial solution
		Denomination currentSolution = new Denomination(EXCHANGE_FLAG);
		if (DEBUG) {
			System.out.println("Annealing: Initial solution score: " + currentSolution.score(N));
			System.out.println("	Initial Result: " + Arrays.toString(currentSolution.coinsExact));
		}
		

		// Set as current best
		best = currentSolution;
		bestScore = currentSolution.score(N);

		// Loop until system has cooled
		while (temp > 1) {

			// Create new neighbor
			Denomination newSolution = null;

			if (temp < 10) {
				newSolution = new Denomination(currentSolution.generateNeighbor(3), EXCHANGE_FLAG);
			} else {
				newSolution = new Denomination(currentSolution.generateNeighbor(), EXCHANGE_FLAG);
			}

			// Get energy of solutions
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

			// Cool system
			temp *= 1 - coolingRate;
		}

		if (DEBUG) {
			System.out.println("	Final solution score: " + best.score(N));
			System.out.println("	Coins: " + Arrays.toString(best.coinsExact));
			System.out.println("	DP: " + best.printDp());
		}
	}
}

