package de.janik.devils.math;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Normal extends Vector
{
	public Normal(Vector v1, Vector v2)
	{
		this(Normal(v1, v2));
	}

	public Normal(Vector v)
	{
		super(v);
	}

	public Normal(float x, float y, float z)
	{
		super(x, y, z);
	}

	public Normal()
	{
		this(0, 0, 0);
	}

	@Override
	public Normal normalize()
	{
		return (Normal) super.normalize();
	}

	@Override
	public Normal add(Vector v)
	{
		return (Normal) super.add(v);
	}
}
