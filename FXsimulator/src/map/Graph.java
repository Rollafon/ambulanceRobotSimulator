package map;

import java.util.LinkedList;
import java.util.List;

/**
 * This structure allows to stock a set of summits and edges
 * 
 * @author Romain LAFON
 */
public class Graph implements IGraph {
	List<ISummit> summitList = new LinkedList<>(); // The list of the summits contained in the graph
	List<IEdge> edgeList = new LinkedList<>(); // The list of the edges contained in the graph

	/**
	 * Add a summit (road representation) to the graph
	 * 
	 * @param s:
	 *            the summit to be added
	 */
	@Override
	public void addSummit(ISummit s) {
		summitList.add(s);
	}

	/**
	 * Add an edge to the graph
	 * 
	 * @param e:
	 *            the edge to be added
	 */
	@Override
	public void addEdge(IEdge e) {
		edgeList.add(e);
	}

	/**
	 * Getter on the list of edges of the graph
	 * 
	 * @return the edge list contained in the graph
	 */
	@Override
	public List<IEdge> getEdgeList() {
		return edgeList;
	}

	/**
	 * Getter on the list of summits of the graph
	 * 
	 * @return the summit list contained int the graph
	 */
	@Override
	public List<ISummit> getSummitList() {
		return summitList;
	}

}
