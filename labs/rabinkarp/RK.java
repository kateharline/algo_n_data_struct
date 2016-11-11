package rabinkarp;

public class RK {
	//a rk has-a window and, string of characters, start
	public int window, numCalls, prevHash, exp;
	public char[] prevChars;
	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements

	/**
	 * Rabin-Karp string matching for a window of the specified size, constructor?
	 * @param m size of the window
	 */
	public RK(int m) {
		this.window = m;
		numCalls = 0;
		prevChars = new char[m];
		prevHash = 0;
		//exponentiation should only have to do this once for time complexity
		exp = 1;
		for(int i=0; i<window; i++){
			this.exp = (exp*31)%511;
		}
	}

	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return the new hash, with char d added and char m-1 subtracted
	 */
	public int nextCh(char d) {
		//rolling hash is rolling assignment of prev chars to a char array that you maintain, calc oldHash and then undo/update
		char byeChar = prevChars[numCalls % window];
		//reassign char to where char that just left was
		prevChars[numCalls % window] = d;
		++numCalls;
		//calculate hash of the character based on hashfor calculation
		int byebye = 0;
		//when you need to start removing chars, otherwise subtract zero in the beginning
		if(numCalls > window){
			byebye = exp * (byeChar%511) % 511;
		}
		//somehow calculate this
		//h = (h×31 – 31m×c + d) mod 511
		this.prevHash =  ((31*prevHash % 511 - byebye + d % 511) % 511);
		//positive mod
		if(prevHash < 0){
			prevHash = prevHash +511;
		}
		return prevHash;
	}
	

}
