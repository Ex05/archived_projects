package de.janik.devils.entity.model.objloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OBJ_File
{
	public static final String	FILE_EXTENSION			= ".obj";
	public static final String	DEFAULT_MODEL_LOCATION	= "./res/model/";

	private File				file;

	public OBJ_File(String name, String... subDirectories)
	{
		String sub_Dir = "";

		for (String s : subDirectories)
			sub_Dir += s + "/";

		if (!name.endsWith(FILE_EXTENSION))
			name += FILE_EXTENSION;

		file = new File(DEFAULT_MODEL_LOCATION + sub_Dir + name);

		// Useless, because file will always be a .obj-file.
		/*		if (!isOBJ_File(file))
		 *			new FileFormatException("The file[" + file.getAbsolutePath() + " is not in the '" + FILE_EXTENSION + "' format.");
		*/
		if (!file.exists())
			new FileNotFoundException("Could not allocate the file[" + file.getAbsolutePath() + " on the hard-drive");
	}

	@Override
	public String toString()
	{
		return "OBJ_File [file=" + file + "]";
	}

	public File getParent()
	{
		return file.getParentFile();
	}

	public String getPath()
	{
		return file.getAbsolutePath();
	}

	public FileInputStream getInputStream()
	{
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return fis;
	}

	public static boolean isOBJ_File(File file)
	{
		return file.getAbsolutePath().endsWith(FILE_EXTENSION) ? true : false;
	}
}
