package de.janik.devils.entity;

import de.janik.devils.math.Vector;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public abstract class Entity
{
	protected boolean				visible;

	protected TransformationObject	tfo;

	protected Entity()
	{
		this(new Vector());
	}

	protected Entity(Vector position)
	{
		tfo = new TransformationObject(position);
	}

	// Abstract
	public abstract void render();

	public abstract void tick();

	public abstract void destroy();

	public void rotate(float x, float y, float z)
	{
		tfo.rotate(x, y, z);
	}

	public void scale(float amount)
	{
		tfo.scale(amount);
	}

	public void translate(float x, float y, float z)
	{
		tfo.translate(x, y, z);
	}

	public void translate(Vector v)
	{
		tfo.translate(v);
	}

	// <-Getter & Setter->
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public TransformationObject getTransformationObject()
	{
		return tfo;
	}
}
