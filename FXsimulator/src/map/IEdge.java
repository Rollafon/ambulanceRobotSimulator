package map;

import java.util.NavigableSet;

/**
 * Interface for edges that are used to represent the points where more than two
 * roads are adjacent
 * 
 * @author Romain LAFON
 *
 */
public interface IEdge {

	/**
	 * Getter on the number of neighboring summits
	 * 
	 * @return the number of neighboring summits
	 */
	public int getNbSummits();

	/**
	 * Getter on the set of neighboring summits
	 * 
	 * @return the set of neighboring summits
	 */
	public NavigableSet<ISummit> getSummits();

	/**
	 * Getter on the type of an edge
	 * 
	 * @return the type of the edge
	 */
	public EdgeType getType();

	/**
	 * Getter on the coordinates of an edge
	 * 
	 * @return the coordinates of an edge
	 */
	public Coordinates getCoordinates();

	/**
	 * Generate a hashcode for the edge
	 * 
	 * @return an integer representing the hashcode
	 */
	public int hashCode();

	/**
	 * Says if an edge is equals to another object or not
	 * 
	 * @param o:
	 *            the object to compare
	 * @return true if the specified object is also an edge, it has the same number
	 *         of neighboring summits, the same neighboring summits, the same type
	 *         and the same coordinates, or false otherwise
	 */
	public boolean equals(Object o);
}
