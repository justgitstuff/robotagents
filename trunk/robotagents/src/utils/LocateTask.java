package utils;

public class LocateTask extends Task
{
	protected int objectId;

	public LocateTask()
	{
	   super();
	}

	public LocateTask(int empid, int pr, int id)
	{
		super(empid, pr);
		objectId = id;
	}

	public int getObjectId()
	{
		return objectId;
	}

	public void setObjectId(int objectId)
	{
	   this.objectId = objectId;
	}
}
