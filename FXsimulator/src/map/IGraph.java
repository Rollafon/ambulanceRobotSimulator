package map;

import java.util.List;

public interface IGraph {

	// Methods used to create the graph
	public void addSummit(ISummit s);
	public void addEdge(IEdge e);
	public List<IEdge> getEdgeList();
	public List<ISummit> getSummitList();
	
}
