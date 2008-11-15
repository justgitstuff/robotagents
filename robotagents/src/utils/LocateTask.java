package utils;

import jade.content.AgentAction;

public class LocateTask extends Task implements AgentAction
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
