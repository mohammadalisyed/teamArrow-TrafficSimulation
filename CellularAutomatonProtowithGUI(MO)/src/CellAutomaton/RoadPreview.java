/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Alexander
 */
public class RoadPreview {

    JFrame frame;
    JTable table;
    JButton stepBtn;
    JButton stopBtn;
    JPanel buttonPane;

    public RoadPreview(OneWayRoad road) {

        int x = road.getRoadXLen();
        int y = road.getRoadYLen();

        frame = new JFrame();
        table = new JTable(y, x);

        stepBtn = new JButton("next time step");
        stopBtn = new JButton("traffic light");

        buttonPane = new JPanel();
        buttonPane.add(stepBtn);
        buttonPane.add(stopBtn);

        table.setEnabled(false);
        frame.add(table, BorderLayout.PAGE_START);
        frame.add(buttonPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public void DisplayRoad(OneWayRoad road) {
        boolean stopLight = road.getStopLight();
        if (stopLight) {
            table.setBackground(Color.red);
        } else {
            table.setBackground(Color.green);
        }
        int roadYLen = road.getRoadYLen();
        int roadXLen = road.getRoadXLen();
        for (int y = 0; y < roadYLen; y++) {
            for (int x = 0; x < roadXLen; x++) {
                if (road.getRoadCell(x, y) == null) {
                    table.setValueAt("",y,x);
                } else {
                    table.setValueAt(road.getRoadCell(x, y).getSpeed(), y, x);
//                    table.setValueAt("{"+x+"}"+"{"+y+"}", y, x);
                    
                }
            }
        }
    }

}
