package de.janik.devils.state.intro;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.awt.Dimension;
import java.util.ArrayList;

import de.janik.devils.camera.Camera;
import de.janik.devils.controller.GameEngine;
import de.janik.devils.entity.SkyBox;
import de.janik.devils.entity.model.Model;
import de.janik.devils.entity.model.objloader.OBJ_File;
import de.janik.devils.entity.model.objloader.ObjLoader;
import de.janik.devils.light.Attenuation;
import de.janik.devils.light.BaseLight;
import de.janik.devils.light.DirectionalLight;
import de.janik.devils.light.PointLight;
import de.janik.devils.math.Vector;
import de.janik.devils.shader.PhongShader.PhongShader;
import de.janik.devils.state.GameState;
import de.janik.devils.texture.LWJGL_TextureLoader;
import de.janik.devils.texture.TextureBuilder;
import de.janik.devils.utility.Color;

public class Intro extends GameState
{
	private ArrayList<Model>	models;

	private SkyBox				skyBox;

	PointLight					light1;

	public Intro(GameEngine engine)
	{
		super(engine);

		camera = new Camera(0.0F, -15.0F, 30.0F);

		controller = new IntroController(this);
	}

	@Override
	public void init()
	{
		super.init();

		models = new ArrayList<Model>();

		/*
		Mesh m = ObjLoader.getInstance().load("Sphere.obj", "Sphere");
		m.setMaterial(new Material(Color.YELLOW, false, 1, 8));

		m.scale(0.25f);
		meshes.add(m);

		Mesh m1 = ObjLoader.getInstance().load("Sphere.obj", "Sphere");

		meshes.add(m1);

		Mesh m2 = ObjLoader.getInstance().load("Plane2.obj", "Plane");

		//m2.scale(10f);
		m2.translate(0, -8, 0);
		meshes.add(m2);
		*/

		//Model model = ObjLoader.getInstance().load("Character2.obj", "Character");		
		//model.scale(10);
		//models.add(model);

		/** Testing */
		//Model sphere = ObjLoader.getInstance().load("Sphere.obj", "Sphere");

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

		b.setTextureFile("./res/img/test.png");

		//		Model model = new Model(mesh);
		Model model = ObjLoader.getInstance().load(new OBJ_File("Character.obj", "Character"));
		model.scale(4);
		model.getMesh(0).getMaterial().setTexture(b.loadTexture());

		models.add(model);

		Model plane = ObjLoader.getInstance().load(new OBJ_File("Plane.obj", "Plane"));
		models.add(plane);

		/** !Tesing */

		skyBox = SkyBox.getInstance("CloudySky", 200);

		PhongShader.ambientLight = new Color(0.25f, 0.25f, 0.25f);
		PhongShader.directionalLight = new DirectionalLight(new BaseLight(new Color(0.5f, 0.5f, 0.5f), 1f), new Vector(1, 1, 1));

		light1 = new PointLight(new BaseLight(new Color(1, 0.5f, 0), 0.8f), new Vector(0f, 0f, 0f), new Attenuation(0, 0, 1), 4);

		//PhongShader.addPointLights(light1);

		Dimension canvasSize = engine.getCanvasSize();

		camera.initProjection(70.0F, canvasSize.width, canvasSize.height, 0.01F, 200.0F);

		tick();
	}

	@Override
	public void initOpenGL()
	{
		glClearColor(Color.BLACK);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);

		glEnable(GL_CULL_FACE);

		glEnable(GL_ALPHA_TEST);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LINEAR);

		glEnable(GL_DEPTH_CLAMP);
		//glEnable(GL_FRAMEBUFFER_SRGB);
	}

	@Override
	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		skyBox.render();

		for (Model model : models)
		{
			model.render();
		}
	}

	float	tmp	= 0;

	@Override
	public void tick()
	{
		super.tick();

		tmp += 0.00025;

		float sin = (float) Math.sin(tmp);

		light1.setPosition(new Vector(0f, sin * 12f, 0f));

		//System.out.println(sin * 12);

		skyBox.tick(camera.getPosition());
		skyBox.setLightLEvel(1.5f + Math.abs(sin * 5));

		PhongShader.projectionMatrix = camera.getProjectedTransformationMatrix();
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}
}