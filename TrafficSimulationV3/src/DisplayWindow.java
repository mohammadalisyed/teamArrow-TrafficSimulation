/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

public class DisplayWindow extends JPanel
{
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
                
				//cross junction road
                g.drawRect(0, 250, 600, 100);
                g.drawRect(250, 50, 100, 600);
		
		RoadEnvironment re = RoadEnvironment.re;
		g.setColor(Color.WHITE);
		g.setColor(Color.BLUE);

		/*for (Point point : re.carSet)
                    //for( int x = 0; x <= 250; x++){
			g.fillRect(point.x + RoadEnvironment.SCALE, point.y + RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
                    //}    
                    for (Point point : re.carSet2)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y * RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		
		
                
                g.fillRect(re.skyline.x + RoadEnvironment.SCALE, re.skyline.y + RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		g.fillRect(re.veyron.x * RoadEnvironment.SCALE, re.veyron.y * RoadEnvironment.SCALE, RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		
	}*/
	}
}	