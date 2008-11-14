package taskComission;

import javax.swing.JFrame;
import javax.swing.JLabel;

import environment.IDField;
import environment.PositionField;

public class TaskSenterTesting
{   
   public static void main(String [] args)
   {
      TaskSendGUI frame = new TaskSendGUI();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
      frame.addTaskRaport("GUI ready to use");

   }
   
}
