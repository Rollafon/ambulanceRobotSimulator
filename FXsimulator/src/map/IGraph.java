package map;

import java.util.List;

/**
 * This structure allows to stock a set of summits and edges
 * 
 * @author Romain LAFON
 */
public interface IGraph {

	/**
	 * Add a summit (road representation) to the graph
	 * 
	 * @param s:
	 *            the summit to be added
	 */
	public void addSummit(ISummit s);

	/**
	 * Add an edge to the graph
	 * 
	 * @param e:
	 *            the edge to be added
	 */
	public void addEdge(IEdge e);

	/**
	 * Getter on the list of edges of the graph
	 * 
	 * @return the edge list contained in the graph
	 */
	public List<IEdge> getEdgeList();

	/**
	 * Getter on the list of summits of the graph
	 * 
	 * @return the summit list contained int the graph
	 */
	public List<ISummit> getSummitList();

}
