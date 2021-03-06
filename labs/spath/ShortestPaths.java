package spath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import heaps.Decreaser;
import heaps.MinHeap;
import spath.graphs.DirectedGraph;
import spath.graphs.Edge;
import spath.graphs.Vertex;
import timing.Ticker;
import spath.VertexAndDist;


// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
public class ShortestPaths {
	private final static Integer inf = Integer.MAX_VALUE;
	private HashMap<Vertex, Decreaser<VertexAndDist>> map;
	private HashMap<Vertex, Edge> toEdge;
	private Map<Edge, Integer> weights;
	private Vertex startVertex;
	private final DirectedGraph g;

	
	//
	// constructor
	//
	public ShortestPaths(DirectedGraph g, Vertex startVertex, Map<Edge,Integer> weights) {

		this.map         = new HashMap<Vertex, Decreaser<VertexAndDist>>();
		this.toEdge      = new HashMap<Vertex, Edge>();
		this.weights     = weights;
		this.startVertex = startVertex;
		this.g           = g;
	
		
	}
	
	//
	// this method does all the real work
	//
	public void run() {
		Ticker ticker = new Ticker();
		MinHeap<VertexAndDist> pq = new MinHeap<VertexAndDist>(30000, ticker);

		//
		// Initially all vertices are placed in the heap
		//   infinitely far away from the start vertex
		//
		
		for (Vertex v : g.vertices()) {
			ticker.tick();
			toEdge.put(v, null);
			VertexAndDist a = new VertexAndDist(v, inf);
			Decreaser<VertexAndDist> d = pq.insert(a);
			map.put(v, d);
		}


		//
		// Now we decrease the start node's distance from
		//   itself to 0.
		// Note how we look up the decreaser using the map...
		// 
		ticker.tick();
		Decreaser<VertexAndDist> startVertDist = map.get(startVertex);
		//
		// and then decrease it using the Decreaser handle
		
		//
		ticker.tick();
		startVertDist.decrease(startVertDist.getValue().sameVertexNewDistance(0));
//		toEdge.put(startVertDist.getValue().getVertex(), null);
		//all distances are from the start node
		//take the first node out, and save it
		
		while(!pq.isEmpty()){
			ticker.tick();
			VertexAndDist minVertex = pq.extractMin();
			Iterable<Edge> nextSucs = minVertex.getVertex().edgesFrom();
			for (Edge e : nextSucs){
				ticker.tick();
				//look up decreaser of the vertex to which start is connected p.649
				Decreaser<VertexAndDist> succ = map.get(e.to);	
				//relax node if necessary
				int relax = minVertex.getDistance() + weights.get(e);
				if( succ.getValue().getDistance() > relax){
					VertexAndDist newVertDist = succ.getValue().sameVertexNewDistance(relax);
					succ.decrease(newVertDist);
					toEdge.put(e.to, e);
					ticker.tick(2);
				}
				ticker.tick();
			}
		}
		
		//relax neighbors--they may need to update distance from start due to shorter path through extracted node
		//relax(u, v, w) v.d is shortest path estimate, v.pred is v's predecessor
		//if v.d > u.d + w(u,v)--weight from u to v
			//v.d = u.d + w(u,v)
			//v.pred = u
		
		//Dijkstra(G, w, s) 
			//initialize single source
			//S= empty set (where the values will be stored
			//Q = G.vertexes
			//while Q.not empty (check minheap)
				//u = extractMin(Q)
				//S = S u {u}, add vertex to the set
				//for each vertex, relax it, update v.d and v.pred if you can
			//

	}

	/**
	 * Return a List of Edges forming a shortest path from the
	 *    startVertex to the specified endVertex.  Do this by tracing
	 *    backwards from the endVertex, using the map you maintain
	 *    during the shortest path algorithm that indicates which
	 *    Edge is used to reach a Vertex on a shortest path from the
	 *    startVertex.
	 * @param endVertex 
	 * @return
	 */
	public LinkedList<Edge> returnPath(Vertex endVertex) {
		LinkedList<Edge> path = new LinkedList<Edge>();
		//add last vertex to the list first
		Vertex at = endVertex;
		while (!at.equals(startVertex)) {
			Edge edge = toEdge.get(at);
			path.addFirst(edge);
			at = edge.from;
		}
		return path;
	}
	
	/**
	 * Return the length of all shortest paths.  This method
	 *    is completed for you, using your solution to returnPath.
	 * @param endVertex
	 * @return
	 */
	public int returnValue(Vertex endVertex) {
		LinkedList<Edge> path = returnPath(endVertex);
		int pathValue = 0;
		for(Edge e : path) {
			pathValue += weights.get(e);
		}
		
		return pathValue;
		
	}
}
