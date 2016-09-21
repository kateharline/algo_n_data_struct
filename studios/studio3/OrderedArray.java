package studio3;

public class OrderedArray<T extends Comparable<T>> implements PriorityQueue<T> {

	public T[] array;
	private int size;
	
	@SuppressWarnings("unchecked")
	public OrderedArray(int maxSize) {
		array = (T[]) new Comparable[maxSize];
		size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return(array.length==0);
	}

	@Override
	public void insert(T thing) {
		//you have to move through the whole arrray comparing 
//		if(size==1){
//			array[0] = thing;
//		}
		for(int i=0; i<size; i++){
			if(thing.compareTo(array[i]) < 0){
				size++;
				for(int j=size; j>i; j--){
					array[j] = array[j-1];
					
				}
				array[i]= thing;
				return;
			}
		}
//		while(thing.compareTo(array[i]) >= 0 && i<size){
//			i++;
//		}
//		for(int j=i; j<size; j++){
//			array[j+1] =array[j];
//		}
		
		
	}
	
	@Override
	public T extractMin() {
		//
		// FIXME
		//the min is the first value in the array
		return array[0];
	}

}
