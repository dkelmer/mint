
public class Driver {
	static int N;
	static boolean exchange;
	public static void main(String[] args) throws InterruptedException {
		//N = Integer.parseInt(args[0]);
		N = 2;
		exchange = true;
		Random r = new Random(N, exchange);
		r.process();
		Anneal a = new Anneal(N, exchange);
		a.process();
		AnnealNotP anp = new AnnealNotP(N, exchange);
		anp.process();
		AnnealP ap = new AnnealP(N, exchange);
		ap.process();
	}
}
