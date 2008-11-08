package utils;

import java.util.Date;

public class Fact extends EnvObject
{
	protected Date time;

	public Fact(int i, int x, int y, Date t)
	{
		super(i, x, y);
		time = t;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}


}
