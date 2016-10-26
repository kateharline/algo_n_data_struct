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
			return input;
		}
		//otherwise divide into subcollections
		//recursively sort subcollections, merge and sort the subcollections
		//this is a more general sort, n/k k subcollections
		else{
			//allocate array to keep track of split pieces
			Integer[][] kSplit = new Integer[K][n/K];
			ticker.tick();
					//assign k arrays to split, keep track of where splits are with double array
					for(int i=0; i<K; i++){
						ticker.tick();
						for(int j=0; j<n/K; j++){
							ticker.tick();
							//recursively calls mergesort on these smaller arrays
							kSplit[K][j] = input[j+i];
							//returns an integer, capture this integer
							kwaymergesort(K, kSplit[K], ticker);
						}
					}
		}
		//compare lowest nodes and merge
		Integer[] ans = new Integer[n];
		//eventually assign to array containing all inputs
		for (int i=0; i < n; ++i) {
			int smallest = 0;
			ticker.tick();
			
			
			ans[i] = smallest;
			
		}
		return ans;
	}

}
