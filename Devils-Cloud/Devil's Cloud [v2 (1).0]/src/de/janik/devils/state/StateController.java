package de.janik.devils.state;
/**
 * 
 * @author Jan.Marcel.Janik
 *
 */
public abstract class StateController
{
	protected GameState	state;

	public StateController(final GameState state)
	{
		this.state = state;
	}

	public void tick()
	{
		handleMouseInput();

		handleKeyBoardInput();
	}

	public abstract void handleMouseInput();

	public abstract void handleKeyBoardInput();
}