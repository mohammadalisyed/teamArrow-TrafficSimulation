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
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();
        int lane;

        switch (road.getDirection()) {
            case RoadEnvironment.RIGHT:
                lane = new Random().nextInt(roadYLen);
                road.setRoadCell(0, lane, car);
                break;

            case RoadEnvironment.LEFT:
                lane = new Random().nextInt(roadYLen);
                road.setRoadCell(roadXLen - 1, lane, car);
                break;

            case RoadEnvironment.UP:
                lane = new Random().nextInt(roadXLen);
                road.setRoadCell(lane, roadYLen - 1, car);
                break;

            case RoadEnvironment.DOWN:
                lane = new Random().nextInt(roadXLen);
                road.setRoadCell(lane, 0, car);
                break;
        }
    }

    public void redLightRoad(OneWayRoad road) {
        road.setStopLight(true);
    }

    public void greenLightRoad(OneWayRoad road) {
        road.setStopLight(false);
    }

    public void switchLightRoad(OneWayRoad road) {
        if (road.getStopLight()) {
            road.setStopLight(false);
        } else {
            road.setStopLight(true);
        }
    }

    public void updateRoad(OneWayRoad road) {
        int direction = road.getDirection();

        switch (direction) {
            case RoadEnvironment.RIGHT:
                updateCarsRight(road);
                break;

            case RoadEnvironment.LEFT:
                updateCarsLeft(road);
                break;

            case RoadEnvironment.UP:
                updateCarsUp(road);
                break;

            case RoadEnvironment.DOWN:
                updateCarsDown(road);
                break;
        }
        resetCarsChk(road);
    }

    public void resetCarsChk(OneWayRoad road) {
        for (int y = 0; y < road.getRoadYLen(); y++) {
            for (int x = 0; x < road.getRoadXLen(); x++) {
                if (road.getRoadCell(x, y) != null) {
                    Vehicle currentCar = road.getRoadCell(x, y);
                    currentCar.setIsChecked(false);
                    road.setRoadCell(x, y, currentCar);
                }
            }
        }
    }

    public void updateCarsLeft(OneWayRoad road) {
        int direction = road.getDirection();
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV();
        boolean stopLight = road.getStopLight();

        for (int y = roadYLen - 1; y > -1; y--) {
//            System.out.println("y:" + y);
            for (int x = roadXLen - 1; x > -1; x--) {
//                System.out.println("");
//                System.out.print("x: " + x);
                if (road.getRoadCell(x, y) != null) {// there is a car in this current cell
//                    System.out.print(" car");
                    int currentX = x; //index of current car
                    Vehicle currentCar = road.getRoadCell(x, y);
                    boolean isChecked = currentCar.getIsChecked();

                    if (isChecked == false) {
                        int currentV = currentCar.getSpeed(); // current car's velocity
                        int dToRoadEnd = x + 1;
                        int nextCarX = -1; // index of next car, -1 : no car in front
                        int nextCarD = -1; // distance to next car, -1 : no car in front
                        int newX = -1; // current car's new cell

//                            find the index of next car in front:
                        for (int i = x - 1; i > -1; i--) {// find the next car in front's location
                            if (road.getRoadCell(i, y) != null) {
                                nextCarX = i; //index of next car
                                break;
                            }
                        }
                        // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                        if (nextCarX == -1) { // no, the road ahead is clear
//                            System.out.print(" no infront");
//                            currentV = acceleration(currentV, maxV);
                            if (stopLight) {
//                               System.out.print("v1:"+currentV);
//                                if (x + currentV > RoadXLen - 1) {
////                                    System.out.print(" clear:"+x+" v:"+currentV);
//                                }
                                currentV = acceleration(currentV, maxV);
//                                System.out.print(" v2:"+currentV);
//
//                                System.out.println("V: " + currentV + " D: " + dToRoadEnd);

                                currentV = braking(currentV, dToRoadEnd);
//                                System.out.println(" v3:"+currentV);
                            } else {
                                currentV = acceleration(currentV, maxV);
                            }
//                            System.out.print(" V:"+currentV);

                        } else { // yes, there's a car in front
                            nextCarD = currentX - nextCarX;
                            currentV = acceleration(currentV, maxV);
                            currentV = braking(currentV, nextCarD);
//                            System.out.print(" V:"+currentV);
                        }

                        // step 3: speed randomisation (to be add later)
                        if (currentV != 0) {
                            currentV = randomization(currentV);
                        }

                        // step 4: driving
                        newX = currentX - currentV;
//                        System.out.print(" newX:"+newX);
                        // will the car drive past the end of the road? 
                        if (newX < 0) { //yes
//                            System.out.print(" clear car");
                            road.clearRoadCell(x, y);
                        } else { //no
//                            System.out.print(" move car");
                            currentCar.setSpeed(currentV);
                            currentCar.setIsChecked(true);

                            road.clearRoadCell(x, y);
                            road.setRoadCell(newX, y, currentCar);
                        }
                    }
                }
            }
        }
    }

    public void updateCarsUp(OneWayRoad road) {
        int direction = road.getDirection();
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV();
        boolean stopLight = road.getStopLight();

        for (int x = roadXLen - 1; x > -1; x--) {
            for (int y = roadYLen - 1; y > -1; y--) {
//            System.out.println("y:" + y);

//                System.out.println("");
//                System.out.print("x: " + x);
                if (road.getRoadCell(x, y) != null) {// there is a car in this current cell
//                    System.out.print(" car");
                    int currentY = y; //index of current car
                    Vehicle currentCar = road.getRoadCell(x, y);
                    boolean isChecked = currentCar.getIsChecked();

                    if (isChecked == false) {
                        int currentV = currentCar.getSpeed(); // current car's velocity
                        int dToRoadEnd = y + 1;
                        int nextCarY = -1; // index of next car, -1 : no car in front
                        int nextCarD = -1; // distance to next car, -1 : no car in front
                        int newY = -1; // current car's new cell

//                            find the index of next car in front:
                        for (int i = y - 1; i > -1; i--) {// find the next car in front's location
                            if (road.getRoadCell(x, i) != null) {
                                nextCarY = i; //index of next car
                                break;
                            }
                        }
                        // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                        if (nextCarY == -1) { // no, the road ahead is clear
//                            System.out.print(" no infront");
//                            currentV = acceleration(currentV, maxV);
                            if (stopLight) {
//                               System.out.print("v1:"+currentV);
//                                if (x + currentV > RoadXLen - 1) {
////                                    System.out.print(" clear:"+x+" v:"+currentV);
//                                }
                                currentV = acceleration(currentV, maxV);
//                                System.out.print(" v2:"+currentV);
//
//                                System.out.println("V: " + currentV + " D: " + dToRoadEnd);

                                currentV = braking(currentV, dToRoadEnd);
//                                System.out.println(" v3:"+currentV);
                            } else {
                                currentV = acceleration(currentV, maxV);
                            }
//                            System.out.print(" V:"+currentV);

                        } else { // yes, there's a car in front
                            nextCarD = currentY - nextCarY;
                            currentV = acceleration(currentV, maxV);
                            currentV = braking(currentV, nextCarD);
//                            System.out.print(" V:"+currentV);
                        }

                        // step 3: speed randomisation (to be add later)
                        if (currentV != 0) {
                            currentV = randomization(currentV);
                        }

                        // step 4: driving
                        newY = currentY - currentV;
//                        System.out.print(" newX:"+newX);
                        // will the car drive past the end of the road? 
                        if (newY < 0) { //yes
//                            System.out.print(" clear car");
                            road.clearRoadCell(x, y);
                        } else { //no
//                            System.out.print(" move car");
                            currentCar.setSpeed(currentV);
                            currentCar.setIsChecked(true);

                            road.clearRoadCell(x, y);
                            road.setRoadCell(x, newY, currentCar);
                        }
                    }
                }
            }
        }
    }

    public void updateCarsDown(OneWayRoad road) {
        int direction = road.getDirection();
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV();
        boolean stopLight = road.getStopLight();

        for (int x = 0; x < roadXLen; x++) {
            for (int y = 0; y < roadYLen; y++) {

                if (road.getRoadCell(x, y) != null) {// there is a car in this current cell

                    int currentY = y; //index of current car
                    Vehicle currentCar = road.getRoadCell(x, y);
                    boolean isChecked = currentCar.getIsChecked();

                    if (isChecked == false) {
                        int currentV = currentCar.getSpeed(); // current car's velocity
                        int dToRoadEnd = roadYLen - y;
                        int nextCarY = -1; // index of next car, -1 : no car in front
                        int nextCarD = -1; // distance to next car, -1 : no car in front
                        int newY = -1; // current car's new cell

//                            find the index of next car in front:
                        for (int i = y + 1; i < roadYLen; i++) {// find the next car in front's location
                            if (road.getRoadCell(x, i) != null) {
                                nextCarY = i; //index of next car
                                break;
                            }
                        }
                        // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                        if (nextCarY == -1) { // no, the road ahead is clear
//                            currentV = acceleration(currentV, maxV);
                            if (stopLight) {
//                                System.out.print("v1:"+currentV);
                                if (y + currentV > roadYLen - 1) {
//                                    System.out.print(" clear:"+x+" v:"+currentV);
                                }
                                currentV = acceleration(currentV, maxV);
//                                System.out.print(" v2:"+currentV);

                                currentV = braking(currentV, roadYLen - y);
//                                System.out.println(" v3:"+currentV);
                            } else {
                                currentV = acceleration(currentV, maxV);
                            }

                        } else { // yes, there's a car in front
                            nextCarD = nextCarY - currentY;
                            currentV = acceleration(currentV, maxV);
                            currentV = braking(currentV, nextCarD);
                        }

                        // step 3: speed randomisation (to be add later)
                        if (currentV != 0) {
                            currentV = randomization(currentV);
                        }

                        // step 4: driving
                        newY = driving(currentY, currentV);
                        // will the car drive past the end of the road? 
                        if (newY >= roadYLen) { //yes
                            road.clearRoadCell(x, y);
                        } else { //no
                            currentCar.setSpeed(currentV);
                            currentCar.setIsChecked(true);

                            road.clearRoadCell(x, y);
                            road.setRoadCell(x, newY, currentCar);
                        }
                    }
                }
            }
        }
    }

    public void updateCarsRight(OneWayRoad road) {

        int direction = road.getDirection();
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV();
        boolean stopLight = road.getStopLight();

        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                if (road.getRoadCell(x, y) != null) {// there is a car in this current cell

                    int currentX = x; //index of current car
                    Vehicle currentCar = road.getRoadCell(x, y);
                    boolean isChecked = currentCar.getIsChecked();

                    if (isChecked == false) {
                        int currentV = currentCar.getSpeed(); // current car's velocity
                        int dToRoadEnd = roadXLen - x;
                        int nextCarX = -1; // index of next car, -1 : no car in front
                        int nextCarD = -1; // distance to next car, -1 : no car in front
                        int newX = -1; // current car's new cell

//                            find the index of next car in front:
                        for (int i = x + 1; i < roadXLen; i++) {// find the next car in front's location
                            if (road.getRoadCell(i, y) != null) {
                                nextCarX = i; //index of next car
                                break;
                            }
                        }
                        // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                        if (nextCarX == -1) { // no, the road ahead is clear
//                            currentV = acceleration(currentV, maxV);
                            if (stopLight) {
//                                System.out.print("v1:"+currentV);
                                if (x + currentV > roadXLen - 1) {
//                                    System.out.print(" clear:"+x+" v:"+currentV);
                                }
                                currentV = acceleration(currentV, maxV);
//                                System.out.print(" v2:"+currentV);

                                currentV = braking(currentV, dToRoadEnd);
//                                System.out.println(" v3:"+currentV);
                            } else {
                                currentV = acceleration(currentV, maxV);
                            }

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
                        // will the car drive past the end of the road? 
                        if (newX >= roadXLen) { //yes
                            road.clearRoadCell(x, y);
                        } else { //no
                            currentCar.setSpeed(currentV);
                            currentCar.setIsChecked(true);

                            road.clearRoadCell(x, y);
                            road.setRoadCell(newX, y, currentCar);
                        }
                    }
                }
            }
        }
    }

    public int acceleration(int currentV, int maxV) {
        int v = currentV + 1;
        if (v < maxV) {
            return v;
        } else {
            return maxV;
        }
    }

    public int braking(int currentV, int nextCarD) {
        int d = nextCarD - 1;
        if (d < currentV) {
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
