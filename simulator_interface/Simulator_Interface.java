/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator_interface;

/**
 *
 * @author ACER
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Simulator_Interface extends JFrame {

	public Simulator_Interface() {
		super("Traffic Simulator");

		// management Policies title for the option panel
		// create JLabel to add the title to the panel
		JLabel Label1 = new JLabel("Management Policies");
		// set the title to font sansserif, centre it and ensure it has size 14
		Label1.setFont(new Font("sansserif", Font.CENTER_BASELINE, 14));

		//title
		JLabel Behaviours = new JLabel("Behaviours");
		//styled the title by changing font and size
		Behaviours.setFont(new Font("sansserif", Font.ITALIC, 13));
		//created three check boxes and ensured the Normal check box is selected by defualt 
		JCheckBox Normal = new JCheckBox("Normal");
		Normal.setSelected(true);
		JCheckBox Cautious = new JCheckBox("Cautious");
		JCheckBox Reckless = new JCheckBox("Reckless");

		//apply a button group to the check boxes so that only one check box can be selected at one time from a group
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(Normal);
		bg1.add(Cautious);
		bg1.add(Reckless);

		//title
		JLabel Vehicles = new JLabel("Vehicles");
		Vehicles.setFont(new Font("sansserif", Font.ITALIC, 13));
		JCheckBox Cars = new JCheckBox("Cars");
		Cars.setSelected(true);
		JCheckBox Buses_Coaches = new JCheckBox("Buses/Coaches");

		//title
		JLabel Road_Network = new JLabel("Road Network");
		Road_Network.setFont(new Font("sansserif", Font.ITALIC, 13));
		//created three check boxes and ensures Straight_Road is selected by default
		JCheckBox Straight_Road = new JCheckBox("Straight Road");
		Straight_Road.setSelected(true);
		JCheckBox Roundabout = new JCheckBox("Roundabout");
		JCheckBox Multi_Lane_Junction = new JCheckBox("Multi-Lane Junction");

		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(Straight_Road);
		bg2.add(Roundabout);
		bg2.add(Multi_Lane_Junction);

		//
		JLabel Emergency_Service = new JLabel("Emergency Service");
		Emergency_Service.setFont(new Font("sansserif", Font.ITALIC, 13));
		JCheckBox Emergency_Vehicles = new JCheckBox(
				"Ambulance/Police/Firetruck");

		//create the button using JButton so that it can update the GUI after user has selected the options - 
		//check boxes provided
		

		// set the GUI layout to BorderLayout
		// it has 5 components SOUTH, EAST, WEST, NORTH & CENTER
		setLayout(new BorderLayout());

		// create new panel to store objects

		//int dim = 50;
		@SuppressWarnings("unused")
		//int matrix[][] = new int[50][50];

		//JPanel panel1 = new JPanel(new GridLayout(dim, dim));
                    
                JPanel panel1 = new JPanel ();
                
                JTable table = new JTable(42, 42);
                panel1.add(table);
                StraightRoad.AnimateThread demoThread = new StraightRoad.AnimateThread(table, 42,42);
		panel1.setBackground(Color.white);
		add(table, BorderLayout.CENTER);
                JButton Update = new JButton("Update");
                Update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                demoThread.run();
            }
        });

		//for (int i = 0; i < dim; i++) {
			//for (int j = 0; j < dim; j++) {
				//JPanel box = new JPanel();
				//box.setBorder(BorderFactory.createLineBorder(Color.black));
				//panel1.add(box);

				//create new panel and apply the grid layout to this panel
				JPanel panel2 = new JPanel(new GridLayout(15, 0));
				//set the background colour for the panel to white
				panel2.setBackground(Color.WHITE);

				//store panel3 in the first row of panel2 grid layout
				JPanel panel3 = new JPanel();
				panel3.add(Label1);
				panel2.add(panel3);

				//store items in the second, third, fourth and fifth rows of the grid layout respectively
				panel2.add(Behaviours);
				panel2.add(Normal);
				panel2.add(Cautious);
				panel2.add(Reckless);

				//store items in the sixth, seventh and eighth rows of the grid layout respectively
				panel2.add(Vehicles);
				panel2.add(Cars);
				panel2.add(Buses_Coaches);

				//store items in the ninth, tenth, eleventh and twelfth rows of the grid layout respectively
				panel2.add(Road_Network);
				panel2.add(Straight_Road);
				panel2.add(Roundabout);
				panel2.add(Multi_Lane_Junction);

				//store items in the thirteenth and fourteenth rows of the grid layout respectively
				panel2.add(Emergency_Service);
				panel2.add(Emergency_Vehicles);

				//store item in the fifteenth row of the grid layout 
				JPanel panel4 = new JPanel(new GridLayout(1, 3));
				panel4.setBackground(Color.white);
				panel4.add(new JLabel());
				panel4.add(new JLabel());
				panel4.add(Update);
				panel2.add(panel4);

				add(panel2, BorderLayout.EAST);

				setSize(1350, 710);
				setResizable(false);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//}

		//}
	}

public static void main(String arg[]) {

		new Simulator_Interface().setVisible(true);
	}
}