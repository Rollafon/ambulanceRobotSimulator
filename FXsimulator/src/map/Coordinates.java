package map;

/**
 * This class is used to represent the coordinates of graphics objects
 * 
 * @author Romain LAFON
 */
public class Coordinates {
	private double x;
	private double y;

	/**
	 * Constructor
	 * 
	 * @param x:
	 *            the x position of the coordinates
	 * @param y:
	 *            the y position of the coordinates
	 */
	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter on the x position
	 * 
	 * @return the x position
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter on the y position
	 * 
	 * @return the y position
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter on the x position
	 * 
	 * @param x:
	 *            the new x position
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Setter on the y position
	 * 
	 * @param y:
	 *            the new y position
	 */
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Coordinates other = (Coordinates) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
