package robot;

import map.Coordinates;
import map.IEdge;
import map.ISummit;

public interface IRobot {
	
	// This method is the method which calculate the best way to achieve objectives
	public void makeWay(ISummit s, IEdge e);
	public Runnable run();
	public Coordinates getCoordinates();
	
}
