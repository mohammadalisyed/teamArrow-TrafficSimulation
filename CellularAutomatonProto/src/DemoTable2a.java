/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 * http://scs-europe.net/services/ess2004/pdf/log-13.pdf
 * https://www.hiskp.uni-bonn.de/uploads/media/traffic.pdf
 *
 * @author Alexander
 */
public class DemoTable2a {

    public static class RoadThread extends Thread {

        int maxV = 5;
        int roadXLen;
        int roadYLen;
        int road[][];
        JTable table;

        public RoadThread(JTable table, int x, int y) {
            this.table = table;
            roadXLen = x;
            roadYLen = y;
            road = new int[roadXLen][roadYLen];
            setRoad();
        }

        public synchronized void run() {
//            int counter = 0;
//            while (counter < 100) {

//                try {
//                System.out.println("step no:" + counter);
            updateRoad();
            addCar();
            displayRoad(table);
//                counter++;

//                displayRoad();
//                this.wait(20);
//                    Thread.sleep(20);
//                } catch (InterruptedException ex) {
//                }
//            }
        }

        public void addCar() {
            // write better addCar() that sets speed based on cars in front
            if (road[0][0] == -1) {
                road[0][0] = 1;
            }
            if (road[0][1] == -1) {
                road[0][1] = 1;
            }

        }

        public void displayRoad(JTable table) {
            for (int y = 0; y < roadYLen; y++) {
                for (int x = 0; x < roadXLen; x++) {
                    if (road[x][y] != -1) {
                        table.setValueAt(road[x][y], y, x);
                    } else {
                        table.setValueAt("", y, x);
                    }
                }
            }
//            Scanner s = new Scanner(System.in);
//            String stepToken = s.next();
        }

        public void displayRoad() {

            for (int y = 0; y < roadYLen; y++) {
                for (int x = 0; x < roadXLen; x++) {
                    if (road[x][y] == -1) {
                        System.out.print("-");
                    } else {
                        System.out.print(road[x][y]);
                    }
                }
                System.out.println();
            }
            System.out.println(";");
        }

        public void setRoad() {
            for (int y = 0; y < roadYLen; y++) {
                for (int x = 0; x < roadXLen; x++) {
                    road[x][y] = -1;
                }
            }

        }

        public void updateRoad() {
            for (int y = 0; y < roadYLen; y++) {
                int x = 0;
                while (x < roadXLen) {
                    if (road[x][y] != -1) {// there is a car in this current cell
                        int currentX = x; // index of current car
                        int currentV = road[x][y]; // current car's velocity
                        int nextCarX = -1; // index of next car, -1 : no car in front
                        int nextCarD = -1; // distance to next car, -1 : no car in front
                        int newX = 0; // current car's new cell
//                        boolean endOfRoad; // is this cell the last cell?

                        if (x == roadXLen - 1) { // are we at the last cell?                           
//                            endOfRoad = true; this is the last cell
                            road[x][y] = -1;
                            break;

                        } else {
//                            endOfRoad = false; this isn't the last cell
//                            find the index of next car in front:
                            for (int i = x + 1; i < roadXLen; i++) {// find the next car in front's location
                                if (road[i][y] != -1) {
                                    nextCarX = i;
                                    break;
                                }
                            }

                            // step 1 + 2: Acceleration and braking
//                            has a car been found in front of the current car? 
                            if (nextCarX == -1) { // no, the road ahead is clear
                                currentV = acceleration(currentV);

                            } else { // yes, there's a car in front
                                nextCarD = nextCarX - currentX;
                                currentV = acceleration(currentV);
                                currentV = braking(currentV, nextCarD);
                            }

                            // step 3: speed randomisation (to be add later)
                            if (currentV != 0) {
                                currentV = randomization(currentV);
                            }

                            // step 4: driving
                            newX = driving(currentX, currentV);
                            System.out.println(newX);
                            // will the car drive past the end of the road? 
                            if (newX >= roadXLen) { //yes
                                road[x][y] = -1;
                            } else { //no
                                road[x][y] = -1;
                                road[newX][y] = currentV;
                            }
                            //move the counter to the cell ahead of were the car has been moved to
                            x = newX + 1;
                        }
                    } else {//no car on this cell, check next cell
                        x++;
                    }
                }

            }
        }

        public int acceleration(int currentV) {
            int v = currentV + 1;
            if (v <= maxV) {
                return v;
            } else {
                return maxV;
            }
        }

        public int braking(int currentV, int nextCarD) {
            int d = nextCarD - 1;
            if (d <= currentV) {
                return d;
            } else {
                return currentV;
            }

        }

        public int driving(int currentX, int currentV) {
            return (currentX + currentV);

        }

        public int randomization(int currentV) {
            int v = currentV - 1;
            boolean p = new Random().nextInt(2) == 0;
            if (p) {
                return v;
            } else {
                return currentV;
            }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JTable table = new JTable(2, 60);
        JButton stepBtn = new JButton("next time step");

        table.setEnabled(false);
        frame.add(table, BorderLayout.PAGE_START);
        frame.add(stepBtn, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        RoadThread demoThread = new RoadThread(table, 60, 2);
        stepBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                demoThread.run();
            }
        });

    }

//    public class block {
//        
//        int x;
//        int y;
//        int state;
//        
//        public block(int x, int y) {
//            this.x = x;
//            this.y = y;
//            state = 0;
//            
//        }
//        
//    }
}
