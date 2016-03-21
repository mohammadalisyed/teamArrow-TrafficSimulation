/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class OneWayRoad {

    private int maxV = 5;
    private int roadLength;
    private int noOfLanes;
    private int roadX;
    private int roadY;
    private int direction = RoadEnvironment.LEFT;

    private Vehicle[][] road;
    private boolean stopLight = false;

//    public OneWayRoad(int roadLength, int noOfLanes, int roadX, int roadY) {
//        this.roadLength = roadLength;
//        this.noOfLanes = noOfLanes;
//        this.roadX = roadX;
//        this.roadY = roadY;
//        road = new Vehicle[roadLength][noOfLanes];
//        resetRoad();
//    }

    public OneWayRoad(int roadLength, int noOfLanes, int roadX, int roadY, int direction) {
        this.roadLength = roadLength;
        this.noOfLanes = noOfLanes;
        this.roadX = roadX;
        this.roadY = roadY;
        road = new Vehicle[roadLength][noOfLanes];
        this.direction = direction;
        resetRoad();
    }

    public void resetRoad() {
        for (int y = 0; y < noOfLanes; y++) {
            for (int x = 0; x < roadLength; x++) {
                road[x][y] = null;
            }
        }
    }

    public int getDirection() {
        return direction;
    }

    public ArrayList<Point> getPointList() {
        ArrayList<Point> ptLst = new ArrayList<Point>();
        for (int y = 0; y < noOfLanes; y++) {
            for (int x = 0; x < roadLength; x++) {
                if (road[x][y] != null) {
                    int altX;
                    int altY;
                    switch (direction) {
//                      ptLst.add(new Point(length, lanes));
                        case RoadEnvironment.RIGHT:
                            ptLst.add(new Point(x + roadX, y + roadY));
                            break;
                        case RoadEnvironment.LEFT:
                            altX = roadX + roadLength;
                            ptLst.add(new Point(altX - x - 1,y + roadY));
                            break;
//                      ptLst.add(new Point(lanes, lengths));
                        case RoadEnvironment.UP:
                            altY = roadY + roadLength;
                            ptLst.add(new Point(y + roadX,altY - x -1));
                            break;
                        case RoadEnvironment.DOWN:
                            ptLst.add(new Point(y + roadX,x + roadY));
                            break;
                    }

                }
            }
        }
        return ptLst;
    }

    public int getX() {
        return roadX;
    }

    public int getY() {
        return roadY;
    }

    public boolean getStopLight() {
        return stopLight;
    }

    public void setStopLight(boolean stop) {
        stopLight = stop;
    }

    public int getRoadLen() {
        return roadLength;
    }

    public int getNoOfLanes() {
        return noOfLanes;
    }

    public int getMaxV() {
        return maxV;
    }

    public void setMaxV(int maxV) {
        this.maxV = maxV;
    }

    public Vehicle getRoadCell(int x, int y) {
        return road[x][y];
    }

    public void clearRoadCell(int x, int y) {
        road[x][y] = null;
    }

    public void setRoadCell(int x, int y, Vehicle newVehicle) {
        road[x][y] = newVehicle;
    }

    public void displayRoad() {
        for (int y = 0; y < noOfLanes; y++) {
            for (int x = 0; x < roadLength; x++) {
                if (road[x][y] == null) {
                    System.out.print("-");
                } else {
                    System.out.print(road[x][y]);
                }
            }
            System.out.println();
        }
        System.out.println(";");
    }
}
