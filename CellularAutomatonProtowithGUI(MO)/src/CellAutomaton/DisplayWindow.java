/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CellAutomaton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;
import javax.swing.JPanel;
import java.util.ArrayList;

public class DisplayWindow extends JPanel {

    private final Color FORWARDGREEN = new Color(0, 204, 0);
    private final Color BACKWARDGREEN = new Color(0, 51, 0);

    private final Color FORWARDRED = new Color(204, 0, 0);
    private final Color BACKWARDRED = new Color(51, 0, 0);

    private final int STOPLIGHTCOLOUR = 0, LANECOLOUR = 1;
    
    private int colourScheme;
    private RoadEnvironment re;

    public DisplayWindow(RoadEnvironment re) {
        this.re = re;
        colourScheme = 0;
    }

    public void colourPicker(Vehicle car, RoadInt road, Graphics2D g2) {
        switch (colourScheme) {
            case LANECOLOUR:
                switch (car.getDirection()) {
                    case RoadEnvironment.LEFT:
                        g2.setColor(Color.BLUE);
                        break;
                    case RoadEnvironment.RIGHT:
                        g2.setColor(Color.CYAN);
                        break;
                    case RoadEnvironment.UP:
                        g2.setColor(Color.MAGENTA);
                        break;
                    case RoadEnvironment.DOWN:
                        g2.setColor(Color.black);
                        break;
                }
                break;

            case STOPLIGHTCOLOUR:

                try {
                    if (road.getStopLight()) {
                        switch (car.getDirection()) {
                            case RoadEnvironment.RIGHT:
                            case RoadEnvironment.UP:
                                g2.setColor(FORWARDRED);
                                break;
                            case RoadEnvironment.DOWN:
                            case RoadEnvironment.LEFT:
                                g2.setColor(BACKWARDRED);
                                break;
                        }
                    } else {
                        switch (car.getDirection()) {
                            case RoadEnvironment.RIGHT:
                            case RoadEnvironment.UP:
                                g2.setColor(FORWARDGREEN);
                                break;
                            case RoadEnvironment.DOWN:
                            case RoadEnvironment.LEFT:
                                g2.setColor(BACKWARDGREEN);
                                break;
                        }
                    }
                    break;
                } catch (NullPointerException e) {
                    System.out.println("null");
                    switch (car.getDirection()) {
                        case RoadEnvironment.RIGHT:
                        case RoadEnvironment.UP:
                            g2.setColor(FORWARDGREEN);
                            break;
                        case RoadEnvironment.DOWN:
                        case RoadEnvironment.LEFT:
                            g2.setColor(BACKWARDGREEN);
                            break;
                    }
                }
                break;
        }
    }
    
    public void paintFrame(Graphics g){
//        RoadEnvironment re = RoadEnvironment;

        ArrayList<OneWayRoad> roadArray = re.getRoadNetwork().getRoadArray();
        ArrayList<Junction> junctArray = re.getRoadNetwork().getJunctArray();

        Graphics2D g2D = (Graphics2D) g.create();
        
//        AffineTransform t = new AffineTransform();
//        t.scale(RoadEnvironment.SCALE, RoadEnvironment.SCALE);
        
        g2D.scale(RoadEnvironment.SCALE, RoadEnvironment.SCALE);
        
        g2D.setStroke(new BasicStroke(0.01f));

        for (OneWayRoad road : roadArray) {

            g2D.setColor(Color.BLACK);
            g2D.drawRect(road.getX(), road.getY(), road.getRoadXLen(), road.getRoadYLen());
            
//            String roadLabel = road.getRoadName();
            
//            if (roadLabel != null){                
//                AttributedString label = new AttributedString(roadLabel);
//                label.addAttribute(TextAttribute.SIZE, 2);
//                g2D.drawString(label.getIterator(), road.getX()+2, road.getY());
//            }

            ArrayList<Vehicle> carLst = road.getVehicleLst();
            for (Vehicle car : carLst) {
                int x = car.getLocation().x;
                int y = car.getLocation().y;

                colourPicker(car, road, g2D);

                if (car.getTurning()) {
                    g2D.fillRect(x, y, 1, 1);
                } else {
                    g2D.drawRect(x, y, 1, 1);
                }
            }
        }

        for (Junction junct : junctArray) {
            g2D.setColor(Color.BLACK);
            g2D.drawRect(junct.getX(), junct.getY(), junct.getRoadXLen(), junct.getRoadYLen());

            ArrayList<Vehicle> carLst = junct.getVehicleLst();
            for (Vehicle car : carLst) {
                int x = car.getLocation().x;
                int y = car.getLocation().y;

                switch (car.getDirection()) {
                    case RoadEnvironment.RIGHT:
                    case RoadEnvironment.UP:
                        g2D.setColor(FORWARDGREEN);
                        break;
                    case RoadEnvironment.DOWN:
                    case RoadEnvironment.LEFT:
                        g2D.setColor(BACKWARDGREEN);
                        break;
                }

                colourPicker(car, junct, g2D);

                if (car.getTurning()) {
                    g2D.fillRect(x, y, 1, 1);
                } else {
                    g2D.drawRect(x, y, 1, 1);
                }

            }
        }
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintFrame(g);    
    }
}
