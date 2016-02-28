/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Alexander
 */
public class Demo {

    public static void main(String[] args) {
        OneWayRoad testRoad = new OneWayRoad(60, 2);
//        testRoad.displayRoad();
        RoadPreview testPreview = new RoadPreview(testRoad);
        AutomatonModel demoModel = new AutomatonModel();

        testPreview.stepBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                demoModel.updateRoad(testRoad);
                demoModel.addCar(testRoad);
                testPreview.DisplayRoad(testRoad);
            }
        });
    }
}
