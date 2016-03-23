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
                car.setLocation(new Point(0, lane));
                road.setRoadCell(0, lane, car);
                break;

            case RoadEnvironment.LEFT:
                lane = new Random().nextInt(roadYLen);
                car.setLocation(new Point(roadXLen - 1, lane));
                road.setRoadCell(roadXLen - 1, lane, car);
                break;

            case RoadEnvironment.UP:
                lane = new Random().nextInt(roadXLen);
                car.setLocation(new Point(lane, roadYLen - 1));
                road.setRoadCell(lane, roadYLen - 1, car);
                break;

            case RoadEnvironment.DOWN:
                lane = new Random().nextInt(roadXLen);
                car.setLocation(new Point(lane, 0));
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

    public void updateJunct(RoadInt junct) {
        int height = junct.getRoadYLen();
        int width = junct.getRoadYLen();

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
//                                driveRight(junct, x, y);
                                driveCar(RoadEnvironment.RIGHT, junct, x, y);
                                break;
                            case RoadEnvironment.LEFT:
//                                driveLeft(junct, x, y);
//                                driveCar(RoadEnvironment.LEFT, junct, x,y);
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

    public void resetCarsChk(RoadInt road) {
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

    //UP-
    public void driveUp(RoadInt road, int x, int y) {
        int direct = RoadEnvironment.UP;
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV(direct);
        boolean stopLight = road.getStopLight();

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
                nextCarY = chckRoad(y - 1, -1, x, road, direct);

                // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                if (nextCarY == -1) { // no, the road ahead is clear
                    if (stopLight) {

                        currentV = acceleration(currentV, maxV);
                        currentV = braking(currentV, dToRoadEnd);

                    } else {
                        currentV = acceleration(currentV, maxV);
                    }
//                            System.out.print(" V:"+currentV);

                } else { // yes, there's a car in front
                    nextCarD = currentY - nextCarY;
//                    nextCarD = driving(currentY, nextCarY, direct);
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

                currentCar.setSpeed(currentV);
                currentCar.setIsChecked(true);
                currentCar.setDirection(direct);
//                        System.out.print(" newX:"+newX);
                // will the car drive past the end of the road? 
                if (newY < 0) { //yes
//                            System.out.print(" clear car");
                    road.clearRoadCell(x, y);
                } else { //no
//                            System.out.print(" move car");

                    road.clearRoadCell(x, y);
                    road.setRoadCell(x, newY, currentCar);
                }
            }
        }
    }

    public void updateCarsUp(RoadInt road) {
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        for (int x = roadXLen - 1; x > -1; x--) {
            for (int y = roadYLen - 1; y > -1; y--) {
                driveCar(RoadEnvironment.UP, road, x, y);
            }
        }
    }
    //-

    //DOWN-
    public void driveDown(RoadInt road, int x, int y) {
        int direct = RoadEnvironment.DOWN;
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV(direct);
        boolean stopLight = road.getStopLight();

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
                nextCarY = chckRoad(y + 1, roadYLen, x, road, direct);

                // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                if (nextCarY == -1) { // no, the road ahead is clear
//                            currentV = acceleration(currentV, maxV);
                    if (stopLight) {
//                                System.out.print("v1:"+currentV);
                        currentV = acceleration(currentV, maxV);
//                                System.out.print(" v2:"+currentV);
                        currentV = braking(currentV, dToRoadEnd);
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

                currentCar.setSpeed(currentV);
                currentCar.setIsChecked(true);
                currentCar.setDirection(direct);
                // will the car drive past the end of the road? 
                if (newY >= roadYLen) { //yes
                    road.clearRoadCell(x, y);
                } else { //no

                    road.clearRoadCell(x, y);
                    road.setRoadCell(x, newY, currentCar);
                }
            }
        }

    }

    public void updateCarsDown(RoadInt road) {
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        for (int x = 0; x < roadXLen; x++) {
            for (int y = 0; y < roadYLen; y++) {
                driveCar(RoadEnvironment.DOWN, road, x, y);
            }
        }
    }
    //-

    //LEFT-
    public void driveLeft(RoadInt road, int x, int y) {
        int direct = RoadEnvironment.LEFT;
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV(direct);
        boolean stopLight = road.getStopLight();

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
                nextCarX = chckRoad(x - 1, -1, y, road, direct);
//                for (int i = x - 1; i > -1; i--) {// find the next car in front's location
//                    if (road.getRoadCell(i, y) != null) {
//                        nextCarX = i; //index of next car
//                        break;
//                    }
//                }

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

                currentCar.setSpeed(currentV);
                currentCar.setIsChecked(true);
                currentCar.setDirection(direct);

                // will the car drive past the end of the road? 
                if (newX < 0) {
                    road.clearRoadCell(x, y);
                } else { //no
                    road.clearRoadCell(x, y);
                    road.setRoadCell(newX, y, currentCar);
                }
            }
        }
    }

    public void updateCarsLeft(RoadInt road) {

        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        for (int y = roadYLen - 1; y > -1; y--) {
            for (int x = roadXLen - 1; x > -1; x--) {
                driveCar(RoadEnvironment.LEFT, road, x, y);
            }
        }
    }
    //-

    //RIGHT-
    public void driveRight(RoadInt road, int x, int y) {

        if (road.getRoadCell(x, y) != null) {// there is a car in this current cell

            int direct = RoadEnvironment.RIGHT;
            int roadYLen = road.getRoadYLen();
            int roadXLen = road.getRoadXLen();

            int maxV = road.getMaxV(direct);
            boolean stopLight = road.getStopLight();

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
                nextCarX = chckRoad((x + 1), roadXLen, y, road, direct);

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
                currentCar.setDirection(direct);

//                      will the car drive past the end of the road? 
                if (newX >= roadXLen) { //yes

                    if (road.getExit(direct) == null) {//does this road exit the network?
                        road.clearRoadCell(x, y);//Y: remove car from network

                    } else {

                        RoadInt junct = road.getExit(direct);
                        int junctCar = chckRoad(0, junct.getRoadXLen(), y, junct, direct);

                        if (junctCar == -1) {// There isn't a car in this car's path
                            if (newX >= junct.getRoadXLen() + roadXLen) {//car clear's junction
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

    public void updateRightRoad(OneWayRoad road) {

        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                driveCar(RoadEnvironment.RIGHT, road, x, y);
            }
        }
    }
    //-

    public void driveCar(int direction, RoadInt road, int x, int y) {
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV(direction);
        boolean stopLight = road.getStopLight();

        if (road.getRoadCell(x, y) != null) {

            int currentCell = -1; //index of current car
            Vehicle currentCar = road.getRoadCell(x, y);
            boolean isChecked = currentCar.getIsChecked();

            switch (direction) {
                case RoadEnvironment.UP:
                case RoadEnvironment.DOWN:
                    currentCell = y;
                    break;
                case RoadEnvironment.LEFT:
                case RoadEnvironment.RIGHT:
                    currentCell = x;
                    break;
            }

            if (isChecked == false) {

                int currentV = currentCar.getSpeed(); // current car's velocity
                int dToRoadEnd = -1;
                int nextCarCell = -1; // index of next car, -1 : no car in front
                int nextCarD = -1; // distance to next car, -1 : no car in front
                int newCell; // current car's new cell

                switch (direction) {
                    case RoadEnvironment.UP:
                        dToRoadEnd = y + 1;
                        nextCarCell = chckRoad(y - 1, -1, x, road, direction);
                        break;
                    case RoadEnvironment.DOWN:
                        dToRoadEnd = roadYLen - y;
                        nextCarCell = chckRoad(y + 1, roadYLen, x, road, direction);
                        break;
                    case RoadEnvironment.LEFT:
                        dToRoadEnd = x + 1;
                        nextCarCell = chckRoad(x - 1, -1, y, road, direction);
                        break;
                    case RoadEnvironment.RIGHT:
                        dToRoadEnd = roadXLen - x;
                        nextCarCell = chckRoad((x + 1), roadXLen, y, road, direction);
                        break;
                }

                // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                if (nextCarCell == -1) { // no, the road ahead is clear
                    if (stopLight) {
                        currentV = acceleration(currentV, maxV);
                        currentV = braking(currentV, dToRoadEnd);
                    } else {
                        currentV = acceleration(currentV, maxV);
                    }

                } else { // yes, there's a car in front
                    switch (direction) {
                        case RoadEnvironment.UP:
                        case RoadEnvironment.LEFT:
                            nextCarD = currentCell - nextCarCell;
                            break;
                        case RoadEnvironment.DOWN:
                        case RoadEnvironment.RIGHT:
                            nextCarD = nextCarCell - currentCell;
                            break;
                    }
                    currentV = acceleration(currentV, maxV);
                    currentV = braking(currentV, nextCarD);
                }

                // step 3: speed randomisation (to be add later)
                if (currentV != 0) {
                    currentV = randomization(currentV);
                }

                // step 4: driving
//              // will the car drive past the end of the road? 
                switch (direction) {
                    case RoadEnvironment.UP:
                        newCell = driving(currentCell, currentV, direction);

                        currentCar.setSpeed(currentV);
                        currentCar.setIsChecked(true);
                        currentCar.setDirection(direction);
                        // will the car drive past the end of the road? 
                        if (newCell < 0) { //yes
                            road.clearRoadCell(x, y);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(x, newCell, currentCar);
                        }
                        break;
                    case RoadEnvironment.DOWN:
                        newCell = driving(currentCell, currentV, direction);

                        currentCar.setSpeed(currentV);
                        currentCar.setIsChecked(true);
                        currentCar.setDirection(direction);
                        // will the car drive past the end of the road? 
                        if (newCell >= roadYLen) { //yes
                            road.clearRoadCell(x, y);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(x, newCell, currentCar);
                        }
                        break;
                    case RoadEnvironment.LEFT:
                        newCell = driving(currentCell, currentV, direction);

                        currentCar.setSpeed(currentV);
                        currentCar.setIsChecked(true);
                        currentCar.setDirection(direction);

                        if (newCell < 0) {//yes
                            road.clearRoadCell(x, y);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                    case RoadEnvironment.RIGHT:
                        newCell = driving(currentCell, currentV, direction);

                        currentCar.setSpeed(currentV);
                        currentCar.setIsChecked(true);
                        currentCar.setDirection(direction);

                        if (newCell >= roadXLen) {//yes
                            exitNextRoad(direction, road, x, y, currentCell,
                                    newCell, currentCar, currentV);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                }
            }
        }
    }

    public void exitNextRoad(int direction, RoadInt currentRoad, int x, int y,
            int currentCell, int newCell, Vehicle currentCar, int v) {

        int roadXLen = currentRoad.getRoadXLen();
        int roadYLen = currentRoad.getRoadYLen();
        int cCell = currentCell;
        int nCell = newCell;
        int currentV = v;

        if (currentRoad.getExit(direction) == null) {//does this road exit the network?
            currentRoad.clearRoadCell(x, y);//Y: remove car from network

        } else {
            RoadInt nextRoad = currentRoad.getExit(direction);
            int nextCarCell = chckRoad(0, nextRoad.getRoadXLen(), y, nextRoad, direction);

            if (nextCarCell == -1) {// There isn't a car in this car's path

                switch (direction) {
                    case RoadEnvironment.UP:
                        break;
                    case RoadEnvironment.DOWN:
                        break;
                    case RoadEnvironment.LEFT:
                        break;
                    case RoadEnvironment.RIGHT:
                        if (newCell >= nextRoad.getRoadXLen() + roadXLen) {
                            // add car to next road over
                            currentRoad.clearRoadCell(x, y);
                        } else {//car lands in junction
                            newCell -= roadXLen;
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                }

            } else {//There is a car in this car's path
                int nextCarD = -1;
                switch (direction) {
                    case RoadEnvironment.UP:
                        break;
                    case RoadEnvironment.DOWN:
                        break;
                    case RoadEnvironment.LEFT:
                        break;
                    case RoadEnvironment.RIGHT:
                        nextCarD = (nextCarCell + roadXLen) - cCell;
                        break;
                }

                currentV = braking(currentV, nextCarD);
                newCell = driving(currentCell, currentV);

                currentCar.setSpeed(currentV);

                switch (direction) {
                    case RoadEnvironment.UP:
                        break;
                    case RoadEnvironment.DOWN:
                        break;
                    case RoadEnvironment.LEFT:
                        break;
                    case RoadEnvironment.RIGHT:
                        if (newCell >= roadXLen) {//car will still land in junction
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(newCell - roadXLen, y, currentCar);
                        } else {//car stays on road
                            currentRoad.clearRoadCell(x, y);
                            currentRoad.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                }

            }
        }
    }

    public int chckRoad(int chckStart, int chckEnd, int laneNo, RoadInt road, int direction) {
        int carX = -1;
        switch (direction) {
            case RoadEnvironment.UP:
                for (int i = chckStart; i > chckEnd; i--) {
                    if (road.getRoadCell(laneNo, i) != null) {
                        carX = i;
                        break;
                    }
                }
                break;

            case RoadEnvironment.DOWN:
                for (int i = chckStart; i < chckEnd; i++) {
                    if (road.getRoadCell(laneNo, i) != null) {
                        carX = i;
                        break;
                    }
                }
                break;
            case RoadEnvironment.LEFT:
                for (int i = chckStart; i > chckEnd; i--) {
                    if (road.getRoadCell(i, laneNo) != null) {
                        carX = i;
                        break;
                    }
                }

                break;
            case RoadEnvironment.RIGHT:
                for (int i = chckStart; i < chckEnd; i++) {
                    if (road.getRoadCell(i, laneNo) != null) {
                        carX = i;
                        break;
                    }
                }
                break;
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

    public int driving(int currentX, int currentV, int direction) {
        int newCell = currentX + currentV;
        switch (direction) {
            case RoadEnvironment.UP:
            case RoadEnvironment.LEFT:
                newCell = currentX - currentV;
                break;
            case RoadEnvironment.DOWN:
            case RoadEnvironment.RIGHT:
            default:
                newCell = currentX + currentV;
                break;

        }
        return newCell;
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
