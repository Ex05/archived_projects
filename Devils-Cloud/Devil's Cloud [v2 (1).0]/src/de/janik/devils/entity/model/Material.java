package de.janik.devils.entity.model;

import de.janik.devils.texture.Texture;
import de.janik.devils.utility.Color;

public class Material
{
	private Texture	texture;
	private Color	color;

	private float	ambienLightModifyer	= 1;
	private boolean	lightingEnabled;

	private float	specularIntensity;
	private float	specularPower;

	private boolean	hasTexture;

	public Material()
	{
		this(Color.WHITE, true, 1, 8);
	}

	public Material(Color color, boolean lightingEnabled, float specularIntensity, float specularExponent)
	{
		this(null, color, lightingEnabled, 1);

		this.specularIntensity = specularIntensity;
		this.specularPower = specularExponent;
		hasTexture = false;
	}

	public Material(Texture texture, boolean lightingEnabled)
	{
		this(texture, Color.WHITE, lightingEnabled, 1);
	}

	public Material(Texture texture, boolean lightingEnabled, float ambientLightLevel)
	{
		this(texture, Color.WHITE, lightingEnabled, ambientLightLevel);
	}

	public Material(Texture texture, Color color, boolean lightingEnabled, float ambientLightLevel)
	{
		this.texture = texture;
		hasTexture = true;

		this.color = color;
		this.lightingEnabled = lightingEnabled;
		this.ambienLightModifyer = ambientLightLevel;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public Material setTexture(Texture texture)
	{
		this.texture = texture;

		hasTexture = true;

		return this;
	}

	public Color getColor()
	{
		return color;
	}

	public Material setColor(Color color)
	{
		this.color = color;

		return this;
	}

	public boolean hasTexture()
	{
		return hasTexture;
	}

	public boolean isLightingEnabled()
	{
		return lightingEnabled;
	}

	public void setLightingEnabled(boolean enabled)
	{
		this.lightingEnabled = enabled;
	}

	public float getAmbienLightModifyer()
	{
		return ambienLightModifyer;
	}

	public void setAmbienLightModifyer(float ambienLightModifyer)
	{
		this.ambienLightModifyer = ambienLightModifyer;
	}

	public float getSpecularIntensity()
	{
		return specularIntensity;
	}

	public void setSpecularIntensity(float specularIntensity)
	{
		this.specularIntensity = specularIntensity;
	}

	public float getSpecularExponent()
	{
		return specularPower;
	}

	public void setSpecularExponent(float specularExponent)
	{
		this.specularPower = specularExponent;
	}

}
