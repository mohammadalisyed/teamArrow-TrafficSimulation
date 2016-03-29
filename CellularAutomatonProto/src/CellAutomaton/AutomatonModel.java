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

    public void addCarToRoad(OneWayRoad road) {
        Vehicle car = new Vehicle(1, 1);
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();
        int lane;

        switch (road.getDirection()) {
            case RoadEnvironment.RIGHT:
                lane = new Random().nextInt(roadYLen);
//                car.setLocation(new Point(0, lane));
                car.setDirection(road.getDirection());
//                car.set
//--TESTING TURNING--
                if (lane == 0) {
                    car.setExit(RoadEnvironment.UP);
                } else if (lane < roadYLen - 1) {
                    car.setExit(RoadEnvironment.RIGHT);
                } else {
                    car.setExit(RoadEnvironment.DOWN);
                }

//                car.setExit(RoadEnvironment.UP);
//--TESTING 
//                car.setExit(RoadEnvironment.UP);
//                car.setExit(RoadEnvironment.RIGHT);
                road.setRoadCell(0, lane, car);
                break;

            case RoadEnvironment.LEFT:
                lane = new Random().nextInt(roadYLen);
//                car.setLocation(new Point(roadXLen - 1, lane));
                car.setDirection(road.getDirection());

                if (lane == 0) {
                    car.setExit(RoadEnvironment.UP);
                } else if (lane < roadYLen - 1) {
                    car.setExit(RoadEnvironment.LEFT);
                } else {
                    car.setExit(RoadEnvironment.DOWN);
                }
//                car.setExit(RoadEnvironment.DOWN);

//                car.setExit(RoadEnvironment.UP);
                road.setRoadCell(roadXLen - 1, lane, car);
                break;

            case RoadEnvironment.UP:
                lane = new Random().nextInt(roadXLen);
//                car.setLocation(new Point(lane, roadYLen - 1));
                car.setDirection(road.getDirection());
                car.setExit(RoadEnvironment.UP);
                road.setRoadCell(lane, roadYLen - 1, car);
                break;

            case RoadEnvironment.DOWN:
                lane = new Random().nextInt(roadXLen);
//                car.setLocation(new Point(lane, 0));
                car.setDirection(road.getDirection());
                car.setExit(RoadEnvironment.LEFT);
                road.setRoadCell(lane, 0, car);
                break;
        }
    }
//FOR TESTING --

    public void addCarJ(RoadInt road, Point location, int direct) {
        Vehicle car = new Vehicle(1, 1);
        car.setDirection(direct);
        road.setRoadCell(location.x, location.y, car);
    }
//--------------

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

                    Vehicle currentCar = junct.getRoadCell(x, y);
                    boolean isChecked = currentCar.getIsChecked();

                    if (isChecked == false) {
                        int direction = currentCar.getDirection();
                        int exitDirect = currentCar.getExit();
                        boolean turning = currentCar.getTurning();//
                        Point turningPt = currentCar.getExitTurningPt();

                        if (direction == exitDirect) {
                            RoadInt nextRoad = junct.getExit(direction);

                            switch (direction) {
                                case RoadEnvironment.UP:
                                    if (turning == false) {
                                        driveCar(RoadEnvironment.UP, junct, x, y);
                                    } else if (currentCar.getPreviousDirect() == RoadEnvironment.LEFT) {//RIGHT to UP? 
//                                      is there enough time to pass without interrupting LEFT traffic if it exists?
                                        junctionTurning(direction, junct, x, y, turningPt);
                                    } else if (currentCar.getPreviousDirect() == RoadEnvironment.RIGHT) {
                                        driveCar(RoadEnvironment.UP, junct, x, y);
                                    } else {//LEFT TO UP

                                    }
                                    break;
                                case RoadEnvironment.DOWN:
                                    if (turning == false) {
                                        driveCar(RoadEnvironment.DOWN, junct, x, y);
                                        // if turning == true and direction == exitdirect then car is in turning pt.
                                    } else if (currentCar.getPreviousDirect() == RoadEnvironment.RIGHT) {//RIGHT to DOWN
//                                        is there enough time to pass without interrupting LEFT traffic if it exists?
                                        junctionTurning(direction, junct, x, y, turningPt);
                                    } else if (currentCar.getPreviousDirect() == RoadEnvironment.LEFT) {//LEFT to DOWN
                                        driveCar(RoadEnvironment.DOWN, junct, x, y);
                                    } else {

                                    }
                                    break;
                                case RoadEnvironment.LEFT:
                                    if (turning == false) {
                                        driveCar(RoadEnvironment.LEFT, junct, x, y);
                                    } else {

                                    }
                                    break;
                                case RoadEnvironment.RIGHT:
                                    if (turning == false) {
                                        driveCar(RoadEnvironment.RIGHT, junct, x, y);
                                    } else {
                                    }
                                    break;

                            }
                        } else {
//                            Point turningPt = currentCar.getExitTurningPt();
                            switch (direction) {

                                case RoadEnvironment.UP:
                                case RoadEnvironment.DOWN:
                                    break;
                                case RoadEnvironment.LEFT:
                                case RoadEnvironment.RIGHT:
//                                    turningPt = getTurningPt(direction, exitDirect, y, junct);
                                    driveCarToTurningPt(direction, junct, x, y, turningPt);
                                    break;
                            }

                        }
                    }
                }
            }
        }
        resetCarsChk(junct);
    }

    public void junctionTurning(int direction, RoadInt junct, int x, int y, Point turningPt) {
        switch (direction) {

            case RoadEnvironment.UP:
                boolean rightIncoming = false;
                boolean rightStopped;

                if (junct.getEntr(RoadEnvironment.RIGHT) != null) {//road outputting RIGHT traffic into this junction exists
                    RoadInt rightRoad = junct.getEntr(RoadEnvironment.RIGHT);
                    rightStopped = rightRoad.getStopLight();

                    if (y == (turningPt.y)) {//waiting to cross RIGHT traffic
                        if (rightStopped == false) {
                            rightIncoming = !rightRoad.getPointList().isEmpty();
                        }
                    }
                }
                if (rightIncoming == false) {
                    driveCar(RoadEnvironment.UP, junct, x, y);
                }
                break;

            case RoadEnvironment.DOWN:
                boolean leftIncoming = false;
                boolean leftStopped;

                if (junct.getEntr(RoadEnvironment.LEFT) != null) {//road outputting LEFT traffic into this junction exists
                    RoadInt leftRoad = junct.getEntr(RoadEnvironment.LEFT);
                    leftStopped = leftRoad.getStopLight();

                    if (y == (turningPt.y)) {//waiting to cross LEFT traffic
//                                                System.out.println("/x:"+x+"/y:"+y+"/yB:"+(leftRoad.getY() - junct.getY()));
                        if (leftStopped == false) {
                            leftIncoming = !leftRoad.getPointList().isEmpty();
                        }
                    }
                }
                if (leftIncoming == false) {
                    driveCar(RoadEnvironment.DOWN, junct, x, y);
                }
                break;
            case RoadEnvironment.LEFT:
                break;
            case RoadEnvironment.RIGHT:

                break;
        }
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
                int dToRoadEnd = -1; // distance to the end of the road
                int nextCarCell = -1; // index of next car, -1 : no car in front
                Point nextCarPt = null;
                int nextCarD = -1; // distance to next car, -1 : no car in front
                int newCell; // current car's new cell

                switch (direction) {
                    case RoadEnvironment.UP:
                        dToRoadEnd = y + 1;
                        nextCarPt = chckRoad(y - 1, -1, x, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.y;
                        }
                        break;
                    case RoadEnvironment.DOWN:
                        dToRoadEnd = roadYLen - y;
                        nextCarPt = chckRoad(y + 1, roadYLen, x, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.y;
                        }
                        break;
                    case RoadEnvironment.LEFT:
                        dToRoadEnd = x + 1;
                        nextCarPt = chckRoad(x - 1, -1, y, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.x;
                        }
                        break;
                    case RoadEnvironment.RIGHT:
                        dToRoadEnd = roadXLen - x;
                        nextCarPt = chckRoad((x + 1), roadXLen, y, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.x;
                        }
                        break;
                }

                // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                if (nextCarPt == null) { // no, the road ahead is clear
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
                newCell = driving(currentCell, currentV, direction);

                currentCar.setSpeed(currentV);
                currentCar.setIsChecked(true);

                //              // will the car drive past the end of the road? 
                switch (direction) {
                    case RoadEnvironment.UP:
                        // will the car drive past the end of the road? 
                        if (newCell < 0) { //yes
                            exitNextRoad(road, x, y, currentCell,
                                    newCell, currentCar, currentV);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(x, newCell, currentCar);
                        }
                        break;
                    case RoadEnvironment.DOWN:
                        // will the car drive past the end of the road? 
                        if (newCell >= roadYLen) { //yes
                            exitNextRoad(road, x, y, currentCell,
                                    newCell, currentCar, currentV);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(x, newCell, currentCar);
                        }
                        break;
                    case RoadEnvironment.LEFT:
                        if (newCell < 0) {//yes
                            exitNextRoad(road, x, y, currentCell,
                                    newCell, currentCar, currentV);
                        } else { //no
                            road.clearRoadCell(x, y);
                            road.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                    case RoadEnvironment.RIGHT:
                        if (newCell >= roadXLen) {//yes
                            exitNextRoad(road, x, y, currentCell,
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

    public void driveCarToTurningPt(int direction, RoadInt road, int x, int y, Point turningPt) {
        // the car is in the junction, but hasn't reached the turning point so must drive there
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();

        int maxV = road.getMaxV(direction);
//        boolean stopLight = road.getStopLight();

        if (road.getRoadCell(x, y) != null) {

            int currentCell = -1; //index of current car
            Vehicle currentCar = road.getRoadCell(x, y);
            int exitDirect = currentCar.getExit();
            boolean isChecked = currentCar.getIsChecked();
            int dToTurnPt = -1;

            switch (direction) {
                case RoadEnvironment.UP:
                    currentCell = y;
                    dToTurnPt = y + 1;
                    break;
                case RoadEnvironment.DOWN:
                    currentCell = y;
                    dToTurnPt = turningPt.y;
                    break;
                case RoadEnvironment.LEFT:
                    currentCell = x;
                    dToTurnPt = x + 1;
                    break;
                case RoadEnvironment.RIGHT:
                    currentCell = x;
                    dToTurnPt = turningPt.x;
                    break;
            }

            if (isChecked == false) {

                int currentV = currentCar.getSpeed(); // current car's velocity
                // distance to the end of the road
                int nextCarCell = -1; // index of next car, -1 : no car in front
                Point nextCarPt = null;
                int nextCarD = -1; // distance to next car, -1 : no car in front
                int newCell; // current car's new cell

                switch (direction) {// find next car infront
                    case RoadEnvironment.UP:
                        nextCarPt = chckRoad(y - 1, -1, x, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.y;
                        }
                        break;
                    case RoadEnvironment.DOWN:
                        nextCarPt = chckRoad(y + 1, roadYLen, x, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.y;
                        }
                        break;
                    case RoadEnvironment.LEFT:
                        nextCarPt = chckRoad(x - 1, -1, y, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.x;
                        }
                        break;
                    case RoadEnvironment.RIGHT:
                        nextCarPt = chckRoad((x + 1), roadXLen, y, road, direction);

                        if (nextCarPt != null) {
                            nextCarCell = nextCarPt.x;
                        }
                        break;
                }

                // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                if (nextCarPt == null) { // no, the road ahead is clear
//                    if (stopLight) {
//                        currentV = acceleration(currentV, maxV);
//                        currentV = braking(currentV, dToRoadEnd);
//                    } else {
                    currentV = acceleration(currentV, maxV);
                    currentV = braking(currentV, dToTurnPt);
//                    }

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
                newCell = driving(currentCell, currentV, direction);

                currentCar.setSpeed(currentV);
                currentCar.setIsChecked(true);
//                currentCar.setDirection(direction);
//                currentCar.setExit(direction);

                //              // will the car drive past the end of the road? 
                switch (direction) {
                    case RoadEnvironment.UP:
//                        // will the car drive past the end of the road? 
//                        if (newCell < 0) { //yes
//                            exitNextRoad(road, x, y, currentCell,
//                                    newCell, currentCar, currentV);
//                        } else { //no
//                            road.clearRoadCell(x, y);
//                            road.setRoadCell(x, newCell, currentCar);
//                        }
                        break;
                    case RoadEnvironment.DOWN:
                        // will the car drive past the end of the road? 
//                        if (newCell >= roadYLen) { //yes
//                            exitNextRoad(road, x, y, currentCell,
//                                    newCell, currentCar, currentV);
//                        } else { //no
//                            road.clearRoadCell(x, y);
//                            road.setRoadCell(x, newCell, currentCar);
//                        }
                        break;
                    case RoadEnvironment.LEFT:
//                        System.out.println("x:" + x + "/turningPt.x:" + turningPt.x);
                        moveIntoTurningPt((newCell > turningPt.x),
                                road, road, x, y, newCell, y, currentCar, turningPt);
                        break;
                    case RoadEnvironment.RIGHT:
//                        turningPt = getTurningPt(direction, exitDirect, y, nextRoad);
//                        System.out.println("RIGHT");
                        moveIntoTurningPt((newCell < turningPt.x),
                                road, road, x, y, newCell, y, currentCar, turningPt);
                        break;
                }
            }
        }
    }

    public void moveIntoTurningPt(boolean ifBool, RoadInt currentRoad,
            RoadInt nextRoad, int x, int y, int newX, int newY,
            Vehicle currentCar, Point turningPt) {

        int exitDirect = currentCar.getExit();
        int direction = currentCar.getDirection();

        switch (direction) {
            case RoadEnvironment.UP:

            case RoadEnvironment.DOWN:

                break;
            case RoadEnvironment.LEFT:
            case RoadEnvironment.RIGHT:
                if (ifBool) {// car won't land in turning pt
                    currentRoad.clearRoadCell(x, y);
                    nextRoad.setRoadCell(newX, newY, currentCar);
                } else {
                    currentCar.setTurning(true);//                     

                    currentCar.setDirection(exitDirect);
                    currentRoad.clearRoadCell(x, y);
                    nextRoad.setRoadCell(turningPt.x, newY, currentCar);
                }
                break;

        }

    }

    public void crossWithNoBlockAhead(RoadInt currentRoad, RoadInt nextRoad, int direction,
            int x, int y, int currentCell, int newCell, Vehicle currentCar, int v) {

        int exitDirect = currentCar.getExit();
        int roadXLen = currentRoad.getRoadXLen();// Width of the current road
        int roadYLen = currentRoad.getRoadYLen();// Height of the current road
        int nextRoadYCoOrd = y + (currentRoad.getY() - nextRoad.getY());
        int nextRoadXCoOrd = x + (currentRoad.getX() - nextRoad.getX());
        Point turningPt;
        int currentV = v;

        switch (direction) {//move car
            case RoadEnvironment.UP:
                if (newCell < 0 - nextRoad.getRoadYLen()) {
                    currentRoad.clearRoadCell(x, y);
                } else {
                    newCell += nextRoad.getRoadYLen();
                    currentRoad.clearRoadCell(x, y);
                    nextRoad.setRoadCell(nextRoadXCoOrd, newCell, currentCar);
                }

                break;
            case RoadEnvironment.DOWN:
                if (newCell >= nextRoad.getRoadYLen() + roadYLen) {
                    // add car to next road over
                    currentRoad.clearRoadCell(x, y);
                } else {//car lands in junction
                    newCell -= roadYLen;
                    currentRoad.clearRoadCell(x, y);
                    nextRoad.setRoadCell(nextRoadXCoOrd, newCell, currentCar);
                }
                break;
            case RoadEnvironment.LEFT:
//                
                if (direction == exitDirect) {// not making a turn
                    if (newCell < 0 - nextRoad.getRoadXLen()) {
                        // add car to next road over
                        currentRoad.clearRoadCell(x, y);
                    } else {
                        newCell += nextRoad.getRoadXLen();
                        currentRoad.clearRoadCell(x, y);

                        nextRoad.setRoadCell(newCell, nextRoadYCoOrd, currentCar);
                    }
                } else {// turning 
                    turningPt = getTurningPt(direction, exitDirect, y, nextRoad);

                    int dToTurnPt = turningPt.x + currentRoad.getRoadXLen();

                    currentV = braking(currentV, dToTurnPt);
                    newCell = driving(currentCell, currentV, direction);
                    newCell += nextRoad.getRoadXLen();

                    currentCar.setSpeed(currentV);
                    currentCar.setTurning(true);
                    currentCar.setExitTuringPt(turningPt);

                    moveIntoTurningPt((newCell > turningPt.x),
                            currentRoad, nextRoad, x, y, newCell, nextRoadYCoOrd, currentCar, turningPt);
                }
//                
                break;
            case RoadEnvironment.RIGHT:
                if (direction == exitDirect) {// not making a turn

                    if (newCell >= nextRoad.getRoadXLen() + roadXLen) {
                        // TO ADD: add car to next road over because crossed junction
                        currentRoad.clearRoadCell(x, y);
                    } else {//car lands in junction
                        newCell -= roadXLen;
                        currentRoad.clearRoadCell(x, y);

                        nextRoad.setRoadCell(newCell, nextRoadYCoOrd, currentCar);
                    }
                } else {// turning 
                    turningPt = getTurningPt(direction, exitDirect, y, nextRoad);

                    int dToTurnPt = turningPt.x + currentRoad.getRoadXLen();

                    currentV = braking(currentV, dToTurnPt);
                    newCell = driving(currentCell, currentV, direction);
                    newCell -= roadXLen;

                    currentCar.setSpeed(currentV);
                    currentCar.setTurning(true);
                    currentCar.setExitTuringPt(turningPt);

                    moveIntoTurningPt((newCell < turningPt.x),
                            currentRoad, nextRoad, x, y, newCell, nextRoadYCoOrd, currentCar, turningPt);
                }
                break;
        }
    }

    public void crossWithBlockAhead(RoadInt currentRoad, RoadInt nextRoad, int direction,
            int x, int y, int currentCell, int newCell, Vehicle currentCar,
            int v, int nextCarCell) {

        int nextCarD = -1;

        int exitDirect = currentCar.getExit();
        int roadXLen = currentRoad.getRoadXLen();// Width of the current road
        int roadYLen = currentRoad.getRoadYLen();// Height of the current road
        int nextRoadYCoOrd = y + (currentRoad.getY() - nextRoad.getY());
        int nextRoadXCoOrd = x + (currentRoad.getX() - nextRoad.getX());
        Point turningPt;
        int currentV = v;

        switch (direction) {
            case RoadEnvironment.UP:
                nextCarD = currentCell + (nextRoad.getRoadYLen() - nextCarCell);
                break;
            case RoadEnvironment.DOWN:
                nextCarD = (nextCarCell + roadYLen) - currentCell;
                break;
            case RoadEnvironment.LEFT:
                nextCarD = currentCell + (nextRoad.getRoadXLen() - nextCarCell);
                break;
            case RoadEnvironment.RIGHT:
                nextCarD = (nextCarCell + roadXLen) - currentCell;
                break;
        }

//                    currentV = acceleration(currentV, currentRoad.getMaxV(direction));
        currentV = braking(currentV, nextCarD);
        newCell = driving(currentCell, currentV, direction);

        currentCar.setSpeed(currentV);

        switch (direction) {//move car
            case RoadEnvironment.UP:
                if (newCell < 0) {//car will still land in junction
                    newCell += nextRoad.getRoadYLen();

                    currentRoad.clearRoadCell(x, y);
                    nextRoad.setRoadCell(x, newCell, currentCar);
                } else {//car stays on road
                    currentRoad.clearRoadCell(x, y);
                    currentRoad.setRoadCell(x, newCell, currentCar);
                }
                break;
            case RoadEnvironment.DOWN:
                if (newCell >= roadYLen) {//car will still land in junction
                    newCell -= roadYLen;

                    currentRoad.clearRoadCell(x, y);
                    nextRoad.setRoadCell(nextRoadXCoOrd, newCell, currentCar);
                } else {//car stays on road
                    currentRoad.clearRoadCell(x, y);
                    currentRoad.setRoadCell(x, newCell, currentCar);
                }
                break;
            case RoadEnvironment.LEFT:
                if (newCell < 0) {//car will still land in junction
                    newCell += nextRoad.getRoadXLen();

                    if (direction == exitDirect) {
//                        currentCar.setTurning(false);
                        currentRoad.clearRoadCell(x, y);
                        nextRoad.setRoadCell(newCell, nextRoadYCoOrd, currentCar);
                    } else {
                        turningPt = getTurningPt(direction, exitDirect, y, nextRoad);
                        currentCar.setTurning(true);
                        currentCar.setExitTuringPt(turningPt);

                        moveIntoTurningPt((newCell > turningPt.x),
                                currentRoad, nextRoad, x, y, newCell, nextRoadYCoOrd, currentCar, turningPt);
                    }
//                    currentRoad.clearRoadCell(x, y);
//                    nextRoad.setRoadCell(newCell, nextRoadYCoOrd, currentCar);
                } else {//car stays on road
                    currentRoad.clearRoadCell(x, y);
                    currentRoad.setRoadCell(newCell, y, currentCar);
                }
                break;
            case RoadEnvironment.RIGHT:

                if (newCell >= roadXLen) {//car will still land in junction
                    newCell -= roadXLen;

                    if (direction == exitDirect) {
//                        currentCar.setTurning(false);
                        currentRoad.clearRoadCell(x, y);
                        nextRoad.setRoadCell(newCell, nextRoadYCoOrd, currentCar);
                    } else {
                        turningPt = getTurningPt(direction, exitDirect, y, nextRoad);
                        currentCar.setTurning(true);
                        currentCar.setExitTuringPt(turningPt);

                        moveIntoTurningPt((newCell < turningPt.x),
                                currentRoad, nextRoad, x, y, newCell, nextRoadYCoOrd, currentCar, turningPt);
                    }
                } else {//car stays on road
                    currentRoad.clearRoadCell(x, y);
                    currentRoad.setRoadCell(newCell, y, currentCar);
                }

                break;
        }
    }

    public void travesalBlocked(RoadInt currentRoad, int direction, int x,
            int y, int currentCell, int newCell, Vehicle currentCar,
            int v) {

        int roadXLen = currentRoad.getRoadXLen();// Width of the current road
        int roadYLen = currentRoad.getRoadYLen();// Height of the current road
        int currentV = v;

        int dToRoadEnd = -1;
        switch (direction) {
            case RoadEnvironment.UP:
                dToRoadEnd = y + 1;
                break;
            case RoadEnvironment.DOWN:
                dToRoadEnd = roadYLen - y;
                break;
            case RoadEnvironment.LEFT:
                dToRoadEnd = x + 1;
                break;
            case RoadEnvironment.RIGHT:
                dToRoadEnd = roadXLen - x;
                break;
        }

//                currentV = acceleration(currentV, currentRoad.getMaxV(direction));
        currentV = braking(currentV, dToRoadEnd);
        newCell = driving(currentCell, currentV, direction);

        currentCar.setSpeed(currentV);

        switch (direction) {
            case RoadEnvironment.UP:
            case RoadEnvironment.DOWN:
//                        System.out.println(" /:newY:" + newCell + " /" + direction);
                currentRoad.clearRoadCell(x, y);
                currentRoad.setRoadCell(x, newCell, currentCar);
                break;
            case RoadEnvironment.LEFT:
            case RoadEnvironment.RIGHT:
//                        System.out.println(" /:newX:" + newCell + " /" + direction);
                currentRoad.clearRoadCell(x, y);
                currentRoad.setRoadCell(newCell, y, currentCar);
                break;
        }
    }

    public void exitNextRoad(RoadInt currentRoad, int x, int y,
            int currentCell, int newCell, Vehicle currentCar, int v) {
// the car has crossed the road
//        int roadXLen = currentRoad.getRoadXLen();// Width of the current road
//        int roadYLen = currentRoad.getRoadYLen();// Height of the current road
        int currentV = v;// Vehicle's current velocity
        int nextRoadYCoOrd; //relative y co-ordinates of the next road, for when two roads have different roadYLens 
        int nextRoadXCoOrd;//relative x co-ordinates of the next road, for when two roads have different roadXLens 
        int direction = currentCar.getDirection();

        if (currentRoad.getExit(direction) == null) {//does this road exit the network?
            currentRoad.clearRoadCell(x, y);//Y: remove car from network

        } else {
            RoadInt nextRoad = currentRoad.getExit(direction);
            int nextCarCell = -1;
            Point nextCarPt = null;

            nextRoadYCoOrd = y + (currentRoad.getY() - nextRoad.getY());
            nextRoadXCoOrd = x + (currentRoad.getX() - nextRoad.getX());

            // find next car infront
            switch (direction) {
                case RoadEnvironment.UP:
                    nextCarPt = chckRoad(nextRoad.getRoadYLen() - 1, -1, nextRoadXCoOrd, nextRoad, direction);

                    if (nextCarPt != null) {
                        nextCarCell = nextCarPt.y;
                    }
                    break;
                case RoadEnvironment.DOWN:
                    nextCarPt = chckRoad(0, nextRoad.getRoadYLen(), nextRoadXCoOrd, nextRoad, direction);

                    if (nextCarPt != null) {
                        nextCarCell = nextCarPt.y;
                    }
                    break;
                case RoadEnvironment.LEFT:
                    nextCarPt = chckRoad(nextRoad.getRoadXLen() - 1, -1, nextRoadYCoOrd, nextRoad, direction);

                    if (nextCarPt != null) {
                        nextCarCell = nextCarPt.x;
                    }
                    break;
                case RoadEnvironment.RIGHT:
                    nextCarPt = chckRoad(0, nextRoad.getRoadXLen(), nextRoadYCoOrd, nextRoad, direction);

                    if (nextCarPt != null) {
                        nextCarCell = nextCarPt.x;
                    }
                    break;
            }

            boolean clearJunction = chckTravesalClearance(nextRoad, currentRoad, direction);
//            clearJunction = true;
            int exitDirect = currentCar.getExit();

            if (direction == exitDirect) {// not making a turn
                if (clearJunction) {// junction is clear for travesal
                    if (nextCarPt == null) {// There isn't a car in this car's path

                        crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                currentCell, newCell, currentCar, currentV);

                        currentCar.setTurning(false);
                    } else {// car in the way

                        crossWithBlockAhead(currentRoad, nextRoad, direction,
                                x, y, currentCell, newCell, currentCar,
                                currentV, nextCarCell);

                        currentCar.setTurning(false);
                    }
                } else {// junction not clear
                    travesalBlocked(currentRoad, direction, x, y, currentCell, newCell, currentCar,
                            currentV);
                }
            } else {// turning
                //turning across a road with moving traffic?
                Point turningPt;

                switch (direction) {
                    case RoadEnvironment.UP:
                    case RoadEnvironment.DOWN:
                        turningPt = getTurningPt(direction, exitDirect, x, nextRoad);
                        System.out.println(turningPt);
                        break;
                    case RoadEnvironment.LEFT:
                        switch (exitDirect) {
                            case RoadEnvironment.UP:// cars turning UP from RIGHT may have to cross moving traffic
                                if (nextRoad.getEntr(RoadEnvironment.DOWN) != null) {//UP turning traffic crosses an DOWN road
                                    //may need to wait for the remenants of previous traffic to pass before moving

                                    turningPt = getTurningPt(direction, exitDirect, y, nextRoad);
                                    Vehicle turningCell = nextRoad.getRoadCell(turningPt.x, turningPt.y);
                                    boolean crossIntoJunct = ((clearJunction) && (turningCell == null) && (nextCarPt == null));

                                    if (crossIntoJunct) {
                                        crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                                currentCell, newCell, currentCar, currentV);
                                    } else {
                                        travesalBlocked(currentRoad, direction,
                                                x, y, currentCell, newCell, currentCar, currentV);
                                    }

                                } else//DOWN turning traffic does not crosses an UP road (CORNER/T-JUNCT)
                                //doesn't need to wait for the remenants of previous traffic to pass before moving
                                {
                                    if (nextCarPt == null) {// There isn't a car in this car's path
                                        crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                                currentCell, newCell, currentCar, currentV);
                                    } else {// car in the way
                                        crossWithBlockAhead(currentRoad, nextRoad, direction,
                                                x, y, currentCell, newCell, currentCar,
                                                currentV, nextCarCell);
                                    }
                                }
                                break;
                            case RoadEnvironment.DOWN:// cars turning DOWN from LEFT never cross moving traffic on a left-hand side traffic netowrk
                                if (nextCarPt == null) {// There isn't a car in this car's path
                                    crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                            currentCell, newCell, currentCar, currentV);
                                } else {// car in the way
                                    crossWithBlockAhead(currentRoad, nextRoad, direction,
                                            x, y, currentCell, newCell, currentCar,
                                            currentV, nextCarCell);
                                }
                                break;
                        }
                        break;
                    case RoadEnvironment.RIGHT:
                        switch (exitDirect) {
                            case RoadEnvironment.UP:// cars turning UP from RIGHT never cross moving traffic on a left-hand side traffic netowrk
                                if (nextCarPt == null) {// There isn't a car in this car's path
                                    crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                            currentCell, newCell, currentCar, currentV);
                                } else {// car in the way
                                    crossWithBlockAhead(currentRoad, nextRoad, direction,
                                            x, y, currentCell, newCell, currentCar,
                                            currentV, nextCarCell);
                                }
//                                currentCar.setTurning(false);
                                break;
                            case RoadEnvironment.DOWN:// cars turning DOWN from RIGHT may have to cross moving traffic
                                if (nextRoad.getEntr(RoadEnvironment.UP) != null) {//DOWN turning traffic crosses an UP road
                                    //may need to wait for the remenants of previous traffic to pass before moving

                                    turningPt = getTurningPt(direction, exitDirect, y, nextRoad);
                                    Vehicle turningCell = nextRoad.getRoadCell(turningPt.x, turningPt.y);
                                    boolean crossIntoJunct = ((clearJunction) && (turningCell == null) && (nextCarPt == null));

                                    if (crossIntoJunct) {
                                        crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                                currentCell, newCell, currentCar, currentV);
                                    } else {
                                        travesalBlocked(currentRoad, direction,
                                                x, y, currentCell, newCell, currentCar, currentV);
                                    }

                                } else//DOWN turning traffic does not crosses an UP road (CORNER/T-JUNCT)
                                //doesn't need to wait for the remenants of previous traffic to pass before moving
                                {
                                    if (nextCarPt == null) {// There isn't a car in this car's path
                                        crossWithNoBlockAhead(currentRoad, nextRoad, direction, x, y,
                                                currentCell, newCell, currentCar, currentV);
                                    } else {// car in the way
                                        crossWithBlockAhead(currentRoad, nextRoad, direction,
                                                x, y, currentCell, newCell, currentCar,
                                                currentV, nextCarCell);
                                    }
                                }
                                break;

                        }
                        break;
                }

                //if (){
                //UP: turning right
                //DOWN: turning left
                //LEFT: turning up
                //RIGHT: turning down
                //} else {}
                //y//n
                currentCar.setPreviousDirect(direction);
            }
        }
    }

    public Point getTurningPt(int currentDirect, int exitDirect,
            int currentLane, RoadInt junct) {
//            Junction junct, OneWayRoad road, int currentLane) {

        Point turningPt = null;

        RoadInt exitRoad = junct.getExit(exitDirect);
        RoadInt entrRoad = junct.getEntr(currentDirect);
//        int exitRoadXLen = exitRoad.getRoadXLen();
//        int exitRoadYLen = exitRoad.getRoadYLen();
        int exitXCoOrd;
        int exitYCoOrd;

        switch (currentDirect) {
            case RoadEnvironment.UP:
                break;
            case RoadEnvironment.DOWN:
//                exitXCoOrd = 0;
//                exitYCoOrd = currentLane;
                
                exitYCoOrd = 0;
                exitXCoOrd = currentLane + (entrRoad.getX() - junct.getX());

                switch (exitDirect) {
                    case RoadEnvironment.LEFT:
                        if (currentLane < exitRoad.getRoadYLen()) {//current road and exit road have the same no of lanes
                            exitYCoOrd = exitXCoOrd;
                        } else {//current road and exit road have a different no of lanes
                            exitYCoOrd = exitRoad.getRoadYLen() - 1;
                        }
                        turningPt = new Point(exitYCoOrd, exitXCoOrd);
                        break;
                    case RoadEnvironment.RIGHT:
                        if (currentLane < exitRoad.getRoadYLen()) {//current road and exit road have the same no of lanes
                            exitYCoOrd = (exitRoad.getRoadYLen() - currentLane - 1);
                        } else {//current road and exit road have a different no of lanes
                            exitYCoOrd = 0;
                        }
                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
                        break;
                }
//                switch (exitDirect) {
//                    case RoadEnvironment.LEFT:
//                        if (currentLane < exitRoad.getRoadXLen()) {//current road and exit road have the same no of lanes
//                            exitXCoOrd = currentLane;
//                        } else {//current road and exit road have a different no of lanes
////                            System.out.println("x");
//                            exitXCoOrd = exitRoad.getRoadXLen() - 1;
//                        }
//                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
//                        break;
//                    case RoadEnvironment.RIGHT:
//                        int xBound = junct.getRoadXLen() - exitRoad.getRoadXLen();
////                        System.out.print("/xBound:" + xBound);
//                        // need if != null for junct.getEntr
//                        if (currentLane < exitRoad.getRoadXLen()) {//current road and exit road have the same no of lanes
//                            exitXCoOrd = junct.getEntr(RoadEnvironment.UP).getRoadXLen() + (exitRoad.getRoadXLen() - currentLane - 1);
//                        } else {//current road and exit road have a different no of lanes
//                            exitXCoOrd = xBound;
////                            System.out.println("x");
//                        }
//                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
//                        break;
//                }
                break;
//LEFT-
            case RoadEnvironment.LEFT:
                exitXCoOrd = 0;
                exitYCoOrd = currentLane + (entrRoad.getY() - junct.getY());

                switch (exitDirect) {
                    case RoadEnvironment.DOWN:
                        if (currentLane < exitRoad.getRoadXLen()) {//current road and exit road have the same no of lanes
                            exitXCoOrd = exitYCoOrd;
                        } else {//current road and exit road have a different no of lanes
                            exitXCoOrd = exitRoad.getRoadXLen() - 1;
                        }
                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
                        break;
                    case RoadEnvironment.UP:
                        if (currentLane < exitRoad.getRoadXLen()) {//current road and exit road have the same no of lanes
                            exitXCoOrd = (exitRoad.getRoadXLen() - currentLane - 1);
                        } else {//current road and exit road have a different no of lanes
                            exitXCoOrd = 0;
                        }
                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
                        break;
                }
                break;
//RIGHT-
            case RoadEnvironment.RIGHT:
                exitXCoOrd = 0;
                exitYCoOrd = currentLane;

                switch (exitDirect) {
                    case RoadEnvironment.UP:
                        if (currentLane < exitRoad.getRoadXLen()) {//current road and exit road have the same no of lanes
                            exitXCoOrd = currentLane;
                        } else {//current road and exit road have a different no of lanes
//                            System.out.println("x");
                            exitXCoOrd = exitRoad.getRoadXLen() - 1;
                        }
                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
                        break;
                    case RoadEnvironment.DOWN:
                        int xBound = junct.getRoadXLen() - exitRoad.getRoadXLen();
//                        System.out.print("/xBound:" + xBound);
                        // need if != null for junct.getEntr
                        if (currentLane < exitRoad.getRoadXLen()) {//current road and exit road have the same no of lanes
                            exitXCoOrd = junct.getEntr(RoadEnvironment.UP).getRoadXLen() + (exitRoad.getRoadXLen() - currentLane - 1);
                        } else {//current road and exit road have a different no of lanes
                            exitXCoOrd = xBound;
//                            System.out.println("x");
                        }
                        turningPt = new Point(exitXCoOrd, exitYCoOrd);
                        break;
                }
                break;
        }

        return turningPt;
    }

    public boolean chckTravesalClearance(RoadInt nextRoad, RoadInt currentRoad, int direction) {

        boolean clearance = true;

        //
        switch (direction) {
            case RoadEnvironment.UP:
                for (int x = nextRoad.getRoadXLen() - 1; x > -1; x--) {
                    for (int y = nextRoad.getRoadYLen() - 1; y > -1; y--) {
                        if (nextRoad.getRoadCell(x, y) != null) {
                            int nextDirect = nextRoad.getRoadCell(x, y).getDirection();
                            boolean turning = nextRoad.getRoadCell(x, y).getTurning();
                            if (turning == false) {
                                if (nextDirect == RoadEnvironment.LEFT) {
                                    clearance = false;
                                }

                                if (x < currentRoad.getRoadXLen()) {
                                    if (nextDirect == RoadEnvironment.RIGHT) {
                                        clearance = false;
                                    }
                                }
                            }
                        }
                    }
                }

                break;

            case RoadEnvironment.DOWN:
                for (int x = 0; x < nextRoad.getRoadXLen(); x++) {
                    for (int y = 0; y < nextRoad.getRoadYLen(); y++) {
                        if (nextRoad.getRoadCell(x, y) != null) {
                            int nextDirect = nextRoad.getRoadCell(x, y).getDirection();
                            int nextRoadXCoOrd = nextRoad.getRoadXLen() - currentRoad.getRoadXLen();
                            boolean turning = nextRoad.getRoadCell(x, y).getTurning();
                            if (turning == false) {
                                if (nextDirect == RoadEnvironment.RIGHT) {
                                    clearance = false;
                                }

                                if (x > nextRoadXCoOrd) {
                                    if (nextDirect == RoadEnvironment.LEFT) {
                                        clearance = false;
                                    }
                                }
                            }
                        }
                    }
                }

                break;

            case RoadEnvironment.LEFT:
                for (int y = nextRoad.getRoadYLen() - 1; y > -1; y--) {
                    for (int x = nextRoad.getRoadXLen() - 1; x > -1; x--) {
                        if (nextRoad.getRoadCell(x, y) != null) {
                            int nextDirect = nextRoad.getRoadCell(x, y).getDirection();
                            int nextRoadYCoOrd = nextRoad.getRoadYLen() - currentRoad.getRoadYLen();

                            boolean turning = nextRoad.getRoadCell(x, y).getTurning();

                            if (turning == false) {
                                if (nextDirect == RoadEnvironment.DOWN) {// && turning == false) {
                                    clearance = false;
                                }
                                if (y >= nextRoadYCoOrd) {
//                                System.out.println(nextRoadYCoOrd);
                                    if (nextDirect == RoadEnvironment.UP) {// && turning == false) {
                                        clearance = false;
                                    }
                                }
                            }
                        }
                    }
                }

                break;

            case RoadEnvironment.RIGHT:
                for (int y = 0; y < nextRoad.getRoadYLen(); y++) {
                    for (int x = 0; x < nextRoad.getRoadXLen(); x++) {
                        if (nextRoad.getRoadCell(x, y) != null) {
                            int nextDirect = nextRoad.getRoadCell(x, y).getDirection();
//                            Point turningPt = getTurningPt(RoadEnvironment.RIGHT,RoadEnvironment.DOWN,y,nextRoad);
//                            System.out.println(turningPt);
                            boolean turning = nextRoad.getRoadCell(x, y).getTurning();
                            if (turning == false) {
                                //  up: all cells
                                if (nextDirect == RoadEnvironment.UP) {// && turning == false) {
                                    clearance = false;
                                }
                                // down: from y = 0 to y = end of this road 
                                // the point isn't the turning point
                                if (y < currentRoad.getRoadYLen()) {// && turning == false) {
                                    if (nextDirect == RoadEnvironment.DOWN) {// && turning == false) {
                                        clearance = false;
                                    }
                                }
                            }
                        }
                    }
                }

                break;
        }
        return clearance;

    }

    public Point chckRoad(int chckStart, int chckEnd, int laneNo,
            RoadInt road, int direction) {
//        int carX = -1;
        Point carPt = null;
        switch (direction) {
            case RoadEnvironment.UP:
                for (int i = chckStart; i > chckEnd; i--) {
                    if (road.getRoadCell(laneNo, i) != null) {
//                        carX = i;
                        carPt = new Point(laneNo, i);
                        break;
                    }
                }
                break;

            case RoadEnvironment.DOWN:
                for (int i = chckStart; i < chckEnd; i++) {
                    if (road.getRoadCell(laneNo, i) != null) {
//                        carX = i;
                        carPt = new Point(laneNo, i);
                        break;
                    }
                }
                break;
            case RoadEnvironment.LEFT:
                for (int i = chckStart; i > chckEnd; i--) {
                    if (road.getRoadCell(i, laneNo) != null) {
//                        carX = i;
                        carPt = new Point(i, laneNo);
                        break;
                    }
                }

                break;
            case RoadEnvironment.RIGHT:
                for (int i = chckStart; i < chckEnd; i++) {
                    if (road.getRoadCell(i, laneNo) != null) {
//                        carX = i;
                        carPt = new Point(i, laneNo);
                        break;
                    }
                }
                break;
        }
//        return carX;
        return carPt;
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
