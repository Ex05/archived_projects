package de.janik.devils.utility;

public class Timer
{
	public static final double	NANOSECOND_PER_SECOND	= 1_000_000_000.0;
	public static final int		MILLISECOND_PER_SECOND	= 1_000;

	public static long getTime()
	{
		return System.nanoTime();
	}
}
