package robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import ch.makery.address.Main;
import map.Coordinates;
import map.Edge;
import map.EdgeType;
import map.IEdge;
import map.ISummit;

public class Robot extends Thread {
	private final double timeCoef = 20d;
	private final int CAPACITY = 2; // The maximum number of victim that a robot can transport
	private final double ESTIMATED_WAIT_TIME = 50000d; // This number is used to avoid crosses
														// The more it will be high, the more robot will avoid to reach
														// each other

	private ISummit currentSummit;
	private IEdge nextEdge;
	private Coordinates coordinates = new Coordinates(0, 0);
	private List<ISummit> way = new LinkedList<>();
	private CommunicationChannel chat;
	private int idRobot;
	private int nbVictimsInside = 0;
	private ISummit localObjective = null;

	// Use to print the robot
	private Main main;

	// At the beginning, the robot is placed on a summit, moving to the edge
	// nextEdge
	public Robot(ISummit startSummit, IEdge nextEdge, CommunicationChannel chat, Main main, int idRobot) {
		Random r = new Random();
		this.currentSummit = startSummit;
		if (nextEdge == null)
			this.nextEdge = startSummit.getEnds()[r.nextInt(2)];
		else
			this.nextEdge = nextEdge;
		this.chat = chat;
		chat.takePlace(this.currentSummit, null, this.nextEdge);
		this.main = main;
		Coordinates c = startSummit.getOtherEnd(this.nextEdge).getCoordinates();
		coordinates.setX(c.getX());
		coordinates.setY(c.getY());
		this.idRobot = idRobot;
	}

	private class CostSummit implements Comparable<CostSummit> {
		private double cost;
		private CostSummit previousSummit;
		private IEdge previousEdge;
		private ISummit summit;

		public CostSummit(double cost, CostSummit previousSummit, IEdge previousEdge, ISummit summit) {
			super();
			this.cost = cost;
			this.previousSummit = previousSummit;
			this.previousEdge = previousEdge;
			this.summit = summit;
		}

		public double getCost() {
			return cost;
		}

		public CostSummit getPreviousSummit() {
			return previousSummit;
		}

		public IEdge getPreviousEdge() {
			return previousEdge;
		}

		public ISummit getSummit() {
			return summit;
		}

		@Override
		public int compareTo(CostSummit o) {
			double diff = cost - o.cost;
			if (diff == 0d)
				return summit.getName().compareTo(o.summit.getName());
			return (int) diff;
		}
	}

	private void makeWay(boolean searchHospital) {
		CostSummit summitTested;
		IEdge nextNextEdge;
		boolean notFound = true;
		List<IEdge> seenEdges = new ArrayList<>();
		TreeSet<CostSummit> possibilities = new TreeSet<>();

		// Initialization of all the ways that are possible starting at currentEdge
		while (possibilities.isEmpty()) {
			for (ISummit s : nextEdge.getSummits()) {
				if (!chat.alreadyTaken(s) && !chat.hasSameDestination(s.getOtherEnd(nextEdge)))
					possibilities.add(new CostSummit(s.getLength(), null, nextEdge, s));
			}
		}
		seenEdges.add(nextEdge);

		// While the search didn't find any victim (or hospital), we extend the search
		// to the next shorter road since the current position
		do {
			summitTested = possibilities.first();
			nextNextEdge = summitTested.getSummit().getOtherEnd(summitTested.getPreviousEdge());
			if (!seenEdges.contains(nextNextEdge)) {
				for (ISummit s : nextNextEdge.getSummits()) {
					if (!chat.alreadyTaken(s))
						possibilities.add(
								new CostSummit(s.getLength() + summitTested.getCost(), summitTested, nextNextEdge, s));
					else
						possibilities.add(new CostSummit(s.getLength() + summitTested.getCost() + ESTIMATED_WAIT_TIME,
								summitTested, nextNextEdge, s));
				}
				seenEdges.add(nextNextEdge);
			}
			possibilities.remove(possibilities.first());

			if (searchHospital)
				notFound = !summitTested.getSummit().isHospital();
			else
				notFound = !summitTested.getSummit().isObjective();
		} while (notFound && !possibilities.isEmpty());

		if (!summitTested.getSummit().equals(localObjective) || localObjective == null) {
			if (localObjective != null && localObjective.isObjective())
				Main.objectives.add(localObjective);

			if (!summitTested.getSummit().isHospital()) {
				localObjective = summitTested.getSummit();
				Main.objectives.remove(localObjective);
			}
		}

		// When the objective has been found, we rebuild the way
		while (summitTested != null) {
			way.add(0, summitTested.summit);
			summitTested = summitTested.getPreviousSummit();
		}
	}

	private void chooseDirection() {
		chat.freePlace(currentSummit, nextEdge);

		do {
			way.clear();
			makeWay(nbVictimsInside >= CAPACITY || (Main.objectives.isEmpty() && nbVictimsInside > 0));
		} while (way.isEmpty() || chat.alreadyTaken(way.get(0)) || chat.hasSameDestination(way.get(0).getOtherEnd(nextEdge)));

		chat.takePlace(way.get(0), nextEdge, way.get(0).getOtherEnd(nextEdge));
		currentSummit = way.get(0);
		nextEdge = currentSummit.getOtherEnd(nextEdge);
		way.remove(0);
	}

	private void envolveCoordinates() {
		coordinates.setX(nextEdge.getCoordinates().getX());
		coordinates.setY(nextEdge.getCoordinates().getY());
	}

	// WARNING : if the third summit (by alphabetic order) is the shorter
	// this will not work
	private boolean isExternalCurve() {
		IEdge e[] = currentSummit.getEnds();
		if (e[0].getType().equals(e[1].getType()) && e[0].getType().equals(EdgeType.CROSS)) {
			for (ISummit s0 : e[0].getSummits()) {
				for (ISummit s1 : e[1].getSummits()) {
					if (!s0.equals(s1) && Main.asSameEnds(s0, s1)) {
						return (s0.getLength() <= currentSummit.getLength()
								&& s1.getLength() <= currentSummit.getLength());
					}
				}
			}
		}
		return false;
	}

	private void arriveEdge(double duration) {
		if (currentSummit.isObjective() && nbVictimsInside < CAPACITY) {
			if (Main.objectives.contains(currentSummit))
				Main.objectives.remove(currentSummit);
			currentSummit.setObjective(false);
			if (!currentSummit.isHospital())
				main.changeSummitColor(currentSummit, duration);
			nbVictimsInside++;
		}

		if (currentSummit.isHospital())
			nbVictimsInside = 0;

		try {
			Thread.sleep((long) duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void endRobot() {
		Coordinates oldC = new Coordinates(0, 0);
		IEdge previousEdge = nextEdge;
		nextEdge = new Edge(0, new TreeSet<>(), null, new Coordinates(idRobot * 10 + 5, 5));
		oldC.setX(coordinates.getX());
		oldC.setY(coordinates.getY());
		envolveCoordinates();
		main.printRobotMovement(0d, 100d * timeCoef, oldC, coordinates, currentSummit.getLength(), true, null, this);
		chat.freePlace(currentSummit, previousEdge);
		chat.freeEdge(previousEdge);

		System.out.println("Robot " + idRobot + " finished.");
	}

	@Override
	public void run() {
		double duration = 0d;
		Coordinates oldC = new Coordinates(0d, 0d);

		while (!Main.objectives.isEmpty() || nbVictimsInside > 0 || localObjective.isObjective()) {

			// Define the transition delay and duration
			duration = timeCoef * currentSummit.getLength();

			// Define the end coordinates and the shape of the movement
			oldC.setX(coordinates.getX());
			oldC.setY(coordinates.getY());
			envolveCoordinates();

			if (isExternalCurve()) {
				main.printRobotMovement(0d, duration, oldC, coordinates, currentSummit.getLength(), false,
						currentSummit.getEnds(), this);
			} else {
				main.printRobotMovement(0d, duration, oldC, coordinates, currentSummit.getLength(), true, null, this);
			}

			// When the robot arrives on the next edge, we choose the next direction
			arriveEdge(duration);
			chooseDirection();
		}

		endRobot();
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
}
