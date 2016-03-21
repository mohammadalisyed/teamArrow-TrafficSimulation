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

public class RoadEnvironment implements ActionListener, KeyListener {

    public static RoadEnvironment re;
    public JFrame mainFrame;
    public DisplayWindow dw;
    public Timer timer = new Timer(50, this);

    public ArrayList<Point> carSet = new ArrayList<Point>();
    public ArrayList<Point> carSet2 = new ArrayList<Point>();

    public Point skyline, veyron, ferrari;

    public boolean paused = false;
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;// do not alter
    public static final int SCALE = 5; 
    public int direction;
    public int carLength = 2;

    OneWayRoad northRoadL = new OneWayRoad(50, 3, 50, 0, UP);
    OneWayRoad northRoadR = new OneWayRoad(50, 3, 53, 0, DOWN);

    OneWayRoad eastRoadL = new OneWayRoad(50, 3, 56, 50, RIGHT);
    OneWayRoad eastRoadR = new OneWayRoad(50, 3, 56, 53, LEFT);

    OneWayRoad southRoadL = new OneWayRoad(50, 3, 50, 56, UP);
    OneWayRoad southRoadR = new OneWayRoad(50, 3, 53, 56, DOWN);

    OneWayRoad westRoadL = new OneWayRoad(50, 3, 0, 50, RIGHT);
    OneWayRoad westRoadR = new OneWayRoad(50, 3, 0, 53, LEFT);

//    OneWayRoad[] roadArray = new OneWayRoad[8];
    ArrayList<OneWayRoad> roadArray = new ArrayList<>();
    ArrayList<OneWayRoad> entrRoads = new ArrayList<>();

    OneWayRoad[] crossroadEntr = {northRoadL, southRoadL, westRoadL, eastRoadL};
    OneWayRoad[] crossroadExit = {northRoadR, eastRoadR, southRoadL, westRoadR};

    Crossroad crossroad = new Crossroad(50, 50, crossroadEntr, crossroadExit, 6, 6);

    AutomatonModel model = new AutomatonModel();
    private int stopCounter;

    public RoadEnvironment() {
        northRoadL.setStopLight(true);
        northRoadR.setStopLight(true);
        southRoadL.setStopLight(true);
        southRoadR.setStopLight(true);
        crossroad.setXTravel(true);

        roadArray.add(northRoadL);
        roadArray.add(northRoadR);
        roadArray.add(eastRoadL);
        roadArray.add(eastRoadR);
        roadArray.add(southRoadL);
        roadArray.add(southRoadR);
        roadArray.add(westRoadL);
        roadArray.add(westRoadR);

        entrRoads.add(northRoadR);
        entrRoads.add(eastRoadR);
        entrRoads.add(southRoadL);
        entrRoads.add(westRoadL);
//        
        stopCounter = 0;

        mainFrame = new JFrame("Traffic Simulation App.");
        mainFrame.setVisible(true);
        mainFrame.setSize(800, 800);
        mainFrame.setResizable(false);
        mainFrame.add(dw = new DisplayWindow());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(this);
        start();
    }

    public void updateRoads() {
        // To demonstrate stoplights, will be removed later

        boolean stopSwitch = false;
        if (stopCounter > 50) {
            stopSwitch = true;
            stopCounter = 0;
        }

        for (OneWayRoad road : roadArray) {

            if (stopSwitch) {
                model.switchLightRoad(road);
                crossroad.switchXTravel();
//                if (road.getStopLight()) {
//                    model.greenLightRoad(road);
//                } else {
//                    model.redLightRoad(road);
//                }
            }
        }

        for (OneWayRoad road : entrRoads) {
            model.addCar(road);
            model.updateRoad(road);
        }

    }

    public void start() {
        direction = DOWN;
        skyline = new Point(0, -1);
        veyron = new Point(2, -1);
        ferrari = new Point(3, -1);
        carSet.clear();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        updateRoads();
        dw.repaint();
        stopCounter++;

        if (skyline != null && veyron != null && !paused) {
            carSet.add(new Point(skyline.x, skyline.y));
            carSet2.add(new Point(veyron.x, veyron.y));

            if (direction == UP) {
                skyline = new Point(skyline.x, skyline.y - 1);
                veyron = new Point(veyron.x, veyron.y - 1);

            }
            if (direction == DOWN) {
                skyline = new Point(skyline.x, skyline.y + 1);
                veyron = new Point(veyron.x, veyron.y + 1);
            }
            if (direction == LEFT) {
                skyline = new Point(skyline.x - 1, skyline.y);
                veyron = new Point(veyron.x - 1, veyron.y);
            }
            if (direction == RIGHT) {
                skyline = new Point(skyline.x + 1, skyline.y);
                veyron = new Point(veyron.x + 1, veyron.y);
            }
            if (carSet.size() > carLength) {
                carSet.remove(0);
            }
            if (carSet2.size() > carLength) {
                carSet2.remove(0);
            }
        }
    }

    public boolean noTailAt(int x, int y) {
        for (Point point : carSet) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        for (Point point : carSet2) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        re = new RoadEnvironment();
    }

    @Override
    public void keyPressed(KeyEvent e) {
//		int i = e.getKeyCode();
//
//		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
//			direction = LEFT;
//		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
//			direction = RIGHT;
//		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
//			direction = UP;
//		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
//			direction = DOWN;
//		if (i == KeyEvent.VK_SPACE)
//			start();
//		if(i== KeyEvent.VK_ENTER)
//			paused = !paused;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
