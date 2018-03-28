package de.janik.devils.entity.model;

import de.janik.devils.entity.Entity;
import de.janik.devils.utility.Color;

public class Model extends Entity
{
	private Mesh[]	meshes;

	public Model(Mesh... meshes)
	{
		super();

		this.meshes = meshes;

		for (Mesh mesh : this.meshes)
			if (!mesh.isInitialized())
			{
				if (!mesh.isFinnalized())
					mesh.finalize();

				mesh.init();
			}
	}

	@Override
	public void render()
	{
		for (Mesh mesh : meshes)
			mesh.render();
	}

	@Override
	public void destroy()
	{

	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(float amount)
	{
		for (Mesh mesh : meshes)
			mesh.scale(amount);
	}

	@Override
	public void rotate(float x, float y, float z)
	{
		for (Mesh mesh : meshes)
			mesh.rotate(x, y, z);
	}

	public void setLightingEnabled(boolean ligthing)
	{
		for (Mesh mesh : meshes)
			mesh.getMaterial().setLightingEnabled(ligthing);
	}

	public void setColor(Color color)
	{
		for (Mesh mesh : meshes)
			mesh.getMaterial().setColor(color);
	}

	public Mesh[] getMeshes()
	{
		return meshes;
	}

	public Mesh getMesh(int i)
	{
		return meshes[i];
	}

	public void setMeshes(Mesh[] meshes)
	{
		this.meshes = meshes;
	}

}
