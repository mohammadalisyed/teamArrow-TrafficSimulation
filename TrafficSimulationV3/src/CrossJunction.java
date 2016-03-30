/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class CrossJunction extends JPanel {
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		RoadEnvironment re = RoadEnvironment.re;

		// Whole Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1200, 1000);

		g.setColor(Color.BLUE);

		for (Point point : re.carSet)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y
					* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
					RoadEnvironment.SCALE);
		
		g.fillRect(re.goingUpFromLeft.x * re.SCALE, re.goingUpFromLeft.y
				* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
				RoadEnvironment.SCALE);
		
		for (Point point : re.carSet2)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y
					* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
					RoadEnvironment.SCALE);
		
		g.fillRect(re.goingRightFromLeft.x * re.SCALE, re.goingRightFromLeft.y * RoadEnvironment.SCALE,
				RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		
		for (Point point : re.carSet3)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y
					* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
					RoadEnvironment.SCALE);
		g.fillRect(re.goingDownFromLeft.x * re.SCALE, re.goingDownFromLeft.y * RoadEnvironment.SCALE,
				RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		
		for (Point point : re.carSet4)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y
					* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
					RoadEnvironment.SCALE);
		g.fillRect(re.goingUpFromRight.x * re.SCALE, re.goingUpFromRight.y * RoadEnvironment.SCALE,
				RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		
		for (Point point : re.carSet5)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y
					* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
					RoadEnvironment.SCALE);
		g.fillRect(re.goingLeftFromRight.x * re.SCALE, re.goingLeftFromRight.y * RoadEnvironment.SCALE,
				RoadEnvironment.SCALE, RoadEnvironment.SCALE);
		
		for (Point point : re.carSet6)
			g.fillRect(point.x * RoadEnvironment.SCALE, point.y
					* RoadEnvironment.SCALE, RoadEnvironment.SCALE,
					RoadEnvironment.SCALE);
		g.fillRect(re.goingDownFromRight.x * re.SCALE, re.goingDownFromRight.y * RoadEnvironment.SCALE,
				RoadEnvironment.SCALE, RoadEnvironment.SCALE);




	
		// drawing of crossjunction rectangles
		g.setColor(Color.black);
		g.drawRect(500, 000, 200, 999);
		g.drawRect(0, 350, 1199, 200);

		this.setSize(1200, 1000);

	}
}