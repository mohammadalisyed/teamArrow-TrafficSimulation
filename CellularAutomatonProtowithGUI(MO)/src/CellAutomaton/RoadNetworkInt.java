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
public interface RoadNetworkInt {

    public ArrayList<OneWayRoad> getRoadArray();

    public ArrayList<Junction> getJunctArray();

    public ArrayList<OneWayRoad> getEntrLst();

    public ArrayList<OneWayRoad> getExitLst();

}
