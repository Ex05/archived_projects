package de.janik.devils.utility;

import java.io.File;

public class LanguageFile extends PropertyFile
{
	public static LanguageFile	Curent_Language;

	private static final String	LOCATION;

	static
	{
		LOCATION = "./res/language/language_";
	}

	private LanguageFile(String name)
	{
		super(new File(LOCATION + name));
	}

	public static void setLanguage(String name)
	{
		Curent_Language = new LanguageFile(name);
	}
}
