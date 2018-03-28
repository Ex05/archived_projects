package de.janik;

import de.janik.devils.Devil_Engine;
import de.janik.devils.utility.LanguageFile;
import de.janik.devils.utility.PropertyFile;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Main
{
	public static final String	DIR;

	static
	{
		DIR = System.getProperty("user.dir");

		LanguageFile.setLanguage(PropertyFile.OPTIONS_TXT.get("Language"));
	}

	public static void main(String[] args)
	{
		new Devil_Engine().start();
	}
}
