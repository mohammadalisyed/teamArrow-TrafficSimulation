/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alexander
 */
public class AutomatonModel {

//    ArrayList<OneWayRoad> networkExit;
    public AutomatonModel(ArrayList<OneWayRoad> networkExit) {
//        this.networkExit = networkExit;
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

    public void updateJunct(Junction junct) {
        int height = junct.getHeight();
        int width = junct.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (junct.getRoadCell(x, y) != null) {

                    int currentX = x;

//                    System.out.println("");
//                    System.out.print("x1:" + currentX + " y:"+ y);
                    Vehicle currentCar = junct.getRoadCell(x, y);
                    boolean isChecked = currentCar.getIsChecked();

                    if (isChecked == false) {
                        int direction = currentCar.getDirection();
                        int currentV = currentCar.getSpeed();
                        int maxV = 5;

                        int dToRoadEnd = width - x;
                        int nextCarX = -1; // index of next car, -1 : no car in front
                        int nextCarD = -1; // distance to next car, -1 : no car in front
                        int newX = -1; // current car's new cell

                        switch (direction) {
                            case RoadEnvironment.RIGHT:
                                for (int i = x + 1; i < width; i++) {
                                    if (junct.getRoadCell(i, y) != null) {
                                        nextCarX = i; //index of next car
                                        break;
                                    }
                                }
                                if (nextCarX == -1) {//no car in junction
                                    currentV = acceleration(currentV, maxV);
                                } else {// car in junction
                                    nextCarD = nextCarX - currentX;
                                    currentV = acceleration(currentV, maxV);
                                    currentV = braking(currentV, nextCarD);

                                }

                                newX = driving(currentX, currentV);

                                currentCar.setSpeed(currentV);
                                currentCar.setIsChecked(true);

//                                  will car exit junction?
                                if (newX >= width) {//yes

                                    OneWayRoad road = junct.getExt(direction);//get exit road;

                                    int nextCarXExit = -1;
                                    if (road != null) {
//                                        System.out.println("");
//                                        System.out.print("junct/ exit road found /");

                                        //find next car on exit road
                                        for (int j = 0; j < road.getRoadXLen(); j++) {
                                            if (road.getRoadCell(j, y) != null) {
                                                nextCarXExit = j;
                                            }
                                            break;
                                        }

                                        if (nextCarXExit == -1) {//no car on exit road;

                                            newX -= width;
                                            junct.clearRoadCell(x, y);
                                            road.setRoadCell(newX, y, currentCar);
                                        } else {// car on exit road;

                                            nextCarD = (nextCarXExit + width) - currentX;;
                                            currentV = braking(currentV, nextCarD);
                                            newX = driving(currentX, currentV);

                                            currentCar.setSpeed(currentV);

                                            if (newX >= width) {//car will still land on road
                                                junct.clearRoadCell(x, y);
                                                road.setRoadCell(newX - width, y, currentCar);

                                            } else {//car stays in junction
                                                road.clearRoadCell(x, y);
                                                road.setRoadCell(newX, y, currentCar);
                                            }
                                        }
                                    }
                                } else {
                                    junct.clearRoadCell(x, y);
                                    junct.setRoadCell(newX, y, currentCar);
                                }
                                break;
                            case RoadEnvironment.LEFT:
//                                System.out.println("left");
                                break;
                            case RoadEnvironment.UP:
                                break;
                            case RoadEnvironment.DOWN:
                                break;

                        }
                    }
                }
            }
        }
        resetCarsChk(junct);
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

    public void resetCarsChk(Junction junct) {
        for (int y = 0; y < junct.getHeight(); y++) {
            for (int x = 0; x < junct.getWidth(); x++) {
                if (junct.getRoadCell(x, y) != null) {
                    Vehicle currentCar = junct.getRoadCell(x, y);
                    currentCar.setIsChecked(false);
                    junct.setRoadCell(x, y, currentCar);
                }
            }
        }
    }

    public void updateCarsLeft(OneWayRoad road) {
//        System.out.println("/road/");
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
                            if (stopLight) {
                                currentV = acceleration(currentV, maxV);
                                currentV = braking(currentV, dToRoadEnd);

                            } else {
                                currentV = acceleration(currentV, maxV);
                            }

                        } else { // yes, there's a car in front
                            nextCarD = currentX - nextCarX;
                            currentV = acceleration(currentV, maxV);
                            currentV = braking(currentV, nextCarD);
                        }

                        // step 3: speed randomisation (to be add later)
                        if (currentV != 0) {
                            currentV = randomization(currentV);
                        }

                        // step 4: driving
//                        System.out.print("/x1:" + newX + "/v1:" + currentV);
                        newX = currentX - currentV;
//                        System.out.print("/x2:" + newX);
                        currentCar.setSpeed(currentV);
                        currentCar.setIsChecked(true);
                        currentCar.setDirection(direction);

                        // will the car drive past the end of the road? 
                        if (newX < 0) {
                            road.clearRoadCell(x, y);
//                            System.out.print("/x2:" + newX + "/v2:" + currentV);
                            //yes
//                            if (road.getExit() == null) {//does this road exit the network?
//                                System.out.println("clear");
//                                road.clearRoadCell(x, y);//Y: remove car from network
//
//                            } else {
//
//                                Junction junct = road.getExit();
//                                int junctCar = chckJunctLeft(junct, y);
////
//                                if (junctCar == -1) {// There isn't a car in this car's path
//                                    if (newX + junct.getWidth() < 0) {//car clear's junction
//                                        //                                        add car to next road over
//                                        road.clearRoadCell(x, y);
////
//                                    } else {//car lands in junction
//                                        newX += junct.getWidth();
////                                        System.out.print(" newX:" + newX);
//                                        road.clearRoadCell(x, y);
//
//                                        if (junct.getEntr(direction) == null) {
//                                            junct.setRoadCell(newX, y, currentCar);
//                                        } else {
//                                            int newY = y + junct.getEntr(direction).getRoadYLen();
//                                            junct.setRoadCell(newX, newY, currentCar);
//                                        }
//                                    }
//                                } else {//There is a car in this car's path
//                                    nextCarD = currentX - junctCar;
//                                    currentV = braking(currentV, nextCarD);
//                                    newX = driving(currentX, currentV);
//
//                                    currentCar.setSpeed(currentV);
////
//                                    if (newX < 0) {//car will still land in junction
//                                        System.out.println(newX);
//
//                                        if (junct.getEntr(direction) == null) {
//                                            junct.setRoadCell(newX + junct.getWidth(), y, currentCar);
//                                        } else {
//                                            int newY = y + junct.getEntr(direction).getRoadYLen();
//                                            junct.setRoadCell(newX+ junct.getWidth(), newY, currentCar);
//                                        }
////
//                                    } else {//car stays on road
//                                        road.clearRoadCell(x, y);
//                                        road.setRoadCell(newX, y, currentCar);
//                                    }
//                                }
//                            }
                        } else { //no
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
//        ArrayList<Vehicle> passedCars = new ArrayList<>();

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
                            if (stopLight) {
                                currentV = acceleration(currentV, maxV);
                                currentV = braking(currentV, dToRoadEnd);
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

                        currentCar.setSpeed(currentV);
                        currentCar.setIsChecked(true);
//                        currentCar.setLocation(new Point(x, y));
                        currentCar.setDirection(direction);

//                      will the car drive past the end of the road? 
                        if (newX >= roadXLen) { //yes

                            if (road.getExit() == null) {//does this road exit the network?

                                road.clearRoadCell(x, y);//Y: remove car from network

                            } else {

                                Junction junct = road.getExit();
                                int junctCar = chckJunctRight(junct, y);

                                if (junctCar == -1) {// There isn't a car in this car's path
                                    if (newX >= junct.getWidth() + roadXLen) {//car clear's junction
//                                        add car to next road over
                                        road.clearRoadCell(x, y);

                                    } else {//car lands in junction
                                        newX -= roadXLen;
                                        road.clearRoadCell(x, y);
                                        junct.setRoadCell(newX, y, currentCar);
                                    }
                                } else {//There is a car in this car's path
                                    nextCarD = (junctCar + roadXLen) - currentX;
                                    currentV = braking(currentV, nextCarD);
                                    newX = driving(currentX, currentV);

                                    currentCar.setSpeed(currentV);

                                    if (newX >= roadXLen) {//car will still land in junction

                                        road.clearRoadCell(x, y);
                                        junct.setRoadCell(newX - roadXLen, y, currentCar);

                                    } else {//car stays on road

                                        road.clearRoadCell(x, y);
                                        road.setRoadCell(newX, y, currentCar);
                                    }
                                }
                            }
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(newX, y, currentCar);
                        }
                    }
                }
            }
        }
//        return passedCars;
    }

    public int chckJunctRight(Junction junct, int y) {
        int carX = -1;
        for (int x = 0; x < junct.getWidth(); x++) {
            if (junct.getRoadCell(x, y) != null) {
                carX = x;
//                System.out.println("junctX: " + x);
                break;
            }
        }
        return carX;
    }

    public int chckJunctLeft(Junction junct, int y) {
        int carX = -1;
        for (int x = junct.getWidth() - 1; x > -1; x--) {
            if (junct.getRoadCell(x, y) != null) {
                carX = x;
//                System.out.println("junctX: " + x);
                break;
            }
        }
        return carX;
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
