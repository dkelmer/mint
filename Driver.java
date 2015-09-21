import java.util.Arrays;

public class Driver {
	static int N;
	static boolean exchange;
	static boolean DEBUG_MODE = true; //turn this off for turnin
	
	//sample args: java Driver 5 exchange
	public static void main(String[] args) throws InterruptedException {
		N = 500;
		exchange = false;
		
		//N = Integer.parseInt(args[0]);
		//exchange = args[1].equals("exchange")? true : false; //if 2nd param isn't "exchange" then it runs exact.
		
		
		//set debug mode
		Random.DEBUG = DEBUG_MODE;
		RandomFive.DEBUG = DEBUG_MODE;
		Anneal.DEBUG = DEBUG_MODE;
		AnnealNeighbors.DEBUG = DEBUG_MODE;
		AnnealP.DEBUG = DEBUG_MODE;
		AnnealNeighborsFive.DEBUG = DEBUG_MODE;
		
		//store the best.
		Denomination best;
		long bestScore;
		
		//run each one and compare it to best.
		Random r = new Random(N, exchange); r.process();
		best = r.best; bestScore = r.bestScore;
		
		for (int i = 0; i < 2; i++) {
		RandomFive rf = new RandomFive(N, exchange); rf.process();
		if (rf.bestScore < bestScore) {
			best = rf.best; bestScore = rf.bestScore;
		}
		
		Anneal a = new Anneal(N, exchange); a.process();
		if (a.bestScore < bestScore) {
			best = a.best; bestScore = a.bestScore;
		}
		
		AnnealNeighbors anp = new AnnealNeighbors(N, exchange); anp.process();
		if (anp.bestScore < bestScore) {
			best = anp.best; bestScore = anp.bestScore;
		}
		
		AnnealNeighborsFive anpff = new AnnealNeighborsFive(N, exchange); anpff.process();
		if (anpff.bestScore < bestScore) {
			best = anpff.best; bestScore = anpff.bestScore;
		}
		
		if (DEBUG_MODE) System.out.println(bestScore);
		printRes(best.coinsExact);
		}
	}
	
	public static void printRes(int[] arr) {
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(' ');
		}
		System.out.println(sb);
	}
}
