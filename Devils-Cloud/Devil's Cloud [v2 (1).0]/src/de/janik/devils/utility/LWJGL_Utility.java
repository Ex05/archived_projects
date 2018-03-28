package de.janik.devils.utility;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;

/**
 * A utility class, used to do LWJGL specific stuff.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class LWJGL_Utility
{
	/**
	 * Bind's the LWJGL-native file's to the current OpenGL-context.
	 * 
	 * @param subdirectories
	 *            A list of all the sub-directories, of the directory, which
	 *            contain's the native-file's.
	 * 
	 */
	public static void bindNative(String... subdirectories)
	{
		String dir = "";

		// Create's the absolute-path of the LWJGL-native's based on the list of
		// sub-directories.
		for (String s : subdirectories)
			dir += s + File.separator;

		bindNative(new File(dir));
	}

	/**
	 * Bind's the LWJGL-native file's to the current OpenGL-context.
	 * 
	 * @param directory
	 *            The directory, which contain's the native-file's.
	 */
	public static void bindNative(File directory)
	{
		// Pulls a copy of the current operating-system.
		OS os = OS.GetCurrentOS();

		// Print some information of the operating-system to the console, just
		// for fun :)
		OS.printOSInfo();

		// Build the native path, from the path the program/jar is running
		// in/from, the path of the natives-file's in the file-structure of the
		// program and the operating-specific path of the native's.
		String nativePath = directory.getAbsolutePath() + File.separator + getOSSpecificNativePath(os);

		// Set the hidden-properie's of the lwjgl.
		System.setProperty("org.lwjgl.librarypath", nativePath);
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
	}

	/**
	 * Get's the LWJGL-native directory, based on the current OS.
	 * 
	 * @param os
	 * 
	 * @return The path of the native's folder, based on the given
	 *         operating-system.
	 */
	private static String getOSSpecificNativePath(OS os)
	{
		String path = null;

		switch (os)
		{
			case WINDOWS:
				path = new String("windows");
				break;
			case LINUX:
				path = new String("linux");
				break;
			case OSX:
				path = new String("macosx");
				break;

			default:
				break;
		}
		return path;
	}

	public static String GetGLVersion()
	{
		return glGetString(GL_VERSION);
	}
}
