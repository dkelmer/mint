import java.util.Arrays;

public class Driver {
	static double N;
	static boolean exchange;
	static boolean DEBUG_MODE = false; //turn this off for turnin
	static String winningStrategy = null;
	
	//sample args: java Driver 5 exchange
	public static void main(String[] args) throws InterruptedException {
		
		long startTime = System.currentTimeMillis();
		
		N = Double.parseDouble(args[0]);
		exchange = args[1].equals("exchange")? true : false; //if 2nd param isn't "exchange" then it runs exact.
		
		//set debug mode
		Random.DEBUG = DEBUG_MODE;
		RandomFive.DEBUG = DEBUG_MODE;
		Anneal.DEBUG = DEBUG_MODE;
		AnnealNeighbors.DEBUG = DEBUG_MODE;
		AnnealP.DEBUG = DEBUG_MODE;
		AnnealNeighborsFive.DEBUG = DEBUG_MODE;
		
		
		//for(int j = 0; j < 30; j++) {
						
		//store the best.
		Denomination best = null;
		double bestScore = Integer.MAX_VALUE;
		
		//run each one and compare it to best.
		Random r = new Random(N, exchange); r.process();
		best = r.best; bestScore = r.bestScore;
		
		RandomFive rf = new RandomFive(N, exchange); 
		rf.process();
		if (rf.bestScore < bestScore) {
			best = rf.best; bestScore = rf.bestScore; winningStrategy = "random five";
		}
		
		for(int i = 0; i < 3; i++) {
			AnnealNeighbors anp = new AnnealNeighbors(N, exchange); 
			anp.process();
			if (anp.bestScore < bestScore) {
				best = anp.best; bestScore = anp.bestScore; winningStrategy = "anneal neighbors";
			}
			
			AnnealNeighborsFive anpff = new AnnealNeighborsFive(N, exchange); 
			anpff.process();
			if (anpff.bestScore < bestScore) {
				best = anpff.best; bestScore = anpff.bestScore; winningStrategy = "anneal neighbors five";
			}
		}
		
		
		//for (int i = 0; i < 5; i++) {
		int c = 0;
		while(true) {
			
			Anneal a = new Anneal(N, exchange); 
			a.process();
			if (a.bestScore < bestScore) {
				best = a.best; bestScore = a.bestScore; winningStrategy = "anneal";
			}
/*			
			AnnealNeighbors anp = new AnnealNeighbors(N, exchange); 
			anp.process();
			if (anp.bestScore < bestScore) {
				best = anp.best; bestScore = anp.bestScore; winningStrategy = "anneal neighbors";
			}
			
			AnnealNeighborsFive anpff = new AnnealNeighborsFive(N, exchange); 
			anpff.process();
			if (anpff.bestScore < bestScore) {
				best = anpff.best; bestScore = anpff.bestScore; winningStrategy = "anneal neighbors five";
			}
*/			
			long currTime = System.currentTimeMillis();
			if(DEBUG_MODE) {
				System.out.println("LOOP #" + c + "ran in " + (currTime-startTime)/1000 + " seconds");
			}
			if(currTime - startTime > 100000) {
				break;
			}
			c++;
				
		}
		if (DEBUG_MODE) {
			System.out.println("N = " + N);
			System.out.println(bestScore);
			System.out.println(winningStrategy);
			System.out.println("num cycles = " + c);
		}
		printRes(best.coinsExact);
		//}
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
