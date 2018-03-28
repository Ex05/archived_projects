package de.janik.devils.state.intro;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import de.janik.devils.camera.Camera;
import de.janik.devils.state.GameState;
import de.janik.devils.state.StateController;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class IntroController extends StateController
{
	private double			deltaT;
	private long			lastTime;

	private static double	MOVEMENT_SPEED;
	private static double	MOUSE_SENSITIVITY;

	private final Camera	camera;

	public IntroController(final Intro intro)
	{
		super(intro);

		camera = GameState.getCamera();

		MOVEMENT_SPEED = 10.0;
		MOUSE_SENSITIVITY = 0.075;
	}

	@Override
	public void tick()
	{
		handleKeyBoardInput();
		handleMouseInput();

		// camera.printInfo();
	}

	@Override
	public void handleKeyBoardInput()
	{
		if (Mouse.isGrabbed())
		{
			boolean key_up = Keyboard.isKeyDown(Keyboard.KEY_UP);
			boolean key_w = Keyboard.isKeyDown(Keyboard.KEY_W);
			boolean key_down = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
			boolean key_s = Keyboard.isKeyDown(Keyboard.KEY_S);
			boolean key_left = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
			boolean key_a = Keyboard.isKeyDown(Keyboard.KEY_A);
			boolean key_right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
			boolean key_d = Keyboard.isKeyDown(Keyboard.KEY_D);
			boolean key_shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

			boolean key_space = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
			boolean key_crtl = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

			long currentTime = System.nanoTime();
			deltaT = (currentTime - lastTime) / 1_000_000_000.0f;
			lastTime = currentTime;

			float dMovement = (float) (MOVEMENT_SPEED * deltaT);

			if (key_shift)
				dMovement *= 2;

			if (key_w || key_up)
				camera.moveForward(dMovement);

			if (key_s || key_down)
				camera.moveBackward(dMovement);

			if (key_a || key_left)
				camera.strafeLeft(dMovement);

			if (key_d || key_right)
				camera.strafeRight(dMovement);

			if (key_space)
				camera.moveUp(dMovement);

			if (key_crtl)
				camera.moveDown(dMovement);
		}
		while (Keyboard.next())
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				if (Mouse.isGrabbed())
					Mouse.setGrabbed(false);
				else
					state.stopEngine();
		}
	}

	@Override
	public void handleMouseInput()
	{

		int dx = Mouse.getDX();
		int dy = Mouse.getDY();

		if (Mouse.isGrabbed())
		{
			// controll camera yaw, from x movement of the mouse
			camera.yaw(dx * MOUSE_SENSITIVITY);
			// controll camera pitch, from y movement of the mouse
			camera.pitch(dy * MOUSE_SENSITIVITY);
		}

		while (Mouse.next())
		{
			boolean leftButton = Mouse.isButtonDown(0);

			if (leftButton)
			{
				if (!Mouse.isGrabbed())
				{
					Mouse.setGrabbed(true);
				}
			}
		}
	}
}
