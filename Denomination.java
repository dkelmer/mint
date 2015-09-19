import java.util.Arrays;

public class Denomination {
	static int NUM_PENCES = 240;
	int[] coins;
	int[] best;

	public Denomination() {
		this.coins = generateRand();
		best = new int[NUM_PENCES];
		dp();
	}

	public Denomination(int[] c) {
		this.coins = c;
		best = new int[NUM_PENCES];
		dp();
	}

	public void changeCoins(int[] newC) {
		this.coins = newC;
		dp();
	}

	private int[] generateRand() {
		int[] result = { 1, 0, 0, 0, 0, 0, 0 };
		for (int i = 1; i < result.length; i++)
			result[i] = generateCoin();
		return result;
	}
	
	private int generateCoin() {
		return 2 + (int) (Math.random() * 238); //value form 2 to 239.
	}
	
	//pick a random denomination we have and add 1 to it.
	public int[] generateNeighbor() {
		int[] result = new int[coins.length];
		for (int i = 0; i < result.length; i++) result[i] = coins[i];
		int rand = 1+(int)(Math.random()*(coins.length-1)); //cant modify the first coin (has value 1)
		result[rand] += (int)(Math.random()*5 + 1) * (Math.random()>0.5? 1 : -1); //add/subtract by some val in (-5,5)
		if (result[rand] >= 239 || result[rand] <= 1) result[rand] = generateCoin(); //reroll coin if value above 239
		return result;
	}

	// test
	private void setBestAllTwos() {
		for (int i = 1; i < NUM_PENCES; i++) {
			best[i] = 2;
		}
		best[0] = 0;
	}

	private void dp() {
		for (int i = 0; i < NUM_PENCES; i++)
			best[i] = 0;
		for (int i = 0; i < coins.length; i++) {
			best[coins[i]] = 1;
		}
		best[0] = 0;
		// System.out.println(Arrays.toString(best));
		for (int i = 1; i < best.length; i++) {
			int currBest = Integer.MAX_VALUE;
			if (best[i] != 0)
				continue;
			for (int j = 0; j < coins.length; j++) {
				int currCoin = coins[j];
				if (i <= currCoin)
					continue;
				if (best[i - currCoin] + 1 < currBest) {
					currBest = best[i - currCoin] + 1;
				}
			}
			best[i] = currBest;
		}
	}

	public int score(int N) {
		int result = 0;
		for (int i = 1; i < best.length; i++) {
			result += i % 5 == 0 ? best[i] * N : best[i];
		}
		return result;
	}
}
