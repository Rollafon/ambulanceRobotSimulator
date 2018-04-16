package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;

import ch.makery.address.Main;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import map.Coordinates;
import map.IEdge;
import map.ISummit;

public class Robot implements IRobot {
	private ISummit currentSummit;
	private IEdge nextEdge;
	private Coordinates coordinates;
	private NavigableSet<ISummit> objectives = new TreeSet<>();
	private List<ISummit> way = new LinkedList<>();
	private ICommunicationChannel chat;

	// Use to print the robot
	Main main;

	// At the beginning, the robot is placed on a summit, moving to the edge e
	public Robot(ISummit startSummit, IEdge nextEdge, ICommunicationChannel chat, Main main) {
		Random r = new Random();
		this.currentSummit = startSummit;
		this.nextEdge = startSummit.getEnds()[r.nextInt(2)];
		this.chat = chat;
		this.main = main;
		coordinates = startSummit.getOtherEnd(this.nextEdge).getCoordinates();
	}

	@Override
	public void addObjective(ISummit s) {
		if (!objectives.contains(s))
			objectives.add(s);
		s.setObjective(true);
	}

	public void makeWay(ISummit s, IEdge e) {
		if (!objectives.isEmpty()) {
			NavigableSet<ISummit> opportunities = e.getSummits();
			ISummit summitTested = opportunities.first();
			if (objectives.contains(summitTested))
				objectives.remove(summitTested);
			// makeWay(summitTested, summitTested.getOtherEnd(e));
		}
	}

	private void chooseDirection() {
		if (way.isEmpty())
			makeWay(currentSummit, currentSummit.getOtherEnd(nextEdge));
		else {
			currentSummit = way.get(0);
			nextEdge = currentSummit.getOtherEnd(nextEdge);
			way.remove(0);
		}
	}

	private void envolveCoordinates() {
		coordinates.setX(nextEdge.getCoordinates().getX());
		coordinates.setY(nextEdge.getCoordinates().getY());
		/*
		 * if (coordinates.getX() == nextEdge.getCoordinates().getX()) { if
		 * (coordinates.getY() < nextEdge.getCoordinates().getY())
		 * coordinates.setY(coordinates.getY() + 10); else
		 * coordinates.setY(coordinates.getY() - 10); } else { if (coordinates.getX() <
		 * nextEdge.getCoordinates().getX()) coordinates.setX(coordinates.getX() + 10);
		 * else coordinates.setX(coordinates.getX() - 10); }
		 */
	}

	@Override
	public void run() {

		Circle c = null;

		Path path = new Path();
		PathTransition pathTransition = new PathTransition();

		pathTransition.setPath(path);

		c = main.initPrintRobot(coordinates, c);

		pathTransition.setNode(c);
		pathTransition.setCycleCount(Timeline.INDEFINITE);

		// TODO : improve to put makeWay in a new thread and put a while there
		if (!objectives.isEmpty()) {
			double duree = 100 * (currentSummit.getLength());
			if (duree == 0)
				duree = 1;
			pathTransition.setDuration(Duration.millis(duree));
	
			if (!coordinates.equals(nextEdge.getCoordinates())) {
				System.out.println("Mon sommet : " + currentSummit.getName() + "\nMa direction : " + nextEdge.toString() + "\nJe viens de : " + currentSummit.getOtherEnd(nextEdge).toString());
				path.getElements().add(new MoveTo(coordinates.getX(), coordinates.getY()));
				envolveCoordinates();
				path.getElements().add(new LineTo(coordinates.getX(), coordinates.getY()));
				pathTransition.play();
			}
			chooseDirection();
			if (objectives.contains(currentSummit)) {
				objectives.remove(currentSummit);
				currentSummit.setObjective(false);
			}
		}
	}

	@Override
	public Coordinates getCoordinates() {
		return coordinates;
	}

}
