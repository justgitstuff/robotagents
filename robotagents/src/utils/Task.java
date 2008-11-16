package utils;

import jade.content.Concept;

public class Task implements Concept
{
   protected int employerId, priority;

   public Task()
   {
      super();
   }

   public Task(int empid, int pr)
   {
      priority = pr;
      employerId = empid;
   }

   public int getEmployerId()
   {
      return employerId;
   }

   public void setEmployerId(int employerId)
   {
      this.employerId = employerId;
   }

   public int getPriority()
   {
      return priority;
   }

   public void setPriority(int priority)
   {
      this.priority = priority;
   }

}
