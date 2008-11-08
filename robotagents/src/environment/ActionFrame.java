package environment;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import utils.EnvObject;

public class ActionFrame extends JFrame
{
	EnvPanel panel;

	public ActionFrame(int width, int high)
	{
		setTitle("Agents Environment");
		setSize(width, high);

		panel = new EnvPanel();
		Container content = getContentPane();
		content.add(panel);
	}

	public void rePaint()
	{
		panel.repaint();
	}
	
	public void upgrade(ArrayList<EnvObject> newObjects,
			ArrayList<EnvObject> newAgents)
	{
		panel.upgrade(newObjects, newAgents);
	}
}

class EnvPanel extends JPanel
{
	private static double WIDTH = 4;
	private static double HIGHT = 4;

	private ArrayList<EnvObject> envObjects;
	private ArrayList<EnvObject> agents;
	
	public EnvPanel()
	{
		envObjects = new ArrayList<EnvObject>();
		agents = new ArrayList<EnvObject>();
	}
	
	public EnvPanel(ArrayList<EnvObject> newObjects,
			ArrayList<EnvObject> newAgents)
	{
		envObjects = newObjects;
		agents = newAgents;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		Font arial5 = new Font("Arial", Font.BOLD, 10);
		g2.setFont(arial5);

		Iterator<EnvObject> iter = envObjects.iterator();

		while (iter.hasNext())
		{
			EnvObject object = iter.next();

			int centX = object.getPosX();
			int centY = object.getPosY();

			Ellipse2D circle = new Ellipse2D.Double(centX - WIDTH / 2, centY
					- HIGHT / 2, WIDTH, HIGHT);
			g2.setPaint(Color.RED);
			g2.fill(circle);
			g2.draw(circle);
			g2.drawString(new Integer(object.getId()).toString(), centX+5, centY);
		}

		iter = agents.iterator();

		while (iter.hasNext())
		{
			EnvObject object = iter.next();

			int centX = object.getPosX();
			int centY = object.getPosY();

			Rectangle2D rect = new Rectangle2D.Double(centX - WIDTH / 2, centY
					- HIGHT / 2, WIDTH, HIGHT);
			g2.setPaint(Color.BLUE);
			g2.fill(rect);
			g2.draw(rect);
			g2.drawString(new Integer(object.getId()).toString(), centX+5, centY);
		}
	}
	
	public void upgrade(ArrayList<EnvObject> newObjects,
			ArrayList<EnvObject> newAgents)
	{
		envObjects = newObjects;
		agents = newAgents;
	}
}