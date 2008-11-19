package robot;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FoundIt extends JFrame
{
   public FoundIt(String text)
   {
      setTitle("Task Completed!!");
      setSize(200, 100);

      Container content = getContentPane();
      Box panel = Box.createVerticalBox();
      JLabel ety = new JLabel(text);
      panel.add(ety);
      content.add(ety);
   }
}
