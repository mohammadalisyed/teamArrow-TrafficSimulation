/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author Alexander
 */
public class RoadPreview {

    JFrame frame;
    JTable table;
    JButton stepBtn;

    public RoadPreview(OneWayRoad road) {
        
        int x = road.getRoadLen();
        int y = road.getNoOfLanes();

        frame = new JFrame();
        table = new JTable(y, x);
        stepBtn = new JButton("next time step");

        table.setEnabled(false);
        frame.add(table, BorderLayout.PAGE_START);
        frame.add(stepBtn, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void DisplayRoad(OneWayRoad road) {
        int roadYLen = road.getNoOfLanes();
        int roadXLen = road.getRoadLen();
        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                if (road.getRoadCell(x, y).getIsEmpty() == false) {
                    table.setValueAt(road.getRoadCell(x, y).getVehicle().getSpeed(), y, x);
                } else {
                    table.setValueAt("", y, x);
                }
            }
        }
    }

}
