package robot;

import map.Coordinates;
import map.IEdge;
import map.ISummit;

public interface IRobot {
	
	public void addObjective(ISummit s);
	// This method is the method which calculate the best way to achieve objectives
	public void makeWay(ISummit s, IEdge e);
	public void run();
	public Coordinates getCoordinates();
	
}
