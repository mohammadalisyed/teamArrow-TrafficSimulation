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

//    public static RoadEnvironment re;
//    public JFrame mainFrame;
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
//    private int inputCounter;
//    private int inputRefresh;

    public RoadEnvironment(RoadNetworkInt roadNet, DisplayWindow dw) {
        this.roadNet = roadNet;
        roadArray = roadNet.getRoadArray();//list of all the roads in the network
        junctArray = roadNet.getJunctArray();//list of all the junctions in the network
        networkEntr = roadNet.getEntrLst();//list of roads where cars spawn into the network
        networkExit = roadNet.getExitLst();//list of roads where cars exit from the network

        model = new AutomatonModel(networkExit);
        this.dw = dw;

//        mainFrame = new JFrame("Traffic Simulation App.");
//        mainFrame.setVisible(true);
//        mainFrame.setSize(800, 800);
//        mainFrame.setResizable(false);
//        mainFrame.add(dw = new DisplayWindow(this));
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start();
    }

    public int getEntrInputRefresh(int i) {
        return networkEntr.get(i).getInputRefresh();
    }

    public void setEntrInputRefresh(int i, int newRefresh) {
        networkEntr.get(i).setInputRefresh(newRefresh);
    }

    public ArrayList<OneWayRoad> getNetworkEntArray() {
        return networkEntr;
    }

    public ArrayList<Junction> getJunctArray() {
        return junctArray;
    }

    public void setTimerDelay(int timerSec) {
        //timer.setDelay(timerSec);
        timer.setDelay(timerSec);
    }

    public RoadNetworkInt getRoadNetwork() {
        return roadNet;
    }

    public void updateRoads() {
        // To demonstrate stoplights, will be removed later

        for (Junction junct : junctArray) {
            junct.addToLightTimer();
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
            boolean inputBool = road.getInputBool();
            if (inputBool) {

                road.inputTimerIncrement();
//            System.out.println(road.getInputTimer());
                if (road.getInputTimer() >= road.getInputRefresh()) {
                    model.addCarToRoad(road);
                    road.resetInputTimer();
                }
            }
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
//        inputCounter++;

    }

//    public static void main(String[] args) {
//        CrossroadNetwork demo = new CrossroadNetwork();
//        Roundabout demo2 = new Roundabout();
//        re = new RoadEnvironment(demo);
//    }
}
