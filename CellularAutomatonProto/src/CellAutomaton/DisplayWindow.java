/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import java.util.ArrayList;

public class DisplayWindow extends JPanel {

    public DisplayWindow() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        RoadEnvironment re = RoadEnvironment.re;
//        ArrayList<OneWayRoad> roadArray = re.roadArray;
//        ArrayList<Junction> junctArray = re.junctArray;

        ArrayList<OneWayRoad> roadArray = re.getRoadNetwork().getRoadArray();
        ArrayList<Junction> junctArray = re.getRoadNetwork().getJunctArray();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(RoadEnvironment.SCALE, RoadEnvironment.SCALE);
        g2.setStroke(new BasicStroke(0.01f));

        for (OneWayRoad road : roadArray) {

            g2.setColor(Color.BLACK);

            g2.drawRect(road.getX(), road.getY(), road.getRoadXLen(), road.getRoadYLen());

            ArrayList<Vehicle> carLst = road.getVehicleLst();
            for (Vehicle car : carLst) {
                int x = car.getLocation().x;
                int y = car.getLocation().y;
                switch (car.getDirection()) {

                    case RoadEnvironment.LEFT:
                        g2.setColor(Color.BLUE);
                        break;
                    case RoadEnvironment.RIGHT:
                        g2.setColor(Color.CYAN);
                        break;
                    case RoadEnvironment.UP:
                        g2.setColor(Color.MAGENTA);
                        break;
                    case RoadEnvironment.DOWN:
                        g2.setColor(Color.black);
                        break;
                }
                
                if(road.getStopLight()){
                    g2.setColor(Color.RED);
                }

                if (car.getTurning()) {
                    g2.fillRect(x, y, 1, 1);
                } else {
                    g2.drawRect(x, y, 1, 1);
                }
            }
        }

        for (Junction junct : junctArray) {
            g2.setColor(Color.BLACK);
            g2.drawRect(junct.getX(), junct.getY(), junct.getRoadXLen(), junct.getRoadYLen());

            ArrayList<Vehicle> carLst = junct.getVehicleLst();
            for (Vehicle car : carLst) {
                int x = car.getLocation().x;
                int y = car.getLocation().y;
                switch (car.getDirection()) {

                    case RoadEnvironment.LEFT:
                        g2.setColor(Color.BLUE);
                        break;
                    case RoadEnvironment.RIGHT:
                        g2.setColor(Color.CYAN);
                        break;
                    case RoadEnvironment.UP:
                        g2.setColor(Color.MAGENTA);
                        break;
                    case RoadEnvironment.DOWN:
                        g2.setColor(Color.black);
                        break;
                }

                if (car.getTurning()) {
                    g2.fillRect(x, y, 1, 1);
                } else {
                    g2.drawRect(x, y, 1, 1);
                }

            }

        }

        g2.dispose();
    }
}
