package environment;

import java.util.ArrayList;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import utils.EnvObject;

public class PaintingTest
{
	public static void main(String[] args) 
	{
		ActionFrame frame = new ActionFrame(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
		
		ArrayList<EnvObject> newObjects = new ArrayList<EnvObject>();
		newObjects.add(new EnvObject(1,50,50));
		newObjects.add(new EnvObject(2,100,100));
		newObjects.add(new EnvObject(3,50,200));
		newObjects.add(new EnvObject(7,400,400));
		
		ArrayList<EnvObject> newAgents = new ArrayList<EnvObject>();
		newAgents.add(new EnvObject(4,250,250));
		newAgents.add(new EnvObject(5,45,90));
		newAgents.add(new EnvObject(6,100,60));
		
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace(); 
		}
		
		frame.upgrade(newObjects, newAgents);
		frame.repaint();
	}
}

