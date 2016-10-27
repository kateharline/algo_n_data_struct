package kwaymergesort;

import timing.Ticker;

public class KWayMergeSort {
	
	/**
	 * 
	 * @param K some positive power of 2.
	 * @param input an array of unsorted integers.  Its size is either 1, or some other power of 2 that is at least K
	 * @param ticker call .tick() on this to account for the work you do
	 * @return
	 */
	public static Integer[] kwaymergesort(int K, Integer[] input, Ticker ticker) {
		int n = input.length;
		//allocate array to keep track of split pieces
		ticker.tick(n);
		
		

		// Following just copies the input as the answer
		//
		// You must replace the loop below with code that performs
		// a K-way merge sort, placing the result in ans
		//
		// The web page for this assignment provides more detail.
		//
		// Use the ticker as you normally would, to account for
		// the operations taken to perform the K-way merge sort.
		//
		
		//if n=1 or n=0, "trivially sort"
		if(n == 1){
			ticker.tick();
			return input;
		}
		//otherwise divide into subcollections
		//recursively sort subcollections, merge and sort the subcollections
		//this is a more general sort, n/k k subcollections
		else{
			Integer[][] kSplit = new Integer[K][n/K];
			ticker.tick();
					//assign k arrays to split, keep track of where splits are with double array
					for(int i=0; i<K; i++){
						ticker.tick();
						for(int j=0; j<n/K; j++){
							ticker.tick();
							System.out.println("this is the input " + input[j+(i*n/K)]);
							//System.out.println(j+(i*n/K));
							ticker.tick();
							kSplit[i][j] = input[j+(i*n/K)];
							//returns an integer, capture this integer
							System.out.println(""+kSplit[i].length);
						}
						ticker.tick();
						//recursively calls mergesort on these smaller arrays
						kwaymergesort(K, kSplit[i], ticker);
					}
					Integer[] ans = new Integer[n*2];
					//merge karrays back together
					//go through the karrays that exist
					for(int i=0; i<n*2; ++i){ 
						ticker.tick();
						int amove = 0;
						int bmove = 0;
						//find the pair elements and compare
						if(kSplit[i][amove] <= kSplit[i+2][bmove]){
							ticker.tick();
							ans[i] = kSplit[i][amove];
							++amove;
						}
						else{
							ticker.tick();
							ans[i] =kSplit[n/2+1][bmove];
							++bmove;
						}
					}
					return ans;
		}
		//compare lowest nodes and merge
		
		//eventually assign to array containing all inputs
	}
	
}
