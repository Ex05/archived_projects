package de.janik.devils.controller;

import static de.janik.devils.utility.LWJGL_Utility.bindNative;

import java.awt.Dimension;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import de.janik.Main;
import de.janik.devils.Devil_Engine;
import de.janik.devils.input.Input;
import de.janik.devils.state.intro.Intro;
import de.janik.devils.utility.LWJGL_Utility;
import de.janik.devils.utility.PropertyFile;
import de.janik.devils.view.ViewListener;

public class GameEngine extends Controller
{
	public static final int		TICKS_PER_SECOND;

	private final Devil_Engine	engine;

	static
	{
		TICKS_PER_SECOND = PropertyFile.OPTIONS_TXT.getInt("TicksPerSecond");
	}

	public GameEngine(final Devil_Engine devil_Engine)
	{
		super();

		engine = devil_Engine;

		view.addListener(new ViewListener(this));

		redirecter.redirectAllOutputStreams(PropertyFile.OPTIONS_TXT.getBool("Redirect_Output-Streams"));

		state = new Intro(this);

		view.setVisible(true);
	}

	@Override
	public void destroy()
	{
		view.setVisible(false);
		view.removeListener();
		view.dispose();

		redirecter.dispose();

		Keyboard.destroy();
		Mouse.destroy();
		Display.destroy();
	}

	private void handleGlobalInput()
	{
		if (Input.KeyPressed(Keyboard.KEY_F10))
			toogleFullscreen();
	}

	@Override
	public void init()
	{
		initOpenGL();

		state.init();
	}

	private void initDisplay()
	{
		try
		{
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

			DisplayMode dm = Display.getDesktopDisplayMode();

			setDisplayMode(dm.getWidth(), dm.getHeight(), false);

			Display.setParent(view.getCanvas());

			Display.create(pixelFormat, contextAtrributes);

			Keyboard.create();
			Mouse.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void initOpenGL()
	{
		bindNative(Main.DIR, "native");

		initDisplay();

		System.out.println("OpenGL_Version: " + LWJGL_Utility.GetGLVersion());
	}

	@Override
	public void render()
	{
		state.render();

		Display.sync(60);

		Display.update();
	}

	@Override
	public void requestClose()
	{
		engine.stop();
	}

	public void setDisplayMode(int width, int height, boolean fullscreen)
	{
		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen))
			return;
		try
		{
			DisplayMode targetDisplayMode = null;

			if (fullscreen)
			{
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++)
				{
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height))
					{
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq))
						{
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
							{
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
						{
							targetDisplayMode = current;
							break;
						}
					}
				}
			}
			else
			{
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null)
			{
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen);
		}
	}

	@Override
	public void tick()
	{
		Input.Tick();

		handleGlobalInput();

		state.tick();
	}

	public void toogleFullscreen()
	{
		try
		{
			Display.setFullscreen(!Display.isFullscreen());
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	//<-Getter & Setter->
	public Dimension getCanvasSize()
	{
		return view.getCanvas().getSize();
	}
}
