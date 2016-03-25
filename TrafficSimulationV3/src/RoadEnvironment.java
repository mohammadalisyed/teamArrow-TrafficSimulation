/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mohammad Ali
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
public class RoadEnvironment implements ActionListener, KeyListener
{

	public static RoadEnvironment re;
	public JFrame mainFrame;
	public CrossJunction dw;
	public Timer timer = new Timer(40, this);
	
	public ArrayList<Point> carSet = new ArrayList<Point>();
	public ArrayList<Point> carSet2 = new ArrayList<Point>();
	public ArrayList<Point> carSet3 = new ArrayList<Point>();
	
	public ArrayList<Point> carSet4 = new ArrayList<Point>();
	public ArrayList<Point> carSet5 = new ArrayList<Point>();
	public ArrayList<Point> carSet6 = new ArrayList<Point>();
	
	public Point goingUpFromLeft, goingRightFromLeft, goingDownFromLeft;
	public Point goingUpFromRight, goingLeftFromRight, goingDownFromRight;

	public boolean paused = false;
	public static final int UP = 8, DOWN = 2, LEFT = 4, RIGHT = 6, SCALE = 10;
	public int  isLeftLanedirection;
	public int carLength = 0;

	
	public RoadEnvironment()
	{
		mainFrame = new JFrame("Traffic Simulation App.");
		mainFrame.setVisible(true);
		mainFrame.setSize(1206, 1029);
		mainFrame.setResizable(false);
		mainFrame.add(dw = new CrossJunction());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addKeyListener(this);
		start();
	}
	public void start()
	{
		//laneRunningNow = RIGHT;
		//isLeftLanedirection = RIGHT;
		goingUpFromLeft = new Point(0, 35);
		goingRightFromLeft = new Point(0,40);
        goingDownFromLeft = new Point(0, 45);
        
        goingUpFromRight = new Point(105, 45);
        goingLeftFromRight = new Point(105, 50);
        goingDownFromRight = new Point(105, 55);
		
        carSet.clear();
        carSet2.clear();
        carSet3.clear();
        carSet4.clear();
        carSet5.clear();
        carSet6.clear();
		
		
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		dw.repaint();
		if (goingUpFromLeft != null && !paused)
		{
			carSet.add(new Point(goingUpFromLeft.x, goingUpFromLeft.y));
			carSet2.add(new Point(goingRightFromLeft.x, goingRightFromLeft.y));
			carSet3.add(new Point(goingDownFromLeft.x, goingDownFromLeft.y));
			
			carSet4.add(new Point(goingUpFromRight.x, goingUpFromRight.y));
			carSet5.add(new Point(goingLeftFromRight.x, goingLeftFromRight.y));
			carSet6.add(new Point(goingDownFromRight.x, goingDownFromRight.y));
			
			//cars on left lane going towards right junction activities					
			if(goingUpFromLeft.x == 50 && goingUpFromLeft.y == 35){
				while(goingUpFromLeft.y - 1 > -1){
				goingUpFromLeft = new Point(goingUpFromLeft.x, goingUpFromLeft.y - 1);
				}
			}else{
				goingUpFromLeft = new Point(goingUpFromLeft.x + 1, goingUpFromLeft.y);
			
			}
			
				goingRightFromLeft = new Point(goingRightFromLeft.x + 1, goingRightFromLeft.y);
			
				
			if(goingDownFromLeft.x == 50 && goingDownFromLeft.y == 45){
					while(goingDownFromLeft.y + 1 < 70){
					goingDownFromLeft = new Point(goingDownFromLeft.x, goingDownFromLeft.y + 1);
					}
			}else{
					goingDownFromLeft = new Point(goingDownFromLeft.x + 1, goingDownFromLeft.y);
				
				}
			
			//cars on right lane going towards left junction activities	
			if(goingUpFromRight.x == 65 && goingUpFromRight.y == 45){
				while(goingUpFromRight.y - 1 > -1){
				goingUpFromRight = new Point(goingUpFromRight.x, goingUpFromRight.y - 1);
				}
			}else{
				goingUpFromRight = new Point(goingUpFromRight.x - 1, goingUpFromRight.y);
			
			}
			
			
				goingLeftFromRight = new Point(goingLeftFromRight.x - 1, goingLeftFromRight.y);
			
			
				
			if(goingDownFromRight.x == 65 && goingDownFromRight.y == 55){
					while(goingDownFromRight.y + 1 < 70){
					goingDownFromRight = new Point(goingDownFromRight.x, goingDownFromRight.y + 1);
					}
			}else{
					goingDownFromRight = new Point(goingDownFromRight.x - 1, goingDownFromRight.y);
				
				}
				
			
			/*
                        
			if (goingUpFromLeft.y - 1 >= 0)
			{
				goingUpFromLeft = new Point(goingUpFromLeft.x, goingUpFromLeft.y - 1);
				goingRightFromLeft = new Point(goingRightFromLeft.x + 1, goingRightFromLeft.y);
				//goingRightFromLeft = new Point(goingRightFromLeft.x, goingRightFromLeft.y - 1);
				//goingDownFromRight = new Point(goingDownFromRight.x, goingDownFromRight.y - 1);

			}
			if (goingUpFromLeft.y + 1 < 100 &&  isLeftLanedirection == UP)
			{
				goingUpFromLeft = new Point(goingUpFromLeft.x, goingUpFromLeft.y + 1);
				goingRightFromLeft = new Point(goingRightFromLeft.x, goingRightFromLeft.y + 1);
				//goingDownFromRight = new Point(goingDownFromRight.x, goingDownFromRight.y + 1);
			}
			if (goingUpFromLeft.x - 1 >= 0 &&  isLeftLanedirection == UP)
			{
				goingUpFromLeft = new Point(goingUpFromLeft.x - 1, goingUpFromLeft.y);
				goingRightFromLeft = new Point(goingRightFromLeft.x - 1, goingRightFromLeft.y);
				//goingDownFromRight = new Point(goingDownFromRight.x - 1, goingDownFromRight.y);
			}
			
			if (goingUpFromLeft.x + 1 < 120 &&  isLeftLanedirection == RIGHT)
			{
				goingUpFromLeft = new Point(goingUpFromLeft.x + 1, goingUpFromLeft.y);
				goingRightFromLeft = new Point(goingRightFromLeft.x + 1, goingRightFromLeft.y);
				//goingDownFromRight = new Point(goingDownFromRight.x + 1, goingDownFromRight.y);
			}
			*/
			
			//remove trails 
			if (carSet.size() > carLength)
				carSet.remove(0);
			if (carSet2.size() > carLength)
				carSet2.remove(0);
			if (carSet3.size() > carLength){
				carSet3.remove(0);
			}
			if (carSet4.size() > carLength)
				carSet4.remove(0);
			if (carSet5.size() > carLength)
				carSet5.remove(0);
			if (carSet6.size() > carLength){
				carSet6.remove(0);
			}
			
		}
	}
	public boolean noCarAt(int x, int y)
	{
		for (Point point : carSet)
			if (point.equals(new Point(x, y)))
				return false;
		for (Point point : carSet2)
			if (point.equals(new Point(x, y)))
				return false;
		
		for (Point point : carSet3)
			if (point.equals(new Point(x, y)))
				return false;
		
		for (Point point : carSet4)
			if (point.equals(new Point(x, y)))
				return false;
		
		for (Point point : carSet5)
			if (point.equals(new Point(x, y)))
				return false;
		
		for (Point point : carSet6)
			if (point.equals(new Point(x, y)))
				return false;
		
		return true;
	}
	
	public static void main(String[] args)
	{
		re = new RoadEnvironment();	
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && isLeftLanedirection != RIGHT)
			isLeftLanedirection = LEFT;
		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && isLeftLanedirection != LEFT)
			isLeftLanedirection = RIGHT;
		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && isLeftLanedirection != DOWN)
			isLeftLanedirection = UP;
		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && isLeftLanedirection != UP)
			isLeftLanedirection = DOWN;
		if (i == KeyEvent.VK_SPACE)
			start();
		if(i== KeyEvent.VK_ENTER)
			paused = !paused;
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}