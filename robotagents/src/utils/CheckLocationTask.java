package utils;

/** Check what agent can see in fixed location
 * 
 */
public class CheckLocationTask extends Task
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
