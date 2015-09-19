import java.util.Arrays;

public class Anneal {

	static double N = 3;

	public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
		if (newEnergy < energy) {
			return 1.0;
		}
		return Math.exp((energy - newEnergy) / temperature);
	}

	public static void main(String[] args) {

		// uncomment when ready.
		//N = Integer.parseInt(args[0]);
		
		System.out.println("args[0] = " + args[0]);

		double temp = 100000; //changed to 100000 from 1 million
		double coolingRate = 0.00002;

		// Initialize initial solution
		Denomination currentSolution = new Denomination();
		System.out.println("Annealing: Initial solution score: " + currentSolution.score(N));
		System.out.println("Initial Result: " + Arrays.toString(currentSolution.coinsExact));

		// Set as current best
		Denomination best = currentSolution;
		long bestScore = currentSolution.score(N);

		// Loop until system has cooled
		while (temp > 1) {
			//System.out.println("HERE! temp = " + temp);
			//System.out.println("curr best: " + Arrays.toString(best.coins));
			int c = 0;
			//while (c < 100) {
				// Create new neighbor
				Denomination newSolution = new Denomination(currentSolution.generateNeighbor());
				
				// Get energy of solutions
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

				c++;
			//}
			// Cool system
			temp *= 1 - coolingRate;
		}

		System.out.println("Final solution score: " + best.score(N));
		System.out.println("Result: " + Arrays.toString(best.coinsExact));
	}
}


/*			Denomination newSolution = null;
if (temp > 800000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(40));
} else if (temp > 500000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(30));
} else if (temp > 200000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(25));
} else if (temp > 100000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(20));
} else if (temp > 50000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(15));
} else if (temp > 10000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(10));
} else if (temp > 5000) {
	newSolution = new Denomination(currentSolution.generateNeighbor(5));
} else {
	newSolution = new Denomination(currentSolution.generateNeighbor(3));
} 

*/
