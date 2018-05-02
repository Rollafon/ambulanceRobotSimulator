package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import map.IEdge;
import map.ISummit;

public class CommunicationChannel {
	private TreeMap<ISummit, List<IEdge>> summitsTaken = new TreeMap<>();
	// private TreeMap<Integer, IEdge> edgesTaken = new TreeMap<>();

	public synchronized boolean alreadyTaken(ISummit nextSummit, IEdge nextEdge) {
		return (summitsTaken.containsKey(nextSummit) && !summitsTaken.get(nextSummit).contains(nextEdge));
	}

	public synchronized boolean sameDestination(ISummit nextSummit, IEdge nextEdge) {
		return false;
	}

	public synchronized void freePlace(Integer idRobot, ISummit oldSummit, IEdge currentEdge) {
		summitsTaken.get(oldSummit).remove(currentEdge);
		if (summitsTaken.get(oldSummit).isEmpty())
			summitsTaken.remove(oldSummit);
		/*
		 * synchronized (edgesTaken) { edgesTaken.put(idRobot, currentEdge); }
		 */
	}

	public synchronized void takePlace(Integer idRobot, ISummit newSummit, IEdge nextEdge) {
		/*
		 * synchronized (edgesTaken) { if (edgesTaken.containsKey(idRobot))
		 * edgesTaken.remove(idRobot); }
		 */
		if (!summitsTaken.containsKey(newSummit)) {
			List<IEdge> edgeList = new LinkedList<>();
			edgeList.add(nextEdge);
			summitsTaken.put(newSummit, edgeList);
		} else {
			List<IEdge> edgeList = summitsTaken.remove(newSummit);
			edgeList.add(nextEdge);
			summitsTaken.put(newSummit, edgeList);
		}
	}
}
