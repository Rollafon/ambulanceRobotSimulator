package ch.makery.address;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import map.Coordinates;
import map.Edge;
import map.EdgeType;
import map.Graph;
import map.IEdge;
import map.IGraph;
import map.ISummit;
import map.Summit;
import robot.IRobot;
import robot.Robot;

public class Main extends Application {
	public static NavigableSet<ISummit> objectives = new TreeSet<>();
	
	private Map<ISummit, Line> summitRepresentation = new HashMap<>();
	private static final int NB_ROBOTS = 6;

	private Stage primaryStage;
	private AnchorPane rootLayout;

	private IGraph graph;
	private Group root = null;

	public Main() {
		graph = new Graph();
		NavigableSet<ISummit> tmpList = new TreeSet<>();

		ISummit s1 = new Summit("s1", 20);
		ISummit s112 = new Summit("s111", 15);
		ISummit s122 = new Summit("s122", 10);
		ISummit s132 = new Summit("s132", 15);
		tmpList.add(s1);
		tmpList.add(s112);
		tmpList.add(s122);
		tmpList.add(s132);
		graph.addSummit(s1);
		graph.addSummit(s112);
		graph.addSummit(s122);
		graph.addSummit(s132);
		IEdge e12 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(200, 200));
		graph.addEdge(e12);
		
		tmpList = new TreeSet<>();
		ISummit s2 = new Summit("s2", 10);
		tmpList.add(s112);
		tmpList.add(s122);
		tmpList.add(s132);
		tmpList.add(s2);
		graph.addSummit(s2);
		IEdge e21 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(300, 200));
		graph.addEdge(e21);

		ISummit s3 = new Summit("s3", 20);
		ISummit s4 = new Summit("s4", 20);
		graph.addSummit(s3);
		graph.addSummit(s4);
		tmpList = new TreeSet<>();
		tmpList.add(s2);
		tmpList.add(s3);
		tmpList.add(s4);
		IEdge e234 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(400, 200));
		graph.addEdge(e234);

		tmpList = new TreeSet<>();
		tmpList.add(s1);
		IEdge endS1 = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(200, 400));
		graph.addEdge(endS1);

		tmpList = new TreeSet<>();
		tmpList.add(s3);
		IEdge endS3 = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(400, 400));
		graph.addEdge(endS3);

		tmpList = new TreeSet<>();
		tmpList.add(s4);
		IEdge endS4 = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(600, 200));
		graph.addEdge(endS4);
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showGraphOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MapOverview.fxml"));
			AnchorPane mapOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setClip(mapOverview);
			/*
			 * // Give the controller access to the main app. MapOverviewController
			 * controller = loader.getController(); controller.setMain(this);
			 */

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		Random r = new Random();
		List<IRobot> robotList = new LinkedList<>();
		List<ISummit> summitList = graph.getSummitList();
		int nbSummits = summitList.size();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Robot Simulator");

		initRootLayout();
		root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);

		for (int i = 0; i < NB_ROBOTS; i++) {
			boolean alreadyUsed = false;
			ISummit s = summitList.get(r.nextInt(nbSummits));
			IRobot robot = new Robot(s, null, null, this);
			robotList.add(robot);
			for (int j = 0; j < i && !alreadyUsed; j++) {
				if (robot.getCoordinates().equals(robotList.get(j).getCoordinates())) {
					robotList.remove(robot);
					alreadyUsed = true;
				}
			}
		}
		for (int j = 0; j <= r.nextInt(nbSummits + 1) + 1; j++) {
			ISummit s = summitList.get(r.nextInt(nbSummits));
			s.setObjective(true);
			objectives.add(s);
		}
		
		printGraph();

		for (int i = 0; i < robotList.size(); i++) {
			robotList.get(i).run();
		}

		showGraphOverview();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Circle createEdges(IEdge e) {
		Circle c = new Circle();
		c.setCenterX(e.getCoordinates().getX());
		c.setCenterY(e.getCoordinates().getY());
		c.setRadius(10);
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

	public void printSummit(ISummit s) {
		IEdge[] e = s.getEnds();
		Line l = new Line();
		l.setStartX(e[0].getCoordinates().getX());
		l.setStartY(e[0].getCoordinates().getY());
		l.setEndX(e[1].getCoordinates().getX());
		l.setEndY(e[1].getCoordinates().getY());
		if (s.isObjective())
			l.setStroke(Color.RED);
		else
			l.setStroke(Color.WHITE);
		l.setStrokeWidth(2);
		root.getChildren().add(l);
		if (e[0].getType().equals(e[1].getType()) && e[0].getType().equals(EdgeType.CROSS)) {
			printCrosses(s);
		}
		summitRepresentation.put(s, l);
	}

	public void printCrosses(ISummit s) {
		IEdge[] e = s.getEnds();
		Arc s12 = new Arc();
		s12.setCenterX((e[0].getCoordinates().getX() + e[1].getCoordinates().getX()) / 2);
		s12.setCenterY((e[0].getCoordinates().getY() + e[1].getCoordinates().getY()) / 2);
		if (e[1].getCoordinates().getX() - e[0].getCoordinates().getX() != 0) {
			s12.setRadiusX((e[1].getCoordinates().getX() - e[0].getCoordinates().getX()) / 2);
			s12.setRadiusY(20);
		} else {
			s12.setRadiusX(20);
			s12.setRadiusY((e[1].getCoordinates().getY() - e[0].getCoordinates().getY()) / 2);
		}
		s12.setStartAngle(0);

		s12.setType(ArcType.OPEN);
		s12.setFill(Color.TRANSPARENT);
		s12.setStroke(Color.WHITE);
		s12.setStrokeWidth(2);

		s12.setLength(360);

		root.getChildren().add(s12);
	}

	public void printGraph() {

		// Creation of circles representing crosses and intersections
		List<IEdge> edgeList = graph.getEdgeList();
		List<Circle> circleList = new LinkedList<>();
		for (IEdge e : edgeList) {
			circleList.add(createEdges(e));
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

	public Circle initPrintRobot(Coordinates coord) {
		Circle circle = new Circle();
		circle.setCenterX(coord.getX());
		circle.setCenterY(coord.getY());
		circle.setRadius(5);
		circle.setStrokeWidth(1);
		circle.setFill(Color.CHARTREUSE);
		circle.setStroke(Color.GREEN);
		root.getChildren().add(circle);

		return circle;
	}

	public void removePrintedRobot(final Circle c) {
		root.getChildren().remove(c);
	}

	public void changeSummitColor(ISummit currentSummit) {
		Line l = summitRepresentation.get(currentSummit);
		l.setStroke(Color.WHITE);
	}
}
