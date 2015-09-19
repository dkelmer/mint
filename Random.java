import java.util.Arrays;

public class Random {

	static int N = 100;

	public static void main(String[] args) {
		// uncomment when ready
		// N = Integer.parseInt(args[0]);

		double temp = 1000000;
		double coolingRate = 0.00002;
		Denomination best = new Denomination();

		System.out.println("Random: Initial solution score: " + best.score(N));
		System.out.println("Initial Result: " + Arrays.toString(best.coins));

		while (temp > 1) {
			// System.out.println(count);
			Denomination curr = new Denomination();
			if (curr.score(N) < best.score(N)) {
				best = curr;
			}
			temp *= 1 - coolingRate;
		}
		System.out.println("Final solution distance: " + best.score(N));
		System.out.println("Tour: " + Arrays.toString(best.coins));
	}

}
