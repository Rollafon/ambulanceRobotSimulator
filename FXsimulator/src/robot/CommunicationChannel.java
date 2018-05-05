package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import map.IEdge;
import map.ISummit;

public class CommunicationChannel {
	private TreeMap<ISummit, List<IEdge>> summitsTaken = new TreeMap<>();

	/* Returns true if another robot is already on the nextSummit going to the nextEdge or false otherwise */
	public synchronized boolean alreadyTaken(ISummit nextSummit) {
		return (summitsTaken.containsKey(nextSummit));
	}

	/* Say that the previous summit is now free, and that a robot is on the currentEdge */
	public synchronized void freePlace(ISummit oldSummit, IEdge currentEdge) {
		summitsTaken.get(oldSummit).remove(currentEdge);
		if (summitsTaken.get(oldSummit).isEmpty())
			summitsTaken.remove(oldSummit);
	}

	/* Say that a robot is now on the newSummit going to the next Edge */
	public synchronized void takePlace(ISummit newSummit, IEdge nextEdge) {
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
