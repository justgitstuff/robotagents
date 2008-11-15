package utils;

import java.util.Date;

public class Fact extends EnvObject
{
	public Fact()
   {
      super();
   }

   public Fact(int id, int posX, int posY, Date time)
   {
      super(id, posX, posY);
      this.time = time;
   }

   protected Date time;

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}


}
