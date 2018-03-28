package de.janik.devils.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.janik.devils.controller.GameEngine;
import de.janik.devils.controller.listener.BasicListener;

public class ViewListener extends BasicListener implements WindowListener
{
	public ViewListener(final GameEngine controller)
	{
		super(controller);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{		
		((GameEngine) controller).requestClose();
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
}
