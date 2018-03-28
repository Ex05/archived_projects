package de.janik.devils.state;

import org.lwjgl.opengl.GL11;

import de.janik.devils.camera.Camera;
import de.janik.devils.controller.GameEngine;
import de.janik.devils.utility.Color;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public abstract class GameState
{
	protected static Camera		camera;

	protected GameEngine		engine;

	protected StateController	controller;

	protected long				ticks;

	public GameState(final GameEngine engine)
	{
		this.engine = engine;
	}

	public abstract void destroy();

	public void init()
	{
		initOpenGL();
	}

	public abstract void initOpenGL();

	public abstract void render();

	public void tick()
	{
		controller.tick();

		ticks++;
	}

	public void glClearColor(Color color)
	{
		GL11.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	// <-Getter & Setter->

	public StateController getController()
	{
		return controller;
	}

	public void setController(StateController controller)
	{
		this.controller = controller;
	}

	public void stopEngine()
	{
		engine.requestClose();
	}

	public static Camera getCamera()
	{
		return camera;
	}
}
