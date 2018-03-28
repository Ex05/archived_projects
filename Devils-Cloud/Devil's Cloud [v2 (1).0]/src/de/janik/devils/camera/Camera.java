package de.janik.devils.camera;

import de.janik.devils.math.Matrix;
import de.janik.devils.math.Vector;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Camera
{
	private Vector	startPosition;
	private Vector	position;

	private Matrix	projectionMatrix;
	private Matrix	translationMatrix;
	private Matrix	rotationMatrix;

	private float	pitch, yaw;

	public Camera(float x, float y, float z)
	{
		position = new Vector(x, y, z);
		startPosition = new Vector(position);

		translationMatrix = new Matrix();

		rotationMatrix = new Matrix();

		projectionMatrix = new Matrix();
		projectionMatrix.loadIdentity();

		pitch = yaw = 0;
	}

	public Matrix getTransformationMatrix()
	{
		translationMatrix.translate(position);
		rotationMatrix.rotate(pitch, yaw, 0);

		return Matrix.Multilply(rotationMatrix, translationMatrix);
	}

	public Matrix getProjectedTransformationMatrix()
	{
		return Matrix.Multilply(projectionMatrix, getTransformationMatrix());
	}

	public void yaw(float amount)
	{
		yaw += amount;
	}

	public void yaw(double amount)
	{
		yaw += (float) amount;
	}

	public void pitch(double amount)
	{
		pitch += (float) amount;
	}

	public void pitch(float amount)
	{
		pitch += amount;
	}

	public void moveForward(float distance)
	{
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void moveBackward(float distance)
	{
		position.x += distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void strafeLeft(float distance)
	{
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	public void strafeRight(float distance)
	{
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}

	public void moveUp(float distance)
	{
		position.y -= distance;
	}

	public void moveDown(float distance)
	{
		position.y += distance;
	}

	public void initProjection(float fov, float width, float height, float zNear, float zFar)
	{
		projectionMatrix.createProjection(fov, width, height, zNear, zFar);
	}

	public void reset()
	{
		position = new Vector(startPosition);

		pitch = yaw = 0;
	}

	public Vector getPosition()
	{
		return position;
	}

	public Vector getPosition_Inverted()
	{
		Vector v = new Vector(position);

		return v.multiply(new Vector(-1, -1, -1));
	}
}
