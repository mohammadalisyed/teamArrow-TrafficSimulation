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
public class Vehicle {

    int length;
    int speed;
    // behaviour? 
    // type? normal car/van or bus/taxi or emergency services?

    public Vehicle(int length, int speed) {
        this.length = length;
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLength() {
        return length;
    }

    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public void setLength(int newLength) {
        length = newLength;
    }
}
