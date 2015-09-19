import java.util.Arrays;

public class Anneal {

	static int N = 100;

	public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}

	public static void main(String[] args) {

		// uncomment when ready.
		// N = Integer.parseInt(args[0]);

		double temp = 1000000;
		double coolingRate = 0.00002;

		// Initialize intial solution
		Denomination currentSolution = new Denomination();
		System.out.println("Annealing: Initial solution score: " + currentSolution.score(N));
		System.out.println("Initial Result: " + Arrays.toString(currentSolution.coins));

		// Set as current best
		Denomination best = currentSolution;

		// Loop until system has cooled
		while (temp > 1) {
			// Create new neighbour
			Denomination newSolution = new Denomination(currentSolution.generateNeighbor());

			// Get energy of solutions
			int currentEnergy = currentSolution.score(N);
			int neighbourEnergy = newSolution.score(N);

			// Decide if we should accept the neighbour
			if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
				currentSolution = newSolution;
			}

			// Keep track of the best solution found
			if (currentSolution.score(N) < best.score(N)) {
				best = currentSolution;
			}
			// Cool system
			temp *= 1 - coolingRate;
		}

		System.out.println("Final solution score: " + best.score(N));
		System.out.println("Result: " + Arrays.toString(best.coins));
	}
}
