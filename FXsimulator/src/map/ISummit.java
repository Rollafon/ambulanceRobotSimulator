package map;

public interface ISummit {
	
	// Used when creating edges
	public void setEnd(IEdge e);
	
	// Getters
	public String getName();
	public int getLength();
	public IEdge[] getEnds();
	public IEdge getOtherEnd(IEdge e);
	
	// Used when browsing the graph to print it
	public SummitColor getColor();
	public void setColor(SummitColor c);
	
	public boolean equals(Object o);
	public int hashCode();

	public boolean isObjective();
	public void setObjective(boolean b);
	
}
