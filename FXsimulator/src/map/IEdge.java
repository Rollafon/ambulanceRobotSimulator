package map;

import java.util.NavigableSet;

public interface IEdge {

	public int getNbSummits();
	public NavigableSet<ISummit> getSummits();
	public EdgeType getType();
	public Coordinates getCoordinates(); 
	public boolean equals(Object o);
	public int hashCode();
	
}
