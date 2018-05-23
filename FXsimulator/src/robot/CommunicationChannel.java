package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import map.IEdge;
import map.ISummit;

public class CommunicationChannel {
	private TreeMap<ISummit, List<IEdge>> summitsTaken = new TreeMap<>();
	private List<IEdge> busyEdges = new LinkedList<>();

	private int nbOccur(List<ISummit> summitList, ISummit summit) {
		int nbOccur = 0;
		for (ISummit s : summitList) {
			if (summit.equals(s))
				nbOccur++;
		}
		return nbOccur;
	}

	/*
	 * Returns true if another robot is already on the nextSummit or false otherwise
	 */
	public synchronized boolean alreadyTaken(ISummit summit, IEdge nextEdge) {
		if (! summitsTaken.containsKey(summit)) {
			List<ISummit> nearSummits = new LinkedList<>();
			nearSummits.addAll(nextEdge.getSummits());
			List<ISummit> tmpList = new LinkedList<>();
			tmpList.addAll(nearSummits);
			
			for (ISummit s: tmpList) {
				nearSummits.addAll(s.getOtherEnd(nextEdge).getSummits());
			}
			
			if (nbOccur(nearSummits, summit) == 2) {
				System.out.println("summit " + summit.getName());
				boolean alreadyUsed = false;
				nearSummits.remove(summit);
				nearSummits.remove(summit);
				
				for (ISummit s: nearSummits) {
					if (nbOccur(nearSummits, s) == 2) {
						System.out.println("summit " + summit.getName());
						alreadyUsed = alreadyUsed || summitsTaken.containsKey(s);
					}
				}
				return alreadyUsed;
			}
		}
		return (true);
	}

	/*
	 * Returns true if another robot is going to nextEdge or false otherwise
	 */
	public synchronized boolean hasForDestination(IEdge nextEdge) {
		return (busyEdges.contains(nextEdge));
	}

	/*
	 * Say that the previous summit is now free, and that a robot is on the
	 * currentEdge
	 */
	public synchronized void freePlace(ISummit oldSummit, IEdge currentEdge) {
		summitsTaken.get(oldSummit).remove(currentEdge);
		if (summitsTaken.get(oldSummit).isEmpty())
			summitsTaken.remove(oldSummit);
	}

	/*
	 * Say that the previous summit is now free, and that a robot is on the
	 * currentEdge
	 */
	public synchronized void freeEdge(IEdge e) {
		busyEdges.remove(e);
	}

	/* Say that a robot is now on the newSummit going to the next Edge */
	public synchronized void takePlace(ISummit newSummit, IEdge oldEdge, IEdge nextEdge) {
		if (oldEdge != null)
			busyEdges.remove(oldEdge);
		if (!summitsTaken.containsKey(newSummit)) {
			List<IEdge> edgeList = new LinkedList<>();
			edgeList.add(nextEdge);
			summitsTaken.put(newSummit, edgeList);
		} else {
			List<IEdge> edgeList = summitsTaken.remove(newSummit);
			edgeList.add(nextEdge);
			summitsTaken.put(newSummit, edgeList);
		}
		busyEdges.add(nextEdge);
	}
}
