package studio3;

import java.util.LinkedList;
import java.util.List;

public class UnorderedList<T extends Comparable<T>> implements PriorityQueue<T> {

	public List<T> list;
	
	public UnorderedList() {
		list = new LinkedList<T>();
	}
	
	@Override
	public boolean isEmpty() {
	return list.size()==0;
	}

	@Override
	public void insert(T thing) {
		list.add(thing);
	}

	@Override
	public T extractMin() {
		if(list.isEmpty()){
			return null;
		}
		if(list.size()==1){
			return list.get(0);
		}
		else{
			T min = list.get(0);
			for(int i=0; i<list.size(); i++){
				T newMin = list.get(i);
				int compare = min.compareTo(newMin);
				if(compare > 0){
					min = newMin;
					
				}
				
			}
			list.remove(min);
			return min;
		}
	}

}
