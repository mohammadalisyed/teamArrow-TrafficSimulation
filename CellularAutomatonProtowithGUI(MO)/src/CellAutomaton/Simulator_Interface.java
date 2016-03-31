package CellAutomaton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

    public DisplayWindow dw;

    public final int FAST = 100, NORMAL = 50, SLOW = 10;
    public final int S0 = -1, S1 = 55, S2 = 50, S3 = 45, S4 = 40, S5 = 35,
            S6 = 30, S7 = 25, S8 = 20, S9 = 15, S10 = 10, S11 = 5, S12 = 0;

    public int currentSpeed = NORMAL;

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

//        ExampleNetwork demo = new ExampleNetwork();
        CrossroadNetwork demo2 = new CrossroadNetwork();
        dw = new DisplayWindow();
        re = new RoadEnvironment(demo2, dw);
        dw.setRoadEnvironment(re);

        simulation_speed_comboBox.addActionListener(new speedComboBoxListener());

        // /////////////Road Network
        road_network = new JLabel("Road Network:");
        // Road Network JComboBox
        road_network_comboBox = new JComboBox();
        road_network_comboBox.setBackground(Color.white);
//        road_network_comboBox.addItem("Select...");
        road_network_comboBox.addItem("Multi-lane Junction");
        road_network_comboBox.addItem("Road Network Example");
        road_network_comboBox.setSelectedIndex(0);

        // Constraint
        // disabling network entrance comboBox if select option is selected in
        // road network comboBox
        road_network_comboBox.addActionListener(new networkComboBoxListener());

        // //////////////Road Network Options
        road_network_options = new JLabel("Road Network Options");
        road_network_options.setFont(new Font("sansserif", Font.BOLD, 13));
        road_network_options.setOpaque(true);

        // Network Entrance ComboBox
        network_entrances = new JLabel("Network Entrances:");
        network_entrances_comboBox = new JComboBox();
        network_entrances_comboBox.setBackground(Color.white);
        network_entrances_comboBox.setEnabled(true);
        setNetworkEntrComboBox();

        // constraint
        // disabling the inflow slider and frequency of vehicles text field if
        // select is chosen in the network entrances comboBox
        network_entrances_comboBox.addActionListener(new entrComboBoxListener());

        // ///////Network Entrance Options
        network_entrance_options = new JLabel("Network Entrance Options");
        network_entrance_options.setFont(new Font("sansserif", Font.BOLD, 13));
        network_entrance_options.setOpaque(true);

        ArrayList<OneWayRoad> networkEntr = re.getNetworkEntArray();

        int currentRefresh = networkEntr.get(0).getInputRefresh();
        currentRefresh = getSliderValueFromInt(currentRefresh);

        // Traffic Inflow Slider
        traffic_inflow = new JLabel("Traffic Inflow (every n timesteps):");
        slider_inflow = new JSlider(JSlider.HORIZONTAL, 0, 12, currentRefresh);
        slider_inflow.setBackground(Color.white);
        slider_inflow.setMinorTickSpacing(1);
        slider_inflow.setPaintTicks(true);

        Hashtable lblTable = new Hashtable();

        lblTable.put(new Integer(0), new JLabel("0"));
        lblTable.put(new Integer(1), new JLabel("55"));
        lblTable.put(new Integer(6), new JLabel("30"));
        lblTable.put(new Integer(11), new JLabel("5"));
        lblTable.put(new Integer(12), new JLabel("1"));
        slider_inflow.setLabelTable(lblTable);

        slider_inflow.setPaintLabels(true);
//        slider_inflow.setLabelTable(slider_inflow.createStandardLabels(5));
        slider_inflow.setEnabled(true);
        slider_inflow.addChangeListener(new inflowSliderListener());

        // Frequency Of Vehicles Text Field
//        frequency_of_vehicles = new JLabel("Frequency Of Vehicles:");
//        fov_textfield = new JTextField();
//        fov_textfield.setEnabled(false);
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

        for (Junction junctItem : junctArray) {
            junctions_comboBox.addItem(junctItem.getJunctName());
        }

        //junctions_comboBox.addItem("Hello");
        junctions_comboBox.addActionListener(new junctComboBoxListener());
        junctions_comboBox.setEnabled(true);

        // Traffic Light Options
        traffic_light_options = new JLabel("Traffic Light Options");
        traffic_light_options.setFont(new Font("sansserif", Font.BOLD, 13));

        // Managing the traffic lights
        light_switch_timer = new JLabel("Light Switch Timer:");
        lst_textfield = new JTextField();
        lst_textfield.setEnabled(false);

        JPanel optionsPanel = new JPanel(new GridLayout(20, 0));
        optionsPanel.setBackground(Color.WHITE);

        optionsPanel.add(simulation_options);
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
//         optionsPanel.add(Box.createRigidArea(new Dimension(5, 5)));

//        optionsPanel.add(frequency_of_vehicles);
//        optionsPanel.add(fov_textfield);
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
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        re.setEntrInputRefresh(0, 50);
    }

    public int getSliderValueFromInt(int i) {
        int r = 0;
        switch (i) {
            case S0:
                r = 0;
                break;
            case S1:
                r = 1;
                break;
            case S2:
                r = 2;
                break;
            case S3:
                r = 3;
                break;
            case S4:
                r = 4;
                break;
            case S5:
                r = 5;
                break;
            case S6:
                r = 6;
                break;
            case S7:
                r = 7;
                break;
            case S8:
                r = 8;
                break;
            case  S9:
                r = 9;
                break;
            case S10:
                r = 10;
                break;
            case S11:
                r = 11;
                break;
            case S12:
                r = 12;
                break;
        }
        return r;
    }

    public int getIntFromSliderValue(int i) {
        int r = 0;
        switch (i) {
            case 0:
                r = S0;
                break;
            case 1:
                r = S1;
                break;
            case 2:
                r = S2;
                break;
            case 3:
                r = S3;
                break;
            case 4:
                r = S4;
                break;
            case 5:
                r = S5;
                break;
            case 6:
                r = S6;
                break;
            case 7:
                r = S7;
                break;
            case 8:
                r = S8;
                break;
            case 9:
                r = S9;
                break;
            case 10:
                r = S10;
                break;
            case 11:
                r = S11;
                break;
            case 12:
                r = S12;
                break;
        }
        return r;
    }
    
    public void setREFromSliderValue(int i) {
        int r = 0;
        switch (i) {
            case 0:
                
                break;
            case 1:
                r = S1;
                break;
            case 2:
                r = S2;
                break;
            case 3:
                r = S3;
                break;
            case 4:
                r = S4;
                break;
            case 5:
                r = S5;
                break;
            case 6:
                r = S6;
                break;
            case 7:
                r = S7;
                break;
            case 8:
                r = S8;
                break;
            case 9:
                r = S9;
                break;
            case 10:
                r = S10;
                break;
            case 11:
                r = S11;
                break;
            case 12:
                r = S12;
                break;
        }
    }

    public class inflowSliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            int index = slider_inflow.getValue();
            System.out.println(index);
        }
    }

    public class junctComboBoxListener implements ActionListener {

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
    }

    public class entrComboBoxListener implements ActionListener {

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
    }

    public class speedComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub

            int numSelected = simulation_speed_comboBox.getSelectedIndex();

            switch (numSelected) {
                // slow timer
                case 0:
                    re.setTimerDelay(FAST);
                    currentSpeed = FAST;
                    break;
                //normal timer
                case 1:
                    re.setTimerDelay(NORMAL);
                    currentSpeed = NORMAL;
                    break;
                //fast timer    
                case 2:
                    re.setTimerDelay(SLOW);
                    currentSpeed = SLOW;
                    break;
            }
        }
    }

    public class networkComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub

            int num = road_network_comboBox.getSelectedIndex();

            if (num == 0) {
                re = new RoadEnvironment(new CrossroadNetwork(), dw);
                dw.setRoadEnvironment(re);

            }
            if (num == 1) {
                re = new RoadEnvironment(new ExampleNetwork(), dw);
                dw.setRoadEnvironment(re);

            }
            re.setTimerDelay(currentSpeed);
            setNetworkEntrComboBox();
            setJunctionComboBox();
        }
    }

    public void setNetworkEntrComboBox() {
        ArrayList<OneWayRoad> networkEntr = re.getNetworkEntArray();
        network_entrances_comboBox.removeAllItems();
        for (int i = 0; i < networkEntr.size(); i++) {
            network_entrances_comboBox.addItem(networkEntr.get(i).getRoadName());
        }
    }

    public void setJunctionComboBox() {
        ArrayList<Junction> junctArray = re.getJunctArray();
        junctions_comboBox.removeAllItems();
        for (int i = 0; i < junctArray.size(); i++) {
            junctions_comboBox.addItem(junctArray.get(i).getJunctName());
        }
    }
}
