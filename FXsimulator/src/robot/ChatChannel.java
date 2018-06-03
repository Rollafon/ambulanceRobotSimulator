package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import map.IEdge;
import map.ISummit;

/**
 * Represent a shared chat channel for the position of the robot
 * 
 * @author Romain LAFON
 */
public class ChatChannel {
	private TreeSet<ISummit> summitsTaken = new TreeSet<>();
	private List<IEdge> busyEdges = new LinkedList<>();

	/**
	 * Say if a robot is already on the given summit
	 * 
	 * @param summit:
	 *            the summit to check
	 * @return true if another robot is already on the summit or false otherwise
	 */
	public synchronized boolean alreadyTaken(ISummit summit) {
		return (summitsTaken.contains(summit));
	}

	/**
	 * Say if there is already another robot going to the given edge
	 * 
	 * @param edge:
	 *            the edge to check
	 * @return true if another robot is already going to edge or false otherwise
	 */
	public synchronized boolean hasForDestination(IEdge edge) {
		return (busyEdges.contains(edge));
	}

	/**
	 * Indicate that the previous summit is now free
	 * 
	 * @param oldSummit:
	 *            the summit that the robot leave
	 */
	public synchronized void freePlace(ISummit oldSummit) {
		summitsTaken.remove(oldSummit);
	}

	/**
	 * Indicate that the robot is now out of the given edge
	 * 
	 * @param e:
	 *            the freed edge
	 */
	public synchronized void freeEdge(IEdge e) {
		busyEdges.remove(e);
	}

	/**
	 * Indicate that a robot is now on the given summit going to the given next edge
	 * 
	 * @param newSummit:
	 *            the summit where the robot had just been inserted
	 * @param oldEdge:
	 *            the robot old position
	 * @param nextEdge:
	 *            the robot next position
	 */
	public synchronized void takePlace(ISummit newSummit, IEdge oldEdge, IEdge nextEdge) {
		if (oldEdge != null)
			busyEdges.remove(oldEdge);
		summitsTaken.add(newSummit);
		busyEdges.add(nextEdge);
	}
}
