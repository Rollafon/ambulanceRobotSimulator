package map;

import java.util.LinkedList;
import java.util.List;

public class Graph implements IGraph {
	List<ISummit> summitList = new LinkedList<>();
	List<IEdge> edgeList = new LinkedList<>();

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
