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

	/*
	 * The name of the variables are the same than in the jointed map 
	 * (Nancy Map.png)
	 * Be careful: in a cross, the 2 summit by alphabetic order has to be the shorter
	 */
	public Main() {
		graph = new Graph();
		NavigableSet<ISummit> tmpList = new TreeSet<>();

		ISummit s1 = new Summit("s1", 360);
		ISummit s112 = new Summit("s111", 180);
		ISummit s122 = new Summit("s122", 120);
		ISummit s132 = new Summit("s132", 180);
		graph.addSummit(s1);
		graph.addSummit(s112);
		graph.addSummit(s122);
		graph.addSummit(s132);
		tmpList.add(s1);
		tmpList.add(s112);
		tmpList.add(s122);
		tmpList.add(s132);
		IEdge eC12 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(10, 10));
		graph.addEdge(eC12);

		tmpList = new TreeSet<>();
		ISummit s2 = new Summit("s2", 40);
		graph.addSummit(s2);
		tmpList.add(s112);
		tmpList.add(s122);
		tmpList.add(s132);
		tmpList.add(s2);
		IEdge eC21 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(130, 10));
		graph.addEdge(eC21);

		tmpList = new TreeSet<>();
		ISummit s213 = new Summit("s213", 180);
		ISummit s223 = new Summit("s223", 120);
		ISummit s233 = new Summit("s233", 180);
		graph.addSummit(s213);
		graph.addSummit(s223);
		graph.addSummit(s233);
		tmpList.add(s2);
		tmpList.add(s213);
		tmpList.add(s223);
		tmpList.add(s233);
		IEdge eC23 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(170, 10));
		graph.addEdge(eC23);

		tmpList = new TreeSet<>();
		ISummit s3 = new Summit("s3", 40);
		graph.addSummit(s3);
		tmpList.add(s3);
		tmpList.add(s233);
		tmpList.add(s223);
		tmpList.add(s213);
		IEdge eC32 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(290, 10));
		graph.addEdge(eC32);

		tmpList = new TreeSet<>();
		ISummit s34 = new Summit("s34", 10);
		ISummit s35 = new Summit("s35", 15);
		graph.addSummit(s34);
		graph.addSummit(s35);
		tmpList.add(s3);
		tmpList.add(s34);
		tmpList.add(s35);
		IEdge eI345 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(330, 10));
		graph.addEdge(eI345);

		tmpList = new TreeSet<>();
		ISummit s4 = new Summit("s4", 105);
		ISummit s45 = new Summit("s45", 15);
		graph.addSummit(s4);
		graph.addSummit(s45);
		tmpList.add(s34);
		tmpList.add(s4);
		tmpList.add(s45);
		IEdge eI435 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(340, 10));
		graph.addEdge(eI435);

		tmpList = new TreeSet<>();
		ISummit s5 = new Summit("s5", 165);
		graph.addSummit(s5);
		tmpList.add(s35);
		tmpList.add(s45);
		tmpList.add(s5);
		IEdge eI534 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(335, 15));
		graph.addEdge(eI534);

		tmpList = new TreeSet<>();
		ISummit s416 = new Summit("s416", 180);
		ISummit s426 = new Summit("s426", 120);
		ISummit s436 = new Summit("s436", 180);
		graph.addSummit(s416);
		graph.addSummit(s426);
		graph.addSummit(s436);
		tmpList.add(s416);
		tmpList.add(s426);
		tmpList.add(s436);
		tmpList.add(s4);
		IEdge eC46 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(445, 10));
		graph.addEdge(eC46);

		tmpList = new TreeSet<>();
		ISummit s6 = new Summit("s6", 210);
		graph.addSummit(s6);
		tmpList.add(s6);
		tmpList.add(s436);
		tmpList.add(s426);
		tmpList.add(s416);
		IEdge eC64 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(565, 10));
		graph.addEdge(eC64);

		tmpList = new TreeSet<>();
		ISummit s617 = new Summit("s617", 180);
		ISummit s627 = new Summit("s627", 120);
		ISummit s637 = new Summit("s637", 180);
		graph.addSummit(s637);
		graph.addSummit(s627);
		graph.addSummit(s617);
		tmpList.add(s617);
		tmpList.add(s627);
		tmpList.add(s637);
		tmpList.add(s6);
		IEdge eC67 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(775, 10));
		graph.addEdge(eC67);

		tmpList = new TreeSet<>();
		ISummit s7 = new Summit("s7", 105);
		graph.addSummit(s7);
		tmpList.add(s617);
		tmpList.add(s627);
		tmpList.add(s637);
		tmpList.add(s7);
		IEdge eC76 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(895, 10));
		graph.addEdge(eC76);

		tmpList = new TreeSet<>();
		ISummit s78 = new Summit("s78", 10);
		ISummit s79 = new Summit("s79", 15);
		graph.addSummit(s78);
		graph.addSummit(s79);
		tmpList.add(s7);
		tmpList.add(s78);
		tmpList.add(s79);
		IEdge eI789 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1000, 10));
		graph.addEdge(eI789);

		tmpList = new TreeSet<>();
		ISummit s8 = new Summit("s8", 40);
		ISummit s89 = new Summit("s89", 15);
		graph.addSummit(s8);
		graph.addSummit(s89);
		tmpList.add(s78);
		tmpList.add(s89);
		tmpList.add(s8);
		IEdge eI879 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1010, 10));
		graph.addEdge(eI879);

		tmpList = new TreeSet<>();
		ISummit s9 = new Summit("s9", 165);
		graph.addSummit(s9);
		tmpList.add(s79);
		tmpList.add(s89);
		tmpList.add(s9);
		IEdge eI978 = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1005, 15));
		graph.addEdge(eI978);

		tmpList = new TreeSet<>();
		ISummit s81A = new Summit("s81A", 180);
		ISummit s82A = new Summit("s82A", 120);
		ISummit s83A = new Summit("s83A", 180);
		graph.addSummit(s81A);
		graph.addSummit(s82A);
		graph.addSummit(s83A);
		tmpList.add(s8);
		tmpList.add(s81A);
		tmpList.add(s82A);
		tmpList.add(s83A);
		IEdge eC8A = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1050, 10));
		graph.addEdge(eC8A);

		tmpList = new TreeSet<>();
		ISummit sA = new Summit("sA", 40);
		graph.addSummit(sA);
		tmpList.add(s81A);
		tmpList.add(s82A);
		tmpList.add(s83A);
		tmpList.add(sA);
		IEdge eCA8 = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1170, 10));
		graph.addEdge(eCA8);

		tmpList = new TreeSet<>();
		ISummit sA1B = new Summit("sA1B", 180);
		ISummit sA2B = new Summit("sA2B", 120);
		ISummit sA3B = new Summit("sA3B", 180);
		graph.addSummit(sA1B);
		graph.addSummit(sA2B);
		graph.addSummit(sA3B);
		tmpList.add(sA);
		tmpList.add(sA1B);
		tmpList.add(sA2B);
		tmpList.add(sA3B);
		IEdge eCAB = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1210, 10));
		graph.addEdge(eCAB);

		tmpList = new TreeSet<>();
		ISummit sB = new Summit("sB", 360);
		graph.addSummit(sB);
		tmpList.add(sA1B);
		tmpList.add(sA2B);
		tmpList.add(sA3B);
		tmpList.add(sB);
		IEdge eCBA = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1330, 10));
		graph.addEdge(eCBA);

		tmpList = new TreeSet<>();
		ISummit s1C = new Summit("s1C", 15);
		ISummit s1D = new Summit("s1D", 10);
		graph.addSummit(s1C);
		graph.addSummit(s1D);
		tmpList.add(s1);
		tmpList.add(s1C);
		tmpList.add(s1D);
		IEdge eI1CD = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(168, 170));
		graph.addEdge(eI1CD);

		tmpList = new TreeSet<>();
		ISummit sC = new Summit("sC", 20);
		ISummit sCD = new Summit("sCD", 15);
		graph.addSummit(sC);
		graph.addSummit(sCD);
		tmpList.add(s1C);
		tmpList.add(sC);
		tmpList.add(sCD);
		IEdge eIC1D = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(173, 175));
		graph.addEdge(eIC1D);

		tmpList = new TreeSet<>();
		ISummit sD = new Summit("sD", 160);
		graph.addSummit(sD);
		tmpList.add(s1D);
		tmpList.add(sCD);
		tmpList.add(sD);
		IEdge eID1C = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(168, 180));
		graph.addEdge(eID1C);

		tmpList = new TreeSet<>();
		ISummit sC1E = new Summit("sC1E", 180);
		ISummit sC2E = new Summit("sC2E", 120);
		ISummit sC3E = new Summit("sC3E", 180);
		graph.addSummit(sC1E);
		graph.addSummit(sC2E);
		graph.addSummit(sC3E);
		tmpList.add(sC);
		tmpList.add(sC1E);
		tmpList.add(sC2E);
		tmpList.add(sC3E);
		IEdge eCCE = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(193, 175));
		graph.addEdge(eCCE);

		tmpList = new TreeSet<>();
		ISummit sE = new Summit("sE", 20);
		graph.addSummit(sE);
		tmpList.add(sC1E);
		tmpList.add(sC2E);
		tmpList.add(sC3E);
		tmpList.add(sE);
		IEdge eCEC = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(313, 175));
		graph.addEdge(eCEC);

		tmpList = new TreeSet<>();
		ISummit s5E = new Summit("s5E", 15);
		ISummit sEF = new Summit("sEF", 10);
		graph.addSummit(s5E);
		graph.addSummit(sEF);
		tmpList.add(sE);
		tmpList.add(s5E);
		tmpList.add(sEF);
		IEdge eIE5F = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(330, 175));
		graph.addEdge(eIE5F);

		tmpList = new TreeSet<>();
		ISummit s5F = new Summit("s5F", 15);
		graph.addSummit(s5F);
		tmpList.add(s5);
		tmpList.add(s5E);
		tmpList.add(s5F);
		IEdge eI5EF = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(335, 170));
		graph.addEdge(eI5EF);

		tmpList = new TreeSet<>();
		ISummit sF = new Summit("sF", 20);
		graph.addSummit(sF);
		tmpList.add(s5F);
		tmpList.add(sEF);
		tmpList.add(sF);
		IEdge eIF5E = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(340, 175));
		graph.addEdge(eIF5E);

		tmpList = new TreeSet<>();
		ISummit sFG = new Summit("sFG", 15);
		ISummit sFH = new Summit("sFH", 15);
		graph.addSummit(sFG);
		graph.addSummit(sFH);
		tmpList.add(sF);
		tmpList.add(sFG);
		tmpList.add(sFH);
		IEdge eIFGH = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(360, 175));
		graph.addEdge(eIFGH);

		tmpList = new TreeSet<>();
		ISummit sG = new Summit("sG", 613);
		ISummit sGH = new Summit("sGH", 10);
		graph.addSummit(sG);
		graph.addSummit(sGH);
		tmpList.add(sG);
		tmpList.add(sFG);
		tmpList.add(sGH);
		IEdge eIGFH = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(365, 170));
		graph.addEdge(eIGFH);

		tmpList = new TreeSet<>();
		ISummit sH = new Summit("sH", 105);
		graph.addSummit(sH);
		tmpList.add(sFH);
		tmpList.add(sGH);
		tmpList.add(sH);
		IEdge eIHFG = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(365, 180));
		graph.addEdge(eIHFG);

		tmpList = new TreeSet<>();
		ISummit sDI = new Summit("sDI", 15);
		ISummit sDJ = new Summit("sDJ", 15);
		graph.addSummit(sDI);
		graph.addSummit(sDJ);
		tmpList.add(sDJ);
		tmpList.add(sD);
		tmpList.add(sDI);
		IEdge eIDIJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(168, 340));
		graph.addEdge(eIDIJ);

		tmpList = new TreeSet<>();
		ISummit sI = new Summit("sI", 148);
		ISummit sIJ = new Summit("sIJ", 10);
		graph.addSummit(sI);
		graph.addSummit(sIJ);
		tmpList.add(sDI);
		tmpList.add(sI);
		tmpList.add(sIJ);
		IEdge eIIDJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(163, 345));
		graph.addEdge(eIIDJ);

		tmpList = new TreeSet<>();
		ISummit sJ = new Summit("sJ", 32);
		graph.addSummit(sJ);
		tmpList.add(sJ);
		tmpList.add(sDJ);
		tmpList.add(sIJ);
		IEdge eIJDI = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(173, 345));
		graph.addEdge(eIJDI);

		tmpList = new TreeSet<>();
		ISummit sIK = new Summit("sIK", 10);
		ISummit sIL = new Summit("sIL", 15);
		tmpList.add(sI);
		tmpList.add(sIK);
		tmpList.add(sIL);
		IEdge eIIKL = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(15, 345));
		graph.addEdge(eIIKL);

		tmpList = new TreeSet<>();
		ISummit sK = new Summit("sK", 5);
		ISummit sKL = new Summit("sKL", 15);
		tmpList.add(sK);
		tmpList.add(sKL);
		tmpList.add(sIK);
		IEdge eIKIL = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(5, 345));
		graph.addEdge(eIKIL);

		tmpList = new TreeSet<>();
		tmpList.add(sK);
		IEdge endK = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(0, 345));
		graph.addEdge(endK);

		tmpList = new TreeSet<>();
		ISummit sL = new Summit("sL", 165);
		graph.addSummit(sL);
		tmpList.add(sKL);
		tmpList.add(sIL);
		tmpList.add(sL);
		IEdge eILIK = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(10, 350));
		graph.addEdge(eILIK);

		tmpList = new TreeSet<>();
		ISummit sJ1M = new Summit("sJ1M", 180);
		ISummit sJ2M = new Summit("sJ2M", 120);
		ISummit sJ3M = new Summit("sJ3M", 180);
		graph.addSummit(sJ3M);
		graph.addSummit(sJ2M);
		graph.addSummit(sJ1M);
		tmpList.add(sJ);
		tmpList.add(sJ1M);
		tmpList.add(sJ2M);
		tmpList.add(sJ3M);
		IEdge eCJM = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(205, 345));
		graph.addEdge(eCJM);

		tmpList = new TreeSet<>();
		ISummit sM = new Summit("sM", 10);
		graph.addSummit(sM);
		tmpList.add(sM);
		tmpList.add(sJ1M);
		tmpList.add(sJ2M);
		tmpList.add(sJ3M);
		IEdge eCMJ = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(325, 345));
		graph.addEdge(eCMJ);

		tmpList = new TreeSet<>();
		tmpList.add(sM);
		IEdge endM = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(335, 345));
		graph.addEdge(endM);

		tmpList = new TreeSet<>();
		ISummit sH1N = new Summit("sH1N", 180);
		ISummit sH2N = new Summit("sH2N", 120);
		ISummit sH3N = new Summit("sH3N", 180);
		graph.addSummit(sH1N);
		graph.addSummit(sH2N);
		graph.addSummit(sH3N);
		tmpList.add(sH);
		tmpList.add(sH1N);
		tmpList.add(sH2N);
		tmpList.add(sH3N);
		IEdge eCHN = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(365, 285));
		graph.addEdge(eCHN);

		tmpList = new TreeSet<>();
		ISummit sN = new Summit("sN", 330);
		graph.addSummit(sN);
		tmpList.add(sH1N);
		tmpList.add(sH2N);
		tmpList.add(sH3N);
		tmpList.add(sN);
		IEdge eCNH = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(365, 405));
		graph.addEdge(eCNH);

		tmpList = new TreeSet<>();
		ISummit sG1O = new Summit("sG1O", 180);
		ISummit sG2O = new Summit("sG2O", 120);
		ISummit sG3O = new Summit("sG3O", 180);
		graph.addSummit(sG1O);
		graph.addSummit(sG2O);
		graph.addSummit(sG3O);
		tmpList.add(sG);
		tmpList.add(sG1O);
		tmpList.add(sG2O);
		tmpList.add(sG3O);
		IEdge eCGO = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(610, 500));
		graph.addEdge(eCGO);

		tmpList = new TreeSet<>();
		ISummit sO = new Summit("sO", 613);
		graph.addSummit(sO);
		tmpList.add(sG1O);
		tmpList.add(sG2O);
		tmpList.add(sG3O);
		tmpList.add(sO);
		IEdge eCOG = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(730, 500));
		graph.addEdge(eCOG);

		tmpList = new TreeSet<>();
		ISummit sOQ = new Summit("sOQ", 10);
		ISummit sOP = new Summit("sOP", 15);
		graph.addSummit(sOP);
		graph.addSummit(sOQ);
		tmpList.add(sO);
		tmpList.add(sOQ);
		tmpList.add(sOP);
		IEdge eIOPQ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1005, 170));
		graph.addEdge(eIOPQ);

		tmpList = new TreeSet<>();
		ISummit sP = new Summit("sP", 20);
		ISummit sPQ = new Summit("sPQ", 15);
		graph.addSummit(sP);
		graph.addSummit(sPQ);
		tmpList.add(sP);
		tmpList.add(sOP);
		tmpList.add(sPQ);
		IEdge eIPOQ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1010, 175));
		graph.addEdge(eIPOQ);

		tmpList = new TreeSet<>();
		ISummit sQ = new Summit("sQ", 105);
		graph.addSummit(sQ);
		tmpList.add(sQ);
		tmpList.add(sOQ);
		tmpList.add(sPQ);
		IEdge eIQOP = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1005, 180));
		graph.addEdge(eIQOP);

		tmpList = new TreeSet<>();
		ISummit s9P = new Summit("s9P", 15);
		ISummit s9R = new Summit("s9R", 15);
		graph.addSummit(s9R);
		graph.addSummit(s9P);
		tmpList.add(s9);
		tmpList.add(s9P);
		tmpList.add(s9R);
		IEdge eI9PR = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1035, 170));
		graph.addEdge(eI9PR);

		tmpList = new TreeSet<>();
		ISummit sPR = new Summit("sPR", 10);
		graph.addSummit(sPR);
		tmpList.add(s9P);
		tmpList.add(sPR);
		tmpList.add(sP);
		IEdge eIP9R = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1030, 175));
		graph.addEdge(eIP9R);

		tmpList = new TreeSet<>();
		ISummit sR = new Summit("sR", 20);
		graph.addSummit(sR);
		tmpList.add(s9R);
		tmpList.add(sPR);
		tmpList.add(sR);
		IEdge eIR9P = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1040, 175));
		graph.addEdge(eIR9P);

		tmpList = new TreeSet<>();
		ISummit sR1S = new Summit("sR1S", 180);
		ISummit sR2S = new Summit("sR2S", 120);
		ISummit sR3S = new Summit("sR3S", 180);
		graph.addSummit(sR1S);
		graph.addSummit(sR2S);
		graph.addSummit(sR3S);
		tmpList.add(sR);
		tmpList.add(sR1S);
		tmpList.add(sR2S);
		tmpList.add(sR3S);
		IEdge eCRS = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1060, 175));
		graph.addEdge(eCRS);

		tmpList = new TreeSet<>();
		ISummit sS = new Summit("sS", 20);
		graph.addSummit(sS);
		tmpList.add(sR1S);
		tmpList.add(sR2S);
		tmpList.add(sR3S);
		tmpList.add(sS);
		IEdge eCSR = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1180, 175));
		graph.addEdge(eCSR);

		tmpList = new TreeSet<>();
		ISummit sBS = new Summit("sBS", 15);
		ISummit sST = new Summit("sST", 15);
		graph.addSummit(sBS);
		graph.addSummit(sST);
		tmpList.add(sS);
		tmpList.add(sBS);
		tmpList.add(sST);
		IEdge eISBT = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1200, 175));
		graph.addEdge(eISBT);

		tmpList = new TreeSet<>();
		ISummit sBT = new Summit("sBT", 10);
		graph.addSummit(sBT);
		tmpList.add(sB);
		tmpList.add(sBS);
		tmpList.add(sBT);
		IEdge eIBST = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1205, 170));
		graph.addEdge(eIBST);

		tmpList = new TreeSet<>();
		ISummit sT = new Summit("sT", 160);
		graph.addSummit(sT);
		tmpList.add(sT);
		tmpList.add(sST);
		tmpList.add(sBT);
		IEdge eITBS = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1205, 180));
		graph.addEdge(eITBS);

		tmpList = new TreeSet<>();
		ISummit sQ1U = new Summit("sQ1U", 180);
		ISummit sQ2U = new Summit("sQ2U", 120);
		ISummit sQ3U = new Summit("sQ3U", 180);
		graph.addSummit(sQ1U);
		graph.addSummit(sQ2U);
		graph.addSummit(sQ3U);
		tmpList.add(sQ);
		tmpList.add(sQ1U);
		tmpList.add(sQ2U);
		tmpList.add(sQ3U);
		IEdge eCQU = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1005, 295));
		graph.addEdge(eCQU);

		tmpList = new TreeSet<>();
		ISummit sU = new Summit("sU", 330);
		graph.addSummit(sQ1U);
		graph.addSummit(sQ2U);
		graph.addSummit(sQ3U);
		graph.addSummit(sU);
		tmpList.add(sQ1U);
		tmpList.add(sQ2U);
		tmpList.add(sQ3U);
		tmpList.add(sU);
		IEdge eCUQ = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1005, 415));
		graph.addEdge(eCUQ);

		tmpList = new TreeSet<>();
		ISummit sTV = new Summit("sTV", 15);
		ISummit sTW = new Summit("sTW", 15);
		graph.addSummit(sTV);
		graph.addSummit(sTW);
		tmpList.add(sT);
		tmpList.add(sTV);
		tmpList.add(sTW);
		IEdge eITVW = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1205, 240));
		graph.addEdge(eITVW);

		tmpList = new TreeSet<>();
		ISummit sV = new Summit("sV", 32);
		ISummit sVW = new Summit("sVW", 10);
		graph.addSummit(sV);
		graph.addSummit(sVW);
		tmpList.add(sTV);
		tmpList.add(sV);
		tmpList.add(sVW);
		IEdge eIVTW = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1200, 245));
		graph.addEdge(eIVTW);

		tmpList = new TreeSet<>();
		ISummit sW = new Summit("sW", 148);
		graph.addSummit(sW);
		tmpList.add(sW);
		tmpList.add(sTW);
		tmpList.add(sVW);
		IEdge eIWTV = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1210, 245));
		graph.addEdge(eIWTV);

		tmpList = new TreeSet<>();
		ISummit sV1X = new Summit("sV1X", 180);
		ISummit sV2X = new Summit("sV2X", 120);
		ISummit sV3X = new Summit("sV3X", 180);
		graph.addSummit(sV1X);
		graph.addSummit(sV2X);
		graph.addSummit(sV3X);
		tmpList.add(sV);
		tmpList.add(sV1X);
		tmpList.add(sV2X);
		tmpList.add(sV3X);
		IEdge eCVX = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1168, 245));
		graph.addEdge(eCVX);

		tmpList = new TreeSet<>();
		ISummit sX = new Summit("sX", 5);
		graph.addSummit(sX);
		tmpList.add(sX);
		tmpList.add(sV1X);
		tmpList.add(sV2X);
		tmpList.add(sV3X);
		IEdge eCXV = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1048, 245));
		graph.addEdge(eCXV);

		tmpList = new TreeSet<>();
		tmpList.add(sX);
		IEdge endX = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(1043, 245));
		graph.addEdge(endX);

		tmpList = new TreeSet<>();
		ISummit sWY = new Summit("sWY", 10);
		ISummit sWZ = new Summit("sWZ", 15);
		graph.addSummit(sWY);
		graph.addSummit(sWZ);
		tmpList.add(sW);
		tmpList.add(sWY);
		tmpList.add(sWZ);
		IEdge eIWYZ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1358, 245));
		graph.addEdge(eIWYZ);

		tmpList = new TreeSet<>();
		ISummit sY = new Summit("sY", 5);
		ISummit sYZ = new Summit("sYZ", 15);
		graph.addSummit(sY);
		graph.addSummit(sYZ);
		tmpList.add(sY);
		tmpList.add(sYZ);
		tmpList.add(sWY);
		IEdge eIYWZ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1368, 245));
		graph.addEdge(eIYWZ);

		tmpList = new TreeSet<>();
		ISummit sZ = new Summit("sZ", 165);
		graph.addSummit(sZ);
		tmpList.add(sZ);
		tmpList.add(sWZ);
		tmpList.add(sYZ);
		IEdge eIZWY = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1363, 250));
		graph.addEdge(eIZWY);

		tmpList = new TreeSet<>();
		tmpList.add(sY);
		IEdge endY = new Edge(1, tmpList, EdgeType.INTERSECTION, new Coordinates(1373, 245));
		graph.addEdge(endY);

		tmpList = new TreeSet<>();
		ISummit sLAB = new Summit("sLAB", 10);
		ISummit sLAA = new Summit("sLAA", 15);
		graph.addSummit(sLAA);
		graph.addSummit(sLAB);
		tmpList.add(sL);
		tmpList.add(sLAA);
		tmpList.add(sLAB);
		IEdge eILAAAB = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(10, 515));
		graph.addEdge(eILAAAB);

		tmpList = new TreeSet<>();
		ISummit sAA = new Summit("sAA", 78);
		ISummit sAAAB = new Summit("sAAAB", 15);
		graph.addSummit(sAA);
		graph.addSummit(sAAAB);
		;
		tmpList.add(sLAA);
		tmpList.add(sAA);
		tmpList.add(sAAAB);
		IEdge eIAALAB = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(15, 520));
		graph.addEdge(eIAALAB);

		tmpList = new TreeSet<>();
		ISummit sAB = new Summit("sAB", 45);
		graph.addSummit(sAB);
		tmpList.add(sAB);
		tmpList.add(sLAB);
		tmpList.add(sAAAB);
		IEdge eIABLAA = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(10, 525));
		graph.addEdge(eIABLAA);

		tmpList = new TreeSet<>();
		ISummit sAA1AC = new Summit("sAA1AC", 180);
		ISummit sAA2AC = new Summit("sAA2AC", 120);
		ISummit sAA3AC = new Summit("sAA3AC", 180);
		graph.addSummit(sAA1AC);
		graph.addSummit(sAA2AC);
		graph.addSummit(sAA3AC);
		tmpList.add(sAA);
		tmpList.add(sAA1AC);
		tmpList.add(sAA2AC);
		tmpList.add(sAA3AC);
		IEdge eCAAAC = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(88, 520));
		graph.addEdge(eCAAAC);

		tmpList = new TreeSet<>();
		ISummit sAC = new Summit("sAC", 78);
		graph.addSummit(sAC);
		tmpList.add(sAC);
		tmpList.add(sAA1AC);
		tmpList.add(sAA2AC);
		tmpList.add(sAA3AC);
		IEdge eCACAA = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(208, 520));
		graph.addEdge(eCACAA);

		tmpList = new TreeSet<>();
		ISummit sNAC = new Summit("sNAC", 15);
		ISummit sACAD = new Summit("sACAD", 10);
		graph.addSummit(sNAC);
		graph.addSummit(sACAD);
		tmpList.add(sNAC);
		tmpList.add(sACAD);
		tmpList.add(sAC);
		IEdge eIACNAD = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(286, 520));
		graph.addEdge(eIACNAD);

		tmpList = new TreeSet<>();
		ISummit sNAD = new Summit("sNAD", 15);
		graph.addSummit(sNAD);
		tmpList.add(sN);
		tmpList.add(sNAD);
		tmpList.add(sNAC);
		IEdge eINACAD = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(291, 515));
		graph.addEdge(eINACAD);

		tmpList = new TreeSet<>();
		ISummit sAD = new Summit("sAD", 91);
		graph.addSummit(sAD);
		tmpList.add(sAD);
		tmpList.add(sNAD);
		tmpList.add(sACAD);
		IEdge eIADNAC = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(296, 520));
		graph.addEdge(eIADNAC);

		tmpList = new TreeSet<>();
		ISummit sAD1AE = new Summit("sAD1AE", 180);
		ISummit sAD2AE = new Summit("sAD2AE", 120);
		ISummit sAD3AE = new Summit("sAD3AE", 180);
		graph.addSummit(sAD1AE);
		graph.addSummit(sAD2AE);
		graph.addSummit(sAD3AE);
		tmpList.add(sAD1AE);
		tmpList.add(sAD2AE);
		tmpList.add(sAD3AE);
		tmpList.add(sAD);
		IEdge eCADAE = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(335, 520));
		graph.addEdge(eCADAE);

		tmpList = new TreeSet<>();
		ISummit sAE = new Summit("sAE", 200);
		graph.addSummit(sAE);
		tmpList.add(sAE);
		tmpList.add(sAD1AE);
		tmpList.add(sAD2AE);
		tmpList.add(sAD3AE);
		IEdge eCAEAD = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(455, 520));
		graph.addEdge(eCAEAD);

		tmpList = new TreeSet<>();
		ISummit sUAF = new Summit("sUAF", 15);
		ISummit sUAG = new Summit("sUAG", 15);
		graph.addSummit(sUAF);
		graph.addSummit(sUAG);
		tmpList.add(sU);
		tmpList.add(sUAF);
		tmpList.add(sUAG);
		IEdge eIUAFAG = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1085, 515));
		graph.addEdge(eIUAFAG);

		tmpList = new TreeSet<>();
		ISummit sAF = new Summit("sAF", 91);
		ISummit sAFAG = new Summit("sAFAG", 10);
		graph.addSummit(sAF);
		graph.addSummit(sAFAG);
		tmpList.add(sUAF);
		tmpList.add(sAF);
		tmpList.add(sAFAG);
		IEdge eIAFUAG = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1080, 520));
		graph.addEdge(eIAFUAG);

		tmpList = new TreeSet<>();
		ISummit sAG = new Summit("sAG", 78);
		graph.addSummit(sAG);
		tmpList.add(sAG);
		tmpList.add(sUAG);
		tmpList.add(sAFAG);
		IEdge eIAGFAF = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1090, 520));
		graph.addEdge(eIAGFAF);

		tmpList = new TreeSet<>();
		ISummit sAF1AH = new Summit("sAF1AH", 180);
		ISummit sAF2AH = new Summit("sAF2AH", 120);
		ISummit sAF3AH = new Summit("sAF3AH", 180);
		graph.addSummit(sAF1AH);
		graph.addSummit(sAF2AH);
		graph.addSummit(sAF3AH);
		tmpList.add(sAF);
		tmpList.add(sAF1AH);
		tmpList.add(sAF2AH);
		tmpList.add(sAF3AH);
		IEdge eCAFAH = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(994, 520));
		graph.addEdge(eCAFAH);

		tmpList = new TreeSet<>();
		ISummit sAH = new Summit("sAH", 200);
		graph.addSummit(sAH);
		tmpList.add(sAH);
		tmpList.add(sAF1AH);
		tmpList.add(sAF2AH);
		tmpList.add(sAF3AH);
		IEdge eCAHAF = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(874, 520));
		graph.addEdge(eCAHAF);

		tmpList = new TreeSet<>();
		ISummit sAG1AI = new Summit("sAG1AI", 180);
		ISummit sAG2AI = new Summit("sAG2AI", 120);
		ISummit sAG3AI = new Summit("sAG3AI", 180);
		graph.addSummit(sAG1AI);
		graph.addSummit(sAG2AI);
		graph.addSummit(sAG3AI);
		tmpList.add(sAG);
		tmpList.add(sAG1AI);
		tmpList.add(sAG2AI);
		tmpList.add(sAG3AI);
		IEdge eCAGAI = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1168, 520));
		graph.addEdge(eCAGAI);

		tmpList = new TreeSet<>();
		ISummit sAI = new Summit("sAI", 78);
		graph.addSummit(sAI);
		tmpList.add(sAI);
		tmpList.add(sAG1AI);
		tmpList.add(sAG2AI);
		tmpList.add(sAG3AI);
		IEdge eCAIAG = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1288, 520));
		graph.addEdge(eCAIAG);

		tmpList = new TreeSet<>();
		ISummit sZAI = new Summit("sZAI", 15);
		ISummit sZAJ = new Summit("sZAJ", 10);
		graph.addSummit(sZAI);
		graph.addSummit(sZAJ);
		tmpList.add(sZAI);
		tmpList.add(sZAJ);
		tmpList.add(sZ);
		IEdge eIZAIAJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1363, 515));
		graph.addEdge(eIZAIAJ);

		tmpList = new TreeSet<>();
		ISummit sAIAJ = new Summit("sAIAJ", 15);
		graph.addSummit(sAIAJ);
		tmpList.add(sAI);
		tmpList.add(sZAI);
		tmpList.add(sAIAJ);
		IEdge eIAIZAJ = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1358, 520));
		graph.addEdge(eIAIZAJ);

		tmpList = new TreeSet<>();
		ISummit sAJ = new Summit("sAJ", 45);
		graph.addSummit(sAJ);
		tmpList.add(sAJ);
		tmpList.add(sAIAJ);
		tmpList.add(sZAJ);
		IEdge eIAJZAI = new Edge(3, tmpList, EdgeType.INTERSECTION, new Coordinates(1363, 525));
		graph.addEdge(eIAJZAI);

		tmpList = new TreeSet<>();
		ISummit sAB1AK = new Summit("sAB1AK", 180);
		ISummit sAB2AK = new Summit("sAB2AK", 120);
		ISummit sAB3AK = new Summit("sAB3AK", 180);
		graph.addSummit(sAB1AK);
		graph.addSummit(sAB2AK);
		graph.addSummit(sAB3AK);
		tmpList.add(sAB1AK);
		tmpList.add(sAB2AK);
		tmpList.add(sAB3AK);
		tmpList.add(sAB);
		IEdge eCABAK = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(10, 570));
		graph.addEdge(eCABAK);

		tmpList = new TreeSet<>();
		ISummit sAK = new Summit("sAK", 360);
		graph.addSummit(sAK);
		tmpList.add(sAK);
		tmpList.add(sAB1AK);
		tmpList.add(sAB2AK);
		tmpList.add(sAB3AK);
		IEdge eCAKAB = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(10, 690));
		graph.addEdge(eCAKAB);

		tmpList = new TreeSet<>();
		ISummit sAE1AK = new Summit("sAE1AK", 180);
		ISummit sAE2AK = new Summit("sAE2AK", 120);
		ISummit sAE3AK = new Summit("sAE3AK", 180);
		graph.addSummit(sAE1AK);
		graph.addSummit(sAE2AK);
		graph.addSummit(sAE3AK);
		tmpList.add(sAE1AK);
		tmpList.add(sAE2AK);
		tmpList.add(sAE3AK);
		tmpList.add(sAK);
		IEdge eCAKAE = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(325, 690));
		graph.addEdge(eCAKAE);

		tmpList = new TreeSet<>();
		tmpList.add(sAE1AK);
		tmpList.add(sAE2AK);
		tmpList.add(sAE3AK);
		tmpList.add(sAE);
		IEdge eCAEAK = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(445, 690));
		graph.addEdge(eCAEAK);

		tmpList = new TreeSet<>();
		ISummit sAH1AL = new Summit("sAH1AL", 180);
		ISummit sAH2AL = new Summit("sAH2AL", 120);
		ISummit sAH3AL = new Summit("sAH3AL", 180);
		graph.addSummit(sAH1AL);
		graph.addSummit(sAH2AL);
		graph.addSummit(sAH3AL);
		tmpList.add(sAH1AL);
		tmpList.add(sAH2AL);
		tmpList.add(sAH3AL);
		tmpList.add(sAH);
		IEdge eCAHAL = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(874, 690));
		graph.addEdge(eCAHAL);

		tmpList = new TreeSet<>();
		ISummit sAL = new Summit("sAL", 360);
		graph.addSummit(sAL);
		tmpList.add(sAL);
		tmpList.add(sAH1AL);
		tmpList.add(sAH2AL);
		tmpList.add(sAH3AL);
		IEdge eCALAH = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(994, 690));
		graph.addEdge(eCALAH);

		tmpList = new TreeSet<>();
		ISummit sAJ1AL = new Summit("sAJ1AL", 180);
		ISummit sAJ2AL = new Summit("sAJ2AL", 120);
		ISummit sAJ3AL = new Summit("sAJ3AL", 180);
		graph.addSummit(sAJ1AL);
		graph.addSummit(sAJ2AL);
		graph.addSummit(sAJ3AL);
		tmpList.add(sAJ1AL);
		tmpList.add(sAJ2AL);
		tmpList.add(sAJ3AL);
		tmpList.add(sAL);
		IEdge eCALAJ = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1363, 690));
		graph.addEdge(eCALAJ);

		tmpList = new TreeSet<>();
		tmpList.add(sAJ);
		tmpList.add(sAJ1AL);
		tmpList.add(sAJ2AL);
		tmpList.add(sAJ3AL);
		IEdge eCAJAL = new Edge(4, tmpList, EdgeType.CROSS, new Coordinates(1363, 570));
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
