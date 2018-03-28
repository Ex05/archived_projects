package de.janik.devils.controller;

import de.janik.devils.console.ConsoleRedirecter;
import de.janik.devils.state.GameState;
import de.janik.devils.view.View;

public abstract class Controller
{
	//protected Model model;
	protected View				view;

	protected ConsoleRedirecter	redirecter;

	protected GameState			state;

	protected int				tps, fps;

	protected Controller()
	{
		view = new View();

		redirecter = new ConsoleRedirecter("ERROR_Console");
	}

	public abstract void init();

	public abstract void render();

	public abstract void destroy();

	public abstract void tick();

	public abstract void requestClose();

	public void update(int ticksPerSecond, int framesPerSecond)
	{
		tps = ticksPerSecond;
		fps = framesPerSecond;

		view.setTitle(" | " + tps + " tp/s " + fps + " fp/s");
	}

	public void switchState(GameState state)
	{
		this.state = state;
	}
}
