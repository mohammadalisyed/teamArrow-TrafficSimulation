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
            junctions, traffic_light_options, light_switch_timer, colour_scheme;
    public JComboBox simulation_speed_comboBox, network_entrances_comboBox,
            road_network_comboBox, junctions_comboBox;

    public JTextField fov_textfield;

    public JSlider slider_inflow, slider_stoplight;

    public JRadioButton traffic_radio_button, lane_radio_button;

    public RoadEnvironment re;

    public DisplayWindow dw;

    public static final int FAST = 100, NORMAL = 50, SLOW = 10;
    public static final int S0 = -1, S1 = 55, S2 = 50, S3 = 45, S4 = 40, S5 = 35,
            S6 = 30, S7 = 25, S8 = 20, S9 = 15, S10 = 10, S11 = 5, S12 = 0;

    public static final int L0 = 10, L1 = 50, L2 = 100, L3 = 150, L4 = 200, L5 = 250,
            L6 = 300, L7 = 350, L8 = 400, L9 = 450, L10 = 500;

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

        colour_scheme = new JLabel("Colour Scheme");
        traffic_radio_button = new JRadioButton("Stop Lights");
        lane_radio_button = new JRadioButton("Lane Directions");
        traffic_radio_button.setBackground(Color.WHITE);
        lane_radio_button.setBackground(Color.WHITE);
        traffic_radio_button.setSelected(true);

        ButtonGroup radio_group = new ButtonGroup();
        radio_group.add(lane_radio_button);
        radio_group.add(traffic_radio_button);
        
        lane_radio_button.addActionListener(new laneRadioListener());
        traffic_radio_button.addActionListener(new trafficRadioListener());

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
        currentRefresh = getSliderInflowFromInt(currentRefresh);

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
        slider_inflow.addChangeListener(new sliderInflowListener());

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
        light_switch_timer = new JLabel("Stop-Light Switch (timesteps to light change):");
        slider_stoplight = new JSlider(JSlider.HORIZONTAL, 0, 10, 8);
        slider_stoplight.setBackground(Color.white);
        slider_stoplight.setMinorTickSpacing(1);
        slider_stoplight.setMajorTickSpacing(2);
        slider_stoplight.setPaintTicks(true);

        slider_stoplight.addChangeListener(new sliderStopListener());

        Hashtable lblTable2 = new Hashtable();

        lblTable2.put(new Integer(0), new JLabel("10"));
        lblTable2.put(new Integer(1), new JLabel("50"));
        lblTable2.put(new Integer(2), new JLabel("100"));
        lblTable2.put(new Integer(4), new JLabel("200"));
        lblTable2.put(new Integer(6), new JLabel("300"));
        lblTable2.put(new Integer(8), new JLabel("400"));
        lblTable2.put(new Integer(10), new JLabel("500"));

        slider_stoplight.setLabelTable(lblTable2);

        slider_stoplight.setPaintLabels(true);
        slider_stoplight.setEnabled(true);
        slider_stoplight.addChangeListener(new sliderInflowListener());

        JPanel optionsPanel = new JPanel(new GridLayout(20, 3));
        optionsPanel.setBackground(Color.WHITE);

        optionsPanel.add(simulation_options);
        optionsPanel.add(simulation_speed);
        optionsPanel.add(simulation_speed_comboBox);

        optionsPanel.add(road_network);
        optionsPanel.add(road_network_comboBox);
        
        optionsPanel.add(colour_scheme);
        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(Color.WHITE);
        radioPanel.add(traffic_radio_button);
        radioPanel.add(lane_radio_button);
        optionsPanel.add(radioPanel);

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
        optionsPanel.add(slider_stoplight);
//        optionsPanel.add(lst_textfield);

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
        setSize(1000, 750);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setStopLightSlider();
        setInflowSlider();

//        re.setEntrInputRefresh(0, 50);
    }

    public class trafficRadioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dw.setColourScheme(DisplayWindow.STOPLIGHTCOLOUR);
        }
    }
    
    public class laneRadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dw.setColourScheme(DisplayWindow.LANECOLOUR);
        }
    }

    public int getSliderStopFromInt(int i) {
        int r = 0;
        switch (i) {
            case L0:
                r = 0;
                break;
            case L1:
                r = 1;
                break;
            case L2:
                r = 2;
                break;
            case L3:
                r = 3;
                break;
            case L4:
                r = 4;
                break;
            case L5:
                r = 5;
                break;
            case L6:
                r = 6;
                break;
            case L7:
                r = 7;
                break;
            case L8:
                r = 8;
                break;
            case L9:
                r = 9;
                break;
            case L10:
                r = 10;
                break;
        }
        return r;
    }

    public int getIntFromSliderStop(int i) {
        int r = 0;
        switch (i) {
            case 0:
                r = L0;
                break;
            case 1:
                r = L1;
                break;
            case 2:
                r = L2;
                break;
            case 3:
                r = L3;
                break;
            case 4:
                r = L4;
                break;
            case 5:
                r = L5;
                break;
            case 6:
                r = L6;
                break;
            case 7:
                r = L7;
                break;
            case 8:
                r = L8;
                break;
            case 9:
                r = L9;
                break;
            case 10:
                r = L10;
                break;
        }
        return r;
    }

    public int getSliderInflowFromInt(int i) {
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
            case S9:
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

    public int getIntFromSliderInflow(int i) {
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

    public class sliderStopListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            try {
                int index = slider_stoplight.getValue();
                int junctI = junctions_comboBox.getSelectedIndex();

                int stopTime = getIntFromSliderStop(index);
                re.getJunctArray().get(junctI).setSwitchTime(stopTime);
            } catch (java.lang.IndexOutOfBoundsException e2) {
            }

        }
    }

    public class sliderInflowListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            try {
                int index = slider_inflow.getValue();
                int roadI = network_entrances_comboBox.getSelectedIndex();

                int inputRefresh = getIntFromSliderInflow(index);
                re.getNetworkEntArray().get(roadI).setInputRefresh(inputRefresh);
            } catch (java.lang.IndexOutOfBoundsException e2) {

            }
        }
    }

    public class junctComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setStopLightSlider();
        }
    }

    public class entrComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            int num = network_entrances_comboBox.getSelectedIndex();
            try {
                setInflowSlider();
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {//actionPerformed 
//                conflicting with the comboBox being reset
                setNetworkEntrComboBox();
//                this.actionPerformed(arg0);
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
            setInflowSlider();
            setStopLightSlider();
        }
    }

    public void setStopLightSlider() {
        try {
            int num = junctions_comboBox.getSelectedIndex();
//                ArrayList<Junction> junctArray = re.getJunctArray();
            Junction junct = re.getJunctArray().get(num);
            int switchTime = junct.getSwitchTime();
            int index = getSliderStopFromInt(switchTime);
            slider_stoplight.setValue(index);

        } catch (java.lang.ArrayIndexOutOfBoundsException e3) {
            setNetworkEntrComboBox();
        }

    }

    public void setInflowSlider() {
        try {
            int roadI = network_entrances_comboBox.getSelectedIndex();
            int inputRefresh = re.getNetworkEntArray().get(roadI).getInputRefresh();
            int index = getSliderInflowFromInt(inputRefresh);
            slider_inflow.setValue(index);

        } catch (java.lang.ArrayIndexOutOfBoundsException e) {//actionPerformed 
            int oldIndex = slider_inflow.getValue();
//                conflicting with the comboBox being reset
            setNetworkEntrComboBox();
            slider_inflow.setValue(oldIndex);
//            setInflowSlider();
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
