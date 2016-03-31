package CellAutomaton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Simulator_Interface extends JFrame {

	public JLabel simulation_options, simulation_speed, road_network,
			road_network_options, network_entrances, network_entrance_options,
			traffic_inflow, frequency_of_vehicles, traffic_management,
			junctions, traffic_light_options, light_switch_timer;

	public JComboBox simulation_speed_comboBox, network_entrances_comboBox,
			road_network_comboBox, junctions_comboBox;

	public JTextField fov_textfield, lst_textfield;

	public JSlider slider_inflow;
        
        public RoadEnvironment re;
        
        
	public Simulator_Interface() {
		super("Traffic Simulator");

		setLayout(new BorderLayout());

		// Simulation Speed
		simulation_options = new JLabel("Simulation Options");
		simulation_options.setFont(new Font("sansserif", Font.BOLD, 13));
		simulation_options.setOpaque(true);

		simulation_speed = new JLabel("Simulation Speed:");
		simulation_speed_comboBox = new JComboBox();
		simulation_speed_comboBox.setBackground(Color.white);
		simulation_speed_comboBox.addItem("Slow");
		simulation_speed_comboBox.addItem("Normal");
		simulation_speed_comboBox.addItem("Fast");
		simulation_speed_comboBox.setSelectedIndex(1);
                
                ExampleNetwork demo = new ExampleNetwork();
                re = new RoadEnvironment(demo);
                
                simulation_speed_comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int numSelected = simulation_speed_comboBox.getSelectedIndex();

                            switch (numSelected) {
                            // slow timer
                                case 0:
                                    re.setTimerDelay(100);    
                                    break;
                            //normal timer
                                case 1:
                                    re.setTimerDelay(50);
                                    break;
                            //fast timer    
                                case 2:
                                   re.setTimerDelay(10);
                                    break;
                            }
			}
            
		});
                       

		// /////////////Road Network
		road_network = new JLabel("Road Network:");
		// Road Network JComboBox
		road_network_comboBox = new JComboBox();
		road_network_comboBox.setBackground(Color.white);
		road_network_comboBox.addItem("Select...");
		road_network_comboBox.addItem("Multi-lane Junction");
		road_network_comboBox.addItem("Roundabout");
		road_network_comboBox.setSelectedIndex(0);

		// Constraint
		// disabling network entrance comboBox if select option is selected in
		// road network comboBox
		road_network_comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int num = road_network_comboBox.getSelectedIndex();

				if (num == 1 || num == 2) {

                                        network_entrances_comboBox.setEnabled(true);
					junctions_comboBox.setEnabled(true);
                                        

				} else if (num == 0) {
					network_entrances_comboBox.setEnabled(false);
					junctions_comboBox.setEnabled(false);
				}
			}

		});

		// //////////////Road Network Options
		road_network_options = new JLabel("Road Network Options");
		road_network_options.setFont(new Font("sansserif", Font.BOLD, 13));
		road_network_options.setOpaque(true);

		// Network Entrance ComboBox
		network_entrances = new JLabel("Network Entrances:");
		network_entrances_comboBox = new JComboBox();
		network_entrances_comboBox.setBackground(Color.white);
		network_entrances_comboBox.setEnabled(true);
                
                
                ArrayList <OneWayRoad> networkEntArray = re.getNetworkEntArray();

		// add the different entrant items here
                
                for(OneWayRoad netEntrItem: networkEntArray ){
                  network_entrances_comboBox.addItem(netEntrItem.getRoadName());
                }
                
		
		//network_entrances_comboBox.addItem("hello");

		// constraint
		// disabling the inflow slider and frequency of vehicles text field if
		// select is chosen in the network entrances comboBox
		network_entrances_comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int num = network_entrances_comboBox.getSelectedIndex();

				// check for all indices
				if (num == 1) {

					slider_inflow.setEnabled(true);
					fov_textfield.setEnabled(true);

				} else if (num == 0) {

					slider_inflow.setEnabled(false);
					fov_textfield.setEnabled(false);
				}
			}

		});

		// ///////Network Entrance Options
		network_entrance_options = new JLabel("Network Entrance Options");
		network_entrance_options.setFont(new Font("sansserif", Font.BOLD, 13));
		network_entrance_options.setOpaque(true);

		// Traffic Inflow Slider
		traffic_inflow = new JLabel("Traffic Inflow:");
		slider_inflow = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		slider_inflow.setBackground(Color.white);
		slider_inflow.setMinorTickSpacing(1);
		slider_inflow.setPaintTicks(true);
		slider_inflow.setPaintLabels(true);
		slider_inflow.setLabelTable(slider_inflow.createStandardLabels(5));
		slider_inflow.setEnabled(false);

		// Frequency Of Vehicles Text Field
		frequency_of_vehicles = new JLabel("Frequency Of Vehicles:");
		fov_textfield = new JTextField();
		fov_textfield.setEnabled(false);

		// ////////////////////////////////////////////////////////////////////
		// Traffic Management

		traffic_management = new JLabel("Traffic Management");
		traffic_management.setFont(new Font("sansserif", Font.BOLD, 13));
		traffic_management.setOpaque(true);

		// Junctions
		junctions = new JLabel("Junctions:");
		junctions_comboBox = new JComboBox();
		junctions_comboBox.setBackground(Color.white);
                
                ArrayList<Junction> junctArray = re.getJunctArray();
                
		for(Junction junctItem: junctArray){
                    junctions_comboBox.addItem(junctItem.getJunctName());		
                }
                
                //junctions_comboBox.addItem("Hello");

		junctions_comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int num = junctions_comboBox.getSelectedIndex();

				if (num == 1) {

					lst_textfield.setEnabled(true);

				} else if (num == 0) {

					lst_textfield.setEnabled(true);
				}
			}

		});

		junctions_comboBox.setEnabled(true);

		// Traffic Light Options
		traffic_light_options = new JLabel("Traffic Light Options");
		traffic_light_options.setFont(new Font("sansserif", Font.BOLD, 13));

		// Managing the traffic lights
		light_switch_timer = new JLabel("Light Switch Timer:");
		lst_textfield = new JTextField();
		lst_textfield.setEnabled(false);

		JPanel optionsPanel = new JPanel(new GridLayout(18, 0));
		optionsPanel.setBackground(Color.WHITE);

		optionsPanel.add(simulation_speed);
		optionsPanel.add(simulation_speed_comboBox);

		optionsPanel.add(road_network);
		optionsPanel.add(road_network_comboBox);

		optionsPanel.add(road_network_options);

		optionsPanel.add(network_entrances);
		optionsPanel.add(network_entrances_comboBox);

		optionsPanel.add(network_entrance_options);

		optionsPanel.add(traffic_inflow);
		optionsPanel.add(slider_inflow);

		optionsPanel.add(frequency_of_vehicles);
		optionsPanel.add(fov_textfield);

		optionsPanel.add(traffic_management);

		optionsPanel.add(junctions);
		optionsPanel.add(junctions_comboBox);

		optionsPanel.add(traffic_light_options);

		optionsPanel.add(light_switch_timer);
		optionsPanel.add(lst_textfield);

		add(optionsPanel, BorderLayout.WEST);
                
                //Road Environment add to Center of Panel
                
               
                
                
                add(re.dw, BorderLayout.CENTER);

		// adding JScollPane to a JPanel to which s then added to the Center of
		// the BorderLayout
		JPanel simulationPanel = new JPanel();
		JScrollPane simulationPane = new JScrollPane(simulationPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//add(simulationPane, BorderLayout.CENTER);

		setSize(1350, 710);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
