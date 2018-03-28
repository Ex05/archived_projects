package de.janik.devils.shader.PhongShader;

import de.janik.devils.entity.model.Material;
import de.janik.devils.light.BaseLight;
import de.janik.devils.light.DirectionalLight;
import de.janik.devils.light.PointLight;
import de.janik.devils.math.Matrix;
import de.janik.devils.math.Vector;
import de.janik.devils.shader.FragmentShader;
import de.janik.devils.shader.GeometryShader;
import de.janik.devils.shader.ShaderProgramm;
import de.janik.devils.shader.VertexShader;
import de.janik.devils.state.GameState;
import de.janik.devils.utility.Color;

public class PhongShader extends ShaderProgramm
{
	public static final String		LOCATION;

	public static final String		UNIFORM_MODEL_TRANSFOMRATION_MATRIX;
	public static final String		UNIFORM_PROJECTION_MATRIX;
	public static final String		UNIFORM_COLOR;
	public static final String		UNIFORM_AMBIENT_LIGHT;

	public static final String		UNIFORM_DIRECTIONAL_LIGHT_BASE_COLOR;
	public static final String		UNIFORM_DIRECTIONAL_LIGHT_BASE_INTENSITY;
	public static final String		UNIFORM_DIRECTIONAL_LIGHT_DIRECTION;
	public static final String		UNIFORM_LIGHTING_ENABLED;
	public static final String		UNIFORM_AMBIENT_LIGHT_MODIFYER;

	public static final String		UNIFORM_SPECULAR_INTENSITY;
	public static final String		UNIFORM_SPECULAR_POWER;
	public static final String		UNIFORM_CAMERA_POSITION;

	private static final int		MAX_POINT_LIGHTS	= 4;

	private static PhongShader		shader;

	public static Color				ambientLight		= new Color(1, 1, 1);
	public static DirectionalLight	directionalLight	= new DirectionalLight(new BaseLight(new Color(1, 1, 1), 0), new Vector());
	private static PointLight[]		pointLights			= {};

	public static Matrix			projectionMatrix;
	public Matrix					modelViewMatrix;

	public Material					material;

	static
	{
		String name = PhongShader.class.getSimpleName();

		LOCATION = "/res/shader/" + name;

		UNIFORM_MODEL_TRANSFOMRATION_MATRIX = "modelTransformation";
		UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
		UNIFORM_COLOR = "baseColor";
		UNIFORM_AMBIENT_LIGHT = "ambientLight";

		UNIFORM_DIRECTIONAL_LIGHT_BASE_COLOR = "directionalLight.base.color";
		UNIFORM_DIRECTIONAL_LIGHT_BASE_INTENSITY = "directionalLight.base.intensity";
		UNIFORM_DIRECTIONAL_LIGHT_DIRECTION = "directionalLight.direction";
		UNIFORM_LIGHTING_ENABLED = "lighting";
		UNIFORM_AMBIENT_LIGHT_MODIFYER = "lightLevel";

		UNIFORM_SPECULAR_INTENSITY = "specularIntensity";
		UNIFORM_SPECULAR_POWER = "specularPower";
		UNIFORM_CAMERA_POSITION = "cameraPos";
	}

	private PhongShader(VertexShader vert, FragmentShader frag, GeometryShader geom)
	{
		super(vert, frag, geom);

		addUniform(UNIFORM_MODEL_TRANSFOMRATION_MATRIX);
		addUniform(UNIFORM_PROJECTION_MATRIX);
		addUniform(UNIFORM_COLOR);
		addUniform(UNIFORM_AMBIENT_LIGHT);

		addUniform(UNIFORM_DIRECTIONAL_LIGHT_BASE_COLOR);
		addUniform(UNIFORM_DIRECTIONAL_LIGHT_BASE_INTENSITY);
		addUniform(UNIFORM_DIRECTIONAL_LIGHT_DIRECTION);
		addUniform(UNIFORM_LIGHTING_ENABLED);
		addUniform(UNIFORM_AMBIENT_LIGHT_MODIFYER);

		addUniform(UNIFORM_SPECULAR_INTENSITY);
		addUniform(UNIFORM_SPECULAR_POWER);
		addUniform(UNIFORM_CAMERA_POSITION);

		for (int i = 0; i < MAX_POINT_LIGHTS; i++)
		{
			addUniform("pointLights[" + i + "].base.color");
			addUniform("pointLights[" + i + "].base.intensity");
			addUniform("pointLights[" + i + "].attenuation.constant");
			addUniform("pointLights[" + i + "].attenuation.linear");
			addUniform("pointLights[" + i + "].attenuation.exponent");
			addUniform("pointLights[" + i + "].range");
			addUniform("pointLights[" + i + "].position");
		}
	}

	private PhongShader()
	{
		this(new VertexShader(LOCATION), new FragmentShader(LOCATION), null);
	}

	public void updateUniform()
	{
		setUniform(UNIFORM_MODEL_TRANSFOMRATION_MATRIX, modelViewMatrix);
		setUniform(UNIFORM_PROJECTION_MATRIX, projectionMatrix);
		setUniform(UNIFORM_COLOR, material.getColor());
		setUniform(UNIFORM_LIGHTING_ENABLED, material.isLightingEnabled());
		setUniform(UNIFORM_AMBIENT_LIGHT, ambientLight);
		setUniform(UNIFORM_AMBIENT_LIGHT_MODIFYER, material.getAmbienLightModifyer());

		setUniform(UNIFORM_SPECULAR_INTENSITY, material.getSpecularIntensity());
		setUniform(UNIFORM_SPECULAR_POWER, material.getSpecularExponent());

		setUniform(UNIFORM_CAMERA_POSITION, GameState.getCamera().getPosition_Inverted());

		setUniform(directionalLight);

		for (int i = 0; i < pointLights.length; i++)
			setUniform("pointLights[" + i + "]", pointLights[i]);
	}

	public static PhongShader getInstance()
	{
		if (shader == null)
			shader = new PhongShader();

		return shader;
	}

	public void setUniform(String uniformName, PointLight light)
	{
		setUniform(uniformName + ".base.color", light.getColor());
		setUniform(uniformName + ".base.intensity", light.getIntensity());
		setUniform(uniformName + ".attenuation.constant", light.getAttenuation().getConstant());
		setUniform(uniformName + ".attenuation.linear", light.getAttenuation().getLinear());
		setUniform(uniformName + ".attenuation.exponent", light.getAttenuation().getExponent());
		setUniform(uniformName + ".range", light.getRange());
		setUniform(uniformName + ".position", light.getPosition());

	}

	private void setUniform(DirectionalLight directionalLight)
	{
		setUniform(UNIFORM_DIRECTIONAL_LIGHT_BASE_COLOR, directionalLight.getColor());
		setUniform(UNIFORM_DIRECTIONAL_LIGHT_BASE_INTENSITY, directionalLight.getIntensity());
		setUniform(UNIFORM_DIRECTIONAL_LIGHT_DIRECTION, directionalLight.getDirection());
	}

	public static void addPointLights(PointLight... lights)
	{
		if (lights.length > 4)
		{
			System.err.println("To many light's doo!!!");
			new Exception().printStackTrace();
			System.exit(1);
		}
		pointLights = lights;
	}
}