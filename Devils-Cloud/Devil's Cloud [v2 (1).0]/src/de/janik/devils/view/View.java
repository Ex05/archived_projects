package de.janik.devils.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;

import de.janik.devils.texture.ImageLoader;
import de.janik.devils.utility.PropertyFile;

public class View extends JFrame
{
	private static final long		serialVersionUID	= 1L;

	private static final String		TITLE;
	private static final String		ICON_IMAGE;

	private static final Dimension	MIN_SIZE;
	private static final Dimension	DEFAULT_SIZE;

	private final GridBagLayout		gbl;

	private GamePanel				gamePanel;

	private ViewListener			listener;

	static
	{
		TITLE = PropertyFile.OPTIONS_TXT.get("Title");
		ICON_IMAGE = "/res/img/WindowIcon.png";

		MIN_SIZE = PropertyFile.OPTIONS_TXT.getDim("Min_Size");
		DEFAULT_SIZE = PropertyFile.OPTIONS_TXT.getDim("Default_Size");
	}

	public View()
	{
		gbl = new GridBagLayout();

		setTitle("");
		setLayout(gbl);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		setMinimumSize(MIN_SIZE);
		setSize(DEFAULT_SIZE);

		setIconImages(ImageLoader.getInstance().loadIconImages(ICON_IMAGE));

		gamePanel = new GamePanel();

		// Insets; Top,Left,Bottom,Right
		add(gamePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		setLocationRelativeTo(null);
	}

	public void addListener(ViewListener listener)
	{
		this.listener = listener;
		addWindowListener(listener);
	}

	public void removeListener()
	{
		removeWindowListener(listener);
		listener = null;
	}

	@Override
	public void setTitle(String title)
	{
		super.setTitle(TITLE + title);
	}

	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);

		gamePanel.setVisible(visible);
	}

	//<-Getter & Setter->
	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public GameCanvas getCanvas()
	{
		return gamePanel.getCanvas();
	}
}
