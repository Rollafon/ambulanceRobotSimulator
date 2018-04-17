package robot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;

import ch.makery.address.Main;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private Coordinates coordinates = new Coordinates(0, 0);
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
		Coordinates c = startSummit.getOtherEnd(this.nextEdge).getCoordinates();
		coordinates.setX(c.getX());
		coordinates.setY(c.getY());
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

	@Override
	public void run() {

		// Create a circle and print it on the graphic window
		Circle c = main.initPrintRobot(coordinates);

		double duration = 0;
		double cumulateDuration = 0;

		while (!Main.objectives.isEmpty()) {
			// Initialize the transition
			Path path = new Path();
			path.getElements().add(new MoveTo(coordinates.getX(), coordinates.getY()));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setPath(path);
			pathTransition.setNode(c);
			pathTransition.setCycleCount(1);

			// Define the transition delay and duration
			pathTransition.setDelay(Duration.millis(cumulateDuration));
			duration = 200 * (currentSummit.getLength());
			if (duration == 0)
				duration = 1;
			pathTransition.setDuration(Duration.millis(duration));
			cumulateDuration += duration;

			// Define the end coordinates and the shape of the movement

			envolveCoordinates();
			path.getElements().add(new LineTo(coordinates.getX(), coordinates.getY()));
			pathTransition.play();

			// When the robot arrives on the next edge, we choose the next direction
			if (Main.objectives.contains(currentSummit)) {
				Main.objectives.remove(currentSummit);
		    	currentSummit.setObjective(false);
				pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
				    	main.changeSummitColor(currentSummit);
				    }
				});
			}
			chooseDirection();
		}

	}

	@Override
	public Coordinates getCoordinates() {
		return coordinates;
	}

}
