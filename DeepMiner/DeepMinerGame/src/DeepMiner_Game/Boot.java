package DeepMiner_Game;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Boot
{
	private static enum State
	{
		STATS, HELP_MENUE, GAME
	}

	private BlockGrid grid;
	private BlockType selection = BlockType.STONE;
	private int selector_x = 0, selector_y = 0;
	private boolean mouseEnabled = true;
	private State state = State.HELP_MENUE;
	private int fps;
	private int wishedFPS = 60;

	public Boot()
	{
		try
		{
			Mouse.setGrabbed(true);

			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("DeepMiner v1.2");
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		grid = new BlockGrid();

		// Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// ///////////////////////////////////////////////////////////////////

		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;

		File xp = new File("CloseSave.xml");
		if (xp.exists())
		{
			System.out.println("Found old score!!!");
			grid.load(xp);
		}
		while (!Display.isCloseRequested())
		{
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;

			while (unprocessedSeconds > secondsPerTick)
			{
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if ((tickCount % 60) == 0)
				{
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked)
			{
				Display.setTitle("DeepMiner v1.2" + " at: " + fps + "fps");
				// renderText("fps"); // Tied, but too slowly, just for static
				// view (I think so ;))
				render();
				frames++;
			}
		}

		Display.destroy();
		System.exit(0);
	}

	/*
	 * private void renderText(String string) {
	 * 
	 * // Clear the screen glClear(GL_COLOR_BUFFER_BIT);
	 * 
	 * glEnable(GL_TEXTURE_2D); org.newdawn.slick.opengl.TextureImpl.bindNone();
	 * // load a default java font Font awtFont = new Font("Times New Roman",
	 * Font.BOLD, 24); font = new TrueTypeFont(awtFont, false);
	 * font.drawString(100, 100, string); glDisable(GL_TEXTURE_2D);
	 * 
	 * }
	 */
	private void render()
	{
		// Clear the screen
		glClear(GL_COLOR_BUFFER_BIT);

		switch (state)
		{
		case STATS:
			// Do nothing
			break;
		case GAME:
			input();
			grid.draw();
			drawSelectionBox();
			break;
		case HELP_MENUE:
			Texture texture = null;
			try
			{
				texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/help.png")));
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			texture.bind();
			glLoadIdentity();
			glTranslatef(0, 0, 0);
			glColor3f(0.5f, 0.5f, 1f);
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);// Upper-left
			glTexCoord2f(1, 0);
			glVertex2f(Display.getWidth() + 322, 0);// Upper-right
			glTexCoord2f(1, 1);
			glVertex2f(Display.getWidth() + 322, Display.getHeight() + 32);// Bottom-right
			glTexCoord2f(0, 1);
			glVertex2f(0, Display.getHeight() + 32);// Bottom-left
			glEnd();
			glLoadIdentity();
			break;
		}
		input();

		// Update the display
		Display.update();
		// Synchronize to 60 fps
		Display.sync(wishedFPS);
	}

	private void drawSelectionBox()
	{
		int x = selector_x * World.BLOCK_SIZE;
		int y = selector_y * World.BLOCK_SIZE;
		int x2 = x + World.BLOCK_SIZE;
		int y2 = y + World.BLOCK_SIZE;
		if (grid.getAt(selector_x, selector_y).getType() != BlockType.AIR || selection == BlockType.AIR)
		{
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1f, 1f, 1f, 0.5f);
			glBegin(GL_QUADS);
			glVertex2i(x, y);
			glVertex2i(x2, y);
			glVertex2i(x2, y2);
			glVertex2i(x, y2);
			glEnd();
			glColor4f(1f, 1f, 1f, 1f);
		} else
		{
			glColor4f(1f, 1f, 1f, 0.5f);
			new Block(selection, selector_x * World.BLOCK_SIZE, selector_y * World.BLOCK_SIZE).draw();
			glColor4f(1f, 1f, 1f, 1f);
		}
	}

	private void input()
	{
		if (mouseEnabled || Mouse.isButtonDown(0))
		{
			mouseEnabled = true;
			int mousex = Mouse.getX();
			int mousey = 480 - Mouse.getY() - 1;
			boolean mouseClicked = Mouse.isButtonDown(0);
			selector_x = Math.round(mousex / World.BLOCK_SIZE);
			selector_y = Math.round(mousey / World.BLOCK_SIZE);
			if (mouseClicked)
			{
				grid.setAt(selector_x, selector_y, selection);
				// grid.save(new File("save.xml"));
				// grid.load(new File("save.xml"));
				// grid.draw();
			}

		}
		while (Keyboard.next())
		{
			if (state == State.GAME)
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState())
				{
					mouseEnabled = false;
					if (!(selector_x + 1 > World.BLOCKS_WIDTH - 2))
					{
						selector_x += 1;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState())
				{
					mouseEnabled = false;
					if (!(selector_x - 1 < 0))
					{
						selector_x -= 1;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState())
				{
					mouseEnabled = false;
					if (!(selector_y - 1 < 0))
					{
						selector_y -= 1;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState())
				{
					mouseEnabled = false;
					if (!(selector_y + 1 > World.BLOCKS_HEIGHT - 2))
					{
						selector_y += 1;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					grid.save(new File("save.xml"));
					System.out.println("Saved score!!!");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_L)
				{
					grid.load(new File("save.xml"));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_1)
				{
					selection = BlockType.STONE;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_2)
				{
					selection = BlockType.DIRT;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_3)
				{
					selection = BlockType.GRASS;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_4)
				{
					selection = BlockType.AIR;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_C)
				{
					grid.clear();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN)
				{
					grid.setAt(selector_x, selector_y, selection);
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
			{
				grid.save(new File("CloseSave.xml"));
				System.out.println("Good bye...");
				Display.destroy();
				System.exit(0);
			}
			// If we've pressed enter, enter the switch statement
			if (Keyboard.isKeyDown(Keyboard.KEY_H))
			{
				// Sets the state to:
				// fading -> main
				// main -> intro
				// intro -> fading
				switch (state)
				{
				case STATS:
					state = State.GAME;
					System.out.println("State changed: " + state);
					break;
				case HELP_MENUE:
					state = State.GAME;
					System.out.println("State changed: " + state);
					break;
				case GAME:
					state = State.HELP_MENUE;
					System.out.println("State changed: " + state);
					break;
				}
			}

		}
	}

	public static void main(String[] args)
	{
		new Boot();
	}
}
