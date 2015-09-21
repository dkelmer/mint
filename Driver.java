
public class Driver {

	static int N;
	static boolean exchange;
	public static void main(String[] args) {
		//N = Integer.parseInt(args[0]);
		N = 2;
		exchange = false;
		Random r = new Random(N, exchange);
		r.process();
		Anneal a = new Anneal(N, exchange);
		a.process();
		AnnealNotP anp = new AnnealNotP(N, exchange);
		anp.process();
	}

}
