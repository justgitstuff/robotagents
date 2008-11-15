package utils;

import jade.content.AgentAction;

public class CheckLocationTask extends Task implements AgentAction
{
	protected int posX, posY;

	public CheckLocationTask()
	{
	   super();
	}

	public CheckLocationTask(int empid, int pr, int x, int y)
	{
		super(empid, pr);
		posX = x;
		posY = y;
	}

	public int getPosX()
	{
		return posX;
	}

	public int getPosY()
	{
		return posY;
	}

	public void setPosX(int posX)
	{
	   this.posX = posX;
	}

	public void setPosY(int posY)
   {
      this.posY = posY;
   }
}
