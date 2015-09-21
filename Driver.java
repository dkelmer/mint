import java.util.Arrays;

public class Driver {
	static int N;
	static boolean exchange;
	static boolean DEBUG_MODE = true; //turn this off for turnin
	
	//sample args: java Driver 5 exchange
	public static void main(String[] args) throws InterruptedException {
		//N = Integer.parseInt(args[0]);
		//exchange = args[1].equals("exchange")? true : false; //if 2nd param isn't "exchange" then it runs exact.
		N = 2;
		exchange = true;
		
		Random.DEBUG = DEBUG_MODE;
		Anneal.DEBUG = DEBUG_MODE;
		AnnealNotP.DEBUG = DEBUG_MODE;
		AnnealP.DEBUG = DEBUG_MODE;
		
		Denomination best;
		long bestScore;
		
		//run each one and compare it to best.
		Random r = new Random(N, exchange); r.process();
		best = r.best; bestScore = r.bestScore;
		
		Anneal a = new Anneal(N, exchange); a.process();
		if (a.bestScore < bestScore) {
			best = a.best; bestScore = a.bestScore;
		}
		
		AnnealNotP anp = new AnnealNotP(N, exchange); anp.process();
		if (anp.bestScore < bestScore) {
			best = anp.best; bestScore = anp.bestScore;
		}
		
//		AnnealP ap = new AnnealP(N, exchange);
//		ap.process();
//		if (ap.bestScore < bestScore) {
//			best = ap.best; bestScore = ap.bestScore;
//		}
//		
		printRes(best.coinsExact);
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
