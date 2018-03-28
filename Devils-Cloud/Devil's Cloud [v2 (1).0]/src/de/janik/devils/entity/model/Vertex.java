package de.janik.devils.entity.model;

import de.janik.devils.math.Normal;
import de.janik.devils.math.Vector;

public class Vertex
{
	// 3 position, 2 texCoord, 3 normal
	public static final int		ELEMENTS	= 8;

	private Vector				position;
	private TextureCoordinate	texCoord;
	private Normal				normal;

	public Vertex(Vector position)
	{
		this(position, new TextureCoordinate(0, 0));
	}

	public Vertex(Vector position, TextureCoordinate texCoord)
	{
		this(position, texCoord, new Normal());
	}

	public Vertex(Vector position, TextureCoordinate texCoord, Normal normal)
	{
		this.position = position;
		this.texCoord = texCoord;
		this.normal = normal;
	}

	public Vector getPosition()
	{
		return position;
	}

	public void setPosition(Vector position)
	{
		this.position = position;
	}

	public TextureCoordinate getTexCoord()
	{
		return texCoord;
	}

	public void setTexCoord(TextureCoordinate texCoord)
	{
		this.texCoord = texCoord;
	}

	public Normal getNormal()
	{
		return normal;
	}

	public void setNormal(Normal normal)
	{
		this.normal = normal;
	}
}
