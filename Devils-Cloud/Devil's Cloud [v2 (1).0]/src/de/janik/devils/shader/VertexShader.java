package de.janik.devils.shader;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class VertexShader extends Shader
{
	public static final String	FILE_NAME;

	static
	{
		FILE_NAME = "/shader.vert";
	}

	public VertexShader(String path)
	{
		super(path + FILE_NAME, VERTEX_SHADER);
	}
}
