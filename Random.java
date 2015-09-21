import java.util.Arrays;

public class Random {

	int N = 100;
	Denomination best;
	boolean EXCHANGE_FLAG;
	
	public Random(int n) {
		N = n;
	}
	
	public Random(int n, boolean exchange) {
		N = n;
		this.EXCHANGE_FLAG = exchange;
	}

	void process() {
		// uncomment when ready
		// N = Integer.parseInt(args[0]);

		double temp = 1000000;
		double coolingRate = 0.00002;
		best = new Denomination(EXCHANGE_FLAG);

		System.out.println("Random: Initial solution score: " + best.score(N));
		System.out.println("Initial Result: " + Arrays.toString(best.coinsExact));

		while (temp > 1) {
			// System.out.println(count);
			Denomination curr = new Denomination(false);
			if (curr.score(N) < best.score(N)) {
				best = curr;
			}
			temp *= 1 - coolingRate;
		}
		System.out.println("Final solution distance: " + best.score(N));
		System.out.println("Tour: " + Arrays.toString(best.coinsExact));
	}
}
