package robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import ch.makery.address.Main;
import map.Coordinates;
import map.EdgeType;
import map.IEdge;
import map.ISummit;

public class Robot extends Thread {
	private final double timeCoef = 10d;

	private ISummit currentSummit;
	private IEdge nextEdge;
	private Coordinates coordinates = new Coordinates(0, 0);
	private List<ISummit> way = new LinkedList<>();
	private ICommunicationChannel chat;
	private int idRobot;

	// Use to print the robot
	private Main main;

	// At the beginning, the robot is placed on a summit, moving to the edge
	// nextEdge
	public Robot(ISummit startSummit, IEdge nextEdge, ICommunicationChannel chat, Main main, int idRobot) {
		Random r = new Random();
		this.currentSummit = startSummit;
		if (nextEdge == null)
			this.nextEdge = startSummit.getEnds()[r.nextInt(2)];
		else
			this.nextEdge = nextEdge;
		this.chat = chat;
		this.main = main;
		Coordinates c = startSummit.getOtherEnd(this.nextEdge).getCoordinates();
		coordinates.setX(c.getX());
		coordinates.setY(c.getY());
		this.idRobot = idRobot;
	}

	public void makeWay(ISummit previousSummit, IEdge currentEdge) {
		NavigableSet<ISummit> opportunities = currentEdge.getSummits();
		List<ISummit> oppForRandom = new ArrayList<>();
		Random r = new Random();

		for (ISummit s : opportunities)
			oppForRandom.add(s);

		way.add(oppForRandom.get(r.nextInt(oppForRandom.size())));
		// if (Main.objectives.contains(summitTested))
		// Main.objectives.remove(summitTested);
		// makeWay(summitTested, summitTested.getOtherEnd(e));
	}

	private void chooseDirection() {
		if (way.isEmpty())
			makeWay(currentSummit, nextEdge);
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

	@Override
	public void run() {
		double duration = 0d;
		//double cumulateDuration = 0;
		Coordinates oldC = new Coordinates(0d, 0d);

		while (!Main.objectives.isEmpty()) {
			
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
				main.printRobotMovement(0d, duration, oldC, coordinates, currentSummit.getLength(), true,
						null, this);
			}

			// When the robot arrives on the next edge, we choose the next direction
			if (Main.objectives.contains(currentSummit)) {
				Main.objectives.remove(currentSummit);
				currentSummit.setObjective(false);
				main.changeSummitColor(currentSummit, duration);
			}
			chooseDirection();
			try {
				Thread.sleep((long) duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Robot " + idRobot + " finished.");
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
}
