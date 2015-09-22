import java.util.Arrays;

public class RandomFive {
static boolean DEBUG = true;
	
	double N = 100;
	Denomination best;
	double bestScore;
	boolean EXCHANGE_FLAG;
	
	public RandomFive(double n) {
		N = n;
	}
	
	public RandomFive(double n, boolean exchange) {
		N = n;
		this.EXCHANGE_FLAG = exchange;
	}

	void process() {
		// uncomment when ready
		// N = Integer.parseInt(args[0]);

		double temp = 1000000;
		double coolingRate = 0.00002;
		best = new Denomination(EXCHANGE_FLAG);
		best.changeCoins(best.generateNeighbor(true));
		bestScore = best.score(N);
		
		if (DEBUG) {
			System.out.println("Random Five: Initial solution score: " + best.score(N));
			System.out.println("	Initial Result: " + Arrays.toString(best.coinsExact));
		}
		

		while (temp > 1) {
			// System.out.println(count);
			Denomination curr = new Denomination(EXCHANGE_FLAG);
			curr.changeCoins(curr.generateNeighbor(true)); //round to nearest multiple of five

			double currScore = curr.score(N);
			if (currScore < bestScore) {
				best = curr;
				bestScore = currScore;
			}
			temp *= 1 - coolingRate;
		}
		
		
		if (DEBUG) {
			System.out.println("	Final solution score: " + best.score(N));
			System.out.println("	Coins: " + Arrays.toString(best.coinsExact));
		}
	}
}
