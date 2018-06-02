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

	@Override
	public void addSummit(ISummit s) {
		summitList.add(s);
	}

	@Override
	public void addEdge(IEdge e) {
		edgeList.add(e);
	}

	@Override
	public List<IEdge> getEdgeList() {
		return edgeList;
	}

	@Override
	public List<ISummit> getSummitList() {
		return summitList;
	}

}
