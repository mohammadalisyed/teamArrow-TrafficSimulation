/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class Crossroad {

    private Vehicle[][] junction;
    private OneWayRoad[] entrRoads;
    private OneWayRoad[] exitRoads;

    private Boolean xTravel;// junction is open to cars entering from horizontally
    private int x;
    private int y;

    public Crossroad(int x, int y, OneWayRoad[] entrRoads, 
            OneWayRoad[] exitRoads, int width, int height) {
        this.x = x;
        this.y = y;
        this.entrRoads = entrRoads;
        this.exitRoads = exitRoads;
        
        junction = new Vehicle[width][height];
    }
    
//    public OneWayRoad getEntr(int entrN){
//        return entrRoads[entrN];
//    }
//    
//    public OneWayRoad getExit(int exitN){
//        return entrRoads[exitN];
//    }

    public boolean getXTravel() {
        return xTravel;
    }

    public void setXTravel(boolean newBool) {
        xTravel = newBool;
    }

    public void switchXTravel() {
        if (xTravel) {
            xTravel = false;
        } else {
            xTravel = true;
        }
    }

}
