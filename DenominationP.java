import java.util.Arrays;

//Runnable -> has method run() which does the DP.
//Constructor does NOT perform DP, only sets denomination count.

public class DenominationP implements Runnable {
	static int NUM_PENCES = 240;
	int[] coinsExact;
	int[] coinsExchange;
	int[] bestExact;
	int[] bestExchange;
	boolean exchangeFlag;

	public DenominationP(boolean exchangeFlag) {
		this.coinsExact = generateRand();
		this.exchangeFlag = exchangeFlag;
		bestExact = new int[NUM_PENCES];
	}

	public DenominationP(int[] c, boolean exchangeFlag) {
		this.coinsExact = c;
		this.exchangeFlag = exchangeFlag;
		bestExact = new int[NUM_PENCES];
	}

	public void changeCoins(int[] newC) {
		this.coinsExact = newC;
		exactDp();
	}

	private int[] generateRand() {
		int[] result = { 1, 0, 0, 0, 0, 0, 0 };
		for (int i = 1; i < result.length; i++)
			result[i] = generateCoin(); //TODO: get 6 unique numbers
		return result;
	}
	
	private int generateCoin() {
		return 2 + (int) (Math.random() * 238); //value form 2 to 239.
	}
	
	//pick a random denomination we have and add 1 to it.
	public int[] generateNeighbor() {
		return generateNeighbor(20);
	}
	
	//pick a random denomination we have and vary it by "range".
		public int[] generateNeighbor(int range) {
			int[] result = new int[coinsExact.length];
			for (int i = 0; i < result.length; i++) result[i] = coinsExact[i];
			int rand = 1+(int)(Math.random()*(coinsExact.length-1)); //cant modify the first coin (has value 1)
			int delta = (int)(Math.random()*range + 1) * (Math.random()>0.5? 1 : -1); // get delta bw (-5,5)
			result[rand] += delta; //change it!
			if (result[rand] >= 239 || result[rand] <= 1) result[rand] = generateCoin(); //reroll coin if value above 239
			return result;
		}

	private void exactDp() {
		for (int i = 0; i < NUM_PENCES; i++)
			bestExact[i] = 0;
		for (int i = 0; i < coinsExact.length; i++) {
			bestExact[coinsExact[i]] = 1;
		}
		bestExact[0] = 0;
		// System.out.println(Arrays.toString(best));
		for (int i = 1; i < bestExact.length; i++) {
			int currBest = Integer.MAX_VALUE;
			if (bestExact[i] != 0)
				continue; //?
			for (int j = 0; j < coinsExact.length; j++) {
				int currCoin = coinsExact[j];
				if (i <= currCoin)
					continue;
				if (bestExact[i - currCoin] + 1 < currBest) {
					currBest = bestExact[i - currCoin] + 1;
				}
			}
			bestExact[i] = currBest;
		}
	}
	
	private void exchangeDp() {
		//initialize whole array to infinity 
		for (int i = 0; i < NUM_PENCES; i++) {
			bestExchange[i] = Integer.MAX_VALUE;
		}
		
		//set 240,0 to zero
		bestExchange[NUM_PENCES] = 0;
		bestExchange[0] = 0;
		
		//set all denominations to 1
		for(int denomination : coinsExact) {
			bestExchange[denomination] = 1;
		}
		
		//start filling in dp from the end
		for(int i = NUM_PENCES-1; i >= 1; i--) {
			int min = Math.min(bestExact[i], bestExact[240-i]);
			for (int denomination : coinsExact) {
				if(i + denomination <= 240) {
					if (bestExchange[i+denomination] + 1 < min) {
						min = bestExchange[i+denomination] + 1;
					}
				}
			}
			bestExchange[i] = min;
		}		
	}

	public int score(double N) {
		int result = 0;
		if(exchangeFlag) {
			for (int i = 1; i < bestExchange.length; i++) {
				result += i % 5 == 0 ? bestExchange[i] * N : bestExchange[i];
			}
		}
		else {
			for (int i = 1; i < bestExact.length; i++) {
				result += i % 5 == 0 ? bestExact[i] * N : bestExact[i];
			}
		}
		return result;
	}
	
	public String printDp() {
		// TODO Auto-generated method stub
		if(exchangeFlag) {
			return Arrays.toString(bestExchange);
		}
		else {
			return Arrays.toString(bestExact);
		}
	}
	
	public static void main(String[] args) {
		Denomination d = new Denomination(true);
		System.out.println(Arrays.toString(d.coinsExact));
		System.out.println(Arrays.toString(d.bestExact));
		System.out.println(Arrays.toString(d.bestExchange));
	}

	@Override
	public void run() {
		exactDp();
		if(exchangeFlag) {
			bestExchange = new int[NUM_PENCES+1];
			exchangeDp();
		}
		
	}
}
