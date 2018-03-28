package de.janik.devils.utility;

import static de.janik.devils.utility.BufferTools.createFloatBufferDirect;

import java.nio.FloatBuffer;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Color
{
	/**
	 * The number of elements(floats) which form a color.
	 */
	public static final int		ELEMENTS	= 4;

	/**
	 * The color 'Red'.
	 */
	public static final Color	RED			= Color.decode("#FF_00_00_FF");

	/**
	 * The color 'Green'.
	 */
	public static final Color	GREEN		= Color.decode("#00_FF_00_FF");

	/**
	 * The color 'Blue'.
	 */
	public static final Color	BLUE		= Color.decode("#00_00_FF_FF");

	/**
	 * The color 'Orange'.
	 */
	public static final Color	ORANGE		= Color.decode("#FF_80_00_FF");

	/**
	 * The color ' Black'.
	 */
	public static final Color	BLACK		= Color.decode("#00_00_00_FF");

	/**
	 * The color 'Yellow'.
	 */
	public static final Color	YELLOW		= Color.decode("#FF_FF_00_FF");

	/**
	 * The color 'White'.
	 */
	public static final Color	WHITE		= Color.decode("#FF_FF_FF_FF");

	/**
	 * The color 'GRAY'.
	 */
	public static final Color	GRAY		= Color.decode("#33_33_33_FF");

	/**
	 * The buffer, our color value's are stored in.
	 */
	private FloatBuffer			buffer;

	/**
	 * The position of the single color's in the buffer.
	 */
	private static int			red			= 0, green = 1, blue = 2, alpha = 3;

	/**
	 * A RGB_A color representation, valid values are in range between 0.0 and
	 * 1.0.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	public Color(float red, float green, float blue, float alpha)
	{
		buffer = createFloatBufferDirect(red, green, blue, alpha);
	}

	public Color(float red, float green, float blue)
	{
		buffer = createFloatBufferDirect(red, green, blue, 1.0f);
	}

	/**
	 * A RGB_A color representation, valid values are in range between 0.0 and
	 * 1.0.
	 * 
	 * @param c
	 */
	public Color(Color c)
	{
		this(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	// <-toString->
	@Override
	public String toString()
	{
		return "Color [Red: " + 255 * getRed() + " Green: " + 255 * getGreen() + " Blue: " + 255 * getBlue() + " Alpha: " + 255 * getAlpha() + "]";
	}

	// <-Static->
	/**
	 * Convert's a color represented by 4 floating point values into a 32bit
	 * integer value.
	 * 
	 * @param color
	 * 
	 * @return The 128bit floating point value color representation, parsed into
	 *         an 32bit integer value.
	 */
	public static int ParsInt(Color color)
	{
		int red = (int) (255 * color.getRed());
		int green = (int) (255 * color.getGreen());
		int blue = (int) (255 * color.getBlue());
		int alpha = (int) (255 * color.getAlpha());

		int i = (red);

		i = i + (green << 8);
		i = i + (blue << 8);
		i = i + (alpha << 8);

		return i;
	}

	/**
	 * Convert's a color represented by a hex-string, into a usable object.
	 * 
	 * Valid input could be: ["#FF_80_00_FF"], ["ff00ff80"], ["#808080ff"],
	 * ["FF_ff_80_20"]
	 * 
	 * @param color
	 * 
	 * @return A color.
	 */
	public static Color decode(String color)
	{
		color = color.replaceAll("\\s", "");

		if (color.startsWith("#"))
			color = color.substring(1, color.length());

		color = color.replace("_", "");

		float red = convert(Integer.decode("#" + color.substring(0, 2)));
		float green = convert(Integer.decode("#" + color.substring(2, 4)));
		float blue = convert(Integer.decode("#" + color.substring(4, 6)));
		float alpha = convert(Integer.decode("#" + color.substring(6, 8)));

		return new Color(red, green, blue, alpha);
	}

	/**
	 * Converts from 0 - 255 color-space to 0.0 - 1.0 color-space
	 * 
	 * @param color
	 * @return
	 */
	private static float convert(float color)
	{
		if (color != 0)
			return color / 255;
		else
			return color;
	}

	// <-Getter & Setter->

	public float getRed()
	{
		buffer.position(red);
		return buffer.get();
	}

	public Color setRed(float red)
	{
		buffer.position(Color.red);
		buffer.put(red);

		return this;
	}

	public Color setGreen(float green)
	{
		buffer.position(Color.green);
		buffer.put(green);

		return this;
	}

	public Color setBlue(float blue)
	{
		buffer.position(Color.blue);
		buffer.put(red);

		return this;
	}

	public Color setAlpha(float alpha)
	{
		buffer.position(Color.alpha);
		buffer.put(red);

		return this;
	}

	public float getGreen()
	{
		buffer.position(green);
		return buffer.get();
	}

	public float getBlue()
	{
		buffer.position(blue);
		return buffer.get();
	}

	public float getAlpha()
	{
		buffer.position(alpha);
		return buffer.get();
	}

	public static java.awt.Color toAWT(Color color)
	{
		float red = color.getRed();
		float green = color.getGreen();
		float blue = color.getBlue();
		float alpha = color.getAlpha();

		return new java.awt.Color(red, green, blue, alpha);
	}

	public static Color randomRGB()
	{
		double random = Math.random();

		if (random < 0.33)
			return new Color(1, 0, 0, 1);
		else
			if (random < 0.66)
				return new Color(0, 1, 0, 1);
			else
				if (random < 1)
					return new Color(0, 0, 1, 1);
				else
					return null;
	}
}