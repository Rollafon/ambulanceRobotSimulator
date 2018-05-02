package map;

import java.util.NavigableSet;

public class Edge implements IEdge {
	private int nbSummits;
	private NavigableSet<ISummit> summitSet;
	private EdgeType type;
	private Coordinates coordinates;

	public Edge(int nbSummits, NavigableSet<ISummit> summitSet, EdgeType type, Coordinates c) {
		this.nbSummits = nbSummits;
		this.summitSet = summitSet;
		this.type = type;
		this.coordinates = c;
		for (ISummit s : this.summitSet)
			s.setEnd(this);
	}

	@Override
	public int getNbSummits() {
		return nbSummits;
	}

	@Override
	public NavigableSet<ISummit> getSummits() {
		return summitSet;
	}

	@Override
	public EdgeType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nbSummits;
		result = prime * result + ((summitSet == null) ? 0 : summitSet.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

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

	@Override
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public String toString() {
		String s = "";
		for (ISummit sum: summitSet)
			s = s + sum.toString() + ", ";
		s = s + "(" + coordinates.getX() + ", " + coordinates.getY() + ")";
		return s;
	}
}
