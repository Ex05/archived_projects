package de.janik.devils.entity;

import java.io.File;
import java.util.ArrayList;

import de.janik.devils.entity.model.Material;
import de.janik.devils.entity.model.Mesh;
import de.janik.devils.entity.model.Model;
import de.janik.devils.entity.model.TextureCoordinate;
import de.janik.devils.entity.model.Vertex;
import de.janik.devils.math.Vector;
import de.janik.devils.texture.LWJGL_TextureLoader;
import de.janik.devils.texture.TextureBuilder;

/**
 * 
 * Attention: Not a 'Singleton'.
 * 
 */
public class SkyBox extends Model
{
	private static final String	SKYBOX_PATH;

	private static final String	SKYBOX_TOP;
	private static final String	SKYBOX_BOTTOM;
	private static final String	SKYBOX_LEFT;
	private static final String	SKYBOX_RIGHT;
	private static final String	SKYBOX_FRONT;
	private static final String	SKYBOX_BACK;

	public static float	LIGHT_LEVEL;

	private static Vector[]		positions;

	static
	{
		SKYBOX_PATH = "./res/skyBoxes/";

		SKYBOX_TOP = "/top.png";
		SKYBOX_BOTTOM = "/bottom.png";
		SKYBOX_LEFT = "/left.png";
		SKYBOX_RIGHT = "/right.png";
		SKYBOX_FRONT = "/front.png";
		SKYBOX_BACK = "/back.png";

		LIGHT_LEVEL = 2.0f;
	}

	private SkyBox(String name, float size)
	{
		super(LoadSkyBox(name, size, size));
	}

	private static Mesh[] LoadSkyBox(String name, float width, float height)
	{
		String location = SKYBOX_PATH + name;

		File path = new File(location);

		if (!path.exists())
		{
			System.err.println("The location: " + path.getAbsolutePath() + " does not exist !!!");

			return null;
		}
		else
		{
			float w = width / 2;
			float h = height / 2;

			ArrayList<Mesh> meshes = new ArrayList<Mesh>(6);
			ArrayList<Vector> positions = new ArrayList<Vector>();

			Vertex[] vertices = { new Vertex(new Vector(-w, 0, -h), new TextureCoordinate(0, 0)),
					new Vertex(new Vector(-w, 0, h), new TextureCoordinate(1, 0)), new Vertex(new Vector(w, 0, -h), new TextureCoordinate(0, 1)),
					new Vertex(new Vector(w, 0, h), new TextureCoordinate(1, 1)) };

			int[] indices = { 0, 1, 2, 3, 2, 1 };

			Mesh mesh = new Mesh(vertices, indices, false);

			TextureBuilder b = new TextureBuilder();
			b.setMipMap(true);
			b.setAutoCreateMipMap(true);
			b.setMipMapLevel(4);
			b.setMinificationFilter(LWJGL_TextureLoader.GL_LINEAR_MIPMAP_LINEAR);
			b.setMagnificationFilter(LWJGL_TextureLoader.GL_LINEAR_MIPMAP_LINEAR);
			b.setPixelFormat(LWJGL_TextureLoader.GL_RGBA);
			b.setTextureTarget(LWJGL_TextureLoader.GL_TEXTURE_2D);
			b.setWrap_S(LWJGL_TextureLoader.GL_CLAMP_TO_EDGE);
			b.setWrap_T(LWJGL_TextureLoader.GL_CLAMP_TO_EDGE);

			File top = new File(location + SKYBOX_TOP);

			if (top.exists())
			{
				b.setTextureFile(top);

				Mesh m = new Mesh(mesh);

				m.setMaterial(new Material(b.loadTexture(), false, LIGHT_LEVEL));

				m.translate(0, h, 0);
				positions.add(new Vector(0, h, 0));
				m.rotate(180, 180, 0);
				meshes.add(m);
			}

			File bottom = new File(location + SKYBOX_BOTTOM);

			if (bottom.exists())
			{
				b.setTextureFile(bottom);

				Mesh m = new Mesh(mesh);

				m.setMaterial(new Material(b.loadTexture(), false, LIGHT_LEVEL));

				m.translate(0, -h, 0);
				positions.add(new Vector(0, -h, 0));
				meshes.add(m);
			}

			File left = new File(location + SKYBOX_LEFT);

			if (left.exists())
			{
				b.setTextureFile(left);

				Mesh m = new Mesh(mesh);

				m.setMaterial(new Material(b.loadTexture(), false, LIGHT_LEVEL));

				m.translate(-w, 0, 0);
				positions.add(new Vector(-w, 0, 0));
				m.rotate(0, 0, 270);
				meshes.add(m);
			}

			File right = new File(location + SKYBOX_RIGHT);

			if (right.exists())
			{
				b.setTextureFile(right);

				Mesh m = new Mesh(mesh);

				m.setMaterial(new Material(b.loadTexture(), false, LIGHT_LEVEL));

				m.translate(w, 0, 0);
				positions.add(new Vector(w, 0, 0));
				m.rotate(0, 180, 90);
				meshes.add(m);
			}

			File front = new File(location + SKYBOX_FRONT);

			if (front.exists())
			{
				b.setTextureFile(front);

				Mesh m = new Mesh(mesh);

				m.setMaterial(new Material(b.loadTexture(), false, LIGHT_LEVEL));

				m.translate(0, 0, h);
				positions.add(new Vector(0, 0, h));
				m.rotate(270, 0, -90);
				meshes.add(m);
			}

			File back = new File(location + SKYBOX_BACK);

			if (back.exists())
			{
				b.setTextureFile(back);

				Mesh m = new Mesh(mesh);

				m.setMaterial(new Material(b.loadTexture(), false, LIGHT_LEVEL));

				m.translate(0, 0, -h);
				positions.add(new Vector(0, 0, -h));
				m.rotate(90, 180, 90);
				meshes.add(m);
			}

			SkyBox.positions = positions.toArray(new Vector[positions.size()]);

			return meshes.toArray(new Mesh[meshes.size()]);
		}
	}

	@Override
	public void render()
	{
		//glDepthMask(false);

		super.render();

		//glDepthMask(true);
	}

	public void tick(Vector cameraPosition)
	{
		for (int i = 0; i < positions.length; i++)
			getMesh(i).translate(Vector.Subtract(positions[i], cameraPosition));
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public static SkyBox getInstance(String name, float size)
	{
		return new SkyBox(name, size);
	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub

	}

	public void setLightLEvel(float lvel)
	{
		for(Mesh mesh : getMeshes())
			mesh.getMaterial().setAmbienLightModifyer(lvel);		
	}
}
