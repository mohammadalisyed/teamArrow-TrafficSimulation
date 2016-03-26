/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class CrossroadNetwork extends RoadNetworkTemplate {

    OneWayRoad northRoadU = new OneWayRoad(3, 50, 50, 0, UP);
    OneWayRoad northRoadD = new OneWayRoad(3, 50, 53, 0, DOWN);

    OneWayRoad eastRoadR = new OneWayRoad(50, 3, 56, 50, RIGHT);
    OneWayRoad eastRoadL = new OneWayRoad(50, 3, 56, 53, LEFT);

    OneWayRoad southRoadU = new OneWayRoad(3, 50, 50, 56, UP);
    OneWayRoad southRoadD = new OneWayRoad(3, 50, 53, 56, DOWN);

    OneWayRoad westRoadR = new OneWayRoad(50, 3, 0, 50, RIGHT);
    OneWayRoad westRoadL = new OneWayRoad(50, 3, 0, 53, LEFT);

    OneWayRoad[] crossroadEntr = new OneWayRoad[4];
    OneWayRoad[] crossroadExit = new OneWayRoad[4];

    Junction crossroad;

    public CrossroadNetwork() {       
        //set junction exits
        crossroadExit[UP] = northRoadU;
        crossroadExit[DOWN] = southRoadD;
        crossroadExit[LEFT] = westRoadL;
        crossroadExit[RIGHT] = eastRoadR;

        //set junction entrances
        crossroadEntr[UP] = southRoadU;
        crossroadEntr[DOWN] = northRoadD;
        crossroadEntr[LEFT] = eastRoadL;
        crossroadEntr[RIGHT] = westRoadR;
        
        //create junctions
        crossroad = new Junction(50, 50, crossroadExit, crossroadEntr, 6, 6);

        //populate junction list
        junctArray.add(crossroad);

        //set road-junction connections
        westRoadR.setExit(crossroad);
        eastRoadL.setExit(crossroad);
        northRoadD.setExit(crossroad);
        southRoadU.setExit(crossroad);

        //set traffic light switches
//        northRoadU.setStopLight(true);
//        northRoadD.setStopLight(true);
//        southRoadU.setStopLight(true);
//        southRoadD.setStopLight(true);
        eastRoadL.setStopLight(true);
        westRoadR.setStopLight(true);

        crossroad.setXTravel(true);

        //populate road list
        roadArray.add(northRoadU);
        roadArray.add(northRoadD);
        roadArray.add(eastRoadR);
        roadArray.add(eastRoadL);
        roadArray.add(southRoadU);
        roadArray.add(southRoadD);
        roadArray.add(westRoadR);
        roadArray.add(westRoadL);

        //populate network entrance list
        networkEntr.add(northRoadD);
        networkEntr.add(eastRoadL);
        networkEntr.add(southRoadU);
        networkEntr.add(westRoadR);

        //populate network exit list
        networkExit.add(northRoadU);
        networkExit.add(eastRoadR);
        networkExit.add(southRoadD);
        networkExit.add(westRoadL);
    }
}
