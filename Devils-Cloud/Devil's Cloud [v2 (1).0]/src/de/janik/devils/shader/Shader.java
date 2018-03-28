package de.janik.devils.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Shader
{
	protected static final int	VERTEX_SHADER	= GL_VERTEX_SHADER;
	protected static final int	FRAGMENT_SHADER	= GL_FRAGMENT_SHADER;
	protected static final int	GEOMETRY_SHADER	= GL_GEOMETRY_SHADER;

	private final int			sID;

	private ShaderSource		source;

	public Shader(String path, int type)
	{
		this.source = ShaderLoader.getInstance().load(path);

		int handle = sID = glCreateShader(type);

		if (handle == 0)
		{
			System.err.println("ERROR, failed to allocated memory for shader.");
			System.exit(1);
		}
	}

	public void compileShader()
	{
		glShaderSource(sID, source.getSourceCode());
		glCompileShader(sID);

		if (glGetShaderi(sID, GL_COMPILE_STATUS) == 0)
		{
			System.err.println("LOL");
			System.err.println(glGetShaderInfoLog(sID, 1024));
			System.exit(1);
		}
	}

	public void delete()
	{
		glDeleteShader(sID);
	}

	// <-Getter & Setter->
	public String getSourceCode()
	{
		return source.getSourceCode();
	}

	public int getID()
	{
		return sID;
	}
}
