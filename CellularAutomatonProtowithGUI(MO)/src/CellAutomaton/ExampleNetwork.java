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
public class ExampleNetwork extends RoadNetworkTemplate {

    OneWayRoad northRoadU = new OneWayRoad(3, 50, 50, 0, UP, "North-Road UP");
    OneWayRoad northRoadD = new OneWayRoad(3, 50, 53, 0, DOWN, "North-Road DOWN");

    OneWayRoad eastRoadR = new OneWayRoad(50, 3, 56, 50, RIGHT, "East-Road RIGHT");
    OneWayRoad eastRoadL = new OneWayRoad(50, 3, 56, 53, LEFT, "East-Road LEFT");

    OneWayRoad southRoadU = new OneWayRoad(3, 50, 50, 56, UP, "South-Road UP");
    OneWayRoad southRoadD = new OneWayRoad(3, 50, 53, 56, DOWN, "South-Road DOWN");

    OneWayRoad westRoadR = new OneWayRoad(50, 3, 0, 50, RIGHT, "West-Road RIGHT");
    OneWayRoad westRoadL = new OneWayRoad(50, 3, 0, 53, LEFT, "West-Road LEFT");

    OneWayRoad aNorthUp = new OneWayRoad(3, 25, 106, 25, UP, "A-NORTH UP");
    OneWayRoad aNorthDown = new OneWayRoad(3, 25, 109, 25, DOWN, "A-NORTH DOWN");
    
    OneWayRoad aSouthUp = new OneWayRoad(3, 25, 106, 25, UP, "A-SOUTH UP");
    OneWayRoad aSouthDown = new OneWayRoad(3, 25, 109, 25, DOWN, "A-SOUTH DOWN");

    OneWayRoad bWestLeft = new OneWayRoad(22, 3, 84, 22, LEFT, "B-WEST LEFT");

    OneWayRoad bNorthUp = new OneWayRoad(3, 22, 81, 0, UP, "B-NORTH UP");

    OneWayRoad[] crossroadEntr = new OneWayRoad[4];
    OneWayRoad[] crossroadExit = new OneWayRoad[4];

    OneWayRoad[] junctAEntr = new OneWayRoad[4];
    OneWayRoad[] junctAExit = new OneWayRoad[4];

    OneWayRoad[] junctBEntr = new OneWayRoad[4];
    OneWayRoad[] junctBExit = new OneWayRoad[4];

    OneWayRoad[] junctLWEntr = new OneWayRoad[4];
    OneWayRoad[] junctLWExit = new OneWayRoad[4];

    Junction crossroad, junctA, junctB, junctLW;

    public ExampleNetwork() {
        //set lanes
        northRoadD.setLaneDirect(0, LEFT);
        northRoadD.setLaneDirect(2, RIGHT);

        southRoadU.setLaneDirect(0, LEFT);
        southRoadU.setLaneDirect(2, RIGHT);

        westRoadR.setLaneDirect(0, UP);
        westRoadR.setLaneDirect(2, DOWN);

        eastRoadL.setLaneDirect(0, UP);
        eastRoadL.setLaneDirect(2, DOWN);

        eastRoadR.setLaneDirect(UP);

        aNorthDown.setLaneDirect(LEFT);
        aNorthUp.setLaneDirect(LEFT);

        bWestLeft.setLaneDirect(UP);
        
//        bEastLeft.setLaneDirect(DOWN);

        //set junction exits
        crossroadExit[UP] = northRoadU;
        crossroadExit[DOWN] = southRoadD;
        crossroadExit[LEFT] = westRoadL;
        crossroadExit[RIGHT] = eastRoadR;

        junctAExit[UP] = aNorthUp;
        junctAExit[DOWN] = null;
        junctAExit[LEFT] = eastRoadL;
        junctAExit[RIGHT] = null;

        junctBExit[UP] = null;
        junctBExit[DOWN] = aNorthDown;
        junctBExit[LEFT] = bWestLeft;
        junctBExit[RIGHT] = null;

        junctLWExit[UP] = bNorthUp;
        junctLWExit[DOWN] = null;
        junctLWExit[LEFT] = null;
        junctLWExit[RIGHT] = null;
//        
        //set junction entrances
        crossroadEntr[UP] = southRoadU;
        crossroadEntr[DOWN] = northRoadD;
        crossroadEntr[LEFT] = eastRoadL;
        crossroadEntr[RIGHT] = westRoadR;

        junctAEntr[UP] = null;
        junctAEntr[DOWN] = aNorthDown;
        junctAEntr[LEFT] = null;
        junctAEntr[RIGHT] = eastRoadR;

        junctBEntr[UP] = aNorthUp;
        junctBEntr[DOWN] = null;
        junctBEntr[LEFT] = null;
        junctBEntr[RIGHT] = null;

        junctLWEntr[UP] = null;
        junctLWEntr[DOWN] = null;
        junctLWEntr[LEFT] = bWestLeft;
        junctLWEntr[RIGHT] = null;

//        junctLeftEEntr[UP] = null;
//        junctLeftEEntr[DOWN] = null;
//        junctLeftEEntr[LEFT] = null;
//        junctLeftEEntr[RIGHT] = null;
//        
        //create junctions
        crossroad = new Junction(50, 50, crossroadExit, crossroadEntr, 6, 6, "Crossroad intersection");
        junctA = new Junction(106, 50, junctAExit, junctAEntr, 6, 6, "Junction A");
        junctB = new Junction(106, 22, junctBExit, junctBEntr, 6, 3, "Junction B");
        junctLW = new Junction(81, 22, junctLWExit, junctLWEntr, 3, 3, "Junction LW");

        //populate junction list
        junctArray.add(crossroad);
        junctArray.add(junctA);
        junctArray.add(junctB);
        junctArray.add(junctLW);

        //set road-junction connections
        westRoadR.setExit(crossroad);
        eastRoadL.setExit(crossroad);
        eastRoadR.setExit(junctA);
        northRoadD.setExit(crossroad);
        southRoadU.setExit(crossroad);
        aNorthDown.setExit(junctA);
        aNorthUp.setExit(junctB);
        bWestLeft.setExit(junctLW);
        //bNorthDown
//        
        //set traffic light switches
//        northRoadU.setStopLight(true);
        northRoadD.setStopLight(true);
        southRoadU.setStopLight(true);
        
        aNorthUp.setStopLight(true);
//        southRoadD.setStopLight(true);
//        eastRoadL.setStopLight(true);
//        westRoadR.setStopLight(true);

//        crossroad.setXTravel(true);
        //populate road list
        roadArray.add(northRoadU);
        roadArray.add(northRoadD);
        roadArray.add(eastRoadR);
        roadArray.add(eastRoadL);
        roadArray.add(southRoadU);
        roadArray.add(southRoadD);
        roadArray.add(westRoadR);
        roadArray.add(westRoadL);
        roadArray.add(aNorthUp);
        roadArray.add(aNorthDown);
        roadArray.add(bWestLeft);
        roadArray.add(bNorthUp);
        
        //populate network entrance list
        networkEntr.add(northRoadD);
//        networkEntr.add(eastRoadL);
        networkEntr.add(southRoadU);
        networkEntr.add(westRoadR);

////        networkEntr.add(aNorthDown);

        //populate network exit list
//        networkExit.add(northRoadU);
////        networkExit.add(eastRoadR);
//        networkExit.add(southRoadD);
//        networkExit.add(westRoadL);
    }
}
