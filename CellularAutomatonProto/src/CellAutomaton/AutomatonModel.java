/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.util.Random;

/**
 *
 * @author Alexander
 */
public class AutomatonModel {

    public AutomatonModel() {

    }

    public void addCar(OneWayRoad road) {
        Vehicle car = new Vehicle(1, 1);
        int roadYLen = road.getNoOfLanes();
        int lane = new Random().nextInt(roadYLen);
        road.setRoadCell(0, lane, car);
    }

    public void updateRoad(OneWayRoad road) {
        int roadYLen = road.getNoOfLanes();
        int roadXLen = road.getRoadLen();
        int maxV = road.getMaxV();

        for (int y = 0; y < roadYLen; y++) {
            int x = 0;
            while (x < roadXLen) {
                if (road.getRoadCell(x, y).getIsEmpty() == false) {// there is a car in this current cell
                    int currentX = x; // index of current car
//                    int currentV = road.getRoadCell(x, y).getVehicle().getSpeed(); // current car's velocity
                    Vehicle currentCar = road.getRoadCell(x, y).getVehicle();
                    int currentV = currentCar.getSpeed();
                    
                    int nextCarX = -1; // index of next car, -1 : no car in front
                    int nextCarD = -1; // distance to next car, -1 : no car in front
                    int newX = -1; // current car's new cell
//                        boolean endOfRoad; // is this cell the last cell?

                    if (x == roadXLen - 1) { // are we at the last cell?                           
//                            endOfRoad = true; this is the last cell
                        road.clearRoadCell(x, y);
                        break;

                    } else {
//                            endOfRoad = false; this isn't the last cell
//                            find the index of next car in front:
                        for (int i = x + 1; i < roadXLen; i++) {// find the next car in front's location
                            if (road.getRoadCell(i, y).getIsEmpty() == false) {
                                nextCarX = i; //index of next car
                                break;
                            }
                        }
                        // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                        if (nextCarX == -1) { // no, the road ahead is clear
                            currentV = acceleration(currentV, maxV);

                        } else { // yes, there's a car in front
                            nextCarD = nextCarX - currentX;
                            currentV = acceleration(currentV, maxV);
                            currentV = braking(currentV, nextCarD);
                        }

                        // step 3: speed randomisation (to be add later)
                        if (currentV != 0) {
                            currentV = randomization(currentV);
                        }

                        // step 4: driving
                        newX = driving(currentX, currentV);
//                        System.out.println(newX);
                        // will the car drive past the end of the road? 
                        if (newX >= roadXLen) { //yes
                            road.clearRoadCell(x, y);
                        } else { //no
                            currentCar.setSpeed(currentV);
                            road.setRoadCell(newX, y, currentCar);
                            road.clearRoadCell(x, y);

                        }
                        //move the counter to the cell ahead of were the car has been moved to
                        x = newX + 1;
                    }
                } else {//no car on this cell, check next cell
                    x++;
                }
            }

        }
    }

    public int acceleration(int currentV, int maxV) {
        int v = currentV + 1;
        if (v <= maxV) {
            return v;
        } else {
            return maxV;
        }
    }

    public int braking(int currentV, int nextCarD) {
        int d = nextCarD - 1;
        if (d <= currentV) {
            return d;
        } else {
            return currentV;
        }

    }

    public int driving(int currentX, int currentV) {
        return (currentX + currentV);
    }

    public int randomization(int currentV) {
        int v = currentV - 1;
        boolean p = new Random().nextInt(2) == 0;
        if (p) {
            return v;
        } else {
            return currentV;
        }
    }

}
