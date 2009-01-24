package taskComission;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import environment.IDField;
import environment.PositionField;
/**
 * Window to use task commission Agent
 *
 */
public class TaskSendGUI extends JFrame
{
   private static final int WIDTH = 350;
   private static final int HEIGHT = 500;
   private static final int FIND = 1;
   private static final int GOTO = 2;
   
   private JTextArea raport;
   private IDField idField;
   private PositionField xField;
   private PositionField yField;
   private JPanel bottonPanel;
   private ButtonGroup group;
   private Box tasksPanel;
   private IDField choosenAgent;
   
   private ArrayList<TaskPanel> taskList;
   
   public TaskSendGUI()
   {
      setTitle("Task Comission");
      setSize(WIDTH, HEIGHT);
      setResizable(false);
      taskList = new ArrayList<TaskPanel>();
      tasksPanel = Box.createVerticalBox();
      
      Container content = getContentPane();
      content.add(tasksPanel,BorderLayout.CENTER);
      
      //choose task panel
      
      bottonPanel = new JPanel();
      group = new ButtonGroup();
      
      content.add(bottonPanel, BorderLayout.NORTH);
      
      //task panels
      JPanel whoPanel = new JPanel();
      createNamedBorder(whoPanel,"Choosen Agent");
      
      choosenAgent = new IDField(5);
      JLabel etyChId = new JLabel("Choosen Agent ID:");
      whoPanel.add(etyChId);
      whoPanel.add(choosenAgent);
      
      tasksPanel.add(whoPanel);
         
      //find task panel
      TaskPanel findPanel = new TaskPanel(FIND);
      createNamedBorder(findPanel,"Find Task");
      taskList.add(findPanel);
      
      addButton("Find Task", FIND);
      idField = new IDField(5);
      JLabel etyId = new JLabel("Object ID:");
      
      findPanel.add(etyId);
      findPanel.add(idField);
      
      JButton send = new JButton("Send");
      send.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            Integer agID = choosenAgent.pobierzWartosc();
            Integer obID = idField.pobierzWartosc();
            
            if(agID != null && obID != null)
            {
               //tu uruchomic odpowiednie behaviour
            }
         }
      });
      
      findPanel.add(send);
      
      tasksPanel.add(findPanel);
      
      //go to task panel
      TaskPanel goToPanel = new TaskPanel(GOTO);
      createNamedBorder(goToPanel,"Check Position Task");
      taskList.add(goToPanel);
      
      addButton("Check Position Task", GOTO);
      
      xField = new PositionField(4, 400);
      yField = new PositionField(4, 400);
      JLabel etyX = new JLabel("X position:");
      JLabel etyY = new JLabel("Y position:");
         
      goToPanel.add(etyX);
      goToPanel.add(xField);
      goToPanel.add(etyY);
      goToPanel.add(yField);
      
      JButton sendT = new JButton("Send");
      send.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            Integer agID = choosenAgent.pobierzWartosc();
            Integer xPos = xField.pobierzWartosc();
            Integer yPos = yField.pobierzWartosc();
            
            if(agID != null && xPos != null && yPos != null)
            {
               //tu uruchomic odpowiednie behaviour
            }
         }
      });
      
      goToPanel.add(sendT);
     
      goToPanel.setVisible(false);
      tasksPanel.add(goToPanel);
      
      // task reporting field
      raport = new JTextArea();
      raport.setEditable(false);
      JScrollPane scroll = new JScrollPane(raport);

      scroll.setMinimumSize(new Dimension(350,300));
      scroll.setPreferredSize(new Dimension(350,300));
      content.add(scroll, BorderLayout.SOUTH);
   }
   
   private void addButton(String nazwa, final int taskType)
   {
      boolean choosen = taskType == FIND;
      JRadioButton botton = new JRadioButton(nazwa, choosen);
      
      group.add(botton);
      bottonPanel.add(botton);
      
      ActionListener listener = new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
           for(int i=0; i< taskList.size(); i++)
           {
              TaskPanel panel = taskList.get(i);
              if (panel.getType() == taskType)
                 panel.setVisible(true);
              else panel.setVisible(false);              
           }
         }  
      };
      
      botton.addActionListener(listener);
   }
   
   public static void createNamedBorder(JPanel pan, String name)
   {
      Border bord = BorderFactory.createEtchedBorder();
      Border named = BorderFactory.createTitledBorder(bord, name);
      pan.setBorder(named);
   }
   
   synchronized void addTaskRaport(String r)
   {
      raport.append(r+"\n");
   }
}

class TaskPanel extends JPanel
{
   private int taskType;
   
   public TaskPanel(int type)
   {
      taskType = type;
   }
   
   public int getType()
   {
      return taskType;
   }
}