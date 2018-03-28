package de.janik.devils.entity;

import de.janik.devils.math.Matrix;
import de.janik.devils.math.Vector;

/**
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class TransformationObject implements iTransform
{
	private Vector	origin;

	private Vector	translation;
	private Vector	rotation;
	private Vector	scale;

	private Matrix	matrix_translate;
	private Matrix	matrix_rotate;
	private Matrix	matrix_scale;

	public TransformationObject(Vector position)
	{
		this(position, new Vector());
	}

	public TransformationObject(Vector position, Vector origin)
	{
		this.origin = origin;

		translation = new Vector(position);
		rotation = new Vector(0, 0, 0);
		scale = new Vector(1, 1, 1);

		matrix_translate = new Matrix();
		matrix_translate.loadIdentity();

		matrix_rotate = new Matrix();
		matrix_rotate.loadIdentity();

		matrix_scale = new Matrix();
		matrix_scale.loadIdentity();
	}

	public Matrix getTransformationMatrix()
	{
		// Instead of using this:
		/*
		 * 	Matrix matrix_translate = new Matrix().translate(translation);
		 *	Matrix matrix_rotate = new Matrix().translate(origin);
		 *  	   matrix_rotate = new Matrix().rotate(rotation);
		 * Matrix matrix_scale = new Matrix().scale(scale);
		 */
		//	I use global variables for the matrices, to avoid creating every tick new matrices.

		matrix_translate.translate(translation);
		matrix_rotate.translate(origin);
		matrix_rotate.rotate(rotation);
		matrix_scale.scale(scale);

		return Matrix.Multilply(matrix_translate, Matrix.Multilply(matrix_rotate, matrix_scale));
		// return matrix_translate.mul(matrix_rotate.mul(matrix_scale));
	}

	public Vector getTranslation()
	{
		return translation;
	}

	public void translate(Vector translation)
	{
		this.translation = translation;
	}

	public void translate(float x, float y, float z)
	{
		this.translation = new Vector(x, y, z);
	}

	public Vector getRotation()
	{
		return rotation;
	}

	public void rotate(Vector rotation)
	{
		this.rotation = rotation;
	}

	public void rotate(float x, float y, float z)
	{
		this.rotation = new Vector(x, y, z);
	}

	public Vector getScale()
	{
		return scale;
	}

	public void scale(Vector scale)
	{
		this.scale = scale;
	}

	public void scale(float factor)
	{
		scale(factor, factor, factor);
	}

	public void scale(float x, float y, float z)
	{
		this.scale = new Vector(x, y, z);
	}
}
