package de.janik.devils.math;

import java.awt.Dimension;

/**
 * A Utility-Class for Mathematical stuff.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class MathHelper
{
	/**
	 * Squares a number
	 * 
	 * @param d
	 * @return The squared value of <b>d</b>.
	 */
	public static double pow2(double d)
	{
		// return Math.pow(f, 2);
		return d * d;
	}

	/**
	 * Squares a number.
	 * 
	 * @param f
	 * @return The squared value of <b>f</b>.
	 */
	public static float pow2(float f)
	{
		return (float) pow2((double) f);
	}

	/**
	 * Squares a number.
	 * 
	 * @param i
	 * @return The squared value of <b>i</b>.
	 */
	public static int pow2(int i)
	{
		return (int) pow2((long) i);
	}

	public static long pow2(long l)
	{
		return l * l;
	}

	/**
	 * Calculates the closest power of two to a given number.
	 * 
	 * @param fold
	 * @return The closest power of two to <b>fold</b>
	 */
	public static int get2Fold(int fold)
	{
		int ret = 2;
		while (ret < fold)
		{
			ret = ret << 1;
		}
		return ret;
	}

	/**
	 * Calculates the positive aspect-ratio between <b>width</b> and
	 * <b>height</b>.
	 * 
	 * @param width
	 * @param height
	 * @return The positive ratio between <b>width</b> and <b>height</b>.
	 */
	public static float Aspect(int width, int height)
	{
		return Aspect((float) width, (float) height);
	}

	/**
	 * Calculates the positive aspect-ratio between <b>width</b> and
	 * <b>height</b>.
	 * 
	 * @param width
	 * @param height
	 * @return The positive ratio between <b>width</b> and <b>height</b>.
	 */
	public static float Aspect(float width, float height)
	{
		float aspect = 0;

		if (width >= height)
			aspect = width / height;
		else
			aspect = height / width;

		/*
		 * if(width == height) aspect = 1; else if(width > height) aspect =
		 * width / height; else if(height > width) aspect = height / width;
		 */
		return aspect;
	}

	public static float Aspect(Dimension size)
	{
		return Aspect((float) size.getWidth(), (float) size.getHeight());
	}

	public static float tangent(float angle)
	{
		return (float) Math.tan(angle);
	}

	public static float coTangent(float angle)
	{
		return (float) (1f / Math.tan(angle));
	}

	public static float degreesToRadians(float angle)
	{
		return angle * (float) (Math.PI / 180d);
	}
}
