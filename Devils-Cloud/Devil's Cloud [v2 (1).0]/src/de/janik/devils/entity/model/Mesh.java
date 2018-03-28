package de.janik.devils.entity.model;

import static de.janik.devils.utility.BufferTools.createFloatBuffer;
import static de.janik.devils.utility.BufferTools.createIntBufferDirect;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import de.janik.devils.entity.Entity;
import de.janik.devils.math.Normal;
import de.janik.devils.math.Vector;
import de.janik.devils.shader.PhongShader.PhongShader;

public class Mesh extends Entity
{
	private int					vao;
	private int					ibo;
	private int					num_Elements;

	private Vertex[]			vertices;
	private int[]				indices;

	private Material			material		= new Material();
	private PhongShader			shader			= PhongShader.getInstance();

	private ArrayList<Vertex>	verticies_list;
	private ArrayList<Integer>	indices_list;

	private String				name;

	private boolean				finalized		= false;
	private boolean				initialized		= false;
	private boolean				normalsIncluded	= false;

	public Mesh()
	{
		super();

		verticies_list = new ArrayList<Vertex>();
		indices_list = new ArrayList<Integer>();
	}

	public Mesh(ArrayList<Vertex> vertecies, ArrayList<Integer> indices, boolean includesNormals)
	{
		this.verticies_list = vertecies;
		this.indices_list = indices;

		finalize();

		init();
	}

	public Mesh(Mesh mesh)
	{
		if (mesh.isInitialized())
		{
			vao = mesh.vao;
			ibo = mesh.ibo;
			num_Elements = mesh.num_Elements;
			material = mesh.material;

			vertices = mesh.vertices;
			indices = mesh.indices;
			finalized = true;
			initialized = true;
		}
		else
			if (mesh.isFinnalized())
			{
				vertices = mesh.vertices;
				indices = mesh.indices;
				finalized = true;
			}
			else
			{
				verticies_list = mesh.verticies_list;
				indices_list = mesh.indices_list;
			}
	}

	public Mesh(Vertex[] vertices, int[] indices, boolean includesNormals)
	{
		this(vertices, indices, includesNormals, new Material());
	}

	public Mesh(Vertex[] vertices, int[] indices, boolean includesNormals, Material material)
	{
		super();

		this.material = material;
		this.indices = indices;
		this.vertices = vertices;
		this.normalsIncluded = includesNormals;

		this.finalized = true;

		init();
	}

	public void init()
	{
		if (initialized)
			return;

		if (!finalized)
			new Exception("ERROR: Mesh must be finnalized to be initialized !!!").printStackTrace();

		if (!normalsIncluded)
			this.vertices = calcNormals(vertices, indices);

		num_Elements = indices.length;

		FloatBuffer verticesBuffer = CreateFloatBuffer(vertices);
		IntBuffer indicesBuffer = createIntBufferDirect(indices);

		vao = glGenVertexArrays();

		glBindVertexArray(vao);

		int vbo = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

		glVertexAttribPointer(0, Vector.ELEMENTS, GL_FLOAT, false, Vertex.ELEMENTS * 4, 0);
		glVertexAttribPointer(1, TextureCoordinate.ELEMENTS, GL_FLOAT, false, Vertex.ELEMENTS * 4, 12);
		glVertexAttribPointer(2, Normal.ELEMENTS, GL_FLOAT, false, Vertex.ELEMENTS * 4, 20);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindVertexArray(0);

		ibo = glGenBuffers();

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		initialized = true;
	}

	public void finalize()
	{
		vertices = verticies_list.toArray(new Vertex[verticies_list.size()]);
		indices = new int[indices_list.size()];

		for (int i = 0; i < indices.length; i++)
			indices[i] = indices_list.get(i);

		finalized = true;
	}

	@Override
	public void render()
	{
		if (material.hasTexture())
		{
			glActiveTexture(GL_TEXTURE0);
			material.getTexture().bind();
		}

		shader.bind();

		shader.modelViewMatrix = getTransformationObject().getTransformationMatrix();
		shader.material = getMaterial();

		shader.updateUniform();

		glBindVertexArray(vao);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, num_Elements, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

		glBindVertexArray(0);

		shader.release();

		if (material.hasTexture())
			material.getTexture().release();
	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	private static FloatBuffer CreateFloatBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.ELEMENTS);

		for (Vertex vertex : vertices)
		{
			Vector v = vertex.getPosition();
			TextureCoordinate texCoord = vertex.getTexCoord();
			Normal n = vertex.getNormal();

			buffer.put(v.getX());
			buffer.put(v.getY());
			buffer.put(v.getZ());

			buffer.put(texCoord.getU());
			buffer.put(texCoord.getV());

			buffer.put(n.getX());
			buffer.put(n.getY());
			buffer.put(n.getZ());
		}

		buffer.flip();

		return buffer;
	}

	private static Vertex[] calcNormals(Vertex[] vertices, int[] indices)
	{
		for (int i = 0; i < indices.length; i += 3)
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];

			Vector AB = Vector.Subtract(vertices[i1].getPosition(), vertices[i0].getPosition());
			Vector AC = Vector.Subtract(vertices[i2].getPosition(), vertices[i0].getPosition());

			Normal n = Vector.Normal(AB, AC).normalize();

			vertices[i0].setNormal(vertices[i0].getNormal().add(n));
			vertices[i1].setNormal(vertices[i1].getNormal().add(n));
			vertices[i2].setNormal(vertices[i2].getNormal().add(n));
		}

		for (Vertex vertex : vertices)
			vertex.setNormal(vertex.getNormal().normalize());

		return vertices;
	}

	//<-Getter & Setter->
	public Material getMaterial()
	{
		return material;
	}

	public Mesh setMaterial(Material material)
	{
		this.material = material;

		return this;
	}

	public boolean isFinnalized()
	{
		return finalized;
	}

	public boolean isInitialized()
	{
		return initialized;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
