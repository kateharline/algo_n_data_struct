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
							ticker.tick();
							kSplit[i][j] = input[j+(i*n/K)];
							//returns an integer, capture this integer
							System.out.println(""+kSplit[i].length);
						}
						ticker.tick();
						//recursively calls mergesort on these smaller arrays
						kwaymergesort(K, kSplit[i], ticker);
					}
					Integer[][] kmerges = merge(kSplit, ticker, K);
					//conduct final merge on the last two split arrays
					Integer[] kmerged = smallMerge(kmerges[0], kmerges[1], ticker);
					return kmerged;
		}
		//compare lowest nodes and merge
		
		//eventually assign to array containing all inputs
	}
	public static Integer[][] merge(Integer[][] ksplit, Ticker ticker, int K){
		int rowLength = ksplit[0].length;
		Integer[][] kmerged = new Integer[K/2][rowLength*2];
		for(int i=0; i<K/2; i++){
			kmerged[i] = smallMerge(ksplit[i], ksplit[i+2], ticker);
		}
		return kmerged;
	}
	
	public static Integer[] smallMerge(Integer[] input1, Integer[] input2, Ticker ticker){
		int n = input1.length;
		Integer[] ans = new Integer[n*2];
		//merge karrays back together
		//go through the karrays that exist
		for(int i=0; i<n*2; ++i){ 
			ticker.tick();
			int amove = 0;
			int bmove = 0;
			//find the pair elements and compare
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
			else if(amove>n && bmove <n){
				ticker.tick();
				ans[i] =input2[bmove];
				++bmove;
			}
			//out of bounds input 2, fill in input 1
			else if(amove <n && bmove >n){
				ticker.tick();
				ans[i] = input1[amove];
				++amove;
			}
		}
		return ans;
	}
}
