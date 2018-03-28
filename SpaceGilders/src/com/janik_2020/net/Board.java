package com.janik_2020.net;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener
{
	private static final long serialVersionUID = (long) 1L;
	private final Timer timer;
    private final Glider craft;
    private ArrayList<Alien> aliens;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    private boolean firePressed;

    private final int[][] pos = { 
        {500 , (int) ( Math.random() * 200 )},
        {600 , (int) ( Math.random() * 200 )},
        {700 , (int) ( Math.random() * 200 )},
        {800 , (int) ( Math.random() * 200 )},
        {900 , (int) ( Math.random() * 200 )},
        {1000, (int) ( Math.random() * 200 )},
        {1100, (int) ( Math.random() * 200 )},
        {1200, (int) ( Math.random() * 200 )},
        {1300, (int) ( Math.random() * 200 )},
        {1400, (int) ( Math.random() * 200 )}
     };

    public Board()
    {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;

        setSize(400, 300);
        craft = new Glider();
        initAliens();

        timer = new Timer(6, this); // Aliens Speed
        timer.start();
    }

    public void addNotify()
    {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }

    public void initAliens() 
    {
        aliens = new ArrayList<Alien>();

        for (int i = 0; i < pos.length; i++ ) 
        {
            aliens.add(new Alien(pos[i][0], pos[i][1]));
        }
    }

    public void paint(final Graphics g) 
    {
        super.paint(g);

        if (ingame) 
        {
            final Graphics2D g2d = (Graphics2D)g;

            if (craft.isVisible())
                g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),this);

            final ArrayList<?> ms = craft.getMissiles();

            for (int i = 0; i < ms.size(); i++) 
            {
                final Missile m = (Missile)ms.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            for (int i = 0; i < aliens.size(); i++) 
            {
                final Alien a = (Alien)aliens.get(i);
                if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawString("Aliens left: " + aliens.size(), 5, 15);


        } 
        else 
        {        	
        	if (aliens.size() == 0)
        	{
        		 final String msg = "You win !!!";
                 final Font small = new Font("Helvetica", Font.BOLD, 18);
                 final FontMetrics metr = this.getFontMetrics(small);

                 g.setColor(Color.white);
                 g.setFont(small);
                 g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                              B_HEIGHT / 2 - 20);
        	}
        	else
        	{        	
        		final String msg = "Game Over";     	        		
        		final Font small = new Font("Helvetica", Font.BOLD, 24);        		        		
        		final FontMetrics metr = this.getFontMetrics(small);        	
        		
        		g.setColor(Color.white);
        		g.setFont(small);
        		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         B_HEIGHT / 2 - 20);
          	}
        	        	
    		final String copyRight = "© 2012 Jan.Marcel.Janik";    		
    		final Font small = new Font("Helvetica", Font.BOLD, 9);    		        		
    		final FontMetrics metr = this.getFontMetrics(small);
    	    		
    		g.setColor(Color.white);
    		g.setFont(small);
    		g.drawString(copyRight, (B_WIDTH - metr.stringWidth(copyRight)) / B_WIDTH,
                     B_HEIGHT - 30);
        	
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    public void actionPerformed(final ActionEvent e)
    {
        if (aliens.size()==0) 
        {
            ingame = false;
        }

        final ArrayList<?> ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) 
        {
            final Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
                m.move();
            else ms.remove(i);
        }

        for (int i = 0; i < aliens.size(); i++)
        {
            final Alien a = (Alien) aliens.get(i);
            if (a.isVisible()) 
                a.move();
            else aliens.remove(i);
        }

        craft.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() 
    {
        final Rectangle r3 = craft.getBounds();

        for (int j = 0; j<aliens.size(); j++) 
        {
            final Alien a = (Alien) aliens.get(j);
            final Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) 
            {
                craft.setVisible(false);
                a.setVisible(false);
                ingame = false;
            }
        }
        final ArrayList<?> ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++)
        {
            final Missile m = (Missile) ms.get(i);

            final Rectangle r1 = m.getBounds();

            for (int j = 0; j<aliens.size(); j++) 
            {
                final Alien a = (Alien) aliens.get(j);
                final Rectangle r2 = a.getBounds();

                if (r1.intersects(r2)) 
                {
                    m.setVisible(false);
                    a.setVisible(false);
                }
            }
        }
    }

    public void setFirePressed(boolean firePressed) {
		this.firePressed = firePressed;
	}

	public boolean isFirePressed() {
		return firePressed;
	}

	private class TAdapter extends KeyAdapter
    {
        public void keyReleased(final KeyEvent e)
        {
            craft.keyReleased(e);
          //  setFirePressed(false);
        }

        public void keyPressed(final KeyEvent e)
        {
        //	if( !isFirePressed() )
        		craft.keyPressed(e);
        //	setFirePressed(true);
           
        }
    }
}
