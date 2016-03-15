/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

/**
 *
 * @author Alexander
 */
public class OneWayRoad {

    private int maxV = 5;
    private int roadLength;
    private int noOfLanes;
    private Vehicle[][] road;
    private boolean stopLight = false;

    public OneWayRoad(int roadLength, int noOfLanes) {
        this.roadLength = roadLength;
        this.noOfLanes = noOfLanes;
        road = new Vehicle[roadLength][noOfLanes];
        resetRoad();
    }

    public void resetRoad() {
        for (int y = 0; y < noOfLanes; y++) {
            for (int x = 0; x < roadLength; x++) {
                road[x][y] = null;
            }
        }
    }
    
    public boolean getStopLight(){
        return stopLight;
    }
    
    public void setStopLight(boolean stop){
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
