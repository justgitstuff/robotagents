package environment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;

import utils.EnvObject;

/**
 * Class containing all GUI Elements
 *
 * @author Maciek
 */
public class ActionFrame extends JFrame
{
   private EnvPanel panel;
   private IDField idField;
   private PositionField xField;
   private PositionField yField;

   /**
    * Preparing Frame with environment to shown
    *
    * @param width
    *           environment width
    * @param height
    *           environment height
    */
   public ActionFrame(int width, int height, ArrayList<EnvObject> robs,
         ArrayList<EnvObject> objs)
   {
      // Preparing frame for showing environment
      setTitle("Agents Environment");
      setSize(width + 50, height + 150);
      setResizable(false);

      // Adding panel with agents and objects positions
      panel = new EnvPanel(objs, robs); // panel which shows environment
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
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BorderLayout());

      // adding new environment object section
      idField = new IDField(5);
      xField = new PositionField(4, width);
      yField = new PositionField(4, height);
      JLabel etyId = new JLabel("ID:");
      JLabel etyX = new JLabel("X position:");
      JLabel etyY = new JLabel("Y position:");

      // adding section border
      JPanel newObjectPanel = new JPanel();
      Border bord = BorderFactory.createEtchedBorder();
      newObjectPanel.setBorder(BorderFactory.createTitledBorder(bord,
            "New environment object"));

      newObjectPanel.add(etyId);
      newObjectPanel.add(idField);
      newObjectPanel.add(etyX);
      newObjectPanel.add(xField);
      newObjectPanel.add(etyY);
      newObjectPanel.add(yField);

      // adding Add button to section
      JButton addObj = new JButton("Add");
      newObjectPanel.add(addObj);
      addObj.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent zd)
         {
            Integer id = idField.pobierzWartosc();
            Integer x = xField.pobierzWartosc();
            Integer y = yField.pobierzWartosc();

            if (id != null && x != null && y != null)
            {
               EnvObject newObj = new EnvObject(id, x, y);
               panel.addEnvObject(newObj);
               repaint();
            }
            // tu wywolana byc powinna funkcja dodajca obiekt od env
         }
      });

      buttonPanel.add(newObjectPanel, BorderLayout.NORTH);

      // Close button
      JButton koniec = new JButton("Close");
      JPanel bPanel = new JPanel();
      bPanel.add(koniec);
      buttonPanel.add(bPanel);
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
    * @param newObjects
    *           new objects list
    * @param newAgents
    *           new agents list
    */
   public void upgrade(ArrayList<EnvObject> newObjects,
         ArrayList<EnvObject> newAgents)
   {
      panel.upgrade(newObjects, newAgents);
   }

   public void reDraw()
   {
      repaint();
   }

   // *********************************************************************
   /**
    * Class containing graphic representation of environment.
    *
    * @author Maciek
    */
   class EnvPanel extends JPanel
   {
      private static final double PWIDTH = 4;
      private static final double PHIGHT = 4;

      private ArrayList<EnvObject> envObjects;
      private ArrayList<EnvObject> agents;
      private EnvObject actual;

      /**
       * Creates panel with empty object and agent lists
       */
      public EnvPanel()
      {
         envObjects = new ArrayList<EnvObject>();
         agents = new ArrayList<EnvObject>();

         actual = null;

         addMouseMotionListener(new ObjectMoveListener());
         addMouseListener(new ObjectClickedListener());
      }

      /**
       * Creates panel with given objects and agents lists
       *
       * @param newObjects
       *           objects list
       * @param newAgents
       *           agents list
       */
      public EnvPanel(ArrayList<EnvObject> newObjects,
            ArrayList<EnvObject> newAgents)
      {
         envObjects = newObjects;
         agents = newAgents;

         actual = null;

         addMouseMotionListener(new ObjectMoveListener());
         addMouseListener(new ObjectClickedListener());
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
            g2.drawString(new Integer(object.getId()).toString(), centX + 5,
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
                  - PHIGHT / 2, PWIDTH, PHIGHT); // preparing agent symbol
            Ellipse2D circle = new Ellipse2D.Double(centX - 10 * PWIDTH / 2,
                  centY - 10 * PHIGHT / 2, 10 * PWIDTH, 10 * PHIGHT); 
            Ellipse2D circle2 = new Ellipse2D.Double(centX - 50 * PWIDTH / 2,
                  centY - 50 * PHIGHT / 2, 50 * PWIDTH, 50 * PHIGHT);// painting
            // signal
            // coverage
            g2.setPaint(Color.BLUE);
            g2.fill(rect);
            g2.draw(rect);
            g2.draw(circle);
            g2.drawString(new Integer(object.getId()).toString(), centX + 5,
                  centY); // writing agent id
            g2.setPaint(Color.GREEN);
            g2.draw(circle2);
         }
      }

      /**
       * Upgrades data which will be shown in panel
       *
       * @param newObjects
       *           new objects list
       * @param newAgents
       *           new agents list
       */
      public void upgrade(ArrayList<EnvObject> newObjects,
            ArrayList<EnvObject> newAgents)
      {
         envObjects = newObjects;
         agents = newAgents;
      }

      public void addEnvObject(EnvObject newObj)
      {
         envObjects.add(newObj);
      }

      public EnvObject find(Point2D p)
      {
         for (int i = 0; i < envObjects.size(); i++)
         {
            EnvObject o = envObjects.get(i);

            int centX = o.getPosX();
            int centY = o.getPosY();

            Rectangle2D r = new Rectangle2D.Double(centX - PWIDTH / 2, centY
                  - PHIGHT / 2, PWIDTH, PHIGHT);
            if (r.contains(p))
               return o;
         }
         return null;
      }

      private class ObjectClickedListener extends MouseAdapter
      {
         public void mousePressed(MouseEvent event)
         {
            actual = find(event.getPoint());
         }
      }

      private class ObjectMoveListener implements MouseMotionListener
      {
         public void mouseDragged(MouseEvent event)
         {
            if (actual != null)
            {
               actual.setPosX(event.getX());
               actual.setPosY(event.getY());
               reDraw();
            }
         }

         public void mouseMoved(MouseEvent event)
         {
            if (find(event.getPoint()) == null)
               setCursor(Cursor.getDefaultCursor());
            else
               setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
         }
      }
   }
}
