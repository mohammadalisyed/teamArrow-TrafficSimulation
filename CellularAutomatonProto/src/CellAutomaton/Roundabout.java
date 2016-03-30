/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

/**
 *
 * @author Windows
 */
public class Roundabout extends RoadNetworkTemplate {

    OneWayRoad northRoadU = new OneWayRoad(2, 26, 56, 0, UP);
    OneWayRoad northRoadD = new OneWayRoad(2, 26, 58, 0, DOWN);
//
    OneWayRoad eastRoadR = new OneWayRoad(26, 2, 58, 64, RIGHT);
    OneWayRoad eastRoadL = new OneWayRoad(26, 2, 58, 66, LEFT);
//
    OneWayRoad southRoadU = new OneWayRoad(2, 26, 40, 82, UP);
    OneWayRoad southRoadD = new OneWayRoad(2, 26, 42, 82, DOWN);
//
    OneWayRoad westRoadR = new OneWayRoad(26, 2, 0, 64, RIGHT);
    OneWayRoad westRoadL = new OneWayRoad(26, 2, 0, 66, LEFT);
//Junction----
    Junction junctW;
    OneWayRoad[] junctWEntr = new OneWayRoad[4];
    OneWayRoad[] junctWExit = new OneWayRoad[4];

    Junction junctS;
    OneWayRoad[] junctSEntr = new OneWayRoad[4];
    OneWayRoad[] junctSExit = new OneWayRoad[4];

    Junction junctE;
    OneWayRoad[] junctEEntr = new OneWayRoad[4];
    OneWayRoad[] junctEExit = new OneWayRoad[4];

    Junction junctN;
    OneWayRoad[] junctNEntr = new OneWayRoad[4];
    OneWayRoad[] junctNExit = new OneWayRoad[4];
//Corner-----
    Junction cornerNW;
    OneWayRoad[] cornerNWEntr = new OneWayRoad[4];
    OneWayRoad[] cornerNWExit = new OneWayRoad[4];

    Junction cornerNE;
    OneWayRoad[] cornerNEEntr = new OneWayRoad[4];
    OneWayRoad[] cornerNEExit = new OneWayRoad[4];

    Junction cornerSE;
    OneWayRoad[] cornerSEEntr = new OneWayRoad[4];
    OneWayRoad[] cornerSEExit = new OneWayRoad[4];

    Junction cornerSW;
    OneWayRoad[] cornerSWEntr = new OneWayRoad[4];
    OneWayRoad[] cornerSWExit = new OneWayRoad[4];

    OneWayRoad westToNorthWest = new OneWayRoad(4, 10, 26, 54, UP, "W to NW");
    OneWayRoad northWestToWest = new OneWayRoad(4, 10, 26, 68, UP, "NW to W");
    OneWayRoad southToNorthWest = new OneWayRoad(10, 4, 30, 78, LEFT, "S to NW");
    OneWayRoad southEastToSouth = new OneWayRoad(10, 4, 44, 78, LEFT, "SE to S");
    OneWayRoad eastToSouthEast = new OneWayRoad(4, 10, 54, 68, DOWN, "E to SE");
    OneWayRoad northEastToEast = new OneWayRoad(4, 10, 54, 54, DOWN, "NE to E");
    OneWayRoad northToNorthEast = new OneWayRoad(10, 4, 44, 50, RIGHT, "N to NE");
    OneWayRoad northWestToNorth = new OneWayRoad(10, 4, 30, 50, RIGHT, "NW to N");

    public Roundabout() {

        //set junction exits
//        crossroadExit[UP] = northRoadU;
//        crossroadExit[DOWN] = southRoadD;
//        crossroadExit[LEFT] = westRoadL;
//        crossroadExit[RIGHT] = eastRoadR;
//Junction----
//    Junction junctW;
//    OneWayRoad[] junctWEntr = new OneWayRoad[4];
        junctWEntr[UP] = northWestToWest;
        junctWEntr[DOWN] = null;
        junctWEntr[LEFT] = null;
        junctWEntr[RIGHT] = westRoadR;
//    OneWayRoad[] junctWExit = new OneWayRoad[4];
        junctWExit[UP] = westToNorthWest;
        junctWExit[DOWN] = null;
        junctWExit[LEFT] = westRoadL;
        junctWExit[RIGHT] = null;
//
//    Junction junctS;
//    OneWayRoad[] junctSEntr = new OneWayRoad[4];
        junctSEntr[UP] = southRoadU;
        junctSEntr[DOWN] = null;
        junctSEntr[LEFT] = southEastToSouth;
        junctSEntr[RIGHT] = null;
//    OneWayRoad[] junctSExit = new OneWayRoad[4
        junctSExit[UP] = null;
        junctSExit[DOWN] = southRoadD;
        junctSExit[LEFT] = southToNorthWest;
        junctSExit[RIGHT] = null;
//
//    Junction junctE;
//    OneWayRoad[] junctEEntr = new OneWayRoad[4];
//    OneWayRoad[] junctEExit = new OneWayRoad[4];
//
//    Junction junctN;
//    OneWayRoad[] junctNEntr = new OneWayRoad[4];
//    OneWayRoad[] junctNExit = new OneWayRoad[4];
////Corner-----
//    Junction cornerNW;
//    OneWayRoad[] cornerNWEntr = new OneWayRoad[4];
//    OneWayRoad[] cornerNWExit = new OneWayRoad[4];
//
//    Junction cornerNE;
//    OneWayRoad[] cornerNEEntr = new OneWayRoad[4];
//    OneWayRoad[] cornerNEExit = new OneWayRoad[4];
//
//    Junction cornerSE;
//    OneWayRoad[] cornerSEEntr = new OneWayRoad[4];
//    OneWayRoad[] cornerSEExit = new OneWayRoad[4];
//
//    Junction cornerSW;
//    OneWayRoad[] cornerSWEntr = new OneWayRoad[4];
//    OneWayRoad[] cornerSWExit = new OneWayRoad[4];

        // cornerNW
        // cornerNE
        // cornerSE
        // cornerSW
        //set junction entrances
//        crossroadEntr[UP] = southRoadU;
//        crossroadEntr[DOWN] = northRoadD;
//        crossroadEntr[LEFT] = eastRoadL;
//        crossroadEntr[RIGHT] = westRoadR;
        // cornerNW
        // cornerNE
        // cornerSE
        // cornerSW
        //create junctions
        junctW = new Junction(26, 64, junctWExit, junctWEntr, 4, 4);
        junctS = new Junction(40, 78, junctSExit, junctSEntr, 4, 4);
        junctE = new Junction(54, 64, junctEExit, junctEEntr, 4, 4);
        junctN = new Junction(40, 50, junctNExit, junctNExit, 4, 4);

        cornerNW = new Junction(26, 50, cornerNWExit, cornerNWEntr, 4, 4);
        cornerSW = new Junction(26, 78, cornerSWExit, cornerSWEntr, 4, 4);
        cornerSE = new Junction(54, 78, cornerSEExit, cornerSEEntr, 4, 4);
        cornerNE = new Junction(54, 50, cornerNEExit, cornerNEEntr, 4, 4);
        //populate junction list
        junctArray.add(junctW);
        junctArray.add(junctS);
        junctArray.add(junctE);
        junctArray.add(junctN);

        junctArray.add(cornerNW);
        junctArray.add(cornerSW);
        junctArray.add(cornerSE);
        junctArray.add(cornerNE);
        //set traffic light switches

        //populate road list
        roadArray.add(westRoadR);
        roadArray.add(westRoadL);
        roadArray.add(southRoadU);
        roadArray.add(southRoadD);
        roadArray.add(eastRoadR);
        roadArray.add(eastRoadL);
        roadArray.add(northRoadU);
        roadArray.add(northRoadD);

        roadArray.add(westToNorthWest);
        roadArray.add(northWestToWest);
        roadArray.add(southToNorthWest);
        roadArray.add(southEastToSouth);
        roadArray.add(eastToSouthEast);
        roadArray.add(northEastToEast);
        roadArray.add(northToNorthEast);
        roadArray.add(northWestToNorth);
        //populate network entrance list
        //populate network exit list
    }
}
