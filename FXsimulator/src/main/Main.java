package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.animation.PathTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import map.*;
import robot.*;

/**
 * Main class
 * @author LAFON Romain
 * @since JDK 1.8
 */
public class Main extends Application {
	private final double xMin = 50d; // Indicate the minimal x position on the window
	private final double xMax = 1850d; // Indicate the maximal x position on the window
	private final double xLength = xMax - xMin; // Calculate the x length of the window with the given positions
	private final double yMin = 50d; // Indicate the minimal y position on the window
	private final double yMax = 1000d; // Indicate the maximal y position on the window
	private final double yLength = yMax - yMin; // Calculate the y length of the window with the given positions
	private final double proportionMaxX = 207d; // Used to auto-size the drawing of the map
	private final double proportionMaxY = 157d; // Used to auto-size the drawing of the map

	// These 3 length are used to give proportional weights to summits
	private final double minLengthPerSummit = 10d;
	private final double minLengthIntersection = 2d * minLengthPerSummit;
	private final double maxLengthIntersection = 2d * minLengthPerSummit;

	// Contains the global objectives (victims' positions)
	public static NavigableSet<ISummit> objectives = new TreeSet<>();

	/**
	 * Used to graphically represent a robot
	 * 
	 * @author Romain LAFON
	 */
	private class RobotReprensentation {
		private Circle circle;
		private Text text;

		/**
		 * Constructor
		 * 
		 * @param circle:
		 *            the circle that will represent the robot
		 * @param text:
		 *            the text that will represent the robot objective
		 */
		public RobotReprensentation(Circle circle, Text text) {
			this.circle = circle;
			this.text = text;
		}

		/**
		 * Getter on the circle
		 * 
		 * @return the circle associated to the robot
		 */
		public Circle getCircle() {
			return circle;
		}

		/**
		 * Getter on the text
		 * 
		 * @return the text associated to the robot
		 */
		public Text getText() {
			return text;
		}
	}

	private Map<Robot, RobotReprensentation> robotMap = new HashMap<>(); // Associate a robot to it representation
	private Map<ISummit, Line> summitRepresentation = new TreeMap<>(); // Associate a summit to it representation

	// Contains the stage, the main pane and the main group of the javaFX
	// application
	private Stage primaryStage;
	private AnchorPane rootLayout;
	private Group root = null;

	// Contains the graph representing the map
	private IGraph graph;

	public static boolean isFinished = false; // Used to know if all the robot have finished
	private int nbRobotsAlive; // Used to know how many robots still alive

	/**
	 * Automatically calculate the x position
	 * 
	 * @param proportion:
	 *            the x proportion on the window
	 * @return the x position of the graphic element to be printed
	 */
	private double calcX(double proportion) {
		return (xMin + (proportion / proportionMaxX) * xLength);
	}

	/**
	 * Automatically calculate the y position
	 * 
	 * @param proportion:
	 *            the y proportion on the window
	 * @return the y position of the graphic element to be printed
	 */
	private double calcY(double proportion) {
		return (yMin + (proportion / proportionMaxY) * yLength);
	}

	/**
	 * Graph constructor
	 * 
	 * The graph represent the map, summits are roads and edge are the points where
	 * robots need to take a decision
	 * 
	 * The current map is represented in a joined file
	 * 
	 * To change the map or hospital position, modifications have to be done here
	 * 
	 * Be careful: in a cross, the second summit by alphabetic order has to be the
	 * shorter
	 */
	public Main() {
		graph = new Graph();
		NavigableSet<ISummit> tmpList;

		ISummit a1b1 = new Summit("a1b1", 2 * maxLengthIntersection + minLengthIntersection, true);

		ISummit a1a2 = new Summit("a1a2", maxLengthIntersection);
		ISummit a1a3 = new Summit("a1a3", minLengthIntersection);
		ISummit a2a3 = new Summit("a2a3", minLengthIntersection);
		ISummit a2h3 = new Summit("a2h3", 10 * maxLengthIntersection + minLengthIntersection);
		ISummit a3c1 = new Summit("a3c1", minLengthIntersection);

		graph.addSummit(a1b1);
		graph.addSummit(a1a2);
		graph.addSummit(a1a3);
		graph.addSummit(a2a3);
		graph.addSummit(a2h3);
		graph.addSummit(a3c1);

		tmpList = new TreeSet<>();
		tmpList.add(a1b1);
		tmpList.add(a1a3);
		tmpList.add(a1a2);
		IEdge e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(50d), calcY(0d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(a2h3);
		tmpList.add(a2a3);
		tmpList.add(a1a2);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(50d + 10d), calcY(0d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(a3c1);
		tmpList.add(a1a3);
		tmpList.add(a2a3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(50d + 5d), calcY(10d)));
		graph.addEdge(e);

		ISummit b1b2 = new Summit("b1b2", minLengthIntersection);
		ISummit b2b3 = new Summit("b2b3", minLengthIntersection);
		ISummit b1b3 = new Summit("b1b3", maxLengthIntersection);
		ISummit b2c2 = new Summit("b2c2", 2 * maxLengthIntersection + minLengthIntersection);
		ISummit b3d1 = new Summit("b3d1", minLengthIntersection);

		graph.addSummit(b1b2);
		graph.addSummit(b1b3);
		graph.addSummit(b2b3);
		graph.addSummit(b2c2);
		graph.addSummit(b3d1);

		tmpList = new TreeSet<>();
		tmpList.add(a1b1);
		tmpList.add(b1b3);
		tmpList.add(b1b2);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(3d), calcY(50d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(b2c2);
		tmpList.add(b1b2);
		tmpList.add(b2b3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(3d + 10d), calcY(50d + 5d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(b3d1);
		tmpList.add(b1b3);
		tmpList.add(b2b3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(3d), calcY(50d + 10d)));
		graph.addEdge(e);

		ISummit d2f1 = new Summit("d2f1", 2 * maxLengthIntersection + minLengthIntersection);
		ISummit d3f2 = new Summit("d3f2", 2 * maxLengthIntersection + minLengthIntersection);
		ISummit d1d2 = new Summit("d1d2", minLengthIntersection);
		ISummit d2d3 = new Summit("d2d3", minLengthIntersection);
		ISummit d1d3 = new Summit("d1d3", maxLengthIntersection);

		graph.addSummit(d1d2);
		graph.addSummit(d2d3);
		graph.addSummit(d1d3);
		graph.addSummit(d3f2);
		graph.addSummit(d2f1);

		tmpList = new TreeSet<>();
		tmpList.add(b3d1);
		tmpList.add(d1d3);
		tmpList.add(d1d2);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(3d), calcY(70d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(d2f1);
		tmpList.add(d1d2);
		tmpList.add(d2d3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(13d), calcY(75d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(d3f2);
		tmpList.add(d1d3);
		tmpList.add(d2d3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(3d), calcY(80d)));
		graph.addEdge(e);

		ISummit c1c2 = new Summit("c1c2", minLengthIntersection);
		ISummit c2c3 = new Summit("c2c3", minLengthIntersection);
		ISummit c1c3 = new Summit("c1c3", maxLengthIntersection);
		ISummit c3e1 = new Summit("c3e1", maxLengthIntersection);

		graph.addSummit(c1c2);
		graph.addSummit(c2c3);
		graph.addSummit(c1c3);
		graph.addSummit(c3e1);

		tmpList = new TreeSet<>();
		tmpList.add(a3c1);
		tmpList.add(c1c3);
		tmpList.add(c1c2);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(55d), calcY(50d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(b2c2);
		tmpList.add(c1c2);
		tmpList.add(c2c3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(55d - 5d), calcY(50d + 5d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(c3e1);
		tmpList.add(c2c3);
		tmpList.add(c1c3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(55d), calcY(50d + 10d)));
		graph.addEdge(e);

		ISummit e1e2 = new Summit("e1e2", minLengthIntersection);
		ISummit e2e3 = new Summit("e2e3", minLengthIntersection);
		ISummit e1e3 = new Summit("e1e3", maxLengthIntersection);
		ISummit e2h1 = new Summit("e2h1", 2 * maxLengthIntersection + minLengthIntersection);
		ISummit e3g1 = new Summit("e3g1", minLengthIntersection);

		graph.addSummit(e1e2);
		graph.addSummit(e2e3);
		graph.addSummit(e1e3);
		graph.addSummit(e2h1);
		graph.addSummit(e3g1);

		tmpList = new TreeSet<>();
		tmpList.add(c3e1);
		tmpList.add(e1e2);
		tmpList.add(e1e3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(55d), calcY(80d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(e3g1);
		tmpList.add(e1e3);
		tmpList.add(e2e3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(55d), calcY(90d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(e2h1);
		tmpList.add(e1e2);
		tmpList.add(e2e3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(60d), calcY(85d)));
		graph.addEdge(e);

		ISummit g1g2 = new Summit("g1g2", minLengthIntersection);
		ISummit g2g3 = new Summit("g2g3", maxLengthIntersection);
		ISummit g1g3 = new Summit("g1g3", minLengthIntersection);
		ISummit f3g2 = new Summit("f3g2", minLengthIntersection, true);
		ISummit g3h2 = new Summit("g3h2", minLengthIntersection, true);

		graph.addSummit(g1g2);
		graph.addSummit(g2g3);
		graph.addSummit(g1g3);
		graph.addSummit(f3g2);
		graph.addSummit(g3h2);

		tmpList = new TreeSet<>();
		tmpList.add(e3g1);
		tmpList.add(g1g2);
		tmpList.add(g1g3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(55d), calcY(110d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(f3g2);
		tmpList.add(g1g2);
		tmpList.add(g2g3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(50d), calcY(120d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(g3h2);
		tmpList.add(g1g3);
		tmpList.add(g2g3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(60d), calcY(120d)));
		graph.addEdge(e);

		ISummit f1f2 = new Summit("f1f2", minLengthIntersection);
		ISummit f2f3 = new Summit("f2f3", maxLengthIntersection);
		ISummit f1f3 = new Summit("f1f3", minLengthIntersection);

		graph.addSummit(f1f2);
		graph.addSummit(f2f3);
		graph.addSummit(f1f3);

		tmpList = new TreeSet<>();
		tmpList.add(d3f2);
		tmpList.add(f1f2);
		tmpList.add(f2f3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(20d), calcY(120d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(d2f1);
		tmpList.add(f1f2);
		tmpList.add(f1f3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(25d), calcY(110d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(f3g2);
		tmpList.add(f1f3);
		tmpList.add(f2f3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(30d), calcY(120d)));
		graph.addEdge(e);

		ISummit h1h2 = new Summit("h1h2", minLengthIntersection);
		ISummit h2h3 = new Summit("h2h3", maxLengthIntersection);
		ISummit h1h3 = new Summit("h1h3", minLengthIntersection);

		graph.addSummit(h1h2);
		graph.addSummit(h2h3);
		graph.addSummit(h1h3);

		tmpList = new TreeSet<>();
		tmpList.add(a2h3);
		tmpList.add(h1h3);
		tmpList.add(h2h3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(90d), calcY(120d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(e2h1);
		tmpList.add(h1h3);
		tmpList.add(h1h2);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(85d), calcY(110d)));
		graph.addEdge(e);

		tmpList = new TreeSet<>();
		tmpList.add(g3h2);
		tmpList.add(h1h2);
		tmpList.add(h2h3);
		e = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(80d), calcY(120d)));
		graph.addEdge(e);
	}

	/**
	 * Initialize the root layout
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("RootLayout.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the drawing of the map
	 */
	public void showMapOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("MapOverview.fxml"));
			AnchorPane mapOverview = (AnchorPane) loader.load();

			rootLayout.setClip(mapOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the robots, the objectives and create the graph With that
	 * implementation, there will be 2 robots and 5 victims
	 * 
	 * @param primaryStage:
	 *            the stage that will contain the animation
	 */
	@Override
	public void start(Stage primaryStage) {
		List<ISummit> summitList = graph.getSummitList();
		ChatChannel chat = new ChatChannel();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Robot Simulator");

		initRootLayout();
		root = new Group();
		Scene scene = new Scene(root, xLength, yLength, Color.BLACK);
		primaryStage.setScene(scene);

		for (ISummit s : summitList) {
			if (s.getName().equals("a3c1") || s.getName().equals("a2h3") || s.getName().equals("c3e1")
					|| s.getName().equals("b3d1") || s.getName().equals("e3g1")) {
				s.setObjective(true);
				objectives.add(s);
			}
		}

		printGraph();

		showMapOverview();
		Robot r0;
		Robot r1;

		for (ISummit s : summitList) {
			if (s.getName().equals("b2c2")) {
				r0 = new Robot(s, null, chat, this, 0);
				initPrintRobot(r0);
				r0.start();
			} else if (s.getName().equals("d3f2")) {
				r1 = new Robot(s, null, chat, this, 1);
				initPrintRobot(r1);
				r1.start();
			}
		}

		nbRobotsAlive = 2;
	}

	/**
	 * Launch the application
	 */
	public static void main() {
		launch();
	}

	/**
	 * Create the graphic circle representing an edge
	 * 
	 * @param e:
	 *            the edge to be represented
	 * @return the circle representing the edge
	 */
	private Circle createEdge(IEdge e) {
		Circle c = new Circle();
		c.setCenterX(e.getCoordinates().getX());
		c.setCenterY(e.getCoordinates().getY());
		c.setRadius(7);
		c.setStrokeWidth(1);
		if (e.getType().equals(EdgeType.CROSS)) {
			c.setFill(Color.YELLOW);
			c.setStroke(Color.ORANGE);
		} else {
			c.setFill(Color.BLUE);
			c.setStroke(Color.DARKBLUE);
		}
		return c;
	}

	/**
	 * Calculate the absolute value
	 * 
	 * @param x:
	 *            the number to be processed
	 * @return the x absolute value
	 */
	private Double abs(Double x) {
		if (x < 0d)
			return -x;
		return x;
	}

	/**
	 * Print the external curves over a cross
	 * 
	 * @param s:
	 *            the summit to be printed
	 */
	private void printCrosses(ISummit s) {
		IEdge[] e = s.getEnds();
		Arc s12 = new Arc();
		s12.setCenterX((e[0].getCoordinates().getX() + e[1].getCoordinates().getX()) / 2d);
		s12.setCenterY((e[0].getCoordinates().getY() + e[1].getCoordinates().getY()) / 2d);
		if (e[1].getCoordinates().getX() - e[0].getCoordinates().getX() != 0d) {
			s12.setRadiusX(abs(e[1].getCoordinates().getX() - e[0].getCoordinates().getX()) / 2d);
			s12.setRadiusY(20d);
		} else {
			s12.setRadiusX(20d);
			s12.setRadiusY(abs(e[1].getCoordinates().getY() - e[0].getCoordinates().getY()) / 2d);
		}
		s12.setStartAngle(0d);

		s12.setType(ArcType.OPEN);
		s12.setFill(Color.TRANSPARENT);
		s12.setStroke(Color.WHITE);
		s12.setStrokeWidth(2d);

		s12.setLength(360d);

		root.getChildren().add(s12);
	}

	/**
	 * Print crosses This is not well done, but when implementing this, it was not
	 * useful to me to make a better version. When an objective is on an external
	 * curve, it will be painted on the central segment. The central segment will
	 * have the color of the last of the 3 summits of the cross.
	 * 
	 * @param e1:
	 *            end number one of the cross to be printed
	 * @param e2:
	 *            end number two of the cross to be printed
	 */
	private void printCross(IEdge e1, IEdge e2) {
		for (ISummit s0 : e1.getSummits()) {
			for (ISummit s1 : e2.getSummits()) {
				if (!s0.equals(s1) && s0.asSameEnds(s1)) {
					printCrosses(s0);
				}
			}
		}
	}

	/**
	 * Print a summit
	 * 
	 * @param s:
	 *            The summit to be printed
	 */
	private void printSummit(ISummit s) {
		IEdge[] e = s.getEnds();
		Line l = new Line();
		l.setStartX(e[0].getCoordinates().getX());
		l.setStartY(e[0].getCoordinates().getY());
		l.setEndX(e[1].getCoordinates().getX());
		l.setEndY(e[1].getCoordinates().getY());
		if (s.isObjective() && !s.isHospital())
			l.setStroke(Color.RED);
		else if (s.isHospital())
			l.setStroke(Color.GREEN);
		else
			l.setStroke(Color.WHITE);
		l.setStrokeWidth(2);
		root.getChildren().add(l);

		// Case of a cross
		if (e[0].getType().equals(e[1].getType()) && e[0].getType().equals(EdgeType.CROSS)) {
			printCross(e[0], e[1]);
		}
		summitRepresentation.put(s, l);
	}

	/**
	 * Print the graph
	 */
	private void printGraph() {
		// Creation of circles representing crosses and intersections
		List<IEdge> edgeList = graph.getEdgeList();
		List<Circle> circleList = new LinkedList<>();
		for (IEdge e : edgeList) {
			circleList.add(createEdge(e));
		}

		// Create the lines representing roads
		List<ISummit> summitList = graph.getSummitList();
		for (ISummit s : summitList) {
			printSummit(s);
		}

		// Print the circles
		for (Circle c : circleList) {
			root.getChildren().add(c);
		}
	}

	/**
	 * Create the circle and the text associated to a particular robot, and
	 * associate them to the robot
	 * 
	 * @param robot:
	 *            the robot to be printed
	 */
	public void initPrintRobot(Robot robot) {
		Circle circle = new Circle();
		circle.setCenterX(robot.getCoordinates().getX());
		circle.setCenterY(robot.getCoordinates().getY());
		circle.setRadius(5);
		circle.setStrokeWidth(1);
		circle.setFill(Color.CHARTREUSE);
		circle.setStroke(Color.GREEN);
		root.getChildren().add(circle);

		Text text = new Text();
		text.setFill(Color.RED);
		text.setX(robot.getCoordinates().getX());
		text.setY(robot.getCoordinates().getY());
		text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
		root.getChildren().add(text);

		robotMap.put(robot, new RobotReprensentation(circle, text));
	}

	/**
	 * Print the movement of a given robot
	 * 
	 * @param length:
	 *            the time the robot will take to reach the next edge
	 * @param oldC:
	 *            the old position of the robot
	 * @param newC:
	 *            the new position of the robot
	 * @param currentSummit:
	 *            the road the robot will follow to move
	 * @param isALine:
	 *            it must be true if the road is represented by a line (all the
	 *            summits except the external curves on cross)
	 * @param robot:
	 *            the robot that will move
	 * @param objective:
	 *            the name of the final destination of the robot (where it goes)
	 */
	public void printRobotMovement(double length, Coordinates oldC, Coordinates newC, ISummit currentSummit,
			boolean isALine, Robot robot, String objective) {
		IEdge ends[] = currentSummit.getEnds();

		// Initialize the transition
		Path path = new Path();
		path.getElements().add(new MoveTo(oldC.getX(), oldC.getY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setPath(path);
		pathTransition.setNode(robotMap.get(robot).getCircle());
		pathTransition.setCycleCount(1);
		pathTransition.setDelay(Duration.millis(0));
		pathTransition.setDuration(Duration.millis(length));

		PathTransition pathTransitionCopy = new PathTransition();
		pathTransitionCopy.setPath(path);
		pathTransitionCopy.setNode(robotMap.get(robot).getText());
		pathTransitionCopy.setCycleCount(1);
		pathTransitionCopy.setDelay(Duration.millis(0));
		pathTransitionCopy.setDuration(Duration.millis(length));

		robotMap.get(robot).getText().setText(objective);

		if (!isALine) {
			ArcTo arc = new ArcTo();
			arc.setX(newC.getX());
			arc.setY(newC.getY());
			if (ends[1].getCoordinates().getX() - ends[0].getCoordinates().getX() != 0d) {
				arc.setRadiusX(abs(ends[1].getCoordinates().getX() - ends[0].getCoordinates().getX()) / 2d);
				arc.setRadiusY(20d);
			} else {
				arc.setRadiusX(20d);
				arc.setRadiusY(abs(ends[1].getCoordinates().getY() - ends[0].getCoordinates().getY()) / 2d);
			}
			path.getElements().add(arc);
		} else
			path.getElements().add(new LineTo(newC.getX(), newC.getY()));
		pathTransition.play();
		pathTransitionCopy.play();
	}

	/**
	 * When a victim has been picked up, the summit has to change color from red to
	 * white
	 * 
	 * @param currentSummit:
	 *            the summit that has to change it color
	 * @param delay:
	 *            the delay to wait before to change the color
	 */
	public void changeSummitColor(ISummit currentSummit, double delay) {
		StrokeTransition changeSummitColor = new StrokeTransition(Duration.ONE, summitRepresentation.get(currentSummit),
				Color.RED, Color.WHITE);
		changeSummitColor.setDelay(Duration.millis(delay));
		changeSummitColor.play();
	}

	/**
	 * Each robot will say when it finished to the main server, and the server will
	 * know if the simulation has ended or not
	 */
	public synchronized void robotFinished() {
		nbRobotsAlive--;
		if (nbRobotsAlive == 0) {
			isFinished = true;
			System.out.println("FINISHED");
		}
	}
}
