package heaps;

import java.util.Random;
import java.util.UUID;

import javax.swing.JOptionPane;

import heaps.util.HeapToStrings;
import heaps.validate.MinHeapValidator;
import timing.Ticker;

public class MinHeap<T extends Comparable<T>> implements PriorityQueue<T> {

	private Decreaser<T>[] array;
	private int size;
	private final Ticker ticker;

	/**
	 * I've implemented this for you.  We create an array
	 *   with sufficient space to accommodate maxSize elements.
	 *   Remember that we are not using element 0, so the array has
	 *   to be one larger than usual.
	 * @param maxSize
	 */
	@SuppressWarnings("unchecked")
	public MinHeap(int maxSize, Ticker ticker) {
		this.array = new Decreaser[maxSize+1];
		this.size = 0;
		this.ticker = ticker;
	}

	//
	// Here begin the methods described in lecture
	//

	/**
	 * Insert a new thing into the heap.  As discussed in lecture, it
	 *   belongs at the end of objects already in the array.  You can avoid
	 *   doing work in this method by observing, as in lecture, that
	 *   inserting into the heap is reducible to calling decrease on the
	 *   newly inserted element.
	 *   
	 *   This method returns a Decreaser instance, which for the inserted
	 *   thing, tracks the thing itself, the location where the thing lives
	 *   in the heap array, and a reference back to MinHeap so it can call
	 *   decrease(int loc) when necessary.
	 */
	public Decreaser<T> insert(T thing) {
		//
		// Below we create the "handle" through which the value of
		//    the contained item can be decreased.
		// VERY IMPORTANT!
		//    The Decreaser object contains the current location
		//    of the item in the heap array.  Initially it's ++size,
		//    as shown below.  The size is increased by 1, and that's
		//    were you should store ans in the heap array.
		//
		//    If and when the element there changes location in the heap
		//    array, the .loc field of the Decreaser must change to reflect
		//    that.
		//
		ticker.tick();
		Decreaser<T> ans = new Decreaser<T>(thing, this, ++size);
		//
		// You have to now put ans into the heap array
		//   Recall in class we reduced insert to decrease
		//
		// add the new decreaser to the array
		array[++size] = ans;
		//make sure that the value is less than its parent, it has experienced a "decrease" from infinity
		this.decrease(++size);
		//
		++size;
		return ans;
	}
	

	/**
	 * This method responds to an element in the heap decreasing in
	 * value.   As described in lecture, that element might have to swap
	 * its way up the tree so that the heap property is maintained.
	 * 
	 * This method can be called from within this class, in response
	 *   to an insert.  Or it can be called from a Decreaser.
	 *   The information needed to call this method is the current location
	 *   of the heap element (index into the array) whose value has decreased.
	 *   
	 * Really important!   If this method changes the location of elements in
	 *   the array, then the loc field within those elements must be modified 
	 *   too.  For example, if a Decreaser d is currently at location 100,
	 *   then d.loc == 100.  If this method moves that element d to
	 *   location 50, then this method must set d.loc = 50.
	 *   
	 * In my solution, I made sure the above happens by writing a method
	 *    moveItem(int from, int to)
	 * which moves the Decreaser from index "from" to index "to" and, when
	 * done, sets array[to].loc = to
	 *   
	 * This method is missing the "public" keyword so that it
	 *   is only callable within this package.
	 * @param loc position in the array where the element has been
	 *     decreased in value
	 */
	void decrease(int loc) {
		int i = loc; //index in the array how I will move through the array
		T child = array[loc].getValue();
		T parent = array[loc/2].getValue();
		ticker.tick();
		while(i>1 && parent.compareTo(child) >0){//while moving to the top of the heap and the parent is greater than
			ticker.tick();
			//the child (min heap violated)
			//switch the two 	//change the .loc of each
			Decreaser<T> holding = new Decreaser<T>(child, this, i);
			array[i] = array[i/2];
			array[i].loc = i/2;
			array[i/2] = holding;
			array[i/2].loc = i/2;

			//set i to the new loc of the new parent
			i = i/2;

			//recalculate child and parent
			child= array[loc].getValue();
			parent = array[loc/2].getValue();
		}



	}

	/**
	 * suggested method from java doc, simplifies the process of 
	 * moving tree elements around
	 * @param to where the tree element is to be placed
	 * @param from the current location of the element in the tree
	 */
	public void moveItem(int to, int from){
		array[to] = array[from];
		array[to].loc = to;
	}
	
	/**
	 * Described in lecture, this method will return a minimum element from
	 *    the heap.  The hole that is created is handled as described in
	 *    lecture.
	 *    This method should call heapify to make sure the heap property is
	 *    maintained at the root node (index 1 into the array).
	 */
	public T extractMin() {
		//base case, there is nothing in the heap
		ticker.tick();
		if(size<1){
			throw new IllegalArgumentException("Heap has nothing to extract");
		}
		else{
			T ans = array[1].getValue();
			//
			// There is effectively a hole at the root, at location 1 now.
			//    Fix up the heap as described in lecture.
			//    Be sure to store null in an array slot if it is no longer
			//      part of the active heap
			//
			// move the last element to the top of the heap
			array[1] = array[size];
			array[size] = null;
			//update the size of the heap (it's now missing an element)
			--size;
			//call heapify to allow the root to float down if its not the least element
			heapify(1);
			//return the min value (stored above)
			return ans;
		}

	}

	/**
	 * As described in lecture, this method looks at a parent and its two 
	 *   children, imposing the heap property on them by perhaps swapping
	 *   the parent with the lesser of the two children.  The child thus
	 *   affected must be heapified itself by a recursive call.
	 * @param where the index into the array where the parent lives
	 */
	private void heapify(int where) {
		//
		// As described in lecture
		//  FIXME
		//where you are in the heap;
		ticker.tick();
		int index =1;
		//values of the l (loc=2*index) and right child (loc=2*index+1)
		T parent = array[index].getValue();
		T lChild = array[2*index].getValue();
		T rChild = array[2*index+1].getValue();
		//boolean to keep track of which child won smallest
		boolean r = false;
		T smallest =null; //place holder to keep track of the smallest element in the comparison
		//if the left child is an element in the heap and it's less than the parent
		if(2*index < size && lChild.compareTo(parent)<0){
			smallest = lChild;
		}
		else{
			smallest = parent;
		}
		//then compare the smallest to the right child
		if(rChild.compareTo(smallest)<0){
			smallest = rChild;
			r = true;
		}
	
		//if the parent is found to not be the smallest element and the smallest is the, left child
		//swap the parent and the smallest element
		if(smallest.compareTo(parent)!=0 && r == false){
			Decreaser<T> holding = new Decreaser<T>(smallest, this, index*2);
			array[index*2] = array[index];
			array[index*2].loc = index*2;
			array[index] = holding;
			array[index].loc = index;
			//recursively call heapify
			//ARE THESE TICKS NECESSARY??
			ticker.tick();
			this.heapify(2*index);
		}
		//if the parent is found to not be the smallest element and the smallest is the, left child
		//swap the parent and the smallest element
		//ARE THESE TICKS NECESSARY??
		if(smallest.compareTo(parent)!=0 && r == true){
			Decreaser<T> holding = new Decreaser<T>(smallest, this, index*2+1);
			array[index*2+1] = array[index];
			array[index*2+1].loc = index*2+1;
			array[index] = holding;
			array[index].loc = index;
			//recursively call heapify
			this.heapify(2*index+1);
			ticker.tick();
		}
		
	}

	/**
	 * Does the heap contain anything currently?
	 * I implemented this for you.  Really, no need to thank me!
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	//
	// End of methods described in lecture
	//

	//
	// The methods that follow are necessary for the debugging
	//   infrastructure.
	//
	/**
	 * This method would normally not be present, but it allows
	 *   our consistency checkers to see if your heap is in good shape.
	 * @param loc the location
	 * @return the value currently stored at the location
	 */
	public T peek(int loc) {
		if (array[loc] == null)
			return null;
		else return array[loc].getValue();
	}

	/**
	 * Return the loc information from the Decreaser stored at loc.  They
	 *   should agree.  This method is used by the heap validator.
	 * @param loc
	 * @return the Decreaser's view of where it is stored
	 */
	public int getLoc(int loc) {
		return array[loc].loc;
	}

	public int size() {
		return this.size;
	}

	public int capacity() {
		return this.array.length-1;
	}


	/**
	 * The commented out code shows you the contents of the array,
	 *   but the call to HeapToStrings.toTree(this) makes a much nicer
	 *   output.
	 */
	public String toString() {
		//		String ans = "";
		//		for (int i=1; i <= size; ++i) {
		//			ans = ans + i + " " + array[i] + "\n";
		//		}
		//		return ans;
		return HeapToStrings.toTree(this);
	}

	/**
	 * This is not the unit test, but you can run this as a Java Application
	 * and it will insert and extract 100 elements into the heap, printing
	 * the heap each time it inserts.
	 * @param args
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "You are welcome to run this, but be sure also to run the TestMinHeap JUnit test");
		MinHeap<Integer> h = new MinHeap<Integer>(500, new Ticker());
		MinHeapValidator<Integer> v = new MinHeapValidator<Integer>(h);
		Random r = new Random();
		for (int i=0; i < 100; ++i) {
			v.check();
			h.insert(r.nextInt(1000));
			v.check();
			System.out.println(HeapToStrings.toTree(h));
			//System.out.println("heap is " + h);
		}
		while (!h.isEmpty()) {
			int next = h.extractMin();
			System.out.println("Got " + next);
		}
	}


}
