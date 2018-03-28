package de.janik.devils.entity.model.objloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Material_File
{
	public static final String	FILE_EXTENSION			= ".mtl";

	private File				file;

	public Material_File(OBJ_File file, String name)
	{		
		this.file = new File(file.getParent().getAbsolutePath() + name);
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

	public File getParent()
	{
		return file.getParentFile();
	}
	
	public static boolean isMaterial_File(File file)
	{
		return file.getAbsolutePath().endsWith(FILE_EXTENSION) ? true : false;
	}
}
