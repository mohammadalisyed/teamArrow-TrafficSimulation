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

    int maxV = 5;
    int roadLength;
    int noOfLanes;
    Cell[][] road;

    public OneWayRoad(int roadLength, int noOfLanes) {
        this.roadLength = roadLength;
        this.noOfLanes = noOfLanes;
        road = new Cell[roadLength][noOfLanes];
        resetRoad();
    }

    public void resetRoad() {
        for (int y = 0; y < noOfLanes; y++) {
            for (int x = 0; x < roadLength; x++) {
                road[x][y] = new Cell();
            }
        }
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

    public Cell getRoadCell(int x, int y) {
        return road[x][y];
    }

    public void clearRoadCell(int x, int y) {
        road[x][y].clearVehicle();
    }
    
    public void setRoadCell(int x, int y, Vehicle newVehicle) {
        road[x][y].setVehicle(newVehicle);
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
