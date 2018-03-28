package de.janik.devils.console;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ConsoleRedirecter extends JFrame
{
	private static final long	serialVersionUID	= 1L;

	private final GridBagLayout	gbl;

	private JTextPane			console;
	private JScrollPane			pane;

	HTMLEditorKit				kit					= new HTMLEditorKit();
	HTMLDocument				doc					= new HTMLDocument();

	public ConsoleRedirecter(String title)
	{
		super(title);

		gbl = new GridBagLayout();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setSize(500, 400);

		getContentPane().setLayout(gbl);

		console = new JTextPane();
		console.setEditable(false);
		console.setContentType("text/html");

		console.setEditorKit(kit);
		console.setDocument(doc);

		pane = new JScrollPane(console);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Insets; Top,Left,Bottom,Right
		getContentPane().add(pane,
				new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		setLocation();
	}

	public void setLocation()
	{
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();

		int width = 0;
		int height = 0;

		if (devices.length >= 2)
		{
			DisplayMode d = devices[0].getDisplayMode();

			int left_width = d.getWidth();
			//int left_height = d.getHeight();

			d = devices[1].getDisplayMode();

			int right_width = d.getWidth();
			int right_height = d.getHeight();

			width = left_width + (int) (right_width * 0.05f);
			height = (right_height - getHeight()) - (int) (right_height * 0.05f);
		}

		setLocation(width, height);
	}

	public ConsoleRedirecter redirectAllOutputStreams(boolean b)
	{
		if (b)
		{
			setVisible(true);

			SetOutputStream(new MyOutputStream());
			SetErrorOutputStream(new MyErrorOutputStream());
		}
		return this;
	}

	private static void SetOutputStream(OutputStream os)
	{
		System.setOut(new PrintStream(os, true));
	}

	private static void SetErrorOutputStream(OutputStream os)
	{
		System.setErr(new PrintStream(os, true));
	}

	private class MyErrorOutputStream extends OutputStream
	{
		private void updateTextArea(final String text)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					try
					{
						kit.insertHTML(doc, doc.getLength(), "<font color='red'>" + text + "</font>", 0, 0, null);
					}
					catch (BadLocationException | IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		@Override
		public void write(int b) throws IOException
		{
			updateTextArea(String.valueOf((char) b));
		}

		@Override
		public void write(byte[] b) throws IOException
		{
			write(b, 0, b.length);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException
		{
			updateTextArea(new String(b, off, len));
		}
	}

	private class MyOutputStream extends OutputStream
	{
		private void updateTextArea(final String text)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					try
					{
						kit.insertHTML(doc, doc.getLength(), "<font color='black'>" + text + "</font>", 0, 0, null);
					}
					catch (BadLocationException | IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		@Override
		public void write(int b) throws IOException
		{
			updateTextArea(String.valueOf((char) b));
		}

		@Override
		public void write(byte[] b) throws IOException
		{
			write(b, 0, b.length);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException
		{
			updateTextArea(new String(b, off, len));
		}
	}
}
