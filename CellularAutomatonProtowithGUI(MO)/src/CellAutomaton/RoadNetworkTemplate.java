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
public class RoadNetworkTemplate implements RoadNetworkInt {

    protected static final int UP = RoadEnvironment.UP,
            DOWN = RoadEnvironment.DOWN, LEFT = RoadEnvironment.LEFT,
            RIGHT = RoadEnvironment.RIGHT;

    ArrayList<OneWayRoad> roadArray = new ArrayList<>();//list of all the roads in the network
    ArrayList<Junction> junctArray = new ArrayList<>();//list of all the junctions in the network
    ArrayList<OneWayRoad> networkEntr = new ArrayList<>();//list of roads where cars spawn into the network
    ArrayList<OneWayRoad> networkExit = new ArrayList<>();//list of roads where cars exit from the network

    public RoadNetworkTemplate() {

    }

    @Override
    public ArrayList<OneWayRoad> getRoadArray() {
        return roadArray;
    }

    @Override
    public ArrayList<Junction> getJunctArray() {
        return junctArray;
    }

    @Override
    public ArrayList<OneWayRoad> getEntrLst() {
        return networkEntr;
    }

    @Override
    public ArrayList<OneWayRoad> getExitLst() {
        return networkExit;
    }
}
