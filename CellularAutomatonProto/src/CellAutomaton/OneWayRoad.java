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
    private int roadXLen;
    private int roadYLen;
    private int roadX;
    private int roadY;
    private int direction = RoadEnvironment.LEFT;
    private Junction exit;

    private Vehicle[][] road;
    private boolean stopLight = false;

    public OneWayRoad(int roadXLen, int roadYLen, int roadX, int roadY, int direction, Junction exit) {
        this.roadXLen = roadXLen;
        this.roadYLen = roadYLen;
        this.roadX = roadX;
        this.roadY = roadY;

        this.direction = direction;
        road = new Vehicle[roadXLen][roadYLen];
        this.exit = exit;
    }

    public void resetRoad() {

        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                road[x][y] = null;

            }
        }
    }
    
    public Junction getExit(){
        return exit;
    }
    
    public void setExit(Junction newExit){
        exit = newExit;
    }

    public int getDirection() {
        return direction;
    }

    public ArrayList<Point> getPointList() {
        ArrayList<Point> ptLst = new ArrayList<Point>();

        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                if (road[x][y] != null) {
                    ptLst.add(new Point(x + roadX, y + roadY));
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

    public int getRoadXLen() {
        return roadXLen;
    }

    public int getRoadYLen() {
        return roadYLen;
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
        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
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
