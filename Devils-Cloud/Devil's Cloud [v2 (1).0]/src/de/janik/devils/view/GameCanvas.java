package de.janik.devils.view;

import java.awt.Canvas;
import java.awt.Color;

public class GameCanvas extends Canvas
{
	private static final long	serialVersionUID	= 1L;

	public GameCanvas()
	{
		super();

		setBackground(Color.BLACK);

		setIgnoreRepaint(true);
	}
}
