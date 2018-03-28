package de.janik.devils.entity.model.objloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import de.janik.devils.entity.model.Mesh;
import de.janik.devils.entity.model.Model;
import de.janik.devils.entity.model.TextureCoordinate;
import de.janik.devils.entity.model.Vertex;
import de.janik.devils.math.Vector;

public class ObjLoader
{
	private static final String	MODEL_LOCATION	= "./res/model/";

	private static ObjLoader	loader;

	private ObjLoader()
	{
		//<-Empty-Constructor->
	}

	public static ObjLoader getInstance()
	{
		if (loader == null)
			loader = new ObjLoader();

		return loader;
	}

	public Model load(OBJ_File objFile)
	{
		ArrayList<Mesh> meshes = new ArrayList<Mesh>();

		ArrayList<Vertex> vertecies = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<TextureCoordinate> texCoords = new ArrayList<TextureCoordinate>();

		MaterialLoader mtlLoader = MaterialLoader.getInstance();

		BufferedReader br = new BufferedReader(new InputStreamReader(objFile.getInputStream()));

		String line = "";
		int num_lines = 0;

		Mesh mesh = null;

		try
		{
			while ((line = br.readLine()) != null)
			{
				num_lines++;

				if (line.equals(""))
					continue;

				StringTokenizer st = new StringTokenizer(line);

				OBJ_Token token = getToken(st.nextToken());

				String[] elements = new String[st.countTokens()];

				int i = 0;
				while (st.hasMoreElements())
				{
					elements[i] = st.nextElement() + "";
					i++;
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("ERROR, while trying to read from file: " + objFile + " at line [" + num_lines + "]");
			e.printStackTrace();
		}
	}

	private OBJ_Token getToken(String token)
	{
		if (token.equals(OBJ_Token.COMMENT.getName()))
			return OBJ_Token.COMMENT;
		else
			if (token.equals(OBJ_Token.GROUP.getName()))
				return OBJ_Token.GROUP;
			else
				if (token.equals(OBJ_Token.OBJECT.getName()))
					return OBJ_Token.OBJECT;
				else
					if (token.equals(OBJ_Token.VECTOR.getName()))
						return OBJ_Token.VECTOR;
					else
						if (token.equals(OBJ_Token.TEXTURE_COORDINATE.getName()))
							return OBJ_Token.TEXTURE_COORDINATE;
						else
							if (token.equals(OBJ_Token.NORMAL.getName()))
								return OBJ_Token.NORMAL;
							else
								if (token.equals(OBJ_Token.FACE.getName()))
									return OBJ_Token.FACE;
								else
									return OBJ_Token.UNDEFINED;
	}

	private static TextureCoordinate ParsTextureCoordinate(String[] elements)
	{
		float u = ParsFloat(elements[0]);
		float v = ParsFloat(elements[0]);

		return new TextureCoordinate(u, v);
	}

	private static Vector ParsVector(String[] elements)
	{
		if (elements.length != 3)
			new Exception().printStackTrace();

		float x = ParsFloat(elements[0]);
		float y = ParsFloat(elements[1]);
		float z = ParsFloat(elements[2]);

		return new Vector(x, y, z);
	}

	private static int ParsInt(String s)
	{
		return Integer.parseInt(s);
	}

	private static float ParsFloat(String s)
	{
		return Float.parseFloat(s);
	}

	public Mesh loadMesh(String filename, String... subDirectories)
	{
		ArrayList<Vertex> vertecies = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<TextureCoordinate> texCoords = new ArrayList<TextureCoordinate>();

		@SuppressWarnings("unused")
		boolean normals = false, textureCoordinates = false;

		String sub_Dir = "";

		for (String s : subDirectories)
			sub_Dir += s + "/";

		BufferedReader br = null;
		File file = new File(MODEL_LOCATION + sub_Dir + filename);
		try
		{
			br = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("\n" + "Failed to laod obj-File: " + file);

			e.printStackTrace();
		}

		String line = "";

		try
		{
			while ((line = br.readLine()) != null)
			{
				String[] lineSplitt = line.split(" ");
				lineSplitt[0] = lineSplitt[0].trim();

				if (lineSplitt[0].equals("#") || line.equals(""))
					continue;
				else
				{
					if (lineSplitt[0].equals("v"))
					{
						float x = ParsFloat(lineSplitt[1]);
						float y = ParsFloat(lineSplitt[2]);
						float z = ParsFloat(lineSplitt[3]);

						vertecies.add(new Vertex(new Vector(x, y, z)));
					}
					else
					{
						if (lineSplitt[0].equals("f"))
						{
							if (textureCoordinates)
								for (int i = 1; i <= 3; i++)
								{
									String[] values = lineSplitt[i].split("/");

									int index = ParsInt(values[0]) - 1;

									indices.add(index);

									int tCoord = ParsInt(values[1]) - 1;

									vertecies.get(index).setTexCoord(texCoords.get(tCoord));
								}
						}
						else
						{
							if (lineSplitt[0].equals("vt"))
							{
								if (!textureCoordinates)
									textureCoordinates = true;

								float u = ParsFloat(lineSplitt[1]);
								float v = ParsFloat(lineSplitt[2]);

								texCoords.add(new TextureCoordinate(u, v));
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("\n" + "Failed to laod obj-File: " + file);

			e.printStackTrace();
		}
		finally
		{
			try
			{
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		Vertex[] vertexData = vertecies.toArray(new Vertex[vertecies.size()]);

		int[] indexData = new int[indices.size()];

		for (int i = 0; i < indexData.length; i++)
			indexData[i] = indices.get(i);

		return new Mesh(vertexData, indexData, false);
	}

}