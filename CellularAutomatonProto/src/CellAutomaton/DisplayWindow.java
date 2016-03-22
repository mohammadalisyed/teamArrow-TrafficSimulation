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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        RoadEnvironment re = RoadEnvironment.re;
        ArrayList<OneWayRoad> roadArray = re.roadArray;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(RoadEnvironment.SCALE, RoadEnvironment.SCALE);
        g2.setStroke(new BasicStroke(0.01f));

        for (OneWayRoad road : roadArray) {

            g2.setColor(Color.BLACK);

//            switch (road.getDirection()) {
//                case RoadEnvironment.LEFT:
//                    g2.drawRect(road.getX(), road.getY(), road.getRoadLen(), road.getNoOfLanes());
//                    break;
//                case RoadEnvironment.RIGHT:
//                    g2.drawRect(road.getX(), road.getY(), road.getRoadLen(), road.getNoOfLanes());
//                    break;
//                case RoadEnvironment.UP:
//                    g2.drawRect(road.getX(), road.getY(), road.getNoOfLanes(), road.getRoadLen());
//                    break;
//                case RoadEnvironment.DOWN:
//                    g2.drawRect(road.getX(), road.getY(), road.getNoOfLanes(), road.getRoadLen());
//                    break;
//            }
                                g2.drawRect(road.getX(), road.getY(), road.getRoadXLen(), road.getRoadYLen());


            ArrayList<Point> ptLst = road.getPointList();
            for (Point car : ptLst) {
                int x = car.x;
                int y = car.y;

                if (road.getStopLight()) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.BLUE);
                }

                g2.fillRect(x,y, 1, 1);

            }

        }

        g2.dispose();

//        g.setColor(Color.WHITE);
//        g.setColor(Color.BLUE);
//
//        for (Point point : re.carSet) {
//            for (int x = 0; x <= 250; x++) {
//                g.fillRect(point.x + RoadEnvironment.SCALE, point.y + RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
//            }
//        }
//        for (Point point : re.carSet2) {
//            g.fillRect(point.x * RoadEnvironment.SCALE, point.y * RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
//        }
//        g.fillRect(re.skyline.x + RoadEnvironment.SCALE, re.skyline.y + RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
//        g.fillRect(re.veyron.x * RoadEnvironment.SCALE, re.veyron.y * RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
    }
}
