package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import map.IEdge;
import map.ISummit;

public class CommunicationChannel {
	private TreeMap<ISummit, List<IEdge>> summitsTaken = new TreeMap<>();

	public synchronized boolean alreadyTaken(ISummit nextSummit, IEdge nextEdge) {
		return (summitsTaken.containsKey(nextSummit) && !summitsTaken.get(nextSummit).contains(nextEdge));
	}

	public synchronized void freePlace(ISummit oldSummit, IEdge currentEdge) {
		summitsTaken.get(oldSummit).remove(currentEdge);
		if (summitsTaken.get(oldSummit).isEmpty())
			summitsTaken.remove(oldSummit);
	}

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
