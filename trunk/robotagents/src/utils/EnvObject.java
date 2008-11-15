package utils;

import jade.content.Concept;
import jade.content.Predicate;

public class EnvObject implements Concept
{
   protected int id, posX, posY;

   public EnvObject()
   {
      super();
   }

	public EnvObject(int id, int posX, int posY)
   {
      this.id = id;
      this.posX = posX;
      this.posY = posY;
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
