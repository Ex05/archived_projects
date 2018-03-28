package de.janik.devils.utility;

import java.io.File;

public enum OS
{
	LINUX("linux", new String[]
	{ "linux", "unix" }), WINDOWS("windows", new String[]
	{ "win" }), OSX("osx", new String[]
	{ "mac" }), UNKNOWN("unknown", new String[0]);

	private final String	name;
	private final String[]	aliases;

	private String			version;
	private String			arch;

	private OS(String name, String[] aliases)
	{
		this.name = name;
		this.aliases = (aliases == null ? new String[0] : aliases);

		String os_version = System.getProperty("os.version");

		version = (os_version == null ? new String() : os_version);

		String os_arch = System.getProperty("os.arch");

		arch = (os_arch == null ? new String() : os_arch);
	}

	public String[] getAliases()
	{
		return this.aliases;
	}

	public String getName()
	{
		return name;
	}

	public String getVersion()
	{
		return version;
	}

	public String getArch()
	{
		return arch;
	}

	public static OS GetCurrentOS()
	{
		String osName = System.getProperty("os.name").toLowerCase();

		for (OS os : values())
			for (String alias : os.getAliases())

				if (osName.contains(alias))
					return os;

		return UNKNOWN;
	}

	public String getJavaDir()
	{
		String separator = System.getProperty("file.separator");
		String path = System.getProperty("java.home") + separator + "bin" + separator;

		if ((GetCurrentOS() == WINDOWS) && (new File(path + "javaw.exe").isFile()))
			return path + "javaw.exe";
		else
			return path + "java";
	}

	public boolean isSupported()
	{
		return this != UNKNOWN;
	}

	@Override
	public String toString()
	{
		String s = "OS [Name: " + name.toUpperCase();

		if (!version.equals(new String()))
			s += " Version:" + version;

		if (!arch.equals(new String()))
			s += " Architecture:" + arch;

		return s;
	}

	public static void printOSInfo()
	{
		OS os = OS.GetCurrentOS();
		
		StringBuilder b = new StringBuilder();
		
		b.append("System info:");
		b.append("\n");
		b.append("Operating system: ");
		b.append("\"" + os.getName() + "\"");
		b.append("\n");
		b.append("System version:");
		b.append("\"" + os.getVersion() + "\"");
		b.append("\n");
		b.append("System architecture:");
		b.append("\"" + os.getArch() + "\"");

		System.out.println(b.toString());
	}
}