/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oldPrototyping;

import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author Alexander
 */
public class Demo {

    public static class RoadThread extends Thread {

        int maxV = 5;
        int roadXLen = 60;
        int roadYLen = 2;
        int road[][] = new int[roadXLen][roadYLen];

        public RoadThread() {
            setRoad();
            displayRoad();
        }

        public synchronized void run() {
            int counter = 0;
            while (counter < 100) {
                counter++;
//                try {
                System.out.println(counter);
                updateRoad();
                addCar();
//                    displayRoad();

//                    Thread.sleep(20);
//                } catch (InterruptedException ex) {
//                }
            }

        }

        public void addCar() {
            if (road[0][0] == -1) {
                road[0][0] = 5;
            }

        }

        public void displayRoad() {
//            StringBuilder labelTxt = new StringBuilder("");
            for (int y = 0; y < roadYLen; y++) {
                for (int x = 0; x < roadXLen; x++) {
//                    System.out.print(road[x][y]);
//                }
                    if (road[x][y] == -1) {
                        System.out.print("-");
                    } else {
                        System.out.print(road[x][y]);
                    }
                }
                System.out.println();
//                labelTxt.append("/n");
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
                for (int x = 0; x < roadXLen; x++) {
                    if (road[x][y] != -1) {
                        int currentX = x;
                        int nextX = -1;
                        if (currentX < roadXLen) { // not at end of road
                            
                            int count = 0;
                            while (count < roadXLen){
                                
                            }
                            
                            for (int i = x + 1; i < roadXLen; i++) {// find the next car in front's location
                                if (road[i][y] != -1) {
                                    nextX = i;
                                    break;
                                }
                            }

                            int currentV = road[x][y];
                            
                            // move car step:
                            if (nextX == -1) {//isn't a car in front of the current car:
                                currentV = acceleration(currentV);

                                int travelD = driving(currentX, currentV);

                                if ((travelD + x) < roadXLen - 1) {// car isn't on last cell of road
                                    road[x + travelD][y] = currentV;
                                    road[x][y] = -1;
                                    x += (travelD);// need to move the counter to the block the were the car has been moved to.
                                } else {// car is on last cell of road i.e is leaving the road
                                    road[x][y] = -1;
                                }
                                
                            } else {// there is a car in front the current car
                                int dToNextX = nextX - currentX;
                                currentV = acceleration(currentV);
                                currentV = braking(currentV, dToNextX);
//                                randomise step
                                road[x][y] = -1;
                                road[driving(currentX, currentV)][y] = currentV;
                                x += (driving(currentX, currentV));
                            }

                        } else {
                            road[x][y] = 0;
                        }
                    }
                }
            }
            displayRoad();
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

//        public int Randomization(int currentV){
//            int v = currentV - 1;
//            
//        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        RoadThread demoThread = new RoadThread();
        demoThread.run();
//        JTable table = new JTable(2, 10);
//        frame.add(table);
//        frame.pack();
//        frame.setVisible(true);

    }

    public class block {

        int x;
        int y;
        int state;

        public block(int x, int y) {
            this.x = x;
            this.y = y;
            state = 0;

        }

    }

}
