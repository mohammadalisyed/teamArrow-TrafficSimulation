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
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 5;
    public int direction;
    public int carLength = 2;
    
    OneWayRoad westRoad = new OneWayRoad(50, 3, 0, 250);
    OneWayRoad[] roadArray = new OneWayRoad[1];
    AutomatonModel model = new AutomatonModel();
    
    public RoadEnvironment() {
        roadArray[0] = westRoad;
        
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
        for (OneWayRoad road : roadArray) {
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
