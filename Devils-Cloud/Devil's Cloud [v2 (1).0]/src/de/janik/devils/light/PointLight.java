package de.janik.devils.light;

import de.janik.devils.math.Vector;

public class PointLight extends BaseLight
{
	private Vector		position;
	private Attenuation	attenuation;
	private float		range;

	public PointLight(BaseLight base, Vector position, Attenuation attenuation, float range)
	{
		super(base);
		this.position = position;
		this.attenuation = attenuation;
		this.setRange(range);
	}

	public Vector getPosition()
	{
		return position;
	}

	public void setPosition(Vector positionVector)
	{
		this.position = positionVector;
	}

	public Attenuation getAttenuation()
	{
		return attenuation;
	}

	public void setAttenuation(Attenuation attenuation)
	{
		this.attenuation = attenuation;
	}

	public float getRange()
	{
		return range;
	}

	public void setRange(float range)
	{
		this.range = range;
	}

}
