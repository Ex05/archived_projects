package de.janik.devils.utility;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class PropertyFile
{
	public static final PropertyFile	OPTIONS_TXT;
	public static final String			PROPERTY_FILE_PATH	= "./res/options.txt";

	public static final String			REGEX				= "=";
	public static final String			DELIMITER			= ";";
	public static final String			COMMENT				= "//";
	public static final String			HASHTAG				= "#";
	public static final String			STRING_IDENTIFYER	= "\"";

	private Hashtable<String, String>	properties;

	static
	{
		OPTIONS_TXT = new PropertyFile(new File(PROPERTY_FILE_PATH));
	}

	public PropertyFile()
	{
		properties = new Hashtable<String, String>();
	}

	public PropertyFile(String path)
	{
		this();

		load(path);
	}

	public PropertyFile(File file)
	{
		this();

		load(file);
	}

	public void load(String path)
	{
		try
		{
			InputStream is = this.getClass().getResourceAsStream(path);

			load(is);
		}

		catch (IOException e)
		{
			System.out.println("\n" + "ERROR, while loading propertyFile: " + path + ".");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void load(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);

			load(fis);
		}

		catch (IOException e)
		{
			System.out.println("\n" + "ERROR, while loading propertyFile: " + file.getAbsolutePath() + ".");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void load(InputStream is) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = "";

		while ((line = br.readLine()) != null)
		{
			if (line.startsWith(COMMENT) || line.startsWith(HASHTAG) || line.equals(""))
				continue;

			String[] segments = line.split(REGEX);

			String key = segments[0].toLowerCase();
			key = key.trim();

			String value = segments[1].split(DELIMITER)[0];
			value = value.trim();

			if (value.startsWith(STRING_IDENTIFYER))
				value = value.replaceAll(STRING_IDENTIFYER, "");

			properties.put(key, value);
		}
	}

	// <Getter & Setter>
	public String get(String key)
	{
		String s = properties.get(key.toLowerCase());

		if (s == null)
			return key;
		else
			return s;
	}

	public boolean getBool(String key)
	{
		return Boolean.parseBoolean(get(key));
	}

	public short getShort(String key)
	{
		return Short.parseShort(get(key));
	}

	public int getInt(String key)
	{
		return Integer.parseInt(get(key));
	}

	public long getLong(String key)
	{
		return Long.parseLong(get(key));
	}

	public float getFloat(String key)
	{
		return Float.parseFloat(get(key));
	}

	public double getDouble(String key)
	{
		return Double.parseDouble(get(key));
	}

	public Dimension getDim(String key)
	{
		String[] lineSplit = get(key).split("/");

		int width = Integer.parseInt(lineSplit[0]);
		int height = Integer.parseInt(lineSplit[1]);

		return new Dimension(width, height);
	}
}
