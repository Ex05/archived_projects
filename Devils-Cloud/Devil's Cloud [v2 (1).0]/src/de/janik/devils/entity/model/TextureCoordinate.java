package de.janik.devils.entity.model;

public class TextureCoordinate
{
	/**
	 * The number of elements(floats) which form a textureCoordinate.
	 */
	public static final int	ELEMENTS	= 2;

	private float			u, v;

	public TextureCoordinate(float u, float v)
	{
		this.u = u;
		this.v = v;
	}

	public float getU()
	{
		return u;
	}

	public void setU(float u)
	{
		this.u = u;
	}

	public float getV()
	{
		return v;
	}

	public void setV(float v)
	{
		this.v = v;
	}

}