package map;

/**
 * Represent the roads of the map
 * 
 * @author Romain LAFON
 */
public interface ISummit {

	/**
	 * Getter on the name of the summit
	 * 
	 * @return the name of the road represented by this summit
	 */
	public String getName();

	/**
	 * Getter on the length of the summit
	 * 
	 * @return the length of the summit
	 */
	public double getLength();

	/**
	 * Getter on the ends of the summit
	 * 
	 * @return an array of two elements containing both ends of the summit
	 */
	public IEdge[] getEnds();

	/**
	 * Getter of the other end of the summit
	 * 
	 * @param e:
	 *            the end of the summit that we have
	 * @return the other end of the summit than e. If e is not an end of the summit,
	 *         returns the first summit end to have been added
	 */
	public IEdge getOtherEnd(IEdge e);

	/**
	 * Put a edge to end the summit
	 * 
	 * @param e:
	 *            the edge that ends the summit
	 */
	public void setEnd(IEdge e);

	/**
	 * Generate a hashcode for the summit
	 * 
	 * @return an integer representing the hashcode
	 */
	public int hashCode();

	/**
	 * Say if the road is equal to another object
	 * 
	 * @param o:
	 *            the object to compare
	 * @return true if the specified object is also a summit and has the same name,
	 *         or false otherwise
	 */
	public boolean equals(Object o);

	/**
	 * Say if there is a victim on the summit
	 * 
	 * @return true if there a victim, or false otherwise
	 */
	public boolean isObjective();

	/**
	 * If b is true, put a victim on the summit, else remove the victim of the
	 * summit
	 * 
	 * @param b:
	 *            boolean saying if there is a victim or not on the summit
	 */
	public void setObjective(boolean b);

	/**
	 * Say if the summit is an hospital
	 * 
	 * @return true if the summit is an hospital, or false otherwise
	 */
	public boolean isHospital();

	/**
	 * Compare the ends of the summit to the ends of the other summit
	 * 
	 * @param other:
	 *            the other summit to compare
	 * @return true if both summits have the same ends (even in a different order)
	 *         or false otherwise
	 */
	public boolean asSameEnds(ISummit other);
}
