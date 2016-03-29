/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

/**
 *
 * @author Mohammad Ali
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class RoadEnvironment implements ActionListener {

    public static RoadEnvironment re;
    public JFrame mainFrame;
    public DisplayWindow dw;
//    private Timer timer = new Timer(75, this);//50
    private Timer timer = new Timer(50, this);//

    public boolean paused = false;
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;// do not alter
    public static final int SCALE = 5;

    private RoadNetworkInt roadNet;
    private ArrayList<OneWayRoad> roadArray;//list of all the roads in the network
    private ArrayList<Junction> junctArray;//list of all the junctions in the network
    private ArrayList<OneWayRoad> networkEntr;//list of roads where cars spawn into the network
    private ArrayList<OneWayRoad> networkExit;//list of roads where cars exit from the network

    AutomatonModel model;
    private int stopCounter;
    private int inputCounter;

    public RoadEnvironment(RoadNetworkInt roadNet) {
        this.roadNet = roadNet;
        roadArray = roadNet.getRoadArray();//list of all the roads in the network
        junctArray = roadNet.getJunctArray();//list of all the junctions in the network
        networkEntr = roadNet.getEntrLst();//list of roads where cars spawn into the network
        networkExit = roadNet.getExitLst();//list of roads where cars exit from the network

        model = new AutomatonModel(networkExit);
//        
        stopCounter = 0;
        inputCounter = 0;

        mainFrame = new JFrame("Traffic Simulation App.");
        mainFrame.setVisible(true);
        mainFrame.setSize(800, 800);
        mainFrame.setResizable(false);
        mainFrame.add(dw = new DisplayWindow());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start();
    }
    
    public RoadNetworkInt getRoadNetwork(){
        return roadNet;
    } 

    public void updateRoads() {
        // To demonstrate stoplights, will be removed later

        boolean stopSwitch = false;
        if (stopCounter > 100) {
            stopSwitch = true;
            stopCounter = 0;
        }

//        for (OneWayRoad road : roadArray) {

            if (stopSwitch) {
//                model.switchLightRoad(road);
                for (Junction junct: junctArray){
                    junct.switchStopLights();
                }
//            }
        }

        for (OneWayRoad road : roadArray) {
            int direction = road.getDirection();
            model.resetCarsChk(road);

            switch (direction) {
                case RIGHT:
                    model.updateRightRoad(road);
                    break;

                case LEFT:
                    model.updateCarsLeft(road);
                    break;

                case UP:
                    model.updateCarsUp(road);
                    break;

                case DOWN:
                    model.updateCarsDown(road);
                    break;
            }
            model.resetCarsChk(road);
        }

        for (OneWayRoad road : networkEntr) {
            if (inputCounter > 1){
//                if (road.getDirection()!= UP){
                model.addCarToRoad(road);
//                }
            }
//            model.addCarToRoad(road);

//            model.addCarJ(crossroad, new Point(1,1), UP);
//            model.addCarJ(crossroad, new Point(1,0), UP);
//            model.addCarJ(crossroad, new Point(4, 3), DOWN);
//            model.addCarJ(crossroad, new Point(4, 4), DOWN);
//            model.addCarJ(crossroad, new Point(4, 2), RIGHT);
//            model.addCarJ(crossroad, new Point(5, 2), RIGHT);
//            model.addCarJ(crossroad, new Point(2, 4), LEFT);
//            model.addCarJ(crossroad, new Point(1, 4), LEFT);
        }
        if (inputCounter > 1){
            inputCounter = 0;
        }

        for (Junction junct : junctArray) {
            model.updateJunct(junct);
        }
    }

    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        updateRoads();
        dw.repaint();
        stopCounter++;
        inputCounter++;

    }

    public static void main(String[] args) {
        CrossroadNetwork demo = new CrossroadNetwork();
        re = new RoadEnvironment(demo);
    }
}
