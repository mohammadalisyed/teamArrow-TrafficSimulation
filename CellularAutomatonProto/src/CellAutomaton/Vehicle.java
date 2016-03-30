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
    private int direction;
    private int exitD;
    private Point location;
    // behaviour? 
    // type? normal car/van or bus/taxi or emergency services?

    public Vehicle(int length, int speed) {
        this.length = length;
        this.speed = speed;
//        this.direction = direction;
        isChecked = false;
    }
    
    public int getExitD(){
        return exitD;
    }
    
    public int setExitD(){
        return exitD;
    }
    
    public Point getLocation(){
        return location;
    }
    
    public void setLocation(Point newL){
        location = newL;
    }
//    
    public int getDirection(){
        return direction;
    }
    
    public void setDirection(int newD){
        direction = newD;
        exitD = newD;
    }
    
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int newLength) {
        length = newLength;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

}
