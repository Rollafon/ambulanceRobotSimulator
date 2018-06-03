package robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import main.Main;
import map.Coordinates;
import map.Edge;
import map.EdgeType;
import map.IEdge;
import map.ISummit;
import map.Summit;

/**
 * Represent a robot and it intelligence
 * 
 * @author Romain LAFON
 */
public class Robot extends Thread {
	private final double timeCoef = 40d; // Coefficient used to determinate the speed of the robot
	private final int CAPACITY = 2; // The maximum number of victim that a robot can transport
	private final double ESTIMATED_WAIT_TIME = 10000d; // This number is used to avoid crosses
														// The more it will be high, the more robot will avoid the other
														// robots

	private ISummit currentSummit; // The summit where the robot is
	private IEdge nextEdge; // The next position of the robot
	private Coordinates coordinates = new Coordinates(0, 0); // The robot coordinates
	private List<ISummit> way = new LinkedList<>(); // The way that the robot will probably follow (according to the
													// intelligence)
	private ChatChannel chat; // The chat used to synchronize all robots
	private int idRobot; // The robot code name
	private int nbVictimsInside = 0; // The number of victim inside the robot
	private ISummit localObjective = null; // The local objective of the robot

	private Main main; // Use to print the robot

	/**
	 * Constructor. At the beginning, the robot is placed on a summit, moving to
	 * nextEdge if nextEdge is not null, or to a random end of the current summit
	 * otherwise
	 * 
	 * @param startSummit:
	 *            the summit where the robot has been placed to start
	 * @param nextEdge:
	 *            the edge where the robot has to go (put null to have a random
	 *            nextEdge)
	 * @param chat:
	 *            the chat channel shared with other robots
	 * @param main:
	 *            the link with graphical objects (used to represent the robot
	 *            moves)
	 * @param idRobot:
	 *            the code name of the robot
	 */
	public Robot(ISummit startSummit, IEdge nextEdge, ChatChannel chat, Main main, int idRobot) {
		Random r = new Random();
		this.currentSummit = startSummit;
		if (nextEdge == null)
			this.nextEdge = startSummit.getEnds()[r.nextInt(2)];
		else
			this.nextEdge = nextEdge;
		this.chat = chat;
		chat.takePlace(this.currentSummit, null, this.nextEdge);
		this.main = main;
		Coordinates c = this.nextEdge.getCoordinates();
		coordinates.setX(c.getX());
		coordinates.setY(c.getY());
		this.idRobot = idRobot;
	}

	/**
	 * Data structure associating a pointer to a previous CostSummit (the rest of
	 * the stack), a previous edge, a current summit and an estimated cost
	 *
	 * It is used by the Dijkstra algorithm
	 * 
	 * @author Romain LAFON
	 */
	private class CostSummit implements Comparable<CostSummit> {
		private double cost; // The estimated cost to arrive at the current summit and to cross it
		private CostSummit previousSummit; // The rest of the summit stack according to the way we choose to reach the
											// current summit
		private IEdge previousEdge; // The edge we came from
		private ISummit summit; // The current summit

		/**
		 * Constructor
		 * 
		 * @param cost:
		 *            the estimated cost to reach this summit and to cross it
		 * @param previousSummit:
		 *            the previous CostSummit used to reach the current one
		 * @param previousEdge:
		 *            the edge we came from
		 * @param summit:
		 *            the current summit
		 */
		public CostSummit(double cost, CostSummit previousSummit, IEdge previousEdge, ISummit summit) {
			this.cost = cost;
			this.previousSummit = previousSummit;
			this.previousEdge = previousEdge;
			this.summit = summit;
		}

		/**
		 * Getter on the estimated cost
		 * 
		 * @return the estimated cost
		 */
		public double getCost() {
			return cost;
		}

		/**
		 * Getter on the previous CostSummit
		 * 
		 * @return the previous CostSummit
		 */
		public CostSummit getPreviousSummit() {
			return previousSummit;
		}

		/**
		 * Getter on the previous edge
		 * 
		 * @return the previous edge
		 */
		public IEdge getPreviousEdge() {
			return previousEdge;
		}

		/**
		 * Getter on the current summit
		 * 
		 * @return the current summit
		 */
		public ISummit getSummit() {
			return summit;
		}

		/**
		 * Used to sort the stacks by estimated cost of the last summit
		 * 
		 * @param other:
		 *            the other CostSummit to compare
		 * @return a positive number if the estimated cost of the current CostSummit is
		 *         lower than the other or if they have the same cost and the name of
		 *         the first is sorted before the name of the second in alphabetic
		 *         order. It returns a negative number otherwise.
		 */
		@Override
		public int compareTo(CostSummit other) {
			double diff = cost - other.cost;
			if (diff == 0d)
				return summit.getName().compareTo(other.summit.getName());
			return (int) diff;
		}
	}

	/**
	 * Make the path to follow for the robot. The algorithm is based on the Dijkstra
	 * algorithm. We will look at the neighboring summits, estimate their costs, and
	 * develop the one that have the lower cost, while we do not found an objective
	 * (victim or hospital)
	 */
	private void makePath() {
		CostSummit summitTested;
		IEdge nextNextEdge;
		boolean found = false;
		List<IEdge> seenEdges = new ArrayList<>();
		TreeSet<CostSummit> possibilities = new TreeSet<>();

		// Initialization of all the ways that are possible starting at currentEdge
		while (possibilities.isEmpty()) {

			// Look at all the neighboring summits of the nextEdge
			for (ISummit s : nextEdge.getSummits()) {

				// It is possible to go to the summit s if it is not already taken by another
				// robot and if no robot has the same edge for destination
				if (!chat.alreadyTaken(s) && !chat.hasForDestination(s.getOtherEnd(nextEdge)))
					possibilities.add(new CostSummit(s.getLength(), null, nextEdge, s));
			}
		}
		seenEdges.add(nextEdge);

		// While the search didn't find any victim (or hospital), we extend the search
		// to the next shorter summit since the current position
		do {
			summitTested = possibilities.first();
			nextNextEdge = summitTested.getSummit().getOtherEnd(summitTested.getPreviousEdge());

			// If we do not already looked at the nextNextEdge
			if (!seenEdges.contains(nextNextEdge)) {

				// Look at all the neighboring summits of the nextNextEdge
				for (ISummit s : nextNextEdge.getSummits()) {

					// If the summit is not already taken, add it to the possibility with the
					// estimated cost of the path until this summit plus the length of the summit
					// itself
					if (!chat.alreadyTaken(s))
						possibilities.add(
								new CostSummit(s.getLength() + summitTested.getCost(), summitTested, nextNextEdge, s));

					// If the summit s is already taken and the robot on this summit is coming
					// towards the current robot, that mean that it will probably be a face to face
					// in some moves. That is why we will add a big constant to the cost. The robot
					// has to find a deviation.
					else if (chat.hasForDestination(nextNextEdge)) {
						possibilities.add(new CostSummit(s.getLength() + summitTested.getCost() + ESTIMATED_WAIT_TIME,
								summitTested, nextNextEdge, s));
					}
					// We are now in the case where the summit s is already taken but the robot on
					// this summit is going to the same direction as the current robot want to go.
					// In that case, we add a big constant divided by the estimated cost of the path
					// to go to the summit s. If we do not add this big constant, a robot might
					// follow another one, and be too close. That might causes collision. The
					// division is necessary because if the other robot is far away, the current
					// robot might follow it (the more the other robot is far the more the
					// probability of too near following is low).
					else {
						possibilities.add(new CostSummit(
								s.getLength() + summitTested.getCost() + ESTIMATED_WAIT_TIME / summitTested.getCost(),
								summitTested, nextNextEdge, s));
					}
				}
				seenEdges.add(nextNextEdge);
			}
			possibilities.remove(possibilities.first());

			// If we search an hospital
			if (nbVictimsInside >= CAPACITY
					|| (Main.objectives.isEmpty() && nbVictimsInside > 0 && !localObjective.isObjective()))
				found = summitTested.getSummit().isHospital();
			else
				found = Main.objectives.contains(summitTested.getSummit())
						|| (summitTested.getSummit().equals(localObjective) && localObjective.isObjective());
		} while (!found && !possibilities.isEmpty());

		// If the chosen objective is not the local objective, we have to remove it from
		// the main and to make it become a local objective.
		if (!summitTested.getSummit().equals(localObjective)) {

			// If the local objective still is an objective, it means that the robot leaves
			// this victim because of the position of another robot. But we do not have to
			// renounce at the victim, therefore the robot adds it to the shared objectives
			// before to go to the new local objective
			if (localObjective != null && localObjective.isObjective())
				Main.objectives.add(localObjective);
			localObjective = summitTested.getSummit();
			if (!localObjective.isHospital())
				Main.objectives.remove(localObjective);
		}

		// When the objective has been found, we rebuild the path from the stack
		while (summitTested != null) {
			way.add(0, summitTested.summit);
			summitTested = summitTested.getPreviousSummit();
		}
	}

	/**
	 * Make the robot choose a direction and indicate it to all other robots
	 */
	private void chooseDirection() {
		chat.freePlace(currentSummit);

		// While the way is not valid or while there will probably be a collision, we
		// remake the way to follow
		do {
			way.clear();
			makePath();
		} while (way.isEmpty() || chat.alreadyTaken(way.get(0))
				|| chat.hasForDestination(way.get(0).getOtherEnd(nextEdge)));

		chat.takePlace(way.get(0), nextEdge, way.get(0).getOtherEnd(nextEdge));
		currentSummit = way.get(0);
		nextEdge = currentSummit.getOtherEnd(nextEdge);
		way.remove(0);
	}

	/**
	 * The robot take the new coordinates. In the intern structure of the robot, the
	 * robot moves by instantaneous shifting and then wait the time of the shift.
	 */
	private void envolveCoordinates() {
		coordinates.setX(nextEdge.getCoordinates().getX());
		coordinates.setY(nextEdge.getCoordinates().getY());
	}

	/**
	 * Say if the summit is an external curve in a cross (not used for the Final
	 * map)
	 * 
	 * @param s:
	 *            the summit to check
	 * @return true if s is an external curve on a crossing or false otherwise
	 */
	// WARNING : if the third summit (by alphabetic order) is the shorter
	// this will not work
	private boolean isExternalCurve(ISummit s) {
		IEdge e[] = s.getEnds();
		if (e[0].getType().equals(e[1].getType()) && e[0].getType().equals(EdgeType.CROSS)) {
			for (ISummit s0 : e[0].getSummits()) {
				for (ISummit s1 : e[1].getSummits()) {
					if (!s0.equals(s1) && s0.asSameEnds(s1)) {
						return (s0.getLength() <= s.getLength() && s1.getLength() <= s.getLength());
					}
				}
			}
		}
		return false;
	}

	/**
	 * Make the things a robot has to do when arriving on an edge:
	 * 
	 * - If the previous summit was an objective and the robot was not full, take
	 * the victim and say it to the other and change the color of the summit
	 * representation to show the victim has been saved.
	 * 
	 * - If the previous summit was an hospital, we indicate to the robot that it is
	 * now empty
	 * 
	 * - Then we have to wait the estimated time of course (the robot is moving by
	 * instantaneous shifting
	 * 
	 * @param duration:
	 *            the duration of the course
	 */
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

	/**
	 * Describe the protocol that each robot has to follow before to end.
	 * 
	 * In that implementation, the robot will go out of the road before to say to
	 * the main that he just finished.
	 */
	private void endRobot() {
		Coordinates oldC = new Coordinates(0, 0);
		IEdge previousEdge = nextEdge;
		nextEdge = new Edge(0, new TreeSet<>(), null, new Coordinates(idRobot * 10 + 5, 5));
		oldC.setX(coordinates.getX());
		oldC.setY(coordinates.getY());
		envolveCoordinates();
		main.printRobotMovement(100d * timeCoef, oldC, coordinates, new Summit("end", 0), true, this, "");
		chat.freePlace(currentSummit);
		chat.freeEdge(previousEdge);

		System.out.println("Robot " + idRobot + " finished.");
		main.robotFinished();
	}

	/**
	 * This is the main method of a robot. It is used to make it think and move.
	 */
	@Override
	public void run() {
		double duration = 0d;
		Coordinates oldC = new Coordinates(0d, 0d);

		// While all the victims are not saved
		while (!Main.objectives.isEmpty() || nbVictimsInside > 0
				|| (localObjective != null && localObjective.isObjective())) {

			chooseDirection();

			// Define the transition duration
			duration = timeCoef * currentSummit.getLength();

			// Define the end coordinates of the movement
			oldC.setX(coordinates.getX());
			oldC.setY(coordinates.getY());
			envolveCoordinates();

			// Define the shape of the movement and the message to print
			// The first case will never be used with that map, but I kept it in a view of
			// evolution of the simulator
			if (isExternalCurve(currentSummit)) {
				main.printRobotMovement(duration, oldC, coordinates, currentSummit, false, this,
						localObjective.getName() + " - " + nbVictimsInside + "/" + CAPACITY);
			} else {
				main.printRobotMovement(duration, oldC, coordinates, currentSummit, true, this,
						localObjective.getName() + " - " + nbVictimsInside + "/" + CAPACITY);
			}

			arriveEdge(duration);
		}
		endRobot();
	}

	/**
	 * Getter on the robot coordinates
	 * 
	 * @return the robot coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Getter on the robot code name
	 * 
	 * @return the robot code name
	 */
	public int getIdRobot() {
		return idRobot;
	}
}
