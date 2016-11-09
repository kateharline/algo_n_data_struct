package rabinkarp;

public class RK {
	//a rk has-a window and, string of characters, start
	public int window, numCalls, prevCharHash;
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
		prevCharHash = 0;
		
	}
	

	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return the new hash, with char d added and char m-1 subtracted
	 */
	public int nextCh(char d) {
		//this should call hashfor, but we don't have chars[]?
		//must modify hashfor so time complexity desired is obtained
		//calculate hash of the character based on hashfor calculation
		
	 //hash of the previous character you want to subtract
		int newHash =0;
		//somehow calculate this
		//h = (h×31 – 31m×c + d) mod 511
		//have to cast math.pow to int because it returns a double (won't happen in this case)
		newHash =  ((31*newHash % 511 - ((int)Math.pow(31, window) * prevCharHash)%511 + d % 511) % 511);
		++numCalls;
		prevCharHash = d % 511;
		return newHash;
	}
	
//	/**
//	 * helper method that calculates the hash for chars in the window
//	 * @param chars the string
//	 * @param start where the window begins in the chars
//	 * @param m the size of the window
//	 * @return the hash for the given window of chars[]
//	 */
//	private static int hashFor(char[] chars, int start, int m) {
//		   int ans = 0;
//		   for (int i=start; i < start+m; ++i) {
//		      int val = 0;
//		      //
//		      // Within the bounds of the chars array?
//		      //
//		      if (i >= 0 && i < chars.length) 
//		         val = chars[i];                        // then use the character value there
//		      ans = (31*ans % 511 + val % 511)% 511;
//		   }
//		   return ans;
//		}

}
