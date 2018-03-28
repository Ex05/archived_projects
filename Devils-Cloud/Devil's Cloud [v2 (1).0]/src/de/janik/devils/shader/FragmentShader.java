package de.janik.devils.shader;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class FragmentShader extends Shader
{
	public static final String	FILE_NAME;

	static
	{
		FILE_NAME = "/shader.frag";
	}

	public FragmentShader(String path)
	{
		super(path + FILE_NAME, FRAGMENT_SHADER);
	}
}
