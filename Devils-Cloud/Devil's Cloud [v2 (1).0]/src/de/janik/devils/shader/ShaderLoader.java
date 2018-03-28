package de.janik.devils.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderLoader
{
	private static final String	COMMENT;

	private static ShaderLoader	loader;

	static
	{
		COMMENT = "//";
	}

	public ShaderSource load(File file)
	{
		ShaderSource source = null;

		try
		{
			source = load(new FileInputStream(file));
		}
		catch (IOException e)
		{
			e.printStackTrace();

			System.exit(1);
		}

		return source;
	}

	public ShaderSource load(String path)
	{
		ShaderSource source = null;

		try
		{
			source = load(this.getClass().getResourceAsStream(path));
		}
		catch (IOException | IllegalArgumentException e)
		{
			System.out.println("\n" + "Failed to load shader_source: " + path);
			e.printStackTrace();

			System.exit(0);
		}

		return source;
	}

	public ShaderSource load(InputStream is) throws IOException, IllegalArgumentException
	{
		StringBuilder b = new StringBuilder();

		if (is == null)
			throw new IllegalArgumentException("Inputstream is null !!");

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = "";

		while ((line = br.readLine()) != null)
			// /*
			if (line.equals(""))
				continue;
			else
				if ((line = line.trim()).startsWith(COMMENT))
					continue;
				else
					// */
					b.append(line + "\n");

		return new ShaderSource(b.toString());
	}

	public static ShaderLoader getInstance()
	{
		if (loader == null)
			loader = new ShaderLoader();

		return loader;
	}
}
