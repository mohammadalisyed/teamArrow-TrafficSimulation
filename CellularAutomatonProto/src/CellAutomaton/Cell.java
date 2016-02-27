/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

/**
 *
 * @author Alexander
 */
public class Cell {

    boolean isEmpty = true;
    Vehicle cellVehicle;
    // Cell specific max speed_

    public Cell() {
        cellVehicle = null; // 
        isEmpty = true;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public Vehicle getVehicle() {
        // throw exception?
        return cellVehicle;
    }

    public void clearVehicle() {
        cellVehicle = null;
        isEmpty = true;
    }

    public void setVehicle(Vehicle cellVehicle) {
        this.cellVehicle = cellVehicle;
        isEmpty = false;
    }

}
