package de.janik.devils.shader;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.util.HashMap;

import de.janik.devils.math.Matrix;
import de.janik.devils.math.Vector;
import de.janik.devils.utility.Color;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class ShaderProgramm
{
	private final int					programmID;

	private VertexShader				vert;
	private FragmentShader				frag;
	private GeometryShader				geom;

	private HashMap<String, Integer>	uniforms;

	protected ShaderProgramm(VertexShader vert, FragmentShader frag, GeometryShader geom)
	{
		this.vert = vert;
		this.frag = frag;
		this.geom = geom;

		uniforms = new HashMap<String, Integer>();

		programmID = glCreateProgram();

		if (programmID == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location.");
			new Exception().printStackTrace();
			System.exit(1);
		}

		compileProgramm();
	}

	public void bind()
	{
		glUseProgram(programmID);
	}

	public void release()
	{
		glUseProgram(0);
	}

	public void compileProgramm()
	{
		vert.compileShader();
		glAttachShader(programmID, vert.getID());

		frag.compileShader();
		glAttachShader(programmID, frag.getID());

		if (geom != null)
		{
			geom.compileShader();
			glAttachShader(programmID, geom.getID());
		}

		glLinkProgram(programmID);

		if (glGetProgrami(programmID, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(programmID, 1024));
			new Exception().printStackTrace();
			System.exit(1);
		}

		glValidateProgram(programmID);

		if (glGetProgrami(programmID, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(programmID, 1024));
			new Exception().printStackTrace();
			System.exit(1);
		}
	}

	public void addUniform(String uniformName)
	{
		int uniformLocation = glGetUniformLocation(programmID, uniformName);

		if (uniformLocation == 0xFF_FF_FF_FF)
		{
			System.out.println("Error: Could not find uniform: " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}

		uniforms.put(uniformName, uniformLocation);
	}

	public void setUniform(String uniformName, boolean b)
	{
		setUniform(uniformName, b ? 1 : 0);
	}

	public void setUniform(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, Vector vector)
	{
		glUniform3f(uniforms.get(uniformName), (float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
	}

	public void setUniform(String uniformName, Color color)
	{
		glUniform4f(uniforms.get(uniformName), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public void setUniform(String uniformName, Matrix matrix)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, matrix.store());
	}

	// <-Getter & Setter->
	public int getID()
	{
		return programmID;
	}
}
