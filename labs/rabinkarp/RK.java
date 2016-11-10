package rabinkarp;

public class RK {
	//a rk has-a window and, string of characters, start
	public int window, numCalls, prevHash;
	public char[] prevChars;
	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements
	//target is string of length s
	//query is string of length q
	//find all occurences of q in s
	//rolling hash computed, so don't match q in all of s, window is q
	//where there aren't q characters in the window, 0 is used
	//first character of window q at index start (start of window) 
	/**
	 * Rabin-Karp string matching for a window of the specified size, constructor?
	 * @param m size of the window
	 */
	public RK(int m) {
		this.window = m;
		numCalls = 0;
		prevChars = new char[m];
		prevHash = 0;
	}

	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return the new hash, with char d added and char m-1 subtracted
	 */
	public int nextCh(char d) {
		//calculate hash of the character based on hashfor calculation
		//exponentiation
		int exp = 1;
		for(int i=0; i<window; i++){
			exp = (exp*31)%511;
		}
		//somehow calculate this
		//h = (h×31 – 31m×c + d) mod 511
		//rolling hash is rolling assignment of prev chars to a char array that you maintain, calc oldHash and then undo/update
		this.prevHash =  ((31*prevHash % 511 - ((exp * prevChars[numCalls % window])%511)%511 + d % 511) % 511);
		//reassign char to its place in the 
		prevChars[numCalls % window] = d;
		++numCalls;
		
		return prevHash;
	}
	

}
