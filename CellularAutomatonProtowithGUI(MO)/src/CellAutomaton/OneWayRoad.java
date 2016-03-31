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
public class OneWayRoad implements RoadInt {

    private int maxV = 5;
    private int roadXLen;
    private int roadYLen;
    private int roadX;
    private int roadY;
    private int direction = RoadEnvironment.LEFT;
    private Junction exit;
    private Junction entr;

    private Vehicle[][] road;
    private boolean stopLight = false;
    private int[] laneDirect;
    
    private String roadName;

    public OneWayRoad(int roadXLen, int roadYLen, int roadX, int roadY, int direction) {
        this.roadXLen = roadXLen;
        this.roadYLen = roadYLen;
        this.roadX = roadX;
        this.roadY = roadY;

        this.direction = direction;
        road = new Vehicle[roadXLen][roadYLen];

        switch (direction) {
            case RoadEnvironment.UP:
            case RoadEnvironment.DOWN:
                laneDirect = new int[roadXLen];
                for (int i = 0; i < roadXLen; i++){
                    laneDirect[i] = direction;
                }
                break;
            case RoadEnvironment.LEFT:
            case RoadEnvironment.RIGHT:
                laneDirect = new int[roadYLen];
                for (int i = 0; i < roadYLen; i++){
                    laneDirect[i] = direction;
                }
                break;
                
        }
    }
    
    public OneWayRoad(int roadXLen, int roadYLen, int roadX, int roadY, int direction, String roadName) {
        this(roadXLen,roadYLen,roadX,roadY,direction);
        this.roadName = roadName;
    }
    
    public String getRoadName(){
        return roadName;
    }

    public void resetRoad() {

        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                road[x][y] = null;

            }
        }
    }
    public int getLaneDirect(int i){
        return laneDirect[i];
    }
    
    public int[] getLaneDirect(){
        return laneDirect;
    }
   
    public void setLaneDirect(int laneNo, int direction){
        laneDirect[laneNo] = direction;
    } 

    public Junction getExit(int direction) {
        return exit;
    }

    public RoadInt getEntr(int direction) {
        return entr;
    }

    public void setExit(Junction newExit) {
        exit = newExit;
    }

    public void setEntr(Junction newEntr) {
        entr = newEntr;
    }

    public int getDirection() {
        return direction;
    }

    public ArrayList<Vehicle> getVehicleLst() {
        ArrayList<Vehicle> carLst = new ArrayList<Vehicle>();

        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                if (road[x][y] != null) {
                    Vehicle newCar = getRoadCell(x, y);
                    newCar.setLocation(new Point(x + roadX, y + roadY));
                    carLst.add(newCar);
                }
            }
        }
        return carLst;
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

    public void switchLightRoad() {
        if (getStopLight()) {
            setStopLight(false);
        } else {
            setStopLight(true);
        }
    }

    public int getRoadXLen() {
        return roadXLen;
    }

    public int getRoadYLen() {
        return roadYLen;
    }

    public int getMaxV(int direction) {
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
