package kwaymergesort;

import java.util.Arrays;

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
					kSplit[i][j] = input[j+(i*n/K)];
					//returns an integer, capture this integer
				}
				ticker.tick();
				//recursively calls mergesort on these smaller arrays
				kSplit[i] = kwaymergesort(K, kSplit[i], ticker);
				
			}
			//merge the arrays together as long as they aren't the last two arrays
			while(kSplit.length > 2 ){
				ticker.tick(2);
				int kRowMergeNum = kSplit.length/2;
				kSplit = merge(kSplit, ticker, kRowMergeNum);
			}
			//conduct final merge on the last two split arrays
			ticker.tick(2);
			Integer[] kmerged = smallMerge(kSplit[0], kSplit[1], ticker);
			return kmerged;
		}
	}
	public static Integer[][] merge(Integer[][] ksplit, Ticker ticker, int rows){
		ticker.tick();
		int rowLength = ksplit[0].length;
		ticker.tick(rows*rowLength*2);
		Integer[][] kmerged = new Integer[rows][rowLength*2];
		ticker.tick();
		int mover = 0;
		for(int i=0; i<rows; i++){
			ticker.tick();
			kmerged[i] = smallMerge(ksplit[mover], ksplit[mover+1], ticker);
			mover =mover+2;
		}
		return kmerged;
	}

	public static Integer[] smallMerge(Integer[] input1, Integer[] input2, Ticker ticker){
		ticker.tick();
		int n = input1.length;
		ticker.tick(n*2);
		Integer[] ans = new Integer[n*2];
		ticker.tick();
		int amove = 0;
		int bmove = 0;
		//merge karrays back together
		//go through the karrays that exist
		for(int i=0; i<n*2; ++i){ 
			ticker.tick();
			//compare elements if neither index is null
			if(amove<n && bmove<n){
				if(input1[amove] <= input2[bmove]){
					ticker.tick();
					ans[i] = input1[amove];
					++amove;
				}
				else if(input1[amove] > input2[bmove]){
					ticker.tick();
					ans[i] =input2[bmove];
					++bmove;
				}
			}
			//out of bounds input 1, fill in input 2 
			else if(amove>=n && bmove <n){
				ticker.tick();
				ans[i] =input2[bmove];
				++bmove;
			}
			//out of bounds input 2, fill in input 1
			else if(amove <n && bmove >=n){
				ticker.tick();
				ans[i] = input1[amove];
				++amove;
			}
		}
		ticker.tick();
		return ans;
	}
}
