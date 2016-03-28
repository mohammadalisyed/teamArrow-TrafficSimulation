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
    private int direction;// direction the car is currently travelling in 
    private int exit;// exit the car wants to take at the next junction
    private int previousDirect;// direction the cars was travelling in before turning
    private boolean turning;
    private Point location;
    // behaviour? 
    // type? normal car/van or bus/taxi or emergency services?

    public Vehicle(int length, int speed) {
        this.length = length;
        this.speed = speed;
        isChecked = false;
        turning = false;
    }
    
    public int getPreviousDirect(){
        return previousDirect;
    }
    
    public void setPreviousDirect(int newDirect){
        previousDirect = newDirect;
    }
    
    public boolean getTurning(){
        return turning; 
    }
    
    public void setTurning(boolean newBool){
        turning = newBool;
    }
    
    public int getExit() {
        return exit;
    }

    public void setExit(int exit) {
        this.exit = exit;
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
//        exit = newD;
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
