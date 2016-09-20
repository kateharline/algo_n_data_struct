package studio1;

import timing.ExecuteAlgorithm;
import timing.Ticker;
import timing.quiet.QuietAlgorithm;
import timing.utils.GenSizes;
import timing.utils.IntArrayGenerator;


public class ProbA extends QuietAlgorithm {

	protected Ticker ticker;
	protected long   sum;

	public ProbA() {

	}

	/**
	 * Begin with an array of two elements.
	 */
	@Override
	public void reset(Ticker ticker) {
		this.ticker = ticker;
	}

	/**
	 * As a "quiet" algorithm, all we care about is the
	 * parameter n.
	 */
	@Override
	public void run() {
		int j = 1;
		while (j < n) {//j depends on n
			int k = n;
			//
			// Here
			ticker.tick();///executes n times
			//				
			while (k > j) {//same number of times as
				k = k - 1;
			}
			j = j + 1;
		}
	}
	
	public String toString() {
		return "Problem A";
	}

	public static void main(String[] args) {
		GenSizes sizes = GenSizes.arithmetic(0, 100, 1);
		ExecuteAlgorithm.timeAlgorithm(
				"ProbA", 
				"studio1.ProbA", 
				new IntArrayGenerator(), 
				sizes
				);	
	}

}
