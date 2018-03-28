package de.janik.devils.entity.model.objloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

import de.janik.devils.entity.model.Material;
import de.janik.devils.texture.LWJGL_TextureLoader;
import de.janik.devils.texture.TextureBuilder;

public class MaterialLoader
{
	public static final String			TEXTURE_LOCATION	= "/img/";

	private static MaterialLoader		loader;

	private Hashtable<String, Material>	list;

	private MaterialLoader()
	{
		list = new Hashtable<String, Material>(1);
	}

	public Material load(Material_File material)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(material.getInputStream()));

		String line = "";
		@SuppressWarnings("unused")
		int lines = 0;

		Material currentMaterial = new Material();

		try
		{
			while ((line = br.readLine()) != null)
			{
				lines++;

				if (line.equals(""))
					continue;

				StringTokenizer st = new StringTokenizer(line);

				MTL_Token token = getToken(st.nextToken());

				String[] elements = new String[st.countTokens()];

				int i = 0;
				while (st.hasMoreElements())
				{
					elements[i] = st.nextElement() + "";
					i++;
				}

				switch (token)
				{
					case NEW_MATERIAL:
						String name = elements[0];

						list.put(name, currentMaterial);
						break;
					case TEXTURE_MAP:
						TextureBuilder b = new TextureBuilder();
						b.setMipMap(true);
						b.setAutoCreateMipMap(true);
						b.setMipMapLevel(4);
						b.setMinificationFilter(LWJGL_TextureLoader.GL_LINEAR_MIPMAP_LINEAR);
						b.setMagnificationFilter(LWJGL_TextureLoader.GL_LINEAR_MIPMAP_LINEAR);
						b.setPixelFormat(LWJGL_TextureLoader.GL_RGBA);
						b.setTextureTarget(LWJGL_TextureLoader.GL_TEXTURE_2D);
						b.setWrap_S(LWJGL_TextureLoader.GL_MIRRORED_REPEAT);
						b.setWrap_T(LWJGL_TextureLoader.GL_MIRRORED_REPEAT);

						b.setTextureFile(material.getParent() + TEXTURE_LOCATION + elements[0]);

						list.get(currentMaterial).setTexture(b.loadTexture());
						break;

					default:
						break;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public Material getMaterial(String identifier)
	{
		return list.get(identifier);
	}

	private MTL_Token getToken(String token)
	{
		if (token.equals(MTL_Token.NEW_MATERIAL.getName()))
			return MTL_Token.NEW_MATERIAL;
		else
			if (token.equals(MTL_Token.TEXTURE_MAP.getName()))
				return MTL_Token.TEXTURE_MAP;
			else
				return MTL_Token.UNDEFINED;
	}

	public static MaterialLoader getInstance()
	{
		if (loader == null)
			loader = new MaterialLoader();

		return loader;
	}

}
