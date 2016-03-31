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

    OneWayRoad northRoadU = new OneWayRoad(3, 50, 50, 0, UP);
    OneWayRoad northRoadD = new OneWayRoad(3, 50, 53, 0, DOWN);

    OneWayRoad eastRoadR = new OneWayRoad(50, 3, 56, 50, RIGHT);
    OneWayRoad eastRoadL = new OneWayRoad(50, 3, 56, 53, LEFT);

    OneWayRoad southRoadU = new OneWayRoad(3, 50, 50, 56, UP);
    OneWayRoad southRoadD = new OneWayRoad(3, 50, 53, 56, DOWN);

    OneWayRoad westRoadR = new OneWayRoad(50, 3, 0, 50, RIGHT);
    OneWayRoad westRoadL = new OneWayRoad(50, 3, 0, 53, LEFT);
    
    OneWayRoad aNorthUp = new OneWayRoad(3, 25, 106, 25, UP);
    OneWayRoad aNorthDown = new OneWayRoad(3, 25, 109, 25, DOWN);

    OneWayRoad[] crossroadEntr = new OneWayRoad[4];
    OneWayRoad[] crossroadExit = new OneWayRoad[4];
    
    OneWayRoad[] junctAEntr = new OneWayRoad[4];
    OneWayRoad[] junctAExit = new OneWayRoad[4];

    Junction crossroad;
    Junction junctA;
    
    

    public ExampleNetwork() {
        //set lanes
        northRoadD.setLaneDirect(0, LEFT);
//        northRoadD.setLaneDirect(1, DOWN);
        northRoadD.setLaneDirect(2, RIGHT);

        southRoadU.setLaneDirect(0, LEFT);
//        southRoadU.setLaneDirect(1, DOWN);
        southRoadU.setLaneDirect(2, RIGHT);
        
        westRoadR.setLaneDirect(0,UP);
//        westRoadR.setLaneDirect(1,RIGHT);
        westRoadR.setLaneDirect(2,DOWN);
        
        eastRoadL.setLaneDirect(0,UP);
//        eastRoadL.setLaneDirect(1,LEFT);
        eastRoadL.setLaneDirect(2,DOWN); 
        
        aNorthDown.setLaneDirect(LEFT);
        
        //set junction exits
        crossroadExit[UP] = northRoadU;
        crossroadExit[DOWN] = southRoadD;
        crossroadExit[LEFT] = westRoadL;
        crossroadExit[RIGHT] = eastRoadR;
        
        junctAExit[UP] = null;
        junctAExit[DOWN] = null;
        junctAExit[LEFT] = eastRoadL;
        junctAExit[RIGHT] = null;
        
        //set junction entrances
        crossroadEntr[UP] = southRoadU;
        crossroadEntr[DOWN] = northRoadD;
        crossroadEntr[LEFT] = eastRoadL;
        crossroadEntr[RIGHT] = westRoadR;
        
        junctAEntr[UP] = null;
        junctAEntr[DOWN] = aNorthDown;
        junctAEntr[LEFT] = null;
        junctAEntr[RIGHT] = null;

        //create junctions
        crossroad = new Junction(50, 50, crossroadExit, crossroadEntr, 6, 6, "Crossroad intersection");
        junctA = new Junction(106,50, junctAExit, junctAEntr,6,6, "Junction A");

        //populate junction list
        junctArray.add(crossroad);
        junctArray.add(junctA);
        

        //set road-junction connections
        westRoadR.setExit(crossroad);
        eastRoadL.setExit(crossroad);
        northRoadD.setExit(crossroad);
        southRoadU.setExit(crossroad);
        aNorthDown.setExit(junctA);

        //set traffic light switches
//        northRoadU.setStopLight(true);
        northRoadD.setStopLight(true);
        southRoadU.setStopLight(true);
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

        //populate network entrance list
        networkEntr.add(northRoadD);
//        networkEntr.add(eastRoadL);
        networkEntr.add(southRoadU);
        networkEntr.add(westRoadR);
        networkEntr.add(aNorthDown);

        //populate network exit list
        networkExit.add(northRoadU);
        networkExit.add(eastRoadR);
        networkExit.add(southRoadD);
        networkExit.add(westRoadL);
    }
}
