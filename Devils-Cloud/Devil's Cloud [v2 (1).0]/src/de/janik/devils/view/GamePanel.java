package de.janik.devils.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class GamePanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;

	private final GridBagLayout	gbl;

	private GameCanvas			canvas;

	public GamePanel()
	{
		gbl = new GridBagLayout();

		setLayout(gbl);

		canvas = new GameCanvas();

		// Insets; Top,Left,Bottom,Right
		add(canvas, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);

		canvas.setVisible(visible);
	}

	public GameCanvas getCanvas()
	{
		return canvas;
	}

	//<Getter & Setter>
	
}
