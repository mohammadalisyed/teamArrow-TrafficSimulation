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

//    public void junctStopLightUpdate(Junction junct){
//        
//    }
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

                        switch (direction) {
                            case RoadEnvironment.RIGHT:
                                driveCar(RoadEnvironment.RIGHT, junct, x, y);
                                break;
                            case RoadEnvironment.LEFT:
                                driveCar(RoadEnvironment.LEFT, junct, x, y);
                                break;
                            case RoadEnvironment.UP:
                                driveCar(RoadEnvironment.UP, junct, x, y);
                                break;
                            case RoadEnvironment.DOWN:
                                driveCar(RoadEnvironment.DOWN, junct, x, y);
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
//                            System.out.println("/UsualnextCarD:"+nextCarD);
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
//                            road.clearRoadCell(x, y);
                            exitNextRoad(direction, road, x, y, currentCell,
                                    newCell, currentCar, currentV);
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
//                            road.clearRoadCell(x, y);
                            exitNextRoad(direction, road, x, y, currentCell,
                                    newCell, currentCar, currentV);
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
                            exitNextRoad(direction, road, x, y, currentCell,
                                    newCell, currentCar, currentV);
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
        int nextRoadYCoOrd = 0;
        int nextRoadXCoOrd = 0;

        if (currentRoad.getExit(direction) == null) {//does this road exit the network?
            currentRoad.clearRoadCell(x, y);//Y: remove car from network

        } else {
            RoadInt nextRoad = currentRoad.getExit(direction);
            int nextCarCell = -1;
            nextRoadYCoOrd = y + (currentRoad.getY() - nextRoad.getY());
            nextRoadXCoOrd = x + (currentRoad.getX() - nextRoad.getX());

            switch (direction) {
                case RoadEnvironment.UP:
                    nextCarCell = chckRoad(nextRoad.getRoadYLen() - 1, -1, nextRoadXCoOrd, nextRoad, direction);
                    break;
                case RoadEnvironment.DOWN:
                    nextCarCell = chckRoad(0, nextRoad.getRoadYLen(), nextRoadXCoOrd, nextRoad, direction);
                    break;
                case RoadEnvironment.LEFT:
                    nextCarCell = chckRoad(nextRoad.getRoadXLen() - 1, -1, nextRoadYCoOrd, nextRoad, direction);
                    break;
                case RoadEnvironment.RIGHT:
                    nextCarCell = chckRoad(0, nextRoad.getRoadXLen(), nextRoadYCoOrd, nextRoad, direction);
                    break;
            }

            if (nextCarCell == -1) {// There isn't a car in this car's path

                switch (direction) {
                    case RoadEnvironment.UP:
                        if (newCell < 0 - nextRoad.getRoadYLen()) {
                            // add car to next road over
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
                        if (newCell < 0 - nextRoad.getRoadXLen()) {
                            // add car to next road over
                            currentRoad.clearRoadCell(x, y);
                        } else {
                            newCell += nextRoad.getRoadXLen();
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(newCell, nextRoadYCoOrd, currentCar);
                        }
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
                        nextCarD = cCell + (nextRoad.getRoadYLen() - nextCarCell);
                        break;
                    case RoadEnvironment.DOWN:
                        nextCarD = (nextCarCell + roadYLen) - cCell;
                        break;
                    case RoadEnvironment.LEFT:
                        nextCarD = cCell + (nextRoad.getRoadXLen() - nextCarCell);
                        break;
                    case RoadEnvironment.RIGHT:
                        nextCarD = (nextCarCell + roadXLen) - cCell;
                        break;
                }

                currentV = braking(currentV, nextCarD);
                newCell = driving(currentCell, currentV, direction);

                currentCar.setSpeed(currentV);

                switch (direction) {
                    case RoadEnvironment.UP:
                        if (newCell < 0) {
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(nextRoadXCoOrd, newCell + nextRoad.getRoadYLen(), currentCar);
                        } else {
                            currentRoad.clearRoadCell(x, y);
                            currentRoad.setRoadCell(x, newCell, currentCar);
                        }
                        break;
                    case RoadEnvironment.DOWN:
                        if (newCell >= roadYLen) {//car will still land in junction
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(nextRoadXCoOrd, newCell - roadYLen, currentCar);
                        } else {//car stays on road
                            currentRoad.clearRoadCell(x, y);
                            currentRoad.setRoadCell(x, newCell, currentCar);
                        }
                        break;
                    case RoadEnvironment.LEFT:
                        if (newCell < 0) {
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(newCell + nextRoad.getRoadXLen(), nextRoadYCoOrd, currentCar);
                        } else {
                            currentRoad.clearRoadCell(x, y);
                            currentRoad.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                    case RoadEnvironment.RIGHT:
                        if (newCell >= roadXLen) {//car will still land in junction
                            currentRoad.clearRoadCell(x, y);
                            nextRoad.setRoadCell(newCell - roadXLen, nextRoadYCoOrd, currentCar);
                        } else {//car stays on road
                            currentRoad.clearRoadCell(x, y);
                            currentRoad.setRoadCell(newCell, y, currentCar);
                        }
                        break;
                }

            }
        }
    }

    public int chckRoad(int chckStart, int chckEnd, int laneNo,
            RoadInt road, int direction) {
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

    public int chckRoadForBlock(int chckStart, int chckEnd, int laneNo,
            RoadInt road, int blockDirect) {
        int carX = -1;
        switch (blockDirect) {
            case RoadEnvironment.UP:
                for (int i = chckStart; i > chckEnd; i--) {
                    if (road.getRoadCell(laneNo, i) != null) {
                        int direct = road.getRoadCell(laneNo, i).getDirection();
                        if (direct == blockDirect) {
                            carX = i;
                            break;
                        }
                    }
                }
                break;

            case RoadEnvironment.DOWN:
                for (int i = chckStart; i < chckEnd; i++) {
                    if (road.getRoadCell(laneNo, i) != null) {
                        int direct = road.getRoadCell(laneNo, i).getDirection();
                        if (direct == blockDirect) {
                            carX = i;
                            break;
                        }
                    }
                }
                break;
            case RoadEnvironment.LEFT:
                for (int i = chckStart; i > chckEnd; i--) {
                    if (road.getRoadCell(i, laneNo) != null) {
                        int direct = road.getRoadCell(i, laneNo).getDirection();
                        if (direct == blockDirect) {
                            carX = i;
                            break;
                        }
                    }
                }

                break;
            case RoadEnvironment.RIGHT:
                for (int i = chckStart; i < chckEnd; i++) {
                    if (road.getRoadCell(i, laneNo) != null) {
                        int direct = road.getRoadCell(i, laneNo).getDirection();
                        if (direct == blockDirect) {
                            carX = i;
                            break;
                        }
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
