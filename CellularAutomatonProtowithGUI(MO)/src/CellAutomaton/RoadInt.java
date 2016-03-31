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
 * @author Windows
 */
public interface RoadInt {

    int getX();

    int getY();

    int getRoadXLen();

    int getRoadYLen();
    
    int getMaxV(int direction);
    
    boolean getStopLight();

    void resetRoad();
    
    void clearRoadCell(int x, int y);
    
    void setRoadCell(int x, int y, Vehicle newVehicle);

    Vehicle getRoadCell(int x, int y);

    RoadInt getExit(int direction);
    
    RoadInt getEntr(int direction);

    ArrayList<Point> getPointList();

}
