package de.janik.devils;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.lwjgl.opengl.Display;

import de.janik.devils.controller.GameEngine;
import de.janik.devils.utility.PropertyFile;
import de.janik.devils.utility.Timer;

public class Devil_Engine implements Runnable
{
	private final GameEngine	engine;

	private volatile boolean	running	= false;
	private Thread				thread;

	//private boolean				limitFPs;
	private boolean				vSync;

	private int					fps, tps;

	//private int					framesPerSecond;

	public Devil_Engine()
	{
		engine = new GameEngine(this);

		vSync = PropertyFile.OPTIONS_TXT.getBool("V-SYNC");

		if (vSync)
		{
			//	limitFPs = true;

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();

			int frameRate = gd.getDisplayMode().getRefreshRate();

			if (frameRate == DisplayMode.REFRESH_RATE_UNKNOWN)
			{
				new IllegalArgumentException("Unable to detect screen-refreshrate, auto set to 60.").printStackTrace();
				frameRate = 60;
			}
		}
		else
		{
			//	limitFPs = PropertyFile.OPTIONS_TXT.getBool("LimitFrameRate");
			//	framesPerSecond = PropertyFile.OPTIONS_TXT.getInt("FramesPerSecond");
		}
	}

	@Override
	public void run()
	{
		engine.init();

		long lastTime = Timer.getTime();

		long updateTimer = lastTime;

		final double NANOS_PER_TICK = (double) Timer.NANOSECOND_PER_SECOND / (double) GameEngine.TICKS_PER_SECOND;

		double tickDelta = 0;

		// TODO: Limit frame rate.
		while (running)
		{
			if (Display.isCloseRequested())
				System.out.println("C");

			long currentTime = Timer.getTime();

			tickDelta += (currentTime - lastTime) / NANOS_PER_TICK;

			lastTime = currentTime;

			while (tickDelta >= 1)
			{
				engine.tick();

				tps++;
				tickDelta--;
			}

			engine.render();

			fps++;

			if (Timer.getTime() - updateTimer > Timer.NANOSECOND_PER_SECOND)
			{
				updateTimer += Timer.NANOSECOND_PER_SECOND;

				engine.update(tps, fps);

				tps = 0;
				fps = 0;
			}
		}

		engine.destroy();
	}

	public synchronized void start()
	{
		if (running)
			return;

		running = true;

		thread = new Thread(this, getClass().getSimpleName());
		thread.start();
	}

	public synchronized void stop()
	{
		if (!running)
			return;

		running = false;
	}
}
