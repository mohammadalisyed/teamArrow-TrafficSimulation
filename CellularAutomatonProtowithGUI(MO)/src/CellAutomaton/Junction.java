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
public class Junction implements RoadInt {

    private Vehicle[][] junct;
    private OneWayRoad[] entrRoads;
    private OneWayRoad[] exitRoads;

//    private Boolean xTravel;// junction is open to cars entering from horizontally
    private int x;
    private int y;
    private int width;
    private int height;

//    private int clearanceTime;
    public Junction(int x, int y,
            OneWayRoad[] exitRoads, OneWayRoad[] entrRoads, int width, int height) {
        this.x = x;
        this.y = y;
//        this.entrRoads = entrRoads;
        this.exitRoads = exitRoads;
        this.entrRoads = entrRoads;

        junct = new Vehicle[width][height];
        this.width = width;
        this.height = height;

    }

    public OneWayRoad getExit(int exitDirect) {
        return exitRoads[exitDirect];
    }

    public OneWayRoad getEntr(int entrDirect) {
        return entrRoads[entrDirect];
    }

    @Override
    public void resetRoad() {

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                junct[w][h] = null;

            }
        }
    }

    @Override
    public ArrayList<Point> getPointList() {
        ArrayList<Point> ptLst = new ArrayList<Point>();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (junct[w][h] != null) {
                    ptLst.add(new Point(w + x, h + y));
                }
            }
        }
        return ptLst;
    }

    public ArrayList<Vehicle> getVehicleLst() {
        ArrayList<Vehicle> carLst = new ArrayList<Vehicle>();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (junct[w][h] != null) {
                    Vehicle newCar = getRoadCell(w, h);
                    newCar.setLocation(new Point(w + x, h + y));
                    carLst.add(newCar);
                }
            }
        }
        return carLst;
    }

    public int getMaxV(int direction) {
        int maxV = 1;
        RoadInt entRoad = getEntr(direction);
        if (entRoad != null) {
            maxV = entRoad.getMaxV(direction);
        }
        return maxV;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getRoadXLen() {
        return width;
    }

    @Override
    public int getRoadYLen() {
        return height;
    }

    @Override
    public Vehicle getRoadCell(int x, int y) {
        return junct[x][y];

    }

    @Override
    public void clearRoadCell(int x, int y) {
        junct[x][y] = null;
    }

    @Override
    public void setRoadCell(int x, int y, Vehicle newV) {
        junct[x][y] = newV;
    }

//    public boolean getXTravel() {
//        return xTravel;
//    }
//
//    public void setXTravel(boolean newBool) {
//        xTravel = newBool;
//    }

    @Override
    public boolean getStopLight() {
        return false;
    }
    
    public void switchStopLights(){
        for (OneWayRoad road : entrRoads) {
            road.switchLightRoad();
        }
        
    }

//    public void switchXTravel() {
//        if (xTravel) {
//            xTravel = false;
//        } else {
//            xTravel = true;
//        }
//    }

}
