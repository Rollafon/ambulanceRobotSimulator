package map;

/**
 * Interface representing he roads of the map
 * 
 * @author Romain LAFON
 */
public class Summit implements ISummit, Comparable<Summit> {
	private String name; // The name of the summit/road
	private double length; // The length of the summit/road
	private IEdge e1 = null; // The first end of the road
	private IEdge e2 = null; // The second end of the road
	private boolean objective = false; // True if there is a victim on the summit, false otherwise
	private boolean hospital = false; // True if there is an hospital on the summit, false otherwise

	/**
	 * Constructor (by default, the summit will not be an hospital)
	 * 
	 * @param name:
	 *            the name of the summit/road
	 * @param length:
	 *            the length of the summit/road
	 */
	public Summit(String name, double length) {
		this.name = name;
		this.length = length;
	}

	/**
	 * Alternative constructor
	 * 
	 * @param name:
	 *            the name of the summit/road
	 * @param length:
	 *            the length of the summit/road
	 * @param isHospital:
	 *            true if there is an hospital on the summit, or false otherwise
	 */
	public Summit(String name, double length, boolean isHospital) {
		this.name = name;
		this.length = length;
		this.hospital = isHospital;
	}

	/**
	 * Getter on the name of the summit
	 * 
	 * @return the name of the road represented by this summit
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Getter on the length of the summit
	 * 
	 * @return the length of the summit
	 */
	@Override
	public double getLength() {
		return length;
	}

	/**
	 * Getter on the ends of the summit
	 * 
	 * @return an array of two elements containing both ends of the summit
	 */
	@Override
	public IEdge[] getEnds() {
		IEdge[] res = new IEdge[2];
		res[0] = e1;
		res[1] = e2;
		return res;
	}

	/**
	 * Getter of the other end of the summit
	 * 
	 * @param e:
	 *            the end of the summit that we have
	 * @return the other end of the summit than e. If e is not an end of the summit,
	 *         returns the first summit end to have been added
	 */
	@Override
	public IEdge getOtherEnd(IEdge e) {
		if (e.equals(e1))
			return e2;
		else
			return e1;
	}

	/**
	 * Put a edge to end the summit
	 * 
	 * @param e:
	 *            the edge that ends the summit
	 */
	@Override
	public void setEnd(IEdge e) {
		if (e1 == null)
			e1 = e;
		else
			e2 = e;
	}

	/**
	 * Generate a hashcode for the summit
	 * 
	 * @return an integer representing the hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Says if the road is equal to another object
	 * 
	 * @param o:
	 *            the object to compare
	 * @return true if the specified object is also a summit and has the same name,
	 *         or false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Summit other = (Summit) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Says if there is a victim on the summit
	 * 
	 * @return true if there a victim, or false otherwise
	 */
	@Override
	public boolean isObjective() {
		return objective;
	}

	/**
	 * If b is true, put a victim on the summit, else remove the victim of the
	 * summit
	 * 
	 * @param b:
	 *            boolean saying if there is a victim or not on the summit
	 */
	@Override
	public void setObjective(boolean b) {
		objective = b;
	}

	/**
	 * Says if the summit is an hospital
	 * 
	 * @return true if the summit is an hospital, or false otherwise
	 */
	@Override
	public boolean isHospital() {
		return hospital;
	}

	/**
	 * Compare the ends of the summit to the ends of the other summit
	 * 
	 * @param other:
	 *            the other summit to compare
	 * @return true if both summits have the same ends (even in a different order)
	 *         or false otherwise
	 */
	@Override
	public boolean asSameEnds(ISummit other) {
		IEdge[] e1 = getEnds();
		IEdge[] e2 = other.getEnds();
		return ((e1[0].equals(e2[0]) && e1[1].equals(e2[1])) || (e1[0].equals(e2[1]) && e1[1].equals(e2[0])));
	}

	/**
	 * Used to sort summits in a set
	 */
	@Override
	public int compareTo(Summit o) {
		return name.compareTo(o.getName());
	}

	/**
	 * Represent a summit by a string
	 * 
	 * @return the string made of the name of the summit
	 */
	@Override
	public String toString() {
		return name;
	}
}
