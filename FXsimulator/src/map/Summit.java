package map;
public class Summit implements ISummit, Comparable<Summit> {
	private String name;
	private double length;
	private IEdge e1 = null;
	private IEdge e2 = null;
	private SummitColor color = SummitColor.BLACK;
	private boolean objective = false;
	private boolean hospital = false;
	
	public Summit(String name, double d) {
		this.name = name;
		this.length = d;
	}
	
	public Summit(String name, double d, boolean isHospital) {
		this.name = name;
		this.length = d;
		this.hospital = isHospital;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getLength() {
		return length;
	}

	@Override
	public IEdge[] getEnds() {
		IEdge[] res = new IEdge[2];
		res[0] = e1;
		res[1] = e2;
		return res;
	}

	@Override
	public void setEnd(IEdge e) {
		if (e1 == null)
			e1 = e;
		else 
			e2 = e;
	}

	@Override
	public IEdge getOtherEnd(IEdge e) {
		if (e.equals(e1))
			return e2;
		else 
			return e1;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Summit other = (Summit) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public SummitColor getColor() {
		return color;
	}
	
	@Override
	public void setColor(SummitColor c) {
		color = c;
	}
	
	@Override
	public boolean isObjective() {
		return objective;
	}
	
	@Override
	public void setObjective(boolean b) {
		objective = b;
	}

	@Override
	public int compareTo(Summit o) {
		return name.compareTo(o.getName());
	}
	
	public String toString() {
		return name;
	}

	@Override
	public boolean isHospital() {
		return hospital;
	}
	
}
