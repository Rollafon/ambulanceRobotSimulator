package map;

import java.util.NavigableSet;

/**
 * This class is used to represent the points where more than two roads are
 * adjacent coming
 * 
 * @author Romain LAFON
 */
public class Edge implements IEdge {
	private int nbSummits; // The number of roads touching the edge
	private NavigableSet<ISummit> summitSet; // The set of roads touching the edge
	private EdgeType type; // The type of the edge (CROSS or INTERSECTION)
	private Coordinates coordinates; // The coordinates of the edge

	/**
	 * Constructor
	 * 
	 * @param nbSummits:
	 *            the number of roads touching the edge
	 * @param summitSet:
	 *            the set of roads touching the edge
	 * @param type:
	 *            the type of the edge (CROSS or INTERSECTION)
	 * @param c:
	 *            the coordinates of the edge
	 */
	public Edge(int nbSummits, NavigableSet<ISummit> summitSet, EdgeType type, Coordinates c) {
		this.nbSummits = nbSummits;
		this.summitSet = summitSet;
		this.type = type;
		this.coordinates = c;
		for (ISummit s : this.summitSet)
			s.setEnd(this);
	}

	/**
	 * Getter on the number of neighboring summits
	 * 
	 * @return the number of neighboring summits
	 */
	@Override
	public int getNbSummits() {
		return nbSummits;
	}

	/**
	 * Getter on the set of neighboring summits
	 * 
	 * @return the set of neighboring summits
	 */
	@Override
	public NavigableSet<ISummit> getSummits() {
		return summitSet;
	}

	/**
	 * Getter on the type of an edge
	 * 
	 * @return the type of the edge
	 */
	@Override
	public EdgeType getType() {
		return type;
	}

	/**
	 * Getter on the coordinates of an edge
	 * 
	 * @return the coordinates of an edge
	 */
	@Override
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Generate a hashcode for the edge
	 * 
	 * @return an integer representing the hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nbSummits;
		result = prime * result + ((summitSet == null) ? 0 : summitSet.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Says if an edge is equals to another object or not
	 * 
	 * @param o:
	 *            the object to compare
	 * @return true if the specified object is also an edge, it has the same number
	 *         of neighboring summits, the same neighboring summits, the same type
	 *         and the same coordinates, or false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (nbSummits != other.nbSummits)
			return false;
		if (summitSet == null) {
			if (other.summitSet != null)
				return false;
		} else if (!summitSet.containsAll(other.summitSet) || !other.summitSet.containsAll(summitSet))
			return false;
		if (type != other.type)
			return false;
		if (!coordinates.equals(other.getCoordinates()))
			return false;
		return true;
	}

	/**
	 * Describe an Edge by a String
	 * 
	 * @return the string composed of the name of all neighboring summits, finished
	 *         by the coordinates
	 */
	public String toString() {
		String s = "";
		for (ISummit sum : summitSet)
			s = s + sum.toString() + ", ";
		s = s + "(" + coordinates.getX() + ", " + coordinates.getY() + ")";
		return s;
	}
}
