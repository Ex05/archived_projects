package de.janik.devils.light;

import de.janik.devils.utility.Color;

public class BaseLight
{
	private Color	color;
	private float	intensity;

	protected BaseLight(BaseLight light)
	{
		this(light.color, light.intensity);
	}

	public BaseLight(Color color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}

	//<-Getter & Setter->
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
}