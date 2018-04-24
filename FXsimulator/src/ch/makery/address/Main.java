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
	private static final int NB_ROBOTS = 157; // 157 is the number of summit on the Nancy graph

	private Stage primaryStage;
	private AnchorPane rootLayout;

	private IGraph graph;
	private Group root = null;

	private final double xMin = 50;
	private final double xMax = 1850;
	private final double xLength = xMax - xMin;
	private final double yMin = 50;
	private final double yMax = 1000;
	private final double yLength = yMax - yMin;
	private final double proportionMaxX = 207;
	private final double proportionMaxY = 157;
	
	private final double timeMinPerSummit = 10;
	private final double timeMinCross = 8*timeMinPerSummit;
	private final double timeMaxCross = timeMinCross + timeMinCross/2;
	private final double timeMinIntersection = 4*timeMinPerSummit;
	private final double timeMaxIntersection = timeMinIntersection + timeMinIntersection/4;
	
	private double calcX(float proportion) {
		return ((double) xMin + (proportion/proportionMaxX) * xLength);
	}
	
	private double calcY(float proportion) {
		return ((double) yMin + (proportion/proportionMaxY) * yLength);
	}

	/*
	 * The name of the variables are the same than in the jointed map 
	 * (Nancy Map.png)
	 * Be careful: in a cross, the second summit by alphabetic order has to be the shorter
	 */
	public Main() {
		graph = new Graph();
		NavigableSet<ISummit> tmpList = new TreeSet<>();

		ISummit s1 = new Summit("s1", 3*timeMinCross);
		ISummit s112 = new Summit("s111", timeMaxCross);
		ISummit s122 = new Summit("s122", timeMinCross);
		ISummit s132 = new Summit("s132", timeMaxCross);
		graph.addSummit(s1);
		graph.addSummit(s112);
		graph.addSummit(s122);
		graph.addSummit(s132);
		tmpList.add(s1);
		tmpList.add(s112);
		tmpList.add(s122);
		tmpList.add(s132);
		IEdge eC12 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(3), calcY(0)));
		graph.addEdge(eC12);

		tmpList = new TreeSet<>();
		ISummit s2 = new Summit("s2", 2*timeMinPerSummit);
		graph.addSummit(s2);
		tmpList.add(s112);
		tmpList.add(s122);
		tmpList.add(s132);
		tmpList.add(s2);
		IEdge eC21 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(19), calcY(0)));
		graph.addEdge(eC21);

		tmpList = new TreeSet<>();
		ISummit s213 = new Summit("s213", timeMaxCross);
		ISummit s223 = new Summit("s223", timeMinCross);
		ISummit s233 = new Summit("s233", timeMaxCross);
		graph.addSummit(s213);
		graph.addSummit(s223);
		graph.addSummit(s233);
		tmpList.add(s2);
		tmpList.add(s213);
		tmpList.add(s223);
		tmpList.add(s233);
		IEdge eC23 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(23), calcY(0)));
		graph.addEdge(eC23);

		tmpList = new TreeSet<>();
		ISummit s3 = new Summit("s3", 2*timeMinPerSummit);
		graph.addSummit(s3);
		tmpList.add(s3);
		tmpList.add(s233);
		tmpList.add(s223);
		tmpList.add(s213);
		IEdge eC32 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(38), calcY(0)));
		graph.addEdge(eC32);

		tmpList = new TreeSet<>();
		ISummit s34 = new Summit("s34", timeMinIntersection);
		ISummit s35 = new Summit("s35", timeMaxIntersection);
		graph.addSummit(s34);
		graph.addSummit(s35);
		tmpList.add(s3);
		tmpList.add(s34);
		tmpList.add(s35);
		IEdge eI345 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(43), calcY(0)));
		graph.addEdge(eI345);

		tmpList = new TreeSet<>();
		ISummit s4 = new Summit("s4", 4*s3.getLength());
		ISummit s45 = new Summit("s45", timeMaxIntersection);
		graph.addSummit(s4);
		graph.addSummit(s45);
		tmpList.add(s34);
		tmpList.add(s4);
		tmpList.add(s45);
		IEdge eI435 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(53), calcY(0)));
		graph.addEdge(eI435);

		tmpList = new TreeSet<>();
		ISummit s5 = new Summit("s5", 2*s4.getLength());
		graph.addSummit(s5);
		tmpList.add(s35);
		tmpList.add(s45);
		tmpList.add(s5);
		IEdge eI534 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(48), calcY(6)));
		graph.addEdge(eI534);

		tmpList = new TreeSet<>();
		ISummit s416 = new Summit("s416", timeMaxCross);
		ISummit s426 = new Summit("s426", timeMinCross);
		ISummit s436 = new Summit("s436", timeMaxCross);
		graph.addSummit(s416);
		graph.addSummit(s426);
		graph.addSummit(s436);
		tmpList.add(s416);
		tmpList.add(s426);
		tmpList.add(s436);
		tmpList.add(s4);
		IEdge eC46 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(69), calcY(0)));
		graph.addEdge(eC46);

		tmpList = new TreeSet<>();
		ISummit s6 = new Summit("s6", 2*s4.getLength());
		graph.addSummit(s6);
		tmpList.add(s6);
		tmpList.add(s436);
		tmpList.add(s426);
		tmpList.add(s416);
		IEdge eC64 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(85), calcY(0)));
		graph.addEdge(eC64);

		tmpList = new TreeSet<>();
		ISummit s617 = new Summit("s617", timeMaxCross);
		ISummit s627 = new Summit("s627", timeMinCross);
		ISummit s637 = new Summit("s637", timeMaxCross);
		graph.addSummit(s637);
		graph.addSummit(s627);
		graph.addSummit(s617);
		tmpList.add(s617);
		tmpList.add(s627);
		tmpList.add(s637);
		tmpList.add(s6);
		IEdge eC67 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(121), calcY(0)));
		graph.addEdge(eC67);

		tmpList = new TreeSet<>();
		ISummit s7 = new Summit("s7", s4.getLength());
		graph.addSummit(s7);
		tmpList.add(s617);
		tmpList.add(s627);
		tmpList.add(s637);
		tmpList.add(s7);
		IEdge eC76 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(137), calcY(0)));
		graph.addEdge(eC76);

		tmpList = new TreeSet<>();
		ISummit s78 = new Summit("s78", timeMinIntersection);
		ISummit s79 = new Summit("s79", timeMaxIntersection);
		graph.addSummit(s78);
		graph.addSummit(s79);
		tmpList.add(s7);
		tmpList.add(s78);
		tmpList.add(s79);
		IEdge eI789 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(154), calcY(0)));
		graph.addEdge(eI789);

		tmpList = new TreeSet<>();
		ISummit s8 = new Summit("s8", s3.getLength());
		ISummit s89 = new Summit("s89", timeMaxIntersection);
		graph.addSummit(s8);
		graph.addSummit(s89);
		tmpList.add(s78);
		tmpList.add(s89);
		tmpList.add(s8);
		IEdge eI879 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(164), calcY(0)));
		graph.addEdge(eI879);

		tmpList = new TreeSet<>();
		ISummit s9 = new Summit("s9", s5.getLength());
		graph.addSummit(s9);
		tmpList.add(s79);
		tmpList.add(s89);
		tmpList.add(s9);
		IEdge eI978 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(159), calcY(6)));
		graph.addEdge(eI978);

		tmpList = new TreeSet<>();
		ISummit s81A = new Summit("s81A", timeMaxCross);
		ISummit s82A = new Summit("s82A", timeMinCross);
		ISummit s83A = new Summit("s83A", timeMaxCross);
		graph.addSummit(s81A);
		graph.addSummit(s82A);
		graph.addSummit(s83A);
		tmpList.add(s8);
		tmpList.add(s81A);
		tmpList.add(s82A);
		tmpList.add(s83A);
		IEdge eC8A = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(168), calcY(0)));
		graph.addEdge(eC8A);

		tmpList = new TreeSet<>();
		ISummit sA = new Summit("sA", s2.getLength());
		graph.addSummit(sA);
		tmpList.add(s81A);
		tmpList.add(s82A);
		tmpList.add(s83A);
		tmpList.add(sA);
		IEdge eCA8 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(184), calcY(0)));
		graph.addEdge(eCA8);

		tmpList = new TreeSet<>();
		ISummit sA1B = new Summit("sA1B", timeMaxCross);
		ISummit sA2B = new Summit("sA2B", timeMinCross);
		ISummit sA3B = new Summit("sA3B", timeMaxCross);
		graph.addSummit(sA1B);
		graph.addSummit(sA2B);
		graph.addSummit(sA3B);
		tmpList.add(sA);
		tmpList.add(sA1B);
		tmpList.add(sA2B);
		tmpList.add(sA3B);
		IEdge eCAB = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(188), calcY(0)));
		graph.addEdge(eCAB);

		tmpList = new TreeSet<>();
		ISummit sB = new Summit("sB", s1.getLength());
		graph.addSummit(sB);
		tmpList.add(sA1B);
		tmpList.add(sA2B);
		tmpList.add(sA3B);
		tmpList.add(sB);
		IEdge eCBA = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(204), calcY(0)));
		graph.addEdge(eCBA);

		tmpList = new TreeSet<>();
		ISummit s1C = new Summit("s1C", timeMaxIntersection);
		ISummit s1D = new Summit("s1D", timeMinIntersection);
		graph.addSummit(s1C);
		graph.addSummit(s1D);
		tmpList.add(s1);
		tmpList.add(s1C);
		tmpList.add(s1D);
		IEdge eI1CD = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(18), calcY(38)));
		graph.addEdge(eI1CD);

		tmpList = new TreeSet<>();
		ISummit sC = new Summit("sC", timeMinPerSummit);
		ISummit sCD = new Summit("sCD", timeMaxIntersection);
		graph.addSummit(sC);
		graph.addSummit(sCD);
		tmpList.add(s1C);
		tmpList.add(sC);
		tmpList.add(sCD);
		IEdge eIC1D = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(23), calcY(43)));
		graph.addEdge(eIC1D);

		tmpList = new TreeSet<>();
		ISummit sD = new Summit("sD", s4.getLength());
		graph.addSummit(sD);
		tmpList.add(s1D);
		tmpList.add(sCD);
		tmpList.add(sD);
		IEdge eID1C = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(18), calcY(48)));
		graph.addEdge(eID1C);

		tmpList = new TreeSet<>();
		ISummit sC1E = new Summit("sC1E", timeMaxCross);
		ISummit sC2E = new Summit("sC2E", timeMinCross);
		ISummit sC3E = new Summit("sC3E", timeMaxCross);
		graph.addSummit(sC1E);
		graph.addSummit(sC2E);
		graph.addSummit(sC3E);
		tmpList.add(sC);
		tmpList.add(sC1E);
		tmpList.add(sC2E);
		tmpList.add(sC3E);
		IEdge eCCE = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(27), calcY(43)));
		graph.addEdge(eCCE);

		tmpList = new TreeSet<>();
		ISummit sE = new Summit("sE", timeMinPerSummit);
		graph.addSummit(sE);
		tmpList.add(sC1E);
		tmpList.add(sC2E);
		tmpList.add(sC3E);
		tmpList.add(sE);
		IEdge eCEC = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(39), calcY(43)));
		graph.addEdge(eCEC);

		tmpList = new TreeSet<>();
		ISummit s5E = new Summit("s5E", timeMaxIntersection);
		ISummit sEF = new Summit("sEF", timeMinIntersection);
		graph.addSummit(s5E);
		graph.addSummit(sEF);
		tmpList.add(sE);
		tmpList.add(s5E);
		tmpList.add(sEF);
		IEdge eIE5F = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(43), calcY(43)));
		graph.addEdge(eIE5F);

		tmpList = new TreeSet<>();
		ISummit s5F = new Summit("s5F", timeMaxIntersection);
		graph.addSummit(s5F);
		tmpList.add(s5);
		tmpList.add(s5E);
		tmpList.add(s5F);
		IEdge eI5EF = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(48), calcY(37)));
		graph.addEdge(eI5EF);

		tmpList = new TreeSet<>();
		ISummit sF = new Summit("sF", timeMinPerSummit);
		graph.addSummit(sF);
		tmpList.add(s5F);
		tmpList.add(sEF);
		tmpList.add(sF);
		IEdge eIF5E = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(53), calcY(43)));
		graph.addEdge(eIF5E);

		tmpList = new TreeSet<>();
		ISummit sFG = new Summit("sFG", timeMaxIntersection);
		ISummit sFH = new Summit("sFH", timeMaxIntersection);
		graph.addSummit(sFG);
		graph.addSummit(sFH);
		tmpList.add(sF);
		tmpList.add(sFG);
		tmpList.add(sFH);
		IEdge eIFGH = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(56), calcY(43)));
		graph.addEdge(eIFGH);

		tmpList = new TreeSet<>();
		ISummit sG = new Summit("sG", 2*s5.getLength() + 2*s4.getLength());
		ISummit sGH = new Summit("sGH", timeMinIntersection);
		graph.addSummit(sG);
		graph.addSummit(sGH);
		tmpList.add(sG);
		tmpList.add(sFG);
		tmpList.add(sGH);
		IEdge eIGFH = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(61), calcY(38)));
		graph.addEdge(eIGFH);

		tmpList = new TreeSet<>();
		ISummit sH = new Summit("sH", 4*timeMinPerSummit);
		graph.addSummit(sH);
		tmpList.add(sFH);
		tmpList.add(sGH);
		tmpList.add(sH);
		IEdge eIHFG = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(61), calcY(48)));
		graph.addEdge(eIHFG);

		tmpList = new TreeSet<>();
		ISummit sDI = new Summit("sDI", timeMaxIntersection);
		ISummit sDJ = new Summit("sDJ", timeMaxIntersection);
		graph.addSummit(sDI);
		graph.addSummit(sDJ);
		tmpList.add(sDJ);
		tmpList.add(sD);
		tmpList.add(sDI);
		IEdge eIDIJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(18), calcY(64)));
		graph.addEdge(eIDIJ);

		tmpList = new TreeSet<>();
		ISummit sI = new Summit("sI", timeMinPerSummit);
		ISummit sIJ = new Summit("sIJ", timeMinIntersection);
		graph.addSummit(sI);
		graph.addSummit(sIJ);
		tmpList.add(sDI);
		tmpList.add(sI);
		tmpList.add(sIJ);
		IEdge eIIDJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(13), calcY(69)));
		graph.addEdge(eIIDJ);

		tmpList = new TreeSet<>();
		ISummit sJ = new Summit("sJ", 3*timeMinPerSummit);
		graph.addSummit(sJ);
		tmpList.add(sJ);
		tmpList.add(sDJ);
		tmpList.add(sIJ);
		IEdge eIJDI = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(23), calcY(69)));
		graph.addEdge(eIJDI);

		tmpList = new TreeSet<>();
		ISummit sIK = new Summit("sIK", timeMinIntersection);
		ISummit sIL = new Summit("sIL", timeMaxIntersection);
		graph.addSummit(sIK);
		graph.addSummit(sIL);
		tmpList.add(sI);
		tmpList.add(sIK);
		tmpList.add(sIL);
		IEdge eIIKL = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(10), calcY(69)));
		graph.addEdge(eIIKL);

		tmpList = new TreeSet<>();
		ISummit sK = new Summit("sK", 2*timeMinPerSummit);
		ISummit sKL = new Summit("sKL", timeMaxIntersection);
		graph.addSummit(sK);
		graph.addSummit(sKL);
		tmpList.add(sK);
		tmpList.add(sKL);
		tmpList.add(sIK);
		IEdge eIKIL = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(0), calcY(69)));
		graph.addEdge(eIKIL);

		tmpList = new TreeSet<>();
		tmpList.add(sK);
		IEdge endK = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(-5), calcY(69)));
		graph.addEdge(endK);

		tmpList = new TreeSet<>();
		ISummit sL = new Summit("sL", sD.getLength() + s3.getLength());
		graph.addSummit(sL);
		tmpList.add(sKL);
		tmpList.add(sIL);
		tmpList.add(sL);
		IEdge eILIK = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(5), calcY(75)));
		graph.addEdge(eILIK);

		tmpList = new TreeSet<>();
		ISummit sJ1M = new Summit("sJ1M", timeMaxCross);
		ISummit sJ2M = new Summit("sJ2M", timeMinCross);
		ISummit sJ3M = new Summit("sJ3M", timeMaxCross);
		graph.addSummit(sJ3M);
		graph.addSummit(sJ2M);
		graph.addSummit(sJ1M);
		tmpList.add(sJ);
		tmpList.add(sJ1M);
		tmpList.add(sJ2M);
		tmpList.add(sJ3M);
		IEdge eCJM = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(30), calcY(69)));
		graph.addEdge(eCJM);

		tmpList = new TreeSet<>();
		ISummit sM = new Summit("sM", sK.getLength());
		graph.addSummit(sM);
		tmpList.add(sM);
		tmpList.add(sJ1M);
		tmpList.add(sJ2M);
		tmpList.add(sJ3M);
		IEdge eCMJ = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(46), calcY(69)));
		graph.addEdge(eCMJ);

		tmpList = new TreeSet<>();
		tmpList.add(sM);
		IEdge endM = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(51), calcY(69)));
		graph.addEdge(endM);

		tmpList = new TreeSet<>();
		ISummit sH1N = new Summit("sH1N", timeMaxCross);
		ISummit sH2N = new Summit("sH2N", timeMinCross);
		ISummit sH3N = new Summit("sH3N", timeMaxCross);
		graph.addSummit(sH1N);
		graph.addSummit(sH2N);
		graph.addSummit(sH3N);
		tmpList.add(sH);
		tmpList.add(sH1N);
		tmpList.add(sH2N);
		tmpList.add(sH3N);
		IEdge eCHN = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(61), calcY(58)));
		graph.addEdge(eCHN);

		tmpList = new TreeSet<>();
		ISummit sN = new Summit("sN", sL.getLength() + s4.getLength() + s3.getLength());
		graph.addSummit(sN);
		tmpList.add(sH1N);
		tmpList.add(sH2N);
		tmpList.add(sH3N);
		tmpList.add(sN);
		IEdge eCNH = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(61), calcY(75)));
		graph.addEdge(eCNH);

		tmpList = new TreeSet<>();
		ISummit sG1O = new Summit("sG1O", timeMaxCross);
		ISummit sG2O = new Summit("sG2O", timeMinCross);
		ISummit sG3O = new Summit("sG3O", timeMaxCross);
		graph.addSummit(sG1O);
		graph.addSummit(sG2O);
		graph.addSummit(sG3O);
		tmpList.add(sG);
		tmpList.add(sG1O);
		tmpList.add(sG2O);
		tmpList.add(sG3O);
		IEdge eCGO = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(95), calcY(92)));
		graph.addEdge(eCGO);

		tmpList = new TreeSet<>();
		ISummit sO = new Summit("sO", sG.getLength());
		graph.addSummit(sO);
		tmpList.add(sG1O);
		tmpList.add(sG2O);
		tmpList.add(sG3O);
		tmpList.add(sO);
		IEdge eCOG = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(111), calcY(92)));
		graph.addEdge(eCOG);

		tmpList = new TreeSet<>();
		ISummit sOQ = new Summit("sOQ", timeMinIntersection);
		ISummit sOP = new Summit("sOP", timeMaxIntersection);
		graph.addSummit(sOP);
		graph.addSummit(sOQ);
		tmpList.add(sO);
		tmpList.add(sOQ);
		tmpList.add(sOP);
		IEdge eIOPQ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(146), calcY(37)));
		graph.addEdge(eIOPQ);

		tmpList = new TreeSet<>();
		ISummit sP = new Summit("sP", sF.getLength());
		ISummit sPQ = new Summit("sPQ", timeMaxIntersection);
		graph.addSummit(sP);
		graph.addSummit(sPQ);
		tmpList.add(sP);
		tmpList.add(sOP);
		tmpList.add(sPQ);
		IEdge eIPOQ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(150), calcY(43)));
		graph.addEdge(eIPOQ);

		tmpList = new TreeSet<>();
		ISummit sQ = new Summit("sQ", sH.getLength());
		graph.addSummit(sQ);
		tmpList.add(sQ);
		tmpList.add(sOQ);
		tmpList.add(sPQ);
		IEdge eIQOP = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(146), calcY(48)));
		graph.addEdge(eIQOP);

		tmpList = new TreeSet<>();
		ISummit s9P = new Summit("s9P", timeMaxIntersection);
		ISummit s9R = new Summit("s9R", timeMaxIntersection);
		graph.addSummit(s9R);
		graph.addSummit(s9P);
		tmpList.add(s9);
		tmpList.add(s9P);
		tmpList.add(s9R);
		IEdge eI9PR = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(159), calcY(37)));
		graph.addEdge(eI9PR);

		tmpList = new TreeSet<>();
		ISummit sPR = new Summit("sPR", timeMinIntersection);
		graph.addSummit(sPR);
		tmpList.add(s9P);
		tmpList.add(sPR);
		tmpList.add(sP);
		IEdge eIP9R = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(154), calcY(43)));
		graph.addEdge(eIP9R);

		tmpList = new TreeSet<>();
		ISummit sR = new Summit("sR", sE.getLength());
		graph.addSummit(sR);
		tmpList.add(s9R);
		tmpList.add(sPR);
		tmpList.add(sR);
		IEdge eIR9P = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(163), calcY(43)));
		graph.addEdge(eIR9P);

		tmpList = new TreeSet<>();
		ISummit sR1S = new Summit("sR1S", timeMaxCross);
		ISummit sR2S = new Summit("sR2S", timeMinCross);
		ISummit sR3S = new Summit("sR3S", timeMaxCross);
		graph.addSummit(sR1S);
		graph.addSummit(sR2S);
		graph.addSummit(sR3S);
		tmpList.add(sR);
		tmpList.add(sR1S);
		tmpList.add(sR2S);
		tmpList.add(sR3S);
		IEdge eCRS = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(166), calcY(43)));
		graph.addEdge(eCRS);

		tmpList = new TreeSet<>();
		ISummit sS = new Summit("sS", sC.getLength());
		graph.addSummit(sS);
		tmpList.add(sR1S);
		tmpList.add(sR2S);
		tmpList.add(sR3S);
		tmpList.add(sS);
		IEdge eCSR = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(181), calcY(43)));
		graph.addEdge(eCSR);

		tmpList = new TreeSet<>();
		ISummit sBS = new Summit("sBS", timeMaxIntersection);
		ISummit sST = new Summit("sST", timeMaxIntersection);
		graph.addSummit(sBS);
		graph.addSummit(sST);
		tmpList.add(sS);
		tmpList.add(sBS);
		tmpList.add(sST);
		IEdge eISBT = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(184), calcY(43)));
		graph.addEdge(eISBT);

		tmpList = new TreeSet<>();
		ISummit sBT = new Summit("sBT", timeMinIntersection);
		graph.addSummit(sBT);
		tmpList.add(sB);
		tmpList.add(sBS);
		tmpList.add(sBT);
		IEdge eIBST = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(189), calcY(37)));
		graph.addEdge(eIBST);

		tmpList = new TreeSet<>();
		ISummit sT = new Summit("sT", sD.getLength());
		graph.addSummit(sT);
		tmpList.add(sT);
		tmpList.add(sST);
		tmpList.add(sBT);
		IEdge eITBS = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(189), calcY(48)));
		graph.addEdge(eITBS);

		tmpList = new TreeSet<>();
		ISummit sQ1U = new Summit("sQ1U", timeMaxCross);
		ISummit sQ2U = new Summit("sQ2U", timeMinCross);
		ISummit sQ3U = new Summit("sQ3U", timeMaxCross);
		graph.addSummit(sQ1U);
		graph.addSummit(sQ2U);
		graph.addSummit(sQ3U);
		tmpList.add(sQ);
		tmpList.add(sQ1U);
		tmpList.add(sQ2U);
		tmpList.add(sQ3U);
		IEdge eCQU = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(146), calcY(58)));
		graph.addEdge(eCQU);

		tmpList = new TreeSet<>();
		ISummit sU = new Summit("sU", sN.getLength());
		graph.addSummit(sQ1U);
		graph.addSummit(sQ2U);
		graph.addSummit(sQ3U);
		graph.addSummit(sU);
		tmpList.add(sQ1U);
		tmpList.add(sQ2U);
		tmpList.add(sQ3U);
		tmpList.add(sU);
		IEdge eCUQ = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(146), calcY(75)));
		graph.addEdge(eCUQ);

		tmpList = new TreeSet<>();
		ISummit sTV = new Summit("sTV", timeMaxIntersection);
		ISummit sTW = new Summit("sTW", timeMaxIntersection);
		graph.addSummit(sTV);
		graph.addSummit(sTW);
		tmpList.add(sT);
		tmpList.add(sTV);
		tmpList.add(sTW);
		IEdge eITVW = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(189), calcY(63)));
		graph.addEdge(eITVW);

		tmpList = new TreeSet<>();
		ISummit sV = new Summit("sV", sJ.getLength());
		ISummit sVW = new Summit("sVW", timeMinIntersection);
		graph.addSummit(sV);
		graph.addSummit(sVW);
		tmpList.add(sTV);
		tmpList.add(sV);
		tmpList.add(sVW);
		IEdge eIVTW = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(184), calcY(69)));
		graph.addEdge(eIVTW);

		tmpList = new TreeSet<>();
		ISummit sW = new Summit("sW", sI.getLength());
		graph.addSummit(sW);
		tmpList.add(sW);
		tmpList.add(sTW);
		tmpList.add(sVW);
		IEdge eIWTV = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(194), calcY(69)));
		graph.addEdge(eIWTV);

		tmpList = new TreeSet<>();
		ISummit sV1X = new Summit("sV1X", timeMaxCross);
		ISummit sV2X = new Summit("sV2X", timeMinCross);
		ISummit sV3X = new Summit("sV3X", timeMaxCross);
		graph.addSummit(sV1X);
		graph.addSummit(sV2X);
		graph.addSummit(sV3X);
		tmpList.add(sV);
		tmpList.add(sV1X);
		tmpList.add(sV2X);
		tmpList.add(sV3X);
		IEdge eCVX = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(176), calcY(69)));
		graph.addEdge(eCVX);

		tmpList = new TreeSet<>();
		ISummit sX = new Summit("sX", sM.getLength());
		graph.addSummit(sX);
		tmpList.add(sX);
		tmpList.add(sV1X);
		tmpList.add(sV2X);
		tmpList.add(sV3X);
		IEdge eCXV = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(162), calcY(69)));
		graph.addEdge(eCXV);

		tmpList = new TreeSet<>();
		tmpList.add(sX);
		IEdge endX = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(156), calcY(69)));
		graph.addEdge(endX);

		tmpList = new TreeSet<>();
		ISummit sWY = new Summit("sWY", timeMinIntersection);
		ISummit sWZ = new Summit("sWZ", timeMaxIntersection);
		graph.addSummit(sWY);
		graph.addSummit(sWZ);
		tmpList.add(sW);
		tmpList.add(sWY);
		tmpList.add(sWZ);
		IEdge eIWYZ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(197), calcY(69)));
		graph.addEdge(eIWYZ);

		tmpList = new TreeSet<>();
		ISummit sY = new Summit("sY", sK.getLength());
		ISummit sYZ = new Summit("sYZ", timeMaxIntersection);
		graph.addSummit(sY);
		graph.addSummit(sYZ);
		tmpList.add(sY);
		tmpList.add(sYZ);
		tmpList.add(sWY);
		IEdge eIYWZ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(207), calcY(69)));
		graph.addEdge(eIYWZ);

		tmpList = new TreeSet<>();
		ISummit sZ = new Summit("sZ", sL.getLength());
		graph.addSummit(sZ);
		tmpList.add(sZ);
		tmpList.add(sWZ);
		tmpList.add(sYZ);
		IEdge eIZWY = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(202), calcY(75)));
		graph.addEdge(eIZWY);

		tmpList = new TreeSet<>();
		tmpList.add(sY);
		IEdge endY = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(213), calcY(69)));
		graph.addEdge(endY);

		tmpList = new TreeSet<>();
		ISummit sLAB = new Summit("sLAB", timeMinIntersection);
		ISummit sLAA = new Summit("sLAA", timeMaxIntersection);
		graph.addSummit(sLAA);
		graph.addSummit(sLAB);
		tmpList.add(sL);
		tmpList.add(sLAA);
		tmpList.add(sLAB);
		IEdge eILAAAB = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(5), calcY(98)));
		graph.addEdge(eILAAAB);

		tmpList = new TreeSet<>();
		ISummit sAA = new Summit("sAA", 2*timeMinPerSummit);
		ISummit sAAAB = new Summit("sAAAB", timeMaxIntersection);
		graph.addSummit(sAA);
		graph.addSummit(sAAAB);
		tmpList.add(sLAA);
		tmpList.add(sAA);
		tmpList.add(sAAAB);
		IEdge eIAALAB = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(10), calcY(103)));
		graph.addEdge(eIAALAB);

		tmpList = new TreeSet<>();
		ISummit sAB = new Summit("sAB", sL.getLength() + timeMinPerSummit);
		graph.addSummit(sAB);
		tmpList.add(sAB);
		tmpList.add(sLAB);
		tmpList.add(sAAAB);
		IEdge eIABLAA = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(5), calcY(108)));
		graph.addEdge(eIABLAA);

		tmpList = new TreeSet<>();
		ISummit sAA1AC = new Summit("sAA1AC", timeMaxCross);
		ISummit sAA2AC = new Summit("sAA2AC", timeMinCross);
		ISummit sAA3AC = new Summit("sAA3AC", timeMaxCross);
		graph.addSummit(sAA1AC);
		graph.addSummit(sAA2AC);
		graph.addSummit(sAA3AC);
		tmpList.add(sAA);
		tmpList.add(sAA1AC);
		tmpList.add(sAA2AC);
		tmpList.add(sAA3AC);
		IEdge eCAAAC = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(15), calcY(103)));
		graph.addEdge(eCAAAC);

		tmpList = new TreeSet<>();
		ISummit sAC = new Summit("sAC", sAA.getLength());
		graph.addSummit(sAC);
		tmpList.add(sAC);
		tmpList.add(sAA1AC);
		tmpList.add(sAA2AC);
		tmpList.add(sAA3AC);
		IEdge eCACAA = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(30), calcY(103)));
		graph.addEdge(eCACAA);

		tmpList = new TreeSet<>();
		ISummit sNAC = new Summit("sNAC", timeMaxIntersection);
		ISummit sACAD = new Summit("sACAD", timeMinIntersection);
		graph.addSummit(sNAC);
		graph.addSummit(sACAD);
		tmpList.add(sNAC);
		tmpList.add(sACAD);
		tmpList.add(sAC);
		IEdge eIACNAD = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(36), calcY(103)));
		graph.addEdge(eIACNAD);

		tmpList = new TreeSet<>();
		ISummit sNAD = new Summit("sNAD", timeMaxIntersection);
		graph.addSummit(sNAD);
		tmpList.add(sN);
		tmpList.add(sNAD);
		tmpList.add(sNAC);
		IEdge eINACAD = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(40), calcY(97)));
		graph.addEdge(eINACAD);

		tmpList = new TreeSet<>();
		ISummit sAD = new Summit("sAD", sAA.getLength());
		graph.addSummit(sAD);
		tmpList.add(sAD);
		tmpList.add(sNAD);
		tmpList.add(sACAD);
		IEdge eIADNAC = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(45), calcY(103)));
		graph.addEdge(eIADNAC);

		tmpList = new TreeSet<>();
		ISummit sAD1AE = new Summit("sAD1AE", timeMaxCross);
		ISummit sAD2AE = new Summit("sAD2AE", timeMinCross);
		ISummit sAD3AE = new Summit("sAD3AE", timeMaxCross);
		graph.addSummit(sAD1AE);
		graph.addSummit(sAD2AE);
		graph.addSummit(sAD3AE);
		tmpList.add(sAD1AE);
		tmpList.add(sAD2AE);
		tmpList.add(sAD3AE);
		tmpList.add(sAD);
		IEdge eCADAE = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(50), calcY(103)));
		graph.addEdge(eCADAE);

		tmpList = new TreeSet<>();
		ISummit sAE = new Summit("sAE", 2*s4.getLength() + 2*sAB.getLength() + timeMinPerSummit);
		graph.addSummit(sAE);
		tmpList.add(sAE);
		tmpList.add(sAD1AE);
		tmpList.add(sAD2AE);
		tmpList.add(sAD3AE);
		IEdge eCAEAD = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(65), calcY(103)));
		graph.addEdge(eCAEAD);

		tmpList = new TreeSet<>();
		ISummit sUAF = new Summit("sUAF", timeMaxIntersection);
		ISummit sUAG = new Summit("sUAG", timeMaxIntersection);
		graph.addSummit(sUAF);
		graph.addSummit(sUAG);
		tmpList.add(sU);
		tmpList.add(sUAF);
		tmpList.add(sUAG);
		IEdge eIUAFAG = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(166), calcY(97)));
		graph.addEdge(eIUAFAG);

		tmpList = new TreeSet<>();
		ISummit sAF = new Summit("sAF", sAD.getLength());
		ISummit sAFAG = new Summit("sAFAG", timeMinIntersection);
		graph.addSummit(sAF);
		graph.addSummit(sAFAG);
		tmpList.add(sUAF);
		tmpList.add(sAF);
		tmpList.add(sAFAG);
		IEdge eIAFUAG = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(162), calcY(103)));
		graph.addEdge(eIAFUAG);

		tmpList = new TreeSet<>();
		ISummit sAG = new Summit("sAG", sAC.getLength());
		graph.addSummit(sAG);
		tmpList.add(sAG);
		tmpList.add(sUAG);
		tmpList.add(sAFAG);
		IEdge eIAGUAF = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(171), calcY(103)));
		graph.addEdge(eIAGUAF);

		tmpList = new TreeSet<>();
		ISummit sAF1AH = new Summit("sAF1AH", timeMaxCross);
		ISummit sAF2AH = new Summit("sAF2AH", timeMinCross);
		ISummit sAF3AH = new Summit("sAF3AH", timeMaxCross);
		graph.addSummit(sAF1AH);
		graph.addSummit(sAF2AH);
		graph.addSummit(sAF3AH);
		tmpList.add(sAF);
		tmpList.add(sAF1AH);
		tmpList.add(sAF2AH);
		tmpList.add(sAF3AH);
		IEdge eCAFAH = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(157), calcY(103)));
		graph.addEdge(eCAFAH);

		tmpList = new TreeSet<>();
		ISummit sAH = new Summit("sAH", sAE.getLength());
		graph.addSummit(sAH);
		tmpList.add(sAH);
		tmpList.add(sAF1AH);
		tmpList.add(sAF2AH);
		tmpList.add(sAF3AH);
		IEdge eCAHAF = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(142), calcY(103)));
		graph.addEdge(eCAHAF);

		tmpList = new TreeSet<>();
		ISummit sAG1AI = new Summit("sAG1AI", timeMaxCross);
		ISummit sAG2AI = new Summit("sAG2AI", timeMinCross);
		ISummit sAG3AI = new Summit("sAG3AI", timeMaxCross);
		graph.addSummit(sAG1AI);
		graph.addSummit(sAG2AI);
		graph.addSummit(sAG3AI);
		tmpList.add(sAG);
		tmpList.add(sAG1AI);
		tmpList.add(sAG2AI);
		tmpList.add(sAG3AI);
		IEdge eCAGAI = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(177), calcY(103)));
		graph.addEdge(eCAGAI);

		tmpList = new TreeSet<>();
		ISummit sAI = new Summit("sAI", sAA.getLength());
		graph.addSummit(sAI);
		tmpList.add(sAI);
		tmpList.add(sAG1AI);
		tmpList.add(sAG2AI);
		tmpList.add(sAG3AI);
		IEdge eCAIAG = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(191), calcY(103)));
		graph.addEdge(eCAIAG);

		tmpList = new TreeSet<>();
		ISummit sZAI = new Summit("sZAI", timeMaxIntersection);
		ISummit sZAJ = new Summit("sZAJ", timeMinIntersection);
		graph.addSummit(sZAI);
		graph.addSummit(sZAJ);
		tmpList.add(sZAI);
		tmpList.add(sZAJ);
		tmpList.add(sZ);
		IEdge eIZAIAJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(202), calcY(97)));
		graph.addEdge(eIZAIAJ);

		tmpList = new TreeSet<>();
		ISummit sAIAJ = new Summit("sAIAJ", timeMaxIntersection);
		graph.addSummit(sAIAJ);
		tmpList.add(sAI);
		tmpList.add(sZAI);
		tmpList.add(sAIAJ);
		IEdge eIAIZAJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(197), calcY(103)));
		graph.addEdge(eIAIZAJ);

		tmpList = new TreeSet<>();
		ISummit sAJ = new Summit("sAJ", sAB.getLength());
		graph.addSummit(sAJ);
		tmpList.add(sAJ);
		tmpList.add(sAIAJ);
		tmpList.add(sZAJ);
		IEdge eIAJZAI = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(calcX(202), calcY(108)));
		graph.addEdge(eIAJZAI);

		tmpList = new TreeSet<>();
		ISummit sAB1AK = new Summit("sAB1AK", timeMaxCross);
		ISummit sAB2AK = new Summit("sAB2AK", timeMinCross);
		ISummit sAB3AK = new Summit("sAB3AK", timeMaxCross);
		graph.addSummit(sAB1AK);
		graph.addSummit(sAB2AK);
		graph.addSummit(sAB3AK);
		tmpList.add(sAB1AK);
		tmpList.add(sAB2AK);
		tmpList.add(sAB3AK);
		tmpList.add(sAB);
		IEdge eCABAK = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(5), calcY(133)));
		graph.addEdge(eCABAK);

		tmpList = new TreeSet<>();
		ISummit sAK = new Summit("sAK", 6*sD.getLength() + 4*sAA.getLength() + timeMinCross + timeMinIntersection);
		graph.addSummit(sAK);
		tmpList.add(sAK);
		tmpList.add(sAB1AK);
		tmpList.add(sAB2AK);
		tmpList.add(sAB3AK);
		IEdge eCAKAB = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(5), calcY(150)));
		graph.addEdge(eCAKAB);

		tmpList = new TreeSet<>();
		ISummit sAE1AK = new Summit("sAE1AK", timeMaxCross);
		ISummit sAE2AK = new Summit("sAE2AK", timeMinCross);
		ISummit sAE3AK = new Summit("sAE3AK", timeMaxCross);
		graph.addSummit(sAE1AK);
		graph.addSummit(sAE2AK);
		graph.addSummit(sAE3AK);
		tmpList.add(sAE1AK);
		tmpList.add(sAE2AK);
		tmpList.add(sAE3AK);
		tmpList.add(sAK);
		IEdge eCAKAE = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(50), calcY(157)));
		graph.addEdge(eCAKAE);

		tmpList = new TreeSet<>();
		tmpList.add(sAE1AK);
		tmpList.add(sAE2AK);
		tmpList.add(sAE3AK);
		tmpList.add(sAE);
		IEdge eCAEAK = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(66), calcY(157)));
		graph.addEdge(eCAEAK);

		tmpList = new TreeSet<>();
		ISummit sAH1AL = new Summit("sAH1AL", timeMaxCross);
		ISummit sAH2AL = new Summit("sAH2AL", timeMinCross);
		ISummit sAH3AL = new Summit("sAH3AL", timeMaxCross);
		graph.addSummit(sAH1AL);
		graph.addSummit(sAH2AL);
		graph.addSummit(sAH3AL);
		tmpList.add(sAH1AL);
		tmpList.add(sAH2AL);
		tmpList.add(sAH3AL);
		tmpList.add(sAH);
		IEdge eCAHAL = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(141), calcY(157)));
		graph.addEdge(eCAHAL);

		tmpList = new TreeSet<>();
		ISummit sAL = new Summit("sAL", sAK.getLength());
		graph.addSummit(sAL);
		tmpList.add(sAL);
		tmpList.add(sAH1AL);
		tmpList.add(sAH2AL);
		tmpList.add(sAH3AL);
		IEdge eCALAH = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(157), calcY(157)));
		graph.addEdge(eCALAH);

		tmpList = new TreeSet<>();
		ISummit sAJ1AL = new Summit("sAJ1AL", timeMaxCross);
		ISummit sAJ2AL = new Summit("sAJ2AL", timeMinCross);
		ISummit sAJ3AL = new Summit("sAJ3AL", timeMaxCross);
		graph.addSummit(sAJ1AL);
		graph.addSummit(sAJ2AL);
		graph.addSummit(sAJ3AL);
		tmpList.add(sAJ1AL);
		tmpList.add(sAJ2AL);
		tmpList.add(sAJ3AL);
		tmpList.add(sAL);
		IEdge eCALAJ = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(202), calcY(150)));
		graph.addEdge(eCALAJ);

		tmpList = new TreeSet<>();
		tmpList.add(sAJ);
		tmpList.add(sAJ1AL);
		tmpList.add(sAJ2AL);
		tmpList.add(sAJ3AL);
		IEdge eCAJAL = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(calcX(202), calcY(133)));
		graph.addEdge(eCAJAL);
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
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MapOverview.fxml"));
			AnchorPane mapOverview = (AnchorPane) loader.load();

			rootLayout.setClip(mapOverview);

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
		int i;

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Robot Simulator");

		initRootLayout();
		root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);

		for (i = 0; i < NB_ROBOTS; i++) {
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
		for (i = 0; i <= r.nextInt(nbSummits + 1) + 1; i++) {
			ISummit s = summitList.get(r.nextInt(nbSummits));
			s.setObjective(true);
			objectives.add(s);
		}

		printGraph();

		for (i = 0; i < robotList.size(); i++) {
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

	public static boolean asSameEnds(ISummit s1, ISummit s2) {
		IEdge[] e1 = s1.getEnds();
		IEdge[] e2 = s2.getEnds();
		return ((e1[0].equals(e2[0]) && e1[1].equals(e2[1])) || (e1[0].equals(e2[1]) && e1[1].equals(e2[0])));
	}

	public static Double abs(Double x) {
		if (x < 0)
			return -x;
		return x;
	}

	private void printCrosses(ISummit s) {
		IEdge[] e = s.getEnds();
		Arc s12 = new Arc();
		s12.setCenterX((e[0].getCoordinates().getX() + e[1].getCoordinates().getX()) / 2);
		s12.setCenterY((e[0].getCoordinates().getY() + e[1].getCoordinates().getY()) / 2);
		if (e[1].getCoordinates().getX() - e[0].getCoordinates().getX() != 0) {
			s12.setRadiusX(abs(e[1].getCoordinates().getX() - e[0].getCoordinates().getX()) / 2);
			s12.setRadiusY(20);
		} else {
			s12.setRadiusX(20);
			s12.setRadiusY(abs(e[1].getCoordinates().getY() - e[0].getCoordinates().getY()) / 2);
		}
		s12.setStartAngle(0);

		s12.setType(ArcType.OPEN);
		s12.setFill(Color.TRANSPARENT);
		s12.setStroke(Color.WHITE);
		s12.setStrokeWidth(2);

		s12.setLength(360);

		root.getChildren().add(s12);
	}

	private void printCross(IEdge e1, IEdge e2) {
		for (ISummit s0 : e1.getSummits()) {
			for (ISummit s1 : e2.getSummits()) {
				if (!s0.equals(s1) && asSameEnds(s0, s1)) {
					printCrosses(s0);
				}
			}
		}
	}

	private void printSummit(ISummit s) {
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

		// Case of a cross
		if (e[0].getType().equals(e[1].getType()) && e[0].getType().equals(EdgeType.CROSS)) {
			printCross(e[0], e[1]);
		}
		summitRepresentation.put(s, l);
	}

	private void printGraph() {

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

	public Line getLine(ISummit currentSummit) {
		return (summitRepresentation.get(currentSummit));
	}
}
