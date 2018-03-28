package com.janik_2020.net;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Glider
{
	private String glider = "craft.png";   
    
    private int dx;
    private int dy;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    private ArrayList<Missile> missiles;

    public Glider() 
    {  
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(glider));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        missiles = new ArrayList<Missile>();
        visible = true;
        x = 30;
        y = 105;
    }


    public void move()
    {
        x += dx;
        y += dy;

        if (x < 1) 
        {
            x = 1;
        }

        if (y < 1)
        {
            y = 1;
        }
        if (x > 345)
        {
            x = 345;
        }
        if (y > 225)
        {
            y = 225;
        }
        
        
    }

    public int getX() 
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Image getImage()
    {
        return image;
    }

    public ArrayList<Missile> getMissiles()
    {
        return missiles;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public boolean isVisible() 
    {
        return visible;
    }

    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, width, height);
    }

    public void keyPressed(KeyEvent e)
    {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE)
        {
            fire();
        }

        if (key == KeyEvent.VK_LEFT)
        {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP)
        {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dy = 1;
        }
    }

    public void fire() 
    {
        missiles.add(new Missile(x + width, y + height/2));
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) 
        {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) 
        {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) 
        {
            dy = 0;
        }
    }
}
