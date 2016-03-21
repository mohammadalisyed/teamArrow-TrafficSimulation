/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import java.util.ArrayList;

public class DisplayWindow extends JPanel {

    public DisplayWindow() {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        RoadEnvironment re = RoadEnvironment.re;
        OneWayRoad[] roadArray = re.roadArray;

        for (OneWayRoad road : roadArray) {
//                    System.out.println("x"+road.getX()+" y"+road.getY()+" w"+road.getRoadLen()*RoadEnvironment.SCALE+" h"+road.getNoOfLanes()*RoadEnvironment.SCALE);
//                    System.out.println("w:"+road.getNoOfLanes());
            g.setColor(Color.BLACK);
            g.drawRect(road.getX(), road.getY(), road.getRoadLen(), road.getNoOfLanes());
//            g.drawRect(road.getX(), road.getY(), road.getRoadLen() * RoadEnvironment.SCALE, road.getNoOfLanes() * RoadEnvironment.SCALE);

            ArrayList<Point> ptLst = road.getPointList();
            for (Point car : ptLst) {
//                g.setColor(Color.WHITE);
                g.setColor(Color.BLUE);

                g.drawRect(car.x, car.y, 1, 1);
//                g.fillRect(car.x * RoadEnvironment.SCALE, car.y * RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
            }

        }

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
