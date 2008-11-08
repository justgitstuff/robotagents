package utils;

import jade.content.Concept;
import jade.content.Predicate;

public class EnvObject implements Concept
{
	protected int id, posX, posY;

	public EnvObject(int i, int x, int y)
	{
		id = i;
		posX = x;
		posY = y;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getId()
	{
		return id;
	}

	public int getPosX()
	{
		return posX;
	}

	public int getPosY()
	{
		return posY;
	}

}
