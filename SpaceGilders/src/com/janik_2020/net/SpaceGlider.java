package com.janik_2020.net;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.swing.JFrame;

public class SpaceGlider extends JFrame
{
	private static final long serialVersionUID = (long) 1L;
	private static boolean isJar;
	
    public SpaceGlider() 
    {
    	   String iniFileStr ="";
   	    String s = this.getClass().getName().replace('.','/') + ".class";
   	    final URL url = this.getClass().getClassLoader().getResource(s);
   	    int pos;
   	    try {
   	        iniFileStr = URLDecoder.decode(url.getPath(),"UTF-8");
   	    } catch (final UnsupportedEncodingException ex) {};
   	    // special handling for JAR
   	    if (( (pos=iniFileStr.toLowerCase().indexOf("file:")) != -1))
   	        iniFileStr = iniFileStr.substring(pos+5);
   	    if ( (pos=iniFileStr.toLowerCase().indexOf(s.toLowerCase())) != -1)
   	        iniFileStr = iniFileStr.substring(0,pos);
   	    s = (this.getClass().getName().replace('.','/') + ".jar").toLowerCase();
   	    if ( (pos=iniFileStr.toLowerCase().indexOf(s)) != -1)
   	        iniFileStr = iniFileStr.substring(0,pos);
   	    
   	    if(iniFileStr.indexOf(".jar") > 0)
   	    {
   	    	this.setJar(true);	  
   	    }
    	    	
        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Space Glider's");
        setResizable(false);
        setVisible(true);
       		
    }

    public static void main(final String[] args)
    {
        new SpaceGlider();
    }

	public void setJar(final boolean isJAr) {
		SpaceGlider.isJar = isJAr;
	}

	public static boolean isJar() {
		return isJar;
	}
}
