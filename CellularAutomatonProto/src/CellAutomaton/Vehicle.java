/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.Point;
/**
 *
 * @author Alexander
 */
public class Vehicle {

    private int length;
    private int speed;
    private boolean isChecked;
    private Point location;
    // behaviour? 
    // type? normal car/van or bus/taxi or emergency services?

    public Vehicle(int length, int speed) {
        this.length = length;
        this.speed = speed;
        isChecked = false;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLength() {
        return length;
    }
    
    public boolean getIsChecked(){
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked){
        this.isChecked = isChecked;
    }
    
    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public void setLength(int newLength) {
        length = newLength;
    }
}
