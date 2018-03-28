package de.janik.devils.light;

import de.janik.devils.math.Vector;
import de.janik.devils.utility.Color;

public class DirectionalLight extends BaseLight
{
	private Vector	direction;

	public DirectionalLight(BaseLight base, Vector direction)
	{
		super(base);
		this.direction = direction.normalize();
	}

	public DirectionalLight(Color color, float intensit, Vector direction)
	{
		this(new BaseLight(color, intensit), direction);
	}

	//<-Getter & Setter->
	public Vector getDirection()
	{
		return direction;
	}

	public void setDirection(Vector direction)
	{
		this.direction = direction;
	}
}