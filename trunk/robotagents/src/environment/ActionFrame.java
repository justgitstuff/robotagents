package environment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

import javax.swing.*;

import utils.EnvObject;

/**
 * Class containing all GUI Elements
 * 
 * @author Maciek
 */
public class ActionFrame extends JFrame
{
   private EnvPanel panel;
   private JPanel buttonPanel;

   /**
    * Preparing Frame with environment to shown
    * @param width environment width
    * @param height environment height
    */
   public ActionFrame(int width, int height)
   {
      // Preparing frame for showing environment
      setTitle("Agents Environment");
      setSize(width + 50, height + 100);
      setResizable(false);

      // Adding panel with agents and objects positions
      panel = new EnvPanel(); // panel which shows environment
      panel.setBorder(BorderFactory.createEtchedBorder());

      JPanel pane = new JPanel(); // panel, which sets environment not resize
                                    // able
      pane.setLayout(null);
      panel.setBounds(0, 0, width, height);

      pane.setMinimumSize(new Dimension(width, height));
      pane.setMaximumSize(new Dimension(width, height));
      pane.add(panel);

      Box pan = Box.createVerticalBox(); // box which centers pane
      pane.setMinimumSize(new Dimension(width, height));
      pan.add(pane);

      Container content = getContentPane();
      content.add(pan, BorderLayout.CENTER);

      // Adding panel with buttons
      buttonPanel = new JPanel();
      JButton koniec = new JButton("Zakoñcz");
      buttonPanel.add(koniec);
      koniec.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent zd)
         {
            System.exit(0);
         }
      });

      content.add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
    * Upgrades data which will be shown in environment
    * 
    * @param newObjects new objects list
    * @param newAgents new agents list
    */
   public void upgrade(ArrayList<EnvObject> newObjects,
         ArrayList<EnvObject> newAgents)
   {
      panel.upgrade(newObjects, newAgents);
   }
}

/**
 * Class containing graphic representation of environment.
 * 
 * @author Maciek
 */
class EnvPanel extends JPanel
{
   private static double PWIDTH = 4;
   private static double PHIGHT = 4;

   private ArrayList<EnvObject> envObjects;
   private ArrayList<EnvObject> agents;

   /**
    * Creates panel with empty object and agent lists
    */
   public EnvPanel()
   {
      envObjects = new ArrayList<EnvObject>();
      agents = new ArrayList<EnvObject>();
   }

   /**
    * Creates panel with given objects and agents lists
    * @param newObjects objects list
    * @param newAgents agents list
    */
   public EnvPanel(ArrayList<EnvObject> newObjects,
         ArrayList<EnvObject> newAgents)
   {
      envObjects = newObjects;
      agents = newAgents;
   }

   /**
    * Method used to paint component, used automatically when repaint method
    * used.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponents(g);
      Graphics2D g2 = (Graphics2D) g;
      Font arial5 = new Font("Arial", Font.BOLD, 10);
      g2.setFont(arial5);

      // painting all objects
      Iterator<EnvObject> iter = envObjects.iterator();

      while (iter.hasNext())
      {
         EnvObject object = iter.next();

         int centX = object.getPosX();
         int centY = object.getPosY();

         Ellipse2D circle = new Ellipse2D.Double(centX - PWIDTH / 2, centY
               - PHIGHT / 2, PWIDTH, PHIGHT);
         g2.setPaint(Color.RED);
         g2.fill(circle);
         g2.draw(circle);
         g2
               .drawString(new Integer(object.getId()).toString(), centX + 5,
                     centY);
      }

      // painting all agents
      iter = agents.iterator();

      while (iter.hasNext())
      {
         EnvObject object = iter.next();

         int centX = object.getPosX();
         int centY = object.getPosY();

         Rectangle2D rect = new Rectangle2D.Double(centX - PWIDTH / 2, centY
               - PHIGHT / 2, PWIDTH, PHIGHT); //preparing agent symbol
         Ellipse2D circle = new Ellipse2D.Double(centX - 10 * PWIDTH / 2, centY
               - 10 * PHIGHT / 2, 10 * PWIDTH, 10 * PHIGHT); // painting signal coverage
         g2.setPaint(Color.BLUE);
         g2.fill(rect);
         g2.draw(rect);
         g2.draw(circle);
         g2
               .drawString(new Integer(object.getId()).toString(), centX + 5,
                     centY); //writing agent id
      }
   }

   /**
    * Upgrades data which will be shown in panel
    * 
    * @param newObjects new objects list
    * @param newAgents new agents list
    */
   public void upgrade(ArrayList<EnvObject> newObjects,
         ArrayList<EnvObject> newAgents)
   {
      envObjects = newObjects;
      agents = newAgents;
   }
}