package de.janik.devils.shader;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class ShaderSource
{
	private String	shader;

	public ShaderSource(String shader)
	{
		this.shader = shader;
	}

	// <-Getter & Setter->
	public String getSourceCode()
	{
		return shader;
	}
}
